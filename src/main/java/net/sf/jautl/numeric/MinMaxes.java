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
 *  Some minimum/maximum related functions.
 */
public class MinMaxes {
    /**
     * The minimum between two numbers.
     * @param x the first argument
     * @param y the second argument
     * @return the result of the function
     */
    public static double min(double x, double y) {
        return Math.min(x, y);
    }

    /**
     * The minimum among three numbers.
     * @param x the first argument
     * @param y the second argument
     * @param z the third argument
     * @return the result of the function
     */
    public static double min(double x, double y, double z) {
        return Math.min(Math.min(x, y), z);
    }

    /**
     * The minimum of absolute value between two numbers.
     * @param x the first argument
     * @param y the second argument
     * @return the result of the function
     */
    public static double absMin(double x, double y) {
        return (Math.abs(x) < Math.abs(y)) ? x : y;
    }

    /**
     * The minimum of absolute value among three numbers.
     * @param x the first argument
     * @param y the second argument
     * @param z the third argument
     * @return the result of the function
     */
    public static double absMin(double x, double y, double z) {
        return absMin(absMin(x, y), z);
    }

    /**
     * The maximum between two numbers.
     * @param x the first argument
     * @param y the second argument
     * @return the result of the function
     */
    public static double max(double x, double y) {
        return Math.max(x, y);
    }

    /**
     * The maximum among three numbers.
     * @param x the first argument
     * @param y the second argument
     * @param z the third argument
     * @return the result of the function
     */
    public static double max(double x, double y, double z) {
        return max(max(x, y), z);
    }

    /**
     * The maximum of absolute value between two numbers.
     * @param x the first argument
     * @param y the second argument
     * @return the result of the function
     */
    public static double absMax(double x, double y) {
        return (Math.abs(x) < Math.abs(y)) ? y : x;
    }

    /**
     * The maximum of absolute value among three numbers.
     * @param x the first argument
     * @param y the second argument
     * @param z the third argument
     * @return the result of the function
     */
    public static double absMax(double x, double y, double z) {
        return absMax(absMax(x, y), z);
    }
}
