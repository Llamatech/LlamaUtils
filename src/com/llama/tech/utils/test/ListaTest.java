package com.llama.tech.utils.test;

import com.llama.tech.utils.list.Lista;
import com.llama.tech.utils.list.ListaDoblementeEnlazada;

import junit.framework.TestCase;

/**
 * Esta es la clase usada para verificar que los metodos de las listas  funcionen correctamente
 */
public abstract class ListaTest extends TestCase {
	// -----------------------------------------------------------------
	// Atributos
	// -----------------------------------------------------------------

	/**
	 * Es la clase donde se har�n las pruebas
	 */
	private Lista<Integer> lista;

	// -----------------------------------------------------------------
	// Metodos
	// -----------------------------------------------------------------

	/**
	 * Crea la lista
	 */
	public abstract Lista<Integer> crearNuevaLista();
	
	/**
	 * Crea el escenario con una lista vacía
	 */
	public void setUpEscenario1()
	{
		lista = crearNuevaLista();
	}

	/**
	 * Crea el escenario con una lista con más de un elemento
	 */
	public void setUpEscenario2()
	{
		lista = crearNuevaLista();
		for(int i = 0; i < 6; i++)
		{
			lista.addAlFinal(i);
		}
		System.out.println(lista);
	}

	/**
	 * Crea el escenario con una lista con un elemento
	 */
	public void setUpEscenario3()
	{
		lista = crearNuevaLista();
		lista.addAlFinal(8);
	}


	/**
	 * Prueba de adición de elementos al principio de la lista
	 * <b> M�todos a probar: </b> <br>
	 * addAlPrincipio. <br>
	 * <b> Objetivo: </b> Probar que el m�todo addAlPrincipio() agrega correctamente elementos a la lista. <br>
	 * <b> Resultados esperados: </b> <br>
	 * El elemento debería agregarse correctamente en la primera posición en todos los casos y retorna true
	 */
	public void testAddAlPrincipio()
	{
		//se prueba el metodo con una lista vacía
		setUpEscenario1();
		boolean agrego = lista.addAlPrincipio(98);
		assertEquals("El método debería retornar true", true, agrego);
		assertEquals("El elemento no se agregó en la primera posición", 98, (int)lista.get(0));

		//Se prueba el metodo con una lista no vacia
		setUpEscenario2();
		agrego = lista.addAlPrincipio(98);
		assertEquals("El método debería retornar true", true, agrego);
		assertEquals("El elemento no se agregó en la primera posición", 98, (int)lista.get(0));
	}

	/**
	 * Prueba de adición de elementos al final de la lista
	 * <b> M�todos a probar: </b> <br>
	 * addAlFinal. <br>
	 * <b> Objetivo: </b> Probar que el m�todo addAlFinal() agrega correctamente elementos a la lista. <br>
	 * <b> Resultados esperados: </b> <br>
	 * El elemento debería agregarse correctamente en la ultima posición en todos los casos, y retorna true 
	 */
	public void testAddAlFinal()
	{
		//se prueba el metodo con una lista vacia
		setUpEscenario1();
		boolean agrego = lista.addAlFinal(98);
		assertEquals("El método debería retornar true", true, agrego);
		assertEquals("El elemento no se agregó en lap primera posición", 98, (int)lista.get(lista.size()-1));

		//Se prueba el metodo con una lista no vacia
		setUpEscenario2();
		agrego = lista.addAlFinal(98);
		assertEquals("El método debería retornar true", true, agrego);
		assertEquals("El elemento no se agregó en lap primera posición", 98, (int)lista.get(lista.size()-1));
	}

