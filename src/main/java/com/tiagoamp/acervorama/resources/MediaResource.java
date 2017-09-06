package com.tiagoamp.acervorama.resources;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

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

import com.google.gson.Gson;
import com.tiagoamp.acervorama.model.AcervoramaBusinessException;
import com.tiagoamp.acervorama.model.MediaItem;
import com.tiagoamp.acervorama.model.service.MediaItemService;

@Path("media")
public class MediaResource {
	
	private MediaItemService service = new MediaItemService();
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public String getMediaItems() {
		List<MediaItem> list;
		try {
			list = service.getAll();
		} catch (AcervoramaBusinessException e) {			
			throw new ResponseProcessingException(Response.serverError().build(), e.getBusinessMessage());
		}
		List<String> jsonList = list.stream().map(item -> item.toJson()).collect(Collectors.toList());
		return new Gson().toJson(jsonList);
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Response add(String content, @Context UriInfo uriInfo) {
		MediaItem item = new Gson().fromJson(content, MediaItem.class);
		try {
			service.insert(item);
			item = service.findByPath(item.getFilePath());			
		} catch (AcervoramaBusinessException e) {
			throw new ResponseProcessingException(Response.serverError().build(), e.getBusinessMessage());
		}	
		URI uri = uriInfo.getAbsolutePathBuilder().path(String.valueOf(item.getId())).build();
		return Response.created(uri).build();
	}
	
	@POST
	@Path("scan")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response scan(String content) {
		//TODO Converts json content message body to Scanner object...
		/*MediaItemFileScanner scanner = new MediaItemFileScanner(origin, mediaType);
		try {
			List<MediaItem> list = scanner.perform();
		} catch (IOException e) {
			throw new ResponseProcessingException(Response.serverError().build(), e);
		}*/
		return Response.ok().build();
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
	@Path("{id}")
	public Response update(@PathParam("id") long id, String content) {
		MediaItem item = new Gson().fromJson(content, MediaItem.class);
		item.setId(id);
		try {
			service.update(item);
		} catch (AcervoramaBusinessException e) {
			throw new ResponseProcessingException(Response.serverError().build(), e.getBusinessMessage());
		}
		return Response.ok().build();
	}
	
}
