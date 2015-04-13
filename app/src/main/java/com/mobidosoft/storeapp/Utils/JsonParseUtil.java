package com.mobidosoft.storeapp.Utils;

import android.util.Log;

import com.mobidosoft.storeapp.Model.Product;
import com.mobidosoft.storeapp.Model.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by RP on 3/17/2015.
 */
public class JsonParseUtil {

    private static final String LOG_TAG = JsonParseUtil.class.getSimpleName() ;

    public static User parseStringLogin(String jsonString)
    {
        User user = null;
        try{

            Log.d(LOG_TAG, "jsonString: " + jsonString);
            JSONObject jsonObject = new JSONObject(jsonString);

            user = new User();
            String success = jsonObject.getString(ConstantsApp.JSON_SUCCESS);
            user.setSuccess(success);

            if(success.equals("1")) {
                user.setMessage(jsonObject.getString(ConstantsApp.JSON_MESSAGE));
                user.setUserAccessKey(jsonObject.getString(ConstantsApp.JSON_USER_ACCESS_KEY));
                user.setIsTokenDevice(jsonObject.getString(ConstantsApp.JSON_IS_TOKEN_DEVICE));

                JSONObject loginObject = jsonObject.getJSONObject(ConstantsApp.JSON_LOGIN);
                user.setAdminsId(ConvertUtil.stringToInteger(loginObject.getString(ConstantsApp.JSON_ADMINS_ID)));
                user.setUsername(loginObject.getString(ConstantsApp.JSON_USERNAME));
                user.setName(loginObject.getString(ConstantsApp.JSON_NAME));
                user.setAdminRolesId(ConvertUtil.stringToInteger(loginObject.getString(ConstantsApp.JSON_ADMINROLES_ID)));
                user.setAdminRolesName(loginObject.getString(ConstantsApp.JSON_ADMINROLES_NAME));

                JSONObject resultsObject = jsonObject.getJSONObject(ConstantsApp.JSON_RESULTS);
                user.setFirstName(resultsObject.getString(ConstantsApp.JSON_FIRST_NAME));
                user.setMiddleName(resultsObject.getString(ConstantsApp.JSON_MIDDLE_NAME));
                user.setLastName(resultsObject.getString(ConstantsApp.JSON_LAST_NAME));
                user.setEmail(resultsObject.getString(ConstantsApp.JSON_EMAIL));
                user.setStatus(resultsObject.getString(ConstantsApp.JSON_STATUS));

            }
            else
            {
                user.setErrorMessage(jsonObject.getString(ConstantsApp.JSON_ERROR_MESSAGE));
            }






            Log.d(LOG_TAG,"user v3: " + user.toString());

            return user;
        }
        catch(JSONException e){
            Log.e(LOG_TAG, "Error parsing data " + e.toString());
            e.printStackTrace();
            return null;
        }

    }


    public static ArrayList<Product> parseStringProducts(String jsonString)
    {





        ArrayList<Product> products = new ArrayList<Product>();
        try{

            Log.d(LOG_TAG, "products jsonString: " + jsonString);


            Log.d(LOG_TAG, "jsonString: " + jsonString);
            JSONObject jsonObject = new JSONObject(jsonString);


            String success = jsonObject.getString(ConstantsApp.JSON_SUCCESS);

            if(success.equals("1")) {

                JSONArray produtsArray = jsonObject.getJSONArray(ConstantsApp.JSON_RESULTS); //new JSONArray(jsonString);
                Log.d(LOG_TAG, "produtsArray.count: " + produtsArray.length());

                for (int i = 0; i < produtsArray.length(); i++){

                    JSONObject prodObject = produtsArray.getJSONObject(i);
                    Product p = new Product();
                    p.setId(ConvertUtil.stringToInteger(prodObject.getString(ConstantsApp.JSON_PRODUCT_ID)));
                    p.setName(prodObject.getString(ConstantsApp.JSON_PRODUCT_NAME));
                    p.setDesc(prodObject.getString(ConstantsApp.JSON_PRODUCT_DESC));
                    p.setPrice(prodObject.getString(ConstantsApp.JSON_PRODUCT_PRICE));
                    p.setImg1(prodObject.getString(ConstantsApp.JSON_PRODUCT_IMG1));
                    products.add(p);

                    Log.d(LOG_TAG,"add p:"+ p.toString());

                }

            }
            else
            {
                //user.setErrorMessage(jsonObject.getString(ConstantsApp.JSON_ERROR_MESSAGE));

                Product p = new Product();
                p.setId(-1);
                p.setName("Error");
                p.setDesc(jsonObject.getString(ConstantsApp.JSON_ERROR_MESSAGE));
                products.add(p);

            }


        }
        catch(JSONException e){
            Log.e(LOG_TAG, "Error parsing data " + e.toString());
            e.printStackTrace();
            return null;
        }


        return products;

    }



}
