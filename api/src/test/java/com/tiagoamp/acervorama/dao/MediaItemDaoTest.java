package com.tiagoamp.acervorama.dao;

import static org.assertj.core.api.Assertions.assertThat;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import com.tiagoamp.acervorama.model.AudioItem;
import com.tiagoamp.acervorama.model.ImageItem;
import com.tiagoamp.acervorama.model.MediaItem;
import com.tiagoamp.acervorama.model.MediaTypeAcervo;
import com.tiagoamp.acervorama.model.TextItem;
import com.tiagoamp.acervorama.model.VideoItem;

@RunWith(SpringRunner.class)
@DataJpaTest
public class MediaItemDaoTest {
	
	@TestConfiguration
	static class MediaItemDaoTestContextConfiguration {
		
		@Bean(value="jpa")
		public MediaItemDao mediaItemDao() {
			return new MediaItemJpaDao();
		}
						
	}
	
	@Autowired
	private MediaItemDao mediaItemDao;

	@Autowired
	private TestEntityManager entityManager;  // uses h2 embedded database
	
	
	@Before
	public void setUp() throws Exception {
		cleanDatabaseDataForTests();
	}

	@After
	public void tearDown() throws Exception {
		cleanDatabaseDataForTests();				
	}
	
	
	@Test
	public void testCreate_shouldInsertEntity()  {
		// given
		AudioItem itemBeforeInsert = this.getItemForTests();
		LocalDate registerDate = itemBeforeInsert.getRegisterDate().toLocalDate();
		Path filePath = itemBeforeInsert.getFilePath();
		insertItemInDatabaseForTests();		
		// when
		MediaItem itemRetrieved = mediaItemDao.findByHash(itemBeforeInsert.getHash());		
		// then
		assertThat(itemBeforeInsert).isEqualTo(itemRetrieved);
		assertThat(registerDate).isEqualTo(itemRetrieved.getRegisterDate().toLocalDate()); // testing classes convertion
		assertThat(filePath).isEqualTo(itemRetrieved.getFilePath()); // testing classes convertion
	}
	
	@Test
	public void testUpdate_shouldUpdateEntity()  {
		// given
		MediaItem itemBeforeUpdate = insertItemInDatabaseForTests();  // transient --> managed --> detached
		itemBeforeUpdate.setFilename("New File Name");		
		// when
		mediaItemDao.update(itemBeforeUpdate);
		// then
		MediaItem itemAfterUpdate = mediaItemDao.findById(itemBeforeUpdate.getId());
		assertThat(itemBeforeUpdate.getFilename()).isEqualTo(itemAfterUpdate.getFilename());
	}
	
	@Test
	public void testDelete_shouldDeleteEntity() {
		// given
		MediaItem itemRetrieved = insertItemInDatabaseForTests();  // transient --> managed --> detached
		// when
		mediaItemDao.delete(itemRetrieved.getId());
		// then
		itemRetrieved = mediaItemDao.findById(itemRetrieved.getId());
		assertThat(itemRetrieved).isNull();
	}
	
	@Test
	public void testFindById_shouldReturnValidOutput()  {
		// given
		AudioItem itemInserted = (AudioItem) insertItemInDatabaseForTests();
		// when
		MediaItem itemRetrievedById = mediaItemDao.findById(itemInserted.getId());
		// then
		assertThat(itemInserted.getId()).isEqualTo(itemRetrievedById.getId());		
	}
	
	@Test
	public void testFindAll_emptyDataBase_shouldReturnEmptyList() {
		List<MediaItem> list = mediaItemDao.findAll();
		assertThat(list).isEmpty();
	}
	
	@Test
	public void testFindAll_shouldReturnValidOutput() {
		// given
		int count = insertItemsInDatabaseForTests().size();
		// when
		List<MediaItem> list = mediaItemDao.findAll();
		// then
		assertThat(list.size()).isEqualTo(count);
	}
		
	@Test
	public void testFindByPath_shouldReturnValidOutput() {
		// given
		Path path = insertItemInDatabaseForTests().getFilePath();
		// when
		MediaItem itemRetrieved = mediaItemDao.findByPath(path);
		// then
		assertThat(itemRetrieved).isNotNull().hasFieldOrPropertyWithValue("filePath", path);
	}
	
