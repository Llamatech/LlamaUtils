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

import java.io.FileNotFoundException;
import java.io.Serializable;
import java.util.Comparator;
import java.util.Iterator;
import java.util.NoSuchElementException;

import com.llama.tech.misc.XMLFormat;
import com.llama.tech.utils.list.Lista;
import com.llama.tech.utils.list.LlamaIterator;

public class LlamaAVLTree <T extends Comparable<T>> extends XMLFormat implements Tree<T>, Serializable
{
	public static final int PREORDEN =1;
	public static final int POSORDEN =2;
	public static final int INORDEN =3;
	public static final int INVERSO =4;
	private AVLNode<T> root;
	private int size;

	@Override
	public Iterator<T> iterator() 
	{
		return new LlamaTreeIterator<T>(root,INORDEN,this);
	}

	public Iterator<T> iteratorInverso()
	{
		return new LlamaTreeIterator<T>(root, INVERSO,this);
	}

	public Iterator<T> iteratorPreOrden()
	{
		return new LlamaTreeIterator<T>(root, PREORDEN,this);
	}

	public Iterator<T> iteratorPosOrden()
	{
		return new LlamaTreeIterator<T>(root, POSORDEN,this);
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
				return true;
			}
		}
		else
		{
			root = new AVLNode<T>(item);
			size++;
			return true;
		}
		return false;
	}

	@Override
	public boolean add(T item, Comparator<T> comp) 
	{
		if(root != null)
		{
			//			if(!contains(item, comp))
			{
				root.add(item, comp);
				if(!root.isRoot())
				{
					root = root.getParent();
				}
				size++;
				return true;
			}

			//			return false;
		}
		else
		{
			root = new AVLNode<T>(item);
			size++;
			return true;
		}
	}

	@Override
	public T remove(T item) 
	{
		if(size >= 1)
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
	public T remove(T item, Comparator<T> comp) 
	{
		if(size >= 1)
		{
			if(contains(item, comp))
			{
				root = root.remove(item, comp);
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
	public boolean contains(T item, Comparator<T> comp) 
	{
		if(root != null)
		{
			return root.contains(item, comp);
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
			size++;
		}
		for(T item: list)
		{
			if(startFrom == 0)
			{
				startFrom = 1;
			}
			else
			{
				add(item);
			}
		}
	}

	@Override
	public void copyArray(T[] list) 
	{
		int startFrom = 0; 
		if(root == null)
		{
			root = new AVLNode<T>(list[0]);
			startFrom = 1;
			size++;
		}
		for(T item: list)
		{
			if(startFrom == 0)
			{
				startFrom = 1;
			}
			else
			{
				add(item);
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
	public T get(T item, Comparator<T> comp) 
	{
		if(contains(item, comp))
		{
			return root.get(item, comp);
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


	@SuppressWarnings("unchecked")
	@Deprecated
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

	@Override
	public boolean isBalanced() 
	{
		if(root != null)
		{
			return root.balanced();
		}

		return true;
	}

	public void toConsole()
	{
		root.toConsole(root);
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
		private int tipo;
		private LlamaAVLTree<T> tree;

		public LlamaTreeIterator(AVLNode<T> root, int ptipo, LlamaAVLTree<T> tree)
		{
			this.tree=tree;
			tipo=ptipo;
			this.root = root;
			if(root != null)
			{
				AVLNode<T> node=null;
				if(tipo==INORDEN)
					node= root.markVisited();
				else if(tipo==INVERSO)
					node= root.markVisitedInv();
				else if(tipo==PREORDEN)
					node= root.markVisitedPre();
				else if(tipo==POSORDEN)
					node= root.markVisitedPos();
				item = node.getValue();
			}
		}

		@Override
		public boolean hasNext() 
		{
			if(tree.getHeight()==1)
			{
				next = null;
                if(item != null)
                {
                	return true;
                }
                return false;
			}
			try
			{
				AVLNode<T> node=null;
				if(tipo==INORDEN)
					node= root.markVisited();
				else if(tipo==INVERSO)
					node= root.markVisitedInv();
				else if(tipo==PREORDEN)
					node= root.markVisitedPre();
				else if(tipo==POSORDEN)
					node= root.markVisitedPos();
				next = node.getValue();

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



	@Override
	public void writeXML() throws FileNotFoundException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String toXML() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void readXML() {
		// TODO Auto-generated method stub
		
	}


}
