package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    RequestQueue queue;
    String url="https://official-joke-api.appspot.com/random_joke";
    TextView txtjokes,txtid,txtType,txtSetup,txtpunch;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        queue=Volley.newRequestQueue(this);
        txtjokes=findViewById(R.id.txtJokes);
        txtid=findViewById(R.id.txtID);
        txtType=findViewById(R.id.txtType);
        txtSetup=findViewById(R.id.txtSetup);
        txtpunch=findViewById(R.id.txtPunch);
        progressBar=findViewById(R.id.progressBar);


    }

    public void getJokes(View view) {
        progressBar.setVisibility(View.VISIBLE);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        int ID=0;
                        String type,setup,punch;
                        try {
                             ID=response.getInt("id");
                            type=response.getString("type");
                            setup=response.getString("setup");
                            punch=response.getString("punchline");
                            Joke joke=new Joke(ID,type,setup,punch);
                            txtid.setText(joke.getID()+"");
                            txtid.setVisibility(View.VISIBLE);
                            txtType.setText(joke.getType()+"");
                            txtType.setVisibility(View.VISIBLE);
                            txtSetup.setText( joke.getSetup()+"");
                            txtSetup.setVisibility(View.VISIBLE);
                            txtpunch.setText(joke.getPunchLine()+"");
                            txtpunch.setVisibility(View.VISIBLE);
                            progressBar.setVisibility(View.INVISIBLE);



                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        txtjokes.setText("Response: " +ID);
                    }
                }, new Response.ErrorListener() {


                    @Override
                    public void onErrorResponse(VolleyError error) {
                        String err=error.toString();
                        txtjokes.setText("Can not get data: " +error.toString());

                    }
                });
         queue.add(jsonObjectRequest);

    }
}
