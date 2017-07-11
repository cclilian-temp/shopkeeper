package com.mall.shopkeeper.utils;

import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URLEncodedUtils;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.fasterxml.jackson.databind.type.MapType;
import com.mall.shopkeeper.exceptions.JsonProcessException;

public class Utils {

	private static final Pattern NUM_LETTER_PATTERN = Pattern.compile("^[A-Za-z0-9]+$");

	private static final ObjectMapper mapper = new ObjectMapper();
	private static final ObjectMapper mapperEscapeNonAscii = new ObjectMapper();

	private static Random random = new Random();
	private static char RANDOM_CHAR_TABLE[] = { 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n',
			'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I',
			'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z',
			// '~', '(', ')', '-', '_', '+', '-', // 这一行可以继续添加特殊字符
			'1', '2', '3', '4', '5', '6', '7', '8', '9', '0' };

	static {
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

		mapperEscapeNonAscii.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		mapperEscapeNonAscii.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS,false);
		mapperEscapeNonAscii.configure(JsonGenerator.Feature.ESCAPE_NON_ASCII, true);
	}

	public static String obj2Json(Object obj, boolean escapeNonAscii) throws JsonProcessException {
		try {
			if (escapeNonAscii) {
				return mapperEscapeNonAscii.writeValueAsString(obj);
			} else {
				return mapper.writeValueAsString(obj);
			}
		} catch (Exception e) {
			throw new JsonProcessException(e);
		}
	}

	public static String obj2Json(Object obj) throws JsonProcessException {
		return obj2Json(obj, false);
	}

	public static <T> T json2Obj(String json, Class<T> clazz) throws JsonProcessException {
		try {
			return mapper.readValue(json, clazz);
		} catch (Exception e) {
			throw new JsonProcessException(e);
		}
	}

	@SuppressWarnings("rawtypes")
	public static <T> Collection<T> json2Obj(String json, Class<T> clazz, Class<? extends Collection> collectionClazz) {
		try {
			CollectionType type = mapper.getTypeFactory().constructCollectionType(collectionClazz, clazz);
			return mapper.readValue(json, type);
		} catch (Exception e) {
			throw new JsonProcessException(e);
		}
	}

	@SuppressWarnings("rawtypes")
	public static <K, V> Map<K, V> json2Obj(String json, Class<K> keyClazz, Class<V> valueClazz,
			Class<? extends Map> mapClazz) {
		try {
			MapType type = mapper.getTypeFactory().constructMapType(mapClazz, keyClazz, valueClazz);
			return mapper.readValue(json, type);
		} catch (Exception e) {
			throw new JsonProcessException(e);
		}
	}

	public static boolean coordinatesEquals(double[] dd1, double[] dd2) {
		if (dd1 == null || dd2 == null || dd1.length != dd2.length) {
			return false;
		}
		final double tolerance = 0.0000001;
		boolean equal = true;
		for (int i = 0; i < dd1.length; i++) {
			double d1 = dd1[i];
			double d2 = dd2[i];
			if (Math.abs(d1 - d2) > tolerance) {
				equal = false;
				break;
			}
		}
		return equal;
	}

	public static boolean coordinatesEquals(double d1, double d2) {
		return coordinatesEquals(new double[] { d1 }, new double[] { d2 });
	}

	public static String getAbsoluteFilePath(String path) {
		if (path == null) {
			return null;
		}
		final String CP_PREFIX = "classpath:";
		if (path.startsWith(CP_PREFIX) || path.startsWith(CP_PREFIX.toUpperCase())) {
			path = path.substring(CP_PREFIX.length());
			path = Utils.class.getResource(path).getPath();
		}
		return path;
	}

	public static String formatTsToPythonStyle(long ts) {
		String tsStr = "" + ts;
		return tsStr.substring(0, 10) + "." + tsStr.substring(10, 13) + "000";
	}

	public static String formatTs(long ts, String pattern) {
		SimpleDateFormat sf = new SimpleDateFormat(pattern);
		return sf.format(new Date(ts));
	}

	public static Boolean isDigit(String str) {
		Boolean isDigit = false;
		char str2Char[] = str.toCharArray();
		for (int i = 0; i < str2Char.length; i++) {
			if (!Character.isDigit(str2Char[i])) {
				isDigit = false;
				break;
			}
			isDigit = true;
		}
		return isDigit;
	}

	// 使用正则表达式判断字符串是否只包含数字或者字母
	public static Boolean isNumberOrLetter(String str) {
		Matcher isNumOrLetter = NUM_LETTER_PATTERN.matcher(str);
		return isNumOrLetter.matches();
	}

	public static int[] parsePageAndNum(Integer page, Integer num) {
		final int defaultMaxNum = 20000;// Integer.MAX_VALUE取出限制在最多200
		final int defaultPageInt = 0;
		int pageInt = defaultPageInt, numInt = defaultMaxNum;
		if (page != null)
			pageInt = page;
		if (num != null)
			numInt = num;
		if (pageInt < 1) {
			pageInt = 1;
		}
		if (numInt < 1) {
			numInt = 1;
		} else if (numInt > defaultMaxNum) {
			numInt = defaultMaxNum;
		}
		int offset = pageInt == 1 ? 0 : (pageInt - 1) * numInt;
		return new int[] { offset, numInt };
	}

	public static long getTotalPage(Long totalCount, Integer num) {
		if (totalCount < 1) {
			totalCount = 1l;
		}
		if (num < 1) {
			num = 1;
		}
		if (totalCount % num == 0 && totalCount >= num)
			return totalCount / num;
		else
			return totalCount / num + 1;
	}

	/**
	 * 生成一个包含大小写字母、数字的随机字符串
	 * 
	 * @param length
	 *            字符串长度
	 * @return
	 */
	public static String generateRandomString(int length) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < length; i++) {
			int tmp = random.nextInt(RANDOM_CHAR_TABLE.length);
			sb.append(RANDOM_CHAR_TABLE[tmp]);
		}
		return sb.toString();
	}

	public static Map<String, String> parseEncodedKVParamsString(String str, String encoding) {
		if (str != null) {
			List<NameValuePair> kvs = URLEncodedUtils.parse(str, Charset.forName(encoding));
			Map<String, String> map = new HashMap<>(kvs.size());
			for (NameValuePair p : kvs) {
				if (p.getValue() != null) {
					map.put(p.getName(), p.getValue()); // 同名参数后出现的覆盖先出现的
				}
			}
			return map;
		} else {
			return null;
		}
	}

	public static String parseBinaryText(byte[] text) {
		try {
			if (text != null) {
				return new String(text, "UTF-8");
			}
		} catch (Exception e) {
			return "";
		}
		return "";
	}

	/**
	 * String的字符串转换成unicode的String
	 * 
	 * @param String
	 *            strText 全角字符串
	 * @return String 每个unicode之间无分隔符
	 */
	public static String strToUnicode(String strText) {
		char c;
		StringBuilder str = new StringBuilder();
		int intAsc;
		String strHex;
		for (int i = 0; i < strText.length(); i++) {
			c = strText.charAt(i);
			intAsc = (int) c;
			strHex = Integer.toHexString(intAsc);
			if (intAsc > 128 && strHex.length() == 4)
				str.append("\\u" + strHex);
			else if (strHex.length() == 2) {// 低位在前面补00
				str.append("\\u00" + strHex);
			} else if (strHex.length() == 3) {
				str.append("\\u0" + strHex);
			}
		}
		return str.toString();
	}

	/**
	 * 汉字和表情符号转换成unicode的String
	 * 
	 * @param String
	 *            strText 全角字符串
	 * @return String 每个unicode之间无分隔符
	 */
	public static String hanziToUnicode(String strText) {
		char c;
		StringBuilder str = new StringBuilder();
		int intAsc;
		String strHex;
		for (int i = 0; i < strText.length(); i++) {
			c = strText.charAt(i);
			intAsc = (int) c;
			strHex = Integer.toHexString(intAsc);
			if (intAsc > 128)
				str.append("\\u" + strHex);
			else // 低位在前面补00
					// str.append("\\u00" + strHex);
				str.append(c);
		}
		return str.toString();
	}

	/**
	 * unicode的String转换成String的字符串
	 * 
	 * @param String
	 *            hex 16进制值字符串 （一个unicode为2byte）
	 * @return String 全角字符串
	 */
	public static String unicodeToString(String hex) {
		int t = hex.length() / 6;
		StringBuilder str = new StringBuilder();
		for (int i = 0; i < t; i++) {
			String s = hex.substring(i * 6, (i + 1) * 6);
			// 高位需要补上00再转
			String s1 = s.substring(2, 4) + "00";
			// 低位直接转
			String s2 = s.substring(4);
			// 将16进制的string转为int
			int n = Integer.valueOf(s1, 16) + Integer.valueOf(s2, 16);
			// 将int转换为字符
			char[] chars = Character.toChars(n);
			str.append(new String(chars));
		}
		return str.toString();
	}
	
	/**
	 * 不会产生NullPointerException的toString方法
	 * @param obj
	 * @return
	 */
	public static String toString(Object obj) {
		return obj == null ? null : obj.toString();
	}

	public static String fixJsonLibBug(String value) {
		if (StringUtils.isNotBlank(value)) {
			// 兼容jsonlib2.2.3bug
			value = value.replaceAll("\\:\"\\[", ": \"[");
			return value;
		}
		return "";
	}

	
	@SuppressWarnings("rawtypes")
	public static List getPageList(List list, Integer currentPage, Integer pageSize,Integer defaultPageSize) {
		if (currentPage == null || currentPage <= 0)
			currentPage = 1;
		if (pageSize == null|| pageSize <= 0)
			pageSize = defaultPageSize;
		int pageNum = list.size() % pageSize == 0 ? list.size() / pageSize : list.size() / pageSize + 1;
		int fromIndex = 0; // 从哪里开始截取
		int toIndex = 0; // 截取几个
		if (list == null || list.size() == 0)
			return null;
		// 当前页小于或等于总页数时执行
		if (currentPage <= pageNum && currentPage != 0) {
			fromIndex = (currentPage - 1) * pageSize;

			if (currentPage == pageNum) {
				toIndex = list.size();

			} else {
				toIndex = currentPage * pageSize;
			}

		}
		return list.subList(fromIndex, toIndex);
	}
}
