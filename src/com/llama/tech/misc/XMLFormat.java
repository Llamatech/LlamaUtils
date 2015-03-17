package com.llama.tech.misc;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;

public abstract class XMLFormat 
{
	private String form;
	private String path;
	private static final String HEADER = "<?xml version="+"1.0"+" encoding="+"UTF-8"+"?>\n";
	
	public abstract String toXML();
	public abstract void readXML();
	
	public void writeXML() throws FileNotFoundException
	{
		form = HEADER+toXML();
        File f = new File(path);
        FileOutputStream fos = new FileOutputStream(f);
        PrintWriter pw = new PrintWriter(fos);
        pw.println(form);
        pw.flush();
        pw.close();
	}
	
	
}
