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
 * UCS enclumption output filter, a filter which converts an UTF-8
 * byte sequence for a UCS character (up to 31 bits) to an 32-bit integer.
 *
 * @author  Mattias Andrée, <a href="mailto:maandree@kth.se">maandree@kth.se</a>
 */
public class EnclumpUCSOutputFilter implements OutputFilter
{
    //Has default constructor
    
    
    
    /**
     * The next output filter in the chain
     */
    private OutputFilter nextFilter = null;
    
    /**
     * The length of the sequence
     */
    private int len = 0;
    
    /**
     * The gathered character data
     */
    private int data = 0;
    
    
    
    /**
     * {@inheritDoc}
     */
    @Override
    public void filterWrite(final int b)
    {
	if (this.nextFilter == null)
	    throw new RuntimeException("Premature end of output filter chain");
	
	if ((b & 0x80) == 0)
	    this.nextFilter.filterWrite(b);
	else if ((b & 0xC0) == 0xC0)
	{
	    len = 0;
	    data = b;
	    while ((data & 0x80) == 0x80)
	    {
		len++;
		data <<= 1;
	    }
	    data = (data & 0xFF) >> len--;
	}
	else
	    if (len > 0)
	    {
		data = (data << 6) | (b & 63);
		if (--len == 0)
		    this.nextFilter.filterWrite(data);
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

