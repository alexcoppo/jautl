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

import net.sf.jautl.numeric.geometry.impl.FloatTriplet;

/**
 *
 */
public class FloatVector3D extends FloatTriplet {
	public static final FloatVector3D I = new FloatVector3D(1, 0, 0);

    public static final FloatVector3D J = new FloatVector3D(0, 1, 0);

    public static final FloatVector3D K = new FloatVector3D(0, 0, 1);

    public FloatVector3D() {
    }
    
    public FloatVector3D(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }
    
    public FloatVector3D(FloatVector3D rhs) {
        setX(rhs.getX());
        setY(rhs.getY());
        setZ(rhs.getZ());
    }
    
    public FloatVector3D(FloatVector2D rhs) {
        setX(rhs.getX());
        setY(rhs.getY());
        setZ(0);
    }
    
    public void toUnit() {
        float l = VectorNorms.norm2(this);
        x /= l;
        y /= l;
        z /= l;
    }
}
