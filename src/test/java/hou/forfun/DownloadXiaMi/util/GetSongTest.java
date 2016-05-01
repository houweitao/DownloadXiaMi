package hou.forfun.DownloadXiaMi.util;

import hou.forfun.DownloadXiaMi.model.Song;

/**
 * @author houweitao
 * @date 2016年5月1日下午1:59:28
 */

public class GetSongTest {
	public static void main(String[] args) {
		Song song=new Song("http://www.xiami.com/song/1769397120");
		System.out.println(song.getTarget());
		System.out.println(GetBean.toXml(song));
		System.out.println(song.getTitle());
	}
}
