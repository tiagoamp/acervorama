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
			arr = null;
			break;
		}			
		return arr;
	}
	
	public Class<? extends MediaItem> getItemSubclass() {		
		Class<? extends MediaItem> clazz;
		
		switch (this) {
		case AUDIO:
			clazz = AudioItem.class;
			break;
		case IMAGE:
			clazz = ImageItem.class;
			break;
		case TEXT:
			clazz = TextItem.class;
			break;
		case VIDEO:
			clazz = VideoItem.class;
			break;
		default:
			clazz = null;
			break;
		}
		return clazz;		
	}	
}
