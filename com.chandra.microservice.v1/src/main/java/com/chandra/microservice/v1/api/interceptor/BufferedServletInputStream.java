package com.chandra.microservice.v1.api.interceptor;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import java.io.ByteArrayInputStream;

/**
 * This class is to read the HTTP request payload an parameters
 * 
 * @author Chandra
 *
 */
public class BufferedServletInputStream extends ServletInputStream {

	private ByteArrayInputStream bais;

	public BufferedServletInputStream(ByteArrayInputStream bais) {

		this.bais = bais;
	}

	@Override
	public int available() {

		return this.bais.available();
	}

	public int read() {

		return this.bais.read();
	}

	@Override
	public int read(byte[] buf, int off, int len) {

		return this.bais.read(buf, off, len);
	}

	public boolean isFinished() {

		return true;
	}

	public boolean isReady() {

		return true;
	}

	public void setReadListener(ReadListener paramReadListener) {

		// nothing
	}
}
