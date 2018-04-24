package com.tiagoamp.acervorama.resources;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

import javax.ws.rs.client.ResponseProcessingException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.tiagoamp.acervorama.model.AcervoramaBusinessException;
import com.tiagoamp.acervorama.model.MediaItem;
import com.tiagoamp.acervorama.model.MediaTypeAcervo;
import com.tiagoamp.acervorama.service.MediaItemService;

@CrossOrigin
@RestController
@RequestMapping("/media")
public class MediaRestController {
	
	@Autowired
	private MediaItemService service;
	
	
	@RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON)
	@ResponseBody
	public List<MediaItemResource> getMediaItems(@RequestParam(value = "filename", required=false) String nameParam, 
										 		 @RequestParam(value = "classification", required=false) String classificationParam,
										 		 @RequestParam(value = "type", required=false) MediaTypeAcervo mediatypeParam,
										 		 @RequestParam(value = "tags", required=false) String tagsParam ) {
		List<MediaItem> itemsList;
		
		final boolean hasParamsFilled = (nameParam != null && !nameParam.isEmpty()) || (classificationParam != null && !classificationParam.isEmpty()) || mediatypeParam != null; 
		if (hasParamsFilled) {
			itemsList = service.findByFields(nameParam, classificationParam, mediatypeParam); 
		} else {
			itemsList = service.getAll();
		}		
		
		if (tagsParam != null) {
			tagsParam = tagsParam.toUpperCase();
			List<String> tagsList = Arrays.asList(tagsParam.split(","));
			Predicate<MediaItem> notContainsTags = m -> tagsList.stream().anyMatch(tag -> !m.containsTag(tag));
			itemsList.removeIf(notContainsTags);			
		}			
		
		List<MediaItemResource> resourceList = new ArrayList<>();
		itemsList.forEach(item -> resourceList.add(new MediaItemResource(item)));		
		
		return resourceList;
	}
	
	@RequestMapping(value="hash/{hashParam}", method=RequestMethod.GET, produces=MediaType.APPLICATION_JSON)
	@ResponseBody
	public MediaItemResource getMediaByHash(@PathVariable("hashParam") String hash) {
		Optional<MediaItem> item = Optional.ofNullable(service.findByHash(hash));
		return item.isPresent() ? new MediaItemResource(item.get()) : null;				
	}
	
	@RequestMapping(value="{id}", method=RequestMethod.GET, produces=MediaType.APPLICATION_JSON)
	@ResponseBody
	public MediaItemResource getMedia(@PathVariable("id") Long id) {
		Optional<MediaItem> item = Optional.ofNullable(service.findById(id));
		return item.isPresent() ? new MediaItemResource(item.get()) : null;
	}
	
	@RequestMapping( method = RequestMethod.POST, consumes=org.springframework.http.MediaType.APPLICATION_JSON_VALUE , produces=MediaType.APPLICATION_JSON )
	@ResponseStatus( value=HttpStatus.CREATED )
	@ResponseBody
	public MediaItemResource add(@RequestBody MediaItem item) {
		try {
			item = service.insert(item);			
		} catch (AcervoramaBusinessException e) {
			throw new ResponseProcessingException(Response.notModified().build(), e.getBusinessMessage());
		}	
		return new MediaItemResource(item);
	}
	
	@RequestMapping(value="{id}", method = RequestMethod.DELETE)
	@ResponseStatus( value=HttpStatus.NO_CONTENT )
	public void delete(@PathVariable("id") Long id) {
		service.delete(id);		
	}
	
	@RequestMapping(value="{id}", method = RequestMethod.PUT, consumes=org.springframework.http.MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public void update(@PathVariable("id") Long id, @RequestBody MediaItem item) {
		item.setId(id);
		service.update(item);
	}
	
}
