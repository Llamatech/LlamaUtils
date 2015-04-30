package com.llama.tech.utils.graph;

public class LlamaGraph<K extends Comparable<K>, V extends Comparable<V>, A> 
{
	private GraphVertex<K, V, A> vertex;
	private int numEdges = 0;
	private int numVertices = 0;
	
	
	public LlamaGraph(K key, V value)
	{
		vertex = new GraphVertex<K, V, A>(key, value);
	}
	
	public void addEdge(K from, K to, V value, A weight)
	{
		GraphVertex<K, V, A> init_vertex = DFS(vertex, from);
		unVisitVertices(vertex);
		if(init_vertex != null)
		{
			GraphVertex<K, V, A> dest_vertex = DFS(vertex, to);
			unVisitVertices(vertex);
			if(dest_vertex != null)
			{
				init_vertex.setEdge(dest_vertex, weight);
			}
			
			init_vertex.addEdge(to, value, weight);
		}
	}
	
	private void unVisitVertices(GraphVertex<K, V, A> v)
	{
		v.unVisit();
		for(GraphEdge<K, V, A> outEdge : v.getEdges())
		{
			GraphVertex<K, V, A> cVertex = outEdge.getDestination();
			unVisitVertices(cVertex);
		}
	}
	
	private GraphVertex<K, V, A> DFS(GraphVertex<K, V, A> v, K key)
	{
		v.visit();
		if(v.getKey().compareTo(key) == 0)
		{
			return v;
		}
		else
		{
			GraphVertex<K, V, A> dVertex = null;
			for(GraphEdge<K, V, A> outEdge : v.getEdges())
			{
				GraphVertex<K, V, A> cVertex = outEdge.getDestination();
				if(!cVertex.isVisited())
				{
					dVertex = DFS(cVertex, key);
					if(dVertex != null)
					{
						break;
					}
				}
			}
			return dVertex;
		}
	}
	
}
