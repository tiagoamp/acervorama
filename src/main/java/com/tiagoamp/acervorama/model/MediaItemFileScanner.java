package com.tiagoamp.acervorama.model;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * Represents the file scanner.
 * 
 * @author tiagoamp
 */
public class MediaItemFileScanner {
	
	/**
	 * Assumes the root directory as origin path, and all media types to be searched.
	 */
	public MediaItemFileScanner() {
		this.origin = ROOT_DIR;
	}
	
	/**
	 * Assumes the search from the give origin and for the given media type.
	 *  
	 * @param origin
	 * @param type
	 */
	public MediaItemFileScanner(Path origin, MediaType type) {
		this.origin = origin;
		this.type = type;
	}
	
	/**
	 * Assumes the search from the give origin and for ALL media type.
	 * 
	 * @param origin
	 */
	public MediaItemFileScanner(Path origin) {
		this(origin, null);
	}
	
	/**
	 * Assumes the search from the file system root directory and for the given media type.
	 * 
	 * @param type
	 */
	public MediaItemFileScanner(MediaType type) {
		this(ROOT_DIR, type);		
	}
	
	
	private Path origin;
	private MediaType type;
	
	private static final Path ROOT_DIR = File.listRoots()[0].toPath();  
	
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("\"Scanner from ");
		sb.append("'" + origin + "'");
		sb.append(" for ");
		if (type == null) {
			sb.append("'ALL'");
		} else {
			sb.append("'" + type + "'");
		}
		sb.append(".");
		return sb.toString();
	}
	
	
	/**
	 * Scans the file system for a media type from a given origin path.
	 * 
	 * @return List of media items scanned
	 * @throws IOException 
	 */
	public List<MediaItem> perform() throws IOException {
		List<String> extensions = getFileExtensionsToBeSearched();		
		List<Path> filesFound = findFilesByExtensios(extensions);		
		List<MediaItem> mediaItems = getMediaItemsFromPathList(filesFound);		
		return mediaItems;		
	}
	
	private List<String> getFileExtensionsToBeSearched() {
		List<String> list = new ArrayList<>();		
		for (MediaType t : MediaType.values()) {
			if (type == null || type == t) {
				list.addAll(Arrays.asList(t.getFileExtensions()));
			}
		}		
		return list;
	}
	
	private List<Path> findFilesByExtensios(List<String> extensions) throws IOException {
		
		Predicate<Path> predicate =	(p) -> { 
				boolean result = false;
				for (String ext : extensions) {
					if (p.getFileName().toString().endsWith(ext)) {
						result = true;
						break;
					}
				}
				return result;
			};		

		return Files.walk(origin).filter(predicate).collect(Collectors.toList());		
	}
	
	private List<MediaItem> getMediaItemsFromPathList(List<Path> paths) {
		Function<Path, MediaItem> function = (p) -> new MediaItem(p);
		return paths.stream().map(function).collect(Collectors.toList());
	}
	
	
	public Path from() {
		return origin;
	}
	
	public MediaType getType() {
		return type;
	}

}
