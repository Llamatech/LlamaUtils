package com.llama.tech.utils.graph;

import java.io.Serializable;

public class NodoDijkstra <K extends Comparable<K>, V extends Comparable<V>,A> implements Comparable<NodoDijkstra<K, V,A>>, Serializable {
	
	private GraphVertex<K, V,A> v;
	private double minCost;
	private NodoDijkstra<K,V,A> pred;
	private boolean visited;
	
	public GraphVertex<K, V,A> getV() {
		return v;
	}
	public void setV(GraphVertex<K, V,A> v) {
		this.v = v;
	}
	public double getMinCost() {
		return minCost;
	}
	public void setMinCost(double minCost) {
		this.minCost = minCost;
	}
	public NodoDijkstra<K,V,A> getPred() {
		return pred;
	}
	public void setPred(NodoDijkstra<K,V,A> pred) {
		this.pred = pred;
	}
	public NodoDijkstra(GraphVertex<K, V,A> v, double minCost) {
		this.v = v;
		this.minCost = minCost;
		visited = false;
	}
	
	@Override
	public int compareTo(NodoDijkstra<K, V,A> o) {
		// TODO Auto-generated method stub
		return 0;
	}
	
	@Override
	public boolean equals(Object o)
	{
		NodoDijkstra<K, V, A> n = ((NodoDijkstra<K, V,A>) o);
		if(n.v.compareTo(v) == 0)
		{
			return true;
		}
		
		return false;
	}
	
	public void visit()
	{
		visited= true;
	}
	
	public void unVisit()
	{
		visited= false;
	}
	
	public boolean visited()
	{
		return visited;
	}

	@Override
	public String toString()
	{
		return v.darId()+"- Pred: "+((pred == null) ? "" : pred.v.darId())+"; Cost: "+minCost;
	}
	



}
