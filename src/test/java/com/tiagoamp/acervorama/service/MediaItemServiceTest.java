package com.tiagoamp.acervorama.service;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.easymock.EasyMockSupport;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import com.tiagoamp.acervorama.dao.MediaItemDao;
import com.tiagoamp.acervorama.dao.MediaItemJpaDao;
import com.tiagoamp.acervorama.model.AcervoramaBusinessException;
import com.tiagoamp.acervorama.model.AudioItem;
import com.tiagoamp.acervorama.model.MediaItem;
import com.tiagoamp.acervorama.model.MediaTypeAcervo;

@RunWith(SpringRunner.class)
public class MediaItemServiceTest extends EasyMockSupport {
	
	@TestConfiguration
	static class MediaItemServiceTestContextConfiguration {
		
		@Bean
		public MediaItemService mediaItemService() {
			return new MediaItemService();
		}
		
		@Bean(value="jpa")
		public MediaItemDao mediaItemDao() {
			return new MediaItemJpaDao();
		}
						
	}
	
	@Autowired
	private MediaItemService mediaItemService;
	
	@MockBean
	private MediaItemDao daoMock;
	
	private MediaItem item;
		
	
	@Before
	public void setUp() throws Exception {
		item = getItemForTests();
	}

	@After
	public void tearDown() throws Exception {
		item = null;
	}

	
	@Test
	public void testInsert() throws AcervoramaBusinessException {
		Mockito.when(daoMock.findByHash(item.getHash())).thenReturn(null);
		mediaItemService.insert(item);		
	}
	
	@Test(expected=AcervoramaBusinessException.class)
	public void testInsert_sameFilePath_shouldThrowException() throws Exception {
		Mockito.when(daoMock.findByHash(item.getHash())).thenReturn(item);		
		mediaItemService.insert(item);				
	}
	
	@Test
	public void testUpdate() {
		mediaItemService.update(item);		
	}
	
	@Test
	public void testDelete() {
		mediaItemService.delete(item.getId());		
	}
	
	@Test
	public void testFindById() {
		mediaItemService.findById(item.getId());		
	}
	
	@Test
	public void testFindByPath() {
		mediaItemService.findByPath(item.getFilePath());		
	}
	
	@Test
	public void testFindByHash() throws Exception {
		mediaItemService.findByHash(item.getHash());
	}
	
	@Test
	public void testFindByFileNameLike() {
		mediaItemService.findByFileNameLike(item.getFilename());		
	}
	
	@Test
	public void testFindByFields() {
		mediaItemService.findByFields("filename", "classification", MediaTypeAcervo.AUDIO);		
	}
	
	@Test
	public void testGetAll() {
		mediaItemService.getAll();		
	}
	
	
	// helper private methods
	private MediaItem getItemForTests() {
		Path testFilePath = Paths.get("fake", "test", "file.txt");
		MediaItem item = new AudioItem(testFilePath);
		item.setClassification("Classification");
		item.setTags("Tag 01 ; Tag 02");
		item.setDescription("Description");
		item.setId(20L);
		return item;
	}
	
}
