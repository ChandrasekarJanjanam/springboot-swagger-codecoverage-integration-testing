package com.chandra.microservice.v1.api.interceptor;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.*;

/**
 * This class is to read the HTTP request payload an parameters
 * 
 * @author chandra
 *
 */
public class BufferedRequestWrapper extends HttpServletRequestWrapper {

	private ByteArrayInputStream bais = null;
	private ByteArrayOutputStream baos = null;
	private BufferedServletInputStream bsis = null;
	private byte[] buffer = null;

	/**
	 * @param req
	 * @throws IOException
	 */
	public BufferedRequestWrapper(HttpServletRequest req) throws IOException {

		super(req);
		// Read InputStream and store its content in a buffer.
		InputStream is = req.getInputStream();
		this.baos = new ByteArrayOutputStream();
		byte buf[] = new byte[1024];
		int letti;
		while ((letti = is.read(buf)) > 0) {
			this.baos.write(buf, 0, letti);
		}
		this.buffer = this.baos.toByteArray();
	}

	/* (non-Javadoc)
	 * @see javax.servlet.ServletRequestWrapper#getInputStream()
	 */
	@Override
	public ServletInputStream getInputStream() {

		this.bais = new ByteArrayInputStream(this.buffer);
		this.bsis = new BufferedServletInputStream(this.bais);
		return this.bsis;
	}

	/**
	 * @return
	 * @throws IOException
	 */
	String getRequestBody() throws IOException {

		BufferedReader reader = new BufferedReader(new InputStreamReader(this.getInputStream()), 2048);
		String line = null;
		StringBuilder inputBuffer = new StringBuilder();
		do {
			line = reader.readLine();
			if (null != line) {
				inputBuffer.append(line.trim());
			}
		} while (line != null);
		reader.close();
		return inputBuffer.toString().trim();
	}
}
