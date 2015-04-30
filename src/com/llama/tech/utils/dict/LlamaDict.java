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

import java.io.Serializable;
import java.util.Iterator;

import com.llama.tech.misc.XMLFormat;
import com.llama.tech.utils.list.Lista;
import com.llama.tech.utils.list.ListaDoblementeEnlazada;
import com.llama.tech.utils.list.LlamaArrayList;
import com.llama.tech.utils.list.LlamaIterator;

public class LlamaDict<K extends Comparable<K>, V extends Comparable<V>> extends XMLFormat implements Dictionary<K, V>, Serializable, Comparable<LlamaDict<K, V>>
{
	 /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int size = 0;
     private double capacity = 0;
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
         capacity = size/((double) mainAreaSize);
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
       
		 capacity = size/((double) mainAreaSize);
		 if(capacity >= 0.8)
		 {
			 dalloc(DictAlloc.INCREASE);
		 }
	
		 
		 int pos = Math.floorMod(key.hashCode(), mainAreaSize);
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
		 
		 
		 int pos = Math.floorMod(key.hashCode(), mainAreaSize);
		 DictEntry<K, V> entry = mainArea.get(pos);
		 
		 return entry.setValue(key, value);
	 }
	 
	 @Override
	 public V removeEntry(K key)
	 {
		 capacity = size/((double) mainAreaSize);
		 if(capacity < 0.2)
		 {
			 dalloc(DictAlloc.DECREASE);
		 }
		 
		 V rem = null;
		 int pos = Math.floorMod(key.hashCode(), mainAreaSize);
		 DictEntry<K,V> primero = mainArea.get(pos);

		 if(primero != null && primero.getMyValue() != null)
		 {
			 
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
		 int pos = Math.floorMod(key.hashCode(), mainAreaSize);
		 if(pos < 0)
		 {
			 return null;
		 }
		 DictEntry<K, V> primero= mainArea.get(pos);
		 
		 return primero.getValue(key);//:D oops Jajajajaja, intervine de forma muy indecente, perdón!
	 }
	//FUe bastante pertienente la intervencion!
	 
   public void dalloc(DictAlloc op)
   {
            int delta = mainAreaSize;
            if(op == DictAlloc.INCREASE)
            {
               delta = mainAreaSize*2;
            }
            else if(op == DictAlloc.DECREASE)
            {
               delta = (int) (mainAreaSize/2);
            }
            
            if(delta > 0)
            {
	            Lista<DictEntry<K,V>> mainAreaTemp = new LlamaArrayList<DictEntry<K,V>>(delta);
	            for(int i = 0; i < delta; i++)
	            {
	                 mainAreaTemp.addAlFinal(new DictEntry<K,V>(null, null));
	            }
	
	            LlamaIterator<K> keys = getKeys();
	            LlamaIterator<V> values = getValues();
	
	            while(keys.hasNext())
	            {
	                 K key = keys.next();
	                 V value = values.next();
	                 int pos = Math.floorMod(key.hashCode(), delta); //int pos = key.hashCode() % delta;
	                 DictEntry<K, V> entry = mainAreaTemp.get(pos);
	                 entry.addEntry(key, value);
	            }
	            
	            mainArea = mainAreaTemp;
	            mainAreaSize = delta;
	            capacity = size/((double) mainAreaSize);
            }
   }
   
	 @Override
	 public int size()
	 {
		 return size;
	 }
	
	 public static class UnhashableTypeException extends Exception
	 {
		 /**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		public UnhashableTypeException(String msj)
		 {
		 	 super(msj);
		 }
	 }

    private static enum DictAlloc
    { 
       INCREASE,
       DECREASE
    }

	@Override
	public int compareTo(LlamaDict<K, V> o) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Iterator<K> iterator() 
	{
		return getKeys();
	}

	@Override
	public String toXML() 
	{
		String content = "<dict>\n";
		Iterator<K> keys = getKeys();
		while(keys.hasNext())
		{
			content += "<key";
			K key = keys.next();
			if(key instanceof XMLFormat)
			{
				content += ">\n"+((XMLFormat) key).toXML();
			}
			else
			{
				content += " value = \""+key.toString()+"\">\n";
			}
			content += "<value";
			V value = getValue(key);
			if(value instanceof XMLFormat)
			{
				content += ">\n"+((XMLFormat) value).toXML()+"</value>\n";
			}
			else
			{
				content += " value = \""+value.toString()+"\" />\n";
			}
			content += "</key>\n";
		}
		
		content += "</dict>\n";
		
		return content;
	}

	@Override
	public void readXML() 
	{
		// TODO Auto-generated method stub
		
	}

	 
	
}
