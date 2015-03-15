/*
 * Tree.java
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

import com.llama.tech.utils.list.Lista;

public interface Tree<T extends Comparable<T>> extends Iterable<T>
{
	/**
	 * Agrega un elemento al árbol. Si el elemento se encuentra en el árbol, no será agregado.
	 * @param item El elemento a ser agregado. item != null.
	 * @return true si el elemento fue agregado, false, de lo contrario.
	 */
	public boolean add(T item);
	
	/**
	 * Retorna un elemento que se encuentra en el árbol.
	 * @param item El objeto a buscar. item != null.
	 * @return El elemento a buscar. null, si el elemento no fue encontrado.
	 */
	public T get(T item);
    
	/**
	 * Elimina un elemento del árbol.
	 * @param item El elemento a ser eliminado. item != null.
	 * @return El elemento eliminado. null, si no se encuentra en el árbol.
	 */
	public T remove(T item);
	
	/**
	 * Retorna el número de elementos agregados al árbol.
	 * @return El número de elementos actuales.
	 */
	public int size();
	
	/**
	 * Retorna la altura actual del árbol.
	 * @return la altura actual del árbol.
	 */
	public int getHeight();
	
	/**
	 * Establece si un elemento se encuentra en el árbol.
	 * @param item El elemento a ser localizado. item != null.
	 * @return true, si el elemento se encuentra en el árbol, false, de lo contrario.
	 */
	public boolean contains(T item);
	
	/**
	 * Agrega los elementos de una lista al árbol.
	 * @param list La lista a copiar. list != null.
	 */
	public void copyList(Lista<T> list);
	
	/**
	 * Agrega los elementos de un arreglo al árbol.
	 * @param list El arreglo a copiar. list != null.
	 */
	public void arrayCopy(T[] list);
	
	/**
	 * Establece si el árbol se encuentra vacío.
	 * @return true, si el árbol no tiene elementos. false, de lo contrario.
	 */
	public boolean isEmpty();

	/**
	 * Retorna los elementos actuales del árbol en un arreglo ordenado. 
	 * @return
	 */
	public T[] toArray();

	/**
	 * Determina si los elementos presentes en una lista, se encuentran en el árbol.
	 * @param c La lista a ser verificada.
	 * @return true, si todos los elementos de la lista fueron hallados. false, de lo contrario.
	 */
	public boolean containsAll(Lista<T> c);

	/**
	 * Elimina todos los elementos del árbol.
	 */
	public void clear();

	/**
	 * Elimina del árbol, los elementos presentes en un arreglo.
	 * @param c El arreglo que contiene los elementos a ser eliminados del árbol.
	 * @return true si todos los elementos del arreglo fueron eliminados del árbol. false, de lo contrario.
	 */
	public boolean removeAll(T[] c);

	/**
	 * Elimina del árbol, los elementos presentes en una lista.
	 * @param c La lista que contiene los elementos a ser eliminados del árbol.
	 * @return true si todos los elementos de la lista fueron eliminados del árbol. false, de lo contrario.
	 */
	public boolean removeAll(Lista<T> c);
	
	
	
}
