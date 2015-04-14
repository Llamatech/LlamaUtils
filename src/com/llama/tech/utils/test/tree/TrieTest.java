package com.llama.tech.utils.test.tree;

import com.llama.tech.utils.list.Lista;
import com.llama.tech.utils.list.LlamaArrayList;
import com.llama.tech.utils.tree.LlamaTrie;

import junit.framework.TestCase;

public class TrieTest extends TestCase{

	private LlamaTrie<String> trie;
	private Lista<String> lista;
	
	public void setupEscenario1()
	{
		trie = new LlamaTrie<String>();
		trie.agregar("Torre", "Torre");		
		trie.agregar("Toma", "Toma");
		trie.agregar("Torreón", "Torreón");
		trie.agregar("Tomate", "A");
		trie.agregar("Tomate", "B");
		trie.agregar("Tomar", "Tomar");
		trie.agregar("Torrijos", "Torrijos");
		trie.agregar("Tornado", "Tornado");
		trie.agregar("Torta", "Torta");
		trie.agregar("Tormenta", "Tormenta");
		trie.agregar("Torno", "Torno");
		trie.agregar("Tornar", "Tornar");
		trie.agregar("Tornasol", "Tornasol");
		trie.agregar("Antes", "Antes");
		
	}
	
	public void setupEscenario2()
	{
		lista = new LlamaArrayList<String>(10);
		
		for (int i = 0; i < 10; i++) {
			
			lista.addAlFinal(""+i);
		}
		
		trie = new LlamaTrie<String>();
		trie.agregar("Torre", lista.iterator());		
		trie.agregar("Toma", lista.iterator(1));
		trie.agregar("Torreón", lista.iterator(2));
		trie.agregar("Tomate", lista.iterator(3));
		trie.agregar("Tomate", "Tomate");
		trie.agregar("Tomar", lista.iterator(4));
		trie.agregar("Torrijos", lista.iterator(5));
		trie.agregar("Tornado", lista.iterator(6));
		trie.agregar("Torno", lista.iterator(7));
		trie.agregar("Tornar", "Tornar");
		trie.agregar("Tornasol", "Tornasol");
		trie.agregar("Antes", "Antes");
	
		
	}
	
	public void setupEscenario3()
	{
		trie = new LlamaTrie<String>();
	}
}
