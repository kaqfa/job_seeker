package id.web.fahrifirdaus.jobseeker;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class LoginActivity extends AppCompatActivity {

    public static final String AUTH_SESSION = "PERF_AUTH_SESSION";
    SharedPreferences preferences;
    EditText edtUsername, edtPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        preferences = getSharedPreferences(AUTH_SESSION, Context.MODE_PRIVATE);
        String token = preferences.getString("token", null);
        if(token != null){
            Intent it = new Intent(this, MainActivity.class);
            it.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            it.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(it);
        }
        edtUsername = (EditText) findViewById(R.id.username);
        edtPassword = (EditText) findViewById(R.id.password);
    }

    public void sendRequest(){
        String username = edtUsername.getText().toString();
        String password = edtPassword.getText().toString();
        HashMap<String, String> param = new HashMap<>();
        param.put("username", username);
        param.put("password", password);
        JsonObjectRequest request = new JsonObjectRequest(EndPoint.LOGIN_URL, new JSONObject(param),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        int userid = 0;
                        String token = "0";
                        Log.d("response.login", response.toString());
                        try {
                            userid = response.getInt("userid");
                            token = response.getString("token");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        if(userid > 0){
                            saveToken(userid, token);
                            Intent it = new Intent(getBaseContext(), MainActivity.class);
                            it.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            it.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(it);
                        } else {
                            Toast.makeText(getBaseContext(), "Username / Password salah",
                                            Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("json.response", error.getMessage());
                    }
                }
        );
        RequestQueue rq = Volley.newRequestQueue(this);
        rq.add(request);
    }

    public void saveToken(int uid, String token){
        SharedPreferences.Editor editor = preferences.edit();

        editor.putString("token", token);
        editor.putInt("uid", uid);
        editor.commit();
    }

    public void login(View v){
        sendRequest();
    }
}
