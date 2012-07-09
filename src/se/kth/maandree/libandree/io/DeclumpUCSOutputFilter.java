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
     * Small data buffer
     */
    private final int[] buf = new int[6];
    
    
    
    /**
     * {@inheritDoc}
     */
    @Override
    public void filterWrite(final int b)
    {
	if (this.nextFilter == null)
	    throw new RuntimeException("Premature end of output filter chain");
	
	if (b < 0x80)
	    this.nextFilter.filterWrite(b);
	else
	{
	    int m = 0x100;
	    int d = b;
	    int ptr = 0;
	    for (;;)
	    {
		m |= m >> 1;
		this.buf[ptr++] = d & 63;
		d >>>= 6;
		if (d == 0)
		{
		    m >>= 1;
		    if ((m & this.buf[ptr - 1]) == 0)
			this.buf[ptr - 1] |= (m << 1) & 0xFF;
		    else
			this.buf[ptr++] = m;
		    break;
		}
	    }
	    
	    while (ptr > 0)
		this.nextFilter.filterWrite(this.buf[--ptr] | 128);
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

