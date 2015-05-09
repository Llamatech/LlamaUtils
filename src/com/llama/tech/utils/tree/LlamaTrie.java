/*
 * LlamaTrie.java
 * This file is part of LlamaUtils
 *
 * Copyright (C) 2015 - LlamaTech Team 
 *
 * LlamaUtils is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 *
 * LlamaUtils is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with LlamaUtils. If not, see <http://www.gnu.org/licenses/>.
 */

package com.llama.tech.utils.tree;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Iterator;

import co.edu.uniandes.estructuras.trie.ITrie;

import com.llama.tech.misc.LlamaText;
import com.llama.tech.misc.XMLFormat;
import com.llama.tech.utils.dict.LlamaDict;
import com.llama.tech.utils.dict.LlamaDict.UnhashableTypeException;
import com.llama.tech.utils.list.Lista;
import com.llama.tech.utils.list.LlamaArrayList;

public class LlamaTrie<T> extends XMLFormat
{	
	public TrieNode<T> root;
	private LlamaDict<Character,String> distinguished_sym = new LlamaDict<Character,String>(20);

	public LlamaTrie()
	{
		root = new TrieNode<T>();
			distinguished_sym.addEntry('.', "Dot");
			distinguished_sym.addEntry(',', "Comma");
			distinguished_sym.addEntry(';', "Semi-Colon");

	}

	public void agregarT(String word, T elem)
	{
		root.agregar(word, elem);	
	}

	//	public boolean agregar(String word, Iterator<T> elems)
	//	{
	//		return root.agregar(word, elems);	
	//	}

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
		
		File f = new File("./data/misc/diccionario.dic");
		String text = "";
		try 
		{
			FileInputStream fis = new FileInputStream(f);
			try(BufferedReader br = new BufferedReader(new InputStreamReader(fis, "UTF8")))
			{
				String line;
				while((line = br.readLine()) != null)
				{
					trie.agregar(line, line);
				}
			} 
			catch (IOException e) 
			{
				
			}
			
			
			f = new File("./data/misc/a1.txt.ocr");
			fis = new FileInputStream(f);
			try(BufferedReader br = new BufferedReader(new InputStreamReader(fis, "UTF8")))
			{
				String line;
				while((line = br.readLine()) != null)
				{
					text += line;
				}
			} 
			catch (IOException e) 
			{
				e.printStackTrace();
			}
			
		} 
		catch (FileNotFoundException e) 
		{
			e.printStackTrace();
		}
		
