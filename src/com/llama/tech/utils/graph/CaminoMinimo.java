package com.llama.tech.utils.graph;

import java.util.Iterator;

import co.edu.uniandes.cupi2.estructuras.grafoDirigido.IArco;
import co.edu.uniandes.cupi2.estructuras.grafoDirigido.ICamino;
import co.edu.uniandes.cupi2.estructuras.grafoDirigido.ICaminosMinimos;
import co.edu.uniandes.cupi2.estructuras.grafoDirigido.IVertice;

import com.llama.tech.utils.dict.LlamaDict;
import com.llama.tech.utils.list.Lista;
import com.llama.tech.utils.list.LlamaArrayList;
import com.llama.tech.utils.list.LlamaIterator;
import com.llama.tech.utils.queue.LlamaHeap;


public class CaminoMinimo <K extends Comparable<K>, V extends Comparable<V>,A> implements ICaminosMinimos<K, V, A>{
	
	private LlamaGraph<K, V,A> g;
	private GraphVertex<K, V,A> vOrigen;
	private LlamaDict<K, NodoDijkstra<K,V,A>> vDestino;
	private LlamaHeap<NodoDijkstra<K, V,A>, Double> cola; //TODO: Cola prid Yo la hago!
	private LlamaDict<K, Camino<K,V,A>> caminos;
	private static CaminoMinimo instance;
	
	public CaminoMinimo(LlamaGraph<K,V,A> gp)
	{
		g = gp;
	}
	
	public static void initializeInstance(LlamaGraph gp)
	{
		instance = new CaminoMinimo(gp);
	}
	
	public static CaminoMinimo getInstance()
	{
		return instance;
	}
	
	public void setOrigin(GraphVertex<K, V, A> or)
	{
		vOrigen = or;
	}
	
	public void initialize()
	{
		cola = new LlamaHeap<NodoDijkstra<K, V,A>, Double>();
		Iterator<IVertice<K, V, A>> it = g.darVertices();
		while(it.hasNext())
		{
			GraphVertex<K, V,A> v=(GraphVertex<K, V,A>)it.next();
			v.desmarcar();
			vDestino.addEntry(v.darId(), new NodoDijkstra<K,V,A>(v, Integer.MAX_VALUE));
		}
	}
	
	public void relajar(NodoDijkstra<K, V,A> origen, GraphEdge<K, V,A> e)
	{
		NodoDijkstra<K, V,A> destino = vDestino.getValue(e.darDestino().darId());
		if(destino.getMinCost()>origen.getMinCost() + e.darCosto())
		{
			destino.setMinCost(origen.getMinCost()+e.darCosto());
			destino.setPred(origen);
		}
	}
	
	public void calcularCaminosMinimos()
	{
		initialize();
		NodoDijkstra<K, V,A> n = vDestino.getValue(vOrigen.darId());
		n.setMinCost(0);
		cola.push(n, 0.0);
		while(!cola.isEmpty())
		{
			NodoDijkstra<K, V,A> nd = cola.pop();
			nd.visit();
			for(IArco<K, V,A> ed: n.getV().getEdgesTo())
			{
				GraphEdge<K, V, A> e = (GraphEdge<K, V, A>) ed;
				if(!vDestino.getValue(e.darDestino().darId()).visited())
				{
					relajar(nd, e);
				}
			}
		}
	}
	
	public void reconstruirCaminosMinimos()
	{
		Iterator<NodoDijkstra<K, V, A>> it = vDestino.getValues();
		
		while(it.hasNext())
		{
			Lista<IVertice<K,V,A>> vertices = new LlamaArrayList<IVertice<K, V, A>>(10);
			Lista<IArco<K,V,A>> arcos = new LlamaArrayList<IArco<K, V, A>>(10);
			double costo=0;
			int longitud=0;
			NodoDijkstra<K, V, A> v = it.next();
			NodoDijkstra<K, V, A> actual = v;
			NodoDijkstra<K, V, A> pred = v.getPred();
			while(pred!=null)
			{
				vertices.addAlPrincipio(pred.getV());
				longitud++;
				arcos.addAlPrincipio(g.darArco(pred.getV().darId(), actual.getV().darId()));
				costo+=g.darArco(pred.getV().darId(), actual.getV().darId()).darCosto();
				
				actual = pred;
				pred = pred.getPred();
			}
			
			caminos.addEntry(v.getV().darId(), new Camino<>(arcos, vertices, costo, longitud));
		}
	}

	@Override
	public ICamino<K, V, A> darCaminoMinimo(K arg0) {
		return caminos.getValue(arg0);
	}

	@Override
	public IVertice<K, V, A> darOrigen() {
		return vOrigen;
	}
	
	
	

}
