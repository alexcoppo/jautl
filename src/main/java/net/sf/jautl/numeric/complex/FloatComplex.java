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
package net.sf.jautl.numeric.complex;

import net.sf.jautl.numeric.GenericFunctions;

/**
 * This class represents double precision complex numbers.
 */
public class FloatComplex {
    private float re;
    private float im;

    /**
     * The imaginary constant.
     */
    public static final FloatComplex I = new FloatComplex(0, 1);

    /**
     * Construct a zero complex.
     */
    public FloatComplex() {
        this(0, 0);
    }

    /**
     * Construct a complex with zero imaginary part.
     * @param re the real part.
     */
    public FloatComplex(float re) {
        this(re, 0);
    }

    /**
     * Construct a generic complex
     * @param re the real part
     * @param im the imaginary part
     */
    public FloatComplex(float re, float im) {
        this.re = re;
        this.im = im;
    }

    /**
     * Copy constructor.
     * @param z the template instance
     */
    public FloatComplex(FloatComplex z) {
        assign(z);
    }

    /**
     * Copy constructor.
     * @param z the template instance
     */
    public FloatComplex(DoubleComplex z) {
        assign(z);
    }

    /**
     * Create a complex, given its modulus and argument.
     * @param rho the modulus
     * @param theta the argument
     */
    public final void createPolar(float rho, float theta) {
        re = (float)(rho * Math.cos(theta));
        im = (float)(rho * Math.sin(theta));
    }

    /**
     * Assign "operator"
     * @param z the source complex.
     */
    public final void assign(FloatComplex z) {
        re = z.re;
        im = z.im;
    }

    /**
     * Assign "operator"
     * @param z the source complex.
     */
    public final void assign(DoubleComplex z) {
        re = (float)z.getRe();
        im = (float)z.getIm();
    }

    /**
     * Assign "operator"
     * @param re the real part
     * @param im the imaginary part
     */
    public final void assign(float re, float im) {
        this.re = re;
        this.im = im;
    }

    /**
     * Set the real part.
     * @param re the new value.
     */
    public final void setRe(float re) {
        this.re = re;
    }

    /**
     * Return the real part.
     */
    public final float getRe() {
        return re;
    }

    /**
     * Set the imaginary part.
     * @param im the new value.
     */
    public final void setIm(float im) {
        this.im = im;
    }

    /**
     * Return the imaginary part.
     */
    public final float getIm() {
        return im;
    }

    /**
     * Conjugate the complex ( conj(a+ib) = a-ib )
     */
    public final void conjugate() {
        im = -im;
    }

    /**
     * Return the argument of the number.
     */
    public final float arg() {
        return (float)GenericFunctions.atan2(im, re);
    }

    /**
     * Return the modulus of the number.
     */
    public final float abs() {
        return (float)GenericFunctions.hypot(re, im);
    }

    /**
     * Return the modulus square of the number.
     */
    public final float abs2() {
        return re * re + im * im;
    }

    /**
     * Add two complex numbers. If it safe to call the method
     * passing it this.
     * @param src0 the first argument
     * @param src1 the second argument
     */
    public final void add(FloatComplex src0, FloatComplex src1) {
        re = src0.re + src1.re;
        im = src0.im + src1.im;
    }

    /**
     * Subtract two complex numbers. If it safe to call the method
     * passing it this.
     * @param src0 the first argument
     * @param src1 the second argument
     */
    public final void subtract(FloatComplex src0, FloatComplex src1) {
        re = src0.re - src1.re;
        im = src0.im - src1.im;
    }

    /**
     * Multiply a complex number by a double factor.
     * @param factor the factor by which multiply the current DComplex.
     */
    public final void multiply(float factor) {
        re *= factor;
        im *= factor;
    }

    /**
     * Multiply two complex numbers. If it safe to call the method
     * passing it this.
     * @param src0 the first argument
     * @param src1 the second argument
     */
    public final void multiply(FloatComplex src0, FloatComplex src1) {
        float tmpRe = src0.re * src1.re - src0.im * src1.im;
        float tmpIm = src0.re * src1.im + src0.im * src1.re;

        re = tmpRe;
        im = tmpIm;
    }

    /**
     * Divide a complex number by a double factor.
     * @param factor the factor by which divide the current DComplex.
     */
    public final void divide(float factor) {
        multiply(1.0f / factor);
    }

    /**
     * Divide two complex numbers. If it safe to call the method
     * passing it this.
     * @param src0 the first argument
     * @param src1 the second argument
     */
    public final void divide(FloatComplex src0, FloatComplex src1) {
        FloatComplex src1_ = new FloatComplex(src1);
        src1_.conjugate();

        multiply(src0, src1_);
        divide(src1_.abs2());
    }

    /**
     * Compute the linear combination of two complexes, that is:
     * <code>dst = mu * src0 + lambda * src1</code>.
     * @param mu the coefficient my which multiply the first source complex
     * @param src0 the first source complex
     * @param lambda the coefficient my which multiply the second source complex
     * @param src1 the second source complex.
     */
    public final void lincomb(float mu, FloatComplex src0, float lambda, FloatComplex src1) {
        re = mu * src0.re + lambda * src1.re;
        im = mu * src0.im + lambda * src1.im;
    }
    
    /**
     * Compute 1/z
     */
    public final void inverse() {
        conjugate();
        divide(abs2());
    }

    /**
     * Swap the values of 2 DoubleComplexes.
     * @param za the first complex
     * @param zb the second complex.
     */
    public static final void swap(FloatComplex za, FloatComplex zb) {
        float tmp;
        
        tmp = za.re;
        za.re = zb.re;
        zb.re = tmp;

        tmp = za.im;
        za.im = zb.im;
        zb.im = tmp;
    }
    
    /**
     * Create a string representation.
     */
    public final String toString() {
        StringBuffer buff = new StringBuffer(32);

        buff.append('(');
        buff.append(Float.toString(re));
        buff.append(',');
        buff.append(' ');
        buff.append(Float.toString(im));
        buff.append(')');
        return buff.toString();
    }
}