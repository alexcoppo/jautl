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

import net.sf.jautl.numeric.MinMaxes;

/**
 * This class contains method for computing most common norms.
 */
public class VectorNorms {
    /**
     * Compute the L1 norm of the given vector.
     * @param v the vector whose norm compute
     */
    public static float norm1(FloatVector2D v) {
        return Math.abs(v.getX()) + Math.abs(v.getY());
    }
    
    /**
     * Compute the L1 norm of the given vector.
     * @param v the vector whose norm compute
     */
    public static float norm1(FloatVector3D v) {
        return Math.abs(v.getX()) + Math.abs(v.getY()) + Math.abs(v.getZ());
    }
    
    /**
     * Compute the L1 norm of the given vector.
     * @param v the vector whose norm compute
     */
    public static double norm1(DoubleVector2D v) {
        return Math.abs(v.getX()) + Math.abs(v.getY());
    }
    
    /**
     * Compute the L1 norm of the given vector.
     * @param v the vector whose norm compute
     */
    public static double norm1(DoubleVector3D v) {
        return Math.abs(v.getX()) + Math.abs(v.getY()) + Math.abs(v.getZ());
    }
    
    /**
     * Compute the L2 norm of the given vector.
     * @param v the vector whose norm compute
     */
    public static float norm2(FloatVector2D v) {
        return (float)Math.sqrt(ScalarProduct.f(v, v));
    }
    
    /**
     * Compute the L2 norm of the given vector.
     * @param v the vector whose norm compute
     */
    public static float norm2(FloatVector3D v) {
        return (float)Math.sqrt(ScalarProduct.f(v, v));
    }
    
    /**
     * Compute the L2 norm of the given vector.
     * @param v the vector whose norm compute
     */
    public static double norm2(DoubleVector2D v) {
        return Math.sqrt(ScalarProduct.f(v, v));
    }
    
    /**
     * Compute the L2 norm of the given vector.
     * @param v the vector whose norm compute
     */
    public static double norm2(DoubleVector3D v) {
        return Math.sqrt(ScalarProduct.f(v, v));
    }

    //TODO: Add L-p norms

    /**
     * Compute the L-infinite norm of the given vector.
     * @param v the vector whose norm compute
     */
    public static float normInf(FloatVector2D v) {
        return (float)MinMaxes.absMax(v.getX(), v.getY());
    }
    
    /**
     * Compute the L-infinite norm of the given vector.
     * @param v the vector whose norm compute
     */
    public static float normInf(FloatVector3D v) {
        return (float)MinMaxes.absMax(v.getX(), v.getY(), v.getZ());
    }
    
    /**
     * Compute the L-infinite norm of the given vector.
     * @param v the vector whose norm compute
     */
    public static double normInf(DoubleVector2D v) {
        return MinMaxes.absMax(v.getX(), v.getY());
    }
    
    /**
     * Compute the L-infinite norm of the given vector.
     * @param v the vector whose norm compute
     */
    public static double normInf(DoubleVector3D v) {
        return MinMaxes.absMax(v.getX(), v.getY(), v.getZ());
    }
}
