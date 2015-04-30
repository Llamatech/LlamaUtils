package com.llama.tech.utils.graph;

public class GraphEdge<K extends Comparable<K>, V extends Comparable<V>, A> 
{
	private A weight;
	
	private GraphVertex<K, V, A> origin;
	private GraphVertex<K, V, A> destination;
	
	public GraphEdge(GraphVertex<K, V, A> from, GraphVertex<K, V, A> to, A weight)
	{
		origin = from;
		destination = to;
		this.weight = weight;
	}
	
	public GraphVertex<K, V, A> getDestination() 
	{
		return destination;
	}
	
	public A getWeight()
	{
		return weight;
	}
	
	
}
