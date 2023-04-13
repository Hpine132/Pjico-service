package utils.soa;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;

public class Crypt {
	  Cipher ecipher;
	  Cipher dcipher;
	  private static final String AES = "AES/GCM/NoPadding";
	  
	  public Crypt(SecretKey key) throws Exception {
		    //ecipher = Cipher.getInstance("AES");
		    //dcipher = Cipher.getInstance("AES");
		  	ecipher = Cipher.getInstance(AES);
		  	dcipher = Cipher.getInstance(AES);
		    ecipher.init(Cipher.ENCRYPT_MODE, key);
		    dcipher.init(Cipher.DECRYPT_MODE, key);
	  }
	  public String encrypt(String Data) throws Exception {
		    // Encode the string into bytes using utf-8
		    byte[] utf8 = Data.getBytes("UTF8");
		    // Encrypt
		    byte[] enc = ecipher.doFinal(utf8);
		    // Encode bytes to base64 to get a string
		    return utils.soa.Base64.encodeBytes(enc);
	  }

	public String decrypt(String DataEcrypt) throws Exception {
		// Decode base64 to get bytes
        byte[] dec = utils.soa.Base64.decode(DataEcrypt);
        byte[] utf8 = dcipher.doFinal(dec);
        // Decode using utf-8
        return new String(utf8, "UTF8");
	}
}
