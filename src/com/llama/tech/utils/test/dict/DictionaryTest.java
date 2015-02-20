package com.llama.tech.utils.test.dict;

import java.util.Random;

import com.llama.tech.utils.dict.Dictionary;
import com.llama.tech.utils.dict.LlamaDict;
import com.llama.tech.utils.dict.LlamaDict.UnhashableTypeException;
import com.llama.tech.utils.list.LlamaIterator;

import junit.framework.TestCase;

public class DictionaryTest extends TestCase
{
	private Dictionary<String, Integer> dict; 
	
	public void setUpEscenario1()
	{
		dict = new LlamaDict<String, Integer>(10);
	}
	
	public void setUpEscenario2() throws UnhashableTypeException
	{
		dict = new LlamaDict<String, Integer>(20);
		for(int i = 0; i < 3545; i++)
		{
			dict.addEntry(String.valueOf(i), i);
		}
		
	}
	
	public void setUpEscenario3() throws UnhashableTypeException
	{
		dict = new LlamaDict<String, Integer>(2);
		dict.addEntry("1", 1);
	}
	
	
	public void testGetValue()
	{
		setUpEscenario1();
		try
		{
			dict.getValue("0");
			fail("El método debería lanzar una excepción");
		}
		catch(Exception a)
		{
			
		}
		try 
		{
			setUpEscenario2();
			int value = new Random().nextInt(3545);
			assertEquals("El método debería retornar "+value, value, (int) dict.getValue(String.valueOf(value)));
			assertEquals("El método debería retornar 1", 1, (int) dict.getValue("1"));
			assertEquals("El método debería retornar null", null, dict.getValue("3545345"));
		} 
		catch (UnhashableTypeException e) 
		{
			e.printStackTrace();
			fail(e.getMessage());
		}
	}
	
	
	public void testRemoveEntry()
	{
		setUpEscenario1();
		int value = new Random().nextInt(3545);
		assertEquals("El método debería retornar null", null, dict.removeEntry(String.valueOf(value)));
		try 
		{
			setUpEscenario2();
			try
			{
				int size_pre = dict.size();
				int v = dict.removeEntry(String.valueOf(value));
				int size_post = dict.size();
				assertEquals("El método debería retornar "+value, value, v);
				assertEquals("El nuevo tamaño del diccionario debería ser: "+size_post, size_post, size_pre-1);
				
				LlamaIterator<String> it = dict.getKeys();
				
				while(it.hasNext())
				{
					String s = it.next();
					if(s.equals(String.valueOf(value)))
					{
						fail("La llave no debería existir en el conjunto de llaves");
					}
					
				}
				
				LlamaIterator<Integer> itV = dict.getValues();
				
				while(itV.hasNext())
				{
					int s = itV.next();
					if(s == value)
					{
						fail("El valor no debería existir en el conjunto de valores");
					}
				}
				
				
			}
			catch(NullPointerException npe)
			{
				npe.printStackTrace();
			}
			
		} 
		catch (UnhashableTypeException e) 
		{
			fail(e.getMessage());
		}
		try 
		{
			setUpEscenario3();
			int size_pre = dict.size();
			int v = dict.removeEntry("1");
			int size_post = dict.size();
			assertEquals("El método debería retornar "+1, 1, v);
			assertEquals("El nuevo tamaño del diccionario debería ser: "+size_post, size_post, size_pre-1);
			
			LlamaIterator<String> it = dict.getKeys();
			
			while(it.hasNext())
			{
				String s = it.next();
				if(s.equals(String.valueOf(value)))
				{
					fail("La llave no debería existir en el conjunto de llaves");
				}
				
				System.out.println(s);
			}
			assertEquals("El método debería retornar null", null, dict.removeEntry("1"));
			
			
			
		} 
		catch (UnhashableTypeException e) 
		{
		    fail(e.getMessage());
		}	
	}
	
	public void testAddEntry()
	{
		setUpEscenario1();
		try 
		{
			int size_pre = dict.size();
			dict.addEntry("2", 2);
			int size_post = dict.size();
			assertEquals("El método debería retornar "+2, 2, (int) dict.getValue("2"));
			assertEquals("El nuevo tamaño del diccionario debería ser: "+size_post, size_post-1, size_pre);
		} 
		catch (UnhashableTypeException e) 
		{
			e.printStackTrace();
		}
		
		try 
		{
			dict.addEntry(null, null);
			fail("El método debería enviar una excepción");
		} 
		catch (UnhashableTypeException e) 
		{
			
		}
		
		
	}
	
	
	
}
