package com.llama.tech.utils.tree;

import java.util.Iterator;

import com.llama.tech.utils.list.Lista;
import com.llama.tech.utils.list.LlamaArrayList;

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
		String repr = root.wordTree();
		if(repr.length() > 1)
		{
		    repr = repr.substring(0, repr.length()-2);
		}
		return repr;

	}


	public static void main(String... args)
	{
		LlamaTrie<String> trie = new LlamaTrie<String>();
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

		Iterator<String> it = trie.buscar("Tomate");
		while(it.hasNext())
		{
			System.out.println(it.next());
		}
		
		
		System.out.println(trie);	

		trie.eliminar("Toma");
		
		System.out.println(trie);	


	}

	public boolean eliminar(String word) 
	{
		return root.eliminar(word);
	}

	public boolean buscarB(String word)
	{
		return root.buscarB(word, 0);
	}
	
	public boolean buscarPrefijoB(String word)
	{
		return root.buscarPrefijoB(word, 0);
	}

	public Iterator<T> buscar(String word)
	{
		return root.buscar(word	, 0);
	}
	
	public Iterator<T> buscarPrefijo(String p)
	{
		Lista<T> lista = new LlamaArrayList<T>(50);
		root.buscarPrefijo(p, lista, 0, false);
		return lista.iterator();
	}   
	
	public boolean eliminarPrefijo(String p)
	{
		return root.eliminarPrefijo(p, 0);
	}



}
