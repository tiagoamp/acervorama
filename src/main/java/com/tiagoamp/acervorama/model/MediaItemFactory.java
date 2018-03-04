package com.tiagoamp.acervorama.model;

import java.io.IOException;
import java.nio.file.Path;
import java.time.LocalDateTime;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class MediaItemFactory {
	
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
		MediaTypeAcervo itemMediaType = MediaTypeAcervo.valueOf(mediaTypeStr);
		
		T item = (T) fromJson(jsonString, getItemSubclass(itemMediaType));
		if (item.getRegisterDate() == null) item.setRegisterDate(LocalDateTime.now());
		if (item.getFilename() == null) item.setFilename(item.getFilePath().getFileName().toString());
		if (item.getHash() == null) item.fillHash();
		return item;
	}
	
	private static <T extends MediaItem> T fromJson(String jsonString, Class<T> itemClass) {
		Gson gson = new GsonBuilder().registerTypeHierarchyAdapter(Path.class, new MyPathConverter()).create();
		return gson.fromJson(jsonString, itemClass);
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
		
}
