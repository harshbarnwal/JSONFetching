package com.tech4use.jsonFetching;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    String s_json_url;
    Toolbar toolbar;
    TextView jsonUrl, name, age, educationText, mClass
            , board, current, birthPlace, addressText, skills
            , year2, board2, mClass2, year;
    Button fetchJson;
    RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViews();
        setSupportActionBar(toolbar);
        s_json_url = "https://api.myjson.com/bins/1dob9p";
        jsonUrl.setText(s_json_url);
        jsonUrl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent openIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(s_json_url));
                startActivity(openIntent);
            }
        });
        fetchJson.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "Fetching Data", Toast.LENGTH_SHORT).show();
                jsonParse();
            }
        });
    }

    private void jsonParse () {
        requestQueue = Volley.newRequestQueue(this);
        // requesting new JsonObject
        final JsonObjectRequest objectRequest = new JsonObjectRequest(Request.Method.GET,
                s_json_url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONObject jsonObject = response.getJSONObject("tech4use");
                            Log.v("harsh", jsonObject.getString("name"));
                            name.setText(jsonObject.getString("name"));
                            age.setText(String.valueOf(jsonObject.getInt("age")));
                            // getting the array
                            JSONArray educationArray = jsonObject.getJSONArray("education");
                            // getting 1st array of education
                            JSONObject educationObjects1 = educationArray.getJSONObject(0);
                            mClass.setText(educationObjects1.getString("class"));
                            board.setText(educationObjects1.getString("board"));
                            year.setText(educationObjects1.getString("year"));
                            // getting 2nd array of education
                            JSONObject educationObjects2 = educationArray.getJSONObject(1);
                            mClass2.setText(educationObjects2.getString("class"));
                            board2.setText(educationObjects2.getString("board"));
                            year2.setText(educationObjects2.getString("year"));
                            // fetching skills array
                            JSONArray skillsArray = jsonObject.getJSONArray("skills");
                            for (int i = 0; i<skillsArray.length(); i++) {
                                skills.append(skillsArray.getString(i));
                                skills.append("\n");
                            }
                            // fetching address
                            JSONObject addressObject = jsonObject.getJSONObject("address");
                            birthPlace.setText(addressObject.getString("birthPlace"));
                            current.setText(addressObject.getString("current"));
                        } catch (JSONException e) {
                            Log.v("harsh", "exception = " + e);
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, "Error = " + error, Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue.add(objectRequest);
    }

    private void findViews() {
        fetchJson = findViewById(R.id.fetch_json);
        toolbar = findViewById( R.id.toolbar );
        jsonUrl = findViewById( R.id.json_url );
        name = findViewById( R.id.name );
        age = findViewById( R.id.age );
        educationText = findViewById( R.id.education_text );
        mClass = findViewById( R.id.mClass );
        board = findViewById( R.id.board );
        year = findViewById( R.id.year );
        mClass2 = findViewById( R.id.mClass2 );
        board2 = findViewById( R.id.board2 );
        year2 = findViewById( R.id.year2 );
        skills = findViewById( R.id.skills );
        addressText = findViewById( R.id.address_text );
        birthPlace = findViewById( R.id.birth_place );
        current = findViewById( R.id.current );
    }

}