	/**
	 * Prueba de adición de elementos al despues de un elemento dado
	 * <b> M�todos a probar: </b> <br>
	 * add. <br>
	 * <b> Objetivo: </b> Probar que el m�todo add(elementoI, elementoAgregar) agrega correctamente elementos a la lista. <br>
	 * <b> Resultados esperados: </b> <br>
	 * El elemento debería agregarse correctamente despues del elemento dado y retorna true, en caso de que no exista, retorna false.
	 */
	public void testAddDespuesDe()
	{
		//Se prueba el metodo con una lista que contiene el elemento dado
		setUpEscenario2();
		int tam = lista.size();
		boolean agrego = lista.add((Integer)2,(Integer) 9);
		assertEquals("El metodo deberia retornar true", true, agrego);
		assertEquals("El elemento no se agregó a la lista", tam+1, lista.size());
		assertEquals("El metodo no agregó el elemento en la posición correcta", (Integer)9, lista.get(3));

		//Se prueba el metodo con una lista que no contiene el elemento dado
		setUpEscenario3();
		tam = lista.size();
		agrego = lista.add((Integer)2,(Integer) 9);
		assertEquals("El metodo deberia retornar false", false, agrego);
		assertEquals("El elemento no se deberia agregar a la lista", tam, lista.size());

	}

	/**
	 * Prueba de adición de elementos según una posición dada
	 * <b> M�todos a probar: </b> <br>
	 * add. <br>
	 * <b> Objetivo: </b> Probar que el m�todo addAlFinal(pos, elemento) agrega correctamente elementos a la lista. <br>
	 * <b> Resultados esperados: </b> <br>
	 * El elemento debería agregarse correctamente en la posición dadasi esta no se sale de los límites y retornar true.
	 * En caso contrario, dbeería lanzar una excepción
	 */
	public void testAddPosicion()
	{
		//Se prueba el caso de una lista no vacía donde existe la posición
		setUpEscenario2();
		int tam = lista.size();
		boolean agrego = lista.add(2,(Integer) 9);
		assertEquals("El metodo deberia retornar true", true, agrego);
		assertEquals("El elemento no se agregó a la lista", tam+1, lista.size());
		assertEquals("El metodo no agregó el elemento en la posición correcta", (Integer)9, lista.get(2));

		//Se prueba el caso de una lista vacía
		setUpEscenario1();
		try{
			lista.add(6, (Integer)9);
			fail("El metodo debería lanzar una excepcion");
		}
		catch(Exception e){

			//Se prueba el caso de una lista no vaía con una posición que no existe
			setUpEscenario3();
			try{
				lista.add(6, (Integer)8);
				fail("El método debería lanzar una excepción");
			}
			catch(Exception ex)
			{

			}
		}
	}

	/**
	 * Prueba de borrar todos los elementos de una lista
	 * <b> M�todos a probar: </b> <br>
	 * clear. <br>
	 * <b> Objetivo: </b> Probar que el m�todo clear() borra correctamente la lista. <br>
	 * <b> Resultados esperados: </b> <br>
	 * La lista no tiene elementos.
	 */
	public void testClear()
	{
		//Prueba el método con una lista vacía
		setUpEscenario1();
		assertEquals("La longitud debería ser cero", 0, lista.size());
		assertEquals("El primer elemento debería ser null", null, lista.getFirst());
		assertEquals("El último elemento debería ser null", null, lista.getLast());

		//Prueba el método con una lista no vacía
		setUpEscenario2();
		assertEquals("La longitud de la lista está incorrecta", 6, lista.size());
		assertNotNull("El primer elemento no debería ser null", lista.getFirst());
		assertNotNull("El último elemento debería ser null", lista.getLast());
	}

