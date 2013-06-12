/*
    Copyright (c) 2000-2013 Alessandro Coppo
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
public class VectorArithmetic {
    public static void combine(FloatVector2D result, float a, FloatVector2D va, float b, FloatVector2D vb) {
        result.setX(a * va.getX() + b * vb.getX());
        result.setY(a * va.getY() + b * vb.getY());
    }

    public static void combine(FloatVector3D result, float a, FloatVector3D va, float b, FloatVector3D vb) {
        result.setX(a * va.getX() + b * vb.getX());
        result.setY(a * va.getY() + b * vb.getY());
        result.setZ(a * va.getZ() + b * vb.getZ());
    }

    public static void combine(DoubleVector2D result, double a, DoubleVector2D va, double    b, DoubleVector2D vb) {
        result.setX(a * va.getX() + b * vb.getX());
        result.setY(a * va.getY() + b * vb.getY());
    }

    public static void combine(DoubleVector3D result, double a, DoubleVector3D va, double b, DoubleVector3D vb) {
        result.setX(a * va.getX() + b * vb.getX());
        result.setY(a * va.getY() + b * vb.getY());
        result.setZ(a * va.getZ() + b * vb.getZ());
    }
}
