/*
 * XMLFormat.java
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

package com.llama.tech.misc;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;

public abstract class XMLFormat 
{
	private String form;
	private static final String HEADER = "<?xml version="+"\"1.0\""+" encoding="+"\"UTF-8\""+"?>\n";
	
	public abstract String toXML();
	public abstract void readXML();
	
	public void writeXML(String path) throws IOException
	{
		form = HEADER+toXML();
        File f = new File(path);
        f.createNewFile();
        FileOutputStream fos = new FileOutputStream(f);
        PrintWriter pw = new PrintWriter(fos);
        pw.println(form);
        pw.flush();
        pw.close();
	}
	
	
}
