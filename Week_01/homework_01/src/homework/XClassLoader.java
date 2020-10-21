package homework;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URISyntaxException;

public class XClassLoader extends ClassLoader{
	public static void main(String args[])  {
		Class<?> tClass;
		try {
			tClass = new XClassLoader().findClass("Hello");
			Object hello=tClass.newInstance();
			Method method = tClass.getDeclaredMethod("hello");
			method.invoke(hello);
		} catch (ClassNotFoundException | InstantiationException |
				 IllegalAccessException |NoSuchMethodException | SecurityException | 
			  IllegalArgumentException | InvocationTargetException e) {
			e.printStackTrace();
		}

		 
	}
	
	
	protected Class<?>findClass(String name) throws ClassNotFoundException{
			byte []bytes=new byte[1024];
			try {
				//获取到Hello.xlass文件
				File file = new File( this.getClass().getClassLoader().getResource("Hello.xlass").toURI().getPath());
				FileInputStream fis = new FileInputStream(file);
				ByteArrayOutputStream bos = new ByteArrayOutputStream();

				int len=-1;
				while((len=fis.read(bytes))!=-1) {
					 bos.write(bytes, 0, len);
				}
				//转成字节数组
				bytes=bos.toByteArray();

				//转换
				for(int i=0;i<bytes.length;i++) {
					bytes[i]=(byte) (255-bytes[i]);
				}
				fis.close();
				bos.close();
			} catch (URISyntaxException | IOException e1) {
				 return super.findClass(name);
			}

			return defineClass(name, bytes, 0, bytes.length);
	}
	
}

