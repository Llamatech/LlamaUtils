package com.llama.tech.utils.tree;

import java.util.Iterator;

import com.llama.tech.utils.list.Lista;
import com.llama.tech.utils.list.LlamaArrayList;

public class TrieNode<T> 
{
	private char character;

	private Lista<T> elements = new LlamaArrayList<T>(1);

	private boolean isWord = false;
	
	private boolean right = false;

	private TrieNode<T> parent;
	
	private TrieNode<T> pSibling;
	
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
	      TrieNode<T> node = this;
	      int index = 0;
	      while(index < word.length())
	      {
	          TrieNode<T> childS = node.getSibling(word.charAt(index));
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
	    	  
	          node = node.appendNode(word.charAt(index));
	          index++;
	      }

	      if(node.isWord)
	      {
	          int j = node.elements.indexOf(elem);
	          if(j < 0)
	          {
	              node.elements.addAlFinal(elem);
	          }
	          else
	          {
	              node.elements.set(j, elem);
	          }
	      }
	      else
	      {
	          node.elements.addAlFinal(elem);
	          node.isWord = true;
	      }

	      return true;
	}
	
	
	public boolean agregar(String word, Iterator<T> elems)
	{
	      TrieNode<T> node = this;
	      int index = 0;
	      while(index < word.length())
	      {
	          TrieNode<T> childS = node.getSibling(word.charAt(index));
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
	          node = node.appendNode(word.charAt(index));
	          index++;
	      }

	      if(node.isWord)
	      {
	    	  while(elems.hasNext())
	    	  {
	    		  T elem = elems.next();
		          int j = node.elements.indexOf(elem);
		          if(j < 0)
		          {
		              node.elements.addAlFinal(elem);
		          }
		          else
		          {
		              node.elements.set(j, elem);
		          }
	    	  }
	      }
	      else
	      {
	    	  while(elems.hasNext())
	    	  {
	    		  node.elements.addAlFinal(elems.next());
	    	  }
	          node.isWord = true;
	      }

	      return true;
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
	
	public boolean eliminar(String word)
	{
		TrieNode<T> node = this;
		boolean found = true;
		int i = 0;
		while(i < word.length())
		{
			node = node.getSibling(word.charAt(i));
			if(node == null)
			{
				found = false;
				break;
			}
			i++;
		}
		
		if(!found)
		{
			return found;
		}
		
		if(node.isWord)
		{
			node.removeNode();
			return true;
		}
		else
		{
			return false;
		}
		   
	}
	
	private void removeNode() 
	{
		
		if(child != null)
		{
			isWord = false;
			elements.clear();
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

	private boolean eliminar(String word, int index)
	{
		boolean[] marks = {false, false};
		
		if(character == word.charAt(index))
		{
			if(child != null)
			{
				marks[0] = child.eliminar(word, index+1);
			}
			else
			{
				if(index == word.length()-1)
				{
					isWord = false;
					elements.clear();
					return true;
				}
			}
		}
		else
		{
			if(sibling != null)
			{
				marks[1] = sibling.eliminar(word, index);
			}
		}
		
		if(marks[0])
		{
			if(!child.hasSiblings())
			{
				child = null;
				if(sibling != null)
				{
					
				}
			}
		}
		
		return false;
	}

	public boolean eliminarPrefijo(String word, int charNum)
	{
		if(character=='\0')
		{
			if (child!=null)
			{
				return child.eliminarPrefijo(word,charNum);
			}
			else
			{
				return word.equals("");
			}
		}
		else
		{
			if(character==word.charAt(charNum))
			{
				if(charNum==word.length()-1)
				{
					if(parent!=null)
						if(sibling!=null)
							parent.child=sibling;
						else
							parent.child=null;
					else //if(pSibling!=null)
						if(sibling!=null)
							pSibling.sibling=sibling;
						else
							pSibling.sibling=null;
					return true;
				}
				else
				{
					if(child!=null)
						return child.eliminarPrefijo(word, charNum+1);
					else
						return false;
				}
			}
			else
			{
				if(sibling!=null)
					return sibling.eliminarPrefijo(word, charNum);
				else
					return false;
			}
		}
	}

	public boolean buscarB(String word, int charNum)
	{
		if(character=='\0')
		{
			if(child!=null)
			{
				return child.buscarB(word, charNum);
			}
			else
			{
				return word.equals("");
			}
		}
		else
		{
			if(character == word.charAt(charNum))
			{
				if(isWord && charNum==word.length()-1)
				{
					return true;
				}
				else if(charNum==word.length()-1)
				{
					return false;
				}
				else if(child!=null)
				{
					return child.buscarB(word,charNum+1);
				}
				else
				{
					return false;
				}

			}
			else
			{
				if(sibling!=null)
					return sibling.buscarB(word,charNum);
				else
				{
					return false;
				}
			}
		}
	}

	public boolean buscarPrefijoB(String p, int charNum)
	{
		if(character=='\0')
		{
			if(child!=null)
			{
				return child.buscarPrefijoB(p, charNum);
			}
			else
			{
				return p.equals("");
			}
		}
		else
		{
			if(character==p.charAt(charNum))
			{

				if(p.length()-1==charNum)
					return true;
				else if(child !=null)
					return child.buscarPrefijoB(p, charNum+1);
				else
					return false;
			}
			else
			{

				if(sibling!=null)
					return sibling.buscarPrefijoB(p, charNum);
				else
					return false;

			}
		}
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
				if(isWord && charNum==word.length()-1)
				{
					return elements.iterator();
				}
				else if(word.length()-1==charNum)
				{
					return new LlamaTrieIterator<T>();
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

	public void buscarPrefijo(String p, Lista<T> lista, int charNum, boolean b)
	{
		if(character=='\0')
		{
			if(child!=null)
			{
				child.buscarPrefijo(p, lista, charNum,false);
			}
			else
			{

			}
		}
		else
		{
			if(character==p.charAt(charNum))
			{
				for(T t: elements)
					lista.addAlFinal(t);
				if(p.length()-1==charNum)
					b=true;
				else if(child !=null)
					child.buscarPrefijo(p, lista, charNum+1, b);
			}
			else
			{
				if(b)
				{
					for(T t: elements)
						lista.addAlFinal(t);
					if(child!=null)
						child.buscarPrefijo(p, lista, charNum, b);
					if(sibling!=null)
						sibling.buscarPrefijo(p, lista, charNum, b);
				}
				else
				{
					if(sibling!=null)
						sibling.buscarPrefijo(p, lista, charNum, b);
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




}
