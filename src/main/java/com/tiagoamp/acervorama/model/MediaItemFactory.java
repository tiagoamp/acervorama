package com.tiagoamp.acervorama.model;

import java.nio.file.Path;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import com.google.gson.Gson;

public class MediaItemFactory {
	
	public static <T extends MediaItem> T fromJson(String jsonString, Class<T> itemClass) {
		return new Gson().fromJson(jsonString, itemClass);
	}
	
	public static List<? extends MediaItem> fromPathList(List<Path> list, MediaType type) {		
		Function<Path, ? extends MediaItem> function = p -> getItemSubclassInstance(p, type);
		return list.stream().map(function).collect(Collectors.toList());
	}
	
	@SuppressWarnings("unchecked")
	public static <T extends MediaItem> T getItemSubclassInstance(Path path, MediaType type) {
		T item;
		
		switch (type) {
		case AUDIO:
			item = (T) new AudioItem(path);
			break;
		case IMAGE:
			item = (T) new ImageItem(path);
			break;
		case TEXT:
			item = (T) new TextItem(path);
			break;
		case VIDEO:
			item = (T) new VideoItem(path);
			break;
		default:
			item = null;
			break;
		}	
		
		return item;
	}
	
}
