package net.sf.jautl.rng.variates;

import net.sf.jautl.rng.interfaces.IDoublesSource;

/**
 * This class is the base for all VariateSources which use an IDoublesSource
 * as source of entropy. 
 */
public abstract class VariatesBaseDoublesSource {
	/** The source of entropy. */
	protected IDoublesSource ids;
	
	/**
	 * Set the source of entropy.
	 * @param ids an implementor of IDoublesSource interface
	 */
	protected VariatesBaseDoublesSource(IDoublesSource ids) {
		this.ids = ids;
	}
}
