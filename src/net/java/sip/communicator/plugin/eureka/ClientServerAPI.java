package net.java.sip.communicator.plugin.eureka;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.json.simple.parser.ParseException;

/**
 * Created with IntelliJ IDEA.
 * User: Zenit
 * Date: 26.08.13
 * Time: 14:01
 * To change this template use File | Settings | File Templates.
 */
public class ClientServerAPI {

    private static final String KEY_PHONE = "phone";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_FIRST_NAME = "firstName";
    private static final String KEY_LAST_NAME = "lastName";

    private static final String KEY_RESULT = "result";
    private static final String KEY_COMMENT = "comment";

    private static final String KEY_VALIDATE_CODE = "validate_code";

    private static final String KEY_LOGIN = "login";
    private static final String KEY_PASSWORD = "password";

    private static final String KEY_ACCOUNTS = "accounts";
    private static final String KEY_ID = "id";
    private static final String KEY_TYPE = "type";
    private static final String KEY_HOST = "host";
    private static final String KEY_DOMAIN = "domain";
    private static final String KEY_CODEC = "codec";

    private static final String KEY_BALANCE = "balance";

    private static final String KEY_DESTINATION = "destination";
    private static final String KEY_PRICE_LIST = "priceList";
    private static final String KEY_ACCOUNT_ID = "accountId";
    private static final String KEY_PRICE = "price";

    private static final int RESULT_OK = 0;
    private static final int RESULT_INVALID_PHONE = -1;
    private static final int RESULT_REACHED_LIMIT = -2;
    private static final int RESULT_INVALID_REQUEST_PARAMS = -100;
    private static final int RESULT_SERVER_ERROR = -666;

    /**
     * RegistrationInit Request
     * @param phone phone number to which the code will be sent
     * @param email user email to which the code will be sent
     * @param firstName user's first name for database
     * @param lastName user's last name for database
     * @return JSON-object to send on server
     */
    public static JSONObject registrationInitConvertRequest(String phone, String email, String firstName, String lastName) {
        JSONObject jsonObj = new JSONObject();

        jsonObj.put(KEY_PHONE, phone);
        jsonObj.put(KEY_EMAIL, email);
        jsonObj.put(KEY_FIRST_NAME, firstName);
        jsonObj.put(KEY_LAST_NAME, lastName);

        return jsonObj;
    }

    /**
     * RegistrationInit Response
     * @param responseContent response content
     * @return responseCode
     */

