package com.tiagoamp.acervorama.model.dao;

import static org.junit.Assert.assertEquals;
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
	public void testUpdate() {
		fail("Not yet implemented");
	}

	@Test
	public void testDelete() {
		fail("Not yet implemented");
	}

	@Test
	public void testFindById() {
		fail("Not yet implemented");
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

	@Test
	public void testFindAll() {
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

}
