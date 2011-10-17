package mageimportimg;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Product
{
	static final String SKU_ATT       = "sku";
	static final String NAME_ATT      = "name";
	static final String IMG_ATT       = "image";
	static final String IMG_SMALL_ATT = "small_image";
	static final String IMG_THUMB_ATT = "thumbnail";
	static final String MEDIA_IMG_ATT = "_media_image";
	static final String MEDIA_POS_ATT = "_media_position";
	static final String MEDIA_DIS_ATT = "_media_is_disabled";
	static final String MEDIA_ATT_ID_ATT = "_media_attribute_id";

	
	private Map<String,String> _map;
	private List<String> _lstAddImg;
	
	Product()
	{
		_map       = new LinkedHashMap<String,String>();
		_lstAddImg = new ArrayList<String>();
	}

	Product(Product p)
	{
		_map       = new LinkedHashMap<String,String>(p._map);
		_lstAddImg = new ArrayList<String>(p._lstAddImg);
	}
	
	void add(String attName, String attVal)
	{
		_map.put(attName, attVal);
	}
	
	void addAddImg(String img)
	{
		_lstAddImg.add(img);
	}
	
	String getImg()
	{
		return _map.get("image");
	}
	
	String getMainAddImg()
	{
		return _map.get("_additional_image");
	}
	
	List<String> getSecondAddImg()
	{
		return _lstAddImg;
	}
	String getSku()
	{
		return _map.get(SKU_ATT);
	}
	String toCSVAttName()
	{
		StringBuffer sb = new StringBuffer();
		int i=0;
		int size = _map.size();
		for(String key : _map.keySet())
		{
			sb.append(key);
			if (i<size-1)
			{
				sb.append(",");
			}
			i++;
		}
		sb.append("\n");
		return sb.toString();
	}
	
	String toCSV()
	{
		StringBuffer sb = new StringBuffer();
		int size = _map.size();
		int i = 0;
		for(Map.Entry<String, String> entry : _map.entrySet())
		{
			sb.append(entry.getValue());
			if (i < size-1)
				sb.append(",");
			i++;
		}
		sb.append("\n");
		if (_lstAddImg.size() > 0)
		{
			StringBuffer sbExtra = new StringBuffer();
			
			for (int img=0; img<_lstAddImg.size(); img++)
			{	
				i=0;
				for(Map.Entry<String, String> entry : _map.entrySet())
				{
					if(Product.MEDIA_ATT_ID_ATT.equals(entry.getKey()))
					{
						sbExtra.append("80");
					}
					else if(Product.MEDIA_IMG_ATT.equals(entry.getKey()))
					{
						sbExtra.append(_lstAddImg.get(img));
					}
					else if(Product.MEDIA_POS_ATT.equals(entry.getKey()))
					{
						sbExtra.append((img+2));
					}
					else if(Product.MEDIA_DIS_ATT.equals(entry.getKey()))
					{
						sbExtra.append("0");
					}
					if (i < size-1)
						sbExtra.append(",");
					i++;					
				}
				sbExtra.append("\n");
			}
			sb.append(sbExtra.toString());
		}

		return sb.toString();
	}
}
