/*
 * ListaDoblementeEnlazada.java
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

public class ListaDoblementeEnlazada<ClaseGenerica> implements Lista<ClaseGenerica>,Serializable{

	private static final long serialVersionUID = -5258391175515712687L;

	private ElementoDoblementeEnlazado<ClaseGenerica> primero;
	private ElementoDoblementeEnlazado<ClaseGenerica> ultimo;
	private int length = 0;

	@Override
	public boolean addAlFinal(ClaseGenerica elemento) 
	{
		if(primero == null)
		{
			primero = new ElementoDoblementeEnlazado<ClaseGenerica>(elemento);
			ultimo = primero;
			//primero.setSiguiente(ultimo);
			//ultimo = primero.getSiguiente();
			//ultimo = new ElementoDoblementeEnlazado<ClaseGenerica>(null);
			//primero.setSiguiente(ultimo);
			//ultimo.setAnterior(primero);
			length++;
		}
		else
		{
			ElementoDoblementeEnlazado<ClaseGenerica> temp = new ElementoDoblementeEnlazado<ClaseGenerica>(elemento);
			ultimo.setSiguiente(temp);
			temp.setAnterior(ultimo);
			ultimo = temp;
			length++;

		}

		return true;

	}

	@Override
	public boolean add(int pos, ClaseGenerica elemento) 
	{
		if(pos == 0 && length == 0)
		{
			primero = new ElementoDoblementeEnlazado<ClaseGenerica>(elemento);
			ultimo = primero;
			length++;
		}
		else if(pos < 0 || pos > length-1)
			throw new IndexOutOfBoundsException(String.format("El �ndice %d, es inv�lido", pos));
		else if (pos == 0)
		{
			ElementoDoblementeEnlazado<ClaseGenerica> nuevo = new ElementoDoblementeEnlazado<ClaseGenerica>(elemento);
			nuevo.setSiguiente(primero);
			primero.setAnterior(nuevo);
			primero = nuevo;
			length++;
			//primero.setValor(elemento);
		}
		else
		{
			ElementoDoblementeEnlazado<ClaseGenerica> actual = primero;
			int posActual = 0;
			boolean agrego = false;
			while(actual != null && !agrego)
			{
				if(posActual == pos)
				{
					ElementoDoblementeEnlazado<ClaseGenerica> anterior = actual.getAnterior();
					ElementoDoblementeEnlazado<ClaseGenerica> nuevo = new ElementoDoblementeEnlazado<ClaseGenerica>(elemento);
					nuevo.setSiguiente(actual);
					nuevo.setAnterior(anterior);
					actual.setAnterior(nuevo);
					anterior.setSiguiente(nuevo);
					length++;
					agrego = true;
				}

				actual = actual.getSiguiente();
				posActual++;
			}

		}
		return true;
	}

	public boolean add(ClaseGenerica elementoI, ClaseGenerica elementoAgregar) 
	{
		boolean agrego = false;
		if (length<=0)
		{
			return agrego;
		}
		else
		{
			ElementoDoblementeEnlazado<ClaseGenerica> actual = primero;

			while(actual != null && !agrego)
			{
				if(actual.getValor().equals(elementoI))
				{
					ElementoDoblementeEnlazado<ClaseGenerica> siguienteI = actual.getSiguiente();
					ElementoDoblementeEnlazado<ClaseGenerica> nuevo = new ElementoDoblementeEnlazado<ClaseGenerica>(elementoAgregar);
					nuevo.setSiguiente(siguienteI);
					nuevo.setAnterior(actual);
					actual.setSiguiente(nuevo);
					siguienteI.setAnterior(nuevo);
					length++;
					agrego= true;
				}

				actual = actual.getSiguiente();
			}
			return agrego;

		}
	}
	@Override
	public void clear() 
	{
		length = 0;
		primero = null;
		ultimo = null;
	}

	@Override
	public boolean contains(ClaseGenerica elemento) 
	{
		if(length > 0)
		{
			ElementoDoblementeEnlazado<ClaseGenerica> actual = primero;
			while(actual != null)
			{
				if(actual.getValor().equals(elemento))
				{
					return true;
				}
				actual = actual.getSiguiente();
			}
		}
		return false;

	}

	@Override
	public ClaseGenerica get(int pos) 
	{
		if(pos < 0 || pos > length-1 || length == 0)
		{
			throw new IndexOutOfBoundsException(String.format("El �ndice %d, es inv�lido", pos));
		}
		else if(pos == 0)
		{
			return primero.getValor();
		}
		else if(pos == length-1)
		{
			return ultimo.getValor();
		}
		else
		{
			int posActual = 0;
			ElementoDoblementeEnlazado<ClaseGenerica> actual = primero;
			while(actual != null)
			{
				if(posActual == pos)
				{
					return actual.getValor();
				}
				actual = actual.getSiguiente();
				posActual++;
			}
		}

		return null; // No deber�a retornar una instancia vac�a
	}

	@Override
	public ClaseGenerica getLast() 
	{
		if( primero!=null)
			return ultimo.getValor();
		else
			return null;
	}

	public ClaseGenerica getFirst()
	{
		if( primero!=null)
			return primero.getValor();
		else
			return null;
	}

	@Override
	public int indexOf(ClaseGenerica elemento) 
	{
		int pos = -1;
		if (length > 0)
		{
			if(ultimo != null)
			{
				if(ultimo.getValor().equals(elemento))
				{
					return length-1;
				}
			}

			ElementoDoblementeEnlazado<ClaseGenerica> actual = primero;
			int posActual = 0;
			while(actual != null)
			{
				if(actual.getValor().equals(elemento))
				{
					return posActual;
				}
				actual = actual.getSiguiente();
				posActual++;
			}
		}
		return pos;
	}

	@Override
	public int lastIndexOf(ClaseGenerica elemento) 
	{
		int pos = -1;
		if (length > 0)
		{
			if(ultimo != null)
			{
				if(ultimo.getValor().equals(elemento))
				{
					return length-1;
				}
			}

			ElementoDoblementeEnlazado<ClaseGenerica> actual = primero;
			int posActual = 0;
			while(actual != null)
			{
				if(actual.getValor().equals(elemento))
				{
					pos = posActual;
				}
				actual = actual.getSiguiente();
				posActual++;
			}
		}
		return pos;
	}

	@Override
	public boolean isEmpty() 
	{
		return length == 0;
	}

	@Override
	public LlamaIterator<ClaseGenerica> iterator() {
		return new IteradorListaDoblementeEnlazada<ClaseGenerica>(primero);
	}

	@Override
	public LlamaIterator<ClaseGenerica> iterator(int pos) 
	{
		ElementoDoblementeEnlazado<ClaseGenerica> elementoPos=null;

		if(pos < 0 || pos > length-1 || length == 0)
		{
			throw new IndexOutOfBoundsException(String.format("El �ndice %d, es inv�lido", pos));
		}

		if(length > 0)
		{
			if(pos == 0)
			{
				elementoPos = primero;
			}
			else if(pos == length-1)
			{
				elementoPos = ultimo;
			}
			else
			{
				ElementoDoblementeEnlazado<ClaseGenerica> actual = primero;
				int posActual = 0;
				boolean encontrado = false;
				while(actual != null && !encontrado)
				{
					if(posActual == pos)
					{
						elementoPos = actual;
						encontrado = true;
					}

					actual = actual.getSiguiente();
					posActual++;
				}
			}
		}
		return new IteradorListaDoblementeEnlazada<ClaseGenerica>(elementoPos);
	}

	@Override
	public LlamaIterator<ClaseGenerica> reverseIterator() 
	{
		return new IteradorReversoListaDoblementeEnlazada<ClaseGenerica>(ultimo);
	}

	@Override
	public ClaseGenerica remove(int pos) 
	{
		ClaseGenerica obj = null;

		if(pos > length-1 || pos < 0 || length == 0)
		{
			throw new IndexOutOfBoundsException(String.format("El �ndice %d, es inv�lido", pos));
		}
		else
		{
			if(pos == 0)
			{
				obj = primero.getValor();
				primero = primero.getSiguiente();
				if(primero!=null)
					primero.reinicializarAnterior();
				length--;
			}
			else if(pos == length-1)
			{
				obj = ultimo.getValor();
				ultimo = ultimo.getAnterior();
				if(ultimo!=null)
					ultimo.reinicializarSiguiente();
				length--;

			}
			else
			{
				ElementoDoblementeEnlazado<ClaseGenerica> actual = primero;
				int posActual = 0;
				boolean eliminado = false;

				while(actual != null && !eliminado)
				{
					if(posActual == pos)
					{
						obj = actual.getValor();
						ElementoDoblementeEnlazado<ClaseGenerica> anterior = actual.getAnterior();
						ElementoDoblementeEnlazado<ClaseGenerica> siguiente = actual.getSiguiente();
						anterior.setSiguiente(siguiente);
						siguiente.setAnterior(anterior);
						length--;
						eliminado = true;				
					}

					actual = actual.getSiguiente();
					posActual++;
				}

			}
		}
		return obj;
	}

	@Override
	public ClaseGenerica remove(ClaseGenerica elemento) 
	{
		boolean eliminado = false;
		if(length > 0)
		{
			if(primero.getValor().equals(elemento))
			{
				primero = primero.getSiguiente();
				primero.reinicializarAnterior();
				length--;
				eliminado = true;
			}
			else if(ultimo.getValor().equals(elemento))
			{
				ultimo = ultimo.getAnterior();
				ultimo.reinicializarSiguiente();
				length--;
				eliminado = true;
			}
			else
			{
				ElementoDoblementeEnlazado<ClaseGenerica> actual = primero;
				while(actual != null && !eliminado)
				{
					if(actual.getValor().equals(elemento))
					{
						ElementoDoblementeEnlazado<ClaseGenerica> anterior = actual.getAnterior();
						ElementoDoblementeEnlazado<ClaseGenerica> siguiente = actual.getSiguiente();
						anterior.setSiguiente(siguiente);
						siguiente.setAnterior(anterior);
						length--;
						eliminado = true;
					}

					actual = actual.getSiguiente();
				}
			}
		}
		if (eliminado)return elemento;
		else return null;
	}

	@Override
	public ClaseGenerica set(int pos, ClaseGenerica elemento) 
	{

		ClaseGenerica obj = null;

		if(pos < 0 || pos > length-1)
		{
			throw new IndexOutOfBoundsException(String.format("El �ndice %d, es inv�lido", pos));
		}
		else if (pos == 0)
		{
			obj = primero.getValor();
			primero.setValor(elemento);
		}
		else if(pos == length-1)
		{
			obj = ultimo.getValor();
			ultimo.setValor(elemento);
		}
		else
		{
			ElementoDoblementeEnlazado<ClaseGenerica> actual = primero;
			int posActual = 0;
			boolean modificado = false;

			while(actual != null && !modificado)
			{
				if(posActual == pos)
				{
					obj = actual.getValor();

					actual.setValor(elemento);

					modificado = true;				
				}

				actual = actual.getSiguiente();
				posActual++;
			}
		}


		return obj;

	}

	@Override
	public int size() {
		return length;
	}

	private void verificarInvariante(){
		assert ( elementosBienEncadenados( ) ) : "La lista no est� bien construida";
	}
	/**
	 * Verifica si los elementos se encuentren bien encadenados en ambos sentidos.
	 * @return True si para todo elemento su siguiente lo relaciona como anterior y viceversa. False de lo contrario.
	 */
	private boolean elementosBienEncadenados( )
	{
		ElementoDoblementeEnlazado<ClaseGenerica> actual = primero;
		ElementoDoblementeEnlazado<ClaseGenerica> siguiente = primero.getSiguiente();

		while(actual.getValor() != null)
		{
			if(actual != siguiente.getAnterior())
			{
				return false;
			}

			actual = siguiente;
			siguiente = siguiente.getSiguiente();
		}

		return true;
	}



	@Override
	public boolean addAlPrincipio(ClaseGenerica elemento) 
	{
		return add(0, elemento);
	}

	@Override
	public ClaseGenerica buscar(ClaseGenerica elemento) {
		if (length > 0)
		{
			if(ultimo.getValor().equals(elemento))
			{
				return ultimo.getValor();
			}

			ElementoDoblementeEnlazado<ClaseGenerica> actual = primero;
			while(actual != null)
			{
				if(actual.getValor().equals(elemento))
				{
					return actual.getValor();
				}
				actual = actual.getSiguiente();
			}
		}
		return null;
	}
	
	public void joinList(ListaDoblementeEnlazada<ClaseGenerica> l)
	{
		ultimo.setSiguiente(l.primero);
		l.primero.setAnterior(ultimo);
	}

	@Override
	public ClaseGenerica removeFirst() throws IndexOutOfBoundsException {
		return remove(0);
	}

	@Override
	public ClaseGenerica removeLast() throws IndexOutOfBoundsException {
		return remove(length-1);
	}

	@Override
	public String toString()
	{
		if(primero != null)
		{
			return "["+primero.toString()+"]";
		}
		return "[]";
	}
}
 