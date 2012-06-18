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
 * Exception encapsultion class
 *
 * @author  Mattias Andrée, <a href="mailto:maandree@kth.se">maandree@kth.se</a>
 */
class EncapsulatedException extends RuntimeException
{
    /**
     * Constructor
     *
     * @param  err  The exception to encapsulate
     */
    @SuppressWarnings("hiding")
    public EncapsulatedException(final Exception err)
    {
	this.err = err;
    }
    
    
    
    /**
     * The encapsulated exception
     */
    private Exception err;
    
    
    
    /**
     * Gets the encapsulated exception
     *
     * @return  The encapsulated exception
     */
    public Exception get()
    {
	return this.err;
    }

}

