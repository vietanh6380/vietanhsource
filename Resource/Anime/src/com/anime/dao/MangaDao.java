package com.anime.dao;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import com.anime.entity.Manga;
import com.anime.utils.HibernateUtils;

public class MangaDao {
	private final Logger logger = Logger.getLogger(MangaDao.class);

	public void addManga(Manga m, Session session) {
		logger.info("Entering add Manga...");
		try {
			session.save(m);
		} catch (Exception e) {
			logger.error("Error at Add Manga..: " + e);
			throw e;
		}
	}

	public Manga getMangaById(int mangaId) {
		logger.info("Entering add Manga...");
		Session session = HibernateUtils.currentSession();
		Manga manga = null;
		try {
			Criteria criteria = session.createCriteria(Manga.class).add(Restrictions.eq("mangaID", mangaId));
			manga = (Manga) criteria.uniqueResult();
		} catch (Exception e) {
			logger.error("Error at Get Manga..: " + e);
			throw e;
		}
		return manga;
	}

	public Manga getMangaByName(String mangaName) {
		logger.info("Entering add Manga...");
		Session session = HibernateUtils.currentSession();
		Manga manga = null;
		try {
			Criteria criteria = session.createCriteria(Manga.class).add(Restrictions.eq("mangaName", mangaName));
			manga = (Manga) criteria.uniqueResult();
		} catch (Exception e) {
			logger.error("Error at Get Manga..: " + e);
			throw e;
		}
		return manga;
	}
}
