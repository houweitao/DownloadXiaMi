package hou.forfun.DownloadXiaMi.util;

import java.util.List;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import com.thoughtworks.xstream.annotations.XStreamImplicit;
import com.thoughtworks.xstream.io.xml.DomDriver;

/**
 * @author houweitao
 * @date 2016年5月1日下午12:22:23
 * @url http://www.blogjava.net/bolo/archive/2014/08/26/417353.html
 */

public class XStreamHandle {

	private static final String xmlString = "<books><book price=\"108\"><name>Java编程思想</name><author>Bruce Eckel</author></book><book price=\"52\"><name>Effective Java</name><author>Joshua Bloch</author></book><book price=\"118\"><name>Java 7入门经典</name><author>Ivor Horton</author></book></books>";

	public static String toXml(Object obj) {
		XStream xstream = new XStream(new DomDriver("utf8"));
		xstream.processAnnotations(obj.getClass()); // 识别obj类中的注解
		/*
		 * // 以压缩的方式输出XML StringWriter sw = new StringWriter();
		 * xstream.marshal(obj, new CompactWriter(sw)); return sw.toString();
		 */
		// 以格式化的方式输出XML
		return xstream.toXML(obj);
	}

	public static <T> T toBean(String xmlStr, Class<T> cls) {
		XStream xstream = new XStream(new DomDriver());
		xstream.processAnnotations(cls);
		@SuppressWarnings("unchecked")
		T t = (T) xstream.fromXML(xmlStr);
		return t;
	}

	public static void main(String[] args) {
		Books books = toBean(xmlString, Books.class);
		List<Book> list = books.getList();
		for (Book book : list) {
			System.out
					.println("name=" + book.getName() + "\tauthor=" + book.getAuthor() + "\tprice=" + book.getPrice());
		}
		System.out.println(toXml(books));
	}
}

@XStreamAlias("books")
class Books {

	// 隐式集合，加上这个注解可以去掉book集合最外面的<list></list>这样的标记
	@XStreamImplicit
	private List<Book> list;

	public List<Book> getList() {
		return list;
	}

	public void setList(List<Book> list) {
		this.list = list;
	}

}

@XStreamAlias("book")
class Book {

	// 别名注解，这个别名就是XML文档中的元素名，Java的属性名不一定要与别名一致
	@XStreamAlias("name")
	private String name;

	@XStreamAlias("author")
	private String author;

	// 属性注解，此price就是book的属性，在XML中显示为：<book price="108">
	@XStreamAsAttribute()
	@XStreamAlias("price")
	private String price;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

}