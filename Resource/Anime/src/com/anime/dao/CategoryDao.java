package com.anime.dao;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import com.anime.entity.Category;
import com.anime.utils.HibernateUtils;

public class CategoryDao {

	private final Logger logger = Logger.getLogger(CategoryDao.class);

	public void addCategory(Category c, Session session) {
		logger.info("Entering add Category...");
		try {
			session.save(c);
		} catch (Exception e) {
			logger.error("Error at Add Category..: " + e);
			throw e;
		}
	}

	public Category getCategoryByID(int id) {
		Session session = HibernateUtils.currentSession();
		logger.info("Entering get Category...");
		Category c = null;
		try {
			Criteria criteria = session.createCriteria(Category.class);
			c = (Category) criteria.add(Restrictions.eq("categoryID", id)).uniqueResult();
		} catch (Exception e) {
			logger.error("Error at Add Category..: " + e);
			throw e;
		}
		return c;
	}

	public Category getCategoryByName(String name) {
		Session session = HibernateUtils.currentSession();
		logger.info("Entering get Category...");
		Category c = null;
		try {
			Criteria criteria = session.createCriteria(Category.class);
			c = (Category) criteria.add(Restrictions.eq("categoryName", name)).uniqueResult();
		} catch (Exception e) {
			logger.error("Error at Add Category..: " + e);
			throw e;
		}
		return c;
	}
}
