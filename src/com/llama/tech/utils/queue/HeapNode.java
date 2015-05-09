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
