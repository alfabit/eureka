package net.java.sip.communicator.plugin.eureka;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.json.simple.parser.ParseException;

public class ServerInfoExchanger {
	
	private static final String PHONE_KEY = "phone";
	private static final String RESULT_KEY = "result";
	private static final String COMMENT_KEY = "comment";
	private static final String CODE_KEY = "code";
	private static final String ACCOUNTS_KEY = "accounts";
	private static final String LOGIN_KEY = "login";
	private static final String PASSWORD_KEY = "password";
	private static final String TYPE_KEY = "type";
	private static final String HOST_KEY = "host";
	private static final String BALANCE_KEY = "balance";
	private static final String DESTINATION_KEY = "destination";
	private static final String PRICE_KEY = "price";

	private static final int RESULT_OK = 0;
	private static final int RESULT_INVALID_PHONE = -1;
	private static final int RESULT_REACHED_LIMIT = -2;
	private static final int RESULT_INVALID_REQUEST_PARAMS = -100;
	private static final int RESULT_SERVER_ERROR = -666;

	public static /*boolean*/void sendPhone(String phoneNumber){
		
		boolean result = false;
		
//		send data
//		convertDataToJson();
		
//		received result:
//		parseCodeResponse("qwe");
	
	}
	
	public static void convertDataToJson() {
		
	}

