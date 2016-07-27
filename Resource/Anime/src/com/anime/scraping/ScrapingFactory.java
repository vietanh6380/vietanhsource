package com.anime.scraping;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.anime.dao.CategoryDao;
import com.anime.dao.MangaDao;
import com.anime.entity.Category;
import com.anime.entity.Chapter;
import com.anime.entity.Manga;
import com.anime.entity.Page;
import com.anime.utils.AnimeContants;
import com.anime.utils.HibernateUtils;
import com.anime.utils.ScrapUtils;

public class ScrapingFactory {

	private final Logger logger = Logger.getLogger(ScrapingFactory.class);

	private final CategoryDao categoryDao = new CategoryDao();

	private final MangaDao mangaDao = new MangaDao();

	public void InsertAllCategory() throws IOException {
		Session session = HibernateUtils.currentSession();

		session.getTransaction().begin();

		Document doc = Jsoup.connect(AnimeContants.MANGAFOX_SREACH).get();
		for (Element element : doc.select("a.tips")) {
			System.out.println("Insert category : " + element.text() + "into DB");
			Category c = new Category();
			c.setCategoryName(element.text());
			categoryDao.addCategory(c, session);
		}

		session.getTransaction().commit();
		HibernateUtils.closeSession();
	}

	public void InsertMangaAll() throws IOException {
		logger.info("Entering insert all manga...");
		Session session = HibernateUtils.currentSession();

		String html = ScrapUtils.getUrl(AnimeContants.MANGAFOX_ALL_MANGA);
		Document doc = Jsoup.parseBodyFragment(html);

		Elements elmMangas = doc.select(".series_preview");
		logger.info("At : " + new Date() + " have " + elmMangas.size() + "mangas");

		session.getTransaction().begin();

		for (Element element : elmMangas) {
			// String nameManga = ScrapUtil.getUrl(element.attr("href"));
			String nameManga = ScrapUtils.fetchUrl(element.attr("href"));
			if (nameManga == null) {
				System.out.println("Manga is null");
				break;
			}
			String titleManga = element.text();
			Manga manga = new Manga();
			manga.setMangaName(nameManga);
			manga.setMangaTitle(titleManga);
			manga.setIdxMangaFox(Integer.valueOf(element.attr("rel")));
			logger.info(nameManga);
			session.save(manga);
		}

		session.getTransaction().commit();

		HibernateUtils.closeSession();
	}

	public void insertDetailsManga(String mangaName) {
		logger.info("Entering insertDetailsManga...");

		Session session = HibernateUtils.currentSession();
		session.getTransaction().begin();

		try {
			Manga manga = mangaDao.getMangaByName(mangaName);

			if (manga != null) {
				// Update details Manga
				ScrapUtils.fetchDetailsManga(manga);
				session.update(manga);

				// Insert Chapter of Manga
				List<Chapter> listChapter = ScrapUtils.fecthChapterManga(mangaName, manga.getMangaID());
				if (listChapter != null && !listChapter.isEmpty()) {
					for (Chapter chapter : listChapter) {
						session.save(chapter);
						// Insert Page of Chapter
						List<Page> listPage = ScrapUtils.fetchPageManga(chapter.getChapterUrl(),
								chapter.getChapterID());
						if (listChapter != null && !listPage.isEmpty()) {
							for (Page page : listPage) {
								session.save(page);
							}
						}
					}
				}
			}
			session.getTransaction().commit();
			HibernateUtils.closeSession();
		} catch (Exception e) {
			logger.error("Error at method insertDetailManga : " + e);
			session.getTransaction().rollback();
			HibernateUtils.closeSession();
		}
	}

	public void countManga() throws IOException {
		String html = ScrapUtils.getUrl(AnimeContants.MANGAFOX_ALL_MANGA);
		Document doc = Jsoup.parseBodyFragment(html);

		Elements elmMangas = doc.select(".series_preview");
		logger.info("At : " + new Date() + " have " + elmMangas.size() + " mangas");
	}

	public static void main(String[] args) throws IOException {
		MangaDao mangaDao = new MangaDao();
		ScrapingFactory sf = new ScrapingFactory();
		// sf.countManga();
		// sf.InsertMangaAll();
		// sf.countManga();
		 sf.insertDetailsManga("hack_xxxx");
//		for (int i = 1; i <= 1000; i++) {
//			String mangaName = mangaDao.getMangaById(i).getMangaName();
//			sf.insertDetailsManga(mangaName);
//		}
	}
}
