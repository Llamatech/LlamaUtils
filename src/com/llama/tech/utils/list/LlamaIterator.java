/*
 * LlamaIterator.java
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


package com.llama.tech.utils.list;

import java.io.Serializable;
import java.util.Iterator;
import java.util.NoSuchElementException;

public interface LlamaIterator<T> extends Serializable, Iterator<T>
{
    public boolean hasNext();
    public boolean hasPrevious();
    public T next() throws NoSuchElementException;
    public T previous() throws NoSuchElementException;

}
