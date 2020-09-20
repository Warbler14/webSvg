package com.study.websvg.util;

import java.util.List;
import java.util.Map;
import com.sun.istack.internal.Nullable;

public class ObjectUtil {

	public static final String SIGN_EQUAL = "=";
	public static final String SIGN_AMPERSAND = "&";
	public static final String SIGN_SEMICOLON = ";";
	public static final String SIGN_COMMA = ",";
	public static final String SIGN_COLON = ":";
	public static final String SIGN_HYPHEN = "-";
	
	public static final String DEFAULT_CHARSET = "UTF-8";
	
	/**
	 * 빈 객체 체크
	 * @param str
	 * @return
	 */
	public static boolean isEmpty(final @Nullable CharSequence cs) {
		return (cs == null || "".equals(cs));
	}
	
	/**
	 * 빈 객체 체크
	 * @param str
	 * @return
	 */
	public static boolean isEmpty(final Map<String, ?> chMap) {
		return ! isNotEmpty(chMap);
	}
	
	/**
	 * 빈 객체 체크
	 * @param str
	 * @return
	 */
	public static boolean isEmpty(final List<?> useList) {
		return ! isNotEmpty(useList);
	}
	
    /**
     * 빈 객체 체크
     * @param str
     * @return
     */
	public static boolean isNotEmpty(final CharSequence cs) {
		if(cs == null || cs.length() == 0) {
			return false;
		}
		
		String str = cs.toString().trim();
		if(isEmpty(str)){
			return false;
		} else {
			return true;
		}
	}
	
	/**
     * 빈 객체 체크
     * @param builder
     * @return
     */
	public static boolean isNotEmpty(final StringBuilder builder ){
		if( builder == null || builder.length() <= 0 ){
			return false;
		} else {
			return true;
		}
	}

	/**
     * 빈 객체 체크
     * @param buffer
     * @return
     */
	public static boolean isNotEmpty(final StringBuffer buffer ){
		if( buffer == null || buffer.length() <= 0 ){
			return false;
		} else {
			return true;
		}
	}

	/**
	 * 빈 객체 체크
	 * @param chMap
	 * @return
	 */
	public static boolean isNotEmpty(final Map<String, ?> chMap ){
		if (chMap == null) {
			return false;
		}
		
		synchronized (chMap) {
			if(chMap.isEmpty()){
				return false;
			} else {
				return true;
			}
		}
	}

	/**
	 * 빈 객체 체크
	 * @param useList
	 * @return
	 */
	public static boolean isNotEmpty(final List<?> useList) {
		if (useList == null) {
			return false;
		}
		
		synchronized (useList) {
			if (useList.isEmpty()) {
				return false;
			} else {
				return true;
			}
		}
	}

    /**
     * 정수객체 체크
     * @param intValue
     * @return
     */
    public static Boolean isPositiveInt(final Integer intValue ){
        if( intValue == null || intValue <= 0){
            return false;
        } else {
            return true;
        }
    }

    /**
     * 정수객체 체크
     * @param longValue
     * @return
     */
    public static Boolean isPositiveLong(final Long longValue ){
        if( longValue == null || longValue <= 0){
            return false;
        } else {
            return true;
        }
    }

	/**
	 * 객체 크기 출력
	 * @param chMap
	 * @return
	 */
	public static int size(Map<String, ?> chMap ){
		int result = 0;
		
		if(chMap == null) {
			return result;
		}
		
		synchronized (chMap) {
			result = chMap.size();
		}
		
		return result;
	}

	/**
	 * 스트링 객체 반환
	 * @param arrs
	 * @return
	 */
	public final static String getString (Object... objs) {
		if(objs == null) {
			return null;
		}
		
		StringBuilder builder = new StringBuilder();
		for(Object obj : objs) {
			builder.append(obj);
		}
		
		return builder.toString();
	}
}
