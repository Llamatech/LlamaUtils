/*
 * LlamaArrayList.java
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

package com.llama.tech.utils.list;

import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class LlamaArrayList<T> implements Lista<T>, Serializable{

	private T[] lista;
	int posActual;
	int startNumber;

	public LlamaArrayList(int sN)
	{
		posActual=0;
		startNumber=sN;
		lista = (T[]) new Object[startNumber];

	}

	@Override
	public synchronized boolean addAlFinal(T elemento) {
		agrandar();
		lista[posActual]=elemento;
		posActual++;
		return true;
	}

	@Override
	public synchronized boolean addAlPrincipio(T elemento) {
		agrandar();
		return add(0,elemento);
	}

	@Override
	public synchronized boolean add(int pos, T elemento) throws IndexOutOfBoundsException 
	{
		agrandar();
		if(pos>posActual+1)
			throw new IndexOutOfBoundsException();
		
		T lastElem = lista[pos];
		for(int i=pos;i<posActual;i++)
		{
            T nextElem = lista[i+1];			 
			lista[i+1]=lastElem;
			lastElem = nextElem;
		}
		lista[pos]=elemento;
		posActual++;
		return true;
	}

	@Override
	public synchronized boolean add(T elementoI, T elementoAgregar) 
	{
		agrandar();
		int pos =0;
		for(T t:lista)
		{
			if(t.equals(elementoI))
			{
				add(pos,elementoAgregar);
				return true;
			}
			pos++;
		}
		return false;

	}

	@Override
	public synchronized void clear() {
		for(int i =0;i<posActual;i++)
		{
			lista[i]=null;
		}
		posActual=0;

	}

	@Override
	public synchronized boolean contains(T elemento) {

		for (T t:lista)
		{
			if(t.equals(elemento))
			{
				return true;
			}
		}
		return false;
	}

	@Override
	public synchronized T buscar(T elemento) {
		for (T t:lista)
		{
			if(t.equals(elemento))
			{
				return elemento;
			}
		}
		return null;
	}

	@Override
	public synchronized T get(int pos) throws IndexOutOfBoundsException {
		if(pos>posActual||pos<0)
			throw new IndexOutOfBoundsException();
		else
			return lista[pos];
	}

	@Override
	public synchronized T getLast() {
		if(posActual!=0)
			return lista[lista.length-1];
		return null;
	}

	@Override
	public synchronized T getFirst() {
		if(posActual!=0)
			return lista[0];
		return null;
	}

	@Override
	public synchronized int indexOf(T elemento) 
	{
		int pos =0;
		for(T t:lista)
		{
			if(t.equals(elemento))
			{
				return pos;
			}
			pos++;
		}
		return -1;
	}

	@Override
	public synchronized int lastIndexOf(T elemento) {

		for (int i = posActual-1; i >=0; i--) {
			if(elemento.equals(lista[i]))
			{
				return i;
			}

		}
		return -1;
	}

	@Override
	public synchronized boolean isEmpty() {
		return (posActual==0);
	}

	@Override
	public synchronized LlamaIterator<T> iterator() 
	{
		return new MyIterator<T>(0);
	}

	@Override
	public synchronized LlamaIterator<T> iterator(int pos) throws IndexOutOfBoundsException {
		return new MyIterator<T>(pos);
	}

	/**
	 * Esta clase modela el iterador del ArrayList
	 * @param <T> el parámetro que representa la clase generica
	 */
	public class MyIterator<T> implements LlamaIterator<T>
	{
		int x;
		public MyIterator(int desde) {
			x=desde-1;
		}

		@Override
		public boolean hasNext() {
			if((x+1)<posActual)
				return true;
			return false;
		}

		@Override
		public T next() {
			T ret=(T) lista[x+1];
			x++;
			return ret;
		}

		public boolean hasPrevious(){
			if((x-1)>=0)
				return true;
			return false;

		}

		public T previous(){
			T ret=(T) lista[x-1];
			x--;
			return ret;
		}
	}

	@Override
	public synchronized T remove(int pos) throws IndexOutOfBoundsException {
		if(pos>=posActual||pos<0)
			throw new IndexOutOfBoundsException();
		else
		{
			T ret = lista[pos];
			for(int i =pos;i<posActual-1;i++)
			{
				lista[i]=lista[i+1];
			}
			return ret;
		}

	}

	@Override
	public synchronized T removeFirst() throws IndexOutOfBoundsException {
		return remove(0);
	}

	@Override
	public synchronized T removeLast() throws IndexOutOfBoundsException {
		return remove(posActual-1);
	}

	@Override
	public synchronized T remove(T elemento) {
		int pos = 0;
		for(T t: lista)
		{
			if(t==null)
			{
				for(int i=pos;i<posActual-1;i++)
				{
					lista[i]=lista[i+1];
				}
			}
			else
			{
				if(t.equals(elemento))
				{
					for(int i=pos;i<posActual-1;i++)
					{
						lista[i]=lista[i+1];
					}
					return elemento;
				}
				pos++;
			}
			
		}
		return null;

	}

	@Override
	public synchronized T set(int pos, T elemento) throws IndexOutOfBoundsException {
		if(pos>=posActual||pos<0)
			throw new IndexOutOfBoundsException();
		else{
			lista[pos]=elemento;
			return elemento;
		}
	}

	@Override
	public synchronized int size() {
		return posActual;
	}

	/**
	 * Este método agranda el arreglo cuando sea necesario
	 */
	private synchronized void agrandar()
	{
		if(posActual==lista.length)
		{
			T[] temp = (T[]) new Object[lista.length+startNumber];
			System.arraycopy(lista, 0, temp, 0, lista.length);
			lista = temp;
		}
	}

	@Override
	public Iterator<T> reverseIterator() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public synchronized void addAll(Lista<T> l) 
	{
		for(T elem: l)
		{
			addAlFinal(elem);
		}
		
	}
	
	@Override
	public String toString()
	{
		String info = "[";
		for(T item: lista)
		{
			if(item != null)
			{
				info += item.toString()+", ";
			}
		}
		
		info += "]";
		
		return info;
		
	}
	
	public LlamaArrayList<T> clone()
	{
		LlamaArrayList<T> copy = new LlamaArrayList<T>(posActual);
		copy.addAll(this);
		return copy;
	}

	



}
