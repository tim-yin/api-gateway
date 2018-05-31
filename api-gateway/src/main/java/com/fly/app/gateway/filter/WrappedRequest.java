package com.fly.app.gateway.filter;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

/**
 * @author tim.yin
 * @date 2015年12月7日 上午10:41:13
 * @version 1.0
 * @Description:TODO
 */

public class WrappedRequest extends HttpServletRequestWrapper {
	private String _body;

	public WrappedRequest(HttpServletRequest request) throws IOException {
		super(request);

		_body = "";
		try {
			BufferedReader bufferedReader = request.getReader();
			String line;
			while ((line = bufferedReader.readLine()) != null)
				_body += line;
		} catch (IOException e) {
			throw e;
		}
	}

	@Override
	public ServletInputStream getInputStream() throws IOException {
		final ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(
				_body.getBytes());
		return new ServletInputStream() {
			@Override
			public boolean isReady() {
				// TODO Auto-generated method stub
				return false;
			}

			@Override
			public boolean isFinished() {
				// TODO Auto-generated method stub
				return false;
			}

			@Override
			public int read() throws IOException {
				return byteArrayInputStream.read();
			}

			@Override
			public void setReadListener(ReadListener readListener) {
				// TODO Auto-generated method stub

			}
		};
	}

	@Override
	public BufferedReader getReader() throws IOException {
		return new BufferedReader(new InputStreamReader(this.getInputStream()));
	}
}