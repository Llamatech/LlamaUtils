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
import com.llama.tech.utils.list.LlamaIterator;

public class LlamaGraph<K extends Comparable<K>, V extends Comparable<V>,A> 
{
	private Dictionary<K, GraphVertex<K, V,A>> vertices;
	private int numEdges = 0;
	private int numVertices = 0;
	
	
	public LlamaGraph()
	{
		vertices = new LlamaDict<K, GraphVertex<K, V,A>>(10);
	}
	
	public void addVertex(K key, V value)
	{
		vertices.addEntry(key, new GraphVertex<K, V,A>(key, value));
	}
	
	public void addEdge(K from, K to, int weight, A label)
	{
		GraphVertex<K, V,A> init_vertex = vertices.getValue(from);
		if(init_vertex != null)
		{
			GraphVertex<K, V,A> dest_vertex = vertices.getValue(to);
			if(dest_vertex != null)
			{
				init_vertex.addEdge(dest_vertex, weight, label);
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
		for(GraphEdge<K, V, A> outEdge : v.getEdgesTo())
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
		GraphVertex<K, V, A> buscado = vertices.getValue(key);
		for(GraphEdge<K, V, A> e: buscado.getEdgesFrom())
		{
			e.getOrigin().removeEdge(e);
		}
		
	}
	
	public void removeEdge(K origin, K destination)
	{
		GraphVertex<K, V,A> origen = vertices.getValue(origin);
		GraphVertex<K, V,A> destino = vertices.getValue(destination);
		
		for(GraphEdge<K, V,A> e:origen.getEdgesTo())
		{
			if(e.getDestination().equals(destino))
			{
				origen.removeEdge(e);
			}
		}
		
		
	}
	
	public void recorrerAnchura(K llave) //Tecnicamente BFS? Peor no busca
	{
		GraphVertex<K, V,A> inicial = vertices.getValue(llave); 
		Lista<GraphVertex<K, V,A>> cola = new LlamaArrayList<GraphVertex<K, V,A>>(20);
		
		cola.addAlFinal(inicial);
		while (!cola.isEmpty())
		{
			GraphVertex<K, V,A> actual = cola.removeFirst();
			actual.visit();
			
			for(GraphEdge<K, V,A> e:actual.getEdgesTo())
			{
				if(!e.getDestination().isVisited())
				{
					cola.addAlFinal(e.getDestination());
				}
			}
		}
		
		
	}

	public LlamaIterator<GraphVertex<K, V,A>> getVertices() {
		return vertices.getValues();
	}
	
	
	
}
