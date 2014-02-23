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
package net.sf.jautl.rng.variates;

import net.sf.jautl.rng.interfaces.GeneratorsDouble;
import net.sf.jautl.rng.interfaces.IDoublesSource;

/**
 * This class creates boolean variates (the distribution is also known as the
 * Bernoulli one).
 */
public class VariateBoolean extends VariatesBaseDoublesSource {
    private double probability;

    /**
     * The constructor.
	 * @param ids an implementor of IDoublesSource interface
     */
    public VariateBoolean(IDoublesSource ids) {
    	super(ids);
    }

    /**
     * Return the value of the probability parameter.
     * @return
     */
    public double getProbability() {
        return probability;
    }

    /**
     * Set the probability parameter.
     * @param probability the new value of the probability parameter
     */
    public void setProbability(double probability) {
        this.probability = probability;
    }

    /**
     * Draw a variate.
     * @return 
     */
	public boolean draw() {
		return GeneratorsDouble.generate(ids, true, true) < probability ? true : false;
	}
}