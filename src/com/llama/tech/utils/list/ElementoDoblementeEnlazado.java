/*
 * ElementoDoblementeEnlazado.java
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
 * Elemento de una lista doblemente encadenada
 * @param <ClaseGenerica> El tipo de valor que va a almacenar este elemento
 */
class ElementoDoblementeEnlazado<ClaseGenerica extends Comparable <? super ClaseGenerica>> implements Serializable{

	private static final long serialVersionUID = -4319248048405830068L;
	
	private ClaseGenerica valor;
	private ElementoDoblementeEnlazado<ClaseGenerica> siguiente;
	private ElementoDoblementeEnlazado<ClaseGenerica> anterior;
	
	public ElementoDoblementeEnlazado(ClaseGenerica valor) 
	{
		this.valor = valor;
	}
	
	public ClaseGenerica getValor() 
	{
		return valor;
	}

	public void setValor(ClaseGenerica valor) 
	{
		this.valor = valor;
	}
	
	public ElementoDoblementeEnlazado<ClaseGenerica> getSiguiente()
	{
		return siguiente;
	}
	
	public ElementoDoblementeEnlazado<ClaseGenerica> getAnterior()
	{
		return anterior;
	}
	
	public void setSiguiente(ClaseGenerica valor)
	{
		siguiente.setValor(valor);
		siguiente.setAnterior(this);
	}
	
	public void setAnterior(ClaseGenerica valor)
	{
		anterior.setValor(valor);
		anterior.setSiguiente(this);
	}
	
	public void setAnterior(ElementoDoblementeEnlazado<ClaseGenerica> node)
	{
		this.anterior = node;
	}
	
	public void setSiguiente(ElementoDoblementeEnlazado<ClaseGenerica> node)
	{
		this.siguiente = node;
	}
	
	public void reinicializarAnterior()
	{
		anterior = null;
	}
	
	public void reinicializarSiguiente()
	{
		siguiente = null;
	}
	
	@Override
	public String toString()
	{
		if(siguiente != null)
		{
			return valor.toString()+","+siguiente.toString();
		}
		else
		{
			if(valor != null)
			{
				return valor.toString();
			}
			return "";
		}
	}
}
