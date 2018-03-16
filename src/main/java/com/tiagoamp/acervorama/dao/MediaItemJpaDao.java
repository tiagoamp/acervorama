package com.tiagoamp.acervorama.dao;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import com.tiagoamp.acervorama.model.MediaItem;
import com.tiagoamp.acervorama.model.MediaTypeAcervo;

@Repository(value="jpa")
@Transactional
public class MediaItemJpaDao implements MediaItemDao {
	
	@PersistenceContext
	private EntityManager em;
	
		
	public MediaItemJpaDao() {		
	}
	
			
	@Override
	public void create(MediaItem item) {
		em.persist(item);
	}

	@Override
	public void update(MediaItem item) {
		em.merge(item);		
	}

	@Override
	public void delete(long id) {
		MediaItem item = em.find(MediaItem.class, id);
		em.remove(item);				    
	}

	@Override
	public List<MediaItem> findAll() {
		return em.createQuery("from MediaItem", MediaItem.class).getResultList();
	}
	
	@Override
	public MediaItem findById(long id) {
		return em.find(MediaItem.class, id);
	}

	@Override
	public MediaItem findByPath(Path path) {
		Query query = em.createQuery("SELECT m from MediaItem m WHERE m.filePath = :pPath");
		query.setParameter("pPath", path);
		MediaItem item;
		try {
			item = (MediaItem) query.getSingleResult();
		} catch (NoResultException nre) {
			item = null;
		}		
		return item;
	}
	
	@Override
	public MediaItem findByHash(String hash) {
		Query query = em.createQuery("SELECT m from MediaItem m WHERE m.hash = :pHash");
		query.setParameter("pHash", hash);
		MediaItem item;
		try {
			item = (MediaItem) query.getSingleResult();
		} catch (NoResultException nre) {
			item = null;
		}
		return item;
	}

	@Override
	public List<MediaItem> findByFileNameLike(String filename) {
		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
		CriteriaQuery<MediaItem> query = criteriaBuilder.createQuery(MediaItem.class);
		
		Root<MediaItem> root = query.from(MediaItem.class);
		javax.persistence.criteria.Path<String> filenamePath = root.<String>get("filename");		
		Predicate filenameLike = criteriaBuilder.like(filenamePath, "%" + filename + "%");		
		query.where(filenameLike);
		
		TypedQuery<MediaItem> typedQuery = em.createQuery(query);				
		return typedQuery.getResultList();
	}

	@Override
	public List<MediaItem> findByFields(String filename, String classification, MediaTypeAcervo mediatype) {
		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
		CriteriaQuery<MediaItem> query = criteriaBuilder.createQuery(MediaItem.class);
		
		Root<MediaItem> root = query.from(MediaItem.class);
		javax.persistence.criteria.Path<String> filenamePath = root.<String>get("filename");
		javax.persistence.criteria.Path<String> classificationPath = root.<String>get("classification");
		javax.persistence.criteria.Path<MediaTypeAcervo> mediatypePath = root.<MediaTypeAcervo>get("type");
		
		List<Predicate> predicates = new ArrayList<>();		
		if (filename != null && !filename.isEmpty()) {
			Predicate filenameLike = criteriaBuilder.like(filenamePath, "%" + filename + "%");
			predicates.add(filenameLike);
		}
		if (classification != null && !classification.isEmpty()) {
			Predicate classificationEqual = criteriaBuilder.equal(classificationPath, classification);
			predicates.add(classificationEqual);
		}
		if (mediatype != null) {
			Predicate mediatypeEqual = criteriaBuilder.equal(mediatypePath, mediatype);
			predicates.add(mediatypeEqual);
		}
		query.where((Predicate[]) predicates.toArray(new Predicate[0]));
		
		TypedQuery<MediaItem> typedQuery = em.createQuery(query);				
		return typedQuery.getResultList();
	}

}
