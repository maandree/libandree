/**
 * libandree — Miscellaneous class library for Java
 * 
 * Copyright © 2007, 2012  Mattias Andrée (maandree@kth.se)
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package se.kth.maandree.libandree.io;


/**
 * UCS declumption output filter, a filter which converts an 32-bit
 * integer to a UTF-8 byte sequence for a UCS character (up to 31 bits).
 *
 * @author  Mattias Andrée, <a href="mailto:maandree@kth.se">maandree@kth.se</a>
 */
public class DeclumpUCSOutputFilter implements OutputFilter
{
    //Has default constructor
    
    
    
    /**
     * The next output filter in the chain
     */
    private OutputFilter nextFilter = null;
    
    
    
    /**
     * {@inheritDoc}
     */
    @Override
    public void filterWrite(final int b)
    {
	if (this.nextFilter == null)
	    throw new RuntimeException("Premature end of output filter chain");
	
	final long bb = ((long)b) & (long)(((int)-1) >>> 1);
	
	if (bb < (1 << 7))        //7
	{
	    this.nextFilter.filterWrite(b);
	}
	else if (bb < (1 << 11))  //5+6
	{
	    this.nextFilter.filterWrite(192 | (b >>> 6));
	    this.nextFilter.filterWrite(128 | (b & 63));
	}
	else if (bb < (1 << 16))  //4+6+6
	{
	    this.nextFilter.filterWrite(224 | (b >>> 12));
	    this.nextFilter.filterWrite(128 | ((b >> 6) & 63));
	    this.nextFilter.filterWrite(128 | (b & 63));
	}
	else if (bb < (1 << 21))  //3+6+6+6
	{
	    this.nextFilter.filterWrite(240 | (b >>> 18));
	    this.nextFilter.filterWrite(128 | ((b >> 12) & 63));
	    this.nextFilter.filterWrite(128 | ((b >> 6) & 63));
	    this.nextFilter.filterWrite(128 | (b & 63));
	}
	else if (bb < (1 << 26))  //2+6+6+6+6
	{
	    this.nextFilter.filterWrite(248 | (b >>> 24));
	    this.nextFilter.filterWrite(128 | ((b >> 18) & 63));
	    this.nextFilter.filterWrite(128 | ((b >> 12) & 63));
	    this.nextFilter.filterWrite(128 | ((b >> 6) & 63));
	    this.nextFilter.filterWrite(128 | (b & 63));
	}
	else                      //1+6+6+6+6+6
	{
	    this.nextFilter.filterWrite(252 | (b >>> 30));
	    this.nextFilter.filterWrite(128 | ((b >> 24) & 63));
	    this.nextFilter.filterWrite(128 | ((b >> 18) & 63));
	    this.nextFilter.filterWrite(128 | ((b >> 12) & 63));
	    this.nextFilter.filterWrite(128 | ((b >> 6) & 63));
	    this.nextFilter.filterWrite(128 | (b & 63));
	}
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public void appendFilter(final OutputFilter filter)
    {
	this.nextFilter = filter;
    }

}

