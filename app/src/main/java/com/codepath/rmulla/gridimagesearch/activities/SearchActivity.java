package com.codepath.rmulla.gridimagesearch.activities;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.codepath.rmulla.gridimagesearch.adapters.ImageResultsAdapter;
import com.codepath.rmulla.gridimagesearch.helpers.EndlessScrollListener;
import com.codepath.rmulla.gridimagesearch.models.ImageFilter;
import com.codepath.rmulla.gridimagesearch.models.ImageResult;
import com.codepath.rmulla.gridimagesearch.R;
import com.etsy.android.grid.StaggeredGridView;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class SearchActivity extends AppCompatActivity {
    private static final int REQUEST_CODE = 200;
    //private EditText etQuery;
    private GridView gvResults;
    private ArrayList<ImageResult> imageResults;
    private ImageResultsAdapter aImageResults;
    private ImageFilter imageFilter;
    private SearchView searchView;
    private StaggeredGridView gridView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_withsgv);
        setupViews();

        //add icon for action bar. doesnt seem to work from xml
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        //on importing same png as 'actionbar image' it was saved as a grey png without content in res/drawables
        getSupportActionBar().setLogo(R.mipmap.ic_launcher_rainbow);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        //create data source
        imageResults = new ArrayList<ImageResult>();
        //attaches data source to adapter
        aImageResults = new ImageResultsAdapter(this, imageResults);
        //link adapter to view in layout
        //gvResults.setAdapter(aImageResults);
        gridView.setAdapter(aImageResults);
        //imageFilter = new ImageFilter();
        //imageFilter=null;

    }

    private void setupViews(){
        //etQuery = (EditText)findViewById(R.id.etQuery);
        gridView = (StaggeredGridView) findViewById(R.id.grid_view);
        //gvResults= (GridView) findViewById(R.id.gvResults);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //launch the image display activity

                //create an intent - use searchactivity.this since u are in an anonyous class, this is that class
                Intent i = new Intent(SearchActivity.this, ImageDisplayActivity.class);
                //get the image result to display
                ImageResult result = imageResults.get(position);
                //pass image result into intent
                //i.putExtra("url", result.fullUrl);
                i.putExtra("result", result);//all things need to be either serializeable or parcelable
                //launch the new activity
                //Toast.makeText(getApplicationContext(), "starting new activity", Toast.LENGTH_SHORT).show();
                startActivity(i);
            }
        });
        gridView.setOnScrollListener(new EndlessScrollListener() {
            @Override
            public void onLoadMore(int page, int totalItemsCount) {
                //Toast.makeText(getApplicationContext(), String.valueOf(page), Toast.LENGTH_LONG);
                if(page < 8){
                   getImages(searchView.getQuery().toString(), page);
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_search, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                //make gridview visible to show the background+contents below
                gridView.setVisibility(View.VISIBLE);
                //perform submit action here, every time you submit, you can clear past results
                aImageResults.clear();
                aImageResults.notifyDataSetChanged();
                getImages(query, 0);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        return true;
    }

    /*Dont need this, using searchview in action bar
    //View is the view that was clicked which is the button
    public void onImageSearch(View v) {
        getImages(searchView.getQuery().toString(), 0);
    }*/


    private void getImages(String query, int page){
        page = page;
        //String query = etQuery.getText().toString();
        //check network connectivity
        if(isNetworkAvailable()) {


            final AsyncHttpClient client = new AsyncHttpClient();
            String searchUrl = "https://ajax.googleapis.com/ajax/services/search/images?v=1.0&q=" + query + "&rsz=8";
            searchUrl += "&start=" + (page * 8);

            //Apply the filters only if they exist and they are != any. any implies dont use that optional parameter in the request
            if (imageFilter != null) {
                if (!imageFilter.siteFilter.equals("")){
                searchUrl += "&as_sitesearch=" + imageFilter.siteFilter;
                }
                if (!imageFilter.colorFilter.equals("any")){
                    searchUrl += "&imgc=" + imageFilter.colorFilter;
                }
                if(!imageFilter.imageSize.equals("any")){
                    searchUrl += "&imgsz=" + imageFilter.imageSize;
                }
                if(!imageFilter.imageType.equals("any")){
                    searchUrl += "&imgtype=" + imageFilter.imageType.replaceAll(" ", "");
                }


                //Toast.makeText(this, searchUrl, Toast.LENGTH_LONG).show();
            }
            //Toast.makeText(this, searchUrl, Toast.LENGTH_LONG).show();
        /*Keep in mind that the onLoadMore method for the endless pagination doesn't fire very often since it's "smart"
        and doesn't get called just when you scroll. It tries to be conservative and only calls onLoadMore when new
        items actually need to be loaded from the server (which means that you have scrolled far enough down and new
        items have been added already since the last call and now we need to load even more items. So until you start
        appending items into the gridview, you aren't expected to see multiple calls to scroll.
         */

            final int finalPage = page;

            client.get(searchUrl, new JsonHttpResponseHandler() {

                        @Override
                        public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                            //Toast.makeText(getApplicationContext(),"onsuccess",Toast.LENGTH_SHORT).show();
                            Log.d("DEBUG", response.toString());
                            JSONArray imageResultsJson = null;
                            try {
                                //Toast.makeText(getApplicationContext(), "return1", Toast.LENGTH_LONG).show();
                                imageResultsJson = response.getJSONObject("responseData").getJSONArray("results");
                                //put parsig logic of JSON in the model not here.

                                //onQueryTextSubmit gets called 2 times when i hit enter once. clear the first result set. it'll fetch it again.
                                if (finalPage ==0) {
                                  aImageResults.clear(); //clear existing images -do only in a new search
                                }
                                //imageResults.addAll(ImageResult.fromJSONArray(imageResultsJson));//factory method
                                //can do this OR use adapter directly
                                //aImageResults.notifyDataSetChanged();
                                //When u make changes to the adapter, it does modify the underlying data
                                aImageResults.addAll(ImageResult.fromJSONArray(imageResultsJson));
                                aImageResults.notifyDataSetChanged();
                            } catch (JSONException e) {
                                e.printStackTrace();

                            }
                            Log.i("INFO", imageResults.toString());
                        }

                        @Override
                        public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                            Toast.makeText(getApplicationContext(), responseString, Toast.LENGTH_LONG).show();

                        }
                    }
            );
        }
        else{
            Toast.makeText(getApplicationContext(), "Network Connection Not Available, Check network settings", Toast.LENGTH_LONG).show();

        }

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            //Toast.makeText(this, "hello", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, SettingsActivity.class);
            if (imageFilter!=null) {
                intent.putExtra("current_filter", imageFilter);
            }
            startActivityForResult(intent, REQUEST_CODE);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE) {

            imageFilter=(ImageFilter)data.getSerializableExtra("image_filter");
            //Toast.makeText(this, imageFilter.colorFilter+ imageFilter.siteFilter+imageFilter.imageSize + imageFilter.imageType,Toast.LENGTH_SHORT).show();

        }
    }

    private Boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting();
    }

}
