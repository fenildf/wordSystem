package cn.ishow.utils;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class WordCache {
	
	private List<String> cache = new LinkedList<>();
	
	private WordCache(){
		
	}
	
	public static WordCache getInstance(){
		return Inner.wordCache;
	}
	
	public void put(String word){
		cache.add(word);
	}
	
	public void putAll(List<String> words){
		cache.addAll(words);
	}
	
	//��ȡ3�����Ž��
	public String[] randThree(){
		if(cache==null||cache.size()<=5){
			cache.add("����ģ��õģ����ĵ�");
			cache.add("��������ŭ��������");
			cache.add("С��");
			cache.add("����");
			cache.add("���");
			cache.add("��֪��");
		}
		return randomList();
	}
	
	
	private String[] randomList(){
		int length = cache.size();
	    String[] result = new String[3];
	    int index = new Random().nextInt(length);
	    result[0] = cache.get(index);
	    int index2 = -1;
	    while(true){
	    	index2 = new Random().nextInt(length);
	    	if(index2!=index){
	    		result[1] = cache.get(index2);
	    		break;
	    	}
	    }
	    int index3 = -1;
	    while(true){
	    	index3 = new Random().nextInt(length);
	    	if(index3!=index2&&index3!=index){
	    		result[2] = cache.get(index3);
	    		break;
	    	}
	    }
	    
	    return result;
	}
	
	private static class Inner{
		public static WordCache wordCache = new WordCache();
	}

}
