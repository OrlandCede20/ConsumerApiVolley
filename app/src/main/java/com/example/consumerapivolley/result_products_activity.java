package com.example.consumerapivolley;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class result_products_activity extends AppCompatActivity {

    parameters parametros;
    String token="";

    TextView txtview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resultado);
        try {
            parametros=new parameters();
            token=this.getIntent().getExtras().getString("token");
            inicializar();
            volley();
        }catch (Exception ex)
        {
            Log.i("error",ex.getMessage());
        }
    }

    private void inicializar()
    {
        txtview=findViewById(R.id.resultado);
    }

    private void volley()
    {
        try {
            RequestQueue queue = Volley.newRequestQueue(this);
            StringRequest stringRequest = new StringRequest(Request.Method.POST,
                    parametros.urlProductos, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.i("jajajajaotyronivel",response);
                    try {
                        JSONArray jsonArray =  new JSONObject(response).getJSONArray("productos");
                        String cadena="";
                        for (int x = 0; x < jsonArray.length(); x++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(x);
                            cadena+=jsonObject.getString("id") + " - " + jsonObject.getString("descripcion") + "\n";
                        }
                        txtview.setText(cadena);
                    }catch (Exception ex)
                    {
                        Log.i("error",ex.getMessage());
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.i("error", error.getMessage());
                }
            }) {
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    return parametros.mapaToken(token);
                }

                @Nullable
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    return parametros.mapaFuente();
                }
            };
            queue.add(stringRequest);
        }catch (Exception ex)
        {
            Log.i("error",ex.getMessage());
        }
    }
}