package com.mobidosoft.storeapp;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.mobidosoft.storeapp.Model.Product;
import com.mobidosoft.storeapp.Utils.ConstantsApp;
import com.mobidosoft.storeapp.Utils.ImageUtil;
import com.mobidosoft.storeapp.Utils.HttpPosUtil;
import com.mobidosoft.storeapp.Utils.ImageUtil;
import com.mobidosoft.storeapp.Utils.JsonParseUtil;
import com.mobidosoft.storeapp.Utils.MenuItem;
import com.mobidosoft.storeapp.Utils.MenuItemArrayAdapter;
import com.mobidosoft.storeapp.Utils.ProductItem;
import com.mobidosoft.storeapp.Utils.ProductItemArrayAdapter;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedList;

/**
 * A placeholder fragment containing a simple view.
 */
public class ProductsByCategoryFragment extends Fragment {

    private static final String LOG_TAG = ProductsByCategoryFragment.class.getSimpleName();

    ListView scrollView ;// (ListView)rootView.findViewById(R.id.result_list_view);
    LinkedList<ProductItem> productItems;// = new LinkedList<ProductItem>();
    View rootViewParent;

    public ProductsByCategoryFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_products_by_category, container, false);
        rootViewParent =rootView;

        String admins_id = getActivity().getIntent().getStringExtra("admins_id");
        String user_access_key = getActivity().getIntent().getStringExtra("user_access_key");
        String category_id = getActivity().getIntent().getStringExtra("category_id");

        Log.d(LOG_TAG, "parametros....admins_id: " + admins_id  + " user_access_key:" + user_access_key + " category_id:" + category_id);

        //scrollView = (ListView)rootView.findViewById(R.id.result_list_view);
        //productItems = new LinkedList<ProductItem>();

        loadProductOfWebServices(getActivity().getIntent().getStringExtra("admins_id"), getActivity().getIntent().getStringExtra("user_access_key"), getActivity().getIntent().getStringExtra("category_id"));

        return rootView;
    }

    public void loadProductOfWebServices(String admins_id, String user_access_key, String category_id)
    {


        GetResultTask task = new GetResultTask();
        task.execute(admins_id, user_access_key, category_id);


    }

    class GetResultTask extends AsyncTask<String, Void, ArrayList<Product> > {

        @Override
        protected ArrayList<Product> doInBackground(String... params) {

            ArrayList<Product> products = new ArrayList<Product>();

            Log.v(LOG_TAG, "stat products doInBrackGround");

            ArrayList<NameValuePair> postparameters2send= new ArrayList<NameValuePair>();

            String admins_id = params[0];
            String user_access_key = params[1];
            String category_id = params[2];

            postparameters2send.add(new BasicNameValuePair("v","1"));
            postparameters2send.add(new BasicNameValuePair("module","2"));
            postparameters2send.add(new BasicNameValuePair("action","1"));
            postparameters2send.add(new BasicNameValuePair("admins_id",admins_id));
            postparameters2send.add(new BasicNameValuePair("user_access_key",user_access_key));
            postparameters2send.add(new BasicNameValuePair("category_id",category_id));


            try {

                HttpPosUtil httpPosUtil = new HttpPosUtil();
                String resultString =  httpPosUtil.getserverdata(postparameters2send, ConstantsApp.URL_BASE_API);
                Log.v(LOG_TAG,"server resultString: " + resultString);


                return JsonParseUtil.parseStringProducts(resultString);

            } catch (Exception e) {
                Log.e(LOG_TAG, "Error parsing" + e.getMessage(), e);
                e.printStackTrace();

                Product p = new Product();
                p.setName("NO DATA");
                p.setDesc("NO DATA");

                products.add(p);

            }

            return products;
        }

        @Override
        protected void onPostExecute(ArrayList<Product> products) {

            Log.d(LOG_TAG, "products.size: "+ products.size());

            scrollView = (ListView)rootViewParent.findViewById(R.id.products_list_view);
            productItems = new LinkedList<ProductItem>();

            for(Product product :products){

                ProductItem productItem = new ProductItem();
                productItem.setId(product.getId());
                productItem.setName(product.getName());
                productItem.setDesc(product.getDesc());
                productItem.setImg1String(product.getImg1());

                /*
                String urlImg1 = !product.getImg1().isEmpty()?product.getImg1():ConstantsApp.URL_IMAGES_PRODUCT_DEFAULT ;
                //String urlImg1 =";
                Bitmap image1 = null;
                try {
                    InputStream in = new URL(urlImg1).openStream();
                    image1 = BitmapFactory.decodeStream(in);
                }catch (Exception ex)
                {
                    Log.e("Error", ex.getMessage());
                    ex.printStackTrace();
                }


                productItem.setImg1(image1);
                */

                productItem.setImgDefaultId(R.drawable.defaul_product);

                productItems.add(productItem);
            }


            scrollView.setAdapter( new ProductItemArrayAdapter(rootViewParent.getContext(), R.layout.product_item_row,0, productItems));
        }
    }
}
