package com.example.insuchef;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONTokener;

public class GetData {

    protected static PrintWriter writer;
    protected static BufferedReader reader;
    protected static URL url;
    protected static HttpURLConnection con;

    public GetData() throws IOException, JSONException {


        writer = new PrintWriter("food.json");
        int offset = 1;
        JSONArray list = new JSONArray();
        JSONArray arr;

        do{
            url = new URL("https://api.nal.usda.gov/fdc/v1/foods/list?dataType=Foundation&pageSize=200&pageNumber=" + offset + "&api_key=c2C7h6Mu1WVT7eXMZYZjGOd9uZHQrGewomaz0YYq");
            //url = new URL("https://api.nal.usda.gov/fdc/v1/foods/list?dataType=SR%20Legacy&pageSize=200&pageNumber=" + offset + "&api_key=c2C7h6Mu1WVT7eXMZYZjGOd9uZHQrGewomaz0YYq");
            //url = new URL("https://api.nal.usda.gov/fdc/v1/foods/list?dataType=Survey%20%28FNDDS%29&pageSize=200&pageNumber=" + offset + "&api_key=c2C7h6Mu1WVT7eXMZYZjGOd9uZHQrGewomaz0YYq");
            con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.connect();
            if (con.getResponseCode() ==  200) // 200 = HttpURLConnection.HTTP_OK
            {
                reader = new BufferedReader(new InputStreamReader(url.openStream()));
                String str = "";
                String c;
                while((c = reader.readLine()) != null)
                {
                    str = str + c;
                }
                JSONTokener token = new JSONTokener(str);
                arr = new JSONArray(token);
                list.put(arr);
                offset++;
            }
            else
            {
                break;
            }
        }while((arr.length() == 200)); // page size = 200
        writer.write(list.toString(4));

        reader.close();
        writer.close();
        con.disconnect();
    }
}
