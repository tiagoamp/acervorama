package com.tiagoamp.acervorama.model;

import java.io.IOException;
import java.time.LocalDateTime;

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
		if (item.getRegisterDate() == null) item.setRegisterDate(LocalDateTime.now());
		if (item.getFilename() == null) item.setFilename(item.getFilePath().getFileName().toString());
		if (item.getHash() == null) item.fillHash();
		return item;
	}
	
	private static <T extends MediaItem> T fromJson(String jsonString, Class<T> itemClass) throws JsonParseException, JsonMappingException, IOException {
		return getJsonMapper().readValue(jsonString, itemClass);
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
