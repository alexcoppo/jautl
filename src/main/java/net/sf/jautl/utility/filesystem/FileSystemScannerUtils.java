package net.sf.jautl.utility.filesystem;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * Utility methods for filesystem scanning. 
 */
public class FileSystemScannerUtils {
	/**
	 * 
	 * @param root
	 * @param fileFilter
	 * @return
	 */
    public static ArrayList<File> enumFiles(File root, FilenameFilter fileFilter) {
        File[] files = (fileFilter != null) ? root.listFiles(fileFilter) : root.listFiles();
        	
        ArrayList<File> result = new ArrayList<File>();

        if (files != null)
	        for (File f : files)
	            if (f.isFile())
	                result.add(f);

        if (result.size() > 1)
        	sort(result);

        return result;
    }

    /**
     * 
     * @param root
     * @param dirFilter
     * @return
     */
    public static ArrayList<File> enumDirectories(File root, FilenameFilter dirFilter) {
        File[] files = (dirFilter != null) ? root.listFiles(dirFilter) : root.listFiles();

        ArrayList<File> result = new ArrayList<File>();
        
        if (files != null)
	        for (File f : files)
	            if (f.isDirectory())
	                result.add(f);

        if (result.size() > 1)
        	sort(result);

        return result;
    }

    private static void sort(ArrayList<File> files) {
        Collections.sort(files, new Comparator<File>() {
            @Override
            public int compare(File f1, File f2) {
                return f1.compareTo(f2);
            }
        });
    }
}
