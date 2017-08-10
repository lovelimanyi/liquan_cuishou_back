package com.info.back.vo;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.charset.Charset;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

public class GzipUtil {
	public static String uncompress(byte[] bytes, String encode) {
		if(bytes==null || bytes.length==0){
			return null;
		}
		
		ByteArrayInputStream byteArrayIn=null;
		GZIPInputStream in = null;

		ByteArrayOutputStream byteArrayout = new ByteArrayOutputStream();
		PrintStream out= new PrintStream(byteArrayout);
		try {
			byteArrayIn= new ByteArrayInputStream(bytes);
			in = new GZIPInputStream(byteArrayIn);
			byte[] b = new byte[1024];
			int readlen = 0;;
			while((readlen=in.read(b))!=-1){
				out.write(b, 0, readlen);
			}
			out.flush();
			
		} catch (IOException e) {
			e.printStackTrace();
		} finally{
			try {
				byteArrayIn.close();
				in.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return new String(byteArrayout.toByteArray(),Charset.forName(encode));
		
	}
	public static byte[] compress(String str, String encode) {
		if(str==null || str.length()==0){
			return null;
		}
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		GZIPOutputStream gzip = null;
		try {
			gzip= new GZIPOutputStream(out);
			gzip.write(str.getBytes(encode));
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			try {
				gzip.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return out.toByteArray();
		
	}

	public static byte[] gzip(String data){
		ByteArrayOutputStream originalContent = new ByteArrayOutputStream();
		ByteArrayOutputStream baos = null;
		GZIPOutputStream gzipOut = null;
		try {
			originalContent.write(data.getBytes(Charset.forName("UTF-8")));
			baos = new ByteArrayOutputStream();
			gzipOut = new GZIPOutputStream(baos);
			originalContent.writeTo(gzipOut);
			gzipOut.finish();
			return baos.toByteArray();
		} catch (IOException e) {
			e.printStackTrace();
		}finally {
			try {
				originalContent.close();
				baos.close();
				gzipOut.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	public static void main(String[] args) {
		/*String str = "测试abcdddddddddddddduuuuuukkkkkkkkkkk测试";
		str = new String(Base64.encode(compress(str,"utf-8")));
		System.out.println(str);*/
//		System.out.println(uncompress(str.getBytes(),"utf-8"));

	}
}
