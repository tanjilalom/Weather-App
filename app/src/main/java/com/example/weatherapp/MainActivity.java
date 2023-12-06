package com.example.weatherapp;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    EditText cityid, countryid;
    TextView infoview;
    Button getinfo;
    private final String url = "https://api.openweathermap.org/data/2.5/weather";
    //private final String url = "https://disease.sh/v3/covid-19/all";
    private final String appid = "1a844b383d1efd4353c8ee2463e63dcd";
    DecimalFormat df = new DecimalFormat("#.##");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        cityid = findViewById(R.id.citynameid);
        countryid = findViewById(R.id.countrynameid);
        infoview = findViewById(R.id.infoviewid);
        getinfo = findViewById(R.id.infogetbtn);

        getinfo.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        String tempurl = "";
        String city = cityid.getText().toString().trim();
        String country = countryid.getText().toString().trim();

        if (city.equals("")) {
            cityid.setError("City field cannot be empty");
            cityid.requestFocus();
        } else {
            if (!country.equals("")) {
                tempurl = url + "?q=" + city + "," + country + "&appid=" + appid;
                Log.d("tanjil", tempurl);
            } else {
                tempurl = url + "?q=" + city + "&appid=" + appid;
                Log.d("tanjil", tempurl);
            }


            StringRequest stringRequest = new StringRequest(Request.Method.GET, tempurl, new Response.Listener<String>() {
                @SuppressLint("SetTextI18n")
                @Override
                public void onResponse(String response) {
                    try {
                        JSONObject jsonObject = new JSONObject(response); // Convert the response String to JSONObject
                        JSONObject mainObject = jsonObject.getJSONObject("main");


                        // Extract temperature related data
                        double temp = mainObject.getDouble("temp");
                        double feelsLike = mainObject.getDouble("feels_like");
                        double tempMin = mainObject.getDouble("temp_min");
                        double tempMax = mainObject.getDouble("temp_max");
                        int pressure = mainObject.getInt("pressure");
                        int humidity = mainObject.getInt("humidity");


                        double xtemp = temp-273.15;
                        double fl = feelsLike-273.15;
                        double mxtemp = tempMax-273.15;
                        double mntemp = tempMin-273.15;



                        infoview.setText("Tempreture: " + df.format(xtemp) +" °C" + "\n" +
                                        "Feels Like: " + df.format(fl)+" °C" + "\n" +
                                        "Min Temperature: " + df.format(mntemp)+" °C" + "\n" +
                                        "Max Temperature: " + df.format(mxtemp)+" °C" + "\n" +
                                        "Pressure: " + df.format(pressure)+" Pa" + "\n" +
                                        "Humidity: " + df.format(humidity)+" g.m-3"
                        );





                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(MainActivity.this, error.toString().trim(), Toast.LENGTH_SHORT).show();
                }
            });
            RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
            requestQueue.add(stringRequest);


        }
    }
}