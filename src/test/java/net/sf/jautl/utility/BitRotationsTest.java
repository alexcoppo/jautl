/*
    Copyright (c) 2000-2012 Alessandro Coppo
    All rights reserved.

    Redistribution and use in source and binary forms, with or without
    modification, are permitted provided that the following conditions
    are met:
    1. Redistributions of source code must retain the above copyright
       notice, this list of conditions and the following disclaimer.
    2. Redistributions in binary form must reproduce the above copyright
       notice, this list of conditions and the following disclaimer in the
       documentation and/or other materials provided with the distribution.
    3. The name of the author may not be used to endorse or promote products
       derived from this software without specific prior written permission.

    THIS SOFTWARE IS PROVIDED BY THE AUTHOR ``AS IS'' AND ANY EXPRESS OR
    IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
    OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED.
    IN NO EVENT SHALL THE AUTHOR BE LIABLE FOR ANY DIRECT, INDIRECT,
    INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT
    NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
    DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY
    THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
    (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF
    THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
*/
package net.sf.jautl.utility;

import org.junit.*;
import static org.junit.Assert.*;

public class BitRotationsTest {
	@Test
	public void testLeftString() {
		String bitfields[] = {
		//   01234567
			"00000001",
			"00000010",
			"00000100",
			"00001000",
			"00010000",
			"00100000",
			"01000000",
			"10000000"
		};
		
		for (int places = 0; places < 8; places++) {
			String rvalue = BitRotations.left(bitfields[0], places);
			assertTrue(rvalue.equals(bitfields[places]));
		}
	}

	@Test
	public void testRightString() {
		String bitfields[] = {
		//   01234567
			"10000000",
			"01000000",
			"00100000",
			"00010000",
			"00001000",
			"00000100",
			"00000010",
			"00000001"
		};
		
		for (int places = 0; places < 8; places++) {
			String rvalue = BitRotations.right(bitfields[0], places);
			assertTrue(rvalue.equals(bitfields[places]));
		}
	}

	@Test
	public void testLeftByte() {
		String bitpattern = "00010001";
		byte original = (byte)Integer.parseInt(bitpattern, 2);
		
		for (int places = 0; places < 8; places++) {
			String rvalue = BitRotations.left(bitpattern, places);
			byte rotated = (byte)Integer.parseInt(rvalue, 2);
			
			byte result = BitRotations.left(original, places);
			assertTrue(result == rotated);
		}
	}

	@Test
	public void testRightByte() {
		String bitpattern = "00010001";
		byte original = (byte)Integer.parseInt(bitpattern, 2);
		
		for (int places = 0; places < 8; places++) {
			String rvalue = BitRotations.right(bitpattern, places);
			byte rotated = (byte)Integer.parseInt(rvalue, 2);
			
			byte result = BitRotations.right(original, places);
			assertTrue(result == rotated);
		}
	}

	@Test
	public void testLeftShort() {
		String bitpattern = "0000000100000001";
		short original = (short)Integer.parseInt(bitpattern, 2);
		
		for (int places = 0; places < 16; places++) {
			String rvalue = BitRotations.left(bitpattern, places);
			short rotated = (short)Integer.parseInt(rvalue, 2);
			
			short result = BitRotations.left(original, places);
			assertTrue(result == rotated);
		}
	}

	public void testRightShort() {
		String bitpattern = "0000000100000001";
		short original = (short)Integer.parseInt(bitpattern, 2);
		
		for (int places = 0; places < 16; places++) {
			String rvalue = BitRotations.right(bitpattern, places);
			short rotated = (short)Integer.parseInt(rvalue, 2);
			
			short result = BitRotations.right(original, places);
			assertTrue(result == rotated);
		}
	}

	@Test
	public void testLeftInt() {
		String bitpattern = "00000001000000010000000100000001";
		int original = Integer.parseInt(bitpattern, 2);
		
		for (int places = 0; places < 32; places++) {
			String rvalue = BitRotations.left(bitpattern, places);
			int rotated = (int)Long.parseLong(rvalue, 2);
			
			int result = BitRotations.left(original, places);
			assertTrue(result == rotated);
		}
	}

	@Test
	public void testRightInt() {
		String bitpattern = "00000001000000010000000100000001";
		int original = Integer.parseInt(bitpattern, 2);
		
		for (int places = 0; places < 32; places++) {
			String rvalue = BitRotations.right(bitpattern, places);
			int rotated = (int)Long.parseLong(rvalue, 2);
			
			int result = BitRotations.right(original, places);
			assertTrue(result == rotated);
		}
	}
}
