package com.tiagoamp.acervorama.model;

import java.nio.file.Path;

import javax.persistence.Entity;

@Entity
public class ImageItem extends MediaItem {
	
	@Deprecated
	public ImageItem() {		
	}
	
	public ImageItem(Path path) {
		super(path);
		this.setType(MediaTypeAcervo.IMAGE);
	}
	
}
