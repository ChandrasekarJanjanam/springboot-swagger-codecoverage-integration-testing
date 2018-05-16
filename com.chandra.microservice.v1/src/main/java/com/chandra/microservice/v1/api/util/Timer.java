package com.chandra.microservice.v1.api.util;

/**
 * This class is used for logging time taken by a particular service.
 * 
 * @author Chandra
 * 
 */
public class Timer {
	private long startTime;
	private String source;
	private String dest;

	private boolean fineGrain;

	public static final String EYE_CATCHER_S = "@@@ ";
	public static final String EYE_CATCHER_E = "ms @@@";

	/**
	 * This constructor is used by Coarse grained services
	 * 
	 * @param src
	 *            - Client
	 * @param dst
	 *            - Service Provider
	 */
	public Timer(String src, String dst) {
		startTime = System.currentTimeMillis();
		source = src;
		dest = dst;
	}

	/**
	 * This constructor is used by Fine grained services
	 */
	public Timer() {
		startTime = System.currentTimeMillis();
		fineGrain = true;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		StringBuffer s = new StringBuffer(EYE_CATCHER_S);
		if (!fineGrain) {
			s.append(source);
			s.append("->");
			s.append(dest);
			s.append(":");
		}
		s.append(System.currentTimeMillis() - startTime);
		s.append(EYE_CATCHER_E);
		return s.toString();
	}
}