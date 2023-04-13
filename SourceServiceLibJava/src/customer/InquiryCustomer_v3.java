package src.customer;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.ibm.broker.javacompute.MbJavaComputeNode;
import com.ibm.broker.plugin.MbElement;
import com.ibm.broker.plugin.MbException;
import com.ibm.broker.plugin.MbMessage;
import com.ibm.broker.plugin.MbMessageAssembly;
import com.ibm.broker.plugin.MbOutputTerminal;
import com.ibm.broker.plugin.MbUserException;

import jdk.nashorn.internal.codegen.types.Type;
import oracle.jdbc.OracleTypes;
import oracle.jdbc.oracore.OracleTypeFLOAT;

import com.ibm.broker.plugin.MbNode.JDBC_TransactionType;

public class InquiryCustomer_v3 extends MbJavaComputeNode {

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

			MbElement test = (MbElement) XMLNSC.getValue() ;  
			String CustCode = (XMLNSC.getFirstElementByPath("InquiryCustomerReq/BodyReq/PartyCode") == null) ? "": XMLNSC.getFirstElementByPath("InquiryCustomerReq/BodyReq/PartyCode").getValue().toString();
			String IDNo = (XMLNSC.getFirstElementByPath("InquiryCustomerReq/BodyReq/IDNo") == null) ? "": XMLNSC.getFirstElementByPath("InquiryCustomerReq/BodyReq/IDNo").getValue().toString();
			String PErr = (XMLNSC.getFirstElementByPath("InquiryCustomerReq/BodyReq/PErr") == null) ? "": XMLNSC.getFirstElementByPath("InquiryCustomerReq/BodyReq/PErr").getValue().toString();

			conn = getJDBCType4Connection("{ESBPolicy}:PREMIADB2Policy", JDBC_TransactionType.MB_TRANSACTION_AUTO);            
		    //cs = conn.prepareCall("{call uat_pjico_info.pck_core_getinfor.fn_get_customer  (?,?,?,?)}");
			cs = conn.prepareCall("begin ? := uat_pjico_info.pck_core_getinfor.fn_get_customer(?,?,?,?); end;");
			cs.registerOutParameter(1, OracleTypes.FLOAT);
			cs.setString(2, CustCode);
			cs.setString(3, IDNo);
			cs.registerOutParameter(4, OracleTypes.CURSOR);
			cs.setString(5, PErr);
			
			cs.executeUpdate();
			// 3 is index of parameter cursor
			//ResultSet resultSet = null;
			ResultSet resultSet = (ResultSet)cs.getObject(4);

//			String cust_code, cust_class, cust_name, cust_long_name = "";
			String CUST_CODE, CUST_NAME, CUST_BL_NAME, CUST_CLASS, CUST_TYPE, CUST_REF_ID1,
			CUST_COMP_REGN_NO, CUST_DOB, CUST_CITY, CUST_STATE, CUST_ADDR1, CUST_PHONE, CUST_EMAIL_ID_OFF,
			CUST_SRC_BUS, CUST_STATUS, CMD_MEM_TYPE, CMD_MEM_NAME, CMD_MEM_NO, CMD_VALID_FM_DT, CMD_VALID_TO_DT,
			CMD_CR_DT, CMD_MEB_STATUS, CMD_MEB_STATUS_DESC ="";
		
			Response.createElementAsLastChild(MbElement.TYPE_NAME,"Customer",null);		
			MbElement listLog = Response.getLastChild();
			int numberRecord = 0;
				
