package com.tiagoamp.acervorama.resources;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

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
	
		java.nio.file.Path origin = getOriginFromParam(directoryPath);
		String[] fileExtensions = getMediaFileExtensionsFromParam(mediatype);
		
		scanner = new FileScanner(origin, fileExtensions);
		
		List<java.nio.file.Path> list = new ArrayList<>();
		try {
			list = scanner.perform();
		} catch (IOException e) {
			throw new ResponseProcessingException(Response.serverError().build(), new AcervoramaBusinessException("IO error!", e));
		}
		
		return (String[]) list.stream().map(p->toString()).toArray();
	}
	
	
	private java.nio.file.Path getOriginFromParam(String directory) {
		return directory.isEmpty() ? null : Paths.get(directory);
	}
	
	private String[] getMediaFileExtensionsFromParam(String mediaType) {
		return mediaType.isEmpty() ? null : com.tiagoamp.acervorama.model.MediaType.valueOf(mediaType.toUpperCase()).getFileExtensions();
	}
		
}
