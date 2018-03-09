package com.tiagoamp.acervorama.model;

import java.io.UnsupportedEncodingException;
import java.nio.file.Path;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeInfo.As;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.tiagoamp.acervorama.dao.LocalDateTimeConverter;
import com.tiagoamp.acervorama.dao.PathConverter;

/**
 * Represents a generic media item/file.
 * 
 * @author tiagoamp
 */
@Entity
@Table(name="MEDIA_ITEMS")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE )
@DiscriminatorColumn(discriminatorType = DiscriminatorType.STRING)
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@JsonTypeInfo(use = com.fasterxml.jackson.annotation.JsonTypeInfo.Id.NAME, include = As.PROPERTY, property = "type")
@JsonSubTypes({
    @JsonSubTypes.Type(value = AudioItem.class, name = "AUDIO"),
    @JsonSubTypes.Type(value = ImageItem.class, name = "IMAGE"),
    @JsonSubTypes.Type(value = TextItem.class, name = "TEXT"),
    @JsonSubTypes.Type(value = VideoItem.class, name = "VIDEO")
})
public abstract class MediaItem {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="ID")
	private long id;
	
	@Convert(converter = PathConverter.class)
	@Column(name="PATH")	
	private Path filePath;
	
	@Column(name="FILENAME")
	private String filename;
	
	@Convert(converter = LocalDateTimeConverter.class)
    @Column(name="REGISTRATION_DATE")
	private LocalDateTime registerDate;	
	
	@Column(name="TYPE")
	@Enumerated(EnumType.STRING)
	private MediaTypeAcervo type;
	
	@Column(name="HASH")
	private String hash;
	
	@Column(name="TAGS")
	private String tags;
	
	@Column(name="CLASSIFICATION")
	private String classification;
	
	@Column(name="DESCRIPTION")
	private String description;
		
	@Column(name="ADDITIONAL_INFO")
	private String additionalInformation;
			
	
	@Deprecated
	public MediaItem() {		
	}
	
	public MediaItem(Path path) {
		this.filePath = path;
		this.filename = path.getFileName().toString();
		this.hash = generateHash();
		this.registerDate = LocalDateTime.now();		
	}
	
		
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof MediaItem)) return false;
		return this.hash.equals( ((MediaItem)obj).hash );
	}
	
	
	public String toJson() {
		Gson gson = new GsonBuilder().registerTypeHierarchyAdapter(Path.class, new MyPathConverter()).create();
		return gson.toJson(this);
	}
		
	public void fillHash() {
		this.hash = generateHash();
	}
	
	public Boolean containsTag(String tag) {
		String[] tagsArr = tags.split(" , ");
		for (int i = 0; i < tagsArr.length; i++) {
			if( tag.equalsIgnoreCase(tagsArr[i].trim()) ) return true;
		}
		return false;
	}
	
	public void addTag(String tag) {
		this.tags += " , " + tag;
	}
	
	/**
	 * Generates MD-5 hash with file path info. 
	 * @return String hash
	 */
	private String generateHash() {
		String hexHash = null;
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(filePath.toString().getBytes("UTF-8"));			
			byte[] byteData = md.digest();
	        //convert the byte to hex format
	        StringBuffer sb = new StringBuffer();
	        for (int i = 0; i < byteData.length; i++) {
	        	sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
	        }
			hexHash = sb.toString();
		} catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return hexHash;
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
	public String getAdditionalInformation() {
		return additionalInformation;
	}
	public void setAdditionalInformation(String additionalInformation) {
		this.additionalInformation = additionalInformation;
	}
	public MediaTypeAcervo getType() {
		return type;
	}
	public void setType(MediaTypeAcervo type) {
		this.type = type;
	}
	public String getHash() {
		return hash;
	}
	public void setHash(String hash) {
		this.hash = hash;
	}
	public String getTags() {
		return tags;
	}
	public void setTags(String tags) {
		this.tags = tags;
	}
	
}
