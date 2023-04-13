package utils.soa;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.regex.Pattern;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import com.sun.org.apache.xml.internal.security.utils.Base64;

@SuppressWarnings("deprecation")
public class SOASecurity {
	
	private static final String AES = "AES/GCM/NoPadding";
	private static final String SHA256 = "SHA-256";
	private static final String SHA1 = "SHA-1";
	
	public static String hashValue(String str) {
		byte[] byteArr = null;
		try {
			MessageDigest digest;
			digest = MessageDigest.getInstance(SHA256);
			byteArr = digest.digest(str.getBytes(StandardCharsets.UTF_8));
			return bytesToHex(byteArr);
		} catch (NoSuchAlgorithmException e) {
			// e.printStackTrace();
		}
		return "";
	}
	
	private static String bytesToHex(byte[] bytesArr) {
		if(bytesArr == null)
			return "";		
		StringBuffer result = new StringBuffer();
		for (byte b : bytesArr){
			result.append(Integer.toString((b & 0xff) + 0x100, 16).substring(1));
		}
		return result.toString();
	}
	
	public static String EncryptBasic(String inputStr, String crytoPass) {
		try {
			 byte[] inputBytes = inputStr.getBytes();
		        byte[] key = (crytoPass).getBytes("UTF-8");
		        MessageDigest sha = MessageDigest.getInstance(SHA1);
		        key = sha.digest(key);
		        key = Arrays.copyOf(key, 16); // use only first 128 bit
		        SecretKeySpec secretKeySpec = new SecretKeySpec(key, "AES");
		        // Instantiate the cipher
		        // Cipher cipher = Cipher.getInstance("AES");
		        Cipher cipher = Cipher.getInstance(AES);
		        cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);
		        byte[] encrypted = cipher.doFinal(inputBytes);
		        return Base64.encode(encrypted);
		} catch (Exception e) {
			// e.printStackTrace();
		}
       return null;
    }

	public static String DecryptBasic(String inputStr, String crytoPass) {  
		try {
			byte[] inputBytes = Base64.decode(inputStr);
	        byte[] key = (crytoPass).getBytes("UTF-8");
	        MessageDigest sha = MessageDigest.getInstance(SHA1);
	        key = sha.digest(key);
	        key = Arrays.copyOf(key, 16); // use only first 128 bit
	        SecretKeySpec secretKeySpec = new SecretKeySpec(key, "AES");
	        // Instantiate the cipher
	        //Cipher cipher = Cipher.getInstance("AES");
	        Cipher cipher = Cipher.getInstance(AES);
	        cipher.init(Cipher.DECRYPT_MODE, secretKeySpec);        
	        String decryptedStr = new String(cipher.doFinal(inputBytes));
	        return decryptedStr;
		} catch (Exception e) {
			//e.printStackTrace();
		}
        return null;
    }
    
	public static Boolean checkIP(String ipCheck, String listIPAddressAcess) {
        
        String [] listRegexIP = listIPAddressAcess.split("#");
        for (String ipRegex : listRegexIP) {
            Pattern ipPattern = Pattern.compile(ipRegex);
            if (ipPattern.matcher(ipCheck).matches())
            {
                return true;
            }
        }
        return false;
    }
	
	public static Boolean validateInput(String Input, String regex) {
        Pattern ipPattern = Pattern.compile(regex);
        if (ipPattern.matcher(Input).matches())
        {
            return true;
        }
        return false;
    }
	
	public static String maskData(String RegexString, String StringMask) {
		String afterMask =  StringMask.replaceAll(RegexString, "******");
        return afterMask;
    }
//    public static void main (String [] args){
//    	try {
//			String encode = EncryptBasic("ESB","551250");
//			String decode = null;
//			decode = DecryptBasic("tgEDBFvti3ROQhHv7JdJHw==","551250");
//			System.out.print(encode +"          " + decode);
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//    }

}
