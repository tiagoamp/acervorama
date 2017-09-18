package com.tiagoamp.acervorama.resources;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.List;
import java.util.stream.Collectors;

import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.MessageBodyWriter;
import javax.ws.rs.ext.Provider;

import com.google.gson.Gson;
import com.tiagoamp.acervorama.model.MediaItem;

@Provider
@Produces(MediaType.APPLICATION_JSON)
public class ListOfMediaItemMessageBodyWriter implements MessageBodyWriter<List<? extends MediaItem>> {

	@Override
	public long getSize(List<? extends MediaItem> arg0, Class<?> arg1, Type arg2, Annotation[] arg3, MediaType arg4) {
		return -1;
	}
	
	@Override
	public boolean isWriteable(Class<?> type, Type arg1, Annotation[] arg2, MediaType arg3) {
		return List.class.isAssignableFrom(type);
	}

	@Override
	public void writeTo(List<? extends MediaItem> listOfItems, Class<?> type, Type type1, Annotation[] antns, MediaType mt,
			MultivaluedMap<String, Object> mm, OutputStream out) throws IOException, WebApplicationException {
		
		List<String> jsonList = listOfItems.stream().map(item -> item.toJson()).collect(Collectors.toList());
		out.write(new Gson().toJson(jsonList).getBytes());
		
	}
	
}