package com.project.tailoredleisureappdevelopment;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.project.tailoredleisureappdevelopment.entities.Review;

import org.w3c.dom.Text;

import java.io.ByteArrayInputStream;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Base64;

public class ReviewAdapter extends ArrayAdapter<Review>{

    private Context mContext;
    private int mResource;
    public ReviewAdapter(@NonNull Context context, int resource, @NonNull ArrayList<Review> objects) {
        super(context, resource, objects);
        this.mContext = context;
        this.mResource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        convertView = layoutInflater.inflate(mResource, parent, false);

        TextView listViewReviewTextId = (TextView) convertView.findViewById(R.id.listViewReviewTextId);

        ImageView userImageViewReviewId = (ImageView) convertView.findViewById(R.id.userImageViewReviewId);

        listViewReviewTextId.setText(getItem(position).toString());

        byte[] byteArray = getItem(position).getUserImage();
        if(byteArray!=null){
            Log.d("DEBUG: ReviewAdapter", "byteArray: "+byteArray);
            try {
                // Decode byte array into Bitmap
                Bitmap bmp = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
                Log.d("DEBUG: ReviewAdapter", "bmp: "+bmp);
                userImageViewReviewId.setImageBitmap(bmp);
            } catch (Exception e) {
                // Exception occurred during decoding
                Log.e("DEBUG: BitmapConversion", "Error decoding byte array", e);
            }
        }

        return convertView;
    }
}
