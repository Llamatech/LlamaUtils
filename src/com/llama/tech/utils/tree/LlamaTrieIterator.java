package com.llama.tech.utils.tree;

import java.util.Iterator;

import com.llama.tech.utils.list.Lista;
import com.llama.tech.utils.list.LlamaArrayList;

public class LlamaTrieIterator<T> implements Iterator<T>{

	private Lista<T> elementos;

	private int actual;

	public  LlamaTrieIterator(Lista<T> elem) {
		elementos = new LlamaArrayList<T>(1);
		for(T t: elem)
			elementos.addAlFinal(t);
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
