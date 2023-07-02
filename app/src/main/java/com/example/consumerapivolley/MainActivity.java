package com.example.consumerapivolley;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

public class MainActivity extends AppCompatActivity {
    EditText email;
    EditText password;
    Button btnLogin;
    parameters parametros;
    TextView txtview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        inicializar();
    }


    private void inicializar()
    {
        txtview=findViewById(R.id.txtMensaje);
        email=findViewById(R.id.email);
        password=findViewById(R.id.password);
        btnLogin=findViewById(R.id.btnLogin);
    }


    public void btnenviar(View view)
    {
        parametros=new parameters();
        volley();
    }


    private void cambiar_vista(String token)
    {
        Intent intent=new Intent(this, result_products_activity.class);
        Bundle b=new Bundle();
        b.putString("token",token);
        intent.putExtras(b);
        startActivity(intent);
    }

    private void volley()
    {
        try {
            RequestQueue queue = Volley.newRequestQueue(this);
            StringRequest stringRequest = new StringRequest(Request.Method.POST,
                    parametros.urlLogin, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        txtview.setText("Inicio correcto");
                        JSONObject obj = new JSONObject(response);
                        cambiar_vista(obj.getString("access_token"));
                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    txtview.setText("Usuario o contraseña incorrectas");
                }
            }) {
                @Nullable
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    return parametros.mapaLogin(email.getText().toString(), password.getText().toString());
                }
            };
            queue.add(stringRequest);
        }catch (Exception ex)
        {
            Log.i("error",ex.getMessage());
        }
    }


}