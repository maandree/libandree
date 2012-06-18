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
 * Input blackboard
 *
 * @author  Mattias Andrée, <a href="mailto:maandree@kth.se">maandree@kth.se</a>
 */
public class InputBlackboard
{
    /**
     * No modifier
     */
    public static final int MODIFIER_NONE = 0;
    
    /**
     * Modifier: Control
     */
    public static final int MODIFIER_CTRL = 1;
    
    /**
     * Modifier: Shift
     */
    public static final int MODIFIER_SHIFT = 2;
    
    /**
     * Modifier: Alternative
     */
    public static final int MODIFIER_ALT = 4;
    
    /**
     * Modifier: Alternative Graphics
     */
    public static final int MODIFIER_ALTGR = 8;
    
    /**
     * Modifier: Super
     */
    public static final int MODIFIER_SUPER = 16;
    
    /**
     * Modifier: Meta
     */
    public static final int MODIFIER_META = 32;
    
    /**
     * Modifier: Level 5 modifier
     */
    public static final int MODIFIER_LEVEL_5 = 64;
    
    /**
     * Modifier: Level 9 modifier
     */
    public static final int MODIFIER_LEVEL_9 = 128;
    
    
    
    /**
     * Hidden constructor
     */
    private InputBlackboard()
    {
	//NUllify default constructor
    }



    /**
     * Registrered keyboard input action listeners
     */
    private static ArrayList<KeyboardInputListener> keyboardListeners = new ArrayList<KeyboardInputListener>();
    
    
    
    /**
     * Listener for keyboard input actions
     */
    public static interface KeyboardInputListener
    {
	/**
	 * Unrecognised key
	 */
	public static final int KEY_UNKNOWN = 0x00000000;
	
	
	/**
	 * Key group: navigation arrows
	 */
	public static final int KEY_GROUP_ARROW = 0x00000007;

	/**
	 * Key: up arrow
	 */
	public static final int KEY_ARROW_UP = 0x00000001;

	/**
	 * Key: down arrow
	 */
	public static final int KEY_ARROW_DOWN = 0x00000002;

	/**
	 * Key: left arrow
	 */
	public static final int KEY_ARROW_LEFT = 0x00000003;

	/**
	 * Key: right arrow
	 */
	public static final int KEY_ARROW_RIGHT = 0x00000004;
	
	
	/**
	 * Key group: F# (F1, F2, F3...)
	 */
	public static final int KEY_GROUP_F = 0x000000C8;

        /**
	 * Key: F1
	 */
	public static final int KEY_F1 = 0x00000008;

        /**
	 * Key: F2
	 */
	public static final int KEY_F2 = 0x00000010;

        /**
	 * Key: F3
	 */
	public static final int KEY_F3 = 0x00000018;

        /**
	 * Key: F4
	 */
	public static final int KEY_F4 = 0x00000020;

        /**
	 * Key: F5
	 */
	public static final int KEY_F5 = 0x00000028;

        /**
	 * Key: F6
	 */
	public static final int KEY_F6 = 0x00000030;

        /**
	 * Key: F7
	 */
	public static final int KEY_F7 = 0x00000038;

        /**
	 * Key: F8
	 */
	public static final int KEY_F8 = 0x00000040;

        /**
	 * Key: F9
	 */
	public static final int KEY_F9 = 0x00000048;

        /**
	 * Key: F10
	 */
	public static final int KEY_F10 = 0x00000050;

        /**
	 * Key: F11
	 */
	public static final int KEY_F11 = 0x00000058;

        /**
	 * Key: F12
	 */
	public static final int KEY_F12 = 0x00000060;

        /**
	 * Key: F13
	 */
	public static final int KEY_F13 = 0x00000068;

        /**
	 * Key: F14
	 */
	public static final int KEY_F14 = 0x00000070;

        /**
	 * Key: F15
	 */
	public static final int KEY_F15 = 0x00000078;

        /**
	 * Key: F16
	 */
	public static final int KEY_F16 = 0x00000080;

        /**
	 * Key: F17
	 */
	public static final int KEY_F17 = 0x00000088;

        /**
	 * Key: F18
	 */
	public static final int KEY_F18 = 0x00000090;

        /**
	 * Key: F19
	 */
	public static final int KEY_F19 = 0x00000098;

        /**
	 * Key: F20
	 */
	public static final int KEY_F20 = 0x000000A0;

        /**
	 * Key: F21
	 */
	public static final int KEY_F21 = 0x000000A8;

        /**
	 * Key: F22
	 */
	public static final int KEY_F22 = 0x000000B0;

        /**
	 * Key: F23
	 */
	public static final int KEY_F23 = 0x000000B8;

