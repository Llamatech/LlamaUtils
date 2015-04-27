/*
 * Lista.java
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

import java.io.IOException;
import java.io.Serializable;
import java.util.Iterator;



public interface Lista<ClaseGenerica> extends Serializable, Iterable<ClaseGenerica>
{
	/**
	 * Agrega un elemento al final de la lista
	 * @param elemento El elemento a agregar
	 * @return true si se agregó false de lo contrario
	 */
	public boolean addAlFinal(ClaseGenerica elemento);
	/**
	 * Agrega un elemento al principio de la lista
	 * @param elemento El elemento a agregar
	 * @return true si se agregó false de lo contrario
	 */
	public boolean addAlPrincipio(ClaseGenerica elemento);
	/**
	 * Agrega un elemento en una posici�n dada de la lista.
	 * 
	 * @param pos La posici�n en la que se agregar� el elemento
	 * @param elemento El elemento a agregar.
	 * @return true si se agregó false de lo contrario
	 * @throws IndexOutOfBoundsException Si la posici�n no existe
	 */
	public boolean add(int pos,ClaseGenerica elemento)throws IndexOutOfBoundsException;
	/**
	 * Agrega un elemento después de un elemento dado
	 * @param elementoI elemento tras del cual agregar el nuevo elemento
	 * @param elementoAgrgar elementoa agregar
	 * @return true si se agregó false de lo contrario
	 */
	public boolean add(ClaseGenerica elementoI, ClaseGenerica elementoAgregar);
	/**
	 * Quita todos los elementos de la lista
	 */
	public void clear();
	/**
	 * Determina si el objeto dado est� contenido en el arreglo
	 * @param elemento El elemento a buscar
	 * @return Un boolean indicando si el elemento est� en la lista o no
	 */
	
	public boolean contains(ClaseGenerica elemento);
	/**
	 * Devuelve el objeto buscado si el objeto dado est� contenido en el arreglo
	 * @param elemento El elemento a buscar
	 * @return El objeto buscado o null si no se encuentra
	 */
	public ClaseGenerica buscar(ClaseGenerica elemento);
	/**
	 * Busca el elemento almacenado en una posici�n de la lista 
	 * @param pos La posici�n del elemento a buscar
	 * @return El elemento en la posici�n buscada.
	 * @throws IndexOutOfBoundsException Si la posici�n no existe
	 */
	public ClaseGenerica get(int pos)throws IndexOutOfBoundsException;
	/**
	 * Retorna el �ltimo elemento de la lista.
	 * @return El �ltimo elemento de la lista.
	 */
	public ClaseGenerica getLast();
	/**
	 * Retorna el primer elemento de la lista
	 * @return El primer elemento de la lista
	 */
	public ClaseGenerica getFirst();
	/**
	 * Retorna la primera posici�n en la lista donde aparece un elemento dado.
	 * @param elemento El elemento a buscar su posici�n.
	 * @return La primera posici�n del elemento dado. Si el elemento no se encuentra en la lista retorna -1
	 */
	public int indexOf(ClaseGenerica elemento);
	/**
	 * Retorna la �ltima posici�n en la lista donde aparece un elemento dado
	 * @param elemento El elemento a buscar su posici�n
	 * @return La �ltima posici�n del elemento dado. Si el elemento no se encuentra en la lista retorna -1
	 */
	public int lastIndexOf(ClaseGenerica element);
	/**
	 * Determina si la lista est� vac�a o no
	 * @return Un boolean indicando si la lista est� vac�a
	 */ 
	public boolean isEmpty();
	/**
	 * Metodo que genera un iterador para recorrer la lista
	 * @return Un iterador sobre la lista
	 */
	public Iterator<ClaseGenerica> iterator();
	
	public Iterator<ClaseGenerica> reverseIterator();
	
	/**
	 * Metodo que genera un iterador para recorrer la lista. El iterador empieza en la posici�n pos
	 * @param pos La posici�n en la que empieza el iterador
	 * @return Un iterador sobre la lista.
	 * @throws IndexOutOfBoundsException Si la posici�n no existe
	 */
	public Iterator<ClaseGenerica> iterator(int pos)throws IndexOutOfBoundsException;

	/**
	 * M�todo que elimina un elemento de la lista.
	 * @param pos La posici�n del elemento a eliminar
	 * @return El objeto eliminado.
	 * @throws IndexOutOfBoundsException Si la posici�n no existe
	 */
	public ClaseGenerica remove(int pos) throws IndexOutOfBoundsException;
	/**
	 * M�todo que elimina el primer elemento de la lista.
	 * @return El objeto eliminado.
	 * @throws IndexOutOfBoundsException Si la posici�n no existe
	 */
	public ClaseGenerica removeFirst() throws IndexOutOfBoundsException;
	/**
	 * M�todo que elimina el ultimo elemento de la lista.
	 * @return El objeto eliminado.
	 * @throws IndexOutOfBoundsException Si la posici�n no existe
	 */
	public ClaseGenerica removeLast() throws IndexOutOfBoundsException;
	/**
	 * M�todo que elimina un objeto de la lista
	 * @param elemento El objeto a eliminar
	 * @return El objeto eliminado.
	 */
//	@Override
	public ClaseGenerica remove(ClaseGenerica elemento);
	/**
	 * M�todo que reemplaza el elemento de una posici�n por otro.
	 * @param pos La posici�n del elemento a reemplazar.
	 * @param elemento El elemento que va a reemplazar al anterior.
	 * @return El objeto que estaba en la posici�n pos antes.
	 * @throws IndexOutOfBoundsException Si la posici�n no existe
	 */
	public ClaseGenerica set(int pos,ClaseGenerica elemento) throws IndexOutOfBoundsException;
	/**
	 * M�todo que determina el tama�o de la lista
	 * @return El tama�o de la lista (el n�mero de elementos que contiene)
	 */
	public int size();
	
	public void addAll(Lista<ClaseGenerica> l);
	
    public String toXML();
	
	public void writeXML(String path) throws IOException;
}
