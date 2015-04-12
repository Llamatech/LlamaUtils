package com.llama.tech.utils.tree;

import java.util.Iterator;

import com.llama.tech.utils.list.LlamaArrayList;

public class LlamaTrieIterator<T> implements Iterator<T>{

	private LlamaArrayList<T> elementos;

	private int actual;

	public  LlamaTrieIterator(LlamaArrayList<T> elem) {
		elementos = elem;
		actual=0;
	}

	/**
	 * Constructor de un iterador vacio
	 */
	public  LlamaTrieIterator() {
		elementos = new LlamaArrayList<T>(1);
	}

	@Override
	public boolean hasNext() {
		if(actual==elementos.size())
			return false;
		return true;
	}

	@Override
	public T next() {
		if(actual>=elementos.size())
			return null;
		return elementos.get(actual++);
	}





}
