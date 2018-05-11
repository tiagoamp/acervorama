package com.tiagoamp.acervorama.resources;

import java.io.IOException;
import java.nio.file.NoSuchFileException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.client.ResponseProcessingException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.tiagoamp.acervorama.model.AcervoramaBusinessException;
import com.tiagoamp.acervorama.model.MediaTypeAcervo;
import com.tiagoamp.acervorama.model.scanner.FileScanner;

@CrossOrigin
@RestController
@RequestMapping("/scanner")
public class ScannerResource {
	
	private FileScanner scanner;
		
	@RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON)
	@ResponseBody
	public String[] scan( @RequestParam(value = "type", required=false) String mediatype,
			              @RequestParam(value = "dirPath", required=false) String directoryPath ) {
	
		java.nio.file.Path origin = getOriginFromParam(directoryPath);
		String[] fileExtensions = getMediaFileExtensionsFromParam(mediatype);
		
		scanner = new FileScanner(origin, fileExtensions);
		
		List<java.nio.file.Path> list = new ArrayList<>();
		try {
			list = scanner.perform();
		} catch (IOException e) {
			if (e instanceof NoSuchFileException) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Diretório inválido!", new AcervoramaBusinessException("Diretório inválido")); 
			throw new ResponseProcessingException(Response.serverError().build(), new AcervoramaBusinessException("IO error!", e));
		}
				
		return list.stream().map(p -> p.toAbsolutePath().toString()).toArray(String[]::new);
	}
	
	
	private java.nio.file.Path getOriginFromParam(String directory) {
		return directory.isEmpty() ? null : Paths.get(directory);
	}
	
	private String[] getMediaFileExtensionsFromParam(String mediaType) {
		return mediaType.isEmpty() ? null : MediaTypeAcervo.valueOf(mediaType.toUpperCase()).getFileExtensions();
	}
		
}
