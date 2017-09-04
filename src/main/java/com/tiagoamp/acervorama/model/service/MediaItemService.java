package com.tiagoamp.acervorama.model.service;

import java.nio.file.Path;
import java.sql.SQLException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import com.tiagoamp.acervorama.model.AcervoramaBusinessException;
import com.tiagoamp.acervorama.model.MediaItem;
import com.tiagoamp.acervorama.model.dao.MediaItemDao;
import com.tiagoamp.acervorama.model.dao.MediaItemJpaDao;

public class MediaItemService {
	
	
	public MediaItemService() {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("PU_ACERVO");
		EntityManager em = emf.createEntityManager();
		this.dao = new MediaItemJpaDao(em);		
	}
	
	
	private MediaItemDao dao;
	
	
	/**
	 * Inserts a new Media Item.
	 * 
	 * @param item
	 * @throws AcervoramaBusinessException
	 */
	void insert(MediaItem item) throws AcervoramaBusinessException {
		try {
			dao.create(item);
		} catch (SQLException e) {
			throw new AcervoramaBusinessException("Database error!" , e);			
		}
	}
	
	/**
	 * Updates a Media Item.
	 * 
	 * @param item
	 * @throws AcervoramaBusinessException 
	 */
	void update(MediaItem item) throws AcervoramaBusinessException {
		try {
			dao.update(item);
		} catch (SQLException e) {
			throw new AcervoramaBusinessException("Database error!" , e);			
		}
	}
	
	/**
	 * Deletes a Media Item.
	 * 
	 * @param id 
	 * @throws AcervoramaBusinessException
	 */
	void delete(long id) throws AcervoramaBusinessException {
		try {
			dao.delete(id);
		} catch (SQLException e) {
			throw new AcervoramaBusinessException("Database error!" , e);			
		}
	}
	
	/**
	 * Search Media Item by 'id'.
	 * 
	 * @param id Entity id.
	 * @return MediaItem 
	 * @throws AcervoramaBusinessException 
	 */
	MediaItem findById(long id) throws AcervoramaBusinessException {
		try {
			return dao.findById(id);
		} catch (SQLException e) {
			throw new AcervoramaBusinessException("Database error!" , e);			
		}
	}
	
	/**
	 * Search Media Item by 'path'.
	 * 
	 * @param path
	 * @return MediaItem
	 * @throws AcervoramaBusinessException
	 */
	MediaItem findByPath(Path path) throws AcervoramaBusinessException {
		try {
			return dao.findByPath(path);
		} catch (SQLException e) {
			throw new AcervoramaBusinessException("Database error!" , e);			
		}
	}
	
	/**
	 * Search Media Item by 'file name-like'.
	 * 
	 * @param filename
	 * @return List<MediaItem> 
	 * @throws AcervoramaBusinessException
	 */
	List<MediaItem> findByFileNameLike(String filename) throws AcervoramaBusinessException {
		try {
			return dao.findByFileNameLike(filename);
		} catch (SQLException e) {
			throw new AcervoramaBusinessException("Database error!" , e);			
		}
	}
	
	/**
	 * Search Media Item by given parameters.
	 * 
	 * @param filename
	 * @param classification
	 * @param subject
	 * @param description
	 * @return List<MediaItem>
	 * @throws AcervoramaBusinessException
	 */
	List<MediaItem> findByFields(String filename, String classification, String subject, String description) throws AcervoramaBusinessException {
		try {
			return dao.findByFields(filename, classification, subject, description);
		} catch (SQLException e) {
			throw new AcervoramaBusinessException("Database error!" , e);			
		}
	}
	
	/**
	 * Retrieve all Media Items.
	 * 
	 * @return List<MediaItem>
	 * @throws AcervoramaBusinessException
	 */
	List<MediaItem> getAll() throws AcervoramaBusinessException {
		try {
			return dao.findAll();
		} catch (SQLException e) {
			throw new AcervoramaBusinessException("Database error!" , e);			
		}
	}

}
