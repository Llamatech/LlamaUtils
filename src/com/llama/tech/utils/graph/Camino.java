package com.llama.tech.utils.graph;

import java.util.Iterator;

import com.llama.tech.utils.list.Lista;

import co.edu.uniandes.cupi2.estructuras.grafoDirigido.IArco;
import co.edu.uniandes.cupi2.estructuras.grafoDirigido.ICamino;
import co.edu.uniandes.cupi2.estructuras.grafoDirigido.IVertice;

public class Camino<K,V,A> implements ICamino<K, V, A> 
{
	
	private Lista<IArco<K,V,A>> arcos;
	private Lista<IVertice<K, V, A>> vertices;
	private double costo;
	private int longitud;
	
	
	

	public Camino(Lista<IArco<K, V, A>> arcos,
			Lista<IVertice<K, V, A>> vertices, double costo, int longitud) {
		super();
		this.arcos = arcos;
		this.vertices = vertices;
		this.costo = costo;
		this.longitud = longitud;
	}

	@Override
	public Iterator<IArco<K, V, A>> darArcos() 
	{
		return arcos.iterator();
	}

	@Override
	public double darCosto() 
	{
		return costo;
	}

	@Override
	public int darLongitud() 
	{
		return longitud;
	}

	@Override
	public Iterator<IVertice<K, V, A>> darVertices() 
	{
		return vertices.iterator();
	}

}