	@Test
	public void testFindByHash_shouldReturnValidOutput() {
		// given
		String hash = insertItemInDatabaseForTests().getHash();
		// when 
		MediaItem itemRetrieved = mediaItemDao.findByHash(hash);
		assertThat(itemRetrieved).isNotNull().hasFieldOrPropertyWithValue("hash", hash);
	}
	
	@Test
	public void testFindByFileNameLike_shouldReturnValidOutput() {
		// given
		MediaItem item = insertItemInDatabaseForTests();
		String partialFileName = item.getFilename().substring(0,3);
		// when
		List<MediaItem> list = mediaItemDao.findByFileNameLike(partialFileName);
		// then
		assertThat(list).contains(item);
	}

	@Test
	public void testFindByFields_noneFieldsMatches_shouldReturnEmptyList() {
		// given
		insertItemsInDatabaseForTests();
		// when
		List<MediaItem> list = mediaItemDao.findByFields("nothing", "nothing", null);
		// then
		assertThat(list).isEmpty();
	}
	
	@Test
	public void testFindByFields_allFieldsMatches_shouldReturnValidOutput() {
		// given
		MediaItem item = insertItemInDatabaseForTests();
		// when
		List<MediaItem> list = mediaItemDao.findByFields(item.getFilename(), item.getClassification(), MediaTypeAcervo.AUDIO);
		// then
		assertThat(list).containsOnly(item);		
	}
	
	@Test
	public void testFindByFields_byFileName_shouldReturnValidOutput() {
		// given
		MediaItem item = insertItemInDatabaseForTests();
		// when 
		List<MediaItem> list = mediaItemDao.findByFields(item.getFilename(), null, null);
		// then 
		assertThat(list).containsExactly(item);
	}
	
	@Test
	public void testFindByFields_byClassification_shouldReturnValidOutput() {
		// given
		MediaItem item = insertItemInDatabaseForTests();
		// when
		List<MediaItem> list = mediaItemDao.findByFields(null, item.getClassification(), null);
		// then
		assertThat(list).containsExactly(item);
	}
	
	@Test
	public void testFindByFields_byMediaType_shouldReturnValidOutput()  {
		// given
		MediaItem item = insertItemInDatabaseForTests();
		// when
		List<MediaItem> list = mediaItemDao.findByFields(null, null, MediaTypeAcervo.AUDIO);
		// then
		assertThat(list).containsExactly(item);
	}

	
	// helper private methods ***
	
	private void cleanDatabaseDataForTests() {
		List<MediaItem> list = mediaItemDao.findAll();
		list.forEach((item) -> {
			entityManager.remove(item);
			entityManager.flush();
		} );				
	}
	
	private AudioItem getItemForTests() {
		Path testFilePath = Paths.get("fake","test","file.txt");
		AudioItem item = new AudioItem(testFilePath);
		item.setClassification("Classification".toUpperCase());
		item.setTags("TAG 01,TAG 02,TAG 03");
		item.setDescription("Description");
		item.setTitle("Title");
		item.setAuthor("Author");
		return item;
	}
	
	private MediaItem insertItemInDatabaseForTests() {
		AudioItem item = this.getItemForTests();
		entityManager.persist(item);
		entityManager.flush();
		return mediaItemDao.findByHash(item.getHash());
	}
	
	private List<MediaItem> insertItemsInDatabaseForTests() {
		Path testFilePath1 = Paths.get("fake","test","file1.txt");
		Path testFilePath2 = Paths.get("fake","test","file2.txt");
		Path testFilePath3 = Paths.get("fake","test","file3.txt");
		Path testFilePath4 = Paths.get("fake","test","file4.txt");
		MediaItem item1 = new AudioItem(testFilePath1);
		MediaItem item2 = new ImageItem(testFilePath2);
		MediaItem item3 = new TextItem(testFilePath3);
		MediaItem item4 = new VideoItem(testFilePath4);
		
		((AudioItem)item1).setTitle("NEW TITLE");
		
		List<MediaItem> list = Arrays.asList(item1, item2, item3, item4);
		
		list.forEach((item) -> { 
			entityManager.persist(item);
			entityManager.flush();
		});
		
		return mediaItemDao.findAll();
	}

}