    public static int registrationInitParseResponse(String responseContent) {
        JSONObject jsonObj = null;

        try {
            jsonObj = (JSONObject) JSONValue.parseWithException(responseContent);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        int resultCode = -5;
        String resultComment = null;

        if(jsonObj!=null){
            if(jsonObj.containsKey(KEY_RESULT)){
                long lTemp = (Long) jsonObj.get(KEY_RESULT);
                resultCode = (int) lTemp;
            }
            if(jsonObj.containsKey(KEY_COMMENT))
                resultComment = (String) jsonObj.get(KEY_COMMENT);
        }

        System.out.println("\n ===> 1. RegistrationInit : Server response <===");

        System.out.println("\n =>Response content:");
        System.out.println(responseContent);

        System.out.println("\n =>Response parsing:");

        System.out.println(KEY_RESULT + " : " + resultCode);
        System.out.println(KEY_COMMENT + " : " + resultComment);

        return resultCode;
    }

    /**
     * RegistrationValidate Request
     * @param phone phone number to which the code will be sent
     * @param validateCode code received by SMS or sent by e-mail
     * @return JSON-object to send on server
     */

    public static JSONObject registrationValidateConvertRequest(String phone, String validateCode) {
        JSONObject jsonObj = new JSONObject();

        jsonObj.put(KEY_PHONE, phone);
        jsonObj.put(KEY_VALIDATE_CODE, validateCode);

        return jsonObj;
    }

    /**
     * RegistrationValidate Response
     * @param responseContent response content
     * @return responseCode
     */

    public static int registrationValidateParseResponse(String responseContent) {
        JSONObject jsonObj = null;

        try {
            jsonObj = (JSONObject) JSONValue.parseWithException(responseContent);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        int resultCode = -5;
        String resultComment = null;

        if(jsonObj!=null){
            if(jsonObj.containsKey(KEY_RESULT)){
                long lTemp = (Long) jsonObj.get(KEY_RESULT);
                resultCode = (int) lTemp;
            }
            if(jsonObj.containsKey(KEY_COMMENT))
                resultComment = (String) jsonObj.get(KEY_COMMENT);
        }

        System.out.println("\n ===> 2. RegistrationValidate : Server response <===");

        System.out.println("\n =>Response content:");
        System.out.println(responseContent);

        System.out.println("\n =>Response parsing:");

        System.out.println(KEY_RESULT + " : " + resultCode);
        System.out.println(KEY_COMMENT + " : " + resultComment);

        return resultCode;
    }

    /**
     * Authentication Request
     * @param login login (420*phone number) received by SMS or sent by e-mail
     * @param password password received by SMS or sent by e-mail
     * @return JSON-object to send on server
     */

    public static JSONObject authenticationConvertRequest(String login, String password) {
        JSONObject jsonObj = new JSONObject();

        jsonObj.put(KEY_LOGIN, login);
        jsonObj.put(KEY_PASSWORD, password);

        return jsonObj;
    }

    /**
     * Authentication Response
     * @param responseContent response content
     * @return responseCode
     */

    public static int authenticationParseResponse(String responseContent) {
        JSONObject jsonObj = null;

        try {
            jsonObj = (JSONObject) JSONValue.parseWithException(responseContent);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        int resultCode = -5;
        String resultComment = null;

        if(jsonObj!=null){
            if(jsonObj.containsKey(KEY_RESULT)){
                long lTemp = (Long) jsonObj.get(KEY_RESULT);
                resultCode = (int) lTemp;
            }
            if(jsonObj.containsKey(KEY_COMMENT))
                resultComment = (String) jsonObj.get(KEY_COMMENT);
        }

        System.out.println("\n ===> 3. Authentication : Server response <===");

        System.out.println("\n =>Response content:");
        System.out.println(responseContent);

        System.out.println("\n =>Response parsing:");

        System.out.println(KEY_RESULT + " : " + resultCode);
        System.out.println(KEY_COMMENT + " : " + resultComment);

        return resultCode;
    }


    /**
     * GetSettings Request
     * @param login login (420*phone number) received by SMS or sent by e-mail
     * @param password password received by SMS or sent by e-mail
     * @return JSON-object to send on server
     */

    public static JSONObject getSettingsConvertRequest(String login, String password) {
        JSONObject jsonObj = new JSONObject();

        jsonObj.put(KEY_LOGIN, login);
        jsonObj.put(KEY_PASSWORD, password);

        return jsonObj;
    }

    /**
     * GetSettings Response
     * @param responseContent response content
     * @return Account object
     */

    public static Account[] getSettingsParseResponse(String responseContent) {
        JSONObject jsonObj = null;

        try {
            jsonObj = (JSONObject) JSONValue.parseWithException(responseContent);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        int resultCode = -5;
        String resultComment = null;

        Account accounts[] = null;

        if(jsonObj!=null){
            if(jsonObj.containsKey(KEY_RESULT)){
                long lTemp = (Long) jsonObj.get(KEY_RESULT);
                resultCode = (int) lTemp;
            }
            if(jsonObj.containsKey(KEY_COMMENT))
                resultComment = (String) jsonObj.get(KEY_COMMENT);

            if(resultCode == RESULT_OK){

                if(jsonObj.containsKey(KEY_ACCOUNTS)){

                    String accountsAsContent = jsonObj.get(KEY_ACCOUNTS).toString();
                    JSONArray accountsAsContentArr = (JSONArray) jsonObj.get(KEY_ACCOUNTS);

                    if(accountsAsContent!=null){

                        JSONArray jsonObjAccounts = null;

                        try {
                            jsonObjAccounts = (JSONArray)JSONValue.parseWithException(accountsAsContent);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }

                        int size = jsonObjAccounts.size();

                        accounts = new Account[size];

                        for(int i = 0; i < size; i++){

                            JSONObject js = (JSONObject) jsonObjAccounts.get(i);

                            Account account = new Account();

                            long lTemp = (Long) js.get(KEY_ID);
                            account.setId((int) lTemp);
                            account.setLogin((String) js.get(KEY_LOGIN));
                            account.setPassword((String) js.get(KEY_PASSWORD));
                            account.setType((String) js.get(KEY_TYPE));
                            account.setHost((String) js.get(KEY_HOST));
                            account.setDomain((String) js.get(KEY_DOMAIN));
                            account.setCodec((String) js.get(KEY_CODEC));

                            accounts[i] = account;
                        }
                    }
                }
             }
        }

        System.out.println("\n ===> 4. GetSettings : Server response <===");

        System.out.println("\n =>Response content:");
        System.out.println(responseContent);

        System.out.println("\n =>Response parsing:");

        System.out.println(KEY_RESULT + " : " + resultCode);
        System.out.println(KEY_COMMENT + " : " + resultComment);

        int counter = 0;

        if(accounts!=null)
            for(Account account : accounts){

                System.out.print("" + ++counter + "  ===>>  ");

                System.out.print(KEY_ID + " : " + account.getId() + " , ");
                System.out.print(KEY_LOGIN + " : " + account.getLogin() + " , ");
                System.out.print(KEY_PASSWORD + " : " + account.getPassword() + " , ");
                System.out.print(KEY_TYPE + " : " + account.getType() + " , ");
                System.out.print(KEY_HOST + " : " + account.getHost() + " , ");
                System.out.print(KEY_DOMAIN + " : " + account.getDomain() + " , ");
                System.out.println(KEY_CODEC + " : " + account.getCodec());
            }

        return accounts;
    }

    /**
     * Balance Request
     * @param login login (420*phone number) received by SMS or sent by e-mail
     * @param password password received by SMS or sent by e-mail
     * @return JSON-object to send on server
     */

    public static JSONObject balanceConvertRequest(String login, String password) {
        JSONObject jsonObj = new JSONObject();

        jsonObj.put(KEY_LOGIN, login);
        jsonObj.put(KEY_PASSWORD, password);

        return jsonObj;
    }

    /**
     * Balance Response
     * @param responseContent response content
     * @return balance value
     */

    public static float balanceParseResponse(String responseContent) {
        JSONObject jsonObj = null;

        try {
            jsonObj = (JSONObject) JSONValue.parseWithException(responseContent);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        int resultCode = -5;
        String resultComment = null;
        float balance = -1;

        if(jsonObj!=null){
            if(jsonObj.containsKey(KEY_RESULT)){
                long lTemp = (Long) jsonObj.get(KEY_RESULT);
                resultCode = (int) lTemp;
            }
            if(jsonObj.containsKey(KEY_COMMENT))
                resultComment = (String) jsonObj.get(KEY_COMMENT);

            if(RESULT_OK == resultCode){
                if(jsonObj.containsKey(KEY_BALANCE)){
                    double bTemp = (Double) jsonObj.get(KEY_BALANCE);
                    balance = (float) bTemp;
                }
            }
        }

        System.out.println("\n ===> 5. Balance : Server response <===");

        System.out.println("\n =>Response content:");
        System.out.println(responseContent);

        System.out.println("\n =>Response parsing:");

        System.out.println(KEY_RESULT + " : " + resultCode);
        System.out.println(KEY_COMMENT + " : " + resultComment);
        System.out.println(KEY_BALANCE + " : " + balance);

        return balance;
    }


    /**
     * GetCallRate Request
     * @param login login (420*phone number) received by SMS or sent by e-mail
     * @param password password received by SMS or sent by e-mail
     * @return JSON-object to send on server
     */

    public static JSONObject getCallRateConvertRequest(String login, String password, String destination) {
        JSONObject jsonObj = new JSONObject();

        jsonObj.put(KEY_LOGIN, login);
        jsonObj.put(KEY_PASSWORD, password);
        jsonObj.put(KEY_DESTINATION, destination);

        return jsonObj;
    }

    /**
     * GetCallRate Response
     * @param responseContent response content
     * @return CallPrice object
     */

    public static CallPrice[] getCallRateParseResponse(String responseContent) {
        JSONObject jsonObj = null;

        try {
            jsonObj = (JSONObject) JSONValue.parseWithException(responseContent);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        int resultCode = -5;
        String resultComment = null;

        CallPrice callPrices[] = null;

        if(jsonObj!=null){
            if(jsonObj.containsKey(KEY_RESULT)){
                long lTemp = (Long) jsonObj.get(KEY_RESULT);
                resultCode = (int) lTemp;
            }
            if(jsonObj.containsKey(KEY_COMMENT))
                resultComment = (String) jsonObj.get(KEY_COMMENT);

            if(resultCode == RESULT_OK){

                if(jsonObj.containsKey(KEY_PRICE_LIST)){

                    String callListsAsContent = jsonObj.get(KEY_PRICE_LIST).toString();
                    JSONArray accountsAsContentArr = (JSONArray) jsonObj.get(KEY_PRICE_LIST);

                    if(callListsAsContent!=null){

                        JSONArray jsonObjAccounts = null;

                        try {
                            jsonObjAccounts = (JSONArray)JSONValue.parseWithException(callListsAsContent);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }

                        int size = jsonObjAccounts.size();

                        callPrices = new CallPrice[size];

                        for(int i = 0; i < size; i++){

                            JSONObject js = (JSONObject) jsonObjAccounts.get(i);

                            CallPrice callPrice = new CallPrice();

                            long lTemp = (Long) js.get(KEY_ACCOUNT_ID);
                            callPrice.setAccountId((int) lTemp);

                            callPrice.setDestination((String) js.get(KEY_DESTINATION));


                            double dTemp = (Double) js.get(KEY_PRICE);
                            callPrice.setPrice((float) dTemp);


                            callPrices[i] = callPrice;
                        }
                    }
                }
            }
        }

        System.out.println("\n ===> 6. GetCallRate : Server response <===");

        System.out.println("\n =>Response content:");
        System.out.println(responseContent);

        System.out.println("\n =>Response parsing:");

        System.out.println(KEY_RESULT + " : " + resultCode);
        System.out.println(KEY_COMMENT + " : " + resultComment);

        int counter = 0;

        if(callPrices!=null)
            for(CallPrice callPrice : callPrices){

                System.out.print("" + ++counter + "  ===>>  ");

                System.out.print(KEY_ACCOUNT_ID + " : " + callPrice.getAccountId() + " , ");
                System.out.print(KEY_DESTINATION + " : " + callPrice.getDestination() + " , ");
                System.out.println(KEY_PRICE + " : " + callPrice.getPrice() + " , ");
            }

        return callPrices;
    }

    public static class Account{
        private int id;
        private String login;
        private String password;
        private String type;
        private String host;
        private String domain;
        private String codec;

        public Account() {
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getLogin() {
            return login;
        }

        public void setLogin(String login) {
            this.login = login;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getHost() {
            return host;
        }

        public void setHost(String host) {
            this.host = host;
        }

        public String getDomain() {
            return domain;
        }

        public void setDomain(String domain) {
            this.domain = domain;
        }

        public String getCodec() {
            return codec;
        }

        public void setCodec(String codec) {
            this.codec = codec;
        }
    }

    public static class CallPrice{
        private int accountId;
        private String destination;
        private float price;

        public CallPrice() {
        }

        public int getAccountId() {
            return accountId;
        }

        public void setAccountId(int accountId) {
            this.accountId = accountId;
        }

        public String getDestination() {
            return destination;
        }

        public void setDestination(String destination) {
            this.destination = destination;
        }

        public float getPrice() {
            return price;
        }

        public void setPrice(float price) {
            this.price = price;
        }
    }
}
