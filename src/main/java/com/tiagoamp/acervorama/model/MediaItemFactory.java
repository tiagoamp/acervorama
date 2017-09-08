package com.tiagoamp.acervorama.model;

import java.nio.file.Path;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import com.google.gson.Gson;

public class MediaItemFactory {
	
	public static MediaItem fromJson(String jsonString) {
		return new Gson().fromJson(jsonString, MediaItem.class);
	}
	
	public static List<MediaItem> fromPathList(List<Path> list) {
		Function<Path, MediaItem> function = p -> new MediaItem(p);
		return list.stream().map(function).collect(Collectors.toList());
	}

}
