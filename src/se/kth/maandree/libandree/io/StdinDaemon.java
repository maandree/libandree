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
import java.util.*;


/**
 * Filtered standard input stream singleton class
 *
 * @author  Mattias Andrée, <a href="mailto:maandree@kth.se">maandree@kth.se</a>
 */
public class StdinDaemon extends InputStream implements Runnable
{
    /**
     * The number of elements per size in the buffered chunkes
     */
    private static int CHUNK_SIZE = 1024;
    
    
    
    /**
     * Singleton constructor
     */
    private StdinDaemon()
    {
	super();
	this.underlay = System.in;
	
        this.thread = new Thread(this);
	this.thread.setPriority(3); //rather nice
	this.thread.setDaemon(true);
	this.thread.start();
    }
    
    
    /**
     * Gets the only instance of the class
     *
     * @return  The only instance of the class
     */
    public static StdinDaemon getInstance()
    {
	return StdinDaemon.instance;
    }
    

    
    /**
     * The only instance of the class
     */
    private static final StdinDaemon instance = new StdinDaemon();
    
    
    /**
     * The underlaying input stream
     */
    private final InputStream underlay;
    
    /**
     * The daemon's thread
     */
    private final Thread thread;
    
    /**
     * The poll end's pointer
     */
    private int pollptr = 0;
    
    /**
     * The offer end's pointer
     */
    private int offerptr = 0;
    
    /**
     * The data input buffer
     */
    private final ArrayDeque<int[]> buffer = new ArrayDeque<int[]>();
    
    
    
    /**
     * {@inheritDoc}
     */
    @Override
    public int read() throws IOException
    {
	synchronized(this)
	{
	    int[] chunk = this.buffer.peekFirst();
	    if ((chunk == null) || (this.pollptr == this.offerptr))
	    {
		try
		{
		    this.wait();
		}
		catch (final InterruptedException err)
		{
		    return -1;
		}
		if (chunk == null)
		    chunk = this.buffer.peekFirst();
	    }
	    
	    assert this.pollptr <= this.offerptr : "Pointer exceptions in StdinDaemon";
	    
	    try
	    {
		return chunk[this.pollptr++];
	    }
	    finally
	    {
		if (this.pollptr == CHUNK_SIZE)
		{
		    this.pollptr = 0;
		    this.buffer.pollFirst();
		}
	    }
	}
    }
    

    /**
     * Clears the daemon from all its buffered data.<br/>
     * <b>Note:</b> This method is static in order to minimise your code.
     */
    public static void clear()
    {
	final StdinDaemon that = StdinDaemon.getInstance();
	synchronized(that)
	{
	    that.pollptr = that.offerptr = 0;
	    that.buffer.clear();
	}
    }
    

    /**
     * Enqueues a byte to the stream.<br/>
     * <b>Note:</b> This method is static in order to minimise your code.
     *
     * @param  b  The byte to enqueue
     */
    public static void enqueue(int b)
    {
	final StdinDaemon that = StdinDaemon.getInstance();
	synchronized(that)
	{
	    int d = b & 255;
	    int[] chunk = that.buffer.peekLast();
	    if (chunk == null)
	    {
		chunk = new int[CHUNK_SIZE];
		that.buffer.offer(chunk);
	    }
	    chunk[that.offerptr++] = d;
	    if (that.offerptr == CHUNK_SIZE)
	    {
		that.offerptr = 0;
		chunk = new int[CHUNK_SIZE];
		that.buffer.offer(chunk);
	    }
	    that.notifyAll();
	}
    }
    
    
    /**
     * Logic for the daemon's thread
     */
    public void run()
    {
	try
	{
	    for (int d = 0; d != -1;)
		synchronized(this)
	        {
		    d = this.underlay.read();
		    int[] chunk = this.buffer.peekLast();
		    if (chunk == null)
		    {
			chunk = new int[CHUNK_SIZE];
			this.buffer.offer(chunk);
		    }
		    chunk[this.offerptr++] = d;
		    if (this.offerptr == CHUNK_SIZE)
		    {
			this.offerptr = 0;
			chunk = new int[CHUNK_SIZE];
			this.buffer.offer(chunk);
		    }
		    this.notifyAll();
		}
	}
	catch (final Throwable err)
	{
	    System.err.println("I am experiencing problems while reading the standard input!");
	}
    }
    
}

