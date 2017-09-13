package com.tiagoamp.acervorama.resources;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.stream.Collectors;

import javax.ws.rs.Consumes;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.MessageBodyReader;
import javax.ws.rs.ext.Provider;

import com.tiagoamp.acervorama.model.MediaItem;
import com.tiagoamp.acervorama.model.MediaItemFactory;

@Provider
@Consumes(MediaType.APPLICATION_JSON)
public class MediaItemMessageBodyReader implements MessageBodyReader<MediaItem>{

	@Override
	public boolean isReadable(Class<?> type, Type arg1, Annotation[] arg2, MediaType arg3) {
		return MediaItem.class.isAssignableFrom(type);
	}

	@Override
	public MediaItem readFrom(Class<MediaItem> mediaitem, Type type, Annotation[] ants, MediaType arg3,
			MultivaluedMap<String, String> mm, InputStream inputStream) throws IOException, WebApplicationException {
		
		//InputStreamReader reader = new InputStreamReader( inputStream, "UTF-8" );
		String json = new BufferedReader(new InputStreamReader(inputStream)).lines().collect(Collectors.joining("\n"));		
		
		return MediaItemFactory.fromJson(json);
	}

}
