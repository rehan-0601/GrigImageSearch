package com.codepath.rmulla.gridimagesearch.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.codepath.rmulla.gridimagesearch.R;
import com.codepath.rmulla.gridimagesearch.models.ImageFilter;

public class SettingsActivity extends AppCompatActivity {
    private Spinner spinnerImageSize;
    private Spinner spinnerColorFilter;
    private Spinner spinnerImageType;
    private EditText etSiteFilter;
    private ImageFilter imageFilter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        //if (imageFilter!=null){
            imageFilter = (ImageFilter)getIntent().getSerializableExtra("current_filter");
        //}
        //else
        if(imageFilter == null){
            imageFilter = new ImageFilter();
        }

        setupSpinners();
        setupListeners();

    }

    private void setupSpinners(){
        etSiteFilter  = (EditText) findViewById(R.id.etSiteFilter);
        spinnerImageSize = (Spinner) findViewById(R.id.spinnerImageSize);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapterImageSize = ArrayAdapter.createFromResource(this,
                R.array.imagesize_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapterImageSize.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinnerImageSize.setAdapter(adapterImageSize);
        //set old filter if one exists
        if(imageFilter!=null) {
            //Toast.makeText(this," position number"+adapterImageSize.getPosition(imageFilter.imageSize), Toast.LENGTH_SHORT).show();
            spinnerImageSize.setSelection(adapterImageSize.getPosition(imageFilter.imageSize));
            //int i = adapterImageSize.getPosition(imageFilter.imageSize);
        }

        spinnerColorFilter = (Spinner) findViewById(R.id.spinnerColorFilter);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapterColorFilter = ArrayAdapter.createFromResource(this,
                R.array.colorfilter_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapterColorFilter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinnerColorFilter.setAdapter(adapterColorFilter);
        if(imageFilter!=null) {
            //Toast.makeText(this," position number"+adapterColorFilter.getPosition(imageFilter.colorFilter), Toast.LENGTH_SHORT).show();
            spinnerColorFilter.setSelection(adapterColorFilter.getPosition(imageFilter.colorFilter));
        }

        spinnerImageType = (Spinner) findViewById(R.id.spinnerImageType);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapterImageType = ArrayAdapter.createFromResource(this,
                R.array.imagetype_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapterImageType.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinnerImageType.setAdapter(adapterImageType);
        if(imageFilter!=null) {
            spinnerImageType.setSelection(adapterImageType.getPosition(imageFilter.imageType));
        }

        if(getIntent().getExtras()!=null){
            etSiteFilter.setText(imageFilter.siteFilter);
        }
    }

    private void setupListeners(){

        //For each spinner, each time an item is selected, save this in the imageFilter object.(=>passed back to main activity)
        spinnerImageSize.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
               imageFilter.imageSize = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinnerColorFilter.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                imageFilter.colorFilter= parent.getItemAtPosition(position).toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        spinnerImageType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                imageFilter.imageType = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        etSiteFilter = (EditText)findViewById(R.id.etSiteFilter);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_settings, menu);
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

    public void onSave(View v){
        Intent intent = new Intent();
        //update filter with edittext.
        imageFilter.siteFilter = etSiteFilter.getText().toString();
        intent.putExtra("image_filter", imageFilter);
        setResult(RESULT_OK, intent);
        finish();

    }
}
