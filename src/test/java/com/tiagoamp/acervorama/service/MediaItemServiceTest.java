package com.tiagoamp.acervorama.service;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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

import static org.junit.Assert.*;

import com.tiagoamp.acervorama.dao.MediaItemDao;
import com.tiagoamp.acervorama.model.AcervoramaBusinessException;
import com.tiagoamp.acervorama.model.AudioItem;
import com.tiagoamp.acervorama.model.MediaItem;
import com.tiagoamp.acervorama.service.MediaItemService;


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
	
	@Test(expected=AcervoramaBusinessException.class)
	public void testInsert_shouldThrowException() throws Exception {
		daoMock.create(item);
		EasyMock.expectLastCall().andThrow(new SQLException());
		replayAll();
		
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
	
	@Test(expected=AcervoramaBusinessException.class)
	public void testUpdate_shouldThrowException() throws Exception {
		daoMock.update(item);
		EasyMock.expectLastCall().andThrow(new SQLException());
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
	
	@Test(expected=AcervoramaBusinessException.class)
	public void testDelete_shouldThrowException() throws Exception {
		Long id = item.getId();
		daoMock.delete(id);
		EasyMock.expectLastCall().andThrow(new SQLException());
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
	
	@Test(expected=AcervoramaBusinessException.class)
	public void testFindById_shouldThrowException() throws Exception {
		Long id = item.getId();
		EasyMock.expect(daoMock.findById(id)).andThrow(new SQLException());
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
	
	@Test(expected=AcervoramaBusinessException.class)
	public void testFindByPath_shouldThrowException() throws Exception {
		Path filePath = item.getFilePath();
		EasyMock.expect(daoMock.findByPath(filePath)).andThrow(new SQLException());
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
	
	@Test(expected=AcervoramaBusinessException.class)
	public void testFindByHash_shouldThrowException() throws Exception {
		String hash = item.getHash();
		EasyMock.expect(daoMock.findByHash(hash)).andThrow(new SQLException());
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
	
	@Test(expected=AcervoramaBusinessException.class)
	public void testFindByFileNameLike_shouldThrowException() throws Exception {
		String filename = item.getFilename();
		EasyMock.expect(daoMock.findByFileNameLike(filename)).andThrow(new SQLException());
		replayAll();
		
		service.findByFileNameLike(filename);
		verifyAll();
	}

	@Test
	public void testFindByFields() throws Exception {
		EasyMock.expect(daoMock.findByFields(EasyMock.anyString(), EasyMock.anyString(), EasyMock.anyString())).andReturn(new ArrayList<>());
		replayAll();
		
		service.findByFields("filename", "classification", "description");
		verifyAll();
	}
	
	@Test
	public void testFilterByTags() throws Exception {
		List<MediaItem> originList = getItemsForTests();
		List<MediaItem> result = service.filterByTags(originList, new String[] {"Tag 03"});
		assertNotNull(result);
		assertEquals(1, result.size());
		assertEquals(originList.get(1), result.get(0));
	}
	
	@Test(expected=AcervoramaBusinessException.class)
	public void testFindByFields_shouldThrowException() throws Exception {
		EasyMock.expect(daoMock.findByFields(EasyMock.anyString(),  EasyMock.anyString(), EasyMock.anyString())).andThrow(new SQLException());
		replayAll();
		
		service.findByFields("filename", "classification", "description");
		verifyAll();
	}

	@Test
	public void testGetAll() throws Exception {
		EasyMock.expect(daoMock.findAll()).andReturn(new ArrayList<>());
		replayAll();
		
		service.getAll();
		verifyAll();
	}
	
	@Test(expected=AcervoramaBusinessException.class)
	public void testGetAll_shouldThrowException() throws Exception {
		EasyMock.expect(daoMock.findAll()).andThrow(new SQLException());
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
	
	private List<MediaItem> getItemsForTests() {
		Path testFilePath1 = Paths.get("fake", "test", "file1.txt");
		Path testFilePath2 = Paths.get("fake", "test", "file2.txt");
		
		MediaItem item1 = new AudioItem(testFilePath1);
		item1.setClassification("Classification");
		item1.setTags("Tag 01 ; Tag 02");
		item1.addTag("Added tag");
		item1.setDescription("Description");
		item1.setId(10L);
		
		MediaItem item2 = new AudioItem(testFilePath2);
		item2.setClassification("Classification");
		item2.setTags("Tag 03 ; Tag 04");
		item2.addTag("Added tag");
		item2.setDescription("Description");
		item2.setId(20L);
		
		List<MediaItem> list = new ArrayList<>();
		list.add(item1);
		list.add(item2);
		
		return list;
	}

}