        /**
	 * Key: F24
	 */
	public static final int KEY_F24 = 0x000000C0;
	
	
	/**
	 * Key group: Misc.
	 */
	public static final int KEY_GROUP_MISC = 0x00000F00;
	
	/**
	 * Key: Escape (esc)
	 */
	public static final int KEY_MISC_ESC = 0x00000100;
	
	/**
	 * Key: System Request (sys rq) / Print screen (prt sc)
	 */
	public static final int KEY_MISC_SYSTEM_REQUEST = 0x00000200;
	
	/**
	 * Key: Scroll lock
	 */
	public static final int KEY_MISC_SCROLL_LOCK = 0x00000300;
	
	/**
	 * Key: Break / Pause
	 */
	public static final int KEY_MISC_BREAK = 0x00000400;
	
	/**
	 * Key: Caps lock
	 */
	public static final int KEY_MISC_CAPS_LOCK = 0x00000500;
	
	/**
	 * Key: Num lock
	 */
	public static final int KEY_MISC_NUM_LOCK = 0x00000600;
	
	/**
	 * Key: Menu
	 */
	public static final int KEY_MISC_MENU = 0x00000700;
	
	/**
	 * Key: Compose
	 */
	public static final int KEY_MISC_COMPOSE = 0x00000800;
	
	/**
	 * Key: Insert (ins)
	 */
	public static final int KEY_MISC_INSERT = 0x00000900;
	
	/**
	 * Key: Home
	 */
	public static final int KEY_MISC_HOME = 0x00000A00;
	
	/**
	 * Key: End
	 */
	public static final int KEY_MISC_END = 0x00000B00;
	
	/**
	 * Key: Page up (also known as Prior; pg up)
	 */
	public static final int KEY_MISC_PAGE_UP = 0x00000C00;
	
	/**
	 * Key: Page down (also known as Next; pg dn)
	 */
	public static final int KEY_MISC_PAGE_DOWN = 0x00000D00;
	
	/**
	 * Key: Begin (5 on keypad without numlock)
	 */
	public static final int KEY_MISC_BEGIN = 0x00000E00;
	
	
	/**
	 * Key group: Modifier
	 */
	public static final int KEY_GROUP_MODIFIER = 0x0000F000;
	
	/**
	 * Key: Left control (ctrl)
	 */
	public static final int KEY_MODIFIER_LEFT_CONTROL = 0x00001000;

	/**
	 * Key: Left shift (hollow arrow up)
	 */
	public static final int KEY_MODIFIER_LEFT_SHIFT = 0x00002000;
	
	/**
	 * Key: Left super (GNU, Linux or Windows &amp;c symboled key)
	 */
	public static final int KEY_MODIFIER_LEFT_SUPER = 0x00003000;
	
	/**
	 * Key: (Left) Alternative (alt) / Meta (actually if combined with shift)
	 */
	public static final int KEY_MODIFIER_ALTERNATIVE = 0x00004000;
	
	/**
	 * Key: Right control (ctrl)
	 */
	public static final int KEY_MODIFIER_RIGHT_CONTROL = 0x00005000;

	/**
	 * Key: Right shift (hollow arrow up)
	 */
	public static final int KEY_MODIFIER_RIGHT_SHIFT = 0x00006000;
	
	/**
	 * Key: Right super (GNU, Linux or Windows &amp;c symboled key)
	 */
	public static final int KEY_MODIFIER_RIGHT_SUPER = 0x00007000;
	
	/**
	 * Key: (Right) Alternative grashics (alt gr; alt graph)
	 */
	public static final int KEY_MODIFIER_ALTERNATIVE_GRAPHICS = 0x00008000;
	
	
	/**
	 * Key group:  Writing
	 */
	public static final int KEY_GROUP_WRITING = 0x00010000;
	
	/**
	 * Key: Tab (backtab if combined with shift)
	 */
	public static final int KEY_WRITING_TAB = 0x00010000;
	
	/**
	 * Key: Backspace
	 */
	public static final int KEY_WRITING_BACKSPACE = 0x00020000;
	
	/**
	 * Key: Delete (del)
	 */
	public static final int KEY_WRITING_DELETE = 0x00030000;
	
	/**
	 * Key: Space (no-breaking space if combined with altgr+shift)
	 */
	public static final int KEY_WRITING_SPACE = 0x00040000;
	
	/**
	 * Key: Enter (also known as Return)
	 */
	public static final int KEY_WRITING_ENTER = 0x00050000;
	
	/**
	 * Key: Enter (also known as Return) on key pad
	 */
	public static final int KEY_WRITING_KEY_PAD_ENTER = 0x00060000;
	
	
	/**
	 * Key group: Symbols
	 */
	public static final int KEY_GROUP_SYMBOLS = 0x00F80000;
	
