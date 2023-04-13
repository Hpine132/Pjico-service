package src.customer;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.text.SimpleDateFormat;
//import java.util.Date;
import java.sql.Date;

import com.ibm.broker.javacompute.MbJavaComputeNode;
import com.ibm.broker.plugin.MbElement;
import com.ibm.broker.plugin.MbException;
import com.ibm.broker.plugin.MbMessage;
import com.ibm.broker.plugin.MbMessageAssembly;
import com.ibm.broker.plugin.MbOutputTerminal;
import com.ibm.broker.plugin.MbUserException;
import com.ibm.broker.plugin.MbNode.JDBC_TransactionType;

public class InquiryCustomer extends MbJavaComputeNode {

	public void evaluate(MbMessageAssembly assembly) throws MbException {
		MbOutputTerminal out = getOutputTerminal("out");
		MbOutputTerminal alt = getOutputTerminal("alternate");

		MbMessage inMessage = assembly.getMessage();
		MbMessageAssembly outAssembly = null;
		Connection conn = null;
		CallableStatement cs = null;
		PreparedStatement prepStmt = null;
		
		// create new message as a copy of the input
		MbMessage outMessage = new MbMessage(inMessage);
		outAssembly = new MbMessageAssembly(assembly, outMessage);
		
		MbElement Response = outMessage.getRootElement().createElementAsLastChild(MbElement.TYPE_NAME, "RESPONSE", null);

		try {
		
			// ----------------------------------------------------------
			// Add user code below
			MbMessage message = assembly.getMessage();
			MbElement inputRoot = inMessage.getRootElement();
			MbElement XMLNSC = inputRoot.getFirstElementByPath("XMLNSC");

			// Obtain input parameters
			//String bankCode = getValue(body, "Data/*/bodyReq/bankCode");
			MbElement test = (MbElement) XMLNSC.getValue() ;  
			//String Date = (XMLNSC.getFirstElementByPath("InquiryCustomerReq/BodyReq/Date") == null) ? "": XMLNSC.getFirstElementByPath("InquiryCustomerReq/BodyReq/Date").getValue().toString();
			String CustCode = (XMLNSC.getFirstElementByPath("InquiryCustomerReq/BodyReq/InquiryValue") == null) ? "": XMLNSC.getFirstElementByPath("InquiryCustomerReq/BodyReq/InquiryValue").getValue().toString();
			//Date = "01/01/2021";
			//CustCode = "4990120479";
	        //String str = "2021-01-11";
	        // converting string into sql date
	        //java.sql.Date date = java.sql.Date.valueOf(str);
			//String runProc = "select * from eocd2.FM_BANK order by bank_name ASC;";
			conn = getJDBCType4Connection("{ESBPolicy}:PREMIADBPolicy",
					JDBC_TransactionType.MB_TRANSACTION_AUTO);
			cs = conn.prepareCall("{call ora_esb.pck_getdata.get_customer (?,?)}");
			//cs.setDate(1, date);
			cs.setString(1, CustCode);

			//cs.registerOutParameter(2,Types.REF_CURSOR);
			cs.registerOutParameter(2, oracle.jdbc.OracleTypes.CURSOR);
			//prepStmt = conn.prepareStatement("select * from FM_BANK order by bank_name ASC");
		    //  prepStmt.setInt(2, 7); //It will throw java.sql.SQLException: Invalid column index
			// execute and retrieve the result set
			cs.execute();
			
			// 2 is index of parameter cursor
			ResultSet resultSet = (ResultSet)cs.getObject(2);

			
			String cust_code, cust_class, cust_name, cust_long_name = "";
			
			
			Response.createElementAsLastChild(MbElement.TYPE_NAME,"Customer",null);
//			
			MbElement listLog = Response.getLastChild();
			int numberRecord = 0;
			
			//ResultSet resultSet = (ResultSet) cSt.getObject(1);
			
			while(resultSet.next())
			{
				cust_code = resultSet.getString("cust_code");
				checkValue(cust_code);
				cust_class = resultSet.getString("cust_class");
				checkValue(cust_class);
				cust_name = resultSet.getString("cust_name");
				checkValue(cust_name);
				cust_long_name = resultSet.getString("cust_long_name");
				checkValue(cust_long_name);
				
				listLog.createElementAsLastChild(MbElement.TYPE_NAME,"CustomerInfo",null);
				MbElement logInfo = listLog.getLastChild();
				
				logInfo.createElementAsLastChild(MbElement.TYPE_NAME_VALUE, "CustCode", cust_code);
				logInfo.createElementAsLastChild(MbElement.TYPE_NAME_VALUE, "CustClass", cust_class);
				logInfo.createElementAsLastChild(MbElement.TYPE_NAME_VALUE, "CustName", cust_name);
				logInfo.createElementAsLastChild(MbElement.TYPE_NAME_VALUE, "CustLongName", cust_long_name);
				numberRecord= numberRecord + 1;
			}
			Response.createElementAsLastChild(MbElement.TYPE_NAME_VALUE,"numberRecord",numberRecord);
			
			cs.close();	
			conn.close();

			//cSt.close();
			// End of user code
			// ----------------------------------------------------------
		} catch (MbException e) {
			// Re-throw to allow Broker handling of MbException
			throw e;
		} catch (RuntimeException e) {
			// Re-throw to allow Broker handling of RuntimeException
			throw e;
		} catch (Exception e) {
			// Consider replacing Exception with type(s) thrown by user code
			// Example handling ensures all exceptions are re-thrown to be handled in the flow
			throw new MbUserException(this, "evaluate()", "", "", e.toString(),
					null);
		}
		finally{

			try {
				cs.close();

				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				//e.printStackTrace();
			}

		}
		// The following should only be changed
		// if not propagating message to the 'out' terminal
		
		out.propagate(outAssembly);
		
	}

//	private void If(boolean equals) {
//		// TODO Auto-generated method stub
//
//	}

	
	
	private String checkValue(String value){ 
		if(value == null) return value= "";
		return value;
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
