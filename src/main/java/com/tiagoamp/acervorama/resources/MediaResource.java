package com.tiagoamp.acervorama.resources;

import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.client.ResponseProcessingException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import com.tiagoamp.acervorama.model.AcervoramaBusinessException;
import com.tiagoamp.acervorama.model.MediaItem;
import com.tiagoamp.acervorama.model.MediaTypeAcervo;
import com.tiagoamp.acervorama.service.MediaItemService;

@Path("media")
public class MediaResource {
	
	private MediaItemService service = new MediaItemService();
	
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<MediaItem> getMediaItems(@QueryParam(value = "filename") String nameParam, 
										 @QueryParam(value = "classification") String classificationParam,
										 @QueryParam(value = "type") String mediatypeParam,
										 @QueryParam(value = "tags") String tagsParam ) {
		
		if (nameParam != null && nameParam.isEmpty()) nameParam = null;
		if (classificationParam != null && classificationParam.isEmpty()) classificationParam = null;
		if (mediatypeParam != null && mediatypeParam.isEmpty()) mediatypeParam = null;
		if (tagsParam != null && tagsParam.isEmpty()) tagsParam = null;
				
		List<MediaItem> list = new ArrayList<>();
		
		if (nameParam != null || classificationParam != null || mediatypeParam != null) {
			MediaTypeAcervo mediaType = mediatypeParam != null ? MediaTypeAcervo.valueOf(mediatypeParam.toUpperCase()) : null; 
			list = service.findByFields(nameParam, classificationParam, mediaType); 
		} else {
			list = service.getAll();
		}		
		
		if (tagsParam != null) {
			List<String> tagsList = Arrays.asList(tagsParam.split(","));
			Predicate<MediaItem> predicate = m -> tagsList.stream().anyMatch(tag -> m.containsTag(tag));
			return list.stream().filter(predicate).collect(Collectors.toList());			
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
