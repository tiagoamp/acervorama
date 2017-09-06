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
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
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
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class MediaItem {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="ID")
	private long id;
	
	@Column(name="FILENAME")
	private String filename;
	
	@Column(name="DESCRIPTION")
	private String description;
	
	@Column(name="CLASSIFICATION")
	private String classification;
	
	@Column(name="SUBJECT")
	private String subject;
	
	@Column(name="ADDITIONAL_INFO")
	private String additionalInformation;
	
	@Convert(converter = LocalDateTimeConverter.class)
    @Column(name="REGISTRATION_DATE")
	private LocalDateTime registerDate;
	
	@Convert(converter = PathConverter.class)
	@Column(name="PATH")	
	private Path filePath;
	
	
	@Deprecated
	public MediaItem() {		
	}
	
	public MediaItem(Path path) {
		this.filePath = path;
		this.registerDate = LocalDateTime.now();
		this.filename = path.getFileName().toString();
	}
	
	
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof MediaItem)) return false;
		return this.filename.equals( ((MediaItem)obj).filename );
	}
	
	
	public String toJson() {
		Gson gson = new GsonBuilder().registerTypeHierarchyAdapter(Path.class, new MyPathConverter()).create();
		return gson.toJson(this);
	}
		
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
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
	public LocalDateTime getRegisterDate() {
		return registerDate;
	}
	public void setRegisterDate(LocalDateTime registerDate) {
		this.registerDate = registerDate;
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