	public static void parsePhoneResponse(String responseContent) {
		JSONObject jsonObj = null;
		
		try {
			jsonObj = (JSONObject)JSONValue.parseWithException(responseContent);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		int resultCode = -5;
		String resultComment = null;
		
		if(jsonObj!=null){
			if(jsonObj.containsKey(RESULT_KEY)){
				long lTemp = (Long) jsonObj.get(RESULT_KEY);
				resultCode = (int) lTemp;
			}
			if(jsonObj.containsKey(COMMENT_KEY))
				resultComment = (String) jsonObj.get(COMMENT_KEY);
		}

        System.out.println("\n =>Server response on phone request.");

        System.out.println("\n =>Response content:");
        System.out.println(responseContent);

        System.out.println("\n =>Response parsing:");

        System.out.println(CODE_KEY + " : " + resultCode);
        System.out.println(COMMENT_KEY + " : " + resultComment);
	}
	
	public static /*boolean*/void sendCode(String phoneNumber, String Code){
		
		boolean result = false;
		
//		send data
//		convertDataToJson();
		
//		received result:
//		parseCodeResponse("qwe");

	}
	
	public static void parseCodeResponse(String responseContent) {
		
		JSONObject jsonObj = null;
		
		try {
			jsonObj = (JSONObject)JSONValue.parseWithException(responseContent);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		
		int resultCode1 = -5;
		String resultComment1 = null;
		
		String logins[] = null;
		String passwords[] = null;
		String types[] = null;
		String hosts[] = null;
		
		if(jsonObj!=null){
			if(jsonObj.containsKey(RESULT_KEY)){
				long lTemp = (Long) jsonObj.get(RESULT_KEY);
				resultCode1 = (int) lTemp;
			}
			if(jsonObj.containsKey(COMMENT_KEY))
				resultComment1 = (String) jsonObj.get(COMMENT_KEY);
			if(jsonObj.containsKey(ACCOUNTS_KEY)){
				
				String accountsAsContent = jsonObj.get(ACCOUNTS_KEY).toString();
				JSONArray accountsAsContentArr = (JSONArray) jsonObj.get(ACCOUNTS_KEY);
				
				if(accountsAsContent!=null){
					
					JSONArray jsonObjAccounts = null;
					
					try {
						jsonObjAccounts = (JSONArray)JSONValue.parseWithException(accountsAsContent);
					} catch (ParseException e) {
						e.printStackTrace();
					}
					
					int size = jsonObjAccounts.size();
					
					logins = new String[size];
					passwords = new String[size];
					types = new String[size];
					hosts = new String[size];
							
					for(int i = 0; i < size; i++){
						
						JSONObject js = (JSONObject) jsonObjAccounts.get(i);
						
						logins[i] = (String) js.get(LOGIN_KEY);
						passwords[i] = (String) js.get(PASSWORD_KEY);
						types[i] = (String) js.get(TYPE_KEY);
						hosts[i] = (String) js.get(HOST_KEY);
						
					}
				}
			}
		}

        System.out.println("\n =>Server response on code request.");

        System.out.println("\n =>Response content:");
		System.out.println(responseContent);

        System.out.println("\n =>Response parsing:");
		
		System.out.println(CODE_KEY + " : " + resultCode1);
		System.out.println(COMMENT_KEY + " : " + resultComment1);
		
		if(logins!=null)
			for(int i = 0; i < logins.length; i++)
				System.out.println(logins[i] + " : " + passwords[i] + " : " + types[i] + " " + hosts[i]);
		
	}
	
	public static /*boolean*/void sendBalanceRequest(String login, String password, String host){
		
		boolean result = false;
		
//		send data
//		convertDataToJson();
		
//		received result:
//		parseBalanceRequest("req");

	}

	public static void parseBalanceRequest(String responseContent) {
		JSONObject jsonObj = null;
		
		try {
			jsonObj = (JSONObject)JSONValue.parseWithException(responseContent);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		int resultCode = -5;
		String resultComment = null;
		float balance = -1;
		
		if(jsonObj!=null){
			if(jsonObj.containsKey(RESULT_KEY)){
				long lTemp = (Long) jsonObj.get(RESULT_KEY);
				resultCode = (int) lTemp;
			}
			if(jsonObj.containsKey(COMMENT_KEY))
				resultComment = (String) jsonObj.get(COMMENT_KEY);
			if(jsonObj.containsKey(BALANCE_KEY)){
				double bTemp = (Double) jsonObj.get(BALANCE_KEY);
				balance = (float) bTemp;
			}
		}

        System.out.println("\n =>Server response on code request.");

        System.out.println("\n =>Response content:");
		System.out.println(responseContent);

        System.out.println("\n =>Response parsing:");
		
		System.out.println(CODE_KEY + " : " + resultCode);
		System.out.println(COMMENT_KEY + " : " + resultComment);
		System.out.println(BALANCE_KEY + " : " + balance);
	}
	
	public static /*boolean*/void sendCallPriceRequest(String login, String password, String host, String destination){
		
		boolean result = false;
		
//		send data
//		convertDataToJson();
		
//		received result:
//		parseCallPriceRequest("req");
		
	}
	
	public static void parseCallPriceRequest(String responseContent) {
		JSONObject jsonObj = null;
		
		try {
			jsonObj = (JSONObject)JSONValue.parseWithException(responseContent);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		int resultCode = -5;
		String resultComment = null;
		float balance = -1;
		float price = -1;
		
		if(jsonObj!=null){
			if(jsonObj.containsKey(RESULT_KEY)){
				long lTemp = (Long) jsonObj.get(RESULT_KEY);
				resultCode = (int) lTemp;
			}
			if(jsonObj.containsKey(COMMENT_KEY))
				resultComment = (String) jsonObj.get(COMMENT_KEY);
			if(jsonObj.containsKey(BALANCE_KEY)){
				double bTemp = (Double) jsonObj.get(BALANCE_KEY);
				balance = (float) bTemp;
			}
			if(jsonObj.containsKey(PRICE_KEY)){
				double pTemp = (Double) jsonObj.get(PRICE_KEY);
				price = (float) pTemp;
			}
		}

        System.out.println("\n =>Server response on code request.");

        System.out.println("\n =>Response content:");
        System.out.println(responseContent);

        System.out.println("\n =>Response parsing:");
		
		System.out.println(CODE_KEY + " : " + resultCode);
		System.out.println(COMMENT_KEY + " : " + resultComment);
		System.out.println(BALANCE_KEY + " : " + balance);
		System.out.println(PRICE_KEY + " : " + price);
	}
}
