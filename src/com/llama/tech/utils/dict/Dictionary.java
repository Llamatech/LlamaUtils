package com.llama.tech.utils.dict;

import com.llama.tech.utils.dict.LlamaDict.UnhashableTypeException;
import com.llama.tech.utils.list.LlamaIterator;

public interface Dictionary<K, V>
{
	/**
	 * Retorna el valor que corresponde a la llave dada por parámetro. 
	 * Busca en el hash el valor correspondiente a la llave
	 * @param key llave del valor que se desea
	 * @return valor correspondiente a la llave o null si no lo encuentra
	 */
	 public V getValue(K key);
	 
	 /**
	  * Remueve un valor de la tbala de hash dada su llave
	  * @param key llave que corresponde al valor que se desea eliminar
	  * @return valor eliminado, null si no se encontró
	  */
	 public V removeEntry(K key);
	 
	 /**
	  * Agrega una nueva entrada al diccionario
	  * @param key Llave del elemento a agregar
	  * @param value valor a agregar en la tabla
	  * @throws UnhashableTypeException Si se agregan un valor o llaves inválidos
	  */
	 public void addEntry(K key, V value) throws UnhashableTypeException;
	 
	 /**
	  * Reemplaza la entrada de la tabla correspondiente a la llave
	  * dada por parametro por un nuevo valor
	  * @param key llave que da la posicion
	  * @param value valor que desea remplazar al antguo valor
	  * @return valor reemplazado
	  * @throws UnhashableTypeException si el valor dado por parámetro es inválido
	  */
	 public V setEntry(K key, V value) throws UnhashableTypeException;
	 
	 /**
	  * Retorna un iterador sobre las llaves de la tabla
	  * @return iterador de las llaves
	  */
	 public LlamaIterator<K> getKeys();
	 
	 /**
	  * Retorna un iterador sobre los valores de la tabla
	  * @return iterador de valores
	  */
	 public LlamaIterator<V> getValues();
	 
	 /**
	  * Retorna los valores del área principal en un string concatenado
	  * @return String con valores del área principal.
	  */
	 public String toString();

	 /**
	  * Retorna el total de entradas agregadas
	  * @return total de entradas 
	  */
	 public int size();
	 
}