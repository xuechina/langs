package com.avaj.interview;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/*
 * ��һ�������У��ҳ����ִ����������ֵ����һ���������
 * 
 */
public class ArrayTest {

	/**
	 * ����һ��ʹ��HashMap
	 *   ˼·��1������һ��map����;
	 *   2����int�����е�Ԫ�������ӽ�Map�����У�key�洢Ԫ�أ�value�洢����������ӹ��������Ԫ���Ѿ����ڣ��򽫸�Ԫ�ض�Ӧ��valueֵ�������ٴ���;
	 *   3����������ȡ�����ִ�����࣬����ֵ����Ԫ��
	 */

	void findElement(int[] array){
        Map<Integer,Integer> map =new HashMap<Integer,Integer>();
        for(int x = 0;x<array.length;x++){
                Integer value = map.get(array[x]);
                if(value==null){
                  map.put(array[x],1);        
                }
                else
                  map.put(array[x], ++value);
        }
        /*
        Set<Integer> set = map.keySet();
        Iterator<Integer> iterator = set.iterator();
        while(iterator.hasNext()){
                Integer key = iterator.next();
                int count = map.get(key);
                System.out.println(key+"������"+count+"��");
        }
        */
        Collection<Integer> coll = map.values();
        int result = Collections.max(coll);
        int value = map.get(result);
	}
   
        /**
    	 * �����������������������
    	 * ����˵��������������0��Ԫ��ʱ����ʣ�µ�Ԫ���ٱ���������û����ͬ��Ԫ�ء�ͬʱ��¼Ԫ��ֵ�����������г��ֵĴ���
    	 */
        void findElement2(int[] array){
        	//Ԫ�أ����ֵĴ���
        	int element = array[0], times = 1;
        	for (int i = 0; i < array.length; i++) {
				int tempElement = array[i];
				int tempTimes = 1;
				for (int j = 0; j < array.length; j++) {
					if (array[j] == tempElement) {
						tempTimes++;
					}
					if(tempTimes > times){
						element = tempElement;
					}else if(tempTimes == times){
						element = tempElement > element ? tempElement : element;
					}
				}
			}
        }
}
