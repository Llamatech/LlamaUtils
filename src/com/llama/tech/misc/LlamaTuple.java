package com.llama.tech.misc;

import java.util.Iterator;

import com.llama.tech.utils.list.LlamaArrayList;

@SuppressWarnings("rawtypes")
public class LlamaTuple<X, Y> implements Comparable<LlamaTuple<X, Y>>, Iterable
{ 
    public final X x; 
    public final Y y; 
    
    public LlamaTuple(X x, Y y) 
    { 
        this.x = x; 
        this.y = y; 
    }

    @Override
    public String toString() {
        return "(" + x + "," + y + ")";
    }

    @SuppressWarnings("unchecked")
	@Override
    public boolean equals(Object other) {
        if (other == null) {
            return false;
        }
        if (other == this) {
            return true;
        }
        if (!(other instanceof LlamaTuple)){
            return false;
        }
        LlamaTuple<X,Y> other_ = ((LlamaTuple<X,Y>) other);
        return other_.x == this.x && other_.y == this.y;
    }

    @Override
    public int hashCode() 
    {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((x == null) ? 0 : x.hashCode());
        result = prime * result + ((y == null) ? 0 : y.hashCode());
        return result;
    }

	@Override
	public int compareTo(LlamaTuple<X, Y> o) 
	{
		if(o.x == this.x && o.y == this.y)
		{
			return 0;
		}
		
		return -1;
	}

	@SuppressWarnings({ "unchecked" })
	@Override
	public Iterator iterator() 
	{
		LlamaArrayList l = new LlamaArrayList(2);
		l.addAlFinal(x);
		l.addAlFinal(y);
		return l.iterator();
	}
}