package com.anime.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.anime.entity.Chapter;
import com.anime.entity.Manga;
import com.anime.entity.Page;

public class ScrapUtils {

	private final static Logger logger = Logger.getLogger(ScrapUtils.class);

	/**
	 * Get html Content.
	 * 
	 * @param url
	 * @return
	 */
	public static String getUrl(String url) {
		URL urlObj = null;
		try {
			urlObj = new URL(url);
		} catch (MalformedURLException e) {
			System.out.println("The url was malformed!");
			return "";
		}
		URLConnection urlCon = null;
		BufferedReader in = null;
		String outputText = "";
		try {
			urlCon = urlObj.openConnection();
			in = new BufferedReader(new InputStreamReader(urlCon.getInputStream()));
			String line = "";
			while ((line = in.readLine()) != null) {
				outputText += line;
			}
			in.close();
		} catch (IOException e) {
			System.out.println("There was an error connecting to the URL");
			return "";
		}
		return outputText;
	}

	/**
	 * Get Current Time.
	 * 
	 * @return
	 */
	public static Timestamp getCurrentTimeStamp() {
		Date today = new Date();
		return new Timestamp(today.getTime());
	}

	/**
	 * Fetch Url.
	 * 
	 * @param url
	 * @return
	 */
	public static String fetchUrl(String url) {
		if (url.indexOf("m.mangafox.me") > -1) {
			return url.substring(27, url.length() - 1);
		} else if (url.indexOf("mangafox.me") > -1) {
			return url.substring(25, url.length() - 1);
		} else {
			return null;
		}
	}

	/**
	 * Parse Time String to Date Realse.
	 * 
	 * @param time
	 * @return
	 */
	public static Timestamp parseTimeRelease(String time) {
		Date date = null;
		try {
			if (time.length() == 4) {
				DateFormat dateFormat = new SimpleDateFormat("yyyy");
				date = dateFormat.parse(time);
			} else {
				DateFormat dateFormat = new SimpleDateFormat("MM dd, yyyy");
				date = dateFormat.parse(time);
			}
		} catch (Exception e) {
			e.getMessage();
		}
		return new Timestamp(date.getTime());
	}

	/**
	 * Fetch Details of Manga.
	 * 
	 * @param m
	 * @return
	 */
	public static Manga fetchDetailsManga(Manga m) {
		logger.info("Fetching Details of Manga :" + m.getMangaName());
		try {
			Document document = Jsoup.connect("http://mangafox.me/manga/" + m.getMangaName()).get();

			Elements elmTitle = document.select("#title td");
			if (elmTitle.size() == 4) {
				if (elmTitle.get(0).text() != null) {
					m.setDateRelease(parseTimeRelease(elmTitle.get(0).text().trim()));
				}
				m.setAuthor(elmTitle.get(1).text());
				m.setArtist(elmTitle.get(2).text());
				m.setGenres(elmTitle.get(3).text());
			}
			Elements elmInfo = document.select("#series_info");
			// Cover
			m.setMangaCover(elmInfo.select("img[src]").attr("src"));

			if (elmInfo.select(".data span").size() == 4) {
				// Status
				if (elmInfo.select(".data span").get(0).text().trim().toLowerCase().contains("completed")) {
					m.setStatus(1);
				} else {
					m.setStatus(0);
				}
				// Ranking
				if (elmInfo.select(".data span").get(2).text().trim().contains("views")) {
					String rank = elmInfo.select(".data span").get(2).text().trim().split(",")[0];
					m.setRankMangaFox(Integer.valueOf(rank.substring(0, rank.length() - 2)));
				}

				// Rating
				String rating = elmInfo.select(".data span").get(3).text().trim();
				m.setRating(Double.valueOf(rating.substring(8, 12)));
			}
			if (elmInfo.select(".data span").size() == 3) {
				// Status
				if (elmInfo.select(".data span").get(0).text().trim().toLowerCase().contains("completed")) {
					m.setStatus(1);
				} else {
					m.setStatus(0);
				}
				// Ranking
				if (elmInfo.select(".data span").get(1).text().trim().contains("views")) {
					String rank = elmInfo.select(".data span").get(1).text().trim().split(",")[0];
					m.setRankMangaFox(Integer.valueOf(rank.substring(0, rank.length() - 2)));
				}

				// Rating
				String rating = elmInfo.select(".data span").get(2).text().trim();
				m.setRating(Double.valueOf(rating.substring(8, 12)));
			}

			// Description
			if (document.select(".summary").size() > 0) {
				Element elmDesc = document.select(".summary").first();
				m.setDescription(elmDesc.text().trim());
			}
		} catch (Exception e) {
			logger.error("Error into process Fetch Details Manga : " + m.getMangaName());
		}
		logger.info("DONE ... fetch details of Manga : " + m.getMangaName());
		return m;
	}

