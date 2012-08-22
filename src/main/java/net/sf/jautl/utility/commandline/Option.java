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
package net.sf.jautl.utility.commandline;

/**
 * This is the root of the elements recognized by the CommandLineParser.
 */
public class Option {
	private String tag;
	private String help;

    /**
     * The constructor of Options.
	 * @param tag the flag tag.
	 * @param help the help message string associated with this flag.
	 */
	protected Option(String tag, String help) {
		this.tag = tag;
		this.help = help;
	}

	/**
     * Return the tag associated with this option.
	 * @return the tag associated with this option
	 */
	public final String getTag() {
		return tag;
	}

	/**
     * Return whether this is a long option (i.e. a multicharacter tag).
	 * @return whether this is a long option
	 */
	public final boolean isLongOpt() {
		return tag.length() > 1;
	}

	/**
     * Return the help message associated with this option.
	 * @return the help message associated with this option
	 */
	public final String getHelp() {
		return help;
	}
}