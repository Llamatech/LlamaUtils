package com.llama.tech.utils.graph;

import com.llama.tech.utils.list.Lista;
import com.llama.tech.utils.list.LlamaArrayList;

public class GraphVertex<K extends Comparable<K>, V extends Comparable<V>, A> 
{
	private K key;
	private V value;
	
	private boolean visited = false;
	private Lista<GraphEdge<K, V, A>> edges;
	
	public GraphVertex(K key, V value) 
	{
		this.key = key;
		this.value = value;
		edges = new LlamaArrayList<GraphEdge<K, V, A>>(5);
	}
	
	public Lista<GraphEdge<K, V, A>> getEdges()
	{
		return edges;
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
	
	public void addNeighbor(GraphEdge<K, V, A> edge)
	{
		edges.addAlFinal(edge);
	}
	
	public void addEdge(K to, V value, A weight)
	{
		GraphVertex<K, V, A> temp = new GraphVertex<K, V, A>(to, value);
		GraphEdge<K, V, A> edge_1 = new GraphEdge<K, V, A>(this, temp, weight);
		GraphEdge<K, V, A> edge_2 = new GraphEdge<K, V, A>(temp, this, weight);
		edges.addAlFinal(edge_1);
		temp.addNeighbor(edge_2);
	}
	
	public void setEdge(GraphVertex<K, V, A> vertex, A weight)
	{		
		boolean exists = false;
		for(GraphEdge<K, V, A> edge : edges)
		{
			GraphVertex<K, V, A> neighbor = edge.getDestination();
			if(neighbor.equals(vertex))
			{
				exists = true;
				break;
			}
		}
		if(!exists)
		{
			GraphEdge<K, V, A> edge_1 = new GraphEdge<K, V, A>(this, vertex, weight);
			GraphEdge<K, V, A> edge_2 = new GraphEdge<K, V, A>(vertex, this, weight);
			edges.addAlFinal(edge_1);
			vertex.addNeighbor(edge_2);
		}
	}
	
//	boolean exists = false;
//	for(GraphEdge<K, V, A> edge : edges)
//	{
//		GraphVertex<K, V, A> neighbor = edge.getDestination();
//		if(neighbor.getKey().compareTo(to) == 0)
//		{
//			exists = true;
//			neighbor.setValue(value);
//		}
//	}
//	if(!exists)
//	{
//		
//	}
}
