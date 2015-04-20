import os

class Node(object):
    def __init__(self, char = '\0'):
        self.character = char
        self.element = None
        self.parent = None
        self.child = None
        self.sibling = None
        self.pSibling = None
        self.word = False
        self.right = False
        self.size = 0

    def append(self, word, value):
        node = self
        index = 0
        while index < len(word):
           child = node.getChild(word[index])
           if child is not None:
              node = child
              index += 1
           else:
              break
        while index < len(word):
           node = node.appendNode(word[index])
           index += 1

        if node.word:
           if isinstance(node.element, list):
              node.element.append(value)
           else:
              node.element = value
              node.word = True
        else:
           node.element = value
           node.word = True
           self.size += 1

    def appendNode(self, char):
        nNode = Node(char)
        if self.child is None:
           self.child = nNode
           self.child.parent = self
           return self.child
        else:
           sib = self.getGreaterChild(char)
           if not sib.right:
              if sib.parent is not None:
                 nNode.parent = sib.parent
                 sib.parent.child = nNode
                 sib.parent = None
              nNode.pSibling = sib.pSibling
              if nNode.pSibling is not None:
                 nNode.pSibling.sibling = nNode
              nNode.sibling = sib
              sib.pSibling = nNode
           else:
              sib.right = False
              nNode.pSibling = sib
              sib.sibling = nNode
           return nNode

    def getChild(self, char):
        if self.child is None:
           return None
        else:
           node = self.child
           while node is not None:
              if node.character == char:
                 break
              else:
                 node = node.sibling
           return node

    def getGreaterChild(self, char):
        node = self.child
        lastVNode = self.child
        while node != None:
           if node.character > char:
              break
           else:
              lastVNode = node
              node = node.sibling
        if node is None:
           if lastVNode.character < char:
              lastVNode.right = True
           return lastVNode
        return node

    def remove(self, word):
        node = self
        found = True
        i = 0
        while i < len(word):
           node = node.getChild(word[i])
           if node is None:
              found = False
              break
           i += 1
        if not found:
           return None
        if node.word:
           data = node.element
           node.removeNode()
           self.size -= 1
           return data
        else:
           return None

    def removeNode(self):
        if self.child is not None:
           self.word = False
           self.element = None
        else:
           if self.sibling is not None:
              prev = self.pSibling
              next = self.sibling
              if prev is not None:
                 prev.sibling = next
              if next is not None:
                 next.pSibling = prev
              if self.parent is not None:
                 self.parent.child = prev
                 if prev is not None:
                    prev.parent = self.parent
           else:
              if self.parent is not None:
                 p = self.parent
                 self.parent.child = None
                 self.parent = None
                 p.removeNode()
              else:
                 self.pSibling.sibling = self.sibling
                 if self.sibling is not None:
                    self.sibling.pSibling = self.pSibling
                 self.sibling = None
                 self.pSibling = None

    def __getitem__(self, word):
        if isinstance(word, str):
           node = self
           i = 0
           found = True
           while i < len(word):
             node = node.getChild(word[i])
             if node is None:
                found = False
                break
             i += 1
           if not found:
              return None
           return node.element


    def string_repr(self, s = '', p = ''):
        sPrefix = ''
        if self.character != '\0':
           sPrefix = p+self.character
        if self.word:
           s += p+self.character+", "
        if self.child is not None:
           s = self.child.string_repr(s, sPrefix)
        if self.sibling is not None:
           s = self.sibling.string_repr(s, p)
        return s

    def __repr__(self):
        return self.string_repr()[0:-1]

    def __str__(self):
        return self.string_repr()[0:-1]



class Trie(object):
    def __init__(self):
        self.root = Node()

    def __getitem__(self, word):
        if isinstance(word, str):
           return self.root[word]

    def __setitem__(self, word, value):
        if isinstance(word, str):
           self.root.append(word, value)

    def __delitem__(self, word):
        return self.remove(word)

    def remove(self, word):
        return self.root.remove(word)

    def __repr__(self):
        return '{'+self.root.__repr__()[0:-1]+'}'

    def __str__(self):
        return '{'+self.root.__str__()[0:-1]+'}'

    def text_segmentation(self, text):
        l = []
        l = self.break_words(text, len(text), l)
        return l

    def break_words(self, text, size, l = [], result = ""):
        for i in range(1, size+1):
            prefix = text[0:i]
            if self.root[prefix] is not None:
               if i == size:
                  result += prefix
                  l.append(result)
                  break
               l = self.break_words(text[i:], size-i, l, result+prefix+' ')
        return l




