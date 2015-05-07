/*
 * GraphEdge.java
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

package com.llama.tech.utils.graph;

public class GraphEdge<K extends Comparable<K>, V extends Comparable<V>> 
{
	private double weight;
	
	private GraphVertex<K, V> origin;
	private GraphVertex<K, V> destination;
	
	public GraphEdge(GraphVertex<K, V> from, GraphVertex<K, V> to, double weight)
	{
		origin = from;
		destination = to;
		this.weight = weight;
	}
	
	public GraphVertex<K, V> getDestination() 
	{
		return destination;
	}
	
	public double getWeight()
	{
		return weight;
	}
	
	public GraphVertex<K, V> getOrigin() 
	{
		return origin;
	}

	public void setOrigin(GraphVertex<K, V> origin) 
	{
		this.origin = origin;
	}

	public void setDestination(GraphVertex<K, V> destination) 
	{
		this.destination = destination;
	}
	
	public void setWeight(double weight)
	{
		this.weight = weight;
	}
	
	
}
