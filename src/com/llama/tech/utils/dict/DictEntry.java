package com.llama.tech.utils.dict;

import com.llama.tech.utils.list.Lista;

public class DictEntry<K, V>
{
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

}

