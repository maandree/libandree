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
 * Filtered standard error output stream singleton class
 *
 * @author  Mattias Andrée, <a href="mailto:maandree@kth.se">maandree@kth.se</a>
 */
public class Stderr extends OutputStream implements OutputFilter
{
    /**
     * Singleton constructor
     */
    private Stderr()
    {
	super();
	this.underlay = System.err;
	
	this.firstFilter = null;
	this.nextFilter = null;
	
	OutputFilter temp1 = this, temp2 = this;
	//final int os = OSIdentifier.getOS() & OSIdentifier.OS_MASK;
	//if ((os == OSIdentifier.GNU_LINUX) || (os == OSIdentifier.GNU_LINUX_LIBRE) || (os == OSIdentifier.GNU_HURD))
	{
	//    if (OSIdentifier.isLinuxVT())
	    {
		temp1.appendFilter(temp2 = new EnclumpUCSOutputFilter());
		temp2.appendFilter(temp1 = new EnLinuxVTOutputFilter());
		temp1.appendFilter(temp2 = new DeclumpUCSOutputFilter());
	    }
	}
	//else
	//{
	//    temp1.appendFilter(temp2 = new EnclumpUCSOutputFilter());
	//    temp2.appendFilter(temp1 = new OSIOutputFilter());
	//    temp1.appendFilter(temp2 = new CSIOutputFilter());
	//    temp2.appendFilter(temp1 = new ESCOutputFilter());
	//    temp1.appendFilter(temp2 = new DeclumpUCSOutputFilter());
	//}
	temp2.appendFilter(this);
    }
    
    
    /**
     * Gets the only instance of the class
     *
     * @return  The only instance of the class
     */
    public static Stderr getInstance()
    {
	return Stderr.instance;
    }
    

    
    /**
     * The only instance of the class
     */
    private static final Stderr instance = new Stderr();
    
    
    /**
     * The underlaying output stream
     */
    private final PrintStream underlay;
    
    /**
     * The first output filter in the output filter chain
     */
    private OutputFilter firstFilter;
    
    /**
     * The next output filter in the output filter chain
     */
    private OutputFilter nextFilter;
    
    
    
    /**
     * {@inheritDoc}
     */
    @Override
    public void flush()
    {
        this.underlay.flush();
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public void write(final int b)
    {
	if (this.nextFilter == null)
	    this.filterWrite(b);
	else
	    this.nextFilter.filterWrite(b);
    }
    
    /**
     * {@inheritDoc}
     */
    public void filterWrite(final int b)
    {
	this.underlay.write(b);
    }
    
    /**
     * {@inheritDoc}
     */
    public void appendFilter(final OutputFilter filter)
    {
	this.nextFilter = filter;
	if (this.firstFilter == null)
	    this.firstFilter = filter;
    }
    
}

