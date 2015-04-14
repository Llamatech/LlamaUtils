package com.llama.tech.utils.test.tree;

import java.util.Iterator;

import com.llama.tech.utils.list.Lista;
import com.llama.tech.utils.list.LlamaArrayList;
import com.llama.tech.utils.tree.LlamaTrie;
import com.llama.tech.utils.tree.TrieNode;

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
		trie.agregar("Tomate", "A");
		trie.agregar("Tomate", "B");
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

	public void testAgregar()
	{
		setupEscenario3();

		trie.agregar("Torre", "Torre");	
		trie.agregar("Toma", "Toma");
		trie.agregar("Tomate", "A");
		trie.agregar("Antes", "Antes");
		lista = new LlamaArrayList<String>(3);
		lista.addAlFinal(1+"");
		lista.addAlFinal(2+"");
		lista.addAlFinal(3+"");
		trie.agregar("Tomate", lista.iterator());

		TrieNode<String> init =  trie.root.getChild();

		assertEquals("No es el caracter esperado", 'T', init.getCharacter());
		assertEquals("No es el caracter esperado", 'o', init.getChild().getCharacter());
		assertEquals("No es el caracter esperado", 'A', init.getSibling().getCharacter());
		assertEquals("No es el caracter esperado", 'r', init.getChild().getChild());
		assertEquals("No es el caracter esperado", "r", init.getChild().getChild().getChild());
		assertEquals("No es el caracter esperado", "e", init.getChild().getChild().getChild().getChild());
		assertEquals("No es el caracter esperado", "m", init.getChild().getChild().getSibling());
		assertEquals("No es el caracter esperado", "a", init.getChild().getChild().getSibling().getChild());
		assertEquals("No es el caracter esperado", "t", init.getChild().getChild().getSibling().getChild().getChild());
		assertEquals("No es el caracter esperado", "e", init.getChild().getChild().getSibling().getChild().getChild().getChild());
		assertEquals("No es el caracter esperado", "n", init.getSibling().getChild());
		assertEquals("No es el caracter esperado", "t", init.getSibling().getChild().getChild());
		assertEquals("No es el caracter esperado", "e", init.getSibling().getChild().getChild().getChild());
		assertEquals("No es el caracter esperado", "s", init.getSibling().getChild().getChild().getChild().getChild());





	}

	public void testBuscar()
	{
		setupEscenario1();

		assertTrue("Deberia encontrar la palabra", trie.buscarB("Torno"));
		assertFalse("No debería encontrar la palabra", trie.buscarB("Torn"));
		assertFalse("No debería encontrar la palabra", trie.buscarB("Hola"));

		setupEscenario2();

		Iterator<String> it = trie.buscar("Torre");

		int i =0;
		while(it.hasNext())
		{
			assertTrue("Hubo un error recuperando el valor", it.next().equals(i+""));
			i++;
		}

		it = trie.buscar("Tomate");
		assertEquals("Hubo un error recuperando el valor", "A", it.next());
		assertEquals("Hubo un error recuperando el valor", "B", it.next());

		it = trie.buscar("Antes");
		assertEquals("Hubo un error recuperando el valor", "Antes", it.next());

	}

	public void testBuscarPrefijo()
	{
		setupEscenario1();

		assertTrue("Deberia encontrar el prefijo", trie.buscarPrefijoB("Torno"));
		assertTrue("Debería encontrar el prefijo", trie.buscarPrefijoB("Torn"));
		assertFalse("No debería encontrar el prefijo", trie.buscarPrefijoB("Hola"));

		setupEscenario2();

		Iterator<String> it = trie.buscarPrefijo("Torna");

		assertEquals("Hubo un error recuperando el valor", "6", it.next());
		assertEquals("Hubo un error recuperando el valor", "7", it.next());
		assertEquals("Hubo un error recuperando el valor", "8", it.next());
		assertEquals("Hubo un error recuperando el valor", "9", it.next());
		assertEquals("Hubo un error recuperando el valor", "Tornar", it.next());
		assertEquals("Hubo un error recuperando el valor", "Tornasol", it.next());

		it = trie.buscarPrefijo("Holi");
		assertFalse("No debería haber ningun elemento", it.hasNext());


		setupEscenario3();

		it = trie.buscarPrefijo("Torna");
		assertFalse("No debería haber ningun elemento", it.hasNext());


	}



	public void testEliminar()
	{
		setupEscenario1();

		assertTrue("Debería eliminarse",trie.eliminar("Tornar"));
		assertFalse("La palabra no se elimino correctamente",trie.buscarB("Tornar"));
		assertTrue("El prefijo no debería haberse borrado", trie.buscarPrefijoB("Torna"));
		assertFalse("No debería eliminarse",trie.eliminar("Holi"));

		setupEscenario3();

		assertFalse("No debería borrarse", trie.eliminar("Tor"));
	}

	public void testEliminarPrefijo()
	{
		setupEscenario1();

		assertTrue("Debería borrar el prefijo", trie.eliminarPrefijo("Torna"));
		assertFalse("La palabra no debería existir", trie.buscarB("Tornar"));
		assertFalse("La palabra no debería existir", trie.buscarB("Tornado"));
		assertFalse("La palabra no debería existir", trie.buscarB("Tornasol"));
		assertTrue("La palabra debería existir", trie.buscarB("Torno"));

		assertFalse("no debería borrar el prefijo", trie.eliminarPrefijo("Hola"));

		setupEscenario3();

		assertFalse("No debería borrarse", trie.eliminarPrefijo("Tor"));
	}
}
