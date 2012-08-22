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
package net.sf.jautl.rng.sources;

import org.junit.*;
import static org.junit.Assert.*;

import net.sf.jautl.rng.interfaces.GeneratorsByte;

public class RandomSourceRC4Test {
	@Test
	public void testRng() {
		RandomSourceRC4 src = new RandomSourceRC4();
		
        src.randomize(
                new byte[] {
                    (byte)0x01, (byte)0x23, (byte)0x45, (byte)0x67,
                    (byte)0x89, (byte)0xab, (byte)0xcd, (byte)0xef
                }
            );
            
        byte res[] = new byte[8];
        GeneratorsByte.generate(src, res, 0, 8);

        byte expected[] = {
            (byte)0x74, (byte)0x94, (byte)0xc2, (byte)0xe7,
            (byte)0x10, (byte)0x4b, (byte)0x08, (byte)0x79
        };
            
        for (int index = 0; index < res.length; index++)
        	assertTrue(res[index] == expected[index]);
	}
}
