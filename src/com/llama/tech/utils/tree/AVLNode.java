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

public class AVLNode <T extends Comparable<T>>
{
	private T data;
	private AVLNode<T> parent;
	private AVLNode<T> left;
	private AVLNode<T> right;
	private int balanceFactor;
	
	public AVLNode(T data)
	{
		this.data = data;
		this.balanceFactor = 1;
	}
	
	public AVLNode(T data, AVLNode<T> parent)
	{
		this.data = data;
		this.parent = parent;
		this.balanceFactor = 1;
		this.parent.recalculateHeight();
	}
	
	public AVLNode<T> getParent() 
	{
		return this.parent;
	}
	
	public void setLeft(AVLNode<T> node)
	{
		this.left = node;
	}
	
	public void setRight(AVLNode<T> node)
	{
		this.right = node;
	}

	public boolean balanced()
	{
		if(this.left == null && this.right != null)
		{
			if(this.right.right == null && this.right.left == null)
			{
				return true;
			}
			else
			{
				return false;
			}
		}
		else if(this.left != null && this.right == null)
		{
			if(this.left.right == null && this.left.left == null)
			{
				return true;
			}
			else
			{
				return false;
			}
		}
		else if(this.left != null && this.right != null)
		{
			return this.left.balanced() &&  this.right.balanced();
		}
		else
		{
			return true;
		}
	}

	public boolean isRoot()
	{
		return this.parent == null;
	}
	
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
		if(this.parent != null)
		{
			this.parent.recalculateHeight();
		}
		else
		{
			this.recalculateHeight();
		}
		
	}
	
	
	public T remove(T item)
	{
		return null;
	}
	
	private int recalculateHeight() 
	{
		if(this.left != null && this.right == null)
		{
			if(!this.balanced())
			{
				if(this.left.left != null && this.left.right == null)
				{
					this.left.parent = this.parent;
					if(this.parent != null)
					{
						this.left.parent.left = this.left;
					}
					this.parent = this.left;
					this.left.right = this;
					this.right = null;
					this.left = null;
					this.balanceFactor = 1;
				}
				else if(this.left.left == null && this.left.right != null)
				{
					this.left.right.parent = this.parent;
					if(this.left.right.parent != null)
					{
						this.left.right.parent.left = this.left.right;
					}
					
					this.left.right.left = this.left;
					this.left.parent = this.left.right;
					this.left.right.right = this;
					this.parent = this.left.right;
					this.left.left = null;
					this.left.right = null;
					this.left.balanceFactor = 1;
					this.left = null;
					this.right = null;
					this.balanceFactor = 1;
				}
				
				return this.parent.recalculateHeight();
			}
			this.balanceFactor = 1+this.left.recalculateHeight();
		}
		
		else if(this.left == null && this.right != null)
		{
			if(!this.balanced())
			{
				
			}
		}
	}
	
//	def recalculateHeight(self):
//	       elif self.left is None and self.right is not None:
//	          if not self.balanced():
//	             if self.right.right is not None and self.right.left is None:
//	                self.right.parent = self.parent
//	                if self.right.parent is not None:
//	                   self.right.parent.right = self.right
//	                self.parent = self.right
//	                self.right.left = self
//	                self.right = None
//	                self.left = None
//	                self.balanceFactor = 1
//	             elif self.right.right is None and self.right.left is not None:
//	                self.right.left.parent = self.parent
//	                if self.right.left.parent is not None:
//	                   self.right.left.parent.right = self.right.left
//	                self.parent = self.right.left
//	                self.right.left.left = self
//	                self.right.left.right = self.right
//	                self.right.parent = self.right.left
//	                self.right.right = None
//	                self.right.left = None
//	                self.right.balanceFactor = 1
//	                self.right = None
//	                self.left = None
//	                self.balanceFactor = 1
//	             return self.parent.recalculateHeight()
//	          self.balanceFactor = 1+self.right.recalculateHeight()
//	       elif self.left is not None and self.right is not None:
//	          leftLoad = self.left.recalculateHeight()
//	          rightLoad = self.right.recalculateHeight()
//	          if leftLoad >= rightLoad+2:
//	             if self.left.right.left is not None:
//	                self.left.right.parent = self.parent
//	                self.parent = self.left.right
//	                self.parent.right = self
//	                self.left.parent = self.parent
//	                node = self.parent.left
//	                node.parent = self.left
//	                self.left.right = node
//	                self.parent.left = self.left
//	                self.left = None
//	             elif self.left.right.right is not None:
//	             	self.left.right.parent = self.parent
//	                self.parent = self.left.right
//	                node = self.parent.right
//	                self.parent.right = self
//	                self.parent.left = self.left
//	                self.left.parent = self.parent
//	                node.parent = self
//	                self.left.right = None
//	                self.left = node
//	             elif self.left.left.left is not None or self.left.left.right is not None:
//	                self.left.parent = self.parent
//	                self.parent = self.left
//	                node = self.parent.right
//	                self.left = node
//	                node.parent = self
//	                self.parent.right = self 
//	             return self.parent.recalculateHeight()
//	          elif rightLoad >= leftLoad+2:
//	             if self.right.right.right is not None or self.right.right.left is not None:
//	                self.right.parent = self.parent
//	                self.parent = self.right
//	                node = self.parent.left
//	                self.right = node
//	                node.parent = self
//	                self.parent.left = self
//	             elif self.right.left.right is not None:
//	                self.right.left.parent = self.parent
//	                self.parent = self.right.left
//	                self.parent.left = self
//	                node = self.parent.right
//	                self.parent.right = self.right
//	                self.right.parent = self.parent
//	                self.right.left = node
//	                node.parent = self.right
//	                self.right = None
//	             elif self.right.left.left is not None:
//	                self.right.left.parent = self.parent
//	                self.parent = self.right.left
//	                node = self.parent.left
//	                self.parent.left = self
//	                self.parent.right = self.right
//	                self.right.parent = self.parent
//	                self.right.left = None
//	                self.right = node
//	                node.parent = self
//	             return self.parent.recalculateHeight()
//	          self.balanceFactor = 1+max(leftLoad, rightLoad)
//	       return self.balanceFactor
	
	
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
	
   
}
