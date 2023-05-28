package com.example.insuchef;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.io.FileWriter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONTokener;


public class GetData extends AsyncTask<Void, Void,Void> {

    protected static FileWriter writer;
    protected static BufferedReader reader;
    protected static URL url;
    protected static HttpURLConnection con;
    protected Context context;

    protected File jFile;

    public GetData(File file, Context context)  throws IOException, JSONException {

        this.jFile = file;
        this.context = context;
    }

    @Override
    protected Void doInBackground(Void... voids) {

        if(android.os.Debug.isDebuggerConnected())
            android.os.Debug.waitForDebugger();

        try {
            writer = new FileWriter(this.jFile);
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
                    String str1 = "";
                    String c;
                    while((c = reader.readLine()) != null)
                    {
                        str1 = str1 + c;
                    }
                    JSONTokener token = new JSONTokener(str1);
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
            writer.flush();

            reader.close();
            writer.close();
            con.disconnect();

        } catch(Exception e) {
            e.printStackTrace();
        }

        try {
            MainPage.foodList = new FoodList(this.jFile, context.getApplicationContext());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return null;

    }

    @Override
    protected void onPostExecute(Void v) {
        super.onPostExecute(v);

        Intent intent = new Intent(this.context, MainPage.class);

        Activity a = (Activity) this.context;
        a.startActivity(intent);
        a.finish();

    }


}
