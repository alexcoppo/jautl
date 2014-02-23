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
package net.sf.jautl.numeric.geometry;

/**
 *
 */
public class CrossProduct {
    //i  j  k
    //v0x v0y v0z
    //v1x v1y v1z

    public static FloatVector3D f(FloatVector3D v0, FloatVector3D v1) {
		float v3x = v0.getY() * v1.getZ() - v1.getY() * v0.getZ();
		float v3y = v0.getX() * v1.getZ() - v1.getX() * v0.getZ();
		float v3z = v0.getX() * v1.getY() - v1.getX() * v0.getY();

		return new FloatVector3D(v3x, v3y, v3z);
    }
    
    public static DoubleVector3D f(DoubleVector3D v0, DoubleVector3D v1) {
		double v3x = v0.getY() * v1.getZ() - v1.getY() * v0.getZ();
		double v3y = v0.getX() * v1.getZ() - v1.getX() * v0.getZ();
		double v3z = v0.getX() * v1.getY() - v1.getX() * v0.getY();

		return new DoubleVector3D(v3x, v3y, v3z);
    }
}
