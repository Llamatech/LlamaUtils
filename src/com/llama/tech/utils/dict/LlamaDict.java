/*
 * LlamaDict.java
 * This f.ile is part of LlamaUtils
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


public class LlamaDict<K, V> 
{

	private class DictEntry
	{
		private DictEntry next;
		private Object key;
		private Object value;
		int hashValue;
		
	    public DictEntry(Object key, Object value)
	    {
	    	this.key = key;
	    	this.value = value;
	    	this.hashValue = key.hashCode();
	    }
	    
	    public Object getValue(Object key)
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
	    	//Aquí no llega
	    	return null;
	    }
	    
	    public void addEntry()
	    {
	    	
	    }
	
	}
	
	
	
	
}
