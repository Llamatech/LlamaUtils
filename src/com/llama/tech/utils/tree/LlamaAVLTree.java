/*
 * LlamaAVLTree.java
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

package com.llama.tech.utils.tree;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Random;

import com.llama.tech.utils.list.Lista;
import com.llama.tech.utils.list.LlamaIterator;

public class LlamaAVLTree <T extends Comparable<T>> implements Tree<T>
{
	private AVLNode<T> root;
	private int size;
	
	@Override
	public Iterator<T> iterator() 
	{
		return new LlamaTreeIterator<T>(root);
	}
	
	@Override
	public int getHeight()
	{
		if(root == null)
		{
			return 0;
		}
		return root.getBalanceFactor();
	}
	
	@Override
	public String toString()
	{
		String s = root.toString(); 
		return "["+s.substring(1, s.length()-1)+"]";
	}

	@Override
	public boolean add(T item) 
	{
		if(root != null)
		{
			if(!root.contains(item))
			{
				root.add(item);
				if(!root.isRoot())
				{
					root = root.getParent();
				}
				size++;
			}
		}
		else
		{
			root = new AVLNode<T>(item);
			size++;
		}
		return true;
	}
	
	@Override
	public T remove(T item) 
	{
		if(size > 1)
		{
			if(root.contains(item))
			{
				root = root.remove(item);
				size--;
				return item;
			}
		}
		
		return null;
		
	}

	@Override
	public boolean contains(T item) 
	{
		if(root != null)
		{
			return root.contains(item);
		}
		return false;
	}

	@Override
	public void copyList(Lista<T> list) 
	{
		int startFrom = 0; 
		if(root == null)
		{
			root = new AVLNode<T>(list.get(0));
			startFrom = 1;
		}
		for(T item: list)
		{
			if(startFrom == 0)
			{
				startFrom = 1;
			}
			else
			{
				root.add(item);
			}
		}
	}

	@Override
	public T get(T item) 
	{
		if(root != null)
		{
			return root.get(item);
		}
		return null;
	}

	@Override
	public boolean isEmpty() 
	{
		return root == null;
	}

	@Override
	public void arrayCopy(T[] list) 
	{
		Iterator<T> it = iterator();
		int i = 0;
		while(it.hasNext())
		{
			T item = it.next(); 
			list[i] = item;
			i++;
		}
	}
	
	@Override
	public int size() 
	{
		return size;
	}


	@Override
	public T[] toArray() 
	{
		T[] arr = (T[]) new Object[size];
		arrayCopy(arr); 
		return arr;
	}

	@Override
	public boolean containsAll(Lista<T> c) 
	{
		boolean contains = true;
		for(T item : c)
		{
			contains = contains && contains(item);
			if(!contains)
			{
				break;
			}
		}
		
		return contains;
	}

	@Override
	public boolean removeAll(T[] c) 
	{
		boolean deletion = true;
		for(T item : c)
		{
			T r = remove(item);
			if(r == null)
			{
				deletion = false;
			}
		}
		return deletion;
	}
	
	@Override
	public boolean removeAll(Lista<T> c) 
	{
		boolean deletion = true;
		for(T item : c)
		{
			T r = remove(item);
			if(r == null)
			{
				deletion = false;
			}
		}
		return deletion;
	}


	@Override
	public void clear() 
	{
		root = null;
		size = 0;
	}
	
	private boolean balanced()
	{
		root.toConsole(root);
		return root.balanced();
	}
	
	public static void main(String... args)
	{
		Random r = new Random();
		LlamaAVLTree <Integer> tree = new LlamaAVLTree <Integer>();
		for(int i = 0; i < 10; i++)
		{
			tree.add(i);
		}
		
		System.out.println(tree.balanced());
		//Iterator<Integer> it = tree.iterator();
		Integer[] l = new Integer[tree.size];
		tree.arrayCopy(l);
		for(int i: l)
		{
			System.out.println(i);
		}
		
		

	}
	
	private static class LlamaTreeIterator<T extends Comparable<T>> implements LlamaIterator<T>
	{
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		
		private T item;
		private T next;
		private AVLNode<T> root;

		public LlamaTreeIterator(AVLNode<T> root)
		{
			this.root = root;
			item = root.markVisited().getValue();
		}
		
		@Override
		public boolean hasNext() 
		{
			try
			{
				next = root.markVisited().getValue();
			}
			catch(Exception e)
			{
				next = null;
			}
			return item != null;
		}

		@Override
		public boolean hasPrevious() 
		{
			return false;
		}

		@Override
		public T next() throws NoSuchElementException 
		{
			T temp = item;
			if(item == null)
			{
				throw new NoSuchElementException("There aren't any elements left on the iterator.");
			}
			item = next;
			return temp;
		}

		@Override
		public T previous() throws NoSuchElementException
		{
			return null;
		}
		
	}
	
	
	
	


	

}
