package com.tiagoamp.acervorama.model;

import java.nio.file.Path;

import javax.persistence.Entity;

@Entity
public class TextItem extends MediaItem {
	
	
	private String title;
	private String author;
	
	
	@Deprecated
	public TextItem() {		
	}
	
	public TextItem(Path path) {
		super(path);
		this.setType(MediaType.TEXT);
	}
	
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	
}
