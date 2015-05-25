/*
 * LlamaHeap.java
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

package com.llama.tech.utils.queue;

/**
 * Implementación de un Heap de Emparejamiento (Min-Heap).
 * @author LlamaTech
 *
 * @param <V> Valor a almacenar.
 * @param <P> Valor de prioridad.
 */

public class LlamaHeap<V, P extends Comparable<P>> 
{
	/**
	 * Atributos del Heap.
	 */
	public HeapNode<V, P> root;
	private int size = 0;
	
	/**
	 * Obtiene el elemento de menor prioridad que se encuentra en el Heap.
	 * @return El elemento de menor prioridad, null si no hay elementos.
	 */
	public V pop()
	{
		if(root != null)
		{
			size--;
			V data = root.getValue();
			root = root.removeMin();
			return data;
		}
		return null;
	}
	
	/**
	 * Agrega un elemento con prioridad priorityValue al heap.
	 * @param value Valor del elemento.
	 * @param priorityValue Valor de prioridad.
	 */
	public void push(V value, P priorityValue)
	{
		HeapNode<V, P> subHeap = new HeapNode<V, P>(value, priorityValue);
		if(root == null)
		{
			root = subHeap;
		}
		else
		{
			root = root.mergeHeap(subHeap);	
		}
		
		size++;
	}
	
	/**
	 * Reduce la prioridad de un elemento que se encuentra en el heap.
	 * @param value Valor del elemento.
	 * @param priorityValue Nueva prioridad a asignar.
	 */
	public void decreasePriority(V value, P priorityValue)
	{
		root = root.decreasePriority(value, priorityValue);
	}

	/**
	 * Establece si el heap se encuentra sin elementos.
	 * @return true si no contiene elementos, false de lo contrario. 
	 */
	public boolean isEmpty()
	{
		return size == 0;
	}
	
	/**
	 * Establece si el heap contiene un elemento dado.
	 * @param value El elemento a buscar.
	 * @return true si se encuentra en el heap, false de lo contrario.
	 */
	public boolean contains(V value)
	{
		if(root != null)
		{
			return root.contains(value);
		}
		return false;
	}

}
