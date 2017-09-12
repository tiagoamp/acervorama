package com.tiagoamp.acervorama.resources;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.client.ResponseProcessingException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.google.gson.Gson;
import com.tiagoamp.acervorama.model.AcervoramaBusinessException;
import com.tiagoamp.acervorama.model.ScannerInputTO;
import com.tiagoamp.acervorama.model.scanner.FileScanner;

@Path("scanner")
public class ScannerResource {
	
	private FileScanner scanner;
	
		
	@GET
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public List<java.nio.file.Path> scan(String content) {
		ScannerInputTO to = new Gson().fromJson(content, ScannerInputTO.class);
		
		java.nio.file.Path origin = null;
		if (!to.getPath().isEmpty()) origin = Paths.get(to.getPath());
		
		String[] fileExtensions = null;
		if (!to.getType().isEmpty()) {
			com.tiagoamp.acervorama.model.MediaType mediaType = com.tiagoamp.acervorama.model.MediaType.valueOf(to.getType().toUpperCase());
			fileExtensions = mediaType.getFileExtensions();
		}
		
		scanner = new FileScanner(origin, fileExtensions);
		
		List<java.nio.file.Path> result = new ArrayList<>();
		try {
			result = scanner.perform();
		} catch (IOException e) {
			throw new ResponseProcessingException(Response.serverError().build(), new AcervoramaBusinessException("IO error!", e));
		}
		
		return result;
	}
		
}
