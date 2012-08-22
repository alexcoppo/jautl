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
package net.sf.jautl.io;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 *  This class encapsulates the objects required for reading from a binary file.
 */
public class BinaryFileReaderHelper {
    private FileInputStream fis;
    private BufferedInputStream bif;
    private DataInputStream dis;

    /**
     * Open the file.
     * @param filename the name of the file
     * @throws FileNotFoundException
     */
    public void open(String filename) throws FileNotFoundException {
        fis = new FileInputStream(filename);
        bif = new BufferedInputStream(fis);
        dis = new DataInputStream(bif);
    }

    /**
     * Open a file.
     * @param filename the name of the file
     * @param bufferSize the size of the buffer
     * @throws FileNotFoundException
     */
    public void open(String filename, int bufferSize) throws FileNotFoundException {
        fis = new FileInputStream(filename);
        bif = new BufferedInputStream(fis, bufferSize);
        dis = new DataInputStream(bif);
    }

    /**
     * Get the created DataInputStream.
     * @return the stream instance
     */
    public DataInputStream getDataInputStream() {
        return dis;
    }

    /**
     * Close the file.
     * @throws IOException
     */
    public void close() throws IOException {
        dis.close();
        bif.close();
        fis.close();

        dis = null;
        bif = null;
        fis = null;
    }
}
