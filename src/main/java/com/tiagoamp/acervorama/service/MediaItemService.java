package com.tiagoamp.acervorama.service;

import java.nio.file.Path;
import java.util.List;

import com.tiagoamp.acervorama.dao.MediaItemDao;
import com.tiagoamp.acervorama.dao.MediaItemJpaDao;
import com.tiagoamp.acervorama.model.AcervoramaBusinessException;
import com.tiagoamp.acervorama.model.MediaItem;

public class MediaItemService {
	
	private MediaItemDao dao;
	
	
	public MediaItemService() {
		this.dao = new MediaItemJpaDao();
	}
	
	
	/**
	 * Inserts a new Media Item.
	 * 
	 * @param item
	 * @throws AcervoramaBusinessException 
	 * @
	 */
	public void insert(MediaItem item) throws AcervoramaBusinessException  {		 
		MediaItem dbItem = dao.findByPath(item.getFilePath());
		if (dbItem != null) throw new AcervoramaBusinessException("File path already exists!");
		dao.create(item);		 
	}
	
	/**
	 * Updates a Media Item.
	 * 
	 * @param item
	 * @ 
	 */
	public void update(MediaItem item)  {		
		dao.update(item);		 
	}
	
	/**
	 * Deletes a Media Item.
	 * 
	 * @param id 
	 * @
	 */
	public void delete(long id)  {		
		dao.delete(id);
	}
	
	/**
	 * Search Media Item by 'id'.
	 * 
	 * @param id Entity id.
	 * @return MediaItem 
	 * @ 
	 */
	public MediaItem findById(long id)  {		
		return dao.findById(id);		
	}
	
	/**
	 * Search Media Item by 'path'.
	 * 
	 * @param path
	 * @return MediaItem
	 * @
	 */
	public MediaItem findByPath(Path path)  {		
		return dao.findByPath(path);		 
	}
	
	/**
	 * Search Media Item by 'hash'.
	 * 
	 * @param hash
	 * @return MediaItem
	 * @
	 */
	public MediaItem findByHash(String hash)  {		
		return dao.findByHash(hash);		 
	}
	
	/**
	 * Search Media Item by 'file name-like'.
	 * 
	 * @param filename
	 * @return List<MediaItem> 
	 * @
	 */
	public List<MediaItem> findByFileNameLike(String filename)  {		
		return dao.findByFileNameLike(filename);		 
	}
	
	/**
	 * Search Media Item by given parameters.
	 * 
	 * @param filename
	 * @param classification
	 * @param description
	 * @return List<MediaItem>
	 * @
	 */
	public List<MediaItem> findByFields(String filename, String classification, String description) {		
		return dao.findByFields(filename, classification, description);		
	}
	
	/**
	 * Retrieve all Media Items.
	 * 
	 * @return List<MediaItem>
	 * @
	 */
	public List<MediaItem> getAll() {		
		return dao.findAll();		
	}
	
}
