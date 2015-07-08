package com.ab.nantescam;

import android.content.Context;
import android.net.ParseException;
import android.support.v4.content.AsyncTaskLoader;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by shagrath on 07/07/15.
 */
public class CamLoader extends AsyncTaskLoader<List<WebCam>> {

    Context context = null;

    public CamLoader(Context context) {
        super(context);
        this.context = context;
        forceLoad();
    }

    @Override
    public List<WebCam> loadInBackground() {
        List<WebCam> cams = new ArrayList<WebCam>();
        File jsonFile = new File(context.getCacheDir(), "nantes_webcams.json");
        try {
            URL url = new URL("https://shagr4th.github.io/nantes_webcams.json");
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            int responseCode = urlConnection.getResponseCode();

            InputStream inputStream = null;
            if (responseCode == 200) {
                long currentTime = System.currentTimeMillis();
                long lastModified = urlConnection.getHeaderFieldDate("Last-Modified", currentTime);

                if (jsonFile.exists() && lastModified < jsonFile.lastModified() && jsonFile.length() > 0) {
                    // skip update
                } else {
                    InputStream in = urlConnection.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                    PrintWriter pw = new PrintWriter(jsonFile, "UTF-8");
                    String line;
                    while ((line = reader.readLine()) != null) {
                        pw.println(line);
                    }
                    pw.close();
                    in.close();
                }
                inputStream = new FileInputStream(jsonFile);
            } else {
                inputStream = context.getAssets().open("nantes_webcams.json");
            }

            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
            reader.close();

            JSONObject json = new JSONObject(sb.toString());
            JSONArray array = json.getJSONArray("webcams");
            for (int i = 0; i < array.length(); i++) {
                JSONObject camJson = array.getJSONObject(i);
                WebCam cam = new WebCam(camJson.getInt("id"),
                        camJson.getString("name"),
                        camJson.getString("url"),
                        camJson.getDouble("latitude"),
                        camJson.getDouble("longitude"));
                cams.add(cam);
            }

        } catch (ParseException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return cams;
    }
}
