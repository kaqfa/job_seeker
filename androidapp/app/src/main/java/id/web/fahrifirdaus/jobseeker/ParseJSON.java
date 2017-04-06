package id.web.fahrifirdaus.jobseeker;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by kaqfa on 4/5/17.
 */

public class ParseJSON {

    public String uid, token, json;

    public ParseJSON(String json){
        this.json = json;
    }

    protected void parseJSON(){
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(json);
            uid = jsonObject.getString("userid");
            token = jsonObject.getString("token");
        } catch (JSONException e){
            e.printStackTrace();
        }
    }
}
