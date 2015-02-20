package com.llama.tech.utils.test.dict;

import com.llama.tech.utils.dict.Dictionary;
import com.llama.tech.utils.dict.LlamaDict;

import junit.framework.TestCase;

public class DictionaryTest extends TestCase
{
	private Dictionary<String, Integer> dict; 
	
	public void setUpEscenario1()
	{
		dict = new LlamaDict<String, Integer>(0);
	}
	
	public void setUpEscenario2()
	{
		
	}
}
