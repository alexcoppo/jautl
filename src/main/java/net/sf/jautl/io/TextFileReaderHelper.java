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

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

/**
 * This class encapsulates the objects required for reading from a text file.
 */
public class TextFileReaderHelper {
    private FileInputStream fis;
    private BufferedInputStream bif;
    private Scanner scn;

    /**
     * Open a file for reading.
     * @param filename the name of the file to read
     * @throws FileNotFoundException
     */
    public void open(String filename) throws FileNotFoundException {
        fis = new FileInputStream(filename);
        bif = new BufferedInputStream(fis);
        scn = new Scanner(bif);
    }

    /**
     * Open a file for reading.
     * @param filename the name of the file to read
     * @param bufferSize the size of the buffer to use for reading
     * @throws FileNotFoundException
     */
    public void open(String filename, int bufferSize) throws FileNotFoundException {
        fis = new FileInputStream(filename);
        bif = new BufferedInputStream(fis, bufferSize);
        scn = new Scanner(bif);
    }

    /**
     * Return the Scanner instance used for reading.
     * @return the instance of the stream
     */
    public Scanner getScanner() {
        return scn;
    }

    /**
     * Check whether the stream begins with a certain character sequence and,
     * if so, remove it.
     * @param s the character sequence to check
     * @return whether the test was succesful
     */
    public boolean match(String s) {
    	if (!scn.hasNext(s))
    		return false;
    	else {
    		scn.next();
    		return true;
    	}
    }

    /**
     * Try reading a string.
     * @return the read value
     * @throws IOException
     */
    public String tryReadString() throws IOException {
    	if (!scn.hasNext())
    		throw new IOException();
    	return scn.next();
    }

    /**
     * Try reading an integer.
     * @return the read value
     * @throws IOException
     */
    public int tryReadInt() throws IOException {
    	if (!scn.hasNextInt())
    		throw new IOException();
    	return scn.nextInt();
    }

    /**
     * Try reading a float.
     * @return the read value
     * @throws IOException
     */
    public float tryReadFloat() throws IOException {
    	if (!scn.hasNextFloat())
    		throw new IOException();
    	return scn.nextFloat();
    }

    /**
     * Try reading a double.
     * @return the read value
     * @throws IOException
     */
    public double tryReadDouble() throws IOException {
    	if (!scn.hasNextDouble())
    		throw new IOException();
    	return scn.nextDouble();
    }

    /**
     * Close all resources.
     * @throws IOException
     */
    public void close() throws IOException {
        scn.close();
        bif.close();
        fis.close();

        scn = null;
        bif = null;
        fis = null;
    }
}
 