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

import com.llama.tech.utils.list.Lista;
import com.llama.tech.utils.list.LlamaArrayList;

public class GraphVertex<K extends Comparable<K>, V extends Comparable<V>> implements Comparable<GraphVertex<K, V>>
{
	private K key;
	private V value;
	
	private boolean visited = false;
	private Lista<GraphEdge<K, V>> edgesTo;
	private Lista<GraphEdge<K, V>> edgesFrom;

	
	public GraphVertex(K key, V value) 
	{
		this.key = key;
		this.value = value;
		edgesTo = new LlamaArrayList<GraphEdge<K, V>>(5);
		edgesFrom = new LlamaArrayList<GraphEdge<K, V>>(5);
	}
	
	public Lista<GraphEdge<K, V>> getEdgesTo()
	{
		return edgesTo;
	}
	
	public Lista<GraphEdge<K, V>> getEdgesFrom()
	{
		return edgesFrom;
	}
	
	public K getKey()
	{
		return key;
	}
	
	public V getValue()
	{
		return value;
	}
	
	public void setValue(V val)
	{
		value = val;
	}
	
	public boolean isVisited()
	{
		return visited;
	}
	
	public void visit()
	{
		visited = true;
	}
	
	public void unVisit()
	{
		visited = false;
	}
	
	public void addNeighbor(GraphEdge<K, V> edge)
	{
		edgesTo.addAlFinal(edge);
		//TODO
	}

	public void addEdge(GraphVertex<K, V> vertex, int weight)
	{
		boolean modified = false;
		for(GraphEdge<K, V> edge: edgesTo)
		{
			if(edge.getDestination().compareTo(vertex) == 0)
			{
				edge.setWeight(weight);
				modified = true;
				break;
			}
		}
		
		if(!modified)
		{
			GraphEdge<K, V> edge = new GraphEdge<K, V>(this, vertex, weight);
			vertex.addEdgeFrom(edge);
			edgesTo.addAlFinal(edge);
		}
	}
	
	public void addEdgeFrom(GraphEdge<K, V> e)
	{
		edgesFrom.addAlFinal(e);
	}
	
	public void removeEdge(GraphEdge<K, V> e)
	{
		edgesTo.remove(e);
	}

	@Override
	public int compareTo(GraphVertex<K, V> vertex) 
	{
		return key.compareTo(vertex.getKey());
	}
}
