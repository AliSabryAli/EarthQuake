package com.ali.earthquake.Activites;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.ali.earthquake.Model.EarthQuake;
import com.ali.earthquake.R;
import com.ali.earthquake.Util.Constant;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class QuakeListActivity extends AppCompatActivity {

    private ListView listView;
    private ArrayList<String> arrayList;
    private RequestQueue queue;
    private List<EarthQuake> quakeList;
    private ArrayAdapter arrayAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quake_list);
        listView = findViewById(R.id.listViewID);
        arrayList = new ArrayList<>();
        quakeList = new ArrayList<>();

        queue = Volley.newRequestQueue(this);
        getAllQuack(Constant.URL);

    }

    void getAllQuack(String url) {

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {


                try {
                    JSONArray features = response.getJSONArray("features");
                    for (int i = 0; i < Constant.LIMIT; i++) {
                        JSONObject properties = features.getJSONObject(i).getJSONObject("properties");
                        JSONObject geometry = features.getJSONObject(i).getJSONObject("geometry");
                        JSONArray coordinates = geometry.getJSONArray("coordinates");

                        double lon = coordinates.getDouble(0);
                        double lat = coordinates.getDouble(1);
                        EarthQuake earthQuake = new EarthQuake();
                        earthQuake.setPlace(properties.getString("place"));
                        earthQuake.setType(properties.getString("types"));
                        earthQuake.setTime(properties.getLong("time"));
                        earthQuake.setMagnitude(properties.getLong("mag"));
                        earthQuake.setDetailsLink(properties.getString("detail"));
                        earthQuake.setLon(lon);
                        earthQuake.setLat(lat);

                        arrayList.add(earthQuake.getPlace());
                        quakeList.add(earthQuake);
                        arrayAdapter = new ArrayAdapter(QuakeListActivity.this, R.layout.row_item_list,
                                android.R.id.text1, arrayList);

                        listView.setAdapter(arrayAdapter);

                        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                Intent intent = new Intent(QuakeListActivity.this, DetailsMapsActivity.class);
                                intent.putExtra("obj", quakeList.get(position));
                                startActivity(intent);
                            }
                        });

                        arrayAdapter.notifyDataSetChanged();

                        String timeFormatted = Constant.getDate(Long.valueOf(properties.getLong("time")));

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        queue.add(jsonObjectRequest);


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
