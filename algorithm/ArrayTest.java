package com.avaj.interview;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/*
 * 在一个数组中，找出出现次数最多且数值最大的一个数并输出
 * 
 */
public class ArrayTest {

	/**
	 * 方法一：使用HashMap
	 *   思路：1、创建一个map集合;
	 *   2、将int数组中的元素逐个添加进Map集合中，key存储元素，value存储次数，在添加过程中如果元素已经存在，则将该元素对应的value值自增后再存入;
	 *   3、遍历集合取出出现次数最多，且数值最大的元素
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
                System.out.println(key+"出现了"+count+"次");
        }
        */
        Collection<Integer> coll = map.values();
        int result = Collections.max(coll);
        int value = map.get(result);
	}
   
        /**
    	 * 方法二：对数组做两层遍历
    	 * 举例说明：当遍历到第0个元素时，对剩下的元素再遍历，看有没有相同的元素。同时记录元素值和它在数组中出现的次数
    	 */
        void findElement2(int[] array){
        	//元素，出现的次数
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
