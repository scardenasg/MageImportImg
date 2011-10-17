package mageimportimg;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

public class MainImport
{
	static final String IMG_PATH = "C:\\source\\MageImportImg\\data\\fotos";
	static final String OUT_PATH = "C:\\source\\MageImportImg\\data\\magetest\\images\\out_img_16.csv";
	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		
		try
		{
			FileImgList lst = new FileImgList();
			lst.readFiles();
			Set<String> set = lst.getFiles();
			System.out.println("Images " + set.size());
			
			MageFileParser parser = new MageFileParser();
			parser.parse();
			Map<String, Product> mapProduct = parser.getProducts();
			System.out.println("Products " + mapProduct.size());
			
			//passInfo(set, mapProduct);
			
			Map<String, Product> mapOut = passOut(set, mapProduct);
			output(mapOut);
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
	}
	
	private static void output(Map<String, Product> mapOut) throws IOException
	{
		FileOutputStream fout = new FileOutputStream(OUT_PATH);
		OutputStreamWriter outw = new OutputStreamWriter(fout, "UTF8");
		BufferedWriter buf = new BufferedWriter(outw);
		
	
		boolean first = true;
		for (Product p : mapOut.values())
		{
			if (first)
			{
				first = false;
				buf.append(p.toCSVAttName());
			}
			buf.append(p.toCSV());
		}
		buf.close();
	}
	private static Map<String, Product> passOut(Set<String> set, Map<String, Product> mapProduct)
	{
		String imgout = "/";
		Map<String, Product> mapOut = new LinkedHashMap<String, Product>();
		
		for(String img : set)
		{	
			//System.out.println(img);
			String[] arrInfo = getSku(img);
			
			if (arrInfo == null)
				continue;
			
			if (!mapProduct.containsKey(arrInfo[0]))
				continue;
			
			if (arrInfo[1] == null)
			{
				Product p = new Product(mapProduct.get(arrInfo[0]));
				
				p.add(Product.IMG_ATT, imgout+img);
				p.add(Product.IMG_SMALL_ATT, imgout+img);
				p.add(Product.IMG_THUMB_ATT, imgout+img);
				p.add(Product.MEDIA_IMG_ATT, imgout+img);
				p.add(Product.MEDIA_POS_ATT, "1");
				p.add(Product.MEDIA_DIS_ATT, "0");
				p.add(Product.MEDIA_ATT_ID_ATT, "80");
								
				mapOut.put(arrInfo[0], p);
			}
			else
			{
				Product p = mapOut.get(arrInfo[0]);
				p.addAddImg(imgout+img);
			}
			
		}
		return mapOut;
	}

	private static String getImage(String img, String extra)
	{
		return FileImgList.getNameNoExt(img) + extra + ".jpg";
	}
	private static void passInfo(Set<String> set, Map<String, Product> mapProduct)
	{
		for(String img : set)
		{
			
			//System.out.println(img);
			String[] arrInfo = getSku(img);
			String sMsg = img + "\t";
			
			if (arrInfo == null)
			{
				sMsg += " NO SKU";
			}
			else
			{
				sMsg += " " +arrInfo[0];
				if (arrInfo[1] != null)
				{
					sMsg += ", " +arrInfo[1];
				}
				String found = mapProduct.containsKey(arrInfo[0]) ? "Found" : "SKU NOT FOUND";
				sMsg += ", " +found;
			}
				
			System.out.println(sMsg);	
		}
		
	}
	private static String[] getSku(String img)
	{
		String ref = "ref";
		
		int index1 = img.indexOf(ref);
		if (index1 == -1)
			return null;
		
		String[] ret = new String[2];
		int index2 = img.indexOf("a", index1);
		if (index2 == -1)
		{
			int index3 = img.indexOf(".");
			ret[0] = img.substring(index1 + ref.length(), index3);
		}
		else
		{
			ret[0] = img.substring(index1 + ref.length(), index2);
			int index3 = img.indexOf(".");
			String val = img.substring(index2+1, index3);
			ret[1] = val;
		}
		return ret;
	}
	private static void checkImg(Set<String> setFile, String img)
	{
		String msg;
		if (setFile.contains(img.toLowerCase()))
		{
			msg = img + " -> OK";
		}
		else
		{
			msg = "\t" + img + " -> NOT FOUND";			
		}
		System.out.println(msg);
	}

}
