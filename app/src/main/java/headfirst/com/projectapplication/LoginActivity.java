package headfirst.com.projectapplication;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends Activity implements View.OnClickListener {

    private TextView info;
    private LoginButton loginButton;
    private CallbackManager callbackManager;

    public String id;
    public String name;
    public String s;
    public String username;
    public String str[]=new String[10];
    public int comment;

    public SharedPreferences sharedPreferences;
    public static final int pref=0;
    public SharedPreferences.Editor editor;
    private MyApplication app;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        callbackManager = CallbackManager.Factory.create();
        setContentView(R.layout.activity_login);

        sharedPreferences=getPreferences(pref);
        editor= sharedPreferences.edit();

        info = (TextView) findViewById(R.id.info);
        loginButton = (LoginButton) findViewById(R.id.login_button);


        Button btnLoginGuest = (Button) findViewById(R.id.buttonGuestID);
        btnLoginGuest.setOnClickListener(this);


        boolean status = isLoggedIn();
        if (status) {
            s = sharedPreferences.getString("sValue", "x");
            username=sharedPreferences.getString("userValue","v");
            str[0]=s;
            str[1]=username;
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            intent.putExtra("strArray",str);
            startActivity(intent);
            finish();
        }


        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {

                GraphRequest request = GraphRequest.newMeRequest(
                        loginResult.getAccessToken(),
                        new GraphRequest.GraphJSONObjectCallback() {

                            @Override
                            public void onCompleted(JSONObject object, GraphResponse response) {
                                Log.v("Main", response.toString());
                                Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                                finish();
                                try {
                                    s=object.getString("id");
                                    username=object.getString("name");
                                    str[0]=s;
                                    str[1]=username;
                                    Log.v("Heyyyy",s);
                                    Log.v("Heyyyy",username);
                                    editor.putString("sValue", s);
                                    editor.putString("userValue", username);
                                    editor.commit();
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                app=(MyApplication)getApplication();
                                app.setComment(1);
                                //if login with facebook , set the comment to 1
                                intent.putExtra("strArray",str);
                               startActivity(intent);

                            }
                        });
                Bundle parameters = new Bundle();
                parameters.putString("fields", "id,name,email,gender, birthday");
                request.setParameters(parameters);
                request.executeAsync();
            }


            @Override
            public void onCancel() {

                info.setText("Login attempt canceled.");

            }

            @Override
            public void onError(FacebookException e) {

                info.setText("Login attempt failed.");

            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    public void onClick(View v) {
        app=(MyApplication)getApplication();
        app.setComment(0);
        //if login as guest, set the comment to 0
        str[0]=" ";
        str[1]=" ";
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        intent.putExtra("strArray",str);
        startActivity(intent);
        finish();



    }

    public boolean isLoggedIn() {
        AccessToken accessToken = AccessToken.getCurrentAccessToken();
        return accessToken != null;
    }

}
