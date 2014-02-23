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
package net.sf.jautl.io;

/**
 * This class is the base of all Indenting Printers, that is printer capable
 * of "indenting" text and terminating lines.
 */
public abstract class IndentingPrinterBase {
	private int nestingLevel;
	private String tab = "\t";
	private String eol = "\n";
	private StringBuffer buff = new StringBuffer();
	private boolean hasIndentingBeenAdded;
	private boolean hasText;

    /** Protected default class constructor */
	protected IndentingPrinterBase() {
		hasIndentingBeenAdded = false;
		hasText = false;
	}

	/**
     * Set the tab sequence.
	 * @param tab the tab sequence.
     */
	public final void setTabSequence(String tab) {
		this.tab = tab;
	}

	/**
     * Return the current tab sequence.
	 * @return the current tab sequence
	 */
	public final String getTabSequence() {
		return tab;
	}

	/**
     * Set the end-of-line char sequence.
	 * @param eol the end-of-line char sequence.
	 */
	public final void setEolSequence(String eol) {
		this.eol = eol;
	}

	/**
     * Return the current end-of-line char sequence.
	 * @return the current end-of-line char sequence
	 */
	public final String getEolSequence() {
		return eol;
	}

	/**
     * Increase the nesting level of next line by one.
	 */
	public final void indent() {
		nestingLevel++;
	}

	/**
     * Reduce the nesting level of next line by one.
	 */
	public final void unindent() {
		nestingLevel--;
	}

	/**
     * Append a string to the line currently being build.
	 * @param s the string to be appended.
	 */
	public final void append(String s) {
		beginLine();
		buff.append(s);
		hasText = true;
	}

	/**
     * Append a string to the line currently being build and
	 * emit it.
	 * @param s the string to be appended.
	 */
	public final void emit(String s) {
		append(s);
		endl();
	}

	/**
     * Terminate the line, emit it and get ready for the new one.
	 */
	public final void endl() {
		buff.append(eol);
		doPrint(buff.toString());
		hasIndentingBeenAdded = false;
		hasText = false;
		buff.setLength(0);  //fast way to clean the buffer
	}

	/**
     * Return whether there is accumulated text (initial line "tabs" do
	 * not count).
	 * @return whether there is accumulated text
	 */
	public final boolean hasText() {
		return hasText;
	}

	/**
     * The printing method to be appropriately defined in derived classes.
	 * @param s the string to print
	 */
	protected abstract void doPrint(String s);

	private void beginLine() {
		if (!hasIndentingBeenAdded) {
			for (int i = 0; i < nestingLevel; i++)
				buff.append(tab);
			hasIndentingBeenAdded = true;
		}
	}
}