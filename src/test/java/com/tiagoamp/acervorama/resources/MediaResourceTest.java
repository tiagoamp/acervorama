package com.tiagoamp.acervorama.resources;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
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
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.tiagoamp.acervorama.model.AudioItem;
import com.tiagoamp.acervorama.model.MediaItem;
import com.tiagoamp.acervorama.model.MediaTypeAcervo;
import com.tiagoamp.acervorama.service.MediaItemService;

@RunWith(SpringRunner.class)
@WebMvcTest(MediaResource.class)
public class MediaResourceTest {

	@Autowired
	private MockMvc mvc;
	
	@MockBean
	private MediaItemService mediaItemService;
	
	
	@Before
	public void setUp() throws Exception {
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
				.andReturn();
		
		@SuppressWarnings("unchecked")
		List<MediaItem> resultList = new Gson().fromJson(result.getResponse().getContentAsString(), List.class);		
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
				.andReturn();
		
		@SuppressWarnings("unchecked")
		List<MediaItem> resultList = new Gson().fromJson(result.getResponse().getContentAsString(), List.class);
		assertThat(resultList.size()).isEqualTo(getItemsListForTests().size());
	}
	
	@Test
	public void testGetMedia_byId_shouldReturnValidOutput() throws Exception {
		long id = getItemForTests().getId();
		Mockito.when(mediaItemService.findById(id)).thenReturn(getItemForTests());
		
		MvcResult result = mvc.perform(get("/media/{id}",id).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(content().contentType(org.springframework.http.MediaType.APPLICATION_JSON_UTF8))
				.andReturn();
		
		assertThat(result.getResponse().getContentAsString()).isNotNull();
	}
	
	@Test
	public void testGetMediaByHash_shouldReturnValidOutput() throws Exception {
		String hash = getItemForTests().getHash();
		Mockito.when(mediaItemService.findByHash(hash)).thenReturn(getItemForTests());
		
		MvcResult result = mvc.perform(get("/media/hash/{hash}",hash).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(content().contentType(org.springframework.http.MediaType.APPLICATION_JSON_UTF8))
				.andReturn();
		
		assertThat(result.getResponse().getContentAsString()).isNotNull();
	}
	
	@Test
	public void testAdd_shouldReturnValidOutput() throws Exception {
		MediaItem item = getItemForTests();
		Mockito.when(mediaItemService.insert(item)).thenReturn(item);
		
		System.out.println(item.toJson());
		
		MvcResult result = mvc.perform(post("/media")
						.contentType(MediaType.APPLICATION_JSON)
						.content(item.toJson()) // PROVAVELMENTE ESTE TOJSON TA MANDANDO ZUADO
						)
						
				.andExpect(status().isCreated())
				.andExpect(content().contentType(org.springframework.http.MediaType.APPLICATION_JSON_UTF8))
				.andReturn();
		
		assertThat(result.getResponse().getContentAsString()).isNotNull();
		
		// TRETA TÁ AQUI ==>   AudioItem["registerDate"])
		
		
		// https://stackoverflow.com/questions/27170298/spring-reponsebody-requestbody-with-abstract-class
		
		/*
* 2018-03-09 19:03:25.295  WARN 3643 --- [           main] .w.s.m.s.DefaultHandlerExceptionResolver : Failed to read HTTP message: org.springframework.http.converter.HttpMessageNotReadableException: JSON parse error: Unexpected token (START_OBJECT), expected VALUE_STRING: Expected array or string.; nested exception is com.fasterxml.jackson.databind.exc.MismatchedInputException: Unexpected token (START_OBJECT), expected VALUE_STRING: Expected array or string.
 at [Source: (PushbackInputStream); line: 1, column: 185] (through reference chain: com.tiagoamp.acervorama.model.AudioItem["registerDate"])

		 * *
		 */
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
