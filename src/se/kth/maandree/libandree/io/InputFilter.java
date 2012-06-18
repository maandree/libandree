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
 * Input filter interface
 *
 * @author  Mattias Andrée, <a href="mailto:maandree@kth.se">maandree@kth.se</a>
 */
public interface InputFilter
{
    /**
     * Gets the next byte which was not filtered
     *
     * @param  The next byte which was not filtered
     */
    public int filterRead();
    
    /**
     * Appends a filter to the filter chain
     *
     * @param  filter  The filter to append to the filter chain
     */
    public void appendFilter(final InputFilter filter);

}

