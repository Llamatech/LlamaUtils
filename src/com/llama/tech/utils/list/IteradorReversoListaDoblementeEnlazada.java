/*
 * IteradorReversoListaDoblementeEnlazada.java
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
import java.util.NoSuchElementException;

/**
 * Esta clase permite recorrer una lista doblemente enlazada de manera reversa (hacia atr�s)
 * @param <ClaseGenerica> el tipo de elemento que permite obtener el iterador
 */
public class IteradorReversoListaDoblementeEnlazada<ClaseGenerica extends Comparable <? super ClaseGenerica>> implements LlamaIterator<ClaseGenerica>, Serializable{
	
	private ElementoDoblementeEnlazado<ClaseGenerica> elementoActual;
	
	/**
	 * Construye un iterador de una lista doblemente enlazada
	 * @param ultimo El elemento desde el que se va a empezar el recorrido hacia atr�s
	 */
	public IteradorReversoListaDoblementeEnlazada(ElementoDoblementeEnlazado<ClaseGenerica> ultimo) 
	{
		this.elementoActual = ultimo;
	}

	@Override
	public boolean hasNext() {
		return elementoActual != null && elementoActual.getValor() != null;
	}

	@Override
	public ClaseGenerica next() throws NoSuchElementException
	{
		if(!hasNext())
		{
			throw new NoSuchElementException("No hay m�s elementos en el Iterador");
		}
		ClaseGenerica obj = elementoActual.getValor();
		elementoActual = elementoActual.getAnterior();
		return obj;
	}

	@Override
	public boolean hasPrevious() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public ClaseGenerica previous() {
		// TODO Auto-generated method stub
		return null;
	}
	
}

