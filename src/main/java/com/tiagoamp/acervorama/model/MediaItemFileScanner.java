package com.tiagoamp.acervorama.model;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

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
	
	
	/**
	 * Scans the file system for a media type from a given origin path.
	 * 
	 * @return List of media items scanned
	 * @throws IOException 
	 */
	public List<MediaItem> perform() throws IOException {
		String[] extensions = getFileExtensionsToBeSearched();		
		List<Path> filesFound = findFilesByExtensios(extensions);		
		List<MediaItem> mediaItems = getMediaItemsFromPathList(filesFound);		
		return mediaItems;		
	}
	
	private String[] getFileExtensionsToBeSearched() {
		List<String> list = new ArrayList<>();		
		for (MediaType t : MediaType.values()) {
			if (type == null || type == t) {
				list.addAll(Arrays.asList(t.getFileExtensions()));
			}
		}		
		return (String[]) list.toArray();
	}
	
	private List<Path> findFilesByExtensios(String[] extensions) throws IOException {
		
		Predicate<Path> predicate =	(p) -> { 
				boolean result = false;
				for (String ext : extensions) {
					if (p.getFileName().endsWith(ext)) {
						result = true;
						break;
					}
				}
				return result;
			};		

		Path[] pathArray = (Path[]) Files.walk(origin).filter(predicate).toArray();
		return Arrays.asList(pathArray);
		
		/* List<Path> filesFound = new ArrayList<>();
		
		Files.walkFileTree(origin, new FileVisitor<Path>() {
			@Override
			public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
				return FileVisitResult.CONTINUE;
			}

			@Override
			public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
				for (String ext : extensions) {
					if (file.getFileName().endsWith(ext)) {
						filesFound.add(file);
					}
				}				 
				return FileVisitResult.CONTINUE;
			}

			@Override
			public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
				return FileVisitResult.CONTINUE;
			}

			@Override
			public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
				return FileVisitResult.CONTINUE;
			}
		});
		
		return filesFound;*/
	}
	
	private List<MediaItem> getMediaItemsFromPathList(List<Path> paths) {
		Function<Path, MediaItem> function = (p) -> new MediaItem(p);
		MediaItem[] itemsArray = (MediaItem[]) paths.stream().map(function).toArray();
		return Arrays.asList(itemsArray);		
	}
	
	
	public Path from() {
		return origin;
	}
	
	public MediaType getType() {
		return type;
	}

}
