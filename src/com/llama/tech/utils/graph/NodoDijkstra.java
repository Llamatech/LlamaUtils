package com.llama.tech.utils.graph;

public class NodoDijkstra <K extends Comparable<K>, V extends Comparable<V>> implements Comparable<NodoDijkstra<K, V>> {
	
	private GraphVertex<K, V> v;
	private double minCost;
	private NodoDijkstra<K,V> pred;
	private boolean visited;
	
	public GraphVertex<K, V> getV() {
		return v;
	}
	public void setV(GraphVertex<K, V> v) {
		this.v = v;
	}
	public double getMinCost() {
		return minCost;
	}
	public void setMinCost(double minCost) {
		this.minCost = minCost;
	}
	public NodoDijkstra<K,V> getPred() {
		return pred;
	}
	public void setPred(NodoDijkstra<K,V> pred) {
		this.pred = pred;
	}
	public NodoDijkstra(GraphVertex<K, V> v, double minCost) {
		this.v = v;
		this.minCost = minCost;
		visited = false;
	}
	
	@Override
	public int compareTo(NodoDijkstra<K, V> o) {
		// TODO Auto-generated method stub
		return 0;
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

	
	
	



}
