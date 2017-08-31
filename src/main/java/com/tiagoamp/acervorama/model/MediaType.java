package com.tiagoamp.acervorama.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public enum MediaType {
	
	
	AUDIO, IMAGE, TEXT, VIDEO;
	
	
	public String[] getFileExtensions() {
		List<String> list = new ArrayList<>();		
		switch (this) {
		case AUDIO:
			list.addAll(Arrays.asList("mp3","wav"));
			break;
		case IMAGE:
			list.addAll(Arrays.asList("png","jpg","gif","bmp"));
			break;
		case TEXT:
			list.addAll(Arrays.asList("doc","docx","odt","pdf","rtf","txt"));
			break;
		case VIDEO:
			list.addAll(Arrays.asList("3gp","avi","flv","m4v","mp4","mpeg","mpg","wmv"));
			break;

		default:
			break;
		}
		
		return (String[]) list.toArray();
	}
	

}
