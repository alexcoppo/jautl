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
package net.sf.jautl.numeric;

import net.sf.jautl.utility.functional.*;

/**
 * FpTolerance encapsulates the operations required to properly
 * compare two floating point numbers for equality/difference.
 * <p>Floating point numbers should never be compared just for equality:
 * almost never they will be exactly equal as this requires same binary
 * representation. Moreover, according to values and situations, the
 * criteria can change. For example an error of 10<SUP>10</SUP> might
 * look very large, but if we are dealing with numbers in the range of
 * 10<SUP>23</SUP> it means that in fact we have 13 digits right, which
 * usually is not bad at all.
 * <p>This class embeds two parameters (the absolute and relative error
 * thresholds) and allows the check whether two numbers or vectors or
 * matrices are &quot;enough&quot; equal.
 * The condition used is:
 * <p><code>abs(loPrec - hiPrec) &lt;= max(absoluteError,
 * relativeError * abs(hiPrec))</code>
 * <p>This means that if a number is &quot;small&quot; (i.e. near
 * 0) the check works prevalently using the absolute error
 * threshold, while for &quot;large&quot; (i.e. far from 0, both
 * in negative and positive directions) the checks verifies
 * prevalently the relative error.
 * <p>This kind of check contains both the situations in which we
 * are interested to the number of significant digits (checking
 * only the relative error) and the situaions in which we want to
 * verify the maximum deviation (checking only the absolute error).
 * Obviously, all in-between situations are handled by extension.
 * Here are some examples:
 * <p>
 * <table>
 * <tr>
 * <td>Absolute Error</td>
 * <td>Relative Error</td>
 * <td>loPrec</td>
 * <td>hiPrec</td>
 * <td>isEqual</td>
 * </tr>
 * <tr>
 * <td>1E-3</td>
 * <td>0</td>
 * <td>.001</td>
 * <td>0</td>
 * <td>Yes: absolute error is just large enough.</td>
 * </tr>
 * <tr>
 * <td>1E-3</td>
 * <td>0</td>
 * <td>999999</td>
 * <td>1000000</td>
 * <td>No: absolute error is too small.</td>
 * </tr>
 * <tr>
 * <td>1</td>
 * <td>0</td>
 * <td>999999</td>
 * <td>1000000</td>
 * <td>Yes: absolute error is just enough.</td>
 * </tr>
 * <tr>
 * <td>0</td>
 * <td>1E-3</td>
 * <td>999999</td>
 * <td>1000000</td>
 * <td>Yes: relative error allows for range of 1000.</td>
 * </tr>
 * <tr>
 * <td>1E-3</td>
 * <td>1E-3</td>
 * <td>999999</td>
 * <td>1000000</td>
 * <td>Yes: relative error allows for range of 1000 which is
 * 	 much greater than 1E-3, so relative error commands.</td>
 * </tr>
 * <tr>
 * <td>1E-3</td>
 * <td>1E-8</td>
 * <td>.999999</td>
 * <td>1</td>
 * <td>Yes: relative error is much less than absolute error, so
 * 	 absolute error commands.</td>
 * </tr>
 * </table>
 *
 * <p class="intro">For further references, see any <EM>really</EM> good
 * numerical analysis book. Comparing floating point numbers is a
 * fundamental topic for professionals and therefore its description is
 * universally available.
 */
public class FpTolerance implements BinaryPredicate<Double, Double> {
    private double absolute;
    private double relative;

    /**
     * The parameterless constructor; creates and instance with
     * zero tolerances. Using such an instance is <em>exactly</em>
     * equivalent to usual == or != comparisons.
     */
    public FpTolerance() {
        absolute = 0.;
        relative = 0.;
    }

    /**
     * This constructor creates an instance with given absolute
     * and relative tolerances.
     * @param absolute the maximum absolute error for equality.
     * @param relative the maximum relative error for equality.
     */
    public FpTolerance(double absolute, double relative) {
        this.absolute = Math.abs(absolute);
        this.relative = Math.abs(relative);
    }

    /**
     * The copy constructor.
     * @param ft the instance to be used for cloning.
     */
    public FpTolerance(FpTolerance ft) {
        assign(ft);
    }

    /**
     * The assigning method.
     * @param ft the instance to be used for assigning.
     */
    public final void assign(FpTolerance ft) {
        absolute = ft.absolute;
        relative = ft.relative;
    }

    /**
     * Sets the absolute error to a given value.
     * @param absolute the new value for the thresold.
     */
    public final void setAbsolute(double absolute) {
        this.absolute = Math.abs(absolute);
    }

    /**
     * Returns the current value of the absolute error thresold.
     * @return 
     */
    public final double getAbsolute() {
        return absolute;
    }

    /**
     * Sets the relative error to a given value.
     * @param relative the new value for the thresold.
     */
    public final void setRelative(double relative) {
        this.relative = Math.abs(relative);
    }

    /**
     * Returns the current value of the relative error thresold.
     * @return 
     */
    public final double getRelative() {
        return relative;
    }

