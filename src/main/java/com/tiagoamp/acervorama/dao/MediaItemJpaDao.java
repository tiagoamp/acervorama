package com.tiagoamp.acervorama.dao;

import java.nio.file.Path;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import com.tiagoamp.acervorama.model.MediaItem;

public class MediaItemJpaDao implements MediaItemDao {
	
	
	private EntityManagerFactory emf;
	private EntityManager em;
	
		
	public MediaItemJpaDao() {		
	}
	
	
	private void createEntityManager() {
		emf = Persistence.createEntityManagerFactory("PU_ACERVO");
		em = emf.createEntityManager();		
	}
	
	private void closeEntityManager() {
		em.close();
		emf.close();
	}
	
		
	@Override
	public void create(MediaItem item) throws SQLException {
		createEntityManager();
		em.getTransaction().begin();
		em.persist(item);
		em.getTransaction().commit();
		closeEntityManager();
	}

	@Override
	public void update(MediaItem item) throws SQLException {
		createEntityManager();
		em.getTransaction().begin();
		em.merge(item);
		em.getTransaction().commit();
		closeEntityManager();
	}

	@Override
	public void delete(long id) throws SQLException {
		createEntityManager();
		em.getTransaction().begin();
		MediaItem item = em.find(MediaItem.class, id);
		em.remove(item);
		em.getTransaction().commit();
		closeEntityManager();
	}

	@Override
	public MediaItem findById(long id) throws SQLException {
		createEntityManager();
		MediaItem item = em.find(MediaItem.class, id);
		closeEntityManager();
		return item;
	}

	@Override
	public MediaItem findByPath(Path path) throws SQLException {
		createEntityManager();
		Query query = em.createQuery("SELECT m from MediaItem m WHERE m.filePath = :pPath");
		query.setParameter("pPath", path);
		MediaItem item;
		try {
			item = (MediaItem) query.getSingleResult();
		} catch (NoResultException nre) {
			item = null;
		}		
		closeEntityManager();
		return item;
	}
	
	@Override
	public MediaItem findByHash(String hash) throws SQLException {
		createEntityManager();
		Query query = em.createQuery("SELECT m from MediaItem m WHERE m.hash = :pHash");
		query.setParameter("pHash", hash);
		MediaItem item;
		try {
			item = (MediaItem) query.getSingleResult();
		} catch (NoResultException nre) {
			item = null;
		}
		closeEntityManager();
		return item;
	}

	@Override
	public List<MediaItem> findByFileNameLike(String filename) throws SQLException {
		createEntityManager();
		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
		CriteriaQuery<MediaItem> query = criteriaBuilder.createQuery(MediaItem.class);
		
		Root<MediaItem> root = query.from(MediaItem.class);
		javax.persistence.criteria.Path<String> filenamePath = root.<String>get("filename");		
		Predicate filenameLike = criteriaBuilder.like(filenamePath, "%" + filename + "%");		
		query.where(filenameLike);
		
		TypedQuery<MediaItem> typedQuery = em.createQuery(query);				
		List<MediaItem> list = typedQuery.getResultList();
		closeEntityManager();
		return list;
	}

	@Override
	public List<MediaItem> findByFields(String filename, String classification, String description) throws SQLException {
		createEntityManager();
		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
		CriteriaQuery<MediaItem> query = criteriaBuilder.createQuery(MediaItem.class);
		
		Root<MediaItem> root = query.from(MediaItem.class);
		javax.persistence.criteria.Path<String> filenamePath = root.<String>get("filename");
		javax.persistence.criteria.Path<String> classificationPath = root.<String>get("classification");
		javax.persistence.criteria.Path<String> descriptionPath = root.<String>get("description");
		
		List<Predicate> predicates = new ArrayList<>();		
		if (filename != null && !filename.isEmpty()) {
			Predicate filenameLike = criteriaBuilder.like(filenamePath, "%" + filename + "%");
			predicates.add(filenameLike);
		}
		if (classification != null && !classification.isEmpty()) {
			Predicate classificationEqual = criteriaBuilder.equal(classificationPath, classification);
			predicates.add(classificationEqual);
		}
		if (description != null && !description.isEmpty()) {
			Predicate descriptionLike = criteriaBuilder.like(descriptionPath, "%" + description + "%");
			predicates.add(descriptionLike);
		}
		query.where((Predicate[]) predicates.toArray(new Predicate[0]));
		
		TypedQuery<MediaItem> typedQuery = em.createQuery(query);				
		List<MediaItem> list = typedQuery.getResultList();
		closeEntityManager();
		return list;
	}

	@Override
	public List<MediaItem> findAll() throws SQLException {
		createEntityManager();
		List<MediaItem> list = em.createQuery("from MediaItem", MediaItem.class).getResultList();
		closeEntityManager();
		return list;
	}

}
