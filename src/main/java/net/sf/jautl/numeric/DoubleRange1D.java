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
package net.sf.jautl.numeric;

public class DoubleRange1D {
    private double minimum;
    private double maximum;

    public DoubleRange1D(double minimum, double maximum) {
    	this.minimum = minimum;
    	this.maximum = maximum;
    }

    /// Check whether a range is valid, i.e. whether the maximum is >= than
    /// the minimum.
    public boolean isValid() {
        return minimum <= maximum;
    }

    /// Check whether a valis is inside a range.
    /// <param name="value">the value to check</param>
    public boolean isInside(double value) {
        return (value >= minimum) && (value <= maximum);
    }

    /// Return the minimum of a given range.
    public double getMinimum() {
        return minimum;
    }

    /// Return the maximum of a given range.
    public double getMaximum() {
        return maximum;
    }

    /// Return the center of a given range.
    public double getCenter() {
        return (maximum + minimum) / 2;
    }

    /// Return the extent of a given range.
    public double getExtent() {
        return maximum - minimum;
    }

    /// Return a number in [0, 1] which represents the relative position of a value
    /// in the range
    /// <param name="Value">the value to map</param>
    public double scale(double value) {
        return (value - getCenter()) / getExtent();
    }

    /// Return an integer representing the quantized position of a value in a range.
    /// <param name="Coordinate">the dimension index of the range</param>
    /// <param name="Value">the value to map</param>
    /// <param name="BucketCount">the number of buckets in which subdivide the range</param>
    public int bucketize(double value, int bucketCount) {
        return (int)(scale(value) * bucketCount * .999999);
    }
}