				LlamaArrayList<String> list = trie.text_segmentation(text, true);
				for(String s: list)
				{
					
				}
		try 
		{
			trie.writeXML("./data/misc/trie.xml");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

	public T eliminar(String word) 
	{
		return root.eliminar(word);
	}


	public T buscar(String word)
	{
		return root.buscar(word	, 0);
	}


	public Iterator<String> eliminarPalabrasConPrefijo(String p)
	{
		Lista<String> lista = new LlamaArrayList<String>(50);
		root.eliminarPrefijo(lista, "", p, 0, false);
		return new LlamaTrieIterator<String>(lista);
	}

	
	public void agregar(String arg0, T arg1) 
	{
		agregarT(arg0, arg1);

	}

	public Iterator<String> buscarPalabrasConPrefijo(String p) {
		Lista<String> lista = new LlamaArrayList<String>(50);
		root.buscarPrefijo(p, lista, 0, false, "");
		return lista.iterator();
	}

	public int darNumeroPalabras() 
	{
		return root.getSize();
	}

	public LlamaArrayList<String> text_segmentation(String text, boolean flag)
	{
		LlamaArrayList<String> l = new LlamaArrayList<String>(10);
		LlamaArrayList<String> bif_tree = new LlamaArrayList<String>(10);
		
		LlamaArrayList<String> text_p = preProcess_text(text, flag);
		
		
		String gen_text = "";

		if(flag)
		{
			for(String fragment: text_p)
			{
				if(fragment != "")
				{
					if(distinguished_sym.getValue(fragment.charAt(0)) != null)
					{
						gen_text += fragment;
//						if(bif_tree.size() == 0)
//						{
//							bif_tree.addAlFinal(fragment);	
//						}
//						else
//						{
//							for(int i = 0; i < bif_tree.size(); i++)
//							{
//								bif_tree.set(i, bif_tree.get(i)+fragment);
//							}
//						}
					}
					else
					{
						l.clear();
						break_words(fragment, fragment.length(), l, "");
						if(l.size() > 0)
						{
							
							gen_text += l.get(l.size()/2);
//							if(bif_tree.size() == 0)
//							{
//								bif_tree = l.clone();
//							}
//							else
//							{
//								LlamaArrayList<String> copy = bif_tree.clone();
//								for(int i = 0; i < bif_tree.size(); i++)
//								{
//									bif_tree.set(i, bif_tree.get(i)+l.get(0));
//								}
//								for(int i = 1; i < l.size(); i++)
//								{
//									for(String preRes: copy)
//									{
//										bif_tree.addAlFinal(preRes+l.get(i));
//									}
//								}
//								
//							}
						}
						else
						{
							//throw new LlamaText.TextSegmentationException(
							//		  String.format(
							//		  "El fragmento: %s, no contiene ninguna palabra válida",
							//		  fragment));
						}
					}
					
				}
			}
			
			
			gen_text.replace(",", ", ");
			gen_text.replace(".", ". ");
			gen_text.replace(";", "; ");
			
			bif_tree.addAlFinal(gen_text);
		}
		else
		{
			break_words(text_p.get(0), text_p.get(0).length(), bif_tree, "");
		}

		//break_words(text, text.length(), l, "");
		return bif_tree;

	}

	public LlamaArrayList<String> preProcess_text(String text, boolean spacing)
	{
		char[] common_char = {',', ';'};//, '¿', '?', '¡', '!', '«', '»',' ', '(', ')', '-'};
		LlamaArrayList<String> preProc;
		if(spacing)
		{
			preProc = LlamaText.splitItemBy(text, '.');
			for(Character c: common_char)
			{
				preProc = LlamaText.splitItemsBy(preProc, c);
			}
		}
		else
		{
			preProc = new LlamaArrayList<String>(1);
			char[] split_sym = {'.', ',', ';'};
			String[] split_arr;
			for(Character c: split_sym)
			{
				split_arr = text.split("["+c+"]");
				if(split_arr.length > 1)
				{
					text = "";
					for(String s: split_arr)
					{
						text += s;
					}
				}
			}
			
			preProc.addAlFinal(text);
			
		}
		return preProc;

	}

	private void break_words(String text, int size, LlamaArrayList<String> l, String result)
	{
		//
		for(int i = 1; i <= size; i++)
		{
			String prefix = text.substring(0, i);
			
			if(prefix.contains("|"))
			{
				int index = prefix.indexOf("|");
				String pPrefix = prefix.substring(0, index);
				String probs =  buscarHijosPrefijo(pPrefix);
				for(int j = 0; j < probs.length(); j++)
				{
					String nPrefix = prefix.replace('|', probs.charAt(j));
					if(buscar(nPrefix) != null)
					{
						    
							if(i == size)
							{
								result += nPrefix;
								l.addAlFinal(result);
								break;
							}
							break_words(text.substring(i), size-i, l, result+nPrefix+" ");						
					}
				}
			}
			
			
			if(buscar(prefix) != null)
			{
				if(i == size)
				{
					
					result += prefix;
					l.addAlFinal(result);
					break;
				}
				break_words(text.substring(i), size-i, l, result+prefix+" ");
			}
		}
	}

	public String buscarHijosPrefijo(String p)
	{
		return root.buscarHijosPrefijo(p, 0);
	}

	public Iterator<T> buscarPrefijo(String p)
	{

		Lista<T> lista = new LlamaArrayList<T>(50);
		root.buscarPrefijoElementos(p, lista, 0, false);
		return lista.iterator();

	}

	@Override
	public String toXML() 
	{
		String format = String.format("<Trie numWords = \"%d\">\n", root.getSize());
		return format+root.toXML()+"</Trie>";
	}

	@Override
	public void readXML() 
	{
		// TODO Auto-generated method stub
		
	}  

	//	def text_segmentation(self, text):
	//        l = []
	//        l = self.break_words(text, len(text), l)
	//        return l
	//
	//    def break_words(self, text, size, l = [], result = ""):
	//        for i in range(1, size+1):
	//            prefix = text[0:i]
	//            if self.root[prefix] is not None:
	//               if i == size:
	//                  result += prefix
	//                  l.append(result)
	//                  break
	//               l = self.break_words(text[i:], size-i, l, result+prefix+' ')
	//        return l

}
