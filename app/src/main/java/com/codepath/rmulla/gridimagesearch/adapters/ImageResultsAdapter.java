package com.codepath.rmulla.gridimagesearch.adapters;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.codepath.rmulla.gridimagesearch.R;
import com.codepath.rmulla.gridimagesearch.models.ImageResult;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by rmulla on 9/22/15.
 */
public class ImageResultsAdapter extends ArrayAdapter<ImageResult> {
    public ImageResultsAdapter(Context context, List<ImageResult> images) {
        super(context, android.R.layout.simple_list_item_1, images);
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageResult imageInfo = getItem(position);
        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_image_result, parent, false);
        }
        ImageView ivImage = (ImageView) convertView.findViewById(R.id.ivImage);
        TextView tvTitle = (TextView) convertView.findViewById(R.id.tvTitle);
        //TextView tvTitle = (TextView) convertView.findViewById(R.id.txt_line1);
        //clear out image from last time
        ivImage.setImageResource(0);
        //populate title and remote download the pic
        tvTitle.setText(Html.fromHtml(imageInfo.title));
        tvTitle.setMaxWidth(imageInfo.tbWidth);
        Picasso.with(getContext()).load(imageInfo.thumbUrl).resize(imageInfo.tbWidth, imageInfo.tbHeight).into(ivImage);

        return convertView;
    }
}
