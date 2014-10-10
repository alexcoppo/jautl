package net.sf.jautl.utility.filesystem;

import java.io.File;

public class FileSystemScanningEvent {	
	private FileSystemScanningEventEnum event;
	private File currentFile;
	private boolean continueProcessing = true;

	public void eventSetUp() {
		event = FileSystemScanningEventEnum.setUp;
	}

	public void eventCleanUp() {
		event = FileSystemScanningEventEnum.cleanUp;
	}

	public void eventEnterDirectory(File directory) {
		event = FileSystemScanningEventEnum.enterDirectory;
		currentFile = directory;
	}

	public void eventVisitFile(File file) {
		event = FileSystemScanningEventEnum.visitFile;
		currentFile = file;
	}

	public void eventExitDirectory(File directory) {
		event = FileSystemScanningEventEnum.exitDirectory;
		currentFile = directory;
	}
	
	public FileSystemScanningEventEnum getEvent() {
		return event;
	}

	public File getCurrentFile() {
		return currentFile;
	}

	public String getCurrentFilename() {
		try {
			return currentFile.getCanonicalPath();
		} catch (Exception e) {
			return null;
		}
	}

	public void abort() {
		continueProcessing = false;
	}
	
	public boolean isOK() {
		return continueProcessing;
	}
}
