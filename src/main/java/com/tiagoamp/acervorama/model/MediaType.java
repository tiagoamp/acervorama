package com.tiagoamp.acervorama.model;

public enum MediaType {
	
	
	AUDIO, IMAGE, TEXT, VIDEO;
	
	
	public String[] getFileExtensions() {
		String[] arr;
		switch (this) {
		case AUDIO:
			arr = new String[]{"mp3","wav"};
			break;
		case IMAGE:
			arr = new String[]{"png","jpg","gif","bmp"};
			break;
		case TEXT:
			arr = new String[]{"doc","docx","odt","pdf","rtf","txt","epub","mobi"};
			break;
		case VIDEO:
			arr = new String[]{"3gp","avi","flv","m4v","mp4","mpeg","mpg","wmv"};
			break;

		default:
			arr = new String[0];
			break;
		}		
					
		return arr;
	}
	

}
