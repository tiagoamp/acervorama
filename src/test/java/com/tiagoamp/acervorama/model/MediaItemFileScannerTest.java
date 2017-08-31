package com.tiagoamp.acervorama.model;

import static org.junit.Assert.*;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class MediaItemFileScannerTest {

	private MediaItemFileScanner scanner;
	
	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testScan_searchForAllMediaTypes_shouldFindThemAll() throws IOException {
		scanner = new MediaItemFileScanner(Paths.get("testFiles", "basedir"));
		List<MediaItem> result = scanner.perform();
		
		assertNotNull("Should return not null instance.", result);
		assertEquals("Should find all the media types.", 25, result.size());
		
		long countOfRightFiles = result.stream().filter(m -> m.getFilename().contains("dummy")).count();
		assertEquals("Should find all the media types.", 25, countOfRightFiles);
	}

}
