package com.anime.dao;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import com.anime.entity.Chapter;
import com.anime.utils.HibernateUtils;

public class ChapterDao {
	private final Logger logger = Logger.getLogger(ChapterDao.class);

	public void addChapter(Chapter c, Session session) {
		logger.info("Entering add Chapter...");
		try {
			session.save(c);
		} catch (Exception e) {
			logger.error("Error at Add Chapter..: " + e);
			throw e;
		}
	}

	public Chapter getChapterID(int id) {
		Session session = HibernateUtils.currentSession();
		logger.info("Entering get Chapter...");
		Chapter c = null;
		try {
			Criteria criteria = session.createCriteria(Chapter.class);
			c = (Chapter) criteria.add(Restrictions.eq("chapterID", id)).uniqueResult();
		} catch (Exception e) {
			logger.error("Error at Add Chapter..: " + e);
			throw e;
		}
		return c;
	}
}
