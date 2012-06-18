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
     * The current state of the filter
     */
    private int state = 0;
    
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
	
	//Error detection is not implemented
	if (state == 0)
	{
	    if ((b & 128) == 0)
		this.nextFilter.filterWrite(b);
	    else
		do
		{
		    len = 0;
		    if ((b & 128) != 0)  len++;  else  break;
		    if ((b &  64) != 0)  len++;  else  break;
		    if ((b &  32) != 0)  len++;  else  break;
		    if ((b &  16) != 0)  len++;  else  break;
		    if ((b &   8) != 0)  len++;  else  break;
		    if ((b &   4) != 0)  len++;
		}
		  while (false);
	    
	    data = (b << len) >> len;
	}
	else
	    data = (data << 6) | (b & 63);
	
	state++;
	if (state == len)
	{
	    state = 0;
	    this.nextFilter.filterWrite(b);
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

