package com.tiagoamp.acervorama.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.easymock.EasyMockSupport;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.tiagoamp.acervorama.model.AudioItem;
import com.tiagoamp.acervorama.model.MediaItem;
import com.tiagoamp.acervorama.model.MediaTypeAcervo;
import com.tiagoamp.acervorama.model.VideoItem;


public class MediaItemFilterTest extends EasyMockSupport {

	private MediaItemFilter filter = new MediaItemFilter();
	
	
	@Before
	public void setUp() throws Exception {		
	}

	@After
	public void tearDown() throws Exception {
	}

		
	@Test
	public void testFilterByTags() throws Exception {
		List<MediaItem> originList = getItemsForTests();
		List<MediaItem> result = filter.filterByTags(originList, new String[] {"Tag 03"});
		assertNotNull(result);
		assertEquals(1, result.size());
		assertEquals(originList.get(1), result.get(0));
	}
	
	@Test
	public void testFilterByMediaType() throws Exception {
		List<MediaItem> originList = getItemsForTests();
		List<MediaItem> result = filter.filterByMediaType(originList, MediaTypeAcervo.AUDIO);
		assertNotNull(result);
		assertEquals(1, result.size());
		assertEquals(originList.get(0), result.get(0));
	}
	
	
	// helper private methods
	private List<MediaItem> getItemsForTests() {
		Path testFilePath1 = Paths.get("fake", "test", "file1.txt");
		Path testFilePath2 = Paths.get("fake", "test", "file2.txt");
		
		MediaItem item1 = new AudioItem(testFilePath1);
		item1.setClassification("Classification");
		item1.setTags("Tag 01 ; Tag 02");
		item1.addTag("Added tag");
		item1.setDescription("Description");
		item1.setId(10L);
		
		MediaItem item2 = new VideoItem(testFilePath2);
		item2.setClassification("Classification");
		item2.setTags("Tag 03 , Tag 04");
		item2.addTag("Added tag");
		item2.setDescription("Description");
		item2.setId(20L);
		
		List<MediaItem> list = new ArrayList<>();
		list.add(item1);
		list.add(item2);
		
		return list;
	}

}
