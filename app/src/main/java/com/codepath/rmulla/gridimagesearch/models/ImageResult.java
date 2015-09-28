package com.codepath.rmulla.gridimagesearch.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by rmulla on 9/21/15.
 */
public class ImageResult implements Serializable {

    //private static final long serialVersionUID = -2893089570992474768L;
    public String fullUrl;
    public String thumbUrl;
    public String title;
    public int tbWidth;
    public int tbHeight;
    public int width;
    public int height;

    public ImageResult(JSONObject json){
        try{
            this.fullUrl = json.getString("url");
            this.thumbUrl = json.getString("tbUrl");
            this.title = json.getString("title");
            this.tbHeight = json.getInt("tbHeight");
            this.tbWidth = json.getInt("tbWidth");
            this.height = json.getInt("height");
            this.width = json.getInt("width");

        }catch(JSONException e){
            e.printStackTrace();
        }

    }
    public static ArrayList<ImageResult> fromJSONArray(JSONArray array){
        ArrayList<ImageResult> results = new ArrayList<ImageResult>();
        //take an array of JSON results and return ArrayList of ImageResults
        for(int i=0;i<array.length();i++){
            try{
                results.add(new ImageResult(array.getJSONObject(i)));
            }catch(JSONException e){
                e.printStackTrace();
            }

        }

        return results;
    }
}
