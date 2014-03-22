package com.xuek.shape.util;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.util.List;

/**
 * 缓冲区工具类
 */
public class BufferUtil {
	/**
	 * 将浮点数组转换成字节缓冲区
	 */
	public static ByteBuffer arr2ByteBuffer(float[] arr){
		ByteBuffer ibb = ByteBuffer.allocateDirect(arr.length * 4);
		ibb.order(ByteOrder.nativeOrder());
		FloatBuffer fbb = ibb.asFloatBuffer();
		fbb.put(arr);
		ibb.position(0);
		return ibb ;
	}
	
	/**
	 * 将list转换成字节缓冲区
	 */
	public static ByteBuffer list2ByteBuffer(List<Float> list){
		ByteBuffer ibb = ByteBuffer.allocateDirect(list.size() * 4);
		ibb.order(ByteOrder.nativeOrder());
		FloatBuffer fbb = ibb.asFloatBuffer();
		for(Float f : list){
			fbb.put(f);
		}
		ibb.position(0);
		return ibb ;
	}
}
