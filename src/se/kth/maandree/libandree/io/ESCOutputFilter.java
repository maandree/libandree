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
 * ESC output filter
 *
 * @author  Mattias Andrée, <a href="mailto:maandree@kth.se">maandree@kth.se</a>
 */
public class ESCOutputFilter implements OutputFilter
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
     * {@inheritDoc}
     */
    @Override
    public void filterWrite(final int b)
    {
	if (this.nextFilter == null)
	    throw new RuntimeException("Premature end of output filter chain");
	
	switch (state)
	{
	    case 0:
		if (b == '\033')
		    state++;
		else
		    this.nextFilter.filterWrite(b);
		break;
		
	    case 1:
		if (b == '%')
		    state++;
		else
		{
		    switch (b)
		    {
		        case '@':
		        case '<':
		        case '=':
		        case '>':
		        case '\\':
		        case '~':
			    break;
			
		        default:
			    if (('a' <= b) && (b <= 'z'))  break;
			    if (('A' <= b) && (b <= 'Z'))  break;
			    if (('0' <= b) && (b <= '9'))  break;
			    
			    this.nextFilter.filterWrite('\033');
			    this.nextFilter.filterWrite(b);
			    break;
		    }
		    state = 0;
		}
		break;
		
	    case 2:
		if (b != 'G')
		{
		    this.nextFilter.filterWrite('\033');
		    this.nextFilter.filterWrite('%');
		    this.nextFilter.filterWrite(b);
		}
		state = 0;
		break;
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

