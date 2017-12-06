package com.song.util;

import org.springframework.beans.BeanUtils;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


//定义公共静态方法
public class Utility {
	private static SimpleDateFormat timeFormatter = new SimpleDateFormat(
			"yyyyMMddHHmmss");
	private static java.util.Random rndGenerator = new java.util.Random();
	
	public static int toInt(String _strData) {
		try {
			if (_strData == null || _strData.length() <= 0)
				return 0;
			return Integer.parseInt(_strData);
		} catch (Exception e) {
			return 0;
		}
	}

	public static long toLong(String _strData) {
		try {
			if (_strData == null || _strData.length() <= 0)
				return 0;
			return Long.parseLong(_strData);
		} catch (Exception e) {
			return 0;
		}
	}
	
	public static Short toShort(String _strData) {
		try {
			if (_strData == null || _strData.length() <= 0)
				return 0;
			return Short.parseShort(_strData);
		} catch (Exception e) {
			return 0;
		}
	}	

	public static double toDouble(String _strData) {
		try {
			if (_strData == null || _strData.length() <= 0)
				return 0;
			return Double.parseDouble(_strData);
		} catch (Exception e) {
			return 0;
		}
	}

	public static float toFloat(String _strData) {
		try {
			if (_strData == null || _strData.length() <= 0)
				return 0;
			return Float.parseFloat(_strData);
		} catch (Exception e) {
			return 0;
		}
	}

	public static boolean toBoolean(String _strData) {
		try {
			if (_strData == null || _strData.length() <= 0)
				return false;
            return !_strData.startsWith("0")
                    && !_strData.toLowerCase().startsWith("false");
		} catch (Exception e) {
			return false;
		}
	}

	public static BigDecimal toBigDecimal(String _strData) {
		try {
			if (_strData == null || _strData.length() <= 0)
				return new BigDecimal("0");
			return new BigDecimal(_strData);
		} catch (Exception e) {
			return new BigDecimal("0");
		}
	}
	
	public static String byte2hex(byte[] _buffer) {
		String strHex = "";
		String strTemp = "";
		for (int i = 0; i < _buffer.length; i++) {
			strTemp = (Integer.toHexString(_buffer[i] & 0XFF));
			if (strTemp.length() == 1)
				strHex = strHex + "0" + strTemp;
			else
				strHex = strHex + strTemp;
			if (i < _buffer.length - 1)
				strHex = strHex + ":";
		}
		return strHex.toUpperCase();
	}
	
	/**
	 * 字符串小数转换为四舍五入并保留两位
	 * @param str
	 * @return String
	 */
	public static String getFloatTwo(String str){
		if(str==null ||str.trim().equals("")){
			str=  "";
		}else{
			double num = Double.parseDouble(str);
			BigDecimal b = new BigDecimal(num);
			num = b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
			str = Double.toString(num);
		}
		return str;
	}
	
