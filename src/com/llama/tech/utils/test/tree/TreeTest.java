package com.llama.tech.utils.test.tree;

import java.util.Iterator;
import java.util.Random;

import com.llama.tech.utils.dict.Dictionary;
import com.llama.tech.utils.dict.LlamaDict;
import com.llama.tech.utils.dict.LlamaDict.UnhashableTypeException;
import com.llama.tech.utils.tree.LlamaAVLTree;
import com.llama.tech.utils.tree.Tree;

import junit.framework.TestCase;

public class TreeTest extends TestCase 
{
	private Tree<Integer> tree;
	
	public void setupEscenario1()
	{
		tree = new LlamaAVLTree<Integer>();
	}
	
	public void setupEscenario2()
	{
		tree = new LlamaAVLTree<Integer>();
		tree.add(new Random().nextInt(50));
		tree.add(new Random().nextInt((200 - 100) + 1) + 100);
	}
	
	public void setupEscenario3()
	{
		tree = new LlamaAVLTree<Integer>();
		tree.add(new Random().nextInt((250 - 200) + 1) + 200);
		tree.add(new Random().nextInt((150 - 100) + 1) + 100);
	}
	
	public void setupEscenario4()
	{
		tree = new LlamaAVLTree<Integer>();
		tree.add(200);
		tree.add(150);
		tree.add(250);
		tree.add(100);
		tree.add(175);
	}
	public void setupEscenario5()
	{
		tree = new LlamaAVLTree<Integer>();
		tree.add(200);
		tree.add(150);
		tree.add(250);
		tree.add(225);
		tree.add(275);
	}
	
	public void testAdd()
	{
		setupEscenario1();
		int n = new Random().nextInt(1000);
		tree.add(n);
		assertEquals("El nuevo tamaño del árbol debería ser 1", 1, tree.size());
		assertEquals("El árbol debería estar balanceado", true, tree.isBalanced());
		assertEquals(String.format("El árbol debería contener %d", n), true, tree.contains(n));
		
		setupEscenario2();
		n = new Random().nextInt((300 - 200) + 1) + 200;
		tree.add(n);
		assertEquals("El árbol debería estar balanceado", true, tree.isBalanced());
		assertEquals("La altura del árbol debería ser 2", 2, tree.getHeight());
		assertEquals(String.format("El árbol debería contener %d", n), true, tree.contains(n));
		
		setupEscenario3();
		n = new Random().nextInt(150);
		tree.add(n);
		assertEquals("El árbol debería estar balanceado", true, tree.isBalanced());
		assertEquals("La altura del árbol debería ser 2", 2, tree.getHeight());
		assertEquals(String.format("El árbol debería contener %d", n), true, tree.contains(n));
		
		setupEscenario2();
		n = new Random().nextInt((100 - 51) + 1) + 51;
		tree.add(n);
		assertEquals("El árbol debería estar balanceado", true, tree.isBalanced());
		assertEquals("La altura del árbol debería ser 2", 2, tree.getHeight());
		assertEquals(String.format("El árbol debería contener %d", n), true, tree.contains(n));
		
		setupEscenario3();
		n = new Random().nextInt((200 - 150) + 1) + 150;
		tree.add(n);
		assertEquals("El árbol debería estar balanceado", true, tree.isBalanced());
		assertEquals("La altura del árbol debería ser 2", 2, tree.getHeight());
		assertEquals(String.format("El árbol debería contener %d", n), true, tree.contains(n));
		
	    setupEscenario4();
	    n = new Random().nextInt(100);
	    tree.add(n);
	    assertEquals("El árbol debería estar balanceado", true, tree.isBalanced());
	    assertEquals("La altura del árbol debería ser 3", 3, tree.getHeight());
	    assertEquals(String.format("El árbol debería contener %d", n), true, tree.contains(n));
	    
	    setupEscenario4();
	    n = new Random().nextInt((150-100)+1) + 100;
	    tree.add(n);
	    assertEquals("El árbol debería estar balanceado", true, tree.isBalanced());
	    assertEquals("La altura del árbol debería ser 3", 3, tree.getHeight());
	    assertEquals(String.format("El árbol debería contener %d", n), true, tree.contains(n));
	    
	    setupEscenario4();
	    n = new Random().nextInt((175-151)+1) + 151;
	    tree.add(n);
	    assertEquals("El árbol debería estar balanceado", true, tree.isBalanced());
	    assertEquals("La altura del árbol debería ser 3", 3, tree.getHeight());
	    assertEquals(String.format("El árbol debería contener %d", n), true, tree.contains(n));
	    
	    setupEscenario4();
	    n = new Random().nextInt((200-175)+1) + 175;
	    tree.add(n);
	    assertEquals("El árbol debería estar balanceado", true, tree.isBalanced());
	    assertEquals("La altura del árbol debería ser 3", 3, tree.getHeight());
	    assertEquals(String.format("El árbol debería contener %d", n), true, tree.contains(n));
	    
	    setupEscenario5();
	    n = new Random().nextInt((300-275)+1) + 275;
	    tree.add(n);
	    assertEquals("El árbol debería estar balanceado", true, tree.isBalanced());
	    assertEquals("La altura del árbol debería ser 3", 3, tree.getHeight());
	    assertEquals(String.format("El árbol debería contener %d", n), true, tree.contains(n));
	    
	    setupEscenario5();
	    n = new Random().nextInt((275-251)+1) + 251;
	    tree.add(n);
	    assertEquals("El árbol debería estar balanceado", true, tree.isBalanced());
	    assertEquals("La altura del árbol debería ser 3", 3, tree.getHeight());
	    assertEquals(String.format("El árbol debería contener %d", n), true, tree.contains(n));
	    
	    setupEscenario5();
	    n = new Random().nextInt((250-225)+1) + 225;
	    tree.add(n);
	    assertEquals("El árbol debería estar balanceado", true, tree.isBalanced());
	    assertEquals("La altura del árbol debería ser 3", 3, tree.getHeight());
	    assertEquals(String.format("El árbol debería contener %d", n), true, tree.contains(n));
	    
	    setupEscenario5();
	    n = new Random().nextInt((225-200)+1) + 200;
	    tree.add(n);
	    assertEquals("El árbol debería estar balanceado", true, tree.isBalanced());
	    assertEquals("La altura del árbol debería ser 3", 3, tree.getHeight());
	    assertEquals(String.format("El árbol debería contener %d", n), true, tree.contains(n));
	    
	    setupEscenario1();
	    
	    for(int i = 0; i < 2000; i++)
	    {
	    	tree.add(i);
	    }
	    assertEquals("El árbol debería estar balanceado", true, tree.isBalanced());
	    
	    for(int i = 0; i < 2000; i++)
	    {
	    	assertEquals(String.format("El árbol debería contener %d", i), true, tree.contains(i));
	    }
	    
	    setupEscenario1();
	    for(int i = 0; i < 40000; i++)
	    {
	    	tree.add(new Random().nextInt(4000)+1);
	    }
	    assertEquals("El árbol debería estar balanceado", true, tree.isBalanced());
	    
	    setupEscenario1();
		assertNull("El árbol no contiene elementos", tree.get(0));
		Integer[] l = new Integer[2000];
		Dictionary<Integer, Integer> d = new LlamaDict<Integer, Integer>(20);
		for(int i = 0; i < 30; i++)
		{
			l[i] = new Random().nextInt(4000)+1;
			while(d.getValue(l[i]) != null)
			{
				l[i] = new Random().nextInt(4000)+1;
			}
			try 
			{
				d.addEntry(l[i], l[i]);
				tree.add(l[i]);
				System.out.println(l[i]);
			} 
			catch (UnhashableTypeException e) 
			{
				fail("El método no debería arrojar excepción.");
			}
		}
		
		Iterator<Integer> it = d.getKeys();
		System.out.println(tree);
		while(it.hasNext())
		{
			int next = it.next();
			assertEquals(String.format("El árbol debería contener %d", next), true, tree.contains(next));
		}
	}
	
