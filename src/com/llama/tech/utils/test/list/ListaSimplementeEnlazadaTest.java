package com.llama.tech.utils.test.list;

import com.llama.tech.utils.list.Lista;
import com.llama.tech.utils.list.ListaSimplementeEnlazada;

/**
 * Esta es la clase que prueba la lista simplemente enlazada
 */
public class ListaSimplementeEnlazadaTest extends ListaTest{

	/**
	 * Este metodo crea una lista nueva
	 */
	@Override
	public Lista<Integer> crearNuevaLista() {
		return new ListaSimplementeEnlazada<Integer>();
	}
	
	

}
