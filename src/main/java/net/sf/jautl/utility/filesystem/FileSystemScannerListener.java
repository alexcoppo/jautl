package net.sf.jautl.utility.filesystem;

/**
 * This interface describes the protocol of the filesystem scanning listeners.
 */
public interface FileSystemScannerListener {
	/**
	 * Event handler callback.
	 * @param event the FileSystemScanningEvent instance describing the event
	 */
	void onScanningEvent(FileSystemScanningEvent event);
}
