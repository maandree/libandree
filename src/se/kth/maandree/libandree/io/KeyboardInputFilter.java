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

import java.util.*;


/**
 * Keyboard input filter
 *
 * @author  Mattias Andrée, <a href="mailto:maandree@kth.se">maandree@kth.se</a>
 */
public class KeyboardInputFilter implements InputFilter
{
    //Has default constructor
    
    
    
    /**
     * The next input filter in the chain
     */
    private InputFilter nextFilter = null;
    
    /**
     * The parsing tree
     */
    private ParsingTree tree = KeyboardInputTree.getInstance();
    
    /**
     * Forward deque
     */
    private final ArrayDeque<int[]> stack = new ArrayDeque<int[]>();
    
    
    
    /**
     * {@inheritDoc}
     */
    public int filterRead()
    {
	if (this.nextFilter == null)
	    throw new RuntimeException("Premature end of input filter chain");
	
	if (stack.size() > 0)
	    return stack.pollFirst()[0];
	
	int next = this.nextFilter.filterRead();
	ParsingTree node = tree;
	
	for (;;)
	{
	    node = node.getNext(next);
	    if (node == null)
	    {
		node = tree;
		while (node.key != 0)
		{
		    stack.offerFirst(new int[] { node.key });
		    node = node.parent;
		}
		
		return stack.pollFirst()[0];
	    }
	    if (node.value != 0L)
	    {
		InputBlackboard.fireKeyboardInput((int)(node.value >>> 32), (int)((node.value << 32) >>> 32));
		while (node.key != 0)
		    node = node.parent;
	    }
	}
    }
    
    /**
     * {@inheritDoc}
     */
    public void appendFilter(final InputFilter filter)
    {
	this.nextFilter = filter;
    }

}

