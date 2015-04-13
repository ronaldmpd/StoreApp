package com.mobidosoft.storeapp;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;
import android.os.Vibrator;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.mobidosoft.storeapp.Model.User;
import com.mobidosoft.storeapp.Utils.ConexionUtil;
import com.mobidosoft.storeapp.Utils.ConstantsApp;
//import com.mobidosoft.storeapp.Utils.GlobalClass;
import com.mobidosoft.storeapp.Utils.HttpPosUtil;
import com.mobidosoft.storeapp.Utils.JsonParseUtil;
import com.mobidosoft.storeapp.data.UserDbHelper;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;

/**
 * A placeholder fragment containing a simple view.
 */
public class LoginFragment extends Fragment {

    private static final String LOG_TAG = LoginFragment.class.getSimpleName();

    EditText editTextUserName;
    EditText editTextPassword;
    Button buttonEntrar;
    Context parentContext;
    HttpPosUtil post;

    UserDbHelper userDbHelper;
    User userLogin;


    boolean result_back;
    private ProgressDialog pDialog;

    public LoginFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.login, container, false);

        parentContext = rootView.getContext();
        post = new HttpPosUtil();

        editTextUserName =(EditText)rootView.findViewById(R.id.txtUserName);
        editTextPassword = (EditText)rootView.findViewById(R.id.txtPassword);
        buttonEntrar = (Button)rootView.findViewById(R.id.btnEntrar);

        //
        userDbHelper = new UserDbHelper(rootView.getContext());


        //onClick login
        buttonEntrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(LOG_TAG, "Start Login");


                String msg="";

                String usuario  = editTextUserName.getText().toString();
                String password  = editTextPassword.getText().toString();

                if( usuario.isEmpty())
                    msg +=  getResources().getString(R.string.login_ingrese_user) + "\n";
                if( password.isEmpty())
                    msg += getResources().getString(R.string.login_ingrese_password) + "\n";


                if(msg.isEmpty())
                {
                        //si pasamos esa validacion ejecutamos el asynctask pasando el usuario y clave como parametros
                        new AsyncLogin().execute(usuario,password);
                }
                else
                {
                    AlertDialog alertDialog;
                    alertDialog = new AlertDialog.Builder(rootView.getContext()).create();
                    alertDialog.setTitle(getResources().getString(R.string.login_title_validate_alert));
                    alertDialog.setMessage(msg);
                    alertDialog.show();
                }

                Log.d(LOG_TAG,"End Login");
            }
        });






        return rootView;
    }


    //vibra y muestra un Toast
    public void err_login(){
        Vibrator vibrator =(Vibrator) parentContext.getSystemService(Context.VIBRATOR_SERVICE);
        vibrator.vibrate(200);
        Toast toast1 = Toast.makeText(parentContext.getApplicationContext(),"Error:Nombre de usuario o password incorrectos", Toast.LENGTH_SHORT);
        toast1.show();
    }


    /*Valida el estado del logueo solamente necesita como parametros el usuario y passw*/
    public boolean loginstatus(String username ,String password ) {
        int logstatus=-1;

    	/*Creamos un ArrayList del tipo nombre valor para agregar los datos recibidos por los parametros anteriores
    	 * y enviarlo mediante POST a nuestro sistema para relizar la validacion*/
        ArrayList<NameValuePair> postparameters2send= new ArrayList<NameValuePair>();

        postparameters2send.add(new BasicNameValuePair("v","1"));
        postparameters2send.add(new BasicNameValuePair("module","1"));
        postparameters2send.add(new BasicNameValuePair("action","1"));
        postparameters2send.add(new BasicNameValuePair("os","2"));
        postparameters2send.add(new BasicNameValuePair("login_type","1"));
        postparameters2send.add(new BasicNameValuePair("id_device","1"));
        postparameters2send.add(new BasicNameValuePair("u",username));
        postparameters2send.add(new BasicNameValuePair("p",password));

        User login = null;
        if(ConexionUtil.isOnline(this.parentContext)) {

            //realizamos una peticion y como respuesta obtenes un array JSON
            String responseJsonString = post.getserverdata(postparameters2send, ConstantsApp.URL_BASE_API);

      		/*como estamos trabajando de manera local el ida y vuelta sera casi inmediato
      		 * para darle un poco realismo decimos que el proceso se pare por unos segundos para poder
      		 * observar el progressdialog
      		 * la podemos eliminar si queremos
      		 */
            SystemClock.sleep(950);


             login = JsonParseUtil.parseStringLogin(responseJsonString);
        }
        else
        {
            Log.d(LOG_TAG,"OFF LINE");
            login =  userDbHelper.getUserByUsername(username);
            if(login != null && login.getPassword().equals(password)) {
                userLogin = login;
                return true;
            }
            else
                return  false;

        }

        Log.d(LOG_TAG,"login.getSuccess():"+ login.getSuccess());

        //if(login != null && login.getSuccess()=="1")
        if(login != null && login.getSuccess().equals("1"))
        {

            Log.d(LOG_TAG,"Recuperando user OLD of DB username:" + username);
            User userOld = userDbHelper.getUserByUsername(username);


            if(userOld == null) {
               login.setPassword(password);
               userDbHelper.addUser(login);
               Log.d(LOG_TAG,"SAVE USER: " + login.toString());
            }
            else
            {
                Log.d(LOG_TAG,"User OLD: " + userOld.toString());
                userOld.setPassword(password);
                userOld.setUserAccessKey(login.getUserAccessKey());
                userOld.setMessage(login.getMessage());
                userDbHelper.updateUser(userOld);
                Log.d(LOG_TAG,"UPDATE USER: " + userOld.toString());
            }

            userLogin = login;

            return true;
        }
        else
        {
            return false;
        }



    }

    //validamos si no hay ningun campo en blanco
    public boolean checklogindata(String username ,String password ) {

        if (username.equals("") || password.equals("")) {
            Log.e("Login ui", "checklogindata user or pass error");
            return false;

        } else {

            return true;
        }

    }




    /*		CLASE ASYNCTASK
*
* usaremos esta para poder mostrar el dialogo de progreso mientras enviamos y obtenemos los datos
* podria hacerse lo mismo sin usar esto pero si el tiempo de respuesta es demasiado lo que podria ocurrir
* si la conexion es lenta o el servidor tarda en responder la aplicacion sera inestable.
* ademas observariamos el mensaje de que la app no responde.
*/
    class AsyncLogin extends AsyncTask< String, String, String > {

        String user,pass;
        protected void onPreExecute() {
            //para el progress dialog
            pDialog = new ProgressDialog(parentContext);
            pDialog.setMessage("Autenticando....");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        protected String doInBackground(String... params) {
            //obtnemos usr y pass
            user=params[0];
            pass=params[1];

            //enviamos y recibimos y analizamos los datos en segundo plano.
            if (loginstatus(user,pass)==true){
                return "ok"; //login valido
            }else{
                return "err"; //login invalido
            }

        }

        /*Una vez terminado doInBackground segun lo que halla ocurrido
        pasamos a la sig. activity
        o mostramos error*/
        protected void onPostExecute(String result) {

            pDialog.dismiss();//ocultamos progess dialog.
            Log.d("onPostExecute=",""+result);

            if (result.equals("ok")){

                /*
                AlertDialog alertDialog;
                alertDialog = new AlertDialog.Builder(parentContext).create();
                alertDialog.setTitle("Login");
                alertDialog.setMessage("Login OK");
                alertDialog.show();
                */

                Log.d( LOG_TAG, "**** userLogin: " + userLogin);

                Intent intent = new Intent(getActivity(),MenuActivity.class);

                intent.putExtra("admins_id", userLogin.getAdminsId().toString());
                intent.putExtra("user_access_key", userLogin.getUserAccessKey());
                startActivity(intent);



            }else{
                err_login();
            }

        }

    }
}
