package src.firebase;

import com.ibm.broker.javacompute.MbJavaComputeNode;
import com.ibm.broker.plugin.MbElement;
import com.ibm.broker.plugin.MbException;
import com.ibm.broker.plugin.MbMessage;
import com.ibm.broker.plugin.MbMessageAssembly;
import com.ibm.broker.plugin.MbOutputTerminal;
import com.ibm.broker.plugin.MbUserException;

import java.io.FileInputStream;
import java.io.File;

//import java.io.IOException;
import java.util.regex.Pattern;

import com.google.auth.oauth2.AccessToken;
import com.google.auth.oauth2.GoogleCredentials;
public class FireBase extends MbJavaComputeNode {


	public void evaluate(MbMessageAssembly inAssembly) throws MbException {
		MbOutputTerminal out = getOutputTerminal("out");
		MbOutputTerminal alt = getOutputTerminal("alternate");
		Boolean status = false;
		MbMessage inMessage = inAssembly.getMessage();
		MbMessageAssembly outAssembly = null;
		
		MbMessage outMessage = new MbMessage(inMessage);
		outAssembly = new MbMessageAssembly(inAssembly, outMessage);
		MbElement RESPONSE = outMessage.getRootElement().createElementAsLastChild(MbElement.TYPE_NAME, "RESPONSE", null);
		try{
			// create new message as a copy of the input
			MbElement inputRoot = inMessage.getRootElement();
			MbElement XMLNSC = inputRoot.getFirstElementByPath("XMLNSC");

			String serviceAccountPath = (XMLNSC.getFirstElementByPath("FireBase/service/AccountPath") == null) ? "": XMLNSC.getFirstElementByPath("FireBase/service/AccountPath").getValue().toString();
//			String serviceAccountPath = ".\\file\\vba-test-2aef2-firebase-adminsdk-7bi0q-49db06fff2.json";
			String MESSAGING_SCOPE = (XMLNSC.getFirstElementByPath("FireBase/service/MessagingScope") == null) ? "": XMLNSC.getFirstElementByPath("FireBase/service/MessagingScope").getValue().toString();
		
			String[] SCOPES = { MESSAGING_SCOPE };
			
			File filetest = new File(serviceAccountPath);
			FileInputStream fileInputStream = new FileInputStream(filetest);
//			FileInputStream fileInputStream = new FileInputStream(serviceAccountPath);
			GoogleCredentials credentials = GoogleCredentials.fromStream(fileInputStream);
			credentials = credentials.createScoped(SCOPES);
			AccessToken accessToken = credentials.refreshAccessToken();
			
			String TokenValue = null;
			TokenValue = accessToken.getTokenValue();
			MbElement TOKENKEY_RESPONSE = RESPONSE.createElementAsLastChild(MbElement.TYPE_NAME_VALUE, "TOKENKEY_RESPONSE", TokenValue.toString());
//			MbElement TOKENKEY = RESPONSE.createElementAsLastChild(MbElement.TYPE_NAME, "TOKENKEY", null);
			
//			checkValue(TOKENKEY_RESPONSE);
			if (TOKENKEY_RESPONSE == null){
				RESPONSE.createElementAsLastChild(MbElement.TYPE_NAME_VALUE, "ERROR_DESC", "Received Token Timeout");
			}
			else {
//				TOKENKEY.createElementAsLastChild(MbElement.TYPE_NAME_VALUE, "TokenKey", accessToken.getTokenValue());
				status = true;
			}
		}catch (MbException e) {
			// Re-throw to allow Broker handling of MbException
			throw e;
		} catch (RuntimeException e) {
			// Re-throw to allow Broker handling of RuntimeException
			throw e;
		} catch (Exception e) {
			// Consider replacing Exception with type(s) thrown by user code
			// Example handling ensures all exceptions are re-thrown to be handled in the flow
			System.out.println(e.getMessage());
			throw new MbUserException(this, "evaluate()", "", "", e.toString(),
					null);
		}
		if (status = true ){
			out.propagate(outAssembly);
		}else {
			alt.propagate(outAssembly);
		}
	}
	private String checkValue(String value){ 
		if(value == null) return value= "";
		return value;
	}
	public  String CleanInvalidXmlChars(String input) 
	{ 
		if (input == null)
		{
			return "";
		}
	    // From xml spec valid chars: 
	    // #x9 | #xA | #xD | [#x20-#xD7FF] | [#xE000-#xFFFD] | [#x10000-#x10FFFF]     
	    // any Unicode character, excluding the surrogate blocks, FFFE, and FFFF. 
		Pattern p = Pattern.compile("[^\\u0009\\u000A\\u000D\\u0020-\\uD7FF\\uE000-\\uFFFD\\u10000-\\u10FFF]+"); 
		return p.matcher(input).replaceAll("");
	}
	private String getValue(MbElement body, String path) {
		String result = null;
		try {
			if (body.getFirstElementByPath(path) != null) {
				result = body.getFirstElementByPath(path).getValueAsString();
				if (result == null) {
					result = "";
				}
			}
		} catch (MbException e) {
			// TODO Auto-generated catch block
			return "";
		}
		return result;
	}

}
