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
package net.sf.jautl.swing;

import java.io.File;
import java.util.ArrayList;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 * This class wraps the standard JFileChooser with some utility methods.
 */
public class JFileChooserDecorator {
	private JFileChooser jfc;
	private JFrame parent;
	private ArrayList<FileNameExtensionFilter> extensions;

	/**
	 * The constructor.
	 * The associated JFileChooser has a null parent frame.
	 */
	public JFileChooserDecorator() {
		this(null);
	}
	
	/**
	 * The constructor.
	 * @param parent the JFrame to use as parent of the JFileChooser instance
	 */
	public JFileChooserDecorator(JFrame parent) {
		this.jfc = new JFileChooser();
		this.parent = parent;
		clearExtensions();
	}
	
	/**
	 * Get the underlying JFileChooser instance.
	 * @return 
	 */
	public JFileChooser getFileChooser() {
		return jfc;
	}
	
	/**
	 * Remove the filename extensions list.
	 */
	public void clearExtensions() {
		extensions = new ArrayList<FileNameExtensionFilter>();
	}
	
	/**
	 * Register a new description/extension pair.
	 * @param description the description of the extension
	 * @param extension the extension string
	 */
	public void registerExtension(String description, String extension) {
		FileNameExtensionFilter fnef = new FileNameExtensionFilter(description, extension);
		extensions.add(fnef);
	}

	private void prepareExtensionsFilter() {
		jfc.resetChoosableFileFilters();

		for (FileNameExtensionFilter fnef : extensions)
			jfc.addChoosableFileFilter(fnef);
	}

	/**
	 * Display the File Open dialog.
	 * @return the selected filename or null if no selection was done
	 */
	public String getOpenName() {
		prepareExtensionsFilter();

		int result = jfc.showOpenDialog(parent);
		
		if (result != JFileChooser.APPROVE_OPTION)
			return null;
		else
			return jfc.getSelectedFile().getAbsolutePath();
	}
	
	/**
	 * Display the File Save dialog.
	 * @return the selected filename or null if no selection was done
	 */
	public String getSaveName() {
		prepareExtensionsFilter();

		jfc.setSelectedFile(new File(""));
		int result = jfc.showSaveDialog(parent);
		
		if (result != JFileChooser.APPROVE_OPTION)
			return null;
		else
			return jfc.getSelectedFile().getAbsolutePath();
	}
}
