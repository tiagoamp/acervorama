package com.tiagoamp.acervorama.model;

import java.nio.file.Path;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
public class VideoItem extends MediaItem {
	
	@Column(name="TITLE")
	private String title;
	
	
	@Deprecated
	public VideoItem() {		
	}
		
	public VideoItem(Path path) {
		super(path);
		this.setType(MediaTypeAcervo.VIDEO);
	}
	
	
	public void transformAttributesInCapitalLetters() {
		super.transformAttributesInCapitalLetters();
		if (title != null) this.setTitle(title.toUpperCase());		
	}
	
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	
}