	/**
	 * Prueba de encontrar si hay o no un elemento en la lista
	 * <b> M�todos a probar: </b> <br>
	 * contains. <br>
	 * <b> Objetivo: </b> Probar que el m�todo contains() contesta correctamente a si hay o no un elemento en la lista<br>
	 * <b> Resultados esperados: </b> <br>
	 * El metodo devuelve true si el metodo esta false de lo contrario
	 */
	public void testContains()
	{
		//Prueba el método con una lista vacía
		setUpEscenario1();
		boolean esta = lista.contains((Integer)6);
		assertEquals("El metodo deberia retornar false porque la lista esta vacía", false, esta);

		//Prueba el método con una lista donde se encuentra el elemento
		setUpEscenario2();
		esta = lista.contains((Integer)2);
		assertEquals("El metodo deberia retornar true", true, esta);

		//Prueba el método con una lista donde no se encuentra el elemento
		setUpEscenario3();
		esta = lista.contains((Integer)66);
		assertEquals("El metodo deberia retornar false porque la lista esta vacía", false, esta);
	}

	/**
	 * Prueba de conseguir un elemento en una posición dada
	 * <b> M�todos a probar: </b> <br>
	 * get. <br>
	 * <b> Objetivo: </b> Probar que el m�todo get(pos) devuelve el elemento correcto. <br>
	 * <b> Resultados esperados: </b> <br>
	 * El metodo debe devolver el elemento correcto si la posición esta dentro de los límites.
	 * De lo contrario, se lanza una excepcion.
	 */
	public void testGet()
	{
		//Prueba el método en una lista vacía
		try{
			setUpEscenario1();
			lista.get(3);
			fail("El metodo debería lanzar una excepción");
		}
		catch(Exception e){

			//Prueba el método con una lista no vacía que se pasa del límite
			try{
				setUpEscenario3();
				lista.get(5);
				fail("El metodo deberia lanzar una excepción");
			}
			catch(Exception ex){
				//Prueba el método con una lista no vacía
				setUpEscenario2();
				Integer num = lista.get(2);
				assertEquals("No retorna el elemento esperado", (Integer)2, num);
			}

		}
	}

	/**
	 * Prueba de conseguir el primer elemento de la lista
	 * <b> M�todos a probar: </b> <br>
	 * getFirst. <br>
	 * <b> Objetivo: </b> Probar que el m�todo getFirst() consigue correctamente el primer elemento  <br>
	 * <b> Resultados esperados: </b> <br>
	 * El metodo devuelve el primer elemento si este existe.
	 * De lo contrario, retorna null
	 */
	public void testGetFirst()
	{
		//Se prueba el método con una lista vacía
		setUpEscenario1();
		Integer el = lista.getFirst();
		assertEquals("El metodo beria retornar null", null, el);

		//Se prueba el método con una lista no vacía
		setUpEscenario2();
		el = lista.getFirst();
		assertEquals("El metodo beria retornar el primer elemento", (Integer)0	, el);
	}

	/**
	 * Prueba de conseguir el ultimo elemento de la lista
	 * <b> M�todos a probar: </b> <br>
	 * getLast. <br>
	 * <b> Objetivo: </b> Probar que el m�todo getLast() consigue correctamente el ultimo elemento de la lista  <br>
	 * <b> Resultados esperados: </b> <br>
	 * Si hay ultimo elemento, se devuelve.
	 * De lo contrario, se lanza excepcion
	 */
	public void testGetLast()
	{
		//Se prueba el método con una lista vacía
		setUpEscenario1();
		Integer el = lista.getLast();
		assertEquals("El metodo beria retornar null", null, el);

		//Se prueba el método con una lista no vacía
		setUpEscenario2();
		el = lista.getLast();
		assertEquals("El metodo beria retornar el primer elemento", (Integer)5	, el);
	}

