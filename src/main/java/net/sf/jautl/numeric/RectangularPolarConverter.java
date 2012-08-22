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
 * This class implements a rectangular <-> polar converter. 
 */
public class RectangularPolarConverter {
    private double x;
    private double y;
    private double rho;
    private double theta;

    /**
     * Return the value of the radius.
     * @return 
     */
    public double getRho() {
        return rho;
    }

    /**
     * Return the angle.
     * @return 
     */
    public double getTheta() {
        return theta;
    }

    /**
     * Return the abscissa.
     * @return 
     */
    public double getX() {
        return x;
    }

    /**
     * Return the ordinate.
     * @return 
     */
    public double getY() {
        return y;
    }

    /**
     * Perform the rectangular to polar conversion.
     * @param x the value of the abscissa
     * @param y the value of the ordinate
     */
    public void setRectangular(double x, double y) {
        this.x = x;
        this.y = y;
        rho = GenericFunctions.hypot(x, y);
        theta = GenericFunctions.atan2(y, x);
    }

    /**
     * Perform the polar to rectangular conversion.
     * @param rho the radius
     * @param theta the angle
     */
    public void setPolar(double rho, double theta) {
        this.rho = rho;
        this.theta = theta;
        x = rho * Math.cos(theta);
        y = rho * Math.sin(theta);
    }
}
