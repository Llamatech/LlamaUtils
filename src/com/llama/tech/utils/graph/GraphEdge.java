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

public class GraphEdge<K extends Comparable<K>, V extends Comparable<V>, A> 
{
	private double weight;
	private A label;
	
	private GraphVertex<K, V,A> origin;
	private GraphVertex<K, V,A> destination;
	
	public GraphEdge(GraphVertex<K, V,A> from, GraphVertex<K, V,A> to, double weight, A label)
	{
		origin = from;
		destination = to;
		this.weight = weight;
		this.label = label;
	}
	
	public GraphVertex<K, V,A> getDestination() 
	{
		return destination;
	}
	
	public A getLabel() {
		return label;
	}

	public void setLabel(A label) {
		this.label = label;
	}

	public double getWeight()
	{
		return weight;
	}
	
	public GraphVertex<K, V,A> getOrigin() 
	{
		return origin;
	}

	public void setOrigin(GraphVertex<K, V,A> origin) 
	{
		this.origin = origin;
	}

	public void setDestination(GraphVertex<K, V,A> destination) 
	{
		this.destination = destination;
	}
	
	public void setWeight(double weight)
	{
		this.weight = weight;
	}
	
	
}
