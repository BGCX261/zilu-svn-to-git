
package com.zilu.util.file;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;


public class DBKeyCreator {
   
	
    static StringBuffer randomPool = new StringBuffer("ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789");
    static char[] arrayPool = new char[randomPool.length()];
    static {
        for(int i = 0 ; i < randomPool.length() ; i ++){
            arrayPool[i] = randomPool.charAt(i);
        }
    }
    
    public DBKeyCreator() {
    }
    
    /*
    private static void exchangePoolData(int position){
        char tmp = arrayPool[position];
        arrayPool[position] = arrayPool[arrayPool.length - 1];
        arrayPool[arrayPool.length - 1] = tmp;
    }*/
    
    private static void exchangePoolData(int position){
        char tmp = arrayPool[0];
        for(int i = 1 ; i <= position ; i++){
        	arrayPool[i - 1] = arrayPool[i];
        }
        arrayPool[position] = tmp;
    }
    
    public static String getRandomKey(int keyLength){
    	int keyLengthTmp= keyLength;
    	
        int nt = 0;
        StringBuffer theKeyBuffer = new  StringBuffer();
        
        
        int ns = (int)(Math.random() * 1000000) << 6;
        //System.out.println(ns);
        while(ns > 0 && keyLengthTmp > 1){
            nt = ns % 36;
            ns = (int)(ns / 7);
            theKeyBuffer.append(arrayPool[nt]);
            exchangePoolData(nt);
            keyLengthTmp--;
        }
        
        
        ns = (int)System.currentTimeMillis() << 20;
        while(ns > 0 &&  keyLengthTmp > 1){
            nt = ns % 36;
            ns = (int)(ns / 7);
            theKeyBuffer.append(arrayPool[nt]);
            exchangePoolData(nt);
            keyLengthTmp--;
        }
        
        Calendar calendar = new GregorianCalendar();
        Date now = new Date();
        calendar.setTime(now);
        ns = calendar.get(Calendar.YEAR) * 365 + calendar.get(Calendar.MONTH) * 30 + calendar.get(Calendar.DATE);
        while(ns > 0 &&  keyLengthTmp > 1){
            nt = ns % 36;
            ns = (int)(ns / 7);
            theKeyBuffer.append(randomPool.substring(nt , nt + 1));
            keyLengthTmp--;
        }
        
        while(theKeyBuffer.length() < keyLength ){
        	nt = (int)(Math.random() * 36);
        	theKeyBuffer.append(arrayPool[nt]);
        }
        
        return theKeyBuffer.toString();
    }
    
    public static String getRandomNum(int size) {
    	if (size > 8) {
    		return getRandomNum(8) + getRandomNum(size-8);
    	}
    	int u = (int)Math.pow(10, size);
    	int i = (int)(Math.random()*u);
    	String a = String.valueOf(i);
    	StringBuilder sb = new StringBuilder();
    	int l = size - a.length();
    	for (int x =0 ; x < l;x++) {
    		sb.append("0");
    	}
    	sb.append(a);
    	return sb.toString();
    }
    /**
     * @param args
     */
    public static void main(String[] args) {
    	System.out.println(Math.pow(10, 3));
       // HashSet keySet = new HashSet();
       //do{
            long begin = System.currentTimeMillis();
            for(int i = 0 ; i < 400; i ++){
                String myKey = DBKeyCreator.getRandomNum(32);
                System.out.println(myKey);
            }
            long end = System.currentTimeMillis();
            System.out.print((end - begin) / 1000.0);
            System.out.println("   OK!");
        //    keySet.clear();
        //}while(result);
    }

    
}
