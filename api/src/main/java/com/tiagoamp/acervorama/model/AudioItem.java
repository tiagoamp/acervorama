package com.tiagoamp.acervorama.model;

import java.nio.file.Path;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
public class AudioItem extends MediaItem {
	
	@Column(name="TITLE")
	private String title;
	
	@Column(name="AUTHOR")
	private String author;
	
	
	@Deprecated
	public AudioItem() {		
	}
	
	public AudioItem(Path path) {
		super(path);
		this.setType(MediaTypeAcervo.AUDIO);
	}
	
	
	public void transformAttributesInCapitalLetters() {
		super.transformAttributesInCapitalLetters();
		if (title != null) this.setTitle(title.toUpperCase());
		if (author != null) this.setAuthor(author.toUpperCase());
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
