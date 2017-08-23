package com.orient.util;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpUrlUtil {

	/**
	 * @param websURL
	 * @param soapRequest
	 * @return
	 */
	public static String callWs(String websURL, String soapRequest) {
		long t1 = System.currentTimeMillis();
		StringBuilder s = new StringBuilder();
		try {
			URL url = new URL(websURL);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();

			conn.setDoInput(true);
			conn.setDoOutput(true);
			conn.setAllowUserInteraction(true);
			conn.setRequestProperty("Content-Length", Integer.toString(soapRequest.getBytes().length));
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Type", "text/xml;charset=UTF-8");

			OutputStream os = conn.getOutputStream();

			os.write(soapRequest.getBytes("utf-8"));
			os.flush();
			os.close();

			InputStream is = conn.getInputStream();
			
			Reader reader = new InputStreamReader(is, "UTF-8");
			char[] b = new char[1024];
			int len = 0;
			while ((len = reader.read(b)) != -1) {
				s.append(new String(b, 0, len));
			}

			is.close();
			conn.disconnect();

		} catch (IOException e) {
			System.out.println(System.currentTimeMillis() - t1);
			e.printStackTrace();
		}
		return s.toString();
	}

}