	/**
	 * Key: Less (&lt;)
	 */
	public static final int KEY_SYMBOLS_LESS = 0x00080000;
	
	/**
	 * Key: Comma (,)
	 */
	public static final int KEY_SYMBOLS_COMMA = 0x00100000;
	
	/**
	 * Key: Dot (also known as Period; &lt;)
	 */
	public static final int KEY_SYMBOLS_DOT = 0x00180000;
	
	/**
	 * Key: Hyphen (-)
	 */
	public static final int KEY_SYMBOLS_HYPHEN = 0x00200000;
	
	/**
	 * Key: Section (§)
	 */
	public static final int KEY_SYMBOLS_SECTION = 0x00280000;
	
	/**
	 * Key: Plus (+)
	 */
	public static final int KEY_SYMBOLS_PLUS = 0x00300000;
	
	/**
	 * Key: Acute
	 */
	public static final int KEY_SYMBOLS_ACUTE = 0x00380000;
	
	/**
	 * Key: Diaeresis
	 */
	public static final int KEY_SYMBOLS_DIAERESIS = 0x00400000;
	
	/**
	 * Key: Asterics (*)
	 */
	public static final int KEY_SYMBOLS_ASTERICS = 0x00480000;
	
	/**
	 * Key: Å
	 */
	public static final int KEY_SYMBOLS_AA = 0x00500000;
	
	/**
	 * Key: Ä
	 */
	public static final int KEY_SYMBOLS_AE = 0x00580000;
	
	/**
	 * Key: Ö
	 */
	public static final int KEY_SYMBOLS_OE = 0x00600000;
	
	/**
	 * Key: Fraction on keypad
	 */
	public static final int KEY_SYMBOLS_KEY_PAD_FRAC = 0x00680000;
	
	/**
	 * Key: Times on keypad
	 */
	public static final int KEY_SYMBOLS_KEY_PAD_TIMES = 0x00700000;
	
	/**
	 * Key: Minus on keypad
	 */
	public static final int KEY_SYMBOLS_KEY_PAD_MINUS = 0x00780000;
	
	/**
	 * Key: Plus on keypad
	 */
	public static final int KEY_SYMBOLS_KEY_PAD_PLUS = 0x00800000;
	
	/**
	 * Key: Comma on keypad
	 */
	public static final int KEY_SYMBOLS_KEY_PAD_COMMA = 0x00880000;
	
	
	/**
	 * Key group: Alphabetical and numerical
	 */
	public static final int KEY_GROUP_ALPHANUM = 0xFF000000;
	
	/**
	 * Key: 0
	 */
	public static final int KEY_ALPHANUM_0 = 0x01000000;
	
	/**
	 * Key: 1
	 */
	public static final int KEY_ALPHANUM_1 = 0x02000000;
	
	/**
	 * Key: 2
	 */
	public static final int KEY_ALPHANUM_2 = 0x03000000;
	
	/**
	 * Key: 3
	 */
	public static final int KEY_ALPHANUM_3 = 0x04000000;
	
	/**
	 * Key: 4
	 */
	public static final int KEY_ALPHANUM_4 = 0x05000000;
	
	/**
	 * Key: 5
	 */
	public static final int KEY_ALPHANUM_5 = 0x06000000;
	
	/**
	 * Key: 6
	 */
	public static final int KEY_ALPHANUM_6 = 0x07000000;
	
	/**
	 * Key: 7
	 */
	public static final int KEY_ALPHANUM_7 = 0x08000000;
	
	/**
	 * Key: 8
	 */
	public static final int KEY_ALPHANUM_8 = 0x09000000;
	
	/**
	 * Key: 9
	 */
	public static final int KEY_ALPHANUM_9 = 0x0A000000;
	
	/**
	 * Key: A
	 */
	public static final int KEY_ALPHANUM_A = 0x0B000000;
	
	/**
	 * Key: B
	 */
	public static final int KEY_ALPHANUM_B = 0x0C000000;
	
	/**
	 * Key: C
	 */
	public static final int KEY_ALPHANUM_C = 0x0D000000;
	
	/**
	 * Key: D
	 */
	public static final int KEY_ALPHANUM_D = 0x0E000000;
	
	/**
	 * Key: E
	 */
	public static final int KEY_ALPHANUM_E = 0x0F000000;
	
	/**
	 * Key: F
	 */
	public static final int KEY_ALPHANUM_F = 0x10000000;
	
	/**
	 * Key: G
	 */
	public static final int KEY_ALPHANUM_G = 0x11000000;
	
	/**
	 * Key: H
	 */
	public static final int KEY_ALPHANUM_H = 0x12000000;
	
	/**
	 * Key: I
	 */
	public static final int KEY_ALPHANUM_I = 0x13000000;
	
	/**
	 * Key: J
	 */
	public static final int KEY_ALPHANUM_J = 0x14000000;
	
	/**
	 * Key: K
	 */
	public static final int KEY_ALPHANUM_K = 0x15000000;
	
	/**
	 * Key: L
	 */
	public static final int KEY_ALPHANUM_L = 0x16000000;
	
	/**
	 * Key: M
	 */
	public static final int KEY_ALPHANUM_M = 0x17000000;
	
	/**
	 * Key: N
	 */
	public static final int KEY_ALPHANUM_N = 0x18000000;
	
	/**
	 * Key: O
	 */
	public static final int KEY_ALPHANUM_O = 0x19000000;
	
	/**
	 * Key: P
	 */
	public static final int KEY_ALPHANUM_P = 0x1A000000;
	
	/**
	 * Key: Q
	 */
	public static final int KEY_ALPHANUM_Q = 0x1B000000;
	
	/**
	 * Key: R
	 */
	public static final int KEY_ALPHANUM_R = 0x1C000000;
	
	/**
	 * Key: S
	 */
	public static final int KEY_ALPHANUM_S = 0x1D000000;
	
	/**
	 * Key: T
	 */
	public static final int KEY_ALPHANUM_T = 0x1E000000;
	
	/**
	 * Key: U
	 */
	public static final int KEY_ALPHANUM_U = 0x1F000000;
	
	/**
	 * Key: V
	 */
	public static final int KEY_ALPHANUM_V = 0x20000000;
	
	/**
	 * Key: W
	 */
	public static final int KEY_ALPHANUM_W = 0x21000000;
	
	/**
	 * Key: X
	 */
	public static final int KEY_ALPHANUM_X = 0x22000000;
	
	/**
	 * Key: Y
	 */
	public static final int KEY_ALPHANUM_Y = 0x23000000;
	
	/**
	 * Key: Z
	 */
	public static final int KEY_ALPHANUM_Z = 0x24000000;
	
	/**
	 * Key: 0 on key pad
	 */
	public static final int KEY_ALPHANUM_KEY_PAD_0 = 0x41000000;
	
	/**
	 * Key: 1 on key pad
	 */
	public static final int KEY_ALPHANUM_KEY_PAD_1 = 0x42000000;
	
	/**
	 * Key: 2 on key pad
	 */
	public static final int KEY_ALPHANUM_KEY_PAD_2 = 0x43000000;
	
	/**
	 * Key: 3 on key pad
	 */
	public static final int KEY_ALPHANUM_KEY_PAD_3 = 0x44000000;
	
	/**
	 * Key: 4 on key pad
	 */
	public static final int KEY_ALPHANUM_KEY_PAD_4 = 0x45000000;
	
	/**
	 * Key: 5 on key pad
	 */
	public static final int KEY_ALPHANUM_KEY_PAD_5 = 0x46000000;
	
	/**
	 * Key: 6 on key pad
	 */
	public static final int KEY_ALPHANUM_KEY_PAD_6 = 0x47000000;
	
	/**
	 * Key: 7 on key pad
	 */
	public static final int KEY_ALPHANUM_KEY_PAD_7 = 0x48000000;
	
	/**
	 * Key: 8 on key pad
	 */
	public static final int KEY_ALPHANUM_KEY_PAD_8 = 0x49000000;
	
	/**
	 * Key: 9 on key pad
	 */
	public static final int KEY_ALPHANUM_KEY_PAD_9 = 0x4A000000;
	
	
	
	/**
	 * Invoked when the keyboard is being used
	 *
	 * @param  modifiers  The held down modifiers
	 * @param  key        The key
	 */
	public void onKeyboardInput(final int modifiers, final int key);
	
    }
    
    
    
    /**
     * Registers an keyboard input action listeners
     *
     * @param  listener  The listener
     */
    public static void addKeyboardInputListener(final KeyboardInputListener listener)
    {
	keyboardListeners.add(listener);
    }
    
    /**
     * Unregisters an keyboard input action listeners
     *
     * @param  listener  The listener
     */
    public static void removeKeyboardInputListener(final KeyboardInputListener listener)
    {
	keyboardListeners.remove(listener);
    }
    
    /**
     * Invoke when the keyboard is being used
     *
     * @param  modifiers  The held down modifiers
     * @param  key        The key
     */
    public static void fireKeyboardInput(final int modifiers, final int key)
    {
	for (final KeyboardInputListener keyboardListener : keyboardListeners)
	    keyboardListener.onKeyboardInput(modifiers, key);
    }

}

