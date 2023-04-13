package utils.ldap;

import java.util.Hashtable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.InitialDirContext;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;
import utils.soa.Crypt;

public class LDAP {

	//static String ldapServer = CSUtil.getValue("SOAConfig","LDAPServer");
	//private static String DIRECTORY_SERVER = "ldap://10.0.58.101:389/";
	//private static String DIRECTORY_USER = "uid=ebanking,ou=proxy,ou=user,dc=vpb,dc=com,dc=vn";
	//private static String DIRECTORY_PASSWORD = "123456a@";
	//private static String BASE_DN = "dc=test,dc=agri,dc=com,dc=vn";
	
	private static String ORC_PATTERN = "[\\000]*";
	private static String SAMACOUNTNAME = "sAMAccountName";
	private static String IGNORE = "ignore";

	public static String authenLDAPEx(String directyServer,String userName, String password){
		InitialDirContext context = null;
		String status = null;
		try {
			//String linkLdap = DIRECTORY_SERVER;
			context = connect(directyServer, userName,password);
			context.close();
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
			String errCode = getErrorCode(e.toString());
			status = errCode +"#" + e.toString();
            Pattern pattern = null;
            Matcher matcher = null;
            pattern = Pattern.compile(ORC_PATTERN);
            matcher = pattern.matcher(status);
            if (matcher.find()) {
            	status = matcher.replaceAll("");
            }
			return status;
		}
		status = "0";
		return status;
	}
	public static String authenLDAPEx(String directyServer, String Ldapkey, String userName, String password){
		InitialDirContext context = null;
		String status = null;
		try {
			SecretKey key = new SecretKeySpec(Ldapkey.getBytes(), "AES");
			try {
				Crypt encrypter = new Crypt(key);
				password = encrypter.decrypt(password);
			} catch (Exception e) {
				status = "999#DecryptEx: "+e.toString();
				return status;
			}
			//String linkLdap = DIRECTORY_SERVER;
			context = connect(directyServer, userName, password);
			context.close();
		} catch (NamingException e) {
			//e.printStackTrace();
			String errCode = getErrorCode(e.toString());
			status = errCode +"#" + e.toString();
            Pattern pattern = null;
            Matcher matcher = null;
            pattern = Pattern.compile(ORC_PATTERN);
            matcher = pattern.matcher(status);
            if (matcher.find()) {
            	status = matcher.replaceAll("");
            }
			return status;
		}
		status = "0";
		return status;
	}
	
	public static String authenLDAPSEx(String directyServer,String userName, String password){
		InitialDirContext context = null;
		String status = null;
		try {
			//String linkLdap = DIRECTORY_SERVER;
			context = connectLDAPS(directyServer, userName,password);
			context.close();
		} catch (NamingException e) {
			// e.printStackTrace();
			String errCode = getErrorCode(e.toString());
			status = errCode +"#" + e.toString();
            Pattern pattern = null;
            Matcher matcher = null;
            pattern = Pattern.compile(ORC_PATTERN);
            matcher = pattern.matcher(status);
            if (matcher.find()) {
            	status = matcher.replaceAll("");
            }
			return status;
		}
		status = "0";
		return status;
	}
	
	public static Long authenLDAP(String directyServer,String userName, String password){
		InitialDirContext context = null;
		try {
			//String linkLdap = DIRECTORY_SERVER;
			context = connect(directyServer, userName,password);
			context.close();
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
			return new Long(0);
		}
		return new Long(1);
	}
	
	public static Long checkUserLDAPS (String directyServer, String baseDN,  String userAdmin, String passAdmin, String username) {
		InitialDirContext context;
        try {
            context = connectLDAPS(directyServer, userAdmin, passAdmin);

            SearchControls ctls = new SearchControls();
            //ctls.setReturningObjFlag (true);
            ctls.setReturningObjFlag (false);

            String returnings[] = new String[2];
            //Configure which element should be return
            returnings[0] = "cn";
            returnings[1] = "mail";
            ctls.setReturningAttributes(returnings);
            //Configure scope of searching
            ctls.setSearchScope(SearchControls.SUBTREE_SCOPE);

            String searchFilter = SAMACOUNTNAME + "=" + username; // filtro trivial
           // String searchFilter = "(sAMAccountName=1000A01)";

            NamingEnumeration<?> answer = context.search(baseDN,searchFilter, ctls);     
            SearchResult result = (SearchResult) answer.next(); // Sabemos que habra un solo resultado
            result.getAttributes().get("cn").get().toString();
            //String field2 = result.getAttributes().get("mail").get().toString();
            context.close();
        } catch (Exception e) {
                // TODO Auto-generated catch block
                // e.printStackTrace();
                return new Long(0);
        }
        return new Long(1);
	}
	
