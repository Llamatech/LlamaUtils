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
			 throw new UnhashableTypeException(String.format("The pair: <%s : %s>", key, value.toString()));
		 else if(key != null && value == null)
			 throw new UnhashableTypeException(String.format("The pair: <%s : %s>", key.toString(), value));
		 
		 int pos = key.hashCode() % mainAreaSize;
		 DictEntry<K, V> entry = mainArea.get(pos);
		 entry.addEntry(key, value);
		 size++;
	 }
	  
	 @Override
	 public V setEntry(K key, V value) throws UnhashableTypeException
	 {
		 if(key == null && value !=  null)
			 throw new UnhashableTypeException(String.format("The pair: <%s : %s>", key, value.toString()));
		 else if(key != null && value == null)
			 throw new UnhashableTypeException(String.format("The pair: <%s : %s>", key.toString(), value));
		 
		 int pos = key.hashCode() % mainAreaSize;
		 DictEntry<K, V> entry = mainArea.get(pos);
		 
		 return entry.setValue(key, value);
	 }
	 
	 @Override
	 public V removeEntry(K key)
	 {
		 V rem = null;
		 int pos = key.hashCode()%mainAreaSize;
		 DictEntry<K,V> primero = mainArea.get(pos);

		 
		 if(primero.getMyKey().equals(key))
		 {
			 rem=primero.getMyValue();
			 mainArea.set(pos, primero.getNext());
		 }
		 else
		 {
			 DictEntry<K, V> actual = primero.getNext();
			 DictEntry<K, V> anterior = primero;
			 while(rem!=null&&actual!=null)
			 {
				 if(actual.getMyKey().equals(key))
				 {
					 rem=actual.getMyValue();
					 anterior.setNext(actual.getNext());
				 }
				 actual=actual.getNext();
				 anterior = anterior.getNext();
				 
			 }
		 }
		 
		 if(rem != null)
		 {
			 size--;
		 }
		 
		 return rem;
		 
	 }
	 
	 @Override
	 public LlamaIterator<K> getKeys()
	 {
		 boolean init = false;
		 ListaDoblementeEnlazada<K> l = null;
		 for(int i = 0; i < mainArea.size(); i++)
		 {
			 DictEntry<K, V> entry = mainArea.get(i);
			 if(entry != null)
			 {
				 if(!init)
				 {
					 l = entry.getKeys();
				 }
				 else
				 {
					 l.joinList(entry.getKeys());
				 }
			 }
		 }
		 
		 return l.iterator();
	 }
	 
	 @Override
	 public LlamaIterator<V> getValues()
	 {
		 boolean init = false;
		 ListaDoblementeEnlazada<V> l = null;
		 for(int i = 0; i < mainArea.size(); i++)
		 {
			 DictEntry<K, V> entry = mainArea.get(i);
			 if(entry != null)
			 {
				 if(!init)
				 {
					 l = entry.getValues();
				 }
				 else
				 {
					 l.joinList(entry.getValues());
				 }
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
			 if(i < mainArea.size()-1)
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
		 int pos = key.hashCode()%mainAreaSize;
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
