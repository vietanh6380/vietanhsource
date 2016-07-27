package com.anime.entity;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "chapter")
public class Chapter {

	@Id
	@Column(name = "chapterID")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int chapterID;

	@Column(name = "mangaID")
	private int mangaID;

	@Column(name = "chapterName")
	private String chapterName;

	@Column(name = "chapterUrl")
	private String chapterUrl;

	@Column(name = "chapterNumber")
	private float chapterNumber;

	@Column(name = "chapterTitle")
	private String chapterTitle;

	@Column(name = "datePublish")
	private Timestamp datePublish;

	public Chapter(int chapterID, String chapterName, String chapterUrl, int chapterNumber, Timestamp datePublish) {
		super();
		this.chapterID = chapterID;
		this.chapterName = chapterName;
		this.chapterUrl = chapterUrl;
		this.chapterNumber = chapterNumber;
		this.datePublish = datePublish;
	}

	public Chapter() {
		super();
	}

	public int getChapterID() {
		return chapterID;
	}

	public void setChapterID(int chapterID) {
		this.chapterID = chapterID;
	}

	public String getChapterName() {
		return chapterName;
	}

	public void setChapterName(String chapterName) {
		this.chapterName = chapterName;
	}

	public String getChapterUrl() {
		return chapterUrl;
	}

	public void setChapterUrl(String chapterUrl) {
		this.chapterUrl = chapterUrl;
	}

	public float getChapterNumber() {
		return chapterNumber;
	}

	public void setChapterNumber(float chapterNumber) {
		this.chapterNumber = chapterNumber;
	}

	public int getMangaID() {
		return mangaID;
	}

	public void setMangaID(int mangaID) {
		this.mangaID = mangaID;
	}

	public String getChapterTitle() {
		return chapterTitle;
	}

	public void setChapterTitle(String chapterTitle) {
		this.chapterTitle = chapterTitle;
	}

	public Timestamp getDatePublish() {
		return datePublish;
	}

	public void setDatePublish(Timestamp datePublish) {
		this.datePublish = datePublish;
	}

}
