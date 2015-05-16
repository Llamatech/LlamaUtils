/*
 * DictEntry.java
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

package com.llama.tech.utils.dict;

import java.io.Serializable;

import com.llama.tech.utils.list.Lista;

public class DictEntry<K extends Comparable<K>, V> implements Serializable, Comparable<DictEntry<K, V>>
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1359144748660811661L;
	private DictEntry<K,V> next;
	private K key;
	private V value;

	public DictEntry(K key, V value)
	{
		this.key = key;
		this.value = value;
	}

	public V getValue(K key)
	{
		if(this.key != null && key != null)
		{
			if(this.key.equals(key))
			{
				return value;
			}
			else
			{
				if(next != null)
				{
					return next.getValue(key);
				}
			}
		}
		//Aquí no llega, miento, si llega! JAjaja pero es posible! Qué pasa si se busca un ele,emto inexistente? Jajaja, por eso me retracté!
		return null;
	}

	public void addEntry(K key, V value)
	{
		if(this.key == null)
		{
			this.key = key;
			this.value = value;
		}
		else
		{
			if(this.key.equals(key))
			{
				this.value = value;
			}
			else if(next == null)
			{
				next = new DictEntry<K, V>(key, value);
			}
			else
			{
				next.addEntry(key, value);
			}
		}
	}

	public V setValue(K key, V value)
	{
		V v = null;
		if(key != null)
		{
			if(this.key != null && this.key.equals(key))
			{
				v = this.value;
				this.value = value;
			}
		}
		else
		{
			if(next != null)
			{
				return next.setValue(key, value);
			}
		}

		return v;
	}

	public void addKeys(Lista<K> l)
	{
		if(key != null)
		{
			l.addAlFinal(key);
		}
		if(next != null)
		{
			next.addKeys(l);
		}
	}

	public void addValues(Lista<V> l)
	{
		l.addAlFinal(value);
		if(next != null)
		{
			next.addValues(l);
		}
	}

	public DictEntry<K, V> getNext()
	{
		return next;
	}

	public void setNext(DictEntry<K,V> nexxt)
	{
		next = nexxt;
	}
	
	public K getMyKey()
	{
		return key;
	}
	
	public V getMyValue()
	{
		return value;
	}
	
	public void repr(StringBuilder sb)
	{
		if(key != null)
		{
			sb.append(key.toString());
			sb.append(":");
			sb.append(value.toString());
		}
		if(next != null)
		{
			sb.append(',');
			next.repr(sb);
		}
	}

	@Override
	public int compareTo(DictEntry<K, V> o) {
		// TODO Auto-generated method stub
		return 0;
	}

}

