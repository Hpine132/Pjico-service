package utils.soa;

import java.util.Vector;

import com.ibm.broker.plugin.MbElement;
import com.ibm.broker.plugin.MbException;
import com.ibm.broker.plugin.MbGlobalMap;



public class GlobalCache {
	/**
	 * Method to get a value from Global Cache using map name and key
	 */
	public static String getValue_old(String strMapName, String strKey) {

		String strValue = "";
		MbGlobalMap globalMap = null;

		try {
			globalMap = strMapName == null ? MbGlobalMap.getGlobalMap()
					: MbGlobalMap.getGlobalMap(strMapName);
			strValue = (String) globalMap.get(strKey);
			System.out.println("Global Cached\nMapName = " + strMapName
					+ " Key = " + strKey + " Value = " + strValue);
		} catch (MbException mbe) {
			System.out.println(mbe.getMessage());
			mbe.printStackTrace();
		}

		return strValue;
	}

	/**
	 * Method to update a key-value pairs for a map in Global Cache
	 */
	public static Boolean setValue_old(String mapName, String key, String value) {
		MbGlobalMap globalMap = null;
		try {
			globalMap = mapName == null ? MbGlobalMap.getGlobalMap()
					: MbGlobalMap.getGlobalMap(mapName);
			if (globalMap.containsKey(key)) {
				globalMap.update(key, value);
			} else {
				globalMap.put(key, value);
			}
		} catch (MbException mbe) {
			System.out.println(mbe.getMessage());
			mbe.printStackTrace();
			return Boolean.FALSE;
		}
		return Boolean.TRUE;
	}
	
	public static void getValue(String strMapName, String strKey, MbElement[] mbElement) {

		MbGlobalMap globalMap = null;	
		try {
			globalMap = strMapName == null ? MbGlobalMap.getGlobalMap()
					: MbGlobalMap.getGlobalMap(strMapName);
			@SuppressWarnings("unchecked")
			Vector<String[]> vectorValue = (Vector<String[]>) globalMap.get(strKey);
			if (vectorValue == null) {
				mbElement = null;
			} else {
				int size = vectorValue.size();
				for (int i = 0; i < size; i++) {
					mbElement[0].createElementAsLastChild(MbElement.TYPE_NAME_VALUE).setName(vectorValue.get(i)[0]);
					mbElement[0].getLastChild().setValue(vectorValue.get(i)[1]); 
				}
			}
		} catch (MbException mbe) {
			System.out.println(mbe.getMessage());
			mbe.printStackTrace();
		}
	}
	
}
