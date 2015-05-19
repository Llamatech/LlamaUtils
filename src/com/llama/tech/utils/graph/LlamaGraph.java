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

import java.io.Serializable;
import java.util.Iterator;

import co.edu.uniandes.cupi2.estructuras.grafoDirigido.IArco;
import co.edu.uniandes.cupi2.estructuras.grafoDirigido.ICamino;
import co.edu.uniandes.cupi2.estructuras.grafoDirigido.ICaminosMinimos;
import co.edu.uniandes.cupi2.estructuras.grafoDirigido.IGrafoDirigido;
import co.edu.uniandes.cupi2.estructuras.grafoDirigido.IVertice;

import com.llama.tech.utils.dict.Dictionary;
import com.llama.tech.utils.dict.LlamaDict;
import com.llama.tech.utils.dict.LlamaDict.UnhashableTypeException;
import com.llama.tech.utils.list.Lista;
import com.llama.tech.utils.list.LlamaArrayList;
import com.llama.tech.utils.list.LlamaIterator;

public class LlamaGraph<K extends Comparable<K>, V extends Comparable<V>,A> implements IGrafoDirigido<K, V, A>, Serializable
{
	private Dictionary<K, IVertice<K,V,A>> vertices;
	private int numEdges = 0;
	private int numVertices = 0;
	
	
	public LlamaGraph()
	{
		vertices = new LlamaDict<K, IVertice<K, V,A>>(10);
		CaminoMinimo.initializeInstance(this);
	}
	
	public boolean agregarVertice(K key, V value)
	{
		vertices.addEntry(key, new GraphVertex<K, V,A>(key, value));
		return true;
	}
	
	public boolean agregarArco(K from, K to, A label, double weight)
	{
		GraphVertex<K, V,A> init_vertex = (GraphVertex<K, V,A>) vertices.getValue(from);
		if(init_vertex != null)
		{
			GraphVertex<K, V,A> dest_vertex = (GraphVertex<K, V,A>) vertices.getValue(to);
			if(dest_vertex != null)
			{
				init_vertex.addEdge(dest_vertex, weight, label);
				return true;
			}
			
		}
		
		return false;
	}
	
	private void unVisitVertices()
	{
		for(K key: vertices)
		{
			vertices.getValue(key).desmarcar();
		}
	}
	
	private void DFS(GraphVertex<K, V, A> v, Lista<IVertice<K, V, A>> path) //Usos por ahora? Recorrido, a futuro.
	{
		v.marcar();
		path.addAlFinal(v);
		for(IArco<K,V,A> outEdge : v.getEdgesTo())
		{
			GraphVertex<K, V, A> cVertex = ((GraphEdge<K,V,A>) outEdge).darDestino();
			if(!cVertex.darMarca())
			{
				DFS(cVertex, path);
			}
		}
	}
	
	
	
	public IVertice<K, V, A> eliminarVertice(K key)
	{
		GraphVertex<K, V, A> buscado = (GraphVertex<K, V,A>) vertices.getValue(key);
		for(GraphEdge<K, V, A> e: buscado.getEdgesFrom())
		{
			e.darOrigen().removeEdge(e);
		}
		
		return buscado;
		
	}
	
	public IArco<K, V, A> eliminarArco(K origin, K destination)
	{
		GraphVertex<K, V, A> origen = (GraphVertex<K, V, A>) vertices.getValue(origin);
		GraphVertex<K, V, A> destino = (GraphVertex<K, V, A>) vertices.getValue(destination);
		
		for(IArco<K,V,A> e:origen.getEdgesTo())
		{
			if(e.darDestino().equals(destino))
			{
				return origen.removeEdge((GraphEdge<K, V, A>) e);
				
			}
		}
		
		return null;
		
		
	}
	
	public void recorrerAnchura(K llave, Lista<IVertice<K, V, A>> path) //Tecnicamente BFS? Peor no busca
	{
		GraphVertex<K, V,A> inicial = (GraphVertex<K, V, A>) vertices.getValue(llave); 
		Lista<GraphVertex<K, V,A>> cola = new LlamaArrayList<GraphVertex<K, V,A>>(20);
		
		cola.addAlFinal(inicial);
		while (!cola.isEmpty())
		{
			GraphVertex<K, V,A> actual = cola.removeFirst();
			actual.marcar();
			path.addAlFinal(actual);
			for(IArco<K,V,A> e:actual.getEdgesTo())
			{
				if(!e.darDestino().darMarca())
				{
					cola.addAlFinal(((GraphEdge<K, V, A>)e).darDestino());
				}
			}
		}
		
		
	}

	public Iterator<IVertice<K, V, A>> darVertices() 
	{
		return vertices.getValues();
	}




	@Override
	public IArco<K, V, A> darArco(K origin, K destination) 
	{
		GraphVertex<K, V, A> origen = (GraphVertex<K, V, A>) vertices.getValue(origin);
		GraphVertex<K, V, A> destino = (GraphVertex<K, V, A>) vertices.getValue(destination);
		
		for(IArco<K, V,A> e:origen.getEdgesTo())
		{
			if(((GraphEdge<K, V, A>)e).darDestino().equals(destino))
			{
				return e;
				
			}
		}
		
		return null;
	}
	

	@Override
	public ICamino<K, V, A> darCaminoMasBarato(K arg0, K arg1) 
	{
		GraphVertex<K, V, A> origen = (GraphVertex<K, V, A>)vertices.getValue(arg0);
		return origen.darCaminoMasBarato(arg1);
		
	}

	@Override
	public ICamino<K, V, A> darCaminoMenorLongitud(K arg0, K arg1) 
	{
		IVertice<K, V, A> origen = vertices.getValue(arg0);
		
		return origen.darCaminoMenorLongitud(arg1);
	}

	@Override
	public ICaminosMinimos<K, V, A> darCaminosMinimos(K arg0) 
	{
		GraphVertex<K, V, A> origin = ((GraphVertex<K, V, A>) vertices.getValue(arg0));
		CaminoMinimo<K,V,A> shortestRoute = CaminoMinimo.getInstance();
		shortestRoute.setOrigin(origin);
		shortestRoute.dijkstra();
		shortestRoute.reconstructPath();
		return shortestRoute;
	}

	@Override
	public int darOrden() 
	{
		return vertices.size();
	}

	@Override
	public Iterator<IVertice<K, V, A>> darRecorridoEnProfundidad(K arg0) 
	{
		Lista<IVertice<K, V, A>> path = new LlamaArrayList<IVertice<K, V, A>>(10);
		DFS((GraphVertex<K, V, A>) vertices.getValue(arg0), path);
		return path.iterator();
	}

	@Override
	public Iterator<IVertice<K, V, A>> darRecorridoPorNiveles(K arg0) 
	{
		Lista<IVertice<K, V, A>> path = new LlamaArrayList<IVertice<K, V, A>>(10);
		recorrerAnchura(arg0, path);
		return path.iterator();
	}

	@Override
	public IVertice<K, V, A> darVertice(K arg0) 
	{
	
		return vertices.getValue(arg0);
	}

	
	
}
