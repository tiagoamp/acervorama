package com.tiagoamp.acervorama.resources;

import static org.junit.Assert.*;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class MediaResourceTest extends MediaResource {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	// Integration tests later... To DO !
	
	/*@Test
	public void testGetMedia_shouldReturnValidOutput() {
		Client client = ClientBuilder.newClient();
		WebTarget target = client.target("http://localhost:8080/acervorama");
		String content = target.path("/webapi/media").request().get(String.class);
		assertNotNull(content);
	}*/

}
