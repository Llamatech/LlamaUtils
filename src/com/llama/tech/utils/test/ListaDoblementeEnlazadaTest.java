package com.llama.tech.utils.test;

import com.llama.tech.utils.list.Lista;
import com.llama.tech.utils.list.ListaDoblementeEnlazada;

/**
 * Esta es la clase que prueba la lista doblemente enlazada
 */
public class ListaDoblementeEnlazadaTest extends ListaTest {
	
	/**
	 * Este método crea una nueva lista
	 */
	@Override
	public Lista<Integer> crearNuevaLista() {
		return new ListaDoblementeEnlazada<Integer>();
	}

}
