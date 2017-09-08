package com.tiagoamp.acervorama.model.scanner;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * Represents the file scanner.
 * 
 * @author tiagoamp
 */
public class FileScanner {
	
	private Path origin;
	private String[] fileExtensions;
	
	private static final Path ROOT_DIR = File.listRoots()[0].toPath();
	
	
	/**
	 * Assumes the root directory as origin path, and all files types to be searched.
	 */
	public FileScanner() {
		this.origin = ROOT_DIR;
	}
	
	/**
	 * Assumes the search from the give origin and for the given file extensions.
	 *  
	 * @param origin
	 * @param fileExtensions
	 */
	public FileScanner(Path origin, String[] fileExtensions) {
		this.origin = origin;
		this.fileExtensions = fileExtensions;
	}
	
	/**
	 * Assumes the search from the give origin and for ALL file extensions.
	 * 
	 * @param origin
	 */
	public FileScanner(Path origin) {
		this(origin, null);
	}
	
	/**
	 * Assumes the search from the file system root directory and for given file extensions.
	 * 
	 * @param type
	 */
	public FileScanner(String[] fileExtensions) {
		this(ROOT_DIR, fileExtensions);		
	}
	
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("\"Scanner from ");
		sb.append("'" + origin + "'");
		sb.append(" for ");
		if (fileExtensions == null) {
			sb.append("'ALL file extensions'");
		} else {
			sb.append("'" + fileExtensions + "'");
		}
		sb.append(".");
		return sb.toString();
	}
	
	
	/**
	 * Scans the file system by file extensions from a given origin path.
	 * 
	 * @return List of paths of the items scanned
	 * @throws IOException 
	 */
	public List<Path> perform() throws IOException {
		
		Predicate<Path> predicate =	p -> { 
			if (fileExtensions == null) return true;
			boolean result = false;			
			for (String ext : fileExtensions) {
				if (p.getFileName().toString().endsWith(ext)) {
					result = true;
					break;
				}
			}
			return result;
		};		

		return Files.walk(origin).filter(predicate).collect(Collectors.toList());
				
	}
	
}
