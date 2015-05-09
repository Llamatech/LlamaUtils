package com.llama.tech.utils.graph;

import com.llama.tech.utils.dict.LlamaDict;
import com.llama.tech.utils.list.LlamaIterator;
import com.llama.tech.utils.queue.LlamaHeap;


public class CaminoMinimo <K extends Comparable<K>, V extends Comparable<V>,A> {
	
	private LlamaGraph<K, V,A> g;
	private GraphVertex<K, V,A> vOrigen;
	private LlamaDict<K, NodoDijkstra<K,V,A>> vDestino;
	private LlamaHeap<NodoDijkstra<K, V,A>, Double> cola; //TODO: Cola prioridad Yo la hago!
	
	public void initialize()
	{
		cola = new LlamaHeap<NodoDijkstra<K, V,A>, Double>();
		LlamaIterator<GraphVertex<K, V,A>> it = g.getVertices();
		while(it.hasNext())
		{
			GraphVertex<K, V,A> v=it.next();
			v.unVisit();
			vDestino.addEntry(v.getKey(), new NodoDijkstra<>(v, Integer.MAX_VALUE));
		}
	}
	
	public void relajar(NodoDijkstra<K, V,A> origen, GraphEdge<K, V,A> e)
	{
		NodoDijkstra<K, V,A> destino = vDestino.getValue(e.getDestination().getKey());
		if(destino.getMinCost()>origen.getMinCost() + e.getWeight())
		{
			destino.setMinCost(origen.getMinCost()+e.getWeight());
			destino.setPred(origen);
		}
	}
	
	public void calcularCaminosMinimos()
	{
		initialize();
		NodoDijkstra<K, V,A> n = vDestino.getValue(vOrigen.getKey());
		n.setMinCost(0);
		cola.push(n, 0.0);
		while(!cola.isEmpty())
		{
			NodoDijkstra<K, V,A> nd = cola.pop();
			nd.visit();
			for(GraphEdge<K, V,A> e: n.getV().getEdgesTo())
			{
				if(!vDestino.getValue(e.getDestination().getKey()).visited())
				{
					relajar(nd, e);
				}
			}
		}
	}
	
	
	

}
