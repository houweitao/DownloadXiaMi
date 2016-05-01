package hou.forfun.DownloadXiaMi;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import hou.forfun.DownloadXiaMi.model.Song;

/**
 * @author houweitao
 * @date 2016年5月1日下午2:30:02
 */

public class Main {

	public static void main(String[] args) {
		Main main = new Main();

		String url = "http://www.xiami.com/song/1774512730";
		main.downLoadBySong(url);

		main.downLoadByAlbum("http://www.xiami.com/album/2100200848");
	}

	void downLoadByAlbum(String albumUrl) {
		List<String> songsUrl = new ArrayList<>();
		try {
			Document doc = Jsoup.connect(albumUrl).followRedirects(true).timeout(10000)
					.userAgent("Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.0)").get();
			Elements songs = doc.select("td.song_name");
			for (Element e : songs) {
				System.out.println(e.text() + " : " + e.select("a").attr("abs:href"));
				songsUrl.add(e.select("a").attr("abs:href"));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		if (songsUrl.size() > 0) {
			// 根据歌曲 URL 下载
			for (String url : songsUrl)
				downLoadBySong(url);

			// 根据歌曲信息下载
			// for (String url : songsUrl) {
			// Song song=new Song(url);
			// downLoadBySong(song);
			// }
		}
	}

	void downLoadBySong(Song song) {
		if (song == null)
			return;

		String artist = song.getArtist();
		String album = song.getAlbumName();
		String name = song.getTitle();
		String downloadLocation = song.getDownloadLocation();

		File root = new File("download");
		if (!root.exists())
			root.mkdirs();
		File artisePath = new File(root + File.separator + artist);
		if (!artisePath.exists())
			artisePath.mkdirs();
		File albumPath = new File(artisePath + File.separator + album);
		if (!albumPath.exists())
			albumPath.mkdirs();

		helpDownload(albumPath, name, downloadLocation);
		helpDownload(albumPath, name, song.getPicUrl());
		helpDownload(albumPath, name, song.getLyric());

	}

	void downLoadBySong(String songUrl) {
		Song song = new Song(songUrl);

		String artist = song.getArtist();
		String album = song.getAlbumName();
		String name = song.getTitle();
		String downloadLocation = song.getDownloadLocation();

		File root = new File("download");
		if (!root.exists())
			root.mkdirs();
		File artisePath = new File(root + File.separator + artist);
		if (!artisePath.exists())
			artisePath.mkdirs();
		File albumPath = new File(artisePath + File.separator + album);
		if (!albumPath.exists())
			albumPath.mkdirs();

		helpDownload(albumPath, name, downloadLocation);
		helpDownload(albumPath, name, song.getPicUrl());
		helpDownload(albumPath, name, song.getLyric());

	}

	private void helpDownload(File rootPath, String downloadLocation) {

		FileOutputStream fos = null;
		BufferedInputStream bis = null;
		HttpURLConnection httpUrl = null;
		URL url = null;
		int BUFFER_SIZE = 1024;
		byte[] buf = new byte[BUFFER_SIZE];
		int size = 0;

		try {
			String[] website = downloadLocation.split("//");
			// printShuzu(website);
			String[] names = website[1].split("/");
			// printShuzu(names);
			String fileName = names[names.length - 1];

			url = new URL(downloadLocation);
			httpUrl = (HttpURLConnection) url.openConnection();
			httpUrl.connect();
			bis = new BufferedInputStream(httpUrl.getInputStream());
			fos = new FileOutputStream(rootPath + File.separator + fileName);
			while ((size = bis.read(buf)) != -1) {
				fos.write(buf, 0, size);
			}
			System.out.println("download..." + downloadLocation);
			fos.flush();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassCastException e) {
			e.printStackTrace();
		} finally {
			try {
				fos.close();
				bis.close();
				httpUrl.disconnect();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (NullPointerException e) {
				e.printStackTrace();
			}
		}

	}

	private void helpDownload(File rootPath, String name, String downloadLocation) {
		FileOutputStream fos = null;
		BufferedInputStream bis = null;
		HttpURLConnection httpUrl = null;
		URL url = null;
		int BUFFER_SIZE = 1024;
		byte[] buf = new byte[BUFFER_SIZE];
		int size = 0;

		try {
			url = new URL(downloadLocation);
			httpUrl = (HttpURLConnection) url.openConnection();
			httpUrl.connect();
			bis = new BufferedInputStream(httpUrl.getInputStream());
			if (downloadLocation.contains("auth_key"))
				fos = new FileOutputStream(rootPath + File.separator + name + ".mp3");
			else if (downloadLocation.contains("lyric"))
				fos = new FileOutputStream(rootPath + File.separator + name + ".lrc");
			else if (downloadLocation.contains("images"))
				fos = new FileOutputStream(rootPath + File.separator + name + ".jpg");
			while ((size = bis.read(buf)) != -1) {
				fos.write(buf, 0, size);
			}
			System.out.println("download..." + downloadLocation);
			fos.flush();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassCastException e) {
			e.printStackTrace();
		} finally {
			try {
				fos.close();
				bis.close();
				httpUrl.disconnect();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (NullPointerException e) {
				e.printStackTrace();
			}
		}

	}
}
