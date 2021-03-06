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

import java.io.BufferedOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;

/**
 * This class encapsulates the objects required for writing a text file.
 */
public class TextFileWriterHelper {
    private FileOutputStream fos;
    private BufferedOutputStream bof;
    private PrintStream ps;

    /**
     * Open a file for writing.
     * @param filename the name of the file to write
     * @throws FileNotFoundException
     */
    public void open(String filename) throws FileNotFoundException {
        fos = new FileOutputStream(filename);
        bof = new BufferedOutputStream(fos);
        ps = new PrintStream(bof);
    }

    /**
     * Open a file for writing.
     * @param filename the name of the file to write
     * @param bufferSize the size of the buffer to use for writing
     * @throws FileNotFoundException
     */
    public void open(String filename, int bufferSize) throws FileNotFoundException {
        fos = new FileOutputStream(filename);
        bof = new BufferedOutputStream(fos, bufferSize);
        ps = new PrintStream(bof);
    }

    /**
     * Return the PrintStream instance to use for writing.
     * @return the instance of the stream
     */
    public PrintStream getPrintStream() {
        return ps;
    }

    /**
     * Flush output.
     * @throws IOException
     */
    public void flush() throws IOException {
        ps.flush();
        bof.flush();
        fos.flush();
    }

    /**
     * Close all resources.
     * @throws IOException
     */
    public void close() throws IOException {
        ps.close();
        bof.close();
        fos.close();

        ps = null;
        bof = null;
        fos = null;
    }
}
