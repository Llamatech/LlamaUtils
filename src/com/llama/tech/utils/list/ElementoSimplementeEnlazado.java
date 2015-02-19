/*
 * ElementoSimplementeEnlazado.java
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

/**
 * Elemento de una lista simplemente encadenada
 * @param <ClaseGenerica> El tipo de valor que va a almacenar este elemento
 */
class ElementoSimplementeEnlazado<ClaseGenerica> implements Serializable{
	
	private static final long serialVersionUID = -1934520635606061885L;
	
	private ClaseGenerica valor;
	private ElementoSimplementeEnlazado<ClaseGenerica> siguiente;
	
	public ElementoSimplementeEnlazado(ClaseGenerica valor) {
		this.valor = valor;
	}
	
	public ClaseGenerica getValor() {
		return valor;
	}
	
	public void setValor(ClaseGenerica valor) {
		this.valor = valor;
	}
	
	public ElementoSimplementeEnlazado<ClaseGenerica> getSiguiente()
	{
		return siguiente;
	}
	
	public void setSiguiente(ClaseGenerica valor)
	{
		siguiente.setValor(valor);
	}
	
	public void setSiguiente(ElementoSimplementeEnlazado<ClaseGenerica> node)
	{
		siguiente = node;
	}
	
	public void reinicializarSiguiente()
	{
		siguiente = null;
	}
}
