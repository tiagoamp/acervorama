package com.tiagoamp.acervorama.service;

import java.nio.file.Path;
import java.sql.SQLException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import com.tiagoamp.acervorama.dao.MediaItemDao;
import com.tiagoamp.acervorama.dao.MediaItemJpaDao;
import com.tiagoamp.acervorama.model.AcervoramaBusinessException;
import com.tiagoamp.acervorama.model.MediaItem;

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
	public void insert(MediaItem item) throws AcervoramaBusinessException {
		try {
			MediaItem dbItem = dao.findByPath(item.getFilePath());
			if (dbItem != null) throw new AcervoramaBusinessException("File path already exists!");
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
	public void update(MediaItem item) throws AcervoramaBusinessException {
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
	public void delete(long id) throws AcervoramaBusinessException {
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
	public MediaItem findById(long id) throws AcervoramaBusinessException {
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
	public MediaItem findByPath(Path path) throws AcervoramaBusinessException {
		try {
			return dao.findByPath(path);
		} catch (SQLException e) {
			throw new AcervoramaBusinessException("Database error!" , e);			
		}
	}
	
	/**
	 * Search Media Item by 'hash'.
	 * 
	 * @param hash
	 * @return MediaItem
	 * @throws AcervoramaBusinessException
	 */
	public MediaItem findByHash(String hash) throws AcervoramaBusinessException {
		try {
			return dao.findByHash(hash);
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
	public List<MediaItem> findByFileNameLike(String filename) throws AcervoramaBusinessException {
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
	 * @param description
	 * @return List<MediaItem>
	 * @throws AcervoramaBusinessException
	 */
	public List<MediaItem> findByFields(String filename, String classification, String description) throws AcervoramaBusinessException {
		try {
			return dao.findByFields(filename, classification, description);
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
	public List<MediaItem> getAll() throws AcervoramaBusinessException {
		try {
			return dao.findAll();
		} catch (SQLException e) {
			throw new AcervoramaBusinessException("Database error!" , e);			
		}
	}
		
}
