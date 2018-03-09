package com.tiagoamp.acervorama.resources;

import java.net.URI;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.client.ResponseProcessingException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tiagoamp.acervorama.model.AcervoramaBusinessException;
import com.tiagoamp.acervorama.model.MediaItem;
import com.tiagoamp.acervorama.model.MediaTypeAcervo;
import com.tiagoamp.acervorama.service.MediaItemService;

@CrossOrigin
@RestController
@RequestMapping("/media")
public class MediaResource {
	
	@Autowired
	private MediaItemService service;
	
	
	@RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON)
	public List<MediaItem> getMediaItems(@RequestParam(value = "filename", required=false) String nameParam, 
										 @RequestParam(value = "classification", required=false) String classificationParam,
										 @RequestParam(value = "type", required=false) MediaTypeAcervo mediatypeParam,
										 @RequestParam(value = "tags", required=false) String tagsParam ) {
		
		List<MediaItem> list;
		
		if (nameParam != null || classificationParam != null || mediatypeParam != null) {
			list = service.findByFields(nameParam, classificationParam, mediatypeParam); 
		} else {
			list = service.getAll();
		}		
		
		if (tagsParam != null) {
			List<String> tagsList = Arrays.asList(tagsParam.split(","));
			Predicate<MediaItem> notContainsTags = m -> tagsList.stream().anyMatch(tag -> !m.containsTag(tag));
			list.removeIf(notContainsTags);			
		}			
		
		return list;
	}
	
	@GET
	@Path("hash/{hash}")
	@Produces(MediaType.APPLICATION_JSON)
	public MediaItem getMedia(@PathParam("hash") String hash) {
		return service.findByHash(hash);
	}
	
	@GET
	@Path("{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public MediaItem getMedia(@PathParam("id") Long id) {
		return service.findById(id);
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response add(MediaItem item, @Context UriInfo uriInfo) {
		try {
			service.insert(item);
			item = service.findByPath(item.getFilePath());			
		} catch (AcervoramaBusinessException e) {
			throw new ResponseProcessingException(Response.serverError().build(), e.getBusinessMessage());
		}	
		URI uri = uriInfo.getAbsolutePathBuilder().path(String.valueOf(item.getId())).build();
		return Response.created(uri).entity(item).build();
	}
	
	@DELETE
	@Path("{id}")
	public Response delete(@PathParam("id") Long id) {
		service.delete(id);
		return Response.noContent().build();
	}
	
	@PUT
	@Path("{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response update(@PathParam("id") Long id, MediaItem item) {
		item.setId(id);
		service.update(item);
		return Response.ok().entity(item).build();
	}
	
}
