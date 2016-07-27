package com.anime.entity;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "manga")
public class Manga implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "mangaID")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int mangaID;

	@Column(name = "mangaName")
	private String mangaName;

	@Column(name = "mangaCover")
	private String mangaCover;

	@Column(name = "mangaTitle")
	private String mangaTitle;

	@Column(name = "author")
	private String author;

	@Column(name = "artist")
	private String artist;

	@Column(name = "description")
	private String description;

	@Column(name = "status")
	private int status;

	@Column(name = "views")
	private int views;

	@Column(name = "rating")
	private double rating;

	@Column(name = "dateRelease")
	private Timestamp dateRelease;

	@Column(name = "dateUpdated")
	private Timestamp dateUpdated;

	@Column(name = "idxMangaFox")
	private int idxMangaFox;

	@Column(name = "genres")
	private String genres;

	@Column(name = "rankMangaFox")
	private int rankMangaFox;

	public int getMangaID() {
		return mangaID;
	}

	public void setMangaID(int mangaID) {
		this.mangaID = mangaID;
	}

	public String getMangaName() {
		return mangaName;
	}

	public void setMangaName(String mangaName) {
		this.mangaName = mangaName;
	}

	public String getMangaCover() {
		return mangaCover;
	}

	public void setMangaCover(String mangaCover) {
		this.mangaCover = mangaCover;
	}

	public String getMangaTitle() {
		return mangaTitle;
	}

	public void setMangaTitle(String mangaTitle) {
		this.mangaTitle = mangaTitle;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getArtist() {
		return artist;
	}

	public void setArtist(String artist) {
		this.artist = artist;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getViews() {
		return views;
	}

	public void setViews(int views) {
		this.views = views;
	}

	public double getRating() {
		return rating;
	}

	public void setRating(double rating) {
		this.rating = rating;
	}

	public Timestamp getDateRelease() {
		return dateRelease;
	}

	public void setDateRelease(Timestamp dateRelease) {
		this.dateRelease = dateRelease;
	}

	public Timestamp getDateUpdated() {
		return dateUpdated;
	}

	public void setDateUpdated(Timestamp dateUpdated) {
		this.dateUpdated = dateUpdated;
	}

	public int getIdxMangaFox() {
		return idxMangaFox;
	}

	public void setIdxMangaFox(int idxMangaFox) {
		this.idxMangaFox = idxMangaFox;
	}

	public String getGenres() {
		return genres;
	}

	public void setGenres(String genres) {
		this.genres = genres;
	}

	public int getRankMangaFox() {
		return rankMangaFox;
	}

	public void setRankMangaFox(int rankMangaFox) {
		this.rankMangaFox = rankMangaFox;
	}

}
