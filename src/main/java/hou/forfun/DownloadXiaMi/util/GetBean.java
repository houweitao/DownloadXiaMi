package hou.forfun.DownloadXiaMi.util;

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

import hou.forfun.DownloadXiaMi.model.Song;

/**
 * @author houweitao
 * @date 2016年5月1日下午12:05:34
 */

public class GetBean {
	private static Logger logger = LoggerFactory.getLogger(GetBean.class); // 日志记录

	public static void main(String[] args) {
		String url = "http://www.xiami.com/song/playlist/id/1773804623/object_name/default/object_id/0";
//		String xmlStr = get(url);
//		System.out.println(xmlStr);
		Song song = toBean(url, Song.class);
		System.out.println(song.getArtist());
		System.out.println(song.getTarget());
		System.out.println(toXml(song));

	}

	public static String toXml(Object obj) {
		XStream xstream = new XStream(new DomDriver("utf8"));
		xstream.ignoreUnknownElements("object_id");
		xstream.processAnnotations(obj.getClass()); // 识别obj类中的注解
		/*
		 * // 以压缩的方式输出XML StringWriter sw = new StringWriter();
		 * xstream.marshal(obj, new CompactWriter(sw)); return sw.toString();
		 */
		// 以格式化的方式输出XML
		return xstream.toXML(obj);
	}

	public static <T> T toBean(String url,Class<T> cls) {
		String xmlStr = get(url);
		if(xmlStr==null||xmlStr.length()<1)
			return null;
		XStream xstream = new XStream(new DomDriver());
		xstream.ignoreUnknownElements();
		xstream.processAnnotations(cls);
		@SuppressWarnings("unchecked")
		T t = (T) xstream.fromXML(xmlStr);
		return t;
	}


	static String get(String url) {
		// String url =
		// "http://www.xiami.com/song/playlist/id/1773804619/object_name/default/object_id/0";
		StringBuffer sb = new StringBuffer();
		try {
			DefaultHttpClient client = new DefaultHttpClient();
			// 发送get请求
			HttpGet request = new HttpGet(url);
			HttpResponse response = client.execute(request);

			/** 请求发送成功，并得到响应 **/
			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				/** 读取服务器返回过来的json字符串数据 **/
				String strResult = EntityUtils.toString(response.getEntity());
				// System.out.println(strResult);

				String[] tmp = strResult.split("\n");
				boolean begin = false;
				for (int i = 0; i < tmp.length; i++) {
					// System.out.println(i+": "+tmp[i]);
					if (tmp[i].contains("<trackList>"))
						tmp[i] = tmp[i].substring(11);
					if (tmp[i].contains("<track>"))
						begin = true;
					if (begin) {
						sb = sb.append(tmp[i]);
					}
					if (tmp[i].contains("</track>"))
						break;
				}
				return sb.toString();
			} else {
				logger.error("get请求提交失败:" + url);
			}
		} catch (IOException e) {
			logger.error("get请求提交失败:" + url, e);
		}
		return null;
	}
}
