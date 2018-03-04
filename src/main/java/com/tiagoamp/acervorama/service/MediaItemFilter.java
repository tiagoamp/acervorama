package com.tiagoamp.acervorama.service;

import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import com.tiagoamp.acervorama.model.MediaItem;
import com.tiagoamp.acervorama.model.MediaTypeAcervo;

public class MediaItemFilter {

	/**
	 * Retrieve filtered Media Items by given tag.
	 * 
	 * @return List<MediaItem>
	 */
	public List<MediaItem> filterByTags(List<MediaItem> list, String[] tags) {
		Predicate<MediaItem> predicate = m -> Arrays.asList(tags).stream().anyMatch(tag -> m.containsTag(tag));		
		return list.stream().filter(predicate).collect(Collectors.toList());
	}
	
	/**
	 * Retrieve filtered Media Items by given media type.
	 * 
	 * @return List<MediaItem>
	 */
	public List<MediaItem> filterByMediaType(List<MediaItem> list, MediaTypeAcervo mediaType) {
		return list.stream().filter(m -> m.getType() == mediaType).collect(Collectors.toList());
	}
	
}
