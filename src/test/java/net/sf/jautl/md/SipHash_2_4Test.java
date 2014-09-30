/*
    Copyright (c) 2000-2014 Alessandro Coppo
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

import java.util.Arrays;
import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

@RunWith(Parameterized.class)
public class SipHash_2_4Test extends TesterBase {
	private static byte[] testKey = {
	        (byte)0x00, (byte)0x01, (byte)0x02, (byte)0x03,
	        (byte)0x04, (byte)0x05, (byte)0x06, (byte)0x07,
	        (byte)0x08, (byte)0x09, (byte)0x0a, (byte)0x0b,
	        (byte)0x0c, (byte)0x0d, (byte)0x0e,	(byte)0x0f
	    };

    public SipHash_2_4Test(String message, String expectedDigest) {
    	super(message, expectedDigest, new SipHash(testKey));
	}
    
    @Parameterized.Parameters
    public static Collection<Object[]> testVectors() {
        return Arrays.asList(new Object[][] {
	        {
	        "",
            "726fdb47dd0e0e31"
	        },
	        {
	        "a",
	        "2ba3e8e9a71148ca"
	        },
	        {
	        "abc",
	        "5dbcfa53aa2007a5"
	        }
        });
    }
    
    @Test
    public void testDigest() {
    	super.testDigest();
    }
}
