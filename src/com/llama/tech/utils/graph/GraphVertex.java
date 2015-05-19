/*
 * GraphVertex.java
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
import co.edu.uniandes.cupi2.estructuras.grafoDirigido.IVertice;

import com.llama.tech.utils.list.Lista;
import com.llama.tech.utils.list.LlamaArrayList;

public class GraphVertex<K extends Comparable<K>, V extends Comparable<V>, A> implements Comparable<GraphVertex<K, V,A>>, IVertice<K, V, A>, Serializable
{
	private K key;
	private V value;
	
	private boolean visited = false;
	private Lista<IArco<K, V,A>> edgesTo;
	private Lista<GraphEdge<K, V,A>> edgesFrom;

	
	public GraphVertex(K key, V value) 
	{
		this.key = key;
		this.value = value;
		edgesTo = new LlamaArrayList<IArco<K, V,A>>(5);
		edgesFrom = new LlamaArrayList<GraphEdge<K, V,A>>(5);
	}
	
	public Lista<IArco<K, V,A>> getEdgesTo()
	{
		return edgesTo;
	}
	
	public Lista<GraphEdge<K, V,A>> getEdgesFrom()
	{
		return edgesFrom;
	}
	
	public K darId()
	{
		return key;
	}
	
	public V darValor()
	{
		return value;
	}
	
	public void setValue(V val)
	{
		value = val;
	}
	
	public boolean darMarca()
	{
		return visited;
	}
	
	public void marcar()
	{
		visited = true;
	}
	
	public void desmarcar()
	{
		visited = false;
	}
	
	public void addNeighbor(GraphEdge<K, V,A> edge)
	{
		edgesTo.addAlFinal(edge);
		//TODO
	}

	public void addEdge(IVertice<K, V,A> vert, double weight, A label)
	{
		boolean modified = false;
		GraphVertex<K, V,A> vertex = (GraphVertex<K, V,A>) vert;
		for(IArco<K, V,A> edg: edgesTo)
		{
			GraphEdge<K, V,A> edge = (GraphEdge<K, V,A>) edg;
			if(edge.darDestino().compareTo(vertex) == 0)
			{
				edge.setWeight(weight);
				edge.setLabel(label);
				modified = true;
				break;
			}
		}
		
		if(!modified)
		{
			GraphEdge<K, V,A> edge = new GraphEdge<K, V,A>(this, vertex, weight,label);
			vertex.addEdgeFrom(edge);
			edgesTo.addAlFinal(edge);
		}
	}
	
	public void addEdgeFrom(GraphEdge<K, V,A> e)
	{
		edgesFrom.addAlFinal(e);
	}
	
	public IArco<K,V,A> removeEdge(GraphEdge<K, V,A> e)
	{
		return edgesTo.remove(e);
	}

	@Override
	public int compareTo(GraphVertex<K, V,A> vertex) 
	{
		return key.compareTo(vertex.darId());
	}

	@Override
	public ICamino<K, V, A> darCaminoMasBarato(K arg0) 
	{
		CaminoMinimo<K, V, A> caminoMinimo = CaminoMinimo.getInstance();
		caminoMinimo.setOrigin(this);
		caminoMinimo.dijkstra();
		caminoMinimo.reconstructPath();
		//caminoMinimo.initialize();
		//caminoMinimo.calcularCaminosMinimos();
		//caminoMinimo.reconstruirCaminosMinimos();
		return caminoMinimo.darCaminoMinimo(arg0);
		
	}

	@Override
	public ICamino<K, V, A> darCaminoMenorLongitud(K arg0) {
		
		GraphVertex<K, V, A> actual = this;
		GraphVertex<K, V, A> anterior = null;
		Lista<IVertice<K, V, A>> vertices = new LlamaArrayList<IVertice<K,V,A>>(10);
		Lista<IArco<K, V, A>> arcos = new LlamaArrayList<IArco<K,V,A>>(10);
		int longitud =0;
		double costo = 0;
		Lista<GraphVertex<K, V, A>> cola = new LlamaArrayList<>(10);
//		cola.addAlFinal(this);
		
		while(!cola.isEmpty())
		{
			
			actual.marcar();
			vertices.addAlFinal(actual);
			longitud++;
			if(actual!=this)
			{
				arcos.addAlFinal(anterior.darSucesor(actual.darId()));
				costo += anterior.darSucesor(actual.darId()).darCosto();
			}
			
			for(IArco<K, V,A> ed:actual.getEdgesTo())
			{
				GraphEdge<K, V, A> e = (GraphEdge<K, V, A>) ed;
				if(e.darDestino().darId().equals(arg0))
				{
					vertices.addAlFinal(e.darDestino());
					longitud++;
					arcos.addAlFinal(actual.darSucesor(e.darDestino().darId()));
					costo += actual.darSucesor(e.darDestino().darId()).darCosto();
					return new Camino<K,V,A>(arcos, vertices, costo, longitud);
				}
				if(!e.darDestino().darMarca())
				{
					cola.addAlFinal(e.darDestino());
				}
			}
			
			anterior = actual;
			actual = cola.removeFirst();
			
		}
		return null;
		
	}


	@Override
	public IArco<K, V, A> darSucesor(K arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Iterator<IArco<K, V, A>> darSucesores() {
		return edgesTo.iterator();
	}
	
	@Override
	public String toString()
	{
		return "Id: "+key+"; Value: "+value;
	}
}
