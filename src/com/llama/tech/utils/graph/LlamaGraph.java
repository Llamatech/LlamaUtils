/*
 * LlamaGraph.java
 * This file is part of LlamaUtils
 *
 * Copyright (C) 2015 - LlamaTech Team 
 *
 * LlamaUtils is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 *
 * LlamaUtils is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with LlamaUtils. If not, see <http://www.gnu.org/licenses/>.
 */

package com.llama.tech.utils.graph;

import com.llama.tech.utils.dict.Dictionary;
import com.llama.tech.utils.dict.LlamaDict;
import com.llama.tech.utils.dict.LlamaDict.UnhashableTypeException;
import com.llama.tech.utils.list.Lista;
import com.llama.tech.utils.list.LlamaArrayList;

public class LlamaGraph<K extends Comparable<K>, V extends Comparable<V>, A> 
{
	private Dictionary<K, GraphVertex<K, V, A>> vertices;
	private int numEdges = 0;
	private int numVertices = 0;
	
	
	public LlamaGraph()
	{
		vertices = new LlamaDict<K, GraphVertex<K, V, A>>(10);
	}
	
	public void addVertex(K key, V value)
	{
		vertices.addEntry(key, new GraphVertex<K, V, A>(key, value));
	}
	
	public void addEdge(K from, K to, A weight)
	{
		GraphVertex<K, V, A> init_vertex = vertices.getValue(from);
		if(init_vertex != null)
		{
			GraphVertex<K, V, A> dest_vertex = vertices.getValue(to);
			if(dest_vertex != null)
			{
				init_vertex.addEdge(dest_vertex, weight);
			}
			
		}
	}
	
	private void unVisitVertices()
	{
		for(K key: vertices)
		{
			vertices.getValue(key).unVisit();
		}
	}
	
	private void DFS(GraphVertex<K, V, A> v) //Usos por ahora? Recorrido, a futuro.
	{
		v.visit();
		for(GraphEdge<K, V, A> outEdge : v.getEdges())
		{
			GraphVertex<K, V, A> cVertex = outEdge.getDestination();
			if(!cVertex.isVisited())
			{
				DFS(cVertex);
			}
		}
	}
	
	
	
	public void removeVertex(K key)
	{
		//depende de como esten los edges? Si son dobles o no. Que contiene la lista de edges? 
		GraphVertex<K, V, A> buscado = DFS(vertex,key );
		for(GraphEdge<K, V, A> e: buscado.getEdges())
		{
			if(!e.getDestination().equals(buscado))
			{
				e.getDestination().removeEdge(e);
			}
			else
			{
				e.getOrigin().removeEdge(e);
			}
		}
		
		//o quito todos ooo vuelvo nulo el vertice
		
		for(GraphEdge<K, V, A> e: buscado.getEdges())
		{
			if(e.getDestination().equals(buscado))
			{
				e.setDestination(null);
			}
			else
			{
				e.setOrigin(null);
			}
		}
	}
	
	public void removeEdge(K origin, K destination)
	{
		GraphVertex<K, V, A> origen = DFS(vertex, origin);
		GraphVertex<K, V, A> destino = DFS(vertex, origin);
		
		for(GraphEdge<K, V, A> e:origen.getEdges())
		{
			if(e.getDestination().equals(destino))
			{
				origen.removeEdge(e);
//				destino.removeEdge(e);
			}
		}
		
		
	}
	
	public void recorrerAnchura(K llave) //Tecnicamente BFS? Peor no busca
	{
		GraphVertex<K, V, A> inicial = DFS(, llave); 
		Lista<GraphVertex<K, V, A>> cola = new LlamaArrayList<GraphVertex<K, V, A>>(20);
		
		cola.addAlFinal(inicial);
		while (!cola.isEmpty())
		{
			GraphVertex<K, V, A> actual = cola.removeFirst();
			actual.visit();
			
			for(GraphEdge<K, V, A> e:actual.getEdges())
			{
				if(!e.getDestination().isVisited())
				{
					cola.addAlFinal(e.getDestination());
				}
			}
		}
		
		
	}
	
}
