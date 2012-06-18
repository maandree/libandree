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
 * Keyboard input parsing tree
 *
 * @author  Mattias Andrée, <a href="mailto:maandree@kth.se">maandree@kth.se</a>
 */
public class KeyboardInputTree extends ParsingTree
{
    /**
     * Singlton constructor
     */
    private KeyboardInputTree()
    {
	super(0, 0, null);
	
	final ParsingTree esc = new ParsingTree(27, 0, this);
	final ParsingTree csi = new ParsingTree(91, 0, esc);
	
	new ParsingTree(65, InputBlackboard.KeyboardInputListener.KEY_ARROW_UP, csi);
	new ParsingTree(66, InputBlackboard.KeyboardInputListener.KEY_ARROW_DOWN, csi);
	new ParsingTree(67, InputBlackboard.KeyboardInputListener.KEY_ARROW_RIGHT, csi);
	new ParsingTree(68, InputBlackboard.KeyboardInputListener.KEY_ARROW_LEFT, csi);
	// 27  91  49  59  50  *  =  shift
	// 27  91  *+32           =  shift
	// 27  91  49  59  51  *  =  alt
	// 27  27  91  *          =  alt
	// 27  27  91  *+32       =  alt+shift
	// 27  91  49  59  52  *  =  alt+shift
	// 27  91  49  59  53  *  =  ctrl
	// 27  79  *+32           =  ctrl
	//                 54     =  shift+ctrl
	//                 55     =  alt+ctrl
	// 27  27  79  *+32       =  alt+ctrl
	//                 56     =  shift+alt+ctrl
	
	final ParsingTree csi_91 = new ParsingTree(91, 0, csi);
	final ParsingTree csi_49 = new ParsingTree(49, 0, csi);
	final ParsingTree csi_49_55 = new ParsingTree(55, 0, csi_49);
	final ParsingTree csi_49_56 = new ParsingTree(56, 0, csi_49);
	final ParsingTree csi_49_57 = new ParsingTree(57, 0, csi_49);
	final ParsingTree csi_50 = new ParsingTree(50, 0, csi);
	final ParsingTree csi_50_48 = new ParsingTree(48, 0, csi_50);
	final ParsingTree csi_50_49 = new ParsingTree(49, 0, csi_50);
	final ParsingTree csi_50_51 = new ParsingTree(51, 0, csi_50);
	final ParsingTree csi_50_52 = new ParsingTree(52, 0, csi_50);
	new ParsingTree(65, InputBlackboard.KeyboardInputListener.KEY_F1, csi_91);
	new ParsingTree(66, InputBlackboard.KeyboardInputListener.KEY_F2, csi_91);
	new ParsingTree(67, InputBlackboard.KeyboardInputListener.KEY_F3, csi_91);
	new ParsingTree(68, InputBlackboard.KeyboardInputListener.KEY_F4, csi_91);
	new ParsingTree(69, InputBlackboard.KeyboardInputListener.KEY_F5, csi_91);
	new ParsingTree(126, InputBlackboard.KeyboardInputListener.KEY_F6, csi_49_55);
	new ParsingTree(126, InputBlackboard.KeyboardInputListener.KEY_F7, csi_49_56);
	new ParsingTree(126, InputBlackboard.KeyboardInputListener.KEY_F8, csi_49_57);
	new ParsingTree(126, InputBlackboard.KeyboardInputListener.KEY_F9, csi_50_48);
	new ParsingTree(126, InputBlackboard.KeyboardInputListener.KEY_F10, csi_50_49);
	new ParsingTree(126, InputBlackboard.KeyboardInputListener.KEY_F11, csi_50_51);
	new ParsingTree(126, InputBlackboard.KeyboardInputListener.KEY_F12, csi_50_52);
	//TODO f1-f8 + shift
	//  csi  50  53  126
	//  csi  50  54  126
	//  csi  50  56  126
	//  csi  50  57  126
	//  csi  51  49  126
	//  csi  51  50  126
	//  csi  51  51  126
	//  csi  51  52  126
	
	//TODO esc
	// esc = 27
	// alt+esc = 27  27
	
	//TODO backspace
	// \b = 127
	//    = 8
	// alt+\b = 27  127
	//        = 194  136
	
	// home =         49
	// ins  = 27  91  50  126
	// del  =         51
	// end  =         52
	// pgup =         53
	// pgdn =         54
	// shift+del  =  27  91  50  59  50  126
	// shift+home =  27  91  49  59  50  70
	// shift+end  =  27  91  49  59  50  72
	// shift+del  =  27  91  55  51  36
	// shift+home =  27  91  55  55  36
	// shift+end  =  27  91  55  56  36
	// alt+home  =  27  79  70
	// alt+end   =  27  79  72
	// alt+home  =  27  91  49  59  51  70
	// alt+end   =  27  91  49  59  51  72
	// alt+*     =  27  91  *  59  51  126
	// alt+home  =  27  27  91  55  126
	// alt+ins   =  27  27  91  50  126
	// alt+del   =  27  27  91  51  126
	// alt+end   =  27  27  91  56  126
	// alt+pgup  =  27  27  91  53  126
	// alt+pgdn  =  27  27  91  54  126
	// shift+alt+*  =  .
	// ctrl+*  =  .
	// shift+ctrl+*  =  .
	// alt+ctrl+*  =  .
	// alt+shift+ctrl+*  =  .
	
	// tab  =  9
	// shift+tab  =  27  9
	// shift+tab  =  27  91  90
	
	// alt/ctrl a-z,A-Z,0-9
    }


    /**
     * Gets the only instance of the class
     * 
     * @return  The only instance of the class
     */
    public static KeyboardInputTree getInstance()
    {
	return instance;
    }
    
    
    
    /**
     * The only instance of the class
     */
    private static final KeyboardInputTree instance = new KeyboardInputTree();
    
}