			while(resultSet.next())
			{
//				cust_code = resultSet.getString("cust_code");
//				checkValue(cust_code);
//				cust_class = resultSet.getString("cust_class");
//				checkValue(cust_class);
//				cust_name = resultSet.getString("cust_name");
//				checkValue(cust_name);
//				cust_long_name = resultSet.getString("cust_long_name");
//				checkValue(cust_long_name);
							
				CUST_CODE = resultSet.getString("CUST_CODE");
				checkValue(CUST_CODE);
				CUST_NAME = resultSet.getString("CUST_NAME");
				checkValue(CUST_NAME);
				CUST_BL_NAME = resultSet.getString("CUST_BL_NAME");
				checkValue(CUST_BL_NAME);
				CUST_CLASS = resultSet.getString("CUST_CLASS");
				checkValue(CUST_CLASS);
				CUST_TYPE = resultSet.getString("CUST_TYPE");
				checkValue(CUST_TYPE);
				CUST_REF_ID1 = resultSet.getString("CUST_REF_ID1");
				checkValue(CUST_REF_ID1);
				CUST_COMP_REGN_NO = resultSet.getString("CUST_COMP_REGN_NO");
				checkValue(CUST_COMP_REGN_NO);
				CUST_DOB = resultSet.getString("CUST_DOB");
				checkValue(CUST_DOB);
				CUST_CITY = resultSet.getString("CUST_CITY");
				checkValue(CUST_CITY);
				CUST_STATE = resultSet.getString("CUST_STATE");
				checkValue(CUST_STATE);
				CUST_ADDR1 = resultSet.getString("CUST_ADDR1");
				checkValue(CUST_ADDR1);
				CUST_PHONE = resultSet.getString("CUST_PHONE");
				checkValue(CUST_PHONE);
				CUST_EMAIL_ID_OFF = resultSet.getString("CUST_EMAIL_ID_OFF");
				checkValue(CUST_EMAIL_ID_OFF);
				CUST_SRC_BUS = resultSet.getString("CUST_SRC_BUS");
				checkValue(CUST_SRC_BUS);
				CUST_STATUS = resultSet.getString("CUST_STATUS");
				checkValue(CUST_STATUS);
				CMD_MEM_TYPE = resultSet.getString("CMD_MEM_TYPE");
				checkValue(CMD_MEM_TYPE);
				CMD_MEM_NAME = resultSet.getString("CMD_MEM_NAME");
				checkValue(CMD_MEM_NAME);
				CMD_MEM_NO = resultSet.getString("CMD_MEM_NO");
				checkValue(CMD_MEM_NO);
				CMD_VALID_FM_DT = resultSet.getString("CMD_VALID_FM_DT");
				checkValue(CMD_VALID_FM_DT);
				CMD_VALID_TO_DT = resultSet.getString("CMD_VALID_TO_DT");
				checkValue(CMD_VALID_TO_DT);
				CMD_CR_DT = resultSet.getString("CMD_CR_DT");
				checkValue(CMD_CR_DT);
				CMD_MEB_STATUS = resultSet.getString("CMD_MEB_STATUS");
				checkValue(CMD_MEB_STATUS);
				CMD_MEB_STATUS_DESC = resultSet.getString("CMD_MEB_STATUS_DESC");
				checkValue(CMD_MEB_STATUS_DESC);
				
				listLog.createElementAsLastChild(MbElement.TYPE_NAME,"CustomerInfo",null);
				MbElement logInfo = listLog.getLastChild();
				
//				logInfo.createElementAsLastChild(MbElement.TYPE_NAME_VALUE, "CustCode", cust_code);
//				logInfo.createElementAsLastChild(MbElement.TYPE_NAME_VALUE, "CustClass", cust_class);
//				logInfo.createElementAsLastChild(MbElement.TYPE_NAME_VALUE, "CustName", cust_name);
//				logInfo.createElementAsLastChild(MbElement.TYPE_NAME_VALUE, "CustLongName", cust_long_name);
				
		     	logInfo.createElementAsLastChild(MbElement.TYPE_NAME_VALUE, "CustCode", CUST_CODE);
				logInfo.createElementAsLastChild(MbElement.TYPE_NAME_VALUE, "CustName", CUST_NAME);
				logInfo.createElementAsLastChild(MbElement.TYPE_NAME_VALUE, "CustBLName", CUST_BL_NAME);
				logInfo.createElementAsLastChild(MbElement.TYPE_NAME_VALUE, "CustClass", CUST_CLASS);
				logInfo.createElementAsLastChild(MbElement.TYPE_NAME_VALUE, "CustType", CUST_TYPE);
				logInfo.createElementAsLastChild(MbElement.TYPE_NAME_VALUE, "CustRefID1", CUST_REF_ID1);
				logInfo.createElementAsLastChild(MbElement.TYPE_NAME_VALUE, "CustCompRegnNo", CUST_COMP_REGN_NO);
				logInfo.createElementAsLastChild(MbElement.TYPE_NAME_VALUE, "CustDOB", CUST_DOB);
				logInfo.createElementAsLastChild(MbElement.TYPE_NAME_VALUE, "CustCity", CUST_CITY);
				logInfo.createElementAsLastChild(MbElement.TYPE_NAME_VALUE, "CustState", CUST_STATE);
				logInfo.createElementAsLastChild(MbElement.TYPE_NAME_VALUE, "CustAddr1", CUST_ADDR1);
				logInfo.createElementAsLastChild(MbElement.TYPE_NAME_VALUE, "CustPhone", CUST_PHONE);
				logInfo.createElementAsLastChild(MbElement.TYPE_NAME_VALUE, "CustEmailIDOff", CUST_EMAIL_ID_OFF);
				logInfo.createElementAsLastChild(MbElement.TYPE_NAME_VALUE, "CustSrcBus", CUST_SRC_BUS);
				logInfo.createElementAsLastChild(MbElement.TYPE_NAME_VALUE, "CustStatus", CUST_STATUS);
				logInfo.createElementAsLastChild(MbElement.TYPE_NAME_VALUE, "CmdMemType", CMD_MEM_TYPE);
				logInfo.createElementAsLastChild(MbElement.TYPE_NAME_VALUE, "CmdMemName", CMD_MEM_NAME);
				logInfo.createElementAsLastChild(MbElement.TYPE_NAME_VALUE, "CmdMemNo", CMD_MEM_NO);
				logInfo.createElementAsLastChild(MbElement.TYPE_NAME_VALUE, "CmdValidFmDt", CMD_VALID_FM_DT);
				logInfo.createElementAsLastChild(MbElement.TYPE_NAME_VALUE, "CmdValidToDt", CMD_VALID_TO_DT);
				logInfo.createElementAsLastChild(MbElement.TYPE_NAME_VALUE, "CmdCrDt", CMD_CR_DT);
				logInfo.createElementAsLastChild(MbElement.TYPE_NAME_VALUE, "CmdMebStatus", CMD_MEB_STATUS);
				logInfo.createElementAsLastChild(MbElement.TYPE_NAME_VALUE, "CmdMebStatusDesc", CMD_MEB_STATUS_DESC);
				
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
	
}
