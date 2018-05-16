package Crawler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import org.jsoup.nodes.Document;

public abstract class Cralwer {
	public abstract void crawl();
	
	protected URLConnection connect(String sUrl, String cookie) {
		URL url;
		URLConnection conn = null;
		try {
			url = new URL(sUrl);
			conn = url.openConnection();
			// Set the cookie value to send
			conn.setRequestProperty("Cookie", cookie);
			// Send the request to the server
			conn.connect();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return conn;
	}

	protected String getResponse(URLConnection conn, String charset) {
		BufferedReader in;
		StringBuffer response = null;
		try {
			in = new BufferedReader(new InputStreamReader(conn.getInputStream(), charset));
			String inputLine;
			response = new StringBuffer();
			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine + "\n");
			}
			in.close();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return response.toString();
	}
	
}
