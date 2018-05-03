package com.tiagoamp.acervorama.model.scanner;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.tiagoamp.acervorama.model.MediaTypeAcervo;

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
		int totalExpected = 28;
		List<Path> result = scanner.perform();		
		assertThat(result.size()).isEqualTo(totalExpected);		
	}
	
	@Test
	public void testScan_searchForAudioMediaTypes_shouldFindValidOutput() throws IOException {
		scanner = new FileScanner(baseTestFilesDirectory, MediaTypeAcervo.AUDIO.getFileExtensions());
		int totalExpected = 4;
		List<Path> result = scanner.perform();		
		assertThat(result.size()).isEqualTo(totalExpected);
	}
	
	@Test
	public void testScan_searchForImageMediaTypes_shouldFindValidOutput() throws IOException {
		scanner = new FileScanner(baseTestFilesDirectory, MediaTypeAcervo.IMAGE.getFileExtensions());
		int totalExpected = 5;
		List<Path> result = scanner.perform();		
		assertThat(result.size()).isEqualTo(totalExpected);
	}
	
	@Test
	public void testScan_searchForTextMediaTypes_shouldFindValidOutput() throws IOException {
		scanner = new FileScanner(baseTestFilesDirectory, MediaTypeAcervo.TEXT.getFileExtensions());
		int totalExpected = 7;
		List<Path> result = scanner.perform();		
		assertThat(result.size()).isEqualTo(totalExpected);
	}
	
	@Test
	public void testScan_searchForVideoMediaTypes_shouldFindValidOutput() throws IOException {
		scanner = new FileScanner(baseTestFilesDirectory, MediaTypeAcervo.VIDEO.getFileExtensions());
		int totalExpected = 9;
		List<Path> result = scanner.perform();		
		assertThat(result.size()).isEqualTo(totalExpected);
	}

}