	/**
	 * Fetch Chapter of Manga.
	 * 
	 * @param mangaUrl
	 * @param mangaID
	 * @return
	 */
	public static List<Chapter> fecthChapterManga(String mangaName, int mangaID) {
		logger.info("Fetching Chapters of Manga :" + mangaName);
		List<Chapter> listChapter = new ArrayList<Chapter>();

		try {
			Document doc = Jsoup.connect(AnimeContants.MANGA_URL + mangaName).get();
			Elements elmChapters = doc.select(".chlist li");

			logger.info("All Chapter of Manga : " + elmChapters.size() + " chapters.");
			if (elmChapters.size() > 0) {
				for (Element element : elmChapters) {
					Chapter c = new Chapter();
					// Manga Id
					c.setMangaID(mangaID);
					// Date Chapter
					if (element.select(".date").size() > 0) {
						String dateChapter = element.select(".date").first().text();
						Date date = DateTimeUtils.convertDateTime(dateChapter);
						c.setDatePublish(new Timestamp(date.getTime()));
					}
					// Url Chapters
					if (element.select(".tips").size() > 0) {
						String urlChapter = element.select(".tips").first().attr("href");
						c.setChapterUrl(urlChapter);
					}

					// Name Chapters

					if (element.select(".tips").size() > 0) {
						String nameChapter = element.select(".tips").first().text();
						c.setChapterName(nameChapter);
						// Number Chapters
						if (nameChapter.split(" ").length > 1) {
							c.setChapterNumber(
									Float.valueOf(nameChapter.split(" ")[nameChapter.split(" ").length - 1]));
						}
					}

					// Title Chapters

					if (element.select(".title").size() > 0) {
						String titleChapter = element.select(".title").first().text();
						c.setChapterTitle(titleChapter);
					}
					listChapter.add(c);
				}
			}
		} catch (Exception e) {
			logger.error("Error into process Fetch Chapter Manga : " + mangaName);
		}
		logger.info("DONE .. get Chapters of Manga .");
		return listChapter;
	}

	/**
	 * Fetch Page of Manga.
	 * 
	 * @param chapterUrl
	 * @param chapterID
	 * @return
	 */
	public static List<Page> fetchPageManga(String chapterUrl, int chapterID) {
		logger.info("Fetching  Pages of Chapter ID :" + chapterUrl);
		List<Page> listPage = null;
		try {
			// String html = getUrl(chapterUrl);
			// Document doc = Jsoup.parseBodyFragment(html);
			Document doc = Jsoup.connect(chapterUrl).get();

			if (doc.select("#top_bar .l").size() > 0) {
				String allPage = doc.select("#top_bar .l").first().text().trim();
				allPage = allPage.split(" ")[allPage.split(" ").length - 1];
				logger.info("All Page of Chapter : " + allPage + " pages.");
				String pathChapter = chapterUrl.substring(0, chapterUrl.lastIndexOf("/"));
				if (Integer.valueOf(allPage) > 0) {
					listPage = new ArrayList<Page>();
					for (int i = 1; i <= Integer.valueOf(allPage); i++) {
						Page p = new Page();
						String pageUrl = pathChapter + "/" + i + ".html";
						p.setPageImage(getImage(pageUrl));
						p.setPageNumber(i);
						p.setChapterID(chapterID);
						listPage.add(p);
					}
				}
			}
		} catch (Exception e) {
			logger.error("Error into process Fetch Page of Chapter : " + chapterUrl);
		}
		logger.info("DONE .. get Pages of Chapter .");
		return listPage;
	}

	/**
	 * Get Content Image of Page
	 * 
	 * @param pageUrl
	 * @return
	 * @throws IOException
	 */
	public static String getImage(String pageUrl) throws IOException {
		String image = null;
		Document doc = Jsoup.connect(pageUrl).get();
		if (doc.select("#viewer img").size() > 0) {
			image = doc.select("#viewer img").first().attr("src");
		}
		logger.info("Image :" + image);
		return image;
	}

	public static void main(String[] args) throws ParseException {
//		Manga m = new Manga();
//		m.setMangaName("6mm_no_taboo");
//		fetchDetailsManga(m);
		fetchPageManga("http://mangafox.me/manga/hack_xxxx/v01/c001/1.html", 1);
		
	}
}
