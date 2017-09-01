package com.tiagoamp.acervorama.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

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
		
		long countOfCorrectFiles = result.stream().filter(m -> m.getFilename().contains("dummy")).count();
		assertEquals("Should find all the media types with name containing 'dummy'.", 25, countOfCorrectFiles);
	}
	
	@Test
	public void testScan_searchForAudioMediaTypes_shouldFindValidOutput() throws IOException {
		scanner = new MediaItemFileScanner(Paths.get("testFiles", "basedir"), MediaType.AUDIO);
		List<MediaItem> result = scanner.perform();		
		assertNotNull("Should return not null instance.", result);
		assertEquals("Should find audio media types.", 4, result.size());
	}
	
	@Test
	public void testScan_searchForImageMediaTypes_shouldFindValidOutput() throws IOException {
		scanner = new MediaItemFileScanner(Paths.get("testFiles", "basedir"), MediaType.IMAGE);
		List<MediaItem> result = scanner.perform();		
		assertNotNull("Should return not null instance.", result);
		assertEquals("Should find image media types.", 5, result.size());
	}
	
	@Test
	public void testScan_searchForTextMediaTypes_shouldFindValidOutput() throws IOException {
		scanner = new MediaItemFileScanner(Paths.get("testFiles", "basedir"), MediaType.TEXT);
		List<MediaItem> result = scanner.perform();		
		assertNotNull("Should return not null instance.", result);
		assertEquals("Should find text media types.", 7, result.size());
	}
	
	@Test
	public void testScan_searchForVideoMediaTypes_shouldFindValidOutput() throws IOException {
		scanner = new MediaItemFileScanner(Paths.get("testFiles", "basedir"), MediaType.VIDEO);
		List<MediaItem> result = scanner.perform();		
		assertNotNull("Should return not null instance.", result);
		assertEquals("Should find video media types.", 9, result.size());
	}

}
