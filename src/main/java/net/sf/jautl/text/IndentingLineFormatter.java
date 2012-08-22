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
package net.sf.jautl.text;

import java.io.IOException;

/**
 * This class describes a generic printer with controllable indenting char and level.
 */
public abstract class IndentingLineFormatter {
    private String tabSequence = "\t";
    private int tabLevel = 0;
    private String lineBuffer = "";

    /**
     * Set the string sequence representing the 'tab'.
     * @param tabSequence the new value for the tab sequence
     */
    public void setTabSequence(String tabSequence) {
        this.tabSequence = tabSequence;
    }

    /**
     * Return the tab sequence value.
     */
    public String getTabSequence() {
        return tabSequence;
    }

    /**
     * Increase by one the tab level.
     * The change will take place starting from next line.
     */
    public void indent() {
        tabLevel++;
    }

    /**
     * Decrease by one the tab level.
     * The change will take place starting from next line.
     */
    public void unindent() {
        tabLevel--;
    }

    /**
     * Append a string to the current line.
     * @param text the text to be appended
     * @return the current instance, to allow for call chaining
     */
    public IndentingLineFormatter append(String text) {
        beginLine();
        lineBuffer += text;
        return this;
    }

    /**
     * Append a string to the current line and emit it.
     * @param text the text to be appended
     * @return the current instance, to allow for call chaining
     */
    public IndentingLineFormatter emit(String text) throws IOException {
        append(text);
        flush(lineBuffer);
        lineBuffer = "";
        return this;
    }

    /**
     * Emit the currently cached line.
     */
    public void emit() throws IOException {
        emit("");
    }

    /**
     * The method to write a line to the output.
     * @param line the line text to write
     */
    protected abstract void flush(String line) throws IOException;

    /**
     * If required, get ready for the next line.
     * If there is already some text in the cached line, nothing is done.
     */
    protected void beginLine() {
        if (lineBuffer.length() > 0)
            return;

        lineBuffer = "";
        for (int Index = 0; Index < tabLevel; Index++)
            lineBuffer += tabSequence;
    }
}