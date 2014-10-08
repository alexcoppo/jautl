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
package net.sf.jautl.utility.filesystem;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * This class implements a filesystem recursive scanner. 
 */
public class FileSystemScanner {
    private FileSystemScannerEventSink snk;
    private FilenameFilter fileFilter;
    private FilenameFilter dirFilter;

    /**
     * The status of the processing.
     */
    public enum ProcessingStatus {
        completed,
        aborted
    }
    
	/**
	 * Set the sink for the file system scanning events.
	 * @param snk the sink to call
	 */
    public void setEventSink(FileSystemScannerEventSink snk) {
        this.snk = snk;
    }
    
    /**
     * Set the filter for the filenames.
     * @param fileFilter the filter for the filenames or null to accept any file
     */
    public void setFilesFilter(FilenameFilter fileFilter) {
        this.fileFilter = fileFilter;
    }
    
    /**
     * Set the filter for the directories names.
     * @param dirFilter the filter for the directories names or null to accept any directory
     */
    public void setDirsFilter(FilenameFilter dirFilter) {
        this.dirFilter = dirFilter;
    }
    
    /**
     * Shallow traverse of a single directory.
     * @param root the directory to process
     * @return the processing status of the scan
     */
    public ProcessingStatus traverseFiles(File root) {
        snk.onSetUp();
        boolean continueScan = traverseFilesAux(root);
        snk.onCleanUp();
        
        return continueScan ? ProcessingStatus.completed : ProcessingStatus.aborted;
    }

    /**
     * Recursive traversal of a directories tree. The directories are iterated before the files.
     * @param root the root directory of the traversal
     */
    public ProcessingStatus traverseDirsFiles(File root) {
        snk.onSetUp();
        boolean continueScan = traverseDirsFilesAux(root);
        snk.onCleanUp();
        
        return continueScan ? ProcessingStatus.completed : ProcessingStatus.aborted;
    }

    /**
     * Recursive traversal of a directories tree. The directories are iterated after the files.
     * @param root the root directory of the traversal
     * @return the processing status of the scan
     */
    public ProcessingStatus traverseFilesDirs(File root) {
        snk.onSetUp();
        boolean continueScan = traverseFilesDirsAux(root);
        snk.onCleanUp();
        
        return continueScan ? ProcessingStatus.completed : ProcessingStatus.aborted;
    }

    private boolean traverseFilesAux(ArrayList<File> files) {
        boolean continueScan = true;
        
        for (int index = 0; index < files.size(); index++) {
            continueScan = snk.onVisitFile(files.get(index));
            if (!continueScan)
                break;
        }
        
        return continueScan;
    }

    private boolean traverseFilesAux(File root) {
        snk.onEnterDirectory(root);
        boolean continueScan = traverseFilesAux(enumFiles(root));
        snk.onExitDirectory(root);
        
        return continueScan;
    }

    private boolean traverseDirsFilesAux(File root) {
        snk.onEnterDirectory(root);

        boolean continueScan = true;
        
        ArrayList<File> files = enumDirectories(root);
        for (File f : files) {
            continueScan = traverseDirsFilesAux(f);
            if (!continueScan)
                break;
        }
        
        if (continueScan)
            continueScan = traverseFilesAux(enumFiles(root));
        
        snk.onExitDirectory(root);
        
        return continueScan;
    }

    private boolean traverseFilesDirsAux(File root) {
        snk.onEnterDirectory(root);

        boolean continueScan = traverseFilesAux(enumFiles(root));

        if (continueScan) {
            ArrayList<File> files = enumDirectories(root);
            
            for (File f : files) {
                continueScan = traverseDirsFilesAux(f);
                if (!continueScan)
                    break;
            }
        }
        
        snk.onExitDirectory(root);
        
        return continueScan;
    }

    private ArrayList<File> enumDirectories(File root) {
        File[] files = (dirFilter != null) ? root.listFiles(dirFilter) : root.listFiles();

        ArrayList<File> result = new ArrayList<File>();
        for (File f : files)
            if (f.isDirectory())
                result.add(f);

        sort(result);

        return result;
    }

    private ArrayList<File> enumFiles(File root) {
        File[] files = (fileFilter != null) ? root.listFiles(fileFilter) : root.listFiles();
        	
        ArrayList<File> result = new ArrayList<File>();
        for (File f : files)
            if (f.isFile())
                result.add(f);

        sort(result);

        return result;
    }

    private void sort(ArrayList<File> files) {
        Collections.sort(files, new Comparator<File>() {
            @Override
            public int compare(File f1, File f2) {
                return f1.compareTo(f2);
            }
        });
    }
}