	/**
	 * Prueba de eliminar el primer elemento de la lista
	 * <b> M�todos a probar: </b> <br>
	 * removeFirst. <br>
	 * <b> Objetivo: </b> Probar que el m�todo removeFirst() remueve correctamente el primer elemento de la lista  <br>
	 * <b> Resultados esperados: </b> <br>
	 * Se remueve el primer elemento. Si este no existe, se lanza excepción.
	 */
	public void testRemoveFirst()
	{
		//Se prueba con la lista vacía
		setUpEscenario1();
		Integer res = null;
		try
		{
			res= lista.removeFirst();
			fail("El método debería fallar porque no hay elementos que remover");

		}
		catch(Exception e)
		{

			//Se prueba con unico elemento
			setUpEscenario3();
			try{
				res = lista.removeFirst();
				assertEquals("El método debería retornar el elemento eliminado", 8, (int)res);
				assertEquals("La lista debería quedar vacía", 0, lista.size());

				//se prueba con múltiples elementos
				setUpEscenario2();
				res = lista.removeFirst();
				assertEquals("El método debería retornar el elemento eliminado", 0, (int)res);
			}
			catch(Exception ex){
				fail("El método no debería fallar");
			}
		}

	}

	/**
	 * Prueba de conseguir el ultimo indice de un elemento dado
	 * <b> M�todos a probar: </b> <br>
	 * lastIndexOf. <br>
	 * <b> Objetivo: </b> Probar que el m�todo lastIndexOf() da el último índice de un elemento dado  <br>
	 * <b> Resultados esperados: </b> <br>
	 * Se retorna el último indice de un elemento dado.
	 * Si no se encuentra el elemento en la lista, se retorna -1
	 */
	public void testLastIndexOf()
	{
		//Se prueba el método con una lista vacía
		setUpEscenario1();
		int pos = lista.lastIndexOf((Integer)9);
		assertEquals("El metodo deberia retornar -1 porque la lista esta vacia", -1, pos);

		//se prueba el método con una lista no vacía donde no esta el elemento
		setUpEscenario2();
		pos = lista.lastIndexOf((Integer)87);
		assertEquals("El metodo deberia retornar -1 porque el elemento no esta en la lista", -1, pos);

		//Se prueba el método con una lista no vacía donde esta el elemento repetido
		setUpEscenario2();
		lista.addAlFinal((Integer)1);
		pos = lista.lastIndexOf((Integer)1);
		assertEquals("El metodo devuelve la posición equivocada", 6, pos);
	}

	/**
	 * Prueba de saber si la lista esta vacía
	 * <b> M�todos a probar: </b> <br>
	 * isEmpty. <br>
	 * <b> Objetivo: </b> Probar que el m�todo isEmpty() contesta correctamente si una lista esta vacía o no  <br>
	 * <b> Resultados esperados: </b> <br>
	 * El metodo devuelve true si la lista esta vacía, false de lo contrario
	 */
	public void testIsEmpty()
	{
		//Se prueba en una lista vacía
		setUpEscenario1();
		boolean vacio = lista.isEmpty();
		assertEquals("La lista debería estar vacía", true, vacio);

		//se prueba con una lista no vacía
		setUpEscenario2();
		vacio = lista.isEmpty();
		assertEquals("La lista no debería retornar vacía", false, vacio);
	}


	/**
	 * Prueba de eliminar el ultimo elemento
	 * <b> M�todos a probar: </b> <br>
	 * removeLast. <br>
	 * <b> Objetivo: </b> Probar que el m�todo removeLast() elimina el ultimo elemento  <br>
	 * <b> Resultados esperados: </b> <br>
	 * Si la lista no esta vacía, elimina el elemento. De estarlo, lanza excepción.
	 */
	public void testRemoveLast()
	{
		//Se prueba con una lista vacía
		setUpEscenario1();
		Integer res = null;
		try
		{
			res= lista.removeLast();
			fail("El método debería fallar porque no hay elementos que remover");

		}
		catch(Exception e)
		{
			//se prueba con una lista de un único elemento
			setUpEscenario3();
			try{
				res = lista.removeLast();
				assertEquals("El método debería retornar el elemento eliminado", 8, (int)res);
				assertEquals("La lista debería quedar vacía", 0, lista.size());

				//Se prueba con una lista de múltiples elementos
				setUpEscenario2();
				res = lista.removeLast();
				assertEquals("El método debería retornar el elemento eliminado", 5, (int)res);
			}
			catch(Exception ex){
				fail("El método no debería fallar");
			}
		}

	}

	/**
	 * Prueba de eliminar un elemento dado
	 * <b> M�todos a probar: </b> <br>
	 * remove. <br>
	 * <b> Objetivo: </b> Probar que el m�todo remove(elemento) elimina correctamente el elemento dado  <br>
	 * <b> Resultados esperados: </b> <br>
	 * Si el elemento existe, se elimina y se retorna.
	 * De lo contrario, se retorna null.
	 */
	public void testRemove()
	{
		//Se prueba el metodo con la lista vacía
		setUpEscenario1();
		Integer res = null;

		res= lista.remove((Integer)9);
		assertEquals("El metodo debería retornal null porque no hay elementos que eliminar", null, res);


		//Se prueba el método con una lista no vacía donde no esta el elemento dado
		setUpEscenario2();
		res=lista.remove((Integer)9);
		assertEquals("El método debería retornar null", null, res);

		//Se prueba el método con una lista no vacía donde el elemento dado es el primero
		int tam = lista.size();
		Integer el = lista.get(0);
		Integer ell = lista.get(1);
		res = lista.remove((Integer)0);
		assertEquals("El metodo debería retornar el primer elemento", el, res);
		assertEquals("El primer elemento no es el correcto", ell, lista.get((int)0));
		assertEquals("Debería haber un elemento menos", tam-1, lista.size());

		//Se prueba el método con una lista no vacía donde el elemento dado es el ultimo
		setUpEscenario2();

		tam = lista.size();
		el = lista.getLast();
		ell = lista.get(lista.size()-2);
		res=lista.remove((Integer)5);
		assertEquals("El metodo debería retornar el ultimo elemento", el, res);
		assertEquals("El ultimo elemento no es el correcto", ell, lista.get(lista.getLast()));
		assertEquals("Debería haber un elemento menos", tam-1, lista.size());


		//Se prueba el método con una lista no vacía donde el elemento dado esta el en medio
		setUpEscenario2();

		tam = lista.size();
		el = lista.get(3);
		res=lista.remove((Integer)3);
		assertEquals("El metodo debería retornar el elemento eliminado", el, res);
		assertEquals("Debería haber un elemento menos", tam-1, lista.size());




	}

	/**
	 * Prueba de remover un elemento por su posicion
	 * <b> M�todos a probar: </b> <br>
	 * remove. <br>
	 * <b> Objetivo: </b> Probar que el m�todo remove(pos) remueve correctamente el elemento en la posición dada <br>
	 * <b> Resultados esperados: </b> <br>
	 * Si la posición esta dentro del límite, se elimina y retorna el elemento
	 * De lo contrario, se lanza excepción
	 */
	public void testRemovePos()
	{
		//se prueba el método con una lista vacía
		try{
			setUpEscenario1();
			lista.get(0);
			fail("El metodo deberia lanzar una excepion porque la lista esta vacia");
		}
		catch (Exception e)
		{
			//Se prueba el metodo con una lista no vacia y una posicion inexistente
			try{
				setUpEscenario3();
				lista.get(99);
				fail("El metodo debería fallar porque no existe esa posicion");
			} catch (Exception ex){

				//Se prueba el metodo con una lista no vacia y una posicion existente
				setUpEscenario2();
				Integer el = lista.get(2);
				assertEquals("El metodo no retorna el elemento correcto", (Integer)2,el);
			}
		}

	}

