package com.llama.tech.utils.tree;

import java.util.Iterator;

public class LlamaTrie<T> 
{	
	public TrieNode<T> root;

	public LlamaTrie()
	{
		root = new TrieNode<T>();
	}

	public boolean agregar(String word, T elem)
	{
		return root.agregar(word, elem);	
	}

	public boolean agregar(String word, Iterator<T> elems)
	{
		return root.agregar(word, elems);	
	}

	@Override
	public String toString()
	{
		return root.wordTree();

	}


	public static void main(String... args)
	{
		LlamaTrie<String> trie = new LlamaTrie<String>();
		trie.agregar("Torre", "Torre");		
		trie.agregar("Toma", "Toma");
		trie.agregar("Torreón", "Torreón");
		trie.agregar("Tomate", "Tomate");
		trie.agregar("Tomar", "Tomar");
		trie.agregar("Torrijos", "Torrijos");
		trie.agregar("Tornado", "Tornado");
		trie.agregar("Torta", "Torta");
		trie.agregar("Tormenta", "Tormenta");
		trie.agregar("Torno", "Torno");
		trie.agregar("Tornar", "Tornar");
		trie.agregar("Tornasol", "Tornasol");
		trie.agregar("Antes", "Antes");	


		Iterator<String> it = trie.buscar("Tomar");
		
		System.out.println(trie);	
		System.out.println('A' < 'T');



	}

	public Iterator<T> buscar(String word)
	{
		return root.buscar(word	, 0);
	}



}
