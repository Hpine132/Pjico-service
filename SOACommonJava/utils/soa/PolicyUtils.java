package utils.soa;

import com.ibm.broker.plugin.MbException;
import com.ibm.broker.plugin.MbPolicy;


public class PolicyUtils {
	
	public static String getPolicy(String policy, String property) {	
		String result = null;
		try {
			MbPolicy myPol = MbPolicy.getPolicy("UserDefined", policy);
			result = myPol.getPropertyValueAsString(property);
		} catch (MbException e) {
			// TODO Auto-generated catch block
			return null;
		}
		return result;
	}
}
