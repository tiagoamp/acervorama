package com.tiagoamp.acervorama.model.dao;

import java.nio.file.Path;
import java.sql.SQLException;
import java.util.List;

import com.tiagoamp.acervorama.model.MediaItem;

public interface MediaItemDao {

	/**
	 * Inserts the entity in the database.
	 * 
	 * @param item
	 * @throws SQLException 
	 */
	void create(MediaItem item) throws SQLException;
	
	/**
	 * Updates the entity in the database.
	 * 
	 * @param item
	 * @throws SQLException 
	 */
	void update(MediaItem item) throws SQLException;
	
	/**
	 * Deletes the entity in the database.
	 * 
	 * @param l 
	 * @throws SQLException
	 */
	void delete(long l) throws SQLException;
	
	/**
	 * Search the entity by 'id' in the database.
	 * 
	 * @param id Entity id.
	 * @return Person 
	 * @throws SQLException 
	 */
	MediaItem findById(int id) throws SQLException;
	
	/**
	 * Search the entity by 'path' in the database.
	 * 
	 * @param path
	 * @return MediaItem
	 * @throws SQLException
	 */
	MediaItem findByPath(Path path) throws SQLException;
	
	/**
	 * Search the entity by 'file name-like' in the database.
	 * 
	 * @param filename
	 * @return List<MediaItem> List of entity
	 * @throws SQLException
	 */
	List<MediaItem> findByFileNameLike(String filename) throws SQLException;
	
	/**
	 * Search the entity by given parameters in the database.
	 * 
	 * @param filename
	 * @param path
	 * @param classification
	 * @param subject
	 * @param description
	 * @return List<MediaItem>
	 * @throws SQLException
	 */
	List<MediaItem> findByFields(String filename, Path path, String classification, String subject, String description) throws SQLException;
	
	/**
	 * Retrieve all entities from the database.
	 * 
	 * @return List<MediaItem> List of entity
	 * @throws SQLException
	 */
	List<MediaItem> findAll() throws SQLException;
	
}