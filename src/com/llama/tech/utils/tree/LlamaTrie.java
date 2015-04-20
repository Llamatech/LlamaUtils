package com.llama.tech.utils.tree;

import java.util.Iterator;

import javax.imageio.ImageTranscoder;

import co.edu.uniandes.estructuras.trie.ITrie;

import com.llama.tech.misc.LlamaText;
import com.llama.tech.misc.LlamaText.TextSegmentationException;
import com.llama.tech.utils.dict.LlamaDict;
import com.llama.tech.utils.dict.LlamaDict.UnhashableTypeException;
import com.llama.tech.utils.list.Lista;
import com.llama.tech.utils.list.LlamaArrayList;

public class LlamaTrie<T>  implements ITrie<T>
{	
	public TrieNode<T> root;
	private LlamaDict<Character,String> distinguished_sym = new LlamaDict<Character,String>(10);

	public LlamaTrie()
	{
		root = new TrieNode<T>();
		try 
		{
			distinguished_sym.addEntry('.', "Dot");
			distinguished_sym.addEntry(',', "Comma");
			distinguished_sym.addEntry(';', "Semi-Colon");
			distinguished_sym.addEntry('¿', "Interrogation_Opening");
			distinguished_sym.addEntry('?', "Interrogation_Closing");
			distinguished_sym.addEntry('¡', "Exclamation_Opening");
			distinguished_sym.addEntry('!', "Exclamation_Closing");
			distinguished_sym.addEntry('«', "L_Guillemet");
			distinguished_sym.addEntry('»', "R_Guillemet");
			distinguished_sym.addEntry(' ', "Space");

		} 
		catch (UnhashableTypeException e) 
		{

		}
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
		trie.agregar("Torre", "Torre");		
		trie.agregar("Toma", "Toma");
		trie.agregar("Torreón", "Torreón");
		trie.agregar("Tomate", "A");
		trie.agregar("Tomate", "B");
		trie.agregar("Tomar", "Tomar");
		trie.agregar("Torrijos", "Torrijos");
		trie.agregar("Tornado", "Tornado");
		trie.agregar("Torta", "Torta");
		trie.agregar("Tormenta", "Tormenta");
		trie.agregar("Torno", "Torno");
		trie.agregar("Tornar", "Tornar");
		trie.agregar("Tornasol", "Tornasol");
		trie.agregar("Antes", "Antes");

		System.out.println(trie);	
		//		Iterator<String> it = trie.eliminarPalabrasConPrefijo("Tom");
		Iterator<String> it = trie.buscarPalabrasConPrefijo("Tom");

		while (it.hasNext())
		{
			System.out.println(it.next());
		}



		System.out.println(trie.buscar("Toma"));

		trie.agregar("casado", "Casado");
		trie.agregar("casa", "Casa");
		trie.agregar("caso", "Caso");
		trie.agregar("doy", "doy");
		trie.agregar("y", "y");

		String text = "casadoy.casadoy?";
		
		LlamaArrayList<String> l = null;
		try {
			l = trie.text_segmentation(text);
		} catch (TextSegmentationException e) 
		{
			e.printStackTrace();
		}
		for(String s: l)
		{
			System.out.println(s);
		}
		
		l.clear();
		text = "cas|doy casadoy";
		System.out.println("Segmentar: "+text);
		try {
			l = trie.text_segmentation(text);
		} catch (TextSegmentationException e) 
		{
			e.printStackTrace();
		}
		for(String s: l)
		{
			System.out.println("Resultado: "+s);
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

	@Override
	public Iterator<String> eliminarPalabrasConPrefijo(String p)
	{
		Lista<String> lista = new LlamaArrayList<String>(50);
		root.eliminarPrefijo(lista, "", p, 0, false);
		return new LlamaTrieIterator<String>(lista);
	}

	@Override
	public void agregar(String arg0, T arg1) 
	{
		agregarT(arg0, arg1);

	}

	@Override
	public Iterator<String> buscarPalabrasConPrefijo(String p) {
		Lista<String> lista = new LlamaArrayList<String>(50);
		root.buscarPrefijo(p, lista, 0, false, "");
		return lista.iterator();
	}

	@Override
	public int darNumeroPalabras() 
	{
		return root.getSize();
	}

	public LlamaArrayList<String> text_segmentation(String text) throws TextSegmentationException
	{
		LlamaArrayList<String> l = new LlamaArrayList<String>(10);
		LlamaArrayList<String> bif_tree = new LlamaArrayList<String>(10);
		
		LlamaArrayList<String> text_p = preProcess_text(text);
		
		System.out.println(text_p);

		for(String fragment: text_p)
		{
			l.clear();
			if(fragment != "")
			{
				if(distinguished_sym.getValue(fragment.charAt(0)) != null)
				{
					if(bif_tree.size() == 0)
					{
						bif_tree.addAlFinal(fragment);	
					}
					else
					{
						for(int i = 0; i < bif_tree.size(); i++)
						{
							bif_tree.set(i, bif_tree.get(i)+fragment);
						}
					}
				}
				else
				{
					break_words(fragment, fragment.length(), l, "");
					if(l.size() > 0)
					{
						if(bif_tree.size() == 0)
						{
							bif_tree = l.clone();
						}
						else
						{
							LlamaArrayList<String> copy = bif_tree.clone();
							for(int i = 0; i < bif_tree.size(); i++)
							{
								bif_tree.set(i, bif_tree.get(i)+l.get(0));
							}
							for(int i = 1; i < l.size(); i++)
							{
								for(String preRes: copy)
								{
									bif_tree.addAlFinal(preRes+l.get(i));
								}
							}
						}
					}
					else
					{
						throw new LlamaText.TextSegmentationException(
								  String.format(
								  "El fragmento: %s, no contiene ninguna palabra válida",
								  fragment));
					}
				}
			}
		}

		//break_words(text, text.length(), l, "");
		return bif_tree;

	}

	public LlamaArrayList<String> preProcess_text(String text)
	{
		char[] common_char = {',', ';', '¿', '?', '¡', '!', '«', '»',' '};
		LlamaArrayList<String> preProc = LlamaText.splitItemBy(text, '.');
		for(Character c: common_char)
		{
			preProc = LlamaText.splitItemsBy(preProc, c);
		}
		return preProc;

	}

	private void break_words(String text, int size, LlamaArrayList<String> l, String result)
	{
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
