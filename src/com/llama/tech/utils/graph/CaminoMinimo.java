package com.llama.tech.utils.graph;

import java.io.Serializable;
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


public class CaminoMinimo <K extends Comparable<K>, V extends Comparable<V>,A> implements ICaminosMinimos<K, V, A>,Serializable{
	
	private LlamaGraph<K, V,A> g;
	private GraphVertex<K, V,A> vOrigen;
	private LlamaDict<K, NodoDijkstra<K,V,A>> vDestino = new LlamaDict<K, NodoDijkstra<K,V,A>>(10);
	private LlamaHeap<GraphVertex<K, V, A>, Double> cola; //TODO: Cola prid Yo la hago!
	private LlamaDict<K, Camino<K,V,A>> caminos = new LlamaDict<K, Camino<K,V,A>>(10);
	
	private LlamaDict<GraphVertex<K, V, A>, Double> dist = new LlamaDict<GraphVertex<K, V, A>, Double>(10);
	private LlamaDict<GraphVertex<K, V, A>, GraphVertex<K, V, A>> pred = new LlamaDict<GraphVertex<K, V, A>, GraphVertex<K, V, A>>(10);
	
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
	
//	public void initialize()
//	{
//		cola = new LlamaHeap<GraphVertex<K, V, A>, Double>();
//		Iterator<IVertice<K, V, A>> it = g.darVertices();
//		while(it.hasNext())
//		{
//			GraphVertex<K, V,A> v=(GraphVertex<K, V,A>)it.next();
//			v.desmarcar();
//			vDestino.addEntry(v.darId(), new NodoDijkstra<K,V,A>(v, Integer.MAX_VALUE));
//		}
//	}
//	
//	public void relajar(NodoDijkstra<K, V,A> origen, GraphEdge<K, V, A> e)
//	{
//		NodoDijkstra<K, V,A> destino = vDestino.getValue(e.darDestino().darId());
//		if(destino.getMinCost() > origen.getMinCost() + e.darCosto())
//		{
//			destino.setMinCost(origen.getMinCost()+e.darCosto());
//	
//			destino.setPred(origen);
//			if(cola.isEmpty() || !cola.contains(destino))
//			{
//				//System.out.println(destino);
//				cola.push(destino, destino.getMinCost());
//			}
//		}
//	}
//	
//	public void calcularCaminosMinimos()
//	{
//		initialize();
//		NodoDijkstra<K, V,A> n = vDestino.getValue(vOrigen.darId());
//		n.setMinCost(0);
//		cola.push(n, 0.0);
//		while(!cola.isEmpty())
//		{
//			NodoDijkstra<K, V,A> nd = cola.pop();
//			System.out.println(nd);
//			//nd.visit();
//			for(IArco<K, V,A> ed: n.getV().getEdgesTo())
//			{
//				GraphEdge<K, V, A> e = (GraphEdge<K, V, A>) ed;
//				//if(!vDestino.getValue(e.darDestino().darId()).visited())
//				//{
//					relajar(nd, e);
//				//}
//			}
//		}
//	}
	
	public void dijkstra()
	{
		dist = new LlamaDict<GraphVertex<K, V, A>, Double>(10);
		pred = new LlamaDict<GraphVertex<K, V, A>, GraphVertex<K, V, A>>(10);
		caminos = new LlamaDict<K, Camino<K,V,A>>(10);
		cola = new LlamaHeap<GraphVertex<K, V, A>, Double>();
		dist.addEntry(vOrigen, 0.0);
		cola.push(vOrigen, 0.0);
		Iterator<IVertice<K, V, A>> it = g.darVertices();
		while(it.hasNext())
		{
			GraphVertex<K, V,A> v = (GraphVertex<K, V,A>) it.next();
			if(v.compareTo(vOrigen) != 0)
			{
				dist.addEntry(v, Double.MAX_VALUE);
				pred.addEntry(v, new GraphVertex<K,V,A>(null, null));
				//cola.push(v, dist.getValue(v));
			}
		}
		
		while(!cola.isEmpty())
		{
			GraphVertex<K,V,A> u = cola.pop();
			//System.out.println(u+"\n");
			for(IArco<K,V,A> a : u.getEdgesTo())
			{
				GraphEdge<K, V, A> e = (GraphEdge<K, V, A>) a;
				GraphVertex<K, V, A> v = e.darDestino();
				double alt = dist.getValue(u) + e.darCosto();
				if(alt < dist.getValue(v))
				{
					dist.addEntry(v, alt);
					pred.addEntry(v, u);
					if(!cola.contains(v))
					{
						//System.out.println(v);
						cola.push(v, alt);
					}
					//cola.decreasePriority(v, alt);
				}
			}
		}
		//System.out.println(pred);
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
	
	public void reconstructPath()
	{
		Lista<IVertice<K,V,A>> vertices = new LlamaArrayList<IVertice<K, V, A>>(10);
		Lista<IArco<K,V,A>> arcs = new LlamaArrayList<IArco<K, V, A>>(10);
		Iterator<GraphVertex<K, V, A>> nodes = dist.getKeys();
		while(nodes.hasNext())
		{
			GraphVertex<K, V, A> v = nodes.next();
			GraphVertex<K, V, A> i = v;
			double cost = dist.getValue(v);
			int length = 0;
			boolean interrupt = false;
			while(v.compareTo(vOrigen) != 0)
			{
				vertices.addAlPrincipio(v);
				GraphVertex<K, V, A> prevNode = pred.getValue(v);
				//System.out.println(prevNode.darId()+","+v.darId());
				if(prevNode.darId() != null)
				{
					arcs.addAlFinal(g.darArco(prevNode.darId(), v.darId()));
					length++;
					v = prevNode;
				}
				else
				{
					//System.out.println("Interrupt!");
					interrupt = true;
				    break;
				}
			}
			
			if(!interrupt)
			{
				//System.out.println("Camino!");
				vertices.addAlPrincipio(vOrigen);
				caminos.addEntry(i.darId(), new Camino<K,V,A>(arcs,vertices,cost,length));
			}
			
			vertices = new LlamaArrayList<IVertice<K, V, A>>(10);
			arcs = new LlamaArrayList<IArco<K, V, A>>(10);
		}
	}

	@Override
	public ICamino<K, V, A> darCaminoMinimo(K arg0) 
	{
		return caminos.getValue(arg0);
	}

	@Override
	public IVertice<K, V, A> darOrigen() {
		return vOrigen;
	}
	
	public Iterator<Camino<K,V,A>> darCaminos()
	{
		return caminos.getValues();
	}
	
	
	

}