	/**
	 * Bean属性拷贝
	 */
	public static void copyProperties(Object source, Object target) {
		try {
			BeanUtils.copyProperties(source, target);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 传入日期（格式：yyyyMMdd），返回当前日期的上一个月份
	 * @param ymd
	 * @return
	 */
	public static String getPreMonth(String ymd){
		String preYm = "";
		if (ymd != null && !ymd.equals("")){
			int y = Integer.parseInt(ymd.substring(0, 4));
			int m = Integer.parseInt(ymd.substring(4, 6));
			if (m == 1){
				y = y - 1;
				m = 12;
			} else {
				m--;
			}
			if (m < 10){
				preYm = y + "0" + m;
			} else {
				preYm = y + "" + m;
			}
		}
		return preYm;
	}
	
	public static String firstToUpper(String str1) {
		String str2 = str1.toUpperCase();
		if (str1.length() > 1) {
			str2 = str1.toLowerCase().substring(0, 1).toUpperCase()
					+ str1.toLowerCase().substring(1);
		}
		return str2;
	}	
	
	public static String replaceLine(String fieldName) {
		String filenametemp = fieldName;
		if (filenametemp.indexOf("_") > 0) {
			String[] filenamenew = filenametemp.split("_");
			for (int j = 0; j < filenamenew.length; j++) {
				if (j == 0) {
					filenametemp = filenamenew[j];
				} else {
					filenametemp = filenametemp + firstToUpper(filenamenew[j]);
				}
			}
		}
		return filenametemp;
	}	
	
	public static String getCurDateString(int _iType) {
		Date curTime = new Date();
		String strTemp = timeFormatter.format(curTime);
		SimpleDateFormat formatter = null;

		switch (_iType) {
		case 0: // yyyymmdd
			strTemp = strTemp.substring(0, 8);
			break;
		case 6: // yymmdd
			strTemp = strTemp.substring(2, 8);
			break;
		case 8: // yyyymmdd
			strTemp = strTemp.substring(0, 8);
			break;
		case 10: // yyyy-mm-dd
			formatter = new SimpleDateFormat("yyyy-MM-dd");
			strTemp = formatter.format(new Date());
			break;
		case 14:// yyyy-mm-dd hh:mm:ss
			formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			strTemp = formatter.format(curTime);
			break;
		case 19: // yyyy-mm-dd HH:mm:ss
			formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			strTemp = formatter.format(curTime);
			break;			
		case -6: // HHmmss
			strTemp = strTemp.substring(8);
			break;
		case -8: // HH:mm:ss
			formatter = new SimpleDateFormat("HH:mm:ss");
			strTemp = formatter.format(new Date());
			break;
		default:
			break;
		}
		return strTemp;
	}

	public static String formatDateString(String _strDateStr) {
		if (_strDateStr == null)
			return "";
		int iLength = _strDateStr.length();
		String strRetn = "";

		switch (iLength) {
		case 6: // hh:mm:ss
			strRetn = _strDateStr.substring(0, 2) + ":"
					+ _strDateStr.substring(2, 4) + ":"
					+ _strDateStr.substring(4);
			break;
		case 8: // yyyy-mm-dd
			strRetn = _strDateStr.substring(0, 4) + "-"
					+ _strDateStr.substring(4, 6) + "-"
					+ _strDateStr.substring(6);
			break;
		case 10: // mm/dd/yyyy
			strRetn = _strDateStr.substring(5, 7) + "/"
					+ _strDateStr.substring(8) + "/"
					+ _strDateStr.substring(0, 4);
			break;
		case 14:// yyyy-mm-dd hh:mm:ss
			strRetn = _strDateStr.substring(0, 4) + "-"
					+ _strDateStr.substring(4, 6) + "-"
					+ _strDateStr.substring(6, 8) + " "
					+ _strDateStr.substring(8, 10) + ":"
					+ _strDateStr.substring(10, 12) + ":"
					+ _strDateStr.substring(12);
			break;
		}

		return strRetn;
	}
	
	public static String dateToString(Date date, String pattern) {
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		String str = sdf.format(date);
		return str;
	}
	
	public static Date addMinute(Date inDate, int _minuteNum) {
		Calendar date = Calendar.getInstance();
		date.setTime(inDate);
		date.add(Calendar.MINUTE, _minuteNum);
		return date.getTime();
	}	
	
	public static boolean isNotEmpty(Object obj)
	{
    	return !isEmpty(obj);
	}
	
	public static boolean isEmpty(Object obj)
	{
    	boolean result = false;
    	if(obj == null ){
    		result = true;
    	}
    	String str = String.valueOf(obj);
    	if(str == null || str.trim().length() <=0){
    		result = true;
    	} else {
    		if("NULL".equals(str.toUpperCase()) && !(obj instanceof String)){
    			result = true;
    		}
    	}
    	return result;
	}
	
	public  static String getSenNo(int len, String value){
		String result ="";
		if(Utility.isEmpty(value)){
			value ="0";
		}
		if(len < value.length()){
			result = value;
		} else {
			value = String.valueOf(Long.valueOf(value)+1);
			result = value;
			for(int i=0;i<len-value.length();i++){
				result = "0"+result;
			}
		}
		return result;
	}
	
	public static String createRnd() {
		int iTemp = rndGenerator.nextInt(10000);
		if (iTemp < 1000)
			iTemp += 1000;
		else if (iTemp == 10000)
			iTemp = 9999;
		return Integer.toString(iTemp);
	}	
	
	/**
	 * 根据Key从MAP获取值
	 * @param map
	 * @param key
	 * @param defaultValue
	 * @return
	 */
	public static String getValueByKey(Map map, String key, String defaultValue){
		String result = defaultValue;
		if(map != null){
			try{
				String rt = (String)map.get(key);
				if(!Utility.isEmpty(rt)){
					result = rt;
				}
			} catch(Exception e){
			}
		}
		return result;
	}	
	
	
	
	


	public static String getNvlStr(String value){
		String result = value;
		if(value == null || value.trim().length() <= 0){
			result ="";
		} else {
			if(Utility.getConverteStr(String.valueOf(value)).indexOf("null")!=-1)
			{
				result ="";
			}
		}
		result = getConverteStr(result);
		return result;
	}
	
	
	public static String getLowerStr(String value){
		String result ="";
		if(value != null && value.length() >0 ){
			result = value.toLowerCase();
		}
		return result;
	}
	
	
	public static String getConverteStr(String value){
		String result = value;
		if(Utility.isEmpty(value)){
			return result;
		}
		try{
			result = result.replaceAll(">", "&gt;");
			result = result.replaceAll("<", "&lt;");
			result = result.replaceAll("\"", "&quot;");
			result = result.replaceAll("\'", "&#39;");
			result = result.replaceAll("\\\\", "\\\\");
	        result = result.replaceAll("\n", "");
	        result = result.replaceAll("\r", "");
		} catch(Exception e){}
        return result;
	}
	
	
	public static String getPrefixByLevel(int level, String functionCode){
		String functionPrefix = "";
		if(level == 1 ){
			functionPrefix = "%" +"000000";
		}
		if(level == 2 ){
			functionPrefix = functionCode.substring(0,2);
			functionPrefix = functionPrefix +"%" +"0000";
		}
		if(level == 3 ){
			functionPrefix = functionCode.substring(0,4);
			functionPrefix = functionPrefix +"%" +"00";
		}
		if(level == 4 ){
			functionPrefix = functionCode.substring(0,6);
			functionPrefix = functionPrefix +"%";
		}
		if(level > 4){
			functionPrefix = functionCode;
		}
		return functionPrefix;
	}
	
	public static String getOptionPrefixByLevel(int level, String optionCode){
		String functionPrefix = "";
		if(level == 1 ){
			functionPrefix = "%" +"000000";
		}
		if(level == 2 ){
			functionPrefix = optionCode.substring(0,2);
			functionPrefix = functionPrefix +"%" +"";
		}
		
		return functionPrefix;
	}
	
	public static int getLevelByCode(String code){
		int level = 1;
		if(!Utility.isEmpty(code)){
			if(code.endsWith("000000")){
				level = 1;
			} else if(code.endsWith("0000")){
				level = 2;
			} else if(code.endsWith("00")){
				level = 3;
			} else {
				level = 4;
			}
		}
		
		return level;
	}
	
	
	public static Object getFieldValueByName(String fieldName, Object o) {
		try {
			String firstLetter = fieldName.substring(0, 1).toUpperCase();
			String getter = "get" + firstLetter + fieldName.substring(1);
			Method method = o.getClass().getMethod(getter);
			Object value = method.invoke(o);
			return value;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	



	/**
	 * 特殊字符检查
	 * @param userName
	 * @param userPassword
	 * @return
	 */
	public static boolean isRightCharacter(String entityCode, String userName, String userPassword){
		boolean result = false;
		Pattern p= Pattern.compile("[\'|\"|%|$|@|+|;|<|>|(|)|\t|\n|\\|]");
		Matcher m=p.matcher(userName);
		result=m.find();
		if(result){
			return result;
		}
		m=p.matcher(userPassword);
		result=m.find();
		if(result){
			return result;
		}
		m=p.matcher(entityCode);
		result=m.find();
		if(result){
			return result;
		}
		return result;
	}
	
	
	public static String filterDangerString(String value) {
        if (value == null) {
			return null;
        }
        value = value.replaceAll("\\|", "");
        value = value.replaceAll("&", "&");
        value = value.replaceAll(";", "");
        value = value.replaceAll("@", "");
        value = value.replaceAll("'", "");
        //value = value.replaceAll("\"", "");
        value = value.replaceAll("\\'", "");
        //value = value.replaceAll("\\\"", "");
        value = value.replaceAll("<", "<");
        value = value.replaceAll(">", ">");
        value = value.replaceAll("\\(", "");
        value = value.replaceAll("\\)", "");
        value = value.replaceAll("\\+", "");
        value = value.replaceAll("\r", "");
        value = value.replaceAll("\n", "");
        value = value.replaceAll("script", "");
        value = value.replaceAll("'", "");
        value = value.replaceAll(">", "");
        value = value.replaceAll("<", "");
        //value = value.replaceAll("=", "");
        value = value.replaceAll("/", "");
        //value = value.replaceAll("%", "");
        return value;
   }
	

   public static boolean isMobile(HttpServletRequest request){
	   boolean result = false;
	   String userAgent = request.getHeader("User-Agent");
	   if(Utility.isEmpty(userAgent)){
		   userAgent = request.getHeader("USER-AGENT");
	   } 
	   if(!Utility.isEmpty(userAgent)){
		   Pattern p= Pattern.compile("iphone|ipod|android|blackberry|webos|incognito|webmate|bada|nokia|lg|ucweb|skyfire");
		   Matcher m=p.matcher(userAgent.toLowerCase());
		   result=m.find();
	   }
	   return result;
   }
   
   public static Map getRequestHeaders(HttpServletRequest request){
	    Map map = new HashMap();
		Enumeration e = request.getHeaderNames() ;
		while(e.hasMoreElements()){  
			 String name = (String) e.nextElement();
			 String value = request.getHeader(name);
			 map.put(name, value);
		}
	    return map;
   }
   
   /**
    * 判断是否是整形
    * @param str
    * @return
    */
   public static boolean isInteger(String str){
	   try{
		   Integer.parseInt(str);
		   return true;
	   } catch (NumberFormatException e) {
		   return false;
	   }
   }
   
   /**
    * 验证是否是浮点型
    * @param str
    * @return
    */
   public static boolean isDouble(String str){
	   try{
		   Double.parseDouble(str);
           return str.contains(".");
       } catch (NumberFormatException e) {
		   return false;
	   }
   }
   
   /**
    * 验证是否是数字类型
    * @param str
    * @return
    */
   public static boolean isNumber(String str){
	   return isInteger(str) || isDouble(str);
   }
   
   // 验证是否是时间类型
   public static boolean isDate(String str){
		try {
		   SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		   sdf.setLenient(false);
		   sdf.parse(str);
		   return true;
		} catch (ParseException e) {
			return false;
		}
   }
   
   
   
   public static void main(String[] args) {

	   
//	   System.out.println(doubleAdd(2513.1211d, -1843.1310d));
	   
   }
   
   
   /**
    * 分割字符串，按照num长度进行分割成字符串数组
    * @param str
    * @param num
    */
   public static List<String> splitStr(String str, int num){
	   str = str.replace(" ", "");
	   List<String> strs = new ArrayList<String>();
	   
	   if(num<1){
		   return null;
	   }
	   if(str.length()<=num){
		   strs.add(str);
	   }else{
		   int stop = str.length() - num + 1;
		   for(int i=0;i<stop;i++){
			   String tmpStr = str.substring(i,i+num);
			  strs.add(tmpStr);
		   }
	   }
	   
	   return strs;
   }
   
   public static String getRandomNum(int num){
	   double value = (Math.random() * 9 + 1) * Math.pow(10d, Double.valueOf(num - 1));
	   DecimalFormat decimalFormat = new DecimalFormat("0");
	   return decimalFormat.format(value);
   }
   
   public static double doubleAdd(Double... doubleVal){
	   double result = 0d;
	   for(int i=0; i<doubleVal.length; i++){
		   result += doubleVal[i];
	   }
	   BigDecimal b = new BigDecimal(result);
       b = b.setScale(2, BigDecimal.ROUND_HALF_UP);
       result = b.doubleValue();
	   return result;
   }
   
   public static double doubleMul(double... doubleVal){
	   double result = 1d;
	   for(int i=0; i<doubleVal.length; i++){
		   result = result * doubleVal[i];
	   }
	   BigDecimal b = new BigDecimal(result);
       b = b.setScale(2, BigDecimal.ROUND_HALF_UP);
       result = b.doubleValue();
	   return result;
   }

	/** 
    * 获取请求主机IP地址,如果通过代理进来，则透过防火墙获取真实IP地址; 
    *  
    * @param request 
    * @return 
    */
   public static String getIpAddress(HttpServletRequest request)  {
       String ip = request.getHeader("X-Forwarded-For");
       if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
           if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
               ip = request.getHeader("Proxy-Client-IP");  
           }
           if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
               ip = request.getHeader("WL-Proxy-Client-IP");  
           }
           if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
               ip = request.getHeader("HTTP_CLIENT_IP");  
           }
           if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
               ip = request.getHeader("HTTP_X_FORWARDED_FOR");
           }
           if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
               ip = request.getRemoteAddr();
           }
       }
       return ip;  
   }





}
