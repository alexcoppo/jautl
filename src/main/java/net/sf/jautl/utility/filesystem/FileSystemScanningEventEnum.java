package net.sf.jautl.utility.filesystem;

/**
 * This enumeration tags the various events of the filesystem scanning process.
 */
public enum FileSystemScanningEventEnum {
	setUp,
	enterDirectory,
	visitFile,
	exitDirectory,
	cleanUp
}
