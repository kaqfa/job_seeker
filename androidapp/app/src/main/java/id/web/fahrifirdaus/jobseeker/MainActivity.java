package id.web.fahrifirdaus.jobseeker;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    SharedPreferences preferences;
    SharedPreferences.Editor editor;

    ArrayList<Job> joblist = new ArrayList<>();
    RecyclerView recJobs;
    JobAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        preferences = getSharedPreferences(LoginActivity.AUTH_SESSION, Context.MODE_PRIVATE);
        recJobs = (RecyclerView) findViewById(R.id.main_list_jobs);

        recJobs.addOnItemTouchListener(new ItemJobClickListener(getApplicationContext(), recJobs, new ItemJobClickListener.ClickListener() {
            @Override
            public void onClick(View v, int position) {
                Job job = joblist.get(position);
                Intent it = new Intent(getApplicationContext(), DetailJobActivity.class);
                it.putExtra("job.detail", job);
                startActivity(it);
            }

            @Override
            public void onLongClick(View v, int position) {

            }
        }));
    }

    @Override
    protected void onResume() {
        super.onResume();

        sendRequest();
    }

    public void sendRequest(){
        JsonArrayRequest request = new JsonArrayRequest(EndPoint.JOB_URL,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d("jobs.result",response.toString());
                        Job newJob;
                        joblist = new ArrayList<>();
                        try {
                            for (int i = 0; i < response.length(); i++) {
                                JSONObject objRes = (JSONObject) response.get(i);
                                newJob = new Job(objRes.getString("jobid"),
                                        objRes.getString("title"),
                                        objRes.getString("description"),
                                        objRes.getInt("salary"),
                                        objRes.getString("post_date"),
                                        objRes.getString("end_date"),
                                        objRes.getString("category"),
                                        objRes.getString("location"),
                                        objRes.getString("provider"),
                                        objRes.getInt("registered"));
                                joblist.add(newJob);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        adapter = new JobAdapter(joblist);
                        recJobs.setAdapter(adapter);
                        RecyclerView.LayoutManager manager = new LinearLayoutManager(getApplicationContext());
                        recJobs.setLayoutManager(manager);
                        recJobs.setItemAnimator(new DefaultItemAnimator());
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
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

        RequestQueue rq = Volley.newRequestQueue(this);
        rq.add(request);
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
                editor = preferences.edit();
                editor.clear();
                editor.commit();

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
