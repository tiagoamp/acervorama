package com.tiagoamp.acervorama.resources;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.tiagoamp.acervorama.model.scanner.FileScanner;

@Path("scanner")
public class ScannerResource {
	
	private FileScanner scanner;
	
		
	@GET
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public List<java.nio.file.Path> scan(String content) {
		//TODO Converts json content message body to Scanner object...
		
		List<java.nio.file.Path> result = new ArrayList<>();
		
		System.out.println(content);
		
		/*try {
			result = scanner.perform();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		
		return result;
	}
		
}
