package com.codepath.rmulla.gridimagesearch.activities;

import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.ShareActionProvider;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.provider.MediaStore.Images;
import android.widget.Toast;

import com.codepath.rmulla.gridimagesearch.R;
import com.codepath.rmulla.gridimagesearch.helpers.TouchImageView;
import com.codepath.rmulla.gridimagesearch.models.ImageResult;
import com.jpardogo.android.googleprogressbar.library.GoogleProgressBar;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.io.File;

import static android.net.Uri.*;

public class ImageDisplayActivity extends AppCompatActivity {
    private ShareActionProvider miShareAction;
    private ImageView ivImageResult;
    //private TouchImageView ivImageResult;
    private GoogleProgressBar gpb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_display);
        /*File sdcard = Environment.getExternalStorageDirectory();
        if (sdcard == null) { return; }
        File dcim = new File(sdcard, "DCIM");
        if (dcim == null) { return; }
        File camera = new File(dcim, "Camera");
        if (camera.exists()) { return; }
        camera.mkdir();*/

        gpb = (GoogleProgressBar) findViewById(R.id.google_progress);

        //remove action bar on this activity
        //getSupportActionBar().hide();
        //pull out url from intent
        ImageResult result =(ImageResult) getIntent().getSerializableExtra("result");
        //find the image view
        ivImageResult = (ImageView) findViewById(R.id.ivImageResult);
        //load image into imageview using picassso
        //Picasso.with(this).load(result.fullUrl).into(ivImageResult);
        // Load image async from remote URL, setup share when complete
        Toast.makeText(getApplicationContext(), result.fullUrl, Toast.LENGTH_LONG);
        Picasso.with(this).load(result.fullUrl).priority(Picasso.Priority.HIGH).resize(result.width, result.height).into(ivImageResult, new Callback() {
            @Override
            public void onSuccess() {
                //GoogleProgressBar gpb = (GoogleProgressBar) findViewById(R.id.google_progress);
                gpb.setVisibility(View.INVISIBLE);
                setupShareIntent();
            }

            @Override
            public void onError() {

            }
        });
    }

    public void setupShareIntent() {
        // Fetch Bitmap Uri locally
        //ImageView ivImage = (ImageView) findViewById(R.id.ivResult);
        Uri bmpUri = getLocalBitmapUri(ivImageResult); // see previous remote images section
        // Create share intent as described above
        Intent shareIntent = new Intent();
        shareIntent.setAction(Intent.ACTION_SEND);
        shareIntent.putExtra(Intent.EXTRA_STREAM, bmpUri);
        shareIntent.setType("image/*");
        // Attach share event to the menu item provider
        miShareAction.setShareIntent(shareIntent);
    }

    public Uri getLocalBitmapUri(ImageView ivResult){

        Drawable mDrawable = ivResult.getDrawable();
        Bitmap mBitmap = ((BitmapDrawable)mDrawable).getBitmap();
        String path=null;
        try {ContentResolver c =getContentResolver();
             path = Images.Media.insertImage(getContentResolver(), mBitmap, "Image Description", null);
        }catch(Exception e){
            e.printStackTrace();
        }

        Uri uri = parse(path);
        return uri;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_image_display, menu);
        // Locate MenuItem with ShareActionProvider
        MenuItem item = menu.findItem(R.id.menu_item_share);
        // Fetch reference to the share action provider
        miShareAction = (ShareActionProvider) MenuItemCompat.getActionProvider(item);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
