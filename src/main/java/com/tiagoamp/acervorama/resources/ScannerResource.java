package com.tiagoamp.acervorama.resources;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.client.ResponseProcessingException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.tiagoamp.acervorama.model.AcervoramaBusinessException;
import com.tiagoamp.acervorama.model.scanner.FileScanner;

@Path("scanner")
public class ScannerResource {
	
	private FileScanner scanner;
		
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public String[] scan( @QueryParam(value = "type") String mediatype, 
						  @QueryParam(value = "dirPath") String directoryPath) {
		
		java.nio.file.Path origin = null;
		if (!directoryPath.isEmpty()) origin = Paths.get(directoryPath);
		
		String[] fileExtensions = null;
		if (!mediatype.isEmpty()) {
			com.tiagoamp.acervorama.model.MediaType mediaType = com.tiagoamp.acervorama.model.MediaType.valueOf(mediatype.toUpperCase());
			fileExtensions = mediaType.getFileExtensions();
		}
		
		scanner = new FileScanner(origin, fileExtensions);
		
		List<java.nio.file.Path> list = new ArrayList<>();
		try {
			list = scanner.perform();
		} catch (IOException e) {
			throw new ResponseProcessingException(Response.serverError().build(), new AcervoramaBusinessException("IO error!", e));
		}
		
		return (String[]) list.stream().map(p->toString()).toArray();
	}
		
}
