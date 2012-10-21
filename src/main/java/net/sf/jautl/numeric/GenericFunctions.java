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
package net.sf.jautl.numeric;

/**
 * This module contains several utility functions.
 */
public class GenericFunctions {
    /**
     * This method returns the base 10 logarithm.
     * @param value the value of which compute the logarithm
     * @return the value of the function
     */
    public static double log10(double value) {
        return Math.log(value) / Constants.LN10;
    }

    /**
     * This method computes the pythagorean sum of two numbers.
     * The computation is performed in a numerically safe
     * way.
     * @param x the x coordinate
     * @param y the y coordinate
     * @return the value of the function
     */
    public static double hypot(double x, double y) {
        // Adapted from "Numerical Recipes in Fortran 77: The Art of Scientific Computing" (ISBN 0-521-43064-X)
        double absX = Math.abs(x);
        double absY = Math.abs(y);

        if (absX == 0.0 && absY == 0.0)
            return  0.0;
        else if (absX >= absY) {
            double d =  y / x;
            return  absX * Math.sqrt(1.0 + d * d);
        } else {
            double d =  x / y;
            return  absY * Math.sqrt(1.0 + d * d);
        }
    }

    /**
     * This method computes the number of exact digits, given a low
     * and high precision data. For example, 3.14 is a ~3 digits
     * approximation to Pi.
     * @param loPrec the low precision value...
     * @param hiPrec the high precision counterpart.
     * @return the value of the function
     */
    public static double exactDigits(double loPrec, double hiPrec) {
        double epsilon = 100 * Double.MIN_VALUE;

        loPrec = Math.abs(loPrec);
        if (loPrec <= epsilon)
            loPrec = 0;

        hiPrec = Math.abs(hiPrec);
        if (hiPrec <= epsilon)
            hiPrec = 0;

        if (hiPrec != 0)
            if (loPrec != 0) {
                double tmp = Math.abs(loPrec - hiPrec) / hiPrec;

                if (tmp <= epsilon)
                    return -log10(epsilon);
                else
                    return -log10(tmp);
            } else
                return -log10(hiPrec);

        if (loPrec != 0)
            return -log10(loPrec);
        else
            return -log10(epsilon);
    }

    /**
     * This function returns an extension of the usual sign function.
     * The pecularity is that it returns 0 when the number is exactly
     * zero instead of the usual +1.
     * @param x
     * @return the value of the function
     */
    public static int sign(double x) {
        if (x == 0)
            return 0;
        else
            return (x > 0) ? 1 : -1;
    }

    /**
     * This function returns the angle of a rectangular to polar
     * conversion in the range [0, 2 * Pi).
     * @param y ordinate
     * @param x abscissa
     * @return the value of the function
     */
    public static double atan2(double y, double x) {
        double result = java.lang.Math.atan2(y, x);

        return (result >= 0) ? result : result + 2 * Math.PI;
    }

    /**
     * Compute the linear interpolation of two numbers.
     * @param x0 the value returned if alpha == 0
     * @param alpha the ratio of the interpolation
     * @param x1 the value returned if alpha == 1
     * @return the value of the function
     */
    public static double lerp(double x0, double alpha, double x1) {
		return x0 + alpha * (x1 - x0);
    }

    /**
     * A cubic s-curve.
     * @param t the argument
     * @return the value of the function
     */
    public static double scurve(double t) {
        return t * t * (3 - 2 * t);
    }

    /**
     * Limit the value to a given range.
     * @param x the value to be clamped
     * @param min the minimum output value
     * @param max the maximum output value
     * @return the value of the function
     */
    public static double clamp(double x, double min, double max) {
        if (x < min)
            return min;
        if (x > max)
            return max;
        return x;
    }

    /**
     * A hard 0 to 1 step.
     * @param x the abscissa
     * @param a the step location
     * @return the value of the function 
     */
    public static double step(double x, double a) {
        return (x >= a) ? 1 : 0;
    }

    /**
     * A smooth 0 to 1 step.
     * @param x the abscissa
     * @param a the step initial location
     * @param b the step final location
     * @return the value of the function
     */
    public static double smoothstep(double x, double a, double b) {
        if (x < a) 
            return 0;
        if (x >= b)
            return 1;
        x = (x - a) / (b - a);
        return scurve(x);
    }

    /**
     * The derivate of smoothstep.
     * @param x the abscissa
     * @param a the step initial location
     * @param b the step final location
     * @return the value of the function
     */
    public static double dsmoothstep(double x, double a, double b) {
        if (x < a) 
            return 0;
        if (x >= b)
            return 0;
        x = (x - a) / (b - a);
        return 4.0 * x * (1.0 - x);
    }

    /**
     * Convert an angle from degrees into radians.
     * @param theta the value to be converted
     * @return the converted value
     */
    public static double degsToRads(double theta) {
        return theta / 180 * Constants.PI;
    }

    /**
     * Convert an angle from radians into degrees.
     * @param theta the value to be converted
     * @return the converted value
     */
    public static double radsToDegs(double theta) {
        return theta * 180 / Constants.PI;
    }

    /**
     * Clip a value in the [0, 1] range.
     * @param x the value to be clipped
     * @return the clipped value
     */
    public static double clip01(double x) {
        if (x < 0) return 0;
        if (x > 1) return 1;
        return x;
    }

    private GenericFunctions() { }
}