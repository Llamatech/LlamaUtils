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

public class LlamaHeap<V, P extends Comparable<P>> 
{
	public HeapNode<V, P> root;
	private int size = 0;
	
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
	
	public void decreasePriority(V value, P priorityValue)
	{
		root = root.decreasePriority(value, priorityValue);
	}

	public boolean isEmpty()
	{
		return size == 0;
	}
	
	public boolean contains(V value)
	{
		if(root != null)
		{
			return root.contains(value);
		}
		return false;
	}
	
	public static void main(String... args)
	{
		LlamaHeap<Double, Double> heap = new LlamaHeap<Double, Double>();
		heap.push(2.3, 3.0);
		heap.push(90.0, 40.0);
		heap.push(2.71, 2.5);
		heap.push(1e6, -23000.3);
		heap.push(3.14, 1.0);
		heap.push(1.3, 2.0);
		heap.push(10000.0, 160.0);
		heap.push(20000.0, -1.0);
		heap.push(2e4, -999.0);
		heap.push(6e-6, 1000000.0);
		
		//heap.decreasePriority(1e6, 1e8);
		System.out.println(heap.pop());
		heap.decreasePriority(1.3, -1000.0);
		System.out.println(heap.pop());
		heap.decreasePriority(6e-6, -1000000.0);
		System.out.println(heap.pop());
		heap.decreasePriority(10000.0, -160.0e45);
		System.out.println(heap.pop());
		heap.decreasePriority(2.71, -0.9);
		System.out.println(heap.pop());
		System.out.println(heap.pop());
    }

}