    /**
     * This method checks whether 2 numbers are equal w.r.t. the
     * given tolerances set in this instance.
     * @param hiPrec is regarded as the &quot;exact&quot; number while...
     * @param loPrec ...is checked for being &quot;enough&quot; near hiPrec.
     * @return 
     */
    public final boolean areEqual(double hiPrec, double loPrec) {
        double delta = Math.abs(hiPrec - loPrec);
        double range = relative * Math.abs(hiPrec);

        if (range < absolute)
            range = absolute;
        return delta <= range;
    }

    /**
     * Wrapper to adapt to the BinaryPredicate<> interface.
     * @param x the first argument of the function
     * @param y the first argument of the function
     * @return 
     */
	public boolean fn(Double x, Double y) {
		return areEqual(x, y);
	}

    /**
     * This method compares two vectors for equality.
     * It is similar to <code>areEqual(double, double)</code>. Two
     * vectors are deemed equal if an only if <em>every</em> pair
     * of elements is equal. The two vectors must contain the same
     * number of elements and are checked in every element.
     * @param hiPrec is regarded as the &quot;exact&quot; number while...
     * @param loPrec ...is checked for being &quot;enough&quot; near hiPrec.
     * @return 
     */
    public final boolean areEqual(double[] hiPrec, double[] loPrec) {
        if (hiPrec.length != loPrec.length)
            throw new IllegalArgumentException();

        for (int index = 0; index < hiPrec.length; index++)
            if (!areEqual(hiPrec[index], loPrec[index]))
                return false;

        return true;
    }

    /**
     * This method compares two vectors for equality.
     * It is similar to <code>areEqual(double[], double[])</code>
     * and is included to remove the need to convert float[] to double[].
     * @param hiPrec is regarded as the &quot;exact&quot; number while...
     * @param loPrec ...is checked for being &quot;enough&quot; near hiPrec.
     * @return 
     */
    public final boolean areEqual(float[] hiPrec, float[] loPrec) {
        if (hiPrec.length != loPrec.length)
            throw new IllegalArgumentException();

        for (int index = 0; index < hiPrec.length; index++)
            if (!areEqual(hiPrec[index], loPrec[index]))
                return false;

        return true;
    }

    /**
     * This method compares two matrices for equality.
     * It is similar to <code>areEqual(double, double)</code>. Two
     * matrices are deemed equal if an only if <em>every</em> pair
     * of elements is equal. The two matrices must contain the same
     * number of elements and are checked in every element.
     * @param hiPrec is regarded as the &quot;exact&quot; number while...
     * @param loPrec ...is checked for being &quot;enough&quot; near hiPrec.
     * @return 
     */
    public final boolean areEqual(double[][] hiPrec, double[][] loPrec) {
        if (hiPrec.length != loPrec.length || hiPrec[0].length != loPrec[0].length)
            throw new IllegalArgumentException();

        for (int row = 0; row < hiPrec.length; row++)
            if (!areEqual(hiPrec[row], loPrec[row]))
                return false;

        return true;
    }

    /**
     * This method compares two matrices for equality.
     * It is similar to <code>areEqual(double[][], double[][])</code>
     * and is included to remove the need to convert float[][] to double[][].
     * @param hiPrec is regarded as the &quot;exact&quot; number while...
     * @param loPrec ...is checked for being &quot;enough&quot; near hiPrec.
     * @return 
     */
    public final boolean areEqual(float[][] hiPrec, float[][] loPrec) {
        if (hiPrec.length != loPrec.length || hiPrec[0].length != loPrec[0].length)
            throw new IllegalArgumentException();

        for (int row = 0; row < hiPrec.length; row++)
            if (!areEqual(hiPrec[row], loPrec[row]))
                return false;

        return true;
    }

    /**
     * Helper method to check for equality with 0.
     * @param value the number to be compared with 0.
     * @return 
     */
    public final boolean isZero(double value) {
        return areEqual(0., value);
    }

    /**
     * This method compares a vector with the null one.
     * @param v the vector to be checked.
     * @return 
     */
    public final boolean isZero(double[] v) {
        for (int index = 0; index < v.length; index++)
            if (!isZero(v[index]))
                return false;

        return true;
    }

    /**
     * This method compares a vector with the null one.
     * It is similar to <code>isZero(double[])</code>
     * and is included to remove the need to convert float[] to double[].
     * @param v the vector to be checked.
     * @return 
     */
    public final boolean isZero(float[] v) {
        for (int index = 0; index < v.length; index++)
            if (!isZero(v[index]))
                return false;

        return true;
    }

    /**
     * This method compares a matrix with the null one.
     * @param m the matrix to be checked.
     * @return 
     */
    public final boolean isZero(double[][] m) {
        for (int row = 0; row < m.length; row++)
            if (!isZero(m[row]))
                return false;

        return true;
    }

    /**
     * This method compares a vectors with the null one.
     * It is similar to <code>isZero(double[][])</code>
     * and is included to remove the need to convert float[] to double[].
     * @param m the matrix to be checked.
     * @return 
     */
    public final boolean isZero(float[][] m) {
        for (int row = 0; row < m.length; row++)
            if (!isZero(m[row]))
                return false;

        return true;
    }
}
