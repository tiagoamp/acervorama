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
		Function<Path, ? extends MediaItem> function = p -> type.getItemSubclassInstance(p);
		return list.stream().map(function).collect(Collectors.toList());
	}
	
}
