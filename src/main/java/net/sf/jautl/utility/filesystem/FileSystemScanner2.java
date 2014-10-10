package net.sf.jautl.utility.filesystem;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;

public class FileSystemScanner2 {
    private FileSystemScannerListener listener;
    private FilenameFilter fileFilter;
    private FilenameFilter dirFilter;
	private FileSystemScanningEvent event;

    public FileSystemScanner2(FileSystemScannerListener listener) {
    	this(listener, null, null);
    }

    public FileSystemScanner2(FileSystemScannerListener listener, FilenameFilter fileFilter, FilenameFilter dirFilter) {
        this.listener = listener;
        this.fileFilter = fileFilter;
        this.dirFilter = dirFilter;
    }

    public void shallowScanFiles(File root) {
    	event = new FileSystemScanningEvent();
    	
    	event.eventSetUp();
    	listener.onScanningEvent(event);
    	if (!event.isOK())
    		return;
    	
        shallowScanFilesAux(root);

        event.eventCleanUp();
        listener.onScanningEvent(event);
    }

    public void deepScanDirsFiles(File root) {
    	event = new FileSystemScanningEvent();
    	
    	event.eventSetUp();
    	listener.onScanningEvent(event);
    	if (!event.isOK())
    		return;
 
        deepScanDirsFilesAux(root);

        event.eventCleanUp();
        listener.onScanningEvent(event);
    }

    public void deepScanFilesDirs(File root) {
    	event = new FileSystemScanningEvent();
    	
    	event.eventSetUp();
    	listener.onScanningEvent(event);
    	if (!event.isOK())
    		return;
 
        deepScanFilesDirsAux(root);

        event.eventCleanUp();
        listener.onScanningEvent(event);
    }

    public boolean isOK() {
        return event.isOK();
    }
    
    private void shallowScanFilesAux(File root) {
    	event.eventEnterDirectory(root);
    	listener.onScanningEvent(event);
    	if (!event.isOK())
            return;

        scanFiles(FileSystemScannerUtils.enumFiles(root, fileFilter));
        
    	event.eventExitDirectory(root);
    	listener.onScanningEvent(event);
    }

    private void deepScanDirsFilesAux(File root) {
    	event.eventEnterDirectory(root);
    	listener.onScanningEvent(event);
    	if (!event.isOK())
            return;

        ArrayList<File> files = FileSystemScannerUtils.enumDirectories(root, dirFilter);
        for (File f : files) {
            deepScanDirsFilesAux(f);
            if (!event.isOK())
                break;
        }
        
        if (event.isOK())
        	scanFiles(FileSystemScannerUtils.enumFiles(root, fileFilter));
        
    	event.eventExitDirectory(root);
    	listener.onScanningEvent(event);
    }

    private void deepScanFilesDirsAux(File root) {
    	event.eventEnterDirectory(root);
    	listener.onScanningEvent(event);
    	if (!event.isOK())
            return;

    	scanFiles(FileSystemScannerUtils.enumFiles(root, fileFilter));

        if (event.isOK()) {
	    	ArrayList<File> files = FileSystemScannerUtils.enumDirectories(root, dirFilter);
	        for (File f : files) {
	            deepScanFilesDirsAux(f);
	            if (!event.isOK())
	                break;
	        }
        }
        
    	event.eventExitDirectory(root);
    	listener.onScanningEvent(event);
    }

    private void scanFiles(ArrayList<File> files) {
        for (int index = 0; index < files.size(); index++) {
        	event.eventVisitFile(files.get(index));
        	listener.onScanningEvent(event);
            if (!event.isOK())
                return;
        }
    }
}
