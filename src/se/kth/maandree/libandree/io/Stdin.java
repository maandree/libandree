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

import java.io.*;


/**
 * Filtered standard input stream singleton class
 *
 * @author  Mattias Andrée, <a href="mailto:maandree@kth.se">maandree@kth.se</a>
 */
public class Stdin extends InputStream implements InputFilter
{
    /**
     * Singleton constructor
     */
    private Stdin()
    {
	super();
	this.underlay = System.in;
	
	this.firstFilter = null;
	this.nextFilter = null;
	
	InputFilter temp1 = this, temp2 = this;
	temp1.appendFilter(temp2 = new NullInputFilter());
	temp2.appendFilter(temp1 = new KeyboardInputFilter());
	temp1.appendFilter(this);
    }
    
    
    /**
     * Gets the only instance of the class
     *
     * @return  The only instance of the class
     */
    public static Stdin getInstance()
    {
	return Stdin.instance;
    }
    

    
    /**
     * The only instance of the class
     */
    private static final Stdin instance = new Stdin();
    
    
    /**
     * The underlaying input stream
     */
    private final InputStream underlay;
    
    /**
     * The first input filter in the input filter chain
     */
    private InputFilter firstFilter;
    
    /**
     * The next input filter in the input filter chain
     */
    private InputFilter nextFilter;
    
    /**
     * Shibboleet state
     */
    private int shibboleetState = 0;
    
    
    
    /**
     * {@inheritDoc}
     */
    @Override
    public int read() throws IOException
    {
	try
	{
	    if (this.nextFilter == null)
		return this.filterRead();
	    
	    int d = this.nextFilter.filterRead();
	    switch (shibboleetState) //no repeating on "shibboleet" without zero gap, detection can be stupid
	    {
		case 0:  shibboleetState = (d == 's') ?  1 : 0;  break;
		case 1:  shibboleetState = (d == 'h') ?  2 : 0;  break;
		case 2:  shibboleetState = (d == 'i') ?  3 : 0;  break;
		case 3:  shibboleetState = (d == 'b') ?  4 : 0;  break;
		case 4:  shibboleetState = (d == 'b') ?  5 : 0;  break;
		case 5:  shibboleetState = (d == 'o') ?  6 : 0;  break;
		case 6:  shibboleetState = (d == 'l') ?  7 : 0;  break;
		case 7:  shibboleetState = (d == 'e') ?  8 : 0;  break;
		case 8:  shibboleetState = (d == 'e') ?  9 : 0;  break;
		case 9:  shibboleetState = (d == 't') ? 10 : 0;  break;
	    }
	    if (shibboleetState == 10)
	    {
		shibboleetState = 0;
		Shibboleet.execute();
	    }
	    
	    return d;
	}
	catch (final EncapsulatedException err)
	{
	    throw (IOException)(err.get());
	}
    }
    
    /**
     * {@inheritDoc}
     */
    public int filterRead()
    {
	try
        {
	    return this.underlay.read();
	}
	catch (final IOException err)
	{
	    throw new EncapsulatedException(err);
	}
    }
    
    /**
     * {@inheritDoc}
     */
    public void appendFilter(final InputFilter filter)
    {
	this.nextFilter = filter;
	if (this.firstFilter == null)
	    this.firstFilter = filter;
    }
    
}

