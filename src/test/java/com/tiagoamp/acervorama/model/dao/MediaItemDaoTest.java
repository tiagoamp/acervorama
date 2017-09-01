package com.tiagoamp.acervorama.model.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.tiagoamp.acervorama.model.MediaItem;

public class MediaItemDaoTest {
	
	private MediaItemDao dao;

	@Before
	public void setUp() throws Exception {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("PU_ACERVO");
		EntityManager em = emf.createEntityManager();
		dao = new MediaItemJpaDao(em);
		
		cleanDatabaseDataForTests();
	}

	@After
	public void tearDown() throws Exception {
		dao = null;
	}

	
	@Test
	public void testCreate_shouldInsertEntity() throws SQLException {
		MediaItem item = this.getItemForTests(); 
		dao.create(item);
		
		MediaItem itemRetrieved = dao.findByPath(item.getFilePath());
		assertEquals("Must retrieve inserted entity.", item, itemRetrieved);		
	}
	
	@Test
	public void testUpdate_shouldUpdateEntity() throws SQLException {
		MediaItem itemRetrieved = insertItemInDatabaseForTests();  // transient --> managed --> detached
		String newFilename = "New File Name";
		itemRetrieved.setFilename(newFilename);
		
		dao.update(itemRetrieved);
		MediaItem itemAfterUpdate = dao.findById(itemRetrieved.getId());
		assertNotNull(itemAfterUpdate);
		assertEquals("'Name' should be updated.", newFilename, itemAfterUpdate.getFilename());
	}
	
	@Test
	public void testDelete_shouldDeleteEntity() throws SQLException {
		MediaItem itemRetrieved = insertItemInDatabaseForTests();  // transient --> managed --> detached
		long id = itemRetrieved.getId();
		dao.delete(id);
		
		itemRetrieved = dao.findById(id);
		assertNull("Entity must be deleted.", itemRetrieved);
	}
	
	@Test
	public void testFindById_shouldReturnValidOutput() throws SQLException {
		MediaItem itemInserted = insertItemInDatabaseForTests();
		
		MediaItem itemRetrievedById = dao.findById(itemInserted.getId());
		assertNotNull("Must retrieve the entity by Id.", itemRetrievedById);
		assertEquals("Must retrieve the entity with same 'Id'.", itemInserted.getId(), itemRetrievedById.getId());		
	}
	
	@Test
	public void testFindAll_emptyDataBase_shouldReturnEmptyList() throws SQLException {
		List<MediaItem> list = dao.findAll();
		assertTrue("Must not retrieve entities.", list.isEmpty());
	}
	
	@Test
	public void testFindAll_shouldReturnValidOutput() throws SQLException {
		insertItemsInDatabaseForTests();
		
		List<MediaItem> list = dao.findAll();
		assertNotNull("Must return entities previously inserted.", list);
		assertEquals("Should return 2 entities.", 2, list.size());
	}
	
	
	@Test
	public void testFindByPath() {
		fail("Not yet implemented");
	}

	@Test
	public void testFindByFileNameLike() {
		fail("Not yet implemented");
	}

	@Test
	public void testFindByFields() {
		fail("Not yet implemented");
	}

	
	// helper private methods
	private void cleanDatabaseDataForTests() {
		try {
			List<MediaItem> list = dao.findAll();
			for (Iterator<MediaItem> iterator = list.iterator(); iterator.hasNext();) {
				MediaItem item = (MediaItem) iterator.next();
				dao.delete(item.getId());
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	private MediaItem getItemForTests() {
		Path testFilePath = Paths.get("fake","test","file.txt");
		MediaItem item = new MediaItem(testFilePath);
		return item;
	}
	
	private MediaItem insertItemInDatabaseForTests() throws SQLException {
		MediaItem item = this.getItemForTests();
		dao.create(item);
		return dao.findByPath(item.getFilePath());
	}
	
	private List<MediaItem> insertItemsInDatabaseForTests() throws SQLException {
		Path testFilePath1 = Paths.get("fake","test","file1.txt");
		Path testFilePath2 = Paths.get("fake","test","file2.txt");
		MediaItem item1 = new MediaItem(testFilePath1);
		MediaItem item2 = new MediaItem(testFilePath2);
		dao.create(item1);
		dao.create(item2);
		return dao.findAll();
	}

}
