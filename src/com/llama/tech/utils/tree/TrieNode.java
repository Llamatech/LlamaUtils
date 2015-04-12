package com.llama.tech.utils.tree;

import java.util.Iterator;

import com.llama.tech.utils.list.Lista;
import com.llama.tech.utils.list.LlamaArrayList;

public class TrieNode<T> 
{
	private char character;

	private Lista<T> elements = new LlamaArrayList<T>(1);

	private boolean isWord = false;

	private TrieNode<T> sibling;

	private TrieNode<T> child;

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
		elements.addAlFinal(elem);
	}

	public TrieNode(char character, Iterator<T> elems)
	{
		this.character = character;
		isWord = true;
		while(elems.hasNext())
		{
			elements.addAlPrincipio(elems.next());
		}
	}


	public boolean agregar(String word, T elem)
	{

		TrieNode<T> currentNode = child;
		TrieNode<T> lastValidNode = this;

		int index = 0;
		while(currentNode != null)
		{
			if(currentNode.character == word.charAt(index))
			{
				lastValidNode = currentNode;
				currentNode = currentNode.child;
				index++;
			}
			else
			{
				currentNode = currentNode.sibling;
			}
		}

		boolean added = false;
		
		while(index < word.length())
		{
			if(lastValidNode.child == null)
			{
				if(index == word.length()-1)
				{
					lastValidNode.child = new TrieNode<T>(word.charAt(index), elem);
				}
				else
				{
					lastValidNode.child = new TrieNode<T>(word.charAt(index));
				}
				added = true;
				lastValidNode = lastValidNode.child;
			}
			else
			{
				lastValidNode = lastValidNode.child;
				currentNode = lastValidNode;
				TrieNode<T> nextSibling = null;
				while(currentNode != null)
				{
					if(currentNode.sibling != null)
					{
						if(currentNode.sibling.character > word.charAt(index))
						{
							nextSibling = currentNode.sibling;
							currentNode = null;
						}
						else
						{
							currentNode = currentNode.sibling;
							lastValidNode = currentNode;
						}
						
					}
					else
					{
						break;
					}
				}

				if(index == word.length()-1)
				{
					lastValidNode.sibling = new TrieNode<T>(word.charAt(index), elem);
				}
				else
				{
					lastValidNode.sibling = new TrieNode<T>(word.charAt(index));
				}

				added = true;
				lastValidNode = lastValidNode.sibling;
				lastValidNode.sibling = nextSibling;
			}
			
			index++;
		}
		
		if(!added)
		{
			if(lastValidNode.isWord)
			{
					int k = elements.indexOf(elem);
					if(k != -1)
					{
						elements.set(k, elem);
					}
					else
					{
						elements.addAlFinal(elem);
					}
			}
		}

		return true;
	}
	
	
	public boolean agregar(String word, Iterator<T> elems)
	{

		TrieNode<T> currentNode = child;
		TrieNode<T> lastValidNode = this;

		int index = 0;
		while(currentNode != null)
		{
			if(currentNode.character == word.charAt(index))
			{
				lastValidNode = currentNode;
				currentNode = currentNode.child;
				index++;
			}
			else
			{
				currentNode = currentNode.sibling;
			}
		}


		System.out.println("Break at: "+lastValidNode+"; Current Char: "+word.charAt(index));

		boolean added = false;
		
		while(index < word.length())
		{
			if(lastValidNode.child == null)
			{
				if(index == word.length()-1)
				{
					lastValidNode.child = new TrieNode<T>(word.charAt(index), elems);
				}
				else
				{
					lastValidNode.child = new TrieNode<T>(word.charAt(index));
				}
				added = true;
				lastValidNode = lastValidNode.child;
			}
			else
			{
				lastValidNode = lastValidNode.child;
				currentNode = lastValidNode;
				TrieNode<T> nextSibling = null;
				while(currentNode != null)
				{
					if(currentNode.sibling != null)
					{
						if(currentNode.sibling.character > word.charAt(index))
						{
							nextSibling = currentNode.sibling;
							currentNode = null;
						}
						else
						{
							currentNode = currentNode.sibling;
							lastValidNode = currentNode;
						}
						
					}
					else
					{
						break;
					}
				}

				if(index == word.length()-1)
				{
					lastValidNode.sibling = new TrieNode<T>(word.charAt(index), elems);
				}
				else
				{
					lastValidNode.sibling = new TrieNode<T>(word.charAt(index));
				}

				added = true;
				lastValidNode = lastValidNode.sibling;
				lastValidNode.sibling = nextSibling;
			}
			
			index++;
		}
		
		if(!added)
		{
			if(lastValidNode.isWord)
			{
				while(elems.hasNext())
				{
					T elem = elems.next();
					int k = elements.indexOf(elem);
					if(k != -1)
					{
						elements.set(k, elem);
					}
					else
					{
						elements.addAlFinal(elem);
					}
				}
			}
		}

		return true;
	}
	

	public boolean eliminar(String word)
	{
		return false;
	}

	public boolean eliminarPrefijo(String word)
	{
		return false;
	}

	public Iterator<T> buscar(String word, int charNum)
	{

		if(character=='\0')
		{
			if(child!=null)
			{
				return child.buscar(word, charNum);
			}
			else
			{
				return elements.iterator();
			}
		}
		else
		{
			if(character == word.charAt(charNum))
			{
				if(isWord && charNum==word.length())
				{
					return elements.iterator();
				}
				else if(child!=null)
				{
					return child.buscar(word,charNum+1);
				}
				else
				{
					return new LlamaTrieIterator<T>();
				}

			}
			else
			{
				if(sibling!=null)
					return sibling.buscar(word,charNum);
				else
				{
					return new LlamaTrieIterator<T>();
				}
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
		return String.format("Character: %c ; Word: %s", character, isWord);
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


}
