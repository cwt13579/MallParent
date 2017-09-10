package com.iec.core.app.utils;


import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class RandomId {
    private Random random;
    private String table;
    private static final int radLength =15;
    private static final String[] radArr = new String[]{"A","B","C","D","E","F","G","H","I","G","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z"};
    
    
    public RandomId() {
        random = new Random();
        table = "0123456789";
    }
    public String randomId(long id) {
        String ret = null,num = String.format("%05d", id);
        int key = random.nextInt(10),seed = random.nextInt(100);
        Caesar caesar = new Caesar(table, seed);
        num = caesar.encode(key, num);
        ret = num  + String.format("%01d", key)  + String.format("%02d", seed);
        
        // 加入生成的随机字符
       /* int letterSize = radLength - ret.length();
        for(int i=0;i<letterSize;i++){
        	String letter = radArr[ random.nextInt(radArr.length)];
        	int index = random.nextInt(ret.length());
        	ret = ret.substring(0, index) + letter + ret.substring(index, ret.length());
        }*/
        
        return ret;
    }
    
    
    public static void main(String[] args) {
        RandomId r = new RandomId();
        System.out.println(r.randomId(1));
    }
}