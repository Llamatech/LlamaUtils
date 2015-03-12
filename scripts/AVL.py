#!/usr/bin/env python
#-*- coding:utf-8 -*-

'''
  AVL.py
  This file is part of LlamaUtils
 
  Copyright (C) 2015 - Edgar Andrés Margffoy Tuay
 
  LlamaUtils is free software; you can redistribute it and/or modify
  it under the terms of the GNU General Public License as published by
  the Free Software Foundation; either version 2 of the License, or
  (at your option) any later version.
 
  LlamaUtils is distributed in the hope that it will be useful,
  but WITHOUT ANY WARRANTY; without even the implied warranty of
  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
  GNU General Public License for more details.
 
  You should have received a copy of the GNU General Public License
  along with LlamaUtils. If not, see <http://www.gnu.org/licenses/>.
'''


class Node(object):
   def __init__(self, data, parent = None):
       self.data = data
       self.parent = parent
       self.left = None
       self.right = None
       self.balanceFactor = 1
       if self.parent is not None:
          self.parent.recalculateHeight()

   def recalculateHeight(self):
       if self.left is not None and self.right is None:
          if not self.balanced():
             if self.left.left is not None and self.left.right is None:
                self.left.parent = self.parent
                if self.left.parent is not None:
                   self.left.parent.left = self.left
                self.parent = self.left
                self.left.right = self
                self.right = None
                self.left = None
                self.balanceFactor = 1
             elif self.left.left is None and self.left.right is not None:
                self.left.right.parent = self.parent
                if self.left.right.parent is not None:
                   self.left.right.parent.left = self.left.right
                self.left.right.left = self.left
                self.left.parent = self.left.right
                self.left.right.right = self
                self.parent = self.left.right
                self.left.left = None
                self.left.right = None
                self.left.balanceFactor = 1
                self.left = None
                self.right = None
                self.balanceFactor = 1
             return self.parent.recalculateHeight()
          self.balanceFactor = 1+self.left.recalculateHeight()
       elif self.left is None and self.right is not None:
          if not self.balanced():
             if self.right.right is not None and self.right.left is None:
                self.right.parent = self.parent
                if self.right.parent is not None:
                   self.right.parent.right = self.right
                self.parent = self.right
                self.right.left = self
                self.right = None
                self.left = None
                self.balanceFactor = 1
             elif self.right.right is None and self.right.left is not None:
                self.right.left.parent = self.parent
                if self.right.left.parent is not None:
                   self.right.left.parent.right = self.right.left
                self.parent = self.right.left
                self.right.left.left = self
                self.right.left.right = self.right
                self.right.parent = self.right.left
                self.right.right = None
                self.right.left = None
                self.right.balanceFactor = 1
                self.right = None
                self.left = None
                self.balanceFactor = 1
             return self.parent.recalculateHeight()
          self.balanceFactor = 1+self.right.recalculateHeight()
       elif self.left is not None and self.right is not None:
          leftLoad = self.left.recalculateHeight()
          rightLoad = self.right.recalculateHeight()
          if leftLoad >= rightLoad+2:
             if self.left.right.left is not None:
                self.left.right.parent = self.parent
                self.parent = self.left.right
                if self.parent.parent != None:
                   self.parent.parent.left = self.parent
                self.parent.right = self
                self.left.parent = self.parent
                node = self.parent.left
                node.parent = self.left
                self.left.right = node
                self.parent.left = self.left
                self.left = None
             elif self.left.right.right is not None:
                self.left.right.parent = self.parent
                self.parent = self.left.right
                if self.parent.parent != None:
                   self.parent.parent.left = self.parent
                node = self.parent.right
                self.parent.right = self
                self.parent.left = self.left
                self.left.parent = self.parent
                node.parent = self
                self.left.right = None
                self.left = node
             elif self.left.left.left is not None or self.left.left.right is not None:
                self.left.parent = self.parent
                self.parent = self.left
                if self.parent.parent != None:
                   self.parent.parent.left = self.parent
                node = self.parent.right
                self.left = node
                node.parent = self
                self.parent.right = self 
             return self.parent.recalculateHeight()
          elif rightLoad >= leftLoad+2:
             if self.right.right.right is not None or self.right.right.left is not None:
                self.right.parent = self.parent
                self.parent = self.right
                if self.parent.parent != None:
                   self.parent.parent.right = self.parent
                node = self.parent.left
                self.right = node
                node.parent = self
                self.parent.left = self
             elif self.right.left.right is not None:
                self.right.left.parent = self.parent
                self.parent = self.right.left
                if self.parent.parent != None:
                   self.parent.parent.right = self.parent
                self.parent.left = self
                node = self.parent.right
                self.parent.right = self.right
                self.right.parent = self.parent
                self.right.left = node
                node.parent = self.right
                self.right = None
             elif self.right.left.left is not None:
                self.right.left.parent = self.parent
                self.parent = self.right.left
                if self.parent.parent != None:
                   self.parent.parent.right = self.parent
                node = self.parent.left
                self.parent.left = self
                self.parent.right = self.right
                self.right.parent = self.parent
                self.right.left = None
                self.right = node
                node.parent = self
             return self.parent.recalculateHeight()
          self.balanceFactor = 1+max(leftLoad, rightLoad)
       return self.balanceFactor

   def append(self, data):
       if data >= self.data:
          if self.right is None:
             self.right = Node(data, self)
          else:
             self.right.append(data)
       else:
          if self.left is None:
             self.left = Node(data, self)
          else:
             self.left.append(data)
       if self.parent is not None:
          self.parent.recalculateHeight()
       else:
          self.recalculateHeight()

   #def remove(self, data):
       #TODO

   def print_subtree(self):
       if self.left is None and self.right is not None:
          s = ' '+str(self.data)+self.right.print_subtree()
       elif self.left is not None and self.right is None:
          s = self.left.print_subtree()+str(self.data)+' '
       elif self.left is not None and self.right is not None:
          s = self.left.print_subtree()+str(self.data)+self.right.print_subtree()
       else:
          s = ' %s ' % str(self.data)
       return s

   def balanced(self):
       if self.left is None and self.right is not None:
          if self.right.right is None and self.right.left is None:
             return True
          else:
             return False
       if self.left is not None and self.right is None:
          if self.left.right is None and self.left.left is None:
             return True
          else:
             return False
       elif self.left is not None and self.right is not None:
          return self.left.balanced() and self.right.balanced()
       else:
          return True

   def is_root(self):
       return self.parent is None

   def __repr__(self):
       return self.print_subtree()

   def __str__(self):
       return self.print_subtree()

   