	public static String checkUserLDAP (String directyServer,String Ldapkey, String baseDN,  String userAdmin, String passAdmin, String username) {
		InitialDirContext context;
		String status = null;
        try {
        	
        	SecretKey key = new SecretKeySpec(Ldapkey.getBytes(), "AES");
			try {
				Crypt encrypter = new Crypt(key);
				passAdmin = encrypter.decrypt(passAdmin);
			} catch (Exception e) {
				status = "999#DecryptEx: "+e.toString();
				return status;
			}
            context = connect(directyServer, userAdmin, passAdmin);

            SearchControls ctls = new SearchControls();
            //ctls.setReturningObjFlag (true);
            ctls.setReturningObjFlag (false);

            String returnings[] = new String[2];
            //Configure which element should be return
            returnings[0] = "cn";
            returnings[1] = "mail";
            ctls.setReturningAttributes(returnings);
            //Configure scope of searching
            ctls.setSearchScope(SearchControls.SUBTREE_SCOPE);

            String searchFilter = SAMACOUNTNAME + "=" + username; // filtro trivial
           // String searchFilter = "(sAMAccountName=1000A01)";

            NamingEnumeration<SearchResult> answer = context.search(baseDN,searchFilter, ctls);     
            SearchResult result = (SearchResult) answer.next(); // Sabemos que habra un solo resultado
            result.getAttributes().get("cn").get().toString();
            //String field2 = result.getAttributes().get("mail").get().toString();
            context.close();
        } catch (Exception e) {
                // TODO Auto-generated catch block
                // e.printStackTrace();
                String errCode = getErrorCode(e.toString());
    			status = errCode +"#" + e.toString();
                Pattern pattern = null;
                Matcher matcher = null;
                pattern = Pattern.compile(ORC_PATTERN);
                matcher = pattern.matcher(status);
                if (matcher.find()) {
                	status = matcher.replaceAll("");
                }
    			return status;
        }
        status = "1";
        return status;
	}
	
	public static String checkUserLDAP (String directyServer, String baseDN,  String userAdmin, String passAdmin, String username) {
		InitialDirContext context;
		String status = null;
        try {
        	
            context = connect(directyServer, userAdmin, passAdmin);

            SearchControls ctls = new SearchControls();
            //ctls.setReturningObjFlag (true);
            ctls.setReturningObjFlag (false);

            String returnings[] = new String[2];
            //Configure which element should be return
            returnings[0] = "cn";
            returnings[1] = "mail";
            ctls.setReturningAttributes(returnings);
            //Configure scope of searching
            ctls.setSearchScope(SearchControls.SUBTREE_SCOPE);

            String searchFilter = SAMACOUNTNAME + "=" + username; // filtro trivial
           // String searchFilter = "(sAMAccountName=1000A01)";

            NamingEnumeration<?> answer = context.search(baseDN,searchFilter, ctls);     
            SearchResult result = (SearchResult) answer.next(); // Sabemos que habra un solo resultado
            result.getAttributes().get("cn").get().toString();
            //String field2 = result.getAttributes().get("mail").get().toString();
            context.close();
        } catch (Exception e) {
                // TODO Auto-generated catch block
                // e.printStackTrace();
                String errCode = getErrorCode(e.toString());
    			status = errCode +"#" + e.toString();
                Pattern pattern = null;
                Matcher matcher = null;
                pattern = Pattern.compile(ORC_PATTERN);
                matcher = pattern.matcher(status);
                if (matcher.find()) {
                	status = matcher.replaceAll("");
                }
    			return status;
        }
        status = "1";
        return status;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private static InitialDirContext connect(String url, String username,
			String password) throws NamingException {
		Hashtable env = new Hashtable();
		env.put(Context.INITIAL_CONTEXT_FACTORY,"com.sun.jndi.ldap.LdapCtxFactory");
		env.put(Context.PROVIDER_URL, url);
		env.put(Context.REFERRAL, IGNORE);
		env.put(Context.REFERRAL, IGNORE);
		if (username != null && !username.isEmpty()) {
			env.put(Context.SECURITY_AUTHENTICATION, "simple");
			env.put(Context.SECURITY_PRINCIPAL, username);
			env.put(Context.SECURITY_CREDENTIALS, password);
		}
		return new InitialDirContext(env);
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private static InitialDirContext connectLDAPS(String url, String username,
			String password) throws NamingException {
		Hashtable env = new Hashtable();
		env.put(Context.INITIAL_CONTEXT_FACTORY,"com.sun.jndi.ldap.LdapCtxFactory");
		env.put(Context.PROVIDER_URL, url);
		env.put(Context.SECURITY_PROTOCOL, "ssl");
		env.put(Context.REFERRAL, IGNORE);
		env.put(Context.REFERRAL, IGNORE);
		if (username != null && !username.isEmpty()) {
			env.put(Context.SECURITY_AUTHENTICATION, "simple");
			env.put(Context.SECURITY_PRINCIPAL, username);
			env.put(Context.SECURITY_CREDENTIALS, password);
		}
		return new InitialDirContext(env);
	}

	private static String getErrorCode(final String exceptionMsg)
    {
        if (exceptionMsg.contains("LDAP"))
        {
            String [] listInfo = exceptionMsg.split(",");
            String errorCode = "";
            for (String infoErr : listInfo)
            {
                if (infoErr.contains("LDAP: error code"))
                {
                    String pattern="-?\\d+";
                    Pattern p=Pattern.compile(pattern);
                    Matcher  m=p.matcher(exceptionMsg);
                    if (m.find()) {
                        errorCode = m.group(0);
                    }
                }
                if (infoErr.contains("data"))
                {
                    String subErr = infoErr.replace("data", "").trim();
                    errorCode += "/" + subErr;
                }
                
            } 
            return errorCode;
        }
        return "999";
    }
}
