package mageimportimg;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class MageFileParser
{
	private static final String IN_CSV_PATH = 
		"C:\\source\\MageImportImg\\data\\magetest\\images\\catalog_product_mage_16.csv";
	
	private Map<String, Product> _mapProduct;
	private Map<String, Integer> _mapAttName;
	private List<String> _lstAttName;
	private String _lastSku;
	
	MageFileParser()
	{
		_mapProduct = new LinkedHashMap<String, Product>();
		_mapAttName = new LinkedHashMap<String,Integer>();
		_lstAttName = new ArrayList<String>();
	}
	
	void parse() throws IOException
	{
		BufferedReader reader = new BufferedReader(
				   new InputStreamReader(
		                      new FileInputStream(IN_CSV_PATH), "UTF8"));
		//BufferedReader reader = new BufferedReader(new FileReader(IN_CSV_PATH));
		String line = null;
		boolean firstLine = true;
        while ((line=reader.readLine()) != null) 
        {
        	if (firstLine)
        	{
        		firstLine = false;
        		parseHeader(line);
        		continue;
        	}
        	parseProduct(line);
        	
        }
	}
	
	private void parseHeader(String line)
	{
		String[] arr = line.split(",", -1);
		for (int i=0; i<arr.length; i++)
		{
			_mapAttName.put(arr[i], i);
			//System.out.println(arr[i]);	
		}
		_lstAttName.addAll(_mapAttName.keySet());
	}

	private int getAttIndex(String attName)
	{
		return _mapAttName.get(attName);
	}
	
	private String getMediaImg(String[] line)
	{
		return line[getAttIndex(Product.MEDIA_IMG_ATT)].trim();
	}
	
	private String getSku(String[] line)
	{
		return line[getAttIndex(Product.SKU_ATT)].trim();
	}
	
		
	private void parseProduct(String line)
	{
		//http://stackoverflow.com/questions/1757065/java-splitting-a-comma-separated-string-but-ignoring-commas-in-quotes
		// split commas that are not withing quotes
		String[] arr = line.split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)");
		if (arr[0].length() == 0)
		{
			String img = getMediaImg(arr);
			_mapProduct.get(_lastSku).addAddImg(img);
			return;
		}
		Product p = new Product();
		for (int i=0; i<arr.length; i++)
		{
			String attName = _lstAttName.get(i);
			String val     = arr[i];
			//System.out.println(attName + "->" + val);
			p.add(attName, val);
		}
		_lastSku = getSku(arr);
		System.out.println("SKU: " + _lastSku);
		_mapProduct.put(_lastSku, p);
	}
	
	Map<String, Product> getProducts()
	{
		return _mapProduct;
	}

}
