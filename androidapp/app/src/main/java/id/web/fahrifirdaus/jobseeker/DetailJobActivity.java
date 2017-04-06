package id.web.fahrifirdaus.jobseeker;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class DetailJobActivity extends AppCompatActivity {

    TextView txtTitle, txtCategory, txtLocation, txtSalary, txtEndDate, txtProvider, txtDescription;
    Button btnApply;
    Job theJob;
    SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_job);

        preferences = getSharedPreferences(LoginActivity.AUTH_SESSION, Context.MODE_PRIVATE);

        txtTitle = (TextView) findViewById(R.id.txt_det_title);
        txtCategory = (TextView) findViewById(R.id.txt_det_category);
        txtLocation = (TextView) findViewById(R.id.txt_det_location);
        txtSalary = (TextView) findViewById(R.id.txt_det_salary);
        txtEndDate = (TextView) findViewById(R.id.txt_det_deadline);
        txtProvider = (TextView) findViewById(R.id.txt_det_provider);
        txtDescription = (TextView) findViewById(R.id.txt_det_description);
        btnApply = (Button) findViewById(R.id.btn_apply);

        Intent it = getIntent();

        theJob = (Job) it.getSerializableExtra("job.detail");
        txtTitle.setText(theJob.title);
        txtCategory.setText(theJob.category);
        txtLocation.setText(theJob.location);
        txtSalary.setText(Integer.toString(theJob.salary));
        txtEndDate.setText(theJob.endDate);
        txtProvider.setText(theJob.provider);
        txtDescription.setText(theJob.description);
        if(theJob.registered == 0){
            btnApply.setText("Lamar Pekerjaan");
        } else {
            btnApply.setText("Tarik Lamaran");
        }
    }

    public void applyJob(View v){
        int actionMethod;
        if(theJob.registered == 0){
            actionMethod = Request.Method.POST;
        } else {
            actionMethod = Request.Method.DELETE;
        }
        StringRequest request = new StringRequest(actionMethod,
                EndPoint.JOB_URL+"/"+theJob.jobid,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("apply.response", response.toString());
                        finish();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("apply.error", error.getMessage());
                    }
                }
        ){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                String token = preferences.getString("token", null);
                String uid = Integer.toString(preferences.getInt("uid", 0));
                params.put("token", token);
                params.put("uid", uid);

                return params;
            }
        };
        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(request);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu_bar, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent it;
        switch (item.getItemId()){
            case R.id.action_search:
                it = new Intent(this, FindJobActivity.class);
                startActivity(it);
                return true;
            case R.id.logout:
                it = new Intent(this, LoginActivity.class);
                it.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                it.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(it);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
