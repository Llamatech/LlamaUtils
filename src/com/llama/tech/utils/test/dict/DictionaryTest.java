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
		
		try 
		{
			setUpEscenario3();
			int size_pre = dict.size();
			int value = new Random().nextInt((9999 - 3546) + 1) + 3545;
			dict.addEntry(String.valueOf(value), value);
			int size_post = dict.size();

			LlamaIterator<String> it = dict.getKeys();
			boolean found = false;
			while(it.hasNext() && !found)
			{
				String s = it.next();
				if(s.equals(String.valueOf(value)))
				{
					found = true;
				}
			}
			
			assertEquals(String.format("La nueva llave: %d, debería encontrarse en el conjunto de llaves.", value), true, found);
			assertEquals("El nuevo tamaño del diccionario debería ser: "+size_post, size_post-1, size_pre);
		
			value = new Random().nextInt((9999 - 3545) + 1) + 3545;
			dict.addEntry(String.valueOf(value), value);
			dict.addEntry(String.valueOf(value), value+9);
			assertEquals(String.format("El valor de la llave: %d, debería corresponder a %d.", value, value+9), value+9, (int) dict.getValue(String.valueOf(value)));
			
		} 
		catch (UnhashableTypeException e) 
		{
			fail("La inicialización no debería ser fallida.");
		}
		
	}
	
	public void testSetEntry()
	{
		setUpEscenario1();
		try 
		{
			assertEquals("El método debería retornar null", null, dict.setEntry("1", 1));
		} 
		catch (UnhashableTypeException e) 
		{
			fail("El método no debería emitir una excepción.");
		}
		
		try 
		{
			setUpEscenario2();
			int pre_v = dict.getValue("1");
			int extract = dict.setEntry("1", 8);
			int post_v = dict.getValue("1");
			
			assertEquals("El valor a reemplazar debería ser: "+pre_v, extract, pre_v);
			assertEquals("El nuevo valor debería corresponder a: "+8, 8, post_v);
			
			
		} 
		catch (UnhashableTypeException e) 
		{
			fail("El método no debería emitir una excepción.");
		}
		
	}
	
	
	
	
	
}
