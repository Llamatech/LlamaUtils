package com.llama.tech.utils.test.tree;

import java.util.Iterator;

import com.llama.tech.utils.list.Lista;
import com.llama.tech.utils.list.LlamaArrayList;
import com.llama.tech.utils.tree.CopyOfLlamaTrie;
import com.llama.tech.utils.tree.CopyOfTrieNode;
import com.llama.tech.utils.tree.LlamaTrie;
import com.llama.tech.utils.tree.TrieNode;

import junit.framework.TestCase;

public class TrieTest extends TestCase{

	private LlamaTrie<String> trie;
	private LlamaTrie<LlamaArrayList<String>> trie2;
	private LlamaArrayList<String> lista;

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

		lista.addAlFinal("a");

		trie2 = new LlamaTrie<LlamaArrayList<String>>();
		
		trie2.agregar("Torre", lista);
		lista= new LlamaArrayList<String>(10);
		lista.addAlFinal("b");
		trie2.agregar("Toma", lista);
		lista= new LlamaArrayList<String>(10);
		lista.addAlFinal("c");
		trie2.agregar("Torreón", lista);
		lista= new LlamaArrayList<String>(10);
		lista.addAlFinal("e1");
		trie2.agregar("Tomate", lista);
		lista= new LlamaArrayList<String>(10);
		lista.addAlFinal("e2");
		trie2.agregar("Tomate", lista);
		lista= new LlamaArrayList<String>(10);
		lista.addAlFinal("e");
		trie2.agregar("Tomar", lista);
		lista= new LlamaArrayList<String>(10);
		lista.addAlFinal("f");
		trie2.agregar("Torrijos", lista);
		lista= new LlamaArrayList<String>(10);
		lista.addAlFinal("g");
		trie2.agregar("Tornados", lista);
		lista= new LlamaArrayList<String>(10);
		lista.addAlFinal("h");
		trie2.agregar("Antes", lista);


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


		TrieNode<String> init =  trie.root.getChild();

		assertEquals("No es el caracter esperado", 'A', init.getCharacter());
		assertEquals("No es el caracter esperado", 'n', init.getChild().getCharacter());
		assertEquals("No es el caracter esperado", 'T', init.getSibling().getCharacter());
		assertEquals("No es el caracter esperado", 't', init.getChild().getChild().getCharacter());
		assertEquals("No es el caracter esperado", 'e', init.getChild().getChild().getChild().getCharacter());
		assertEquals("No es el caracter esperado", 's', init.getChild().getChild().getChild().getChild().getCharacter());
		init = trie.root.getChild().getSibling();
		assertEquals("No es el caracter esperado", 'o', init.getChild().getCharacter());
		assertEquals("No es el caracter esperado", 'm', init.getChild().getChild().getCharacter());
		assertEquals("No es el caracter esperado", 'a', init.getChild().getChild().getChild().getCharacter());
		assertEquals("No es el caracter esperado", 't', init.getChild().getChild().getChild().getChild().getCharacter());
		assertEquals("No es el caracter esperado", 'e', init.getChild().getChild().getChild().getChild().getChild().getCharacter());
		assertEquals("No es el caracter esperado", 'r', init.getChild().getChild().getSibling().getCharacter());
		assertEquals("No es el caracter esperado", 'r', init.getChild().getChild().getSibling().getChild().getCharacter());
		assertEquals("No es el caracter esperado", 'e', init.getChild().getChild().getSibling().getChild().getChild().getCharacter());



	}

	public void testBuscar()
	{
		setupEscenario1();

		assertEquals("Deberia encontrar la palabra", "Torno", trie.buscar("Torno"));
		assertEquals("Deberia encontrar la palabra","B", trie.buscar("Tomate"));
		assertNull("No debería encontrar la palabra", trie.buscar("Torn"));
		assertNull("No debería encontrar la palabra", trie.buscar("Hola"));

		setupEscenario2();

		LlamaArrayList<String> it = trie2.buscar("Tomate");
		assertEquals("El valor no corresponde", it.get(0),"e1");
		assertEquals("El valor no corresponde", it.get(1),"e2");
		
		it = trie2.buscar("Toma");
		assertEquals("El valor no corresponde", "b",it.get(0));
		
		assertNull("No debería retornar ningún valor porque la palabra no existe", trie2.buscar("holi"));
		

	}

	public void testBuscarPrefijo()
	{
		setupEscenario1();

		Iterator<String> it = trie.buscarPalabrasConPrefijo("Tom");
		
		assertEquals("Hubo un error recuperando el valor", "Toma", it.next());
		assertEquals("Hubo un error recuperando el valor", "Tomar", it.next());
		assertEquals("Hubo un error recuperando el valor", "Tomate", it.next());
		
		it = trie.buscarPalabrasConPrefijo("holi");
		assertFalse("No debería retornar ningún valor porque la palabra no existe", it.hasNext());
		

		setupEscenario3();

		it = trie.buscarPalabrasConPrefijo("Torna");
		assertFalse("No debería haber ningun elemento", it.hasNext());


	}



	public void testEliminar()
	{
		setupEscenario1();

		assertEquals("Debería eliminarse",trie.eliminar("Tornar"),"Tornar");
		assertNull("La palabra no se elimino correctamente",trie.buscar("Tornar"));
		assertNull("No debería poderse eliminar",trie.eliminar("Holi"));
		
		setupEscenario2();
		
		LlamaArrayList<String> it = trie2.eliminar("Tomate");
		
		assertEquals("No se eliminaron los valores correctos", "e1", it.get(0));
		assertEquals("No se eliminaron los valores correctos", "e2", it.get(1));

		setupEscenario3();

		assertNull("No debería borrarse", trie.eliminar("Tor"));
	}

	public void testEliminarPrefijo()
	{
		setupEscenario1();

		Iterator<String> it = trie.eliminarPalabrasConPrefijo("Tom");
		
		assertEquals("Hubo un error recuperando el valor", "Toma", it.next());
		assertEquals("Hubo un error recuperando el valor", "Tomar", it.next());
		assertEquals("Hubo un error recuperando el valor", "Tomate", it.next());
		
		assertNull("No debería encontrarse la palabra", trie.buscar("Toma"));
		assertNull("No debería encontrarse la palabra", trie.buscar("Tomar"));
		assertNull("No debería encontrarse la palabra", trie.buscar("Tomate"));


		
		it = trie.eliminarPalabrasConPrefijo("holi");
		assertFalse("No debería retornar ningún valor porque la palabra no existe", it.hasNext());
		

		setupEscenario3();

		it = trie.eliminarPalabrasConPrefijo("Torna");
		assertFalse("No debería haber ningun elemento", it.hasNext());
	}
}
