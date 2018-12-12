package com.warmnut.support;

import java.util.HashMap;
import java.util.List;

import com.warmnut.bean.S_Dict;
import com.warmnut.repository.S_DictRepository;


public class DictMap {
   
    private static HashMap<String, String> hashMap = new HashMap<>();

     //从数据库中取值放入到HashMap中
     public static void queryDic(S_DictRepository dicDao){
           List<S_Dict> dics = dicDao.findAll();
           StringBuilder sb = new StringBuilder();
           for(int i=0;i<dics.size();i++){
        	   S_Dict dic = dics.get(i);
               String tableName = dic.getTableName();
               String fieldName = dic.getFieldName();
               int fieldValue = dic.getKey();
               String key = tableName+"_"+fieldName+"_"+fieldValue;
               String value = dic.getValue();              
               hashMap.put(key,value);
           }
       }



    public static String getFieldDetail(String tableName,String fieldName,int fieldValue){
        StringBuilder sb = new StringBuilder();
        StringBuilder keySb = sb.append(tableName).append("_").append(fieldName).append("_").append(fieldValue);
        String key = keySb.toString();
        String value = hashMap.get(key);
        return value;
    }
}