package com.tiagoamp.acervorama.resources;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

import javax.ws.rs.core.MediaType;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tiagoamp.acervorama.model.AudioItem;
import com.tiagoamp.acervorama.model.MediaItem;
import com.tiagoamp.acervorama.model.MediaTypeAcervo;
import com.tiagoamp.acervorama.service.MediaItemService;

@RunWith(SpringRunner.class)
@WebMvcTest(MediaRestController.class)
public class MediaRestControllerTest {

	private ObjectMapper jsonMapper;
	
	@Autowired
	private MockMvc mvc;
	
	@MockBean
	private MediaItemService mediaItemService;
	
	
	@Before
	public void setUp() throws Exception {
		jsonMapper = new ObjectMapper();
		jsonMapper.findAndRegisterModules();
		jsonMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
	}

	@After
	public void tearDown() throws Exception {
	}

	
	@Test
	public void testGetMedia_noQueryParams_shouldReturnValidOutput() throws Exception {
		Mockito.when(mediaItemService.getAll()).thenReturn(getItemsListForTests());
		
		MvcResult result = mvc.perform(get("/media").contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(content().contentType(org.springframework.http.MediaType.APPLICATION_JSON_UTF8))
				.andDo(print())
				.andReturn();
		
		@SuppressWarnings("unchecked")
		List<MediaItemResource> resultList = jsonMapper.readValue(result.getResponse().getContentAsString(), List.class); 
		assertThat(resultList.size()).isEqualTo(getItemsListForTests().size());		
	}
	
	@Test
	public void testGetMedia_withQueryParams_shouldReturnValidOutput() throws Exception {
		Mockito.when(mediaItemService.findByFields("filename", "classification", MediaTypeAcervo.AUDIO)).thenReturn(getItemsListForTests());
		
		MvcResult result = mvc.perform(get("/media").contentType(MediaType.APPLICATION_JSON)
					.param("filename", "filename")
					.param("classification", "classification")
					.param("type", "AUDIO")
					)
				.andExpect(status().isOk())
				.andExpect(content().contentType(org.springframework.http.MediaType.APPLICATION_JSON_UTF8))
				.andDo(print())
				.andReturn();
		
		String jsonResource = result.getResponse().getContentAsString();
		@SuppressWarnings("unchecked")
		List<MediaItemResource> resultList = jsonMapper.readValue(jsonResource, List.class);
		assertThat(resultList.size()).isEqualTo(getItemsListForTests().size());
	}
	
	@Test
	public void testGetMedia_byId_shouldReturnValidOutput() throws Exception {
		long id = getItemForTests().getId();
		Mockito.when(mediaItemService.findById(id)).thenReturn(getItemForTests());
		
		MvcResult result = mvc.perform(get("/media/{id}",id).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(content().contentType(org.springframework.http.MediaType.APPLICATION_JSON_UTF8))
				.andDo(print())
				.andReturn();
		
		String jsonResource = result.getResponse().getContentAsString();
		MediaItemResource resource = jsonMapper.readValue(jsonResource, MediaItemResource.class);		
		assertThat(resource.getResource().getId()).isEqualTo(id);
	}
	
	@Test
	public void testGetMediaByHash_shouldReturnValidOutput() throws Exception {
		String hash = getItemForTests().getHash();
		Mockito.when(mediaItemService.findByHash(hash)).thenReturn(getItemForTests());
		
		MvcResult result = mvc.perform(get("/media/hash/{hash}",hash).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(content().contentType(org.springframework.http.MediaType.APPLICATION_JSON_UTF8))
				.andDo(print())
				.andReturn();
		
		String jsonResource = result.getResponse().getContentAsString();
		MediaItemResource resource = jsonMapper.readValue(jsonResource, MediaItemResource.class);
		assertThat(resource.getResource().getHash()).isEqualTo(hash);
	}
	
	@Test
	public void testAdd_shouldReturnValidOutput() throws Exception {
		MediaItem item = getItemForTests();
		Mockito.when(mediaItemService.insert(item)).thenReturn(item);
		
		MvcResult result = mvc.perform(post("/media")
						.contentType(MediaType.APPLICATION_JSON)
						.content(item.toJson())
						)						
				.andExpect(status().isCreated())
				.andExpect(content().contentType(org.springframework.http.MediaType.APPLICATION_JSON_UTF8))
				.andDo(print())
				.andReturn();
		
		String jsonResource = result.getResponse().getContentAsString();
		MediaItemResource resource = jsonMapper.readValue(jsonResource, MediaItemResource.class);		
		assertThat(resource.getResource()).isEqualTo(item);		
	}
	
	@Test
	public void testDelete_shouldReturnValidOutput() throws Exception {
		Long id = getItemForTests().getId();
		doNothing().when(mediaItemService).delete(id);
		mvc.perform(delete("/media/{id}",id)).andExpect(status().isNoContent());
	}
	
	@Test
	public void testUpdate_shouldReturnValidOutput() throws Exception {
		MediaItem item = getItemForTests();
		doNothing().when(mediaItemService).update(item);
		mvc.perform(put("/media/{id}",item.getId())
				.contentType(MediaType.APPLICATION_JSON)
				.content(item.toJson())
				)
			.andExpect(status().isOk());
	}
	
	
	// Helper methods ***
	
	private MediaItem getItemForTests() {
		Path testFilePath = Paths.get("fake", "test", "file.txt");
		MediaItem item = new AudioItem(testFilePath);
		item.setClassification("Classification");
		item.setTags("Tag 01 ; Tag 02");
		item.setDescription("Description");
		item.setId(20L);
		return item;
	}
	
	private List<MediaItem> getItemsListForTests() {
		Path testFilePath = Paths.get("fake", "test", "file1.txt");
		MediaItem item1 = new AudioItem(testFilePath);
		item1.setClassification("Classification");
		item1.setTags("Tag 01 ; Tag 02");
		item1.setDescription("Description One");
		item1.setId(20L);
				
		testFilePath = Paths.get("fake", "test", "file2.txt");
		MediaItem item2 = new AudioItem(testFilePath);
		item2.setClassification("Classification");
		item2.setTags("Tag 03 ; Tag 04");
		item2.setDescription("Description Two");
		item2.setId(30L);
		
		return Arrays.asList(item1, item2);		
	}
	
}
