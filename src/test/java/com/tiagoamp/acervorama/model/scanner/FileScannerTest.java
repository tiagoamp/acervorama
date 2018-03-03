package com.tiagoamp.acervorama.model.scanner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.tiagoamp.acervorama.model.MediaType;

public class FileScannerTest {

	private FileScanner scanner;
	private Path baseTestFilesDirectory; 
	
	
	@Before
	public void setUp() throws Exception {
		this.baseTestFilesDirectory = Paths.get("testFiles", "basedir");
	}
	
	public void tearDown() throws Exception {
		baseTestFilesDirectory = null;
		scanner = null;
	}
	
	
	@Test
	public void testScan_searchForAllFiles_shouldFindThemAll() throws IOException {
		scanner = new FileScanner(Paths.get("testFiles", "basedir"));
		List<Path> result = scanner.perform();
		
		assertNotNull("Should return not null instance.", result);
		assertEquals("Should find all the media types.", 28, result.size());		
	}
	
	@Test
	public void testScan_searchForAudioMediaTypes_shouldFindValidOutput() throws IOException {
		scanner = new FileScanner(baseTestFilesDirectory, MediaType.AUDIO.getFileExtensions());
		List<Path> result = scanner.perform();		
		assertEquals("Should find audio media types.", 4, result.size());
	}
	
	@Test
	public void testScan_searchForImageMediaTypes_shouldFindValidOutput() throws IOException {
		scanner = new FileScanner(baseTestFilesDirectory, MediaType.IMAGE.getFileExtensions());
		List<Path> result = scanner.perform();		
		assertEquals("Should find image media types.", 5, result.size());
	}
	
	@Test
	public void testScan_searchForTextMediaTypes_shouldFindValidOutput() throws IOException {
		scanner = new FileScanner(baseTestFilesDirectory, MediaType.TEXT.getFileExtensions());
		List<Path> result = scanner.perform();		
		assertEquals("Should find text media types.", 7, result.size());
	}
	
	@Test
	public void testScan_searchForVideoMediaTypes_shouldFindValidOutput() throws IOException {
		scanner = new FileScanner(baseTestFilesDirectory, MediaType.VIDEO.getFileExtensions());
		List<Path> result = scanner.perform();		
		assertEquals("Should find video media types.", 9, result.size());
	}

}
