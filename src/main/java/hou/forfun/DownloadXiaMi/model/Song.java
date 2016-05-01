package hou.forfun.DownloadXiaMi.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.thoughtworks.xstream.annotations.XStreamAlias;

import hou.forfun.DownloadXiaMi.util.DecodeKaiserMatrix;
import hou.forfun.DownloadXiaMi.util.GetBean;

/**
 * @author houweitao
 * @date 2016年5月1日上午11:47:22
 */

@XStreamAlias("track")
public class Song {
	private static Logger log = LoggerFactory.getLogger(Song.class); // 日志记录
	private String PATH = "http://www.xiami.com/song/playlist/id/[songid]/object_name/default/object_id/0";
	// @XStreamAlias("target")
	private String target;
	@XStreamAlias("title")
	private String title;
	@XStreamAlias("song_id")
	private int songId;
	@XStreamAlias("album_id")
	private int albumId;
	@XStreamAlias("album_name")
	private String albumName;
	@XStreamAlias("artist")
	private String artist;
	private int artistId;
	@XStreamAlias("artist_url")
	private String artistUrl;
	@XStreamAlias("location")
	private String downloadLocation;// target
	@XStreamAlias("lylic")
	private String lyric;
	@XStreamAlias("album_pic")
	private String picUrl;

	public Song() {

	}

	public Song(String url) {
		build(url);
	}

	private void build(String url) {
		songId = Integer.valueOf(url.split("song")[1].substring(1));
		target = PATH.replace("[songid]", songId + "");
		boolean yes = false;
		int n = 1;
		while (!yes) {
			if (n > 30) {
				log.info("下载失败！");
				break;
			}
			System.out.println("try " + n++);
			Song song = GetBean.toBean(target, Song.class);
			if (song == null) {
				log.info("虾米无版权！");
				break;
			}
			yes = copy(this, song);
			try {
				Thread.sleep(20);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	private boolean copy(Song to, Song from) {
		to.downloadLocation = DecodeKaiserMatrix.decodeRubust(from.downloadLocation);
		if (to.downloadLocation == null)
			return false;
		to.albumId = from.albumId;
		to.albumName = from.albumName;
		to.artist = from.artist;
		to.artistId = from.artistId;
		to.artistUrl = from.artistUrl;
		to.lyric = from.lyric;
		to.picUrl = from.picUrl;
		to.songId = from.songId;
		to.title = from.title;
		// to.target = from.target;

		return true;
	}

	public int getSongId() {
		return songId;
	}

	public void setSongId(int songId) {
		this.songId = songId;
	}

	public int getAlbumId() {
		return albumId;
	}

	public void setAlbumId(int albumId) {
		this.albumId = albumId;
	}

	public String getAlbumName() {
		return albumName;
	}

	public void setAlbumName(String albumName) {
		this.albumName = albumName;
	}

	public String getArtist() {
		return artist;
	}

	public void setArtist(String artist) {
		this.artist = artist;
	}

	public int getArtistId() {
		return artistId;
	}

	public void setArtistId(int artistId) {
		this.artistId = artistId;
	}

	public String getArtistUrl() {
		return artistUrl;
	}

	public void setArtistUrl(String artistUrl) {
		this.artistUrl = artistUrl;
	}

	public String getDownloadLocation() {
		return downloadLocation;
	}

	public void setDownloadLocation(String downloadLocation) {
		this.downloadLocation = downloadLocation;
	}

	public String getLyric() {
		return lyric;
	}

	public void setLyric(String lyric) {
		this.lyric = lyric;
	}

	public String getPicUrl() {
		return picUrl;
	}

	public void setPicUrl(String picUrl) {
		this.picUrl = picUrl;
	}

	public String getTarget() {
		return target;
	}

	public void setTarget(String target) {
		this.target = target;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

}
