package com.tiagoamp.acervorama.model;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Path;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class MediaItemFactory {
	
	@SuppressWarnings("unchecked")
	public static <T extends MediaItem> T fromJson(String jsonString) throws JsonParseException, JsonMappingException, IOException {
		JsonNode jsonNode = getJsonMapper().readTree(jsonString);
		
		String mediaTypeStr = jsonNode.get("type").asText();
		MediaTypeAcervo itemMediaType = MediaTypeAcervo.valueOf(mediaTypeStr);
		T item = (T) fromJson(jsonString, getItemSubclass(itemMediaType));
				
		return item;
	}
	
	@SuppressWarnings("unchecked")
	public static <T extends MediaItem> T getItemSubclassInstance(MediaTypeAcervo type, Path filePath) {		
		T item;
		
		switch (type) {
		case AUDIO:
			item = (T) new AudioItem(filePath);
			break;
		case IMAGE:
			item = (T) new ImageItem(filePath);
			break;
		case TEXT:
			item = (T) new TextItem(filePath);
			break;
		case VIDEO:
			item = (T) new VideoItem(filePath);
			break;
		default:
			item = null;
			break;
		}
		
		return item;		
	}
	
	@SuppressWarnings("unchecked")
	public static <T extends MediaItem> T getItemSubclassInstance(Class<? extends MediaItem> clazz, Path filePath) {
		T item = null;		
		try {
			Constructor<T> c = (Constructor<T>) Class.forName(clazz.getName()).getConstructor(Path.class);
			item = c.newInstance(filePath);
		} catch (NoSuchMethodException | SecurityException | ClassNotFoundException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			e.printStackTrace();
		}
		return item;
	}
	
	private static <T extends MediaItem> T fromJson(String jsonString, Class<T> itemClass) throws JsonParseException, JsonMappingException, IOException {
		T item = getJsonMapper().readValue(jsonString, itemClass);
		return getItemSubclassInstance(item.getType(), item.getFilePath()); // force call to constructor to fill attributes
	}
	
	private static Class<? extends MediaItem> getItemSubclass(MediaTypeAcervo type) {		
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
	
	private static ObjectMapper getJsonMapper() {
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.findAndRegisterModules();
		objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		return objectMapper;
	}
		
}
