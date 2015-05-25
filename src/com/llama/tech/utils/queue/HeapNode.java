/*
 * HeapNode.java
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

import com.llama.tech.utils.list.LlamaArrayList;

public class HeapNode<V, P extends Comparable<P>> implements Comparable<HeapNode<V, P>>
{
	private V data;
	private P priority;


	public HeapNode<V, P> child;
	private HeapNode<V, P> right;
	
	
	public HeapNode(V data, P priority)
	{
		this.data = data;
		this.priority = priority;
	}
	
	public void setPriority(P priority)
	{
		this.priority = priority;
	}
	
	public V getValue()
	{
		return data;
	}
	
	public HeapNode<V, P> removeMin() 
	{
		//Yo soy la raíz
		HeapNode<V, P> n_heap = null;
		
		if(child != null)
		{
			HeapNode<V, P> act_node = child;
			//child = null;

			LlamaArrayList<HeapNode<V, P> > list = new LlamaArrayList<HeapNode<V, P>>(10);
			
			n_heap = new HeapNode<V, P>(act_node.data, act_node.priority);
			n_heap.child = act_node.child;
			
			if(act_node.right == null)
			{
				return n_heap;
			}
			
			while(act_node.right != null)
			{	
				act_node = act_node.right;
				HeapNode<V, P> temp = new HeapNode<V, P>(act_node.data, act_node.priority);
				temp.child = act_node.child;
				n_heap = n_heap.mergeHeap(temp);
				list.addAlFinal(n_heap);
				if(act_node.right != null)
				{
					act_node = act_node.right;
					n_heap = new HeapNode<V, P>(act_node.data, act_node.priority);
					n_heap.child = act_node.child;
					if(act_node.right == null)
					{
						list.addAlFinal(n_heap);
					}
				}
			}
			
			n_heap = list.getLast();
			for(int i = list.size()-2; i >= 0; i--)
			{
				n_heap = n_heap.mergeHeap(list.get(i));
			}
			
		}
		
		return n_heap;
	}
	
	
	public HeapNode<V, P> mergeHeap(HeapNode<V, P> subHeap)
	{
		if(this.compareTo(subHeap) <= 0)
		{
			if(child != null)
			{
				subHeap.right = child;
				child = subHeap;
			}
			else
			{
				child = subHeap;
			}
			
			return this;
		}
		else
		{
			if(subHeap.child != null)
			{
				this.right = subHeap.child;
				subHeap.child = this;
			}
			else
			{
				subHeap.child = this;
			}
			
			return subHeap;
		}
	}
	
	public HeapNode<V, P> decreasePriority(V value, P priority) 
	{
		HeapNode<V, P> node = this;
		//Yo soy la raíz
		if(this.data.equals(value))
		{
			if(this.priority.compareTo(priority) > 0)
			{
				this.priority = priority; 
				return this;
			}
			else
			{
				HeapNode<V, P> temp = new HeapNode<V, P>(value, priority);
				if(child != null)
				{
					HeapNode<V, P> root = this.removeMin(); 
					root = root.mergeHeap(temp);
					return root;
				}
				return temp;
			}
		}
			
	    if(child != null)
		{
	    	LlamaArrayList<HeapNode<V, P>> prevL = new LlamaArrayList<HeapNode<V, P>>(1); 
			HeapNode<V, P> act = getNode(value, prevL);
			HeapNode<V, P> prev = prevL.getFirst();
			
			if(act != null)
			{
				if(this.priority.compareTo(priority) > 0)
				{
					//Respecto a la raíz
					HeapNode<V, P> temp = new HeapNode<V, P>(value, priority);
					if(prev != null)
					{
						prev.right = act.right;
						act.right = null;
					}
					act = act.removeMin();
					if(act != null)
					{
						act = act.mergeHeap(temp);
						temp = this.mergeHeap(act);
					}
					else
					{
						temp = this.mergeHeap(temp);
					}
					return temp;
				}
				else
				{
					//Respecto al subheap
					HeapNode<V, P> yeOldeRight = act.right;
					act = act.decreasePriority(value, priority);
					act.right = yeOldeRight;
					prev.right = act;
				}
			}
		}
		
		return node;
	}
	
    public boolean contains(V value)
    {
    	boolean found = false;
    	
    	if(this.data.equals(value))
    	{
    		found = true;
    	}
    	
    	if(this.child != null)
    	{
    		found = found || child.contains(value);
    	}
    	
    	if(this.right != null)
    	{
    		found = found || right.contains(value);
    	}
    	
    	return found;    	
    }
    
    private HeapNode<V, P> getNode(V value, LlamaArrayList<HeapNode<V, P>> stack)
    {
    	HeapNode<V, P> req = null;
    	
    	if(this.data.equals(value))
    	{
    		return this;
    	}
    	
    	if(child != null)
    	{
    		req = child.getNode(value, stack);
    	}
    	
    	if(req == null)
    	{
    		if(right != null)
    		{
    			req = right.getNode(value, stack);
    		}
    	}
    	else
    	{
    		stack.addAlFinal(this);
    	}
    	return req;
    }
    
    

	@Override
	public int compareTo(HeapNode<V, P> o) 
	{
		return priority.compareTo(o.priority);
	}	
	
	@Override 
	public String toString()
	{
		return "Value: "+data.toString()+"; Priority: "+priority.toString();
	}
	    
}
