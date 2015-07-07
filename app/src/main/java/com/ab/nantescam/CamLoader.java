package com.ab.nantescam;

import android.content.Context;
import android.net.ParseException;
import android.support.v4.content.AsyncTaskLoader;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by shagrath on 07/07/15.
 */
public class CamLoader extends AsyncTaskLoader<List<WebCam>> {

    public CamLoader(Context context) {
        super(context);
        forceLoad();
    }

    @Override
    public List<WebCam> loadInBackground() {
        List<WebCam> cams = new ArrayList<WebCam>();
        String url = "https://shagr4th.github.io/nantes_webcams.json";
        HttpClient httpClient = new DefaultHttpClient();
        HttpGet get = new HttpGet(url);
        HttpResponse res = null;
        try {
            res = httpClient.execute(get);
            HttpEntity entity = res.getEntity();
            String body = EntityUtils.toString(entity);
            JSONObject json = new JSONObject(body);
            JSONArray array = json.getJSONArray("webcams");
            for(int i=0;i<array.length();i++) {
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
        } finally {
            if (cams.size() == 0) {
                // todo: load json from assets
            }
        }
        return cams;
    }
}
