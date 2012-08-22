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
package net.sf.jautl.md;

import org.junit.*;

import static org.junit.Assert.*;

public class RIPEMD128Test {
	private DigestEngine md;

    @Before
	public void setUp() {
		md = new RIPEMD128();
		md.initiate();
	}
	
    @Test
	public void test1() {
		md.add("");
		md.terminate();
		String result = md.getAsHex();
		assertTrue(result.equalsIgnoreCase("CDF26213A150DC3ECB610F18F6B38B46"));
	}

    @Test
	public void test2() {
		md.add("a");
		md.terminate();
		String result = md.getAsHex();
		assertTrue(result.equalsIgnoreCase("86BE7AFA339D0FC7CFC785E72F578D33"));
	}

    @Test
	public void test3() {
		md.add("abc");
		md.terminate();
		String result = md.getAsHex();
		assertTrue(result.equalsIgnoreCase("C14A12199C66E4BA84636B0F69144C77"));
	}

    @Test
	public void test4() {
		md.add("message digest");
		md.terminate();
		String result = md.getAsHex();
		assertTrue(result.equalsIgnoreCase("9E327B3D6E523062AFC1132D7DF9D1B8"));
	}

    @Test
	public void test5() {
		md.add("abcdefghijklmnopqrstuvwxyz");
		md.terminate();
		String result = md.getAsHex();
		assertTrue(result.equalsIgnoreCase("FD2AA607F71DC8F510714922B371834E"));
	}

    @Test
	public void test6() {
		md.add("abcdbcdecdefdefgefghfghighijhijkijkljklmklmnlmnomnopnopq");
		md.terminate();
		String result = md.getAsHex();
		assertTrue(result.equalsIgnoreCase("A1AA0689D0FAFA2DDC22E88B49133A06"));
	}

    @Test
	public void test7() {
		md.add("ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789");
		md.terminate();
		String result = md.getAsHex();
		assertTrue(result.equalsIgnoreCase("D1E959EB179C911FAEA4624C60C5C702"));
	}

    @Test
	public void test8() {
		md.add("12345678901234567890123456789012345678901234567890123456789012345678901234567890");
		md.terminate();
		String result = md.getAsHex();
		assertTrue(result.equalsIgnoreCase("3F45EF194732C2DBB2C4A2C769795FA3"));
	}

    @Test
	public void test1M() {
        for (int index = 0; index < 1000000; index++)
            md.add("a");
		md.terminate();
		String result = md.getAsHex();
		assertTrue(result.equalsIgnoreCase("4a7f5723f954eba1216c9d8f6320431f"));
	}
}
