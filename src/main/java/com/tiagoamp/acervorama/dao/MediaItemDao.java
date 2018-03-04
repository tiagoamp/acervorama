package com.tiagoamp.acervorama.dao;

import java.nio.file.Path;
import java.util.List;

import com.tiagoamp.acervorama.model.MediaItem;

public interface MediaItemDao {

	/**
	 * Inserts the entity in the database.
	 * 
	 * @param item
	 * @throws SQLException 
	 */
	void create(MediaItem item);
	
	/**
	 * Updates the entity in the database.
	 * 
	 * @param item
	 * @throws SQLException 
	 */
	void update(MediaItem item);
	
	/**
	 * Deletes the entity in the database.
	 * 
	 * @param id 
	 * @throws SQLException
	 */
	void delete(long id);
	
	/**
	 * Retrieve all entities from the database.
	 * 
	 * @return List<MediaItem> List of entity
	 * @throws SQLException
	 */
	List<MediaItem> findAll();
	
	/**
	 * Search the entity by 'id' in the database.
	 * 
	 * @param id Entity id.
	 * @return Person 
	 * @throws SQLException 
	 */
	MediaItem findById(long id);
	
	/**
	 * Search the entity by 'path' in the database.
	 * 
	 * @param path
	 * @return MediaItem
	 * @throws SQLException
	 */
	MediaItem findByPath(Path path);
	
	/**
	 * Search the entity by 'hash' in the database.
	 * 
	 * @param hash
	 * @return MediaItem
	 * @throws SQLException
	 */
	MediaItem findByHash(String hash);
	
	/**
	 * Search the entity by 'file name-like' in the database.
	 * 
	 * @param filename
	 * @return List<MediaItem> List of entity
	 * @throws SQLException
	 */
	List<MediaItem> findByFileNameLike(String filename);
	
	/**
	 * Search the entity by given parameters in the database.
	 * 
	 * @param filename file name like search
	 * @param classification 
	 * @param description description like search
	 * @return List<MediaItem> 
	 * @throws SQLException
	 */
	List<MediaItem> findByFields(String filename, String classification, String description);
		
}
