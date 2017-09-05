package com.tiagoamp.acervorama.resources;

import java.io.IOException;
import java.net.URI;
import java.nio.file.Paths;
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
import javax.ws.rs.QueryParam;
import javax.ws.rs.client.ResponseProcessingException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.google.gson.Gson;
import com.tiagoamp.acervorama.model.AcervoramaBusinessException;
import com.tiagoamp.acervorama.model.MediaItem;
import com.tiagoamp.acervorama.model.scanner.MediaItemFileScanner;
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
	public Response add(String content) {
		MediaItem item = new Gson().fromJson(content, MediaItem.class);
		try {
			service.insert(item);
			item = service.findByPath(item.getFilePath());
		} catch (AcervoramaBusinessException e) {
			throw new ResponseProcessingException(Response.serverError().build(), e.getBusinessMessage());
		}
		URI uri = URI.create("/webapi/media/" + item.getId());
		return Response.created(uri).build();
	}
	
	@POST
	@Path("scan")
	public Response scan(@QueryParam("from") String from, @QueryParam("media") String type) {
		java.nio.file.Path origin = Paths.get(from);
		com.tiagoamp.acervorama.model.MediaType mediaType = com.tiagoamp.acervorama.model.MediaType.valueOf(type);
		MediaItemFileScanner scanner = new MediaItemFileScanner(origin, mediaType);
		try {
			List<MediaItem> list = scanner.perform();
		} catch (IOException e) {
			throw new ResponseProcessingException(Response.serverError().build(), e);
		}
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
	public Response update(String content) {
		MediaItem item = new Gson().fromJson(content, MediaItem.class);
		try {
			service.update(item);
		} catch (AcervoramaBusinessException e) {
			throw new ResponseProcessingException(Response.serverError().build(), e.getBusinessMessage());
		}
		return Response.ok().build();
	}
	
}
