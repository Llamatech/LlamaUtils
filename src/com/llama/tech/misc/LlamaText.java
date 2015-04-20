package com.llama.tech.misc;

import com.llama.tech.utils.list.LlamaArrayList;

public class LlamaText 
{
	public static LlamaArrayList<String> llamaSplit(String text, char id)
	{
		LlamaArrayList<String> l = new LlamaArrayList<String>(10);
		int start = 0;
		for(int i = 0; i < text.length(); i++)
		{
			if(text.charAt(i) == id)
			{
				l.addAlFinal(text.substring(start, i));
				if(i != 0)
				{
					l.addAlFinal(String.valueOf(id)+" ");
				}
				else
				{
					l.addAlFinal(String.valueOf(id));
				}
				start = i+1;
			}
		}
		
		
		String subs = text.substring(start);
		if(!subs.equals(""))
		{
			l.addAlFinal(subs);
		}
		
		return l;
	}
	
	public static LlamaArrayList<String> splitItemsBy(LlamaArrayList<String> items, char sym)
	{
		LlamaArrayList<String> splitList = new LlamaArrayList<String>(10);
		for(String item: items)
		{
			LlamaArrayList<String> temp = llamaSplit(item, sym);
			if(temp.size() > 0)
			{
			    splitList.addAll(temp);
			}
			else
			{
				splitList.addAlFinal(item);
			}
			
		}
		
		return splitList; 
	}
	
	public static LlamaArrayList<String> splitItemBy(String text, char sym)
	{
		LlamaArrayList<String> split = llamaSplit(text, sym);
		if(split.size() == 0)
		{
			split.addAlFinal(text);
		}
		return split;
	}
	
	public static class TextSegmentationException extends Exception
	{
		public TextSegmentationException(String msg)
		{
			super(msg);
		}
	}
	
	
	

}
