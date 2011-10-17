package mageimportimg;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Set;
import java.util.TreeSet;

public class FileImgList
{
	private Set<String> _set;
	
	FileImgList()
	{
		_set = new TreeSet<String>();
	}
	
	void readFiles()
	{
		File dir = new File(MainImport.IMG_PATH);
		try
		{
			readFiles(dir);
		}
		catch (IOException ex)
		{
			ex.printStackTrace();
		}
	}
	
	private void readFiles(File file) throws IOException
	{
		if (file.isDirectory())
		{
			File[] arrFile = file.listFiles();
			for (int i=0; i<arrFile.length; i++)
			{
				readFiles(arrFile[i]);
			}			
		}
		else
		{	
			String name = file.getName();
			if(name.endsWith(".jpg") ||
			   name.endsWith(".JPG")	)
			{
				if (name.contains("ref"))
				{
//					if (name.contains("_"))
//					{
//						String newName = name.replace("_", "a");
//						File newFile = new File(file.getParentFile().getAbsolutePath()+
//												File.separatorChar+
//												newName);
//						file.renameTo(newFile);
//						name = newName;
//					}
//					else
//					{
//						String newName = getNameNoExt(name) + "s.jpg";
//						File newFile = new File(file.getParentFile().getAbsolutePath()+
//												File.separatorChar+
//												newName);
//						
//						copyFile(file.getAbsolutePath(), newFile.getAbsolutePath());
//						_set.add(newName);
//
//						newName = getNameNoExt(name) + "t.jpg";
//						newFile = new File(file.getParentFile().getAbsolutePath()+
//												File.separatorChar+
//												newName);
//						copyFile(file.getAbsolutePath(), newFile.getAbsolutePath());
//						_set.add(newName);
//					}
				}
				_set.add(name);
			}
		}	
		
	}
	
	Set<String> getFiles()
	{
		return _set;
	}
	
	public static String getNameNoExt(String fileName)
	{
		int index = fileName.indexOf(".");
		if (index == -1)
			return fileName;
		
		return fileName.substring(0, index);
	}
	
	private void copyFile(String from, String to) throws IOException
	{
		InputStream in = new FileInputStream(from);
		OutputStream out = new FileOutputStream(to);
		byte[] buf = new byte[1024];
		int len;
		while ((len = in.read(buf)) > 0) {
			   out.write(buf, 0, len);
		}
		in.close();
		out.close();
	}
}
