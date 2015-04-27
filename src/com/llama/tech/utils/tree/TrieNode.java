/*
 * TrieNode.java
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

import java.util.Iterator;

import com.llama.tech.misc.XMLFormat;
import com.llama.tech.utils.list.Lista;
import com.llama.tech.utils.list.LlamaArrayList;

public class TrieNode<T> extends XMLFormat
{
	private char character;

	private T element;

	private boolean isWord = false;

	private boolean right = false;

	private TrieNode<T> parent;

	private TrieNode<T> pSibling;

	private TrieNode<T> sibling;

	private TrieNode<T> child;

	private int size = 0;

	public TrieNode()
	{
		character = '\0';
	}

	public TrieNode(char character)
	{
		this.character = character;
	}

	public TrieNode(char character, T elem)
	{
		this.character = character;
		isWord = true;
		element = elem;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void agregar(String word, T elem)
	{
		TrieNode<T> node = this;
		int index = 0;
		while(index < word.length())
		{
			TrieNode<T> childS = node.getSibling(Character.toLowerCase(word.charAt(index)));
			if(childS != null)
			{
				node = childS;
				index++;
			}
			else
			{
				break;
			}
		}


		while(index < word.length())
		{
			node = node.appendNode(Character.toLowerCase(word.charAt(index)));
			index++;
		}

		if(node.isWord)
		{
			if(node.element instanceof Lista)
			{
				((Lista) node.element).addAll((Lista) elem);
			}
			else
			{
				node.element = elem;
				node.isWord = true;
			}

		}
		else
		{
			node.element = elem;
			node.isWord = true;
			size++;
		}


	}

	private TrieNode<T> appendNode(char c)
	{
		TrieNode<T> nNode = new TrieNode<T>(c);
		if(child == null)
		{
			child = nNode;
			child.parent = this;
			return child;
		}
		else
		{
			TrieNode<T> sib = getGreaterSibling(c);

			if(!sib.right)
			{
				if (sib.parent != null) 
				{
					nNode.parent = sib.parent;
					nNode.parent.child = nNode;
					sib.parent = null;

				}

				nNode.pSibling = sib.pSibling;
				if (nNode.pSibling != null) 
				{
					nNode.pSibling.sibling = nNode;
				}
				nNode.sibling = sib;
				sib.pSibling = nNode;
			}
			else
			{
				sib.right = false;
				nNode.pSibling = sib;
				sib.sibling = nNode;
			}
			return nNode;
		}
	}

	private TrieNode<T> getSibling(char c)
	{     
		if(child == null)
		{
			return null;
		}

		else
		{
			TrieNode<T> sibling = this.child;
			while(sibling != null)
			{
				if(sibling.character == c)
				{
					break;
				}
				else
				{
					sibling = sibling.sibling;
				}
			}

			return sibling;

		}
	}


	private TrieNode<T> getGreaterSibling(char c)
	{
		TrieNode<T> sibling = this.child;
		TrieNode<T> lastVSibling = this.child;
		while(sibling != null)
		{
			if(sibling.character > c)
			{
				break;
			}
			else
			{
				lastVSibling = sibling;
				sibling = sibling.sibling;
			}
		}

		if(sibling == null)
		{
			if(lastVSibling.character < c)
			{
				lastVSibling.right = true;
			}
			return lastVSibling;
		}

		return sibling;

	}

	public T eliminar(String word)
	{
		TrieNode<T> node = this;
		boolean found = true;
		int i = 0;
		while(i < word.length())
		{
			node = node.getSibling(Character.toLowerCase(word.charAt(i)));
			if(node == null)
			{
				found = false;
				break;
			}
			i++;
		}

		if(!found)
		{
			return null;
		}

		if(node.isWord)
		{
			T data = node.element;
			node.removeNode();
			size--;
			return data;
		}
		else
		{
			return null;
		}

	}

	private void removeNode() 
	{

		if(child != null)
		{
			isWord = false;
			element=null;
		}
		else
		{
			if(sibling != null)
			{
				TrieNode<T> prev = pSibling;
				TrieNode<T> next = sibling;
				if(prev != null)
				{
					prev.sibling = next;
				}
				if(next != null)
				{
					next.pSibling = prev;
				}
				if(parent != null)
				{
					parent.child = prev;
					if(prev != null)
					{
						prev.parent = parent;
					} 	
				}
			}
			else
			{
				//Orphan
				if(parent != null)
				{
					TrieNode<T> p = parent;
					parent.child = null;
					parent = null;
					p.removeNode();
				}
				else
				{
					pSibling.sibling = sibling;
					if(sibling != null)
					{
						sibling.pSibling = pSibling;
					}
					sibling = null;
					pSibling = null;
				}
			}

		}

	}

	public void eliminarPrefijo(Lista<String> lista, String wordWIP, String word, int charNum, boolean b)
	{
		if(character=='\0')
		{
			if (child!=null)
			{
				child.eliminarPrefijo(lista, wordWIP,word,charNum,b);
			}
			else
			{

			}
		}
		else
		{
			if(b)
			{
				wordWIP+=character;
				if(isWord)
				{
					lista.addAlFinal(wordWIP);
				}

				if (child!=null)
				{
					child.eliminarPrefijo(lista, wordWIP, word, charNum+1, b);
				}
				if(sibling!=null)
				{
					sibling.eliminarPrefijo(lista, wordWIP.substring(0,wordWIP.length()-1), word, charNum, b);
				}

				child=null;
				sibling=null;
			}
			else if(character==Character.toLowerCase(word.charAt(charNum)))
			{
				wordWIP+=character;


				if(charNum==word.length()-1)
				{
					b=true;
					if(isWord)
					{
						lista.addAlFinal(wordWIP);
					}

					if(child!=null)
					{
						child.eliminarPrefijo(lista, wordWIP, word, charNum, b);
					}

					if(parent!=null)
					{
						if(sibling!=null)
						{
							//sibling.eliminarPrefijo(lista, wordWIP.substring(0,wordWIP.length()-1), word, charNum, b);
							parent.child=sibling;
						}
						else
						{
							parent.child=null;
						}
					}
					else //if(pSibling!=null)
					{
						if(sibling!=null)
						{
							//sibling.eliminarPrefijo(lista, wordWIP.substring(0,wordWIP.length()-1), word, charNum, b);
							pSibling.sibling=sibling;
						}
						else
						{
							pSibling.sibling=null;
						}
					}

				}
				else
				{
					if(child!=null)
						child.eliminarPrefijo(lista, wordWIP, word, charNum+1, b);
				}
			}
			else
			{
				if(sibling!=null)
					sibling.eliminarPrefijo(lista, wordWIP, word, charNum, b);
			}
		}
	}

	public T buscar(String word, int charNum)
	{
		if(character=='\0')
		{
			if(child!=null)
			{
				return child.buscar(word, charNum);
			}
			else
			{
				return null;
			}
		}
		else
		{
			if(character == Character.toLowerCase(word.charAt(charNum)))
			{
				if(isWord && charNum==word.length()-1)
				{
					return element;
				}
				else if(charNum==word.length()-1)
				{
					return null;
				}
				else if(child!=null)
				{
					return child.buscar(word,charNum+1);
				}
				else
				{
					return null;
				}

			}
			else
			{
				if(character > Character.toLowerCase(word.charAt(charNum))||sibling==null)
				{
					return null;
				}
				else
				{
					return sibling.buscar(word,charNum);
				}

			}
		}
	}


	public void buscarPrefijo(String p, Lista<String> lista, int charNum, boolean b, String wordWIP)
	{
		if(character=='\0')
		{
			if(child!=null)
			{
				child.buscarPrefijo(p, lista, charNum,false,wordWIP);
			}

		}
		else
		{
			if(b)
			{
				wordWIP+=character;
				if(isWord)
				{
					lista.addAlFinal(wordWIP);
					b=true;
				}
				if(child!=null)
				{
					child.buscarPrefijo(p, lista, charNum+1, b, wordWIP);
				}
				if(sibling!=null)
				{
					sibling.buscarPrefijo(p, lista, charNum, b, wordWIP.substring(0, wordWIP.length()-1));
				}

			}
			else if(character==Character.toLowerCase(p.charAt(charNum)))
			{
				wordWIP+=character;
				if(p.length()-1==charNum)
				{
					b=true;
				}
				if(child !=null)
					child.buscarPrefijo(p, lista, charNum+1, b, wordWIP);
			}
			else
			{

				if(sibling!=null&&character<Character.toLowerCase(p.charAt(charNum)))
					sibling.buscarPrefijo(p, lista, charNum, b, wordWIP);
			}
		}
	}




	public TrieNode<T> getChild()
	{
		return child;
	}

	public boolean hasSiblings()
	{
		return sibling != null;
	}

	public TrieNode<T> getSibling() 
	{
		return sibling;
	}

	@Override
	public String toString()
	{
		return String.format("Character: %c ; Word: %s ; Parent: %c", character, isWord, parent != null ? parent.character : ' ');
	}

	public String wordTree() 
	{
		return getWords("", "");
	}

	private String getWords(String comp, String prefix) 
	{
		String sPrefix = character != '\0'? prefix+character : prefix;

		if(this.isWord)
		{
			comp += prefix+character+", ";
		}

		if(child != null)
		{
			comp = child.getWords(comp, sPrefix);
		}

		if(sibling != null)
		{
			comp = sibling.getWords(comp, prefix);
		}

		return comp;
	}

	public char getCharacter()
	{
		return character;
	}

	public int getSize()
	{
		return size;
	}

	public String buscarHijosPrefijo(String p, int charNum)
	{
		//System.out.println(p);
		if(p.equals(""))
		{
			if(child!=null)
				return getTodosMisHijos();
			else
				return "";
		}
		if(character=='\0')
		{
			if(child!=null)
			{
				return child.buscarHijosPrefijo(p, charNum);
			}
			else
			{
				return "";
			}
		}
		else
		{
			try
			{
				if(character==Character.toLowerCase(p.charAt(charNum)))
				{
	
					if(p.length()-1==charNum)
					{
						if(child!=null)
							return getTodosMisHijos();
						else
							return "";
					}
					else if(child !=null)
						return child.buscarHijosPrefijo(p, charNum+1);
					else
						return"";
				}
				else
				{
	
					if(sibling!=null)
						return sibling.buscarHijosPrefijo(p, charNum);
					else
						return "";
	
				}
			}
			catch(StringIndexOutOfBoundsException s)
			{
				return "";
			}
		}
	}

	public String getTodosMisHijos()
	{
		return child.getHermanos();
	}

	public String getHermanos()
	{
		if (sibling!=null)
		{
			return character+sibling.getHermanos();
		}

		else 
		{
			return character+"";
		}
	}

	public void buscarPrefijoElementos(String p, Lista<T> lista, int charNum, boolean b)
	{
		if(character=='\0')
		{
			if(child!=null)
			{
				child.buscarPrefijoElementos(p, lista, charNum,false);
			}
			else
			{

			}
		}
		else
		{
			if(character==Character.toLowerCase(p.charAt(charNum)))
			{
				if(element instanceof Lista)
					lista.addAll((Lista)element);
				else
					lista.addAlFinal(element);
				if(p.length()-1==charNum)
					b=true;
				else if(child !=null)
					child.buscarPrefijoElementos(p, lista, charNum+1, b);
			}
			else
			{
				if(b)
				{
					if(element instanceof Lista)
						lista.addAll((Lista)element);
					else
						lista.addAlFinal(element);
					if(child!=null)
						child.buscarPrefijoElementos(p, lista, charNum, b);
					if(sibling!=null)
						sibling.buscarPrefijoElementos(p, lista, charNum, b);
				}
				else
				{
					if(sibling!=null)
						sibling.buscarPrefijoElementos(p, lista, charNum, b);
				}
			}
		}
	}

	@Override
	public String toXML() 
	{
		 if(character != '\0')
		 {
		     StringBuilder sb = new StringBuilder();
		     sb.append(String.format(" character = \"%c\" isWord = \"%s\">\n", character, isWord));
		     if(this.isWord)
		     {
		    	 sb.append("<element ");
		    	 if(element instanceof XMLFormat)
		    	 {
		    		 sb.append(">\n");
		    		 sb.append(((XMLFormat) element).toXML());
		    		 sb.append("</element>\n");
		    	 }
		    	 else
		    	 {
		    		 sb.append(String.format("value = \"%s\"/>\n", element.toString()));
		    	 }
		     }
		     if(this.child != null)
		     {
		    	 sb.append("<child");
		    	 sb.append(child.toXML());
		    	 sb.append("</child>\n");
		     }
		     
		     if(this.sibling != null)
		     {
		    	sb.append("<sibling");
		    	sb.append(sibling.toXML());
		    	sb.append("</sibling>\n");
		     }
		     
		     return sb.toString();
		 }
		 else
		 {
			 return (child != null) ? "<child"+child.toXML()+"</child>\n": "\n";
		 }
	}

	@Override
	public void readXML() 
	{
		
	}
	
	



}