	/**
	 * Prueba de reemplazar un elemento en una posicion
	 * <b> M�todos a probar: </b> <br>
	 * set. <br>
	 * <b> Objetivo: </b> Probar que el m�todo set() reemplaza correctamente un elemento en la posicion dada por el elemento dado  <br>
	 * <b> Resultados esperados: </b> <br>
	 * Si la posicion esta dentro del limite, reemplaza el elemento y retorna el elemento reemplazado
	 * De lo contrario, lanza una excepcion
	 */
	public void testSet()
	{
		//Se prueba el método con una lista vacía
		try{
			setUpEscenario1();
			lista.set(0, (Integer)5);
			fail("El metodo debería lanzar una excepcion");
		}catch(Exception e){

			//Se prueba el método con una lista no vacía y una posición inexistente
			try{
				setUpEscenario3();
				lista.add(17, (Integer)9);
				fail("El metodo debería lanzar una excepcion");
			}
			catch(Exception ex){

				//Se prueba el método con una lista no vacía y una posición existente
				setUpEscenario2();
				int tam = lista.size();
				Integer ant = lista.get(3);
				Integer el = lista.set(3, (Integer)17);
				assertEquals("El elemento no se insertó en el lugar debido", (Integer)17, lista.get(3));
				assertEquals("El metodo debería retornar el elemento eliminado" , ant, el);
				assertEquals("El tamaño de la lista no debería cambiar", tam, lista.size());
			}
		}
	}

	/**
	 * Prueba de retornar el tamaño de la lista
	 * <b> M�todos a probar: </b> <br>
     * size. <br>
     * <b> Objetivo: </b> Probar que el m�todo size() devuelve correctamente el tamño de la lista  <br>
     * <b> Resultados esperados: </b> <br>
     * El metodo devuelve el tamaño correcto
	 */
	public void testSize()
	{
		//Se prueba el método con la lista vacía
		setUpEscenario1();
		int tam = lista.size();
		assertEquals("El tamaño esta equivocado", 0, tam); 

		//Se prueba el método con la lista no vacía
		setUpEscenario2();
		tam = lista.size();
		assertEquals("El tamaño esta equivocado", 6, tam); 

		//Se prueba el método con la lista  d3 1 elemento
		setUpEscenario3();
		tam = lista.size();
		assertEquals("El tamaño esta equivocado", 1, tam); 

	}

	/**
	 * Prueba de buscar un elemento dado
	 * <b> M�todos a probar: </b> <br>
     * buscar. <br>
     * <b> Objetivo: </b> Probar que el m�todo buscar() retorna correctamente el elemento buscado  <br>
     * <b> Resultados esperados: </b> <br>
     * Si el elemento está en la lista, se retorna
     * De lo contrario, se retorna null
	 */
	public void testBuscar()
	{
		setUpEscenario1();
		Integer res = lista.buscar((Integer)54);
		assertEquals("El metodo deberia retornar null porque la lista esta vacia", null, res);

		setUpEscenario2();
		res = lista.buscar((Integer)4);
		assertEquals("El metodo deberia retornar el elemento buscado", (Integer)4, res);

		setUpEscenario2();
		res= lista.buscar((Integer)12);
		assertEquals("El metodo deberia retornar null porque el elemento no se encuentra en la lista", null, res);
	}

	/**
	 * Prueba de conocer la posiciñon de un elemento
	 * <b> M�todos a probar: </b> <br>
     * indexOf. <br>
     * <b> Objetivo: </b> Probar que el m�todo indexOf() retorna correctamente el indice de un elemento  <br>
     * <b> Resultados esperados: </b> <br>
     * Si el elemento esta en la lista se retorna su posicion
     * De lo contrario, se retorna -1.
	 */
	public void testIndexOf()
	{
		//Se prueba el método con una lista vacía
		setUpEscenario1();
		int pos = lista.indexOf((Integer)9);
		assertEquals("El metodo deberia retornar -1 porque la lista esta vacia", -1, pos);

		//se prueba el método con una lista no vacía donde no esta el elemento
		setUpEscenario2();
		pos = lista.indexOf((Integer)87);
		assertEquals("El metodo deberia retornar -1 porque el elemento no esta en la lista", -1, pos);

		//Se prueba el método con una lista no vacía donde esta el elemento
		pos = lista.indexOf((Integer)3);
		assertEquals("El metodo devuelve la posición equivocada", 3, pos);

	}

}
