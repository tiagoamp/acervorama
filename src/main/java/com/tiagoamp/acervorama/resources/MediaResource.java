package com.tiagoamp.acervorama.resources;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

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

import com.tiagoamp.acervorama.model.AcervoramaBusinessException;
import com.tiagoamp.acervorama.model.MediaItem;
import com.tiagoamp.acervorama.model.MediaItemFactory;
import com.tiagoamp.acervorama.service.MediaItemService;

@Path("media")
public class MediaResource {
	
	private MediaItemService service = new MediaItemService();
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<MediaItem> getMediaItems() {
		List<MediaItem> list = new ArrayList<>();
		try {
			list = service.getAll();
		} catch (AcervoramaBusinessException e) {			
			throw new ResponseProcessingException(Response.serverError().build(), e.getBusinessMessage());
		}
		return list;
	}
	
	@GET
	@Path("{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public MediaItem getMedia(@PathParam("id") long id) {
		MediaItem item = null;
		try {
			item = service.findById(id);
		} catch (AcervoramaBusinessException e) {
			throw new ResponseProcessingException(Response.serverError().build(), e.getBusinessMessage());
		}
		return item;
	}

	@POST
	@Path("{type}")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response add(@PathParam("type") String type, String content, @Context UriInfo uriInfo) {
		com.tiagoamp.acervorama.model.MediaType mediaType = com.tiagoamp.acervorama.model.MediaType.valueOf(type.toUpperCase());
		MediaItem item = MediaItemFactory.fromJson(content, mediaType.getItemSubclass());
		try {
			service.insert(item);
			item = service.findByPath(item.getFilePath());			
		} catch (AcervoramaBusinessException e) {
			throw new ResponseProcessingException(Response.serverError().build(), e.getBusinessMessage());
		}	
		URI uri = uriInfo.getAbsolutePathBuilder().path(String.valueOf(item.getId())).build();
		return Response.created(uri).build();
	}
	
	@DELETE
	@Path("{id}")
	public Response delete(@PathParam("id") long id) {
		try {
			service.delete(id);
		} catch (AcervoramaBusinessException e) {
			throw new ResponseProcessingException(Response.serverError().build(), e.getBusinessMessage());
		}
		return Response.ok().build();
	}
	
	@PUT
	@Path("{type}/{id}")
	public Response update(@PathParam("type") String type, @PathParam("id") long id, String content) {
		com.tiagoamp.acervorama.model.MediaType mediaType = com.tiagoamp.acervorama.model.MediaType.valueOf(type.toUpperCase());
		MediaItem item = MediaItemFactory.fromJson(content, mediaType.getItemSubclass());
		item.setId(id);
		try {
			service.update(item);
		} catch (AcervoramaBusinessException e) {
			throw new ResponseProcessingException(Response.serverError().build(), e.getBusinessMessage());
		}
		return Response.ok().build();
	}
	
}
