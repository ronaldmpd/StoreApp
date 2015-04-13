package com.mobidosoft.storeapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.mobidosoft.storeapp.Model.User;
import com.mobidosoft.storeapp.Utils.MenuItem;
import com.mobidosoft.storeapp.Utils.MenuItemArrayAdapter;
import com.mobidosoft.storeapp.data.UserDbHelper;

import java.util.LinkedList;
import static com.mobidosoft.storeapp.R.*;

/**
 * A placeholder fragment containing a simple view.
 */
public class MenuFragment extends Fragment {

    private static final String LOG_TAG = MenuFragment.class.getSimpleName();

    UserDbHelper userDbHelper;
    User userLogin;

    public MenuFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_menu, container, false);

        userDbHelper = new UserDbHelper(rootView.getContext());
        userLogin = userDbHelper.getUserById(1);

        Log.d(LOG_TAG, "MenuFragment *********userLogin: " + userLogin.toString());

        TextView editTextUserName =(TextView)rootView.findViewById(R.id.textViewSaludo);

        editTextUserName.setText("username:" + getActivity().getIntent().getStringExtra("username"));


        //*** Start - Menu List View
        final ListView scrollView = (ListView)rootView.findViewById(id.result_list_view);
        final LinkedList<MenuItem> elements = new LinkedList<MenuItem>();

        MenuItem mujeresMenutItem = new MenuItem();
        mujeresMenutItem.setId(1);
        mujeresMenutItem.setCategoryId("1");
        mujeresMenutItem.setName("Mujeres");
        mujeresMenutItem.setImageId(R.drawable.mujeres_menu);
        elements.add(mujeresMenutItem);

        MenuItem hombresMenuItem = new MenuItem();
        hombresMenuItem.setId(2);
        hombresMenuItem.setCategoryId("2");
        hombresMenuItem.setName("Hombres");
        hombresMenuItem.setImageId(R.drawable.hombres_menu);
        elements.add(hombresMenuItem);

        MenuItem ninosMenuItem = new MenuItem();
        ninosMenuItem.setId(3);
        ninosMenuItem.setCategoryId("3");
        ninosMenuItem.setName("Niños");
        ninosMenuItem.setImageId(R.drawable.ninos_menu);
        elements.add(ninosMenuItem);

        scrollView.setAdapter( new MenuItemArrayAdapter(rootView.getContext(), layout.menu_item_row,0, elements));

        //*** End - Menu List View

        //Onclick
        scrollView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                Log.d(LOG_TAG, "position: " + position);
                MenuItem menuItemSelect = elements.get(position);
                Log.d(LOG_TAG, "menuItemSelect: " + menuItemSelect.toString());


                //HashMap<String, String> o = (HashMap<String, String>) scrollView.getItemAtPosition(position);

                Intent intent = new Intent(getActivity(),ProductsByCategoryActivity.class);

                //intent.putExtra("admins_id", getActivity().getIntent().getStringExtra("admins_id"));
                //intent.putExtra("user_access_key", getActivity().getIntent().getStringExtra("user_access_key"));



                intent.putExtra("admins_id", userLogin.getAdminsId().toString());
                intent.putExtra("user_access_key", userLogin.getUserAccessKey());

                intent.putExtra("category_id", menuItemSelect.getCategoryId());


                startActivity(intent);

            }
        });




        return rootView;
    }
}
