package utils.soa;

import java.util.Vector;
import com.ibm.broker.plugin.MbException;
import com.ibm.broker.plugin.MbElement;
import com.ibm.broker.plugin.MbGlobalMap;

public class CacheUtil {

	public static void getValue(String strMapName, String strKey, MbElement[] mbElement) {
	
		MbGlobalMap globalMap = null;
		try {
		
			globalMap = strMapName == null ? MbGlobalMap.getGlobalMap() : MbGlobalMap.getGlobalMap(strMapName);
			@SuppressWarnings("unchecked")
			Vector<String[]> vectorValue = (Vector<String[]>) globalMap.get(strKey);
			if (vectorValue == null) {
				mbElement = null;
			} else {
				int size = vectorValue.size();
				for (int i = 0; i < size; i++) {
					String name = vectorValue.get(i)[0];
					String value = vectorValue.get(i)[1];
					mbElement[0].createElementAsLastChild(MbElement.TYPE_NAME_VALUE).setName(name);
					mbElement[0].getLastChild().setValue(value); 
				}
			}
		} catch (MbException mbe) {
			mbElement = null;
			mbe.printStackTrace();
		}
	}
	
	@SuppressWarnings("unchecked")
	public static void getAllValue(String strMapName, MbElement[] mbElement) {
		MbGlobalMap globalMapKey = null;
		try {
		
			globalMapKey = strMapName == null ? MbGlobalMap.getGlobalMap() : MbGlobalMap.getGlobalMap("KEY");
			Vector<String> vectorValue = (Vector<String>) globalMapKey.get(strMapName);
			mbElement[0].createElementAsFirstChild(MbElement.TYPE_NAME_VALUE).setName("MapName");
			mbElement[0].getFirstChild().setValue(strMapName);
			mbElement[0].createElementAsFirstChild(MbElement.TYPE_NAME_VALUE).setName("NumberOfEntry");
			
			if (vectorValue == null) {
				mbElement[0].getFirstChild().setValue(0);
			} else {
				mbElement[0].getFirstChild().setValue(vectorValue.size());
				for (String key : vectorValue) {
					mbElement[0].createElementAsLastChild(MbElement.TYPE_NAME_VALUE).setName("Key");
					mbElement[0].getLastChild().setValue(key);
					mbElement[0].createElementAsLastChild(MbElement.TYPE_NAME_VALUE).setName("Value");
					MbElement[] mbElementTmp = new  MbElement[1];
					mbElementTmp[0]=mbElement[0].getLastChild();
					getValue(strMapName,key,mbElementTmp);			
				}
			}
		} catch (MbException mbe) {
			mbElement = null;
			mbe.printStackTrace();
		}
	}

	public static Boolean setValue(String mapName, String key, MbElement value) {
		
		MbGlobalMap globalMap = null;
		Vector<String[]> list = new Vector<String[]>();
		try {
			MbElement item = value.getFirstChild();
			while (item != null) {
				String[] arrayTmp = new String[2];
				arrayTmp[0] = item.getName();
				arrayTmp[1] = item.getValueAsString();
				list.add(arrayTmp);
				item = item.getNextSibling();
			}
			globalMap = mapName == null ? MbGlobalMap.getGlobalMap() : MbGlobalMap.getGlobalMap(mapName);
			
			if(!addUpdateKey(mapName,key))
				return false;
			
			if (globalMap.containsKey(key)) {
				globalMap.update(key, list);
			} else {
				globalMap.put(key, list);
			}
		} catch (MbException mbe) {
			mbe.printStackTrace();
			return Boolean.FALSE;
		}
		return Boolean.TRUE;
	}
	
	@SuppressWarnings("unchecked")
	public static Boolean removeAllValue(String mapName) {
		MbGlobalMap globalMapKey = null;
		MbGlobalMap globalMapCache = null;
		try{
			globalMapKey = mapName == null ? MbGlobalMap.getGlobalMap() : MbGlobalMap.getGlobalMap("KEY");
			globalMapCache = mapName == null ? MbGlobalMap.getGlobalMap() : MbGlobalMap.getGlobalMap(mapName);
			
			Vector<String> vectorKey = new Vector<String>();
			vectorKey = (Vector<String>)globalMapKey.get(mapName);
			if(vectorKey==null) return true;
			for (String elementKey : vectorKey) {
				globalMapCache.remove(elementKey);
			}
			removeKey(mapName,"ALLKEY");
		}catch(MbException mbe){
			mbe.printStackTrace();
			return Boolean.FALSE;
		}
		return Boolean.TRUE;
	}
	
	@SuppressWarnings("unchecked")
	public static Boolean addUpdateKey(String mapName, String valueKey) {
		MbGlobalMap globalMap = null;
		try{
			globalMap = mapName == null ? MbGlobalMap.getGlobalMap(): MbGlobalMap.getGlobalMap("KEY");
			Vector<String> vectorKey = null;
			vectorKey = (Vector<String>)globalMap.get(mapName);
			if(vectorKey==null) 
				vectorKey = new Vector<String>();
			vectorKey.add(valueKey);
			
			if (globalMap.containsKey(mapName)) {
				globalMap.update(mapName, vectorKey);
			} else {
				globalMap.put(mapName, vectorKey);
			}		
		}catch(MbException mbe){
			mbe.printStackTrace();
			return Boolean.FALSE;		
		}		
		return true;
	}
	
	@SuppressWarnings("unchecked")
	public static Vector<String> getListKey(String mapName, String valueKey) {
		MbGlobalMap globalMap = null;
		try{
			globalMap = mapName == null ? MbGlobalMap.getGlobalMap() : MbGlobalMap.getGlobalMap("KEY");
			Vector<String> vectorKey = new Vector<String>();
			vectorKey = (Vector<String>)globalMap.get(mapName);
			return vectorKey;
			
		}catch(MbException mbe){
			mbe.printStackTrace();
			return null;
			
		}
	}
	
	@SuppressWarnings("unchecked")
	public static Boolean removeKey(String mapName, String valueKey) {
		MbGlobalMap globalMap = null;
		try{
			globalMap = mapName == null ? MbGlobalMap.getGlobalMap() : MbGlobalMap.getGlobalMap("KEY");
			if (globalMap==null) return false;
			if(valueKey.equals("ALLKEY")){
				globalMap.remove(mapName);
				return true;
			}else{
				
				Vector<String> vectorKey = new Vector<String>();
				vectorKey = (Vector<String>)globalMap.get(mapName);
				Boolean result = vectorKey.remove(valueKey);
				return result;
			}		
		}catch(MbException mbe){
			mbe.printStackTrace();
			return Boolean.FALSE;	
		}
	}	
}