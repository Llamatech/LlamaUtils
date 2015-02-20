/*
 * LlamaDict.java
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

import com.llama.tech.utils.list.Lista;
import com.llama.tech.utils.list.ListaDoblementeEnlazada;
import com.llama.tech.utils.list.LlamaArrayList;
import com.llama.tech.utils.list.LlamaIterator;

public class LlamaDict<K, V> implements Dictionary<K, V>
{
	 private int size = 0;
	 private int mainAreaSize = 0;
	 private Lista<DictEntry<K, V>> mainArea;
	 
	 
	 public LlamaDict(int len)
	 {
		 mainArea = new LlamaArrayList<DictEntry<K,V>>(len);
		 for(int i = 0; i < len; i++)
		 {
			 mainArea.addAlFinal(new DictEntry<K,V>(null, null));
		 }
		 mainAreaSize = len;
	 }
	 
	 @Override
	 public void addEntry(K key, V value) throws UnhashableTypeException
	 {
		 if(key == null && value !=  null)
			 throw new UnhashableTypeException(String.format("The pair: <%s : %s>, is invalid", key, value.toString()));
		 else if(key != null && value == null)
			 throw new UnhashableTypeException(String.format("The pair: <%s : %s>, is invalid", key.toString(), value));
		 else if(key == null && value == null)
			 throw new UnhashableTypeException(String.format("The pair: <%s : %s>, is invalid", key, value));
		 
		 int pos = key.hashCode() % mainAreaSize;
		 DictEntry<K, V> entry = mainArea.get(pos);
		 if(entry == null)
		 {
			 entry = new DictEntry<K, V>(key, value);
		 }
		 else
		 {
			 entry.addEntry(key, value);
		 }
		 size++;
	 }
	  
	 @Override
	 public V setEntry(K key, V value) throws UnhashableTypeException
	 {
		 if(key == null && value !=  null)
			 throw new UnhashableTypeException(String.format("The pair: <%s : %s>, is invalid", key, value.toString()));
		 else if(key != null && value == null)
			 throw new UnhashableTypeException(String.format("The pair: <%s : %s>, is invalid", key.toString(), value));
		 else if(key == null && value == null)
			 throw new UnhashableTypeException(String.format("The pair: <%s : %s>, is invalid", key, value));
		 
		 int pos = key.hashCode() % mainAreaSize;
		 DictEntry<K, V> entry = mainArea.get(pos);
		 
		 return entry.setValue(key, value);
	 }
	 
	 @Override
	 public V removeEntry(K key)
	 {
		 V rem = null;
		 int pos = key.hashCode() % mainAreaSize;
		 DictEntry<K,V> primero = mainArea.get(pos);

		 if(primero != null && primero.getMyValue() != null)
		 {
			 System.out.println("Here!");
			 if(primero.getMyKey().equals(key))
			 {
				 rem=primero.getMyValue();
				 mainArea.set(pos, primero.getNext());
			 }
			 else
			 {
				 System.out.println("Then, there");
				 DictEntry<K, V> actual = primero.getNext();
				 DictEntry<K, V> anterior = primero;
				 while(rem == null && actual!=null)
				 {
					 //System.out.println(actual.getMyKey()+":"+key);
					 if(actual.getMyKey().equals(key))
					 {
						 rem=actual.getMyValue();
						 anterior.setNext(actual.getNext());
					 }
					 actual=actual.getNext();
					 anterior = anterior.getNext();
					 
				 }
			 }
		 }
		 
		 if(rem != null)
		 {
			 System.out.println("Yep");
			 size--;
		 }
		 
		 return rem;
		 
	 }
	 
	 @Override
	 public LlamaIterator<K> getKeys()
	 {
		 ListaDoblementeEnlazada<K> l = new ListaDoblementeEnlazada<K>();
		 for(int i = 0; i < mainArea.size(); i++)
		 {
			 DictEntry<K, V> entry = mainArea.get(i);
			 if(entry != null && entry.getMyKey() != null)
			 {
				 entry.addKeys(l);
			 }
		 }
		 
		 return l.iterator();
	 }
	 
	 @Override
	 public LlamaIterator<V> getValues()
	 {
		 ListaDoblementeEnlazada<V> l = new ListaDoblementeEnlazada<V>();
		 for(int i = 0; i < mainArea.size(); i++)
		 {
			 DictEntry<K, V> entry = mainArea.get(i);
			 if(entry != null && entry.getMyKey() != null)
			 {
				 entry.addValues(l);
			 }
		 }
		 
		 return l.iterator();
	 }
	 
	 @Override
	 public String toString()
	 {
		 StringBuilder sb = new StringBuilder("{");
		 for(int i = 0; i < mainArea.size(); i++)
		 {
			 DictEntry<K,V> entry = mainArea.get(i);
			 entry.repr(sb);
			 if(i < mainArea.size()-1 && entry.getMyKey() != null)
			 {
				 sb.append(',');
			 }
		 }
		 sb.append('}');
		 
		 return sb.toString();
	 }
	 
	 @Override
	 public V getValue(K key)
	 {
		 int pos = key.hashCode() % mainAreaSize;
		 if(pos < 0)
		 {
			 return null;
		 }
		 DictEntry<K, V> primero= mainArea.get(pos);
		 
		 return primero.getValue(key);//:D oops Jajajajaja, intervine de forma muy indecente, perdón!
	 }
	//FUe bastante pertienente la intervencion!
	 
	 @Override
	 public int size()
	 {
		 return size;
	 }
	
	 public static class UnhashableTypeException extends Exception
	 {
		 public UnhashableTypeException(String msj)
		 {
		 	 super(msj);
		 }
	 }
	 
	
}
