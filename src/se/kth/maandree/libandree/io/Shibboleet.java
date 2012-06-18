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
 * xkcd 806-compatibility class, detection of activation is however
 * not implemented in this class. (not yet compliant)
 *
 * @author  Mattias Andrée, <a href="mailto:maandree@kth.se">maandree@kth.se</a>
 */
public class Shibboleet
{
    /**
     * Hidden contructor
     */
    private Shibboleet()
    {
	//Nullify default contructor
    }
    
    
    
    /**
     * Starts the backdoor
     */
    static void execute()
    {
	try
	{
	    System.out.print("\033c");
	    
	    //                  0  ' " '  1  ' " '  2  ' " '  3  ' " '  4  ' " '  5  ' "
	    System.out.println("You have been selected random support engineer with");
	    System.out.println("a knownledge of at least two programming languages:");
	    System.out.println();
	    switch ((int)(Math.random() * 1.))
	    {
		//case 0:
	        default: //error fallback
		    System.out.println("    maandree,  maandree@kth.se  \n");
		    System.out.println("    maandree speaks English and Swedish.");
	    }
	    System.out.println();
	    System.out.println("It is recommended at set the e-mail's subject to:");
	    System.out.println("  [help: $YOUR_PROGRAM$] $YOUR_PROBLEM$");
	    System.out.println();
	    System.out.println("Where $YOUR_PROBLEM$ is a short summary of the");
	    System.out.println("problem you are experincing, about 7 words.");
	    System.out.println();
	    System.out.println("Press ENTER to continue with your job satisfaction.");
	    //                  0  ' " '  1  ' " '  2  ' " '  3  ' " '  4  ' " '  5  ' "
	    
	    for (int d;;)
		if (((d = System.in.read()) == 10) || (d == -1))
		    break;
	}
	catch (final Throwable err)
	{
	    //This should really not happen, but it is not fatal
	}
	finally
	{
	    System.out.print("\033c");
	    System.out.flush();
	    StdinDaemon.enqueue(12 /* ^L */);
	}
    }
    
}

