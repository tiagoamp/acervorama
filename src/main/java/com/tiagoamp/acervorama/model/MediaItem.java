package com.tiagoamp.acervorama.model;

import java.nio.file.Path;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.tiagoamp.acervorama.model.dao.LocalDateTimeConverter;
import com.tiagoamp.acervorama.model.dao.PathConverter;

/**
 * 
 * Represents a generic media item/file.
 * 
 * @author tiagoamp
 */
@Entity
@Table(name="MEDIA_ITEM")
public class MediaItem {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="ID")
	private long id;
	
	@Convert(converter = LocalDateTimeConverter.class)
    @Column(name="REGISTRATION_DATE")
	private LocalDateTime registerDate;
	
	@Column(name="FILENAME")
	private String filename;
	
	@Column(name="DESCRIPTION")
	private String description;
	
	@Convert(converter = PathConverter.class)
	@Column(name="PATH")	
	private Path filePath;
	
	@Column(name="CLASSIFICATION")
	private String classification;
	
	@Column(name="SUBJECT")
	private String subject;
	
	@Column(name="ADDITIONAL_INFO")
	private String additionalInformation;
	
	
	@Deprecated
	public MediaItem() {		
	}
	
	public MediaItem(Path path) {
		this.filePath = path;
		this.filename = path.getFileName().toString();
		this.registerDate = LocalDateTime.now();
	}
	
	
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof MediaItem)) return false;
		return this.filename.equals( ((MediaItem)obj).filename );
	}
	
		
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public LocalDateTime getRegisterDate() {
		return registerDate;
	}
	public void setRegisterDate(LocalDateTime registerDate) {
		this.registerDate = registerDate;
	}
	public String getFilename() {
		return filename;
	}
	public void setFilename(String filename) {
		this.filename = filename;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Path getFilePath() {
		return filePath;
	}
	public void setFilePath(Path filePath) {
		this.filePath = filePath;
	}
	public String getClassification() {
		return classification;
	}
	public void setClassification(String classification) {
		this.classification = classification;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getAdditionalInformation() {
		return additionalInformation;
	}
	public void setAdditionalInformation(String additionalInformation) {
		this.additionalInformation = additionalInformation;
	}
	
}
