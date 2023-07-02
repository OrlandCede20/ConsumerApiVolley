package com.example.consumerapivolley;

import java.util.HashMap;
import java.util.Map;

public class parameters {

    String urlLogin="https://api.uealecpeterson.net/public/login";
    String urlProductos="https://api.uealecpeterson.net/public/productos/search";
    private static String fuente="1";
    Map<String,String> mapaParameters;

    public parameters()
    {
        mapaParameters=new HashMap<>();
    }



    public Map<String,String> mapaLogin(String usuario,String contrasenia)
    {
        mapaParameters.put("correo",usuario);
        mapaParameters.put("clave",contrasenia);
        mapaParameters.put("fuente",fuente);
        return mapaParameters;
    }


    public Map<String,String> mapaToken(String token)
    {
        mapaParameters.put("Authorization", "Bearer " + token);
        return mapaParameters;
    }


    public Map<String,String> mapaFuente()
    {
        mapaParameters.put("fuente",fuente);
        return mapaParameters;
    }


}
