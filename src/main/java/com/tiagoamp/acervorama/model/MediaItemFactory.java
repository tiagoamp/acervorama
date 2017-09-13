package com.tiagoamp.acervorama.model;

import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Path;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class MediaItemFactory {
	
	public static <T extends MediaItem> T fromJson(String jsonString, Class<T> itemClass) {
		Gson gson = new GsonBuilder().registerTypeHierarchyAdapter(Path.class, new MyPathConverter()).create();
		return gson.fromJson(jsonString, itemClass);
	}
	
	public static <T extends MediaItem> T fromJson(InputStreamReader inputStreamReader, Class<T> itemClass) {
		Gson gson = new GsonBuilder().registerTypeHierarchyAdapter(Path.class, new MyPathConverter()).create();
		return gson.fromJson(inputStreamReader, itemClass);
	}
	
	@SuppressWarnings("unchecked")
	public static <T extends MediaItem> T fromJson(String jsonString) {
		ObjectMapper objectMapper = new ObjectMapper();
		JsonNode jsonNode = null;
		try {
			jsonNode = objectMapper.readTree(jsonString);
		} catch (IOException e) {
			e.printStackTrace();
		}
		String mediaTypeStr = jsonNode.get("type").asText();
		MediaType itemMediaType = MediaType.valueOf(mediaTypeStr);
		
		return (T) fromJson(jsonString, getItemSubclass(itemMediaType));
	}
	
	public static List<? extends MediaItem> fromPathList(List<Path> list, MediaType type) {		
		Function<Path, ? extends MediaItem> function = p -> getItemSubclassInstance(p, type);
		return list.stream().map(function).collect(Collectors.toList());
	}
	
	public static Class<? extends MediaItem> getItemSubclass(MediaType type) {		
		Class<? extends MediaItem> clazz;
		
		switch (type) {
		case AUDIO:
			clazz = AudioItem.class;
			break;
		case IMAGE:
			clazz = ImageItem.class;
			break;
		case TEXT:
			clazz = TextItem.class;
			break;
		case VIDEO:
			clazz = VideoItem.class;
			break;
		default:
			clazz = null;
			break;
		}
		
		return clazz;		
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
	
	public static <T extends MediaItem> T makeSubclassCast(MediaItem mediaItemSubclassInstance) {
		@SuppressWarnings("unchecked")
		T item = (T) mediaItemSubclassInstance;
		return item;
	}
	
}
