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
 * En-Linux VT output filter, a filter which tries to convert transform
 * text to text which as few character not support by a standard Linux VT
 * as possible.  Characters not not transformed to their closes proper
 * ASCII character/text, but rather to the the closes ASCII text which
 * to how the character is displayed in a USC compatible terminal.
 *
 * @author  Mattias Andrée, <a href="mailto:maandree@kth.se">maandree@kth.se</a>
 */
public class EnLinuxVTOutputFilter implements OutputFilter
{
    //Has default constructor
    
    
    
    /**
     * The next output filter in the chain
     */
    private OutputFilter nextFilter = null;
    
    
    /**
     * Transformation map
     */
    private static final HashMap<Integer, int[]> charMap;
    
    
    
    /**
     * Class initialiser
     */
    static
    {
	charMap = new HashMap<Integer, int[]>();
	
	final String[] items =
	        {
		    "   ",  "   ", //line separator,  paragraph separator,  medium mathematical space
		    "‐‑‒–—―",  "------",   //hyphen,  non-breaking hyphen,  figure dash,  en dash,  em dash,  horizontal bar
		    "‖",  "||",    //double vertical line
		    "‗",  "_",     //double low line
		    "‘’",  "`'",   //left single quotation mark,  right single quotation mark
		    "‚‛",  ",`",   //single low-9 qoutation mark,  single high-reversed-9 qoutation mark
		    "“",  "``",    //left double quotation mark
		    "”",  "''",    //right double quotation mark
		    "„",  ",,",    //double low-9 qoutation mark,
		    "‟",  "``",    //double high-reversed-9 qoutation mark
		    "•‣",  "**",   //bullet,  trianglar bullet
		    "․‧",  ".-",   //one dot leader,  hyphenation dot
		    "‥",  "..",    //two dot leader
		    "…",  "...",   //horizontal ellipsis
		    "‰",  "%.",    //per mille sign
		    "‱",  "%..",   //per ten thousand sign
		    "⁒",  "%",     //commercial minus sign
		    "⁅⁆",  "[]",   //left square bracket with quill,  right square bracket with quill
		    "‹›※⁃⁄⁓⁕⁜⁋⁎⁏⁌⁍",  "<>**/--*P*;<>",
		    "′‵",  "'`",    "″",  "''",    "‶",  "``",    "‴",  "'''",    "‷",  "```",  "⁗",  "''''",
		    "⁑",  "**",    "⁂",  "***",
		    "‼",  "!!",    "⁇",  "??",    "⁈",  "?!",    "⁉",  "!?",    "‽",  "!?",    "⸘",  "¿¡",    "⸮",  "?",
		    "⸌⸍⸏⟨⟩⟮⟯⧼⧽⧸⧹∕∖∗∘∙∁∅−⌀⎯⍥",  "`'_<>)(<>/\\/\\***CØ-ø_ö",  
		    "≔",  ":=",    "≕",  "=:",    "∹",  "-:",    "∨∧∪∩⋄⋅⋆⋎⋏≺≻⋿∼",  "v^un.**^v<>E~",
		    "⟦",  "[[",    "⟧",  "]]",    "⟪",  "<<",    "⟫",  ">>",    "⸨",  "((",  
		    "⟬",  "(|",    "⟭",  "|)",    "⦃",  "{{",    "⦄",  "}}",    "⸩",  "))",  
		    "ⅅⅆⅇⅈⅉ",  "Ddeij",    "≪",  "<<",    "≫",  ">>",
		    "ℂℇℊℋℌℍℎℐℑℒℓℕ№℘ℙℚℛℜℝ℞℟ℤℨℬℭ℮ℯℰℱℳℴℹ℣",  "CEgHHHhIILlN#pPQRRRRRZzBCeeEFMoiV",  
		    "℀",  "a/c",    "℁",  "a/s",    "⅍",  "A/S",    "℅",  "c/o",    "℆",  "c/u",
		    "℃",  "°C",	   "℄",  "CL",    "℉",  "°F",    "℔",  "lb",    "℗",  "(P)",    "℠",  "SM",
		    "℡",  "Tel",    "™",  "TM",    "℻",  "Fax",    "⅌",  "per",
		    "⋘",  "<<<",    "⋙",  ">>>",  
		};
	
	for (int i = 0, n = items.length; i < n;)
	    if (items[i].length() == 1)
	    {
		int m;
		Integer key = Integer.valueOf((int)(items[i++].charAt(0)));
		String value = items[i++];
		int[] array = new int[m = value.length()];
		
		for (int j = 0; j < m; j++)
		    array[j] = (int)(value.charAt(j));
		
		charMap.put(key, array);
	    }
	    else
	    {
		String keys = items[i++];
		int m = keys.length();
		String values = items[i++];
		
		for (int j = 0; j < m; j++)
		{
		    Integer key = Integer.valueOf((int)(keys.charAt(j)));
		    int[] value = { values.charAt(j) };
		    charMap.put(key, value);
		}
	    }
	
	int alpha = 0x1D400;
	for (int i = 0; i < 14; i++)
	{
	    for (char c = 'A'; c <= 'Z'; c++)
		charMap.put(new Integer(alpha++), new int[] { (int)c });
	    for (char c = 'a'; c <= 'z'; c++)
		charMap.put(new Integer(alpha++), new int[] { (int)c });
	}
	
	int num = 0x1D7CE;
	for (int i = 0; i < 5; i++)
	    for (char c = '0'; c <= '9'; c++)
		charMap.put(new Integer(num++), new int[] { (int)c });
    }
    
    
    
    /**
     * {@inheritDoc}
     */
    @Override
    public void filterWrite(final int b)
    {
	if (this.nextFilter == null)
	    throw new RuntimeException("Premature end of output filter chain");
	
	int[] cs;
	if ((cs = charMap.get(new Integer(b))) != null)
	    for (int c : cs)
	        this.nextFilter.filterWrite(c);
	else
	    this.nextFilter.filterWrite(b);
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

