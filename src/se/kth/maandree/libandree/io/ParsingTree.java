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
 * Parsing tree
 *
 * @author  Mattias Andrée, <a href="mailto:maandree@kth.se">maandree@kth.se</a>
 */
public class ParsingTree
{
    /**
     * Constructor
     *
     * @param  key     The partial key
     * @param  value   The value
     * @param  parent  The parent node
     */
    public ParsingTree(final int key, final long value, final ParsingTree parent)
    {
	this.key = key;
	this.value = value;
	if ((this.parent = parent) != null)
	    this.parent.children.add(this);
    }
    
    
    
    /**
     * The partial key
     */
    public final int key;
    
    /**
     * The value
     */
    public final long value;
    
    /**
     * The parent node
     */
    public final ParsingTree parent;
    
    /**
     * The node's children
     */
    protected final ArrayList<ParsingTree> children = new ArrayList<ParsingTree>();
    
    
    
    /**
     * Gets the next node in the parsing tree
     *
     * @param  key  The next node in the parsing tree, <code>null</code> if not found
     */
    public final ParsingTree getNext(final int key)
    {
	for (final ParsingTree child : children)
	    if (child.key == key)
		return child;
	
	return null;
    }
    
}