	public void testRemove()
	{
		setupEscenario1();
		assertNull("No hay elementos en el árbol", tree.remove(0));
		int n = new Random().nextInt(2000);
		tree.add(n);
		assertEquals(String.format("El árbol debería contener %d", n), true, tree.contains(n));
		int r = tree.remove(n);
		assertEquals(String.format("%d debería ser eliminado", n), n, r);
		assertEquals("La altura del árbol debería ser 0", 0, tree.getHeight());
		assertEquals("No deberían quedar elementos en el árbol", 0, tree.size());
		
		setupEscenario2();
		n = new Random().nextInt((400-300)+1) + 300;
		assertNull(String.format("El elemento %d, no debería existir", n), tree.remove(n));
		
		setupEscenario1();
		
		for(int i = 0; i < 2000; i++)
		{
			tree.add(i);
		}
		
		for(int i = 0; i < 100; i++)
		{
			//n = new Random().nextInt(1999);
			tree.remove(i);
			assertEquals(String.format("El árbol no debería contener %d", n), false, tree.contains(i));
			assertEquals("El árbol debería estar balanceado", true, tree.isBalanced());
		}
		
	}
	
	public void testGet()
	{
		setupEscenario1();
		assertNull("El árbol no contiene elementos", tree.get(0));
		Integer[] l = new Integer[2000];
		Dictionary<Integer, Integer> d = new LlamaDict<Integer, Integer>(20);
		for(int i = 0; i < 2000; i++)
		{
			l[i] = new Random().nextInt(4000)+1;
			while(d.getValue(l[i]) != null)
			{
				l[i] = new Random().nextInt(4000)+1;
			}
			try 
			{
				d.addEntry(l[i], l[i]);
				tree.add(l[i]);
			} 
			catch (UnhashableTypeException e) 
			{
				fail("El método no debería arrojar excepción.");
			}
		}
		int len = d.size();
		assertEquals(String.format("La cantidad de elementos en el árbol debería ser %d", len), len, tree.size());
		assertEquals("El árbol debería estar balanceado", true, tree.isBalanced());
		Iterator<Integer> it = d.getKeys();
		System.out.println(tree);
		while(it.hasNext())
		{
			int item = it.next();
			System.out.println(item);
			assertEquals(String.format("El elemento %d debería estar en el árbol", item), item, (int) tree.get(item));
		}
		
	}
	
	
	
}
