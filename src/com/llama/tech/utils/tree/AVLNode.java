/*
 * AVLNode.java
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

public class AVLNode <T extends Comparable<T>> implements Comparable<AVLNode<T>>
{
	private T data;
	private AVLNode<T> parent;
	private AVLNode<T> left;
	private AVLNode<T> right;
	boolean[] visited = {false, false, false};
	private int balanceFactor;
	
	/**
	 * Constructor de la raíz del árbol
	 * @param data El valor del nodo. data != null.
	 */
	public AVLNode(T data)
	{
		this.data = data;
		this.balanceFactor = 1;
	}
	
	/**
	 * Constructor de un nodo del árbol.
	 * @param data El valor del nodo. data != null.
	 * @param parent El nodo padre del nuevo nodo. parent != null.
	 */
	public AVLNode(T data, AVLNode<T> parent)
	{
		this.data = data;
		this.parent = parent;
		this.balanceFactor = 1;
		this.parent.calculateHeight();
	}
	
	/**
	 * Retorna el valor del nodo.
	 * @return el valor del nodo.
	 */
	public T getValue()
	{
		return this.data;
	}
	
	/**
	 * Retorna el padre del nodo actual.
	 * @return el nodo padre. null, si el nodo es raíz.
	 */
	public AVLNode<T> getParent() 
	{
		return this.parent;
	}
	
	/**
	 * Retorna la altura del subárbol que tiene por raíz, el nodo actual. 
	 * @return la altura del subárbol.
	 */
	public int getBalanceFactor()
	{
		return balanceFactor;
	}
	
	/**
	 * Establece si un elemento corresponde al nodo presente, o se encuentra en el subárbol correspondiente.
	 * @param item El elemento a buscar. item != null.
	 * @return true, si el elemento existe. false, de lo contrario.
	 */
	public boolean contains(T item)
	{
		if(item.compareTo(this.data) == 0)
		{
			return true;
		}
		else if(item.compareTo(this.data) > 0)
		{
			if(right != null)
			{
				return right.contains(item);
			}
			return false;
		}
		else
		{
			if(left != null)
			{
				return left.contains(item);
			}
			return false;
		}
	}
	
	/**
	 * Busca un elemento en el subárbol que lleva por raíz, el nodo actual.
	 * @param item El elemento a buscar.
	 * @return el elemento a buscar, null si no fue hallado.
	 */
	public T get(T item)
	{
		if(item.compareTo(this.data) == 0)
		{
			return this.data;
		}
		else if(item.compareTo(this.data) > 0)
		{
			if(right != null)
			{
				return right.get(item);
			}
			return null;
		}
		else
		{
			if(left != null)
			{
				return left.get(item);
			}
			return null;
		}
	}

	/**
	 * Describe si el árbol/subárbol que tiene por raíz el nodo actual, se encuentra balanceado. 
	 * @return true, si el subárbol se encuentra balanceado. false, de lo contrario.
	 */
	public boolean balanced()
	{
		int leftLoad = (left == null) ? 0 : left.getBalanceFactor();
		int rightLoad = (right == null) ? 0 : right.getBalanceFactor();
		return Math.abs(leftLoad-rightLoad) <= 1;
	}
	
	/**
	 * Método que balancea un subárbol, en caso de que este se encuentre desbalanceado.
	 * @return true, si el árbol fue balanceado. false, de lo contrario.
	 */
	private boolean balanceTree()
	{
		if(this.left != null && this.right == null)
		{
			AVLNode<T> leftNode = this.left;
			if(leftNode.getBalanceFactor() >= 2)
			{
				int leftLoad = (leftNode.left == null) ? 0 : leftNode.left.getBalanceFactor();
				int rightLoad = (leftNode.right == null) ? 0 : leftNode.right.getBalanceFactor();
				if(leftLoad > rightLoad)
				{
					rightRotation();
				}
				else
				{
					hybridRightRotation();
				}
				
				return true;
			}
			
		}
		else if(this.right != null && this.left == null)
		{
			AVLNode<T> rightNode = this.right;
			if(right.getBalanceFactor() >= 2)
			{
				int leftLoad = (rightNode.left == null) ? 0 : rightNode.left.getBalanceFactor();
				int rightLoad = (rightNode.right == null) ? 0 : rightNode.right.getBalanceFactor();
				
				if(rightLoad > leftLoad)
				{
					leftRotation();
				}
				else
				{
					hybridLeftRotation();
				}
				
				return true;
			}
		}
		else if(this.right != null && this.left != null)
		{
			if(this.right.getBalanceFactor() >= this.left.getBalanceFactor()+2)
			{
				AVLNode<T> rightNode = this.right;
				if(rightNode.left.getBalanceFactor() > rightNode.right.getBalanceFactor())
				{
					hybridLeftRotation();
				}
				else
				{
				    leftRotation();
				}
				return true;
			}
			else if(left.getBalanceFactor() >= right.getBalanceFactor()+2)
			{
				AVLNode<T> leftNode = this.left;
				if(leftNode.left.getBalanceFactor() > leftNode.right.getBalanceFactor())
				{
					rightRotation();
				}
				else
				{
					hybridRightRotation();
				}
				return true;
			}
		}
		return false;
		
	}

	/**
	 * Ejecuta una rotación a la derecha.
	 * @return la nueva raíz tras la rotación.
	 */
	private AVLNode<T> rightRotation() 
	{
		AVLNode<T> left = this.left;
		left.parent = this.parent;
		if(left.parent != null)
		{
			if(left.parent.left != null && this.compareTo(left.parent.left) == 0)
			{
				left.parent.left = left;
			}
			else if(left.parent.right != null && this.compareTo(left.parent.right) == 0)
			{
				left.parent.right = left;
			}
			//left.parent.left = left;
		}
		this.left = left.right;
		left.right = this;
		this.parent = left;
		//left.calculateHeight();
		return left;
	}
	
	/**
	 * Ejecuta una rotación a la izquierda.
	 * @return la nueva raíz tras la rotación.
	 */
	private AVLNode<T> leftRotation()
	{
		AVLNode<T> right = this.right;
		right.parent = this.parent;
		if(right.parent != null)
		{
			if(right.parent.left != null && this.compareTo(right.parent.left) == 0)
			{
				right.parent.left = right;
			}
			else if(right.parent.right != null && this.compareTo(right.parent.right) == 0)
			{
				right.parent.right = right;
			}
		}
		this.right = right.left;
		right.left = this;
		this.parent = right;
		return right;
	}
	
	/**
	 * Realiza una rotación mixta, en caso que el árbol se encuentre desbalanceado debido 
	 * a el subárbol izquierdo del hijo derecho del nodo actual.
	 */
	private void hybridLeftRotation()
	{
		AVLNode<T> root = leftRotation();
		AVLNode<T> leftR = root.left.leftRotation();
		root.left = leftR;
		root.rightRotation();
	}
	
	/**
	 * Realiza una rotación mixta, en caso que el árbol se encuentre desbalanceado debido 
	 * a el subárbol derecho del hijo izquierdo del nodo actual.
	 */
	private void hybridRightRotation()
	{
		AVLNode<T> root = rightRotation();
		AVLNode<T> rightR = root.right.rightRotation();
		root.right = rightR;
		root.leftRotation();
	}
	

	/**
	 * Describe si el nodo actual corresponde a la raíz de un árbol.
	 * @return true, si el nodo es raíz. false, de lo contrario.
	 */
	public boolean isRoot()
	{
		return this.parent == null;
	}
	
	/**
	 * Agrega un elemento al subárbol.
	 * @param item el elemento a agregar.
	 */
	public void add(T item)
	{
		if(item.compareTo(this.data) > 0)
		{
			if(this.right == null)
			{
				this.right = new AVLNode<T>(item, this);
			}
			else
			{
				this.right.add(item);
			}
		}
		else
		{
			if(this.left == null)
			{
				this.left = new AVLNode<T>(item, this);
			}
			else
			{
				this.left.add(item);
			}
		}

		this.calculateHeight();
		
	}
	
	/**
	 * Elimina un elemento de un subárbol.
	 * @param item el elemento a ser eliminado.
	 */
	private void removeNode(T item)
	{
		if(this.data.compareTo(item) == 0)
		{
			if(this.left != null && this.right != null)
			{
				AVLNode<T> left = this.left;
				AVLNode<T> replacement = left;
				while(replacement.right != null)
				{
					replacement = replacement.right;
				}
				
				replacement.parent.right = null;
				replacement.parent = this.parent;
				replacement.left = left;
				replacement.right = this.right;
				left.parent = replacement;
               
				if (this.parent.left != null && this.compareTo(this.parent.left) == 0) 
				{
					this.parent.left = replacement;
				} 
				else if (this.parent.right != null && this.compareTo(this.parent.right) == 0) 
				{
					this.parent.right = replacement;
				}
				
				this.left = null;
			    this.right = null;
			    this.parent = null;
			    replacement.calculateHeight();
			}
			else if(this.left == null && this.right != null)
			{
				AVLNode<T> right = this.right;
				right.parent = this.parent;
				if (this.parent.left != null && this.compareTo(this.parent.left) == 0) 
				{
					this.parent.left = right;
				} 
				else if (this.parent.right != null && this.compareTo(this.parent.right) == 0) 
				{
					this.parent.right = right;
				}
				
				this.right = null;
				this.parent = null;
				right.calculateHeight();
			}
			else if(this.left != null && this.right == null)
			{
				AVLNode<T> left = this.left;
				left.parent = this.parent;
				
				if (this.parent.left != null && this.compareTo(this.parent.left) == 0) 
				{
					this.parent.left = left;
				} 
				else if (this.parent.right != null && this.compareTo(this.parent.right) == 0) 
				{
					this.parent.right = left;
				}
				
				this.left = null;
				this.parent = null;
				
				left.calculateHeight();
			}
			else
			{
				if (this.parent.left != null && this.compareTo(this.parent.left) == 0) 
				{
					this.parent.left = null;
				} 
				else if (this.parent.right != null && this.compareTo(this.parent.right) == 0) 
				{
					this.parent.right = null;
				}
				this.parent.calculateHeight();
				this.parent = null;
			}
		}
		else if(this.data.compareTo(item) > 0)
		{
			if(this.left != null)
			{
				this.left.removeNode(item);
			}
		}
		else
		{
			if(this.right != null)
			{
				this.right.removeNode(item);
			}
		}
	}
	
	/**
	 * Elimina el elemento de la raíz del árbol.
	 * @param item el elemento a ser eliminado.
	 * @return la nueva raíz del árbol.
	 */
	public AVLNode<T> remove(T item)
	{
		AVLNode<T> node = this;
		if(this.data.compareTo(item) == 0)
		{
			if(balanceFactor == 1)
			{
				return null;
			}
			
			if(this.left != null && this.right != null)
			{
				AVLNode<T> left = this.left;
				AVLNode<T> right = this.right;
				AVLNode<T> replacement = left;

				int search = 0;
				while(replacement.right != null)
				{
					replacement = replacement.right;
					search++;
				}
				System.out.println(replacement);
				
				right.parent = replacement;
				if(search > 0)
				{
					replacement.parent.right = null;
				}
				replacement.parent = this.parent;
				if(search > 0)
				{
					replacement.left = left;
				}
				replacement.right = right;
				left.parent = replacement;
				right.parent = replacement;
				replacement.calculateHeight();
				return replacement;
			}
			else if(this.right != null && this.left == null)
			{
				AVLNode<T> right = this.right;
				right.parent = this.parent;
				this.right = null;
				right.calculateHeight();
				return right; 
			}
			else if(this.left != null && this.right == null)
			{
				AVLNode<T> left = this.left;
				left.parent = this.parent;
				this.left = null;
				left.calculateHeight();
				return left; 
			}
		}
		else if (this.data.compareTo(item) > 0)
		{
			this.left.removeNode(item);
		}
		else
		{
			this.right.removeNode(item);
		}
		
		return node;
			
	}
	
	/**
	 * Reinicia el orden de recorrido de un nodo (Preorden).
	 */
	private void resetVisits()
	{
		visited[0] = false;
		visited[1] = false;
		visited[2] = false;
	}
	
	/**
	 * Establece si el nodo y el subárbol correspondiente fueron visitados.
	 * @return true, si el subárbol y el nodo fueron visitados. false, de lo contrario.
	 */
	private boolean partialPathCompletion()
	{
		return visited[0] && visited[1] && visited[2];
	}
	
	/**
	 * 
	 * @return
	 */
	public AVLNode<T> markVisited()
	{
		if(this.left == null && this.right == null)
		{
			visited[0] = true;
			visited[1] = true;
			visited[2] = true;
			return this;
		}
		
		if(!visited[0])
		{
			if(left != null && !left.partialPathCompletion())
			{
				return left.markVisited();
			}
			else
			{
				visited[0] = true;
				visited[1] = true;
				if(left != null)
				{
					left.resetVisits();
				}
				return this;
			}
		}
		else if(!visited[2])
		{
			
			if(right != null && !right.partialPathCompletion())
			{
				return right.markVisited();
			}
			else
			{
				visited[2] = true;
				if(right != null)
				{
					right.resetVisits();
				}
			}
		}
		else
		{
			resetVisits();
		}
		if(parent != null)
		{
			return parent.markVisited();
		}
		return null;
	}
	
	private int calculateHeight()
	{
		if(this.left != null && this.right == null)
		{
			this.balanceFactor = 1+left.calculateHeight();
			if(!this.balanced())
			{
				balanceTree();
				return this.parent.calculateHeight();
			}
		}
		else if(this.left == null && this.right != null)
		{
			this.balanceFactor = 1+right.calculateHeight();
			if(!this.balanced())
			{
				balanceTree();
				return this.parent.calculateHeight();
			}
		}
		else if(this.left != null && this.right != null)
		{
			this.balanceFactor = 1+Math.max(left.calculateHeight(), right.calculateHeight());
			if(!this.balanced())
			{
				balanceTree();
				return this.parent.calculateHeight();
			}
		}
		else
		{
			this.balanceFactor = 1;
		}
		
		return this.balanceFactor;
	}
	
	
	
	@Override
	public String toString()
	{
		String s = "";
		if(this.left == null && this.right != null)
		{
			s = " "+this.data.toString()+this.right.toString();
		}
		else if(this.left != null && this.right == null)
		{
			s = this.left.toString()+this.data.toString()+" ";
		}
		else if(this.left != null && this.right != null)
		{
			s = this.left.toString()+this.data.toString()+this.right.toString();
		}
		else
		{
			s = String.format(" %s ", this.data.toString());
		}
		return s;
		
	}
	
	public AVLNode<T> toConsole(AVLNode<T> tree)
	{
		if(!tree.isRoot())
		{
			tree = tree.getParent();
		}
		System.out.println(tree);
		try
		{
			System.out.println("Root: "+tree.data);
			System.out.println("Left: "+tree.left+"("+tree.left.data+")");
			System.out.println("Right: "+tree.right+"("+tree.right.data+")");
			System.out.println("Height: "+tree.getBalanceFactor());
		}
		catch(Exception e)
		{
			
		}
		
		return tree;
	}

	@Override
	public int compareTo(AVLNode<T> o) 
	{
		return o.data.compareTo(this.data);
	}
	
   
}
