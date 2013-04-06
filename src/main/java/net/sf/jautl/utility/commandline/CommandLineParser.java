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
package net.sf.jautl.utility.commandline;

import java.io.PrintStream;
import java.util.ArrayList;
import net.sf.jautl.text.StringUtils;

/**
 * This class processes the command line according to the flags and
 * parameters with which it has been configured. The currently handled
 * syntax is very similar to GNU getopt.
 */
public class CommandLineParser {
	private ArrayList<Option> options = new ArrayList<Option>();
	private ArrayList<String> arguments = new ArrayList<String>();

    /**
     * Adds a flag specification to the list of recognized
	 * options.
	 * @param f the flag
	 * @return the argument with which it is called.
	 */
	public final Flag add(Flag f) {
		options.add(f);
		return f;
	}

	/**
     * Add a parameter specification to the list of recognized
	 * options.
	 * @param p the parameter
	 * @return the argument with which it is called.
	 */
	public final Parameter add(Parameter p) {
		options.add(p);
		return p;
	}

	/**
     * The parsing method.
	 * @param args the same vector of the static main() method.
	 * @throws SyntaxException should it enconter an ill-formed
	 * command line.
	 */
	public final void parse(String[] args) throws SyntaxException {
		for (int argc = 0; argc < args.length;) {
			if (args[argc].equals("--"))
				return;
			if (args[argc].startsWith("--"))
				argc = parseLongOption(args, argc);
			else if (args[argc].startsWith("-"))
				argc = parseShortOptionsCluster(args, argc);
			else
				argc = parseArgument(args, argc);
		}
	}

	private int parseLongOption(String[] args, int argc) throws SyntaxException {
		String tag;
		int pos = args[argc].indexOf("=");
		if (pos == -1)
			tag = args[argc].substring(2);
		else
			tag = args[argc].substring(2, pos);

		Option o = lookup(tag);

		if (o instanceof Flag) {
			if (pos != -1)
				throw new SyntaxException("Tag " + tag + " is a flag, no value allowed");
			((Flag)o).mark();

			return argc + 1;
		} else {
			Parameter p = (Parameter)o;

			if (pos == -1) {
				if (args.length <= argc + 1)
					throw new SyntaxException("Tag " + tag + " needs parameter value");
				p.setValue(args[argc + 1]);
				return argc + 2;
			} else {
				p.setValue(args[argc].substring(pos + 1));
				return argc + 1;
			}
		}
	}

	private int parseShortOptionsCluster(String[] args, int argc) throws SyntaxException {
		String tag = args[argc].substring(1);

		Parameter opt = null;
		int paramCount = 0;

		for (int index = 0; index < tag.length(); index++) {
			Option o = lookup(tag.substring(index, index + 1));

			if (o instanceof Flag)
				((Flag)o).mark();
			else {
				paramCount++;
				opt = (Parameter)o;
			}
		}

		switch (paramCount) {
			case 0:
				return argc + 1;
			case 1:
				if ((argc + 1) >= args.length)
					throw new SyntaxException("Missing parameter for last short options cluster");
				opt.setValue(args[argc + 1]);
				return argc + 2;
			default:
				throw new SyntaxException("Too many parameters in short options cluster");
		}
	}

	private int parseArgument(String[] args, int argc) {
		arguments.add(args[argc]);
		return argc + 1;
	}

	/**
     * The number of arguments.
	 * @return the number of arguments.
	 */
	public final int argumentsCount() {
		return arguments.size();
	}

	/**
     * The n-th argument.
	 * @param index the index of the argument.
	 * @return the argument.
	 */
	public final String getArgument(int index) {
		return (String)arguments.get(index);
	}

	/**
     * Prints the "usage is..." help message.
	 * @param clse the encountered exception.
	 * @param ps the PrintStream on with emit the message (usually System.err).
	 */
	public final void printUsage(SyntaxException clse, PrintStream ps) {
		ps.println();
		if (clse != null) {
			ps.println("Error:" + clse.toString());
			ps.println();
		}
		ps.println("Options are:");

		int maxOptSize = 0;
		for (int i = 0; i < options.size(); i++) {
			Option o = (Option)options.get(i);
			if (maxOptSize < o.getTag().length())
				maxOptSize = o.getTag().length();
		}
		maxOptSize += 2; //take into account -/-- and a blank after

		for (int i = 0; i < options.size(); i++) {
			Option o = (Option)options.get(i);

			ps.print((o instanceof Flag) ? "\t[F] " : "\t[P] ");
			ps.print(o.isLongOpt() ? "--" : "-");
			ps.print(o.getTag());
			ps.print(StringUtils.createString(maxOptSize - (o.isLongOpt() ? 1 : 0) - o.getTag().length(), ' '));
			ps.print(o.getHelp());
			if (o instanceof Parameter) {
				Parameter p = (Parameter)o;
				if (p.hasDefault())
					ps.print(", default:" + p.getDefault());
			}

			ps.println();
		}

		ps.println();
	}

	private Option lookup(String tag) throws SyntaxException {
		for (int i = 0; i < options.size(); i++) {
			Option o = (Option)options.get(i);
			if (o.getTag().equals(tag))
				return o;
		}

		throw new SyntaxException("Unrecognized tag:" + tag);
	}
}