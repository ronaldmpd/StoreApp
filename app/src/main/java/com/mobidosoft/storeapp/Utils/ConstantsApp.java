package com.mobidosoft.storeapp.Utils;

/**
 * Created by RP on 3/17/2015.
 */
public class ConstantsApp {

    //api url
    //public static final String URL_BASE_API = "http://testessi.myempiresecurity.com/essi/api/services/api.php";
    public static final String URL_BASE_API = "http://192.168.0.190:8080/store_crm/api/services/api.php";
    public static final String URL_IMAGES_PRODUCT_DEFAULT = "http://192.168.0.190:8080/store_crm/api/images/products/default.png";


    //login
    public static final String JSON_SUCCESS = "success";
    public static final String JSON_MESSAGE = "message";
    public static final String JSON_ERROR_MESSAGE = "error_message";
    public static final String JSON_USER_ACCESS_KEY = "user_access_key";
    public static final String JSON_IS_TOKEN_DEVICE = "is_token_device";
    public static final String JSON_ADMINS_ID = "admins_id";
    public static final String JSON_USERNAME = "username";
    public static final String JSON_NAME = "name";
    public static final String JSON_ADMINROLES_ID = "adminroles_id";
    public static final String JSON_ADMINROLES_NAME = "adminroles_name";
    public static final String JSON_FIRST_NAME = "first_name";
    public static final String JSON_MIDDLE_NAME = "middle_name";
    public static final String JSON_LAST_NAME = "last_name";
    public static final String JSON_EMAIL = "email";
    public static final String JSON_STATUS = "status";
    public static final String JSON_LOGIN = "login";
    public static final String JSON_RESULTS = "results";

    //products
    public static final String JSON_PRODUCT_ID = "id";
    public static final String JSON_PRODUCT_NAME = "name";
    public static final String JSON_PRODUCT_DESC = "desc";
    public static final String JSON_PRODUCT_PRICE = "price";
    public static final String JSON_PRODUCT_IMG1 = "img1";



    private ConstantsApp(){
        throw new AssertionError();
    }
}
