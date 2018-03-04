package com.tiagoamp.acervorama.service;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.ArrayList;

import org.easymock.EasyMock;
import org.easymock.EasyMockRule;
import org.easymock.EasyMockSupport;
import org.easymock.Mock;
import org.easymock.MockType;
import org.easymock.TestSubject;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import com.tiagoamp.acervorama.dao.MediaItemDao;
import com.tiagoamp.acervorama.model.AcervoramaBusinessException;
import com.tiagoamp.acervorama.model.AudioItem;
import com.tiagoamp.acervorama.model.MediaItem;
import com.tiagoamp.acervorama.model.MediaTypeAcervo;


public class MediaItemServiceTest extends EasyMockSupport {
	
	@Rule
	public EasyMockRule rule = new EasyMockRule(this);
	
	@Mock(type = MockType.NICE)
	private MediaItemDao daoMock;
	
	@TestSubject
	private MediaItemService service = new MediaItemService();
	
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
	public void testInsert() throws SQLException, AcervoramaBusinessException {
		daoMock.create(item);
		replayAll();
				
		service.insert(item);
		verifyAll();
	}
	
	@Test(expected=AcervoramaBusinessException.class)
	public void testInsert_sameFilePath_shouldThrowException() throws Exception {
		daoMock.create(item);
		EasyMock.expect(daoMock.findByPath(item.getFilePath())).andReturn(item);
		replayAll();
		
		service.insert(item);
		service.insert(item);
		verifyAll();		
	}
	
	@Test
	public void testUpdate() throws Exception {
		daoMock.update(item);
		replayAll();
		
		service.update(item);
		verifyAll();
	}
	
	@Test
	public void testDelete() throws Exception {
		Long id = item.getId();
		daoMock.delete(id);
		replayAll();
		
		service.delete(id);
		verifyAll();
	}
	
	@Test
	public void testFindById() throws Exception {
		Long id = item.getId();
		EasyMock.expect(daoMock.findById(id)).andReturn(item);
		replayAll();
		
		service.findById(id);
		verifyAll();
	}
	
	@Test
	public void testFindByPath() throws Exception {
		Path filePath = item.getFilePath();
		EasyMock.expect(daoMock.findByPath(filePath)).andReturn(item);
		replayAll();
		
		service.findByPath(filePath);
		verifyAll();
	}
	
	@Test
	public void testFindByHash() throws Exception {
		String hash = item.getHash();
		EasyMock.expect(daoMock.findByHash(hash)).andReturn(item);
		replayAll();
		
		service.findByHash(hash);
		verifyAll();
	}
	
	@Test
	public void testFindByFileNameLike() throws Exception {
		String filename = item.getFilename();
		EasyMock.expect(daoMock.findByFileNameLike(filename)).andReturn(new ArrayList<>());
		replayAll();
		
		service.findByFileNameLike(filename);
		verifyAll();
	}
	
	@Test
	public void testFindByFields() throws Exception {
		EasyMock.expect(daoMock.findByFields(EasyMock.anyString(), EasyMock.anyString(), (MediaTypeAcervo)EasyMock.anyObject())).andReturn(new ArrayList<>());
		replayAll();
		
		service.findByFields("filename", "classification", MediaTypeAcervo.AUDIO);
		verifyAll();
	}
	
	@Test
	public void testGetAll() throws Exception {
		EasyMock.expect(daoMock.findAll()).andReturn(new ArrayList<>());
		replayAll();
		
		service.getAll();
		verifyAll();
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
