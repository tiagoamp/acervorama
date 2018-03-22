package com.tiagoamp.acervorama.resources;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

import org.springframework.hateoas.ResourceSupport;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.tiagoamp.acervorama.model.MediaItem;

@JsonIgnoreProperties({"id"})
public class MediaItemResource extends ResourceSupport {
	
	private MediaItem resource;
	
	public MediaItemResource() {		
	}
	
	public MediaItemResource(MediaItem item) {
		this.resource = item;		
		this.add(linkTo(MediaItemResource.class).slash(item.getId()).withSelfRel());
	}
	
		
	public MediaItem getResource() {
		return resource;
	}
	public void setResource(MediaItem resource) {
		this.resource = resource;
	}

}
