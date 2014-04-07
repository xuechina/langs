package com.speex.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;


import android.util.Base64;

public class Base64Util
{
	public static String encode(String filename) throws Exception
	{
		File file = new File(filename);
		
		InputStream in = null;
		byte[] data = null;
		
		try
		{
			in = new FileInputStream(file);
			data = new byte[in.available()];
			
			in.read(data);
			in.close();
		} catch (Exception e)
		{
			if (in != null)
			{
				in.close();
			}
			
			throw e;
		}
		
//		BASE64Encoder encoder = new BASE64Encoder();
//		Base64.decode(data, Base64.DEFAULT);
		return Base64.encodeToString(data, Base64.DEFAULT);
	}
	
	
	public static File decode(String str, String filename) throws Exception
	{
//		BASE64Decoder decoder = new BASE64Decoder();
		
		File file = new File(filename);
		
		OutputStream out = null;
		try
		{
			if (!file.exists())
			{
				new File(file.getParent()).mkdirs();
				
				file.createNewFile();
			}
			byte[] data = Base64.decode(str, Base64.DEFAULT);
//			byte[] data = decoder.decodeBuffer(str);
			for (int i = 0; i < data.length; ++i)
			{
				if (data[i] < 0)
				{
					data[i] += 256;
				}
			}
			
			out = new FileOutputStream(filename);
			
			out.write(data);
			out.flush();
			out.close();
		} catch (Exception e)
		{
			if (out != null)
			{
				out.close();
			}
			
			file = null;
			
			throw e;
		}
		
		return file;
	}
	
	
	public static void main(String[] args)
	{
		try
		{
			String imgStr = encode("d:/flower.jpg");
			
			decode(imgStr, "d:/test.png");
			
		} catch (Exception e)
		{
			// TODO: handle exception
			e.printStackTrace();
		}
	}
}
