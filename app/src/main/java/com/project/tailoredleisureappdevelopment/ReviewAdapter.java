package com.project.tailoredleisureappdevelopment;
/*
Authors: Saikarthik Uda (Technical Lead), Ebere Janet Eboh, Prathyusha Kamma.
 */
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Rating;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
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

    //Global Variables

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

        EditText userNameReviewId = (EditText) convertView.findViewById(R.id.username_review_id);
        RatingBar ratingReviewId =  (RatingBar) convertView.findViewById(R.id.review_rating_id);
        EditText reviewedDateReviewId = (EditText) convertView.findViewById(R.id.reviewed_date_review_id);
        TextView reviewReviewId = (TextView) convertView.findViewById(R.id.review_review_id);

        ImageView userImageViewReviewId = (ImageView) convertView.findViewById(R.id.userImageViewReviewId);

        userNameReviewId.setText(getItem(position).getPersonName().trim());
        ratingReviewId.setRating(getItem(position).getRating());
        reviewedDateReviewId.setText(getItem(position).getReviewedDate().toString());
        reviewReviewId.setText(getItem(position).getReview().trim());



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

        for(int i=position+1;i<100; i++){

            if(i==1){
                userImageViewReviewId.setImageResource(R.drawable.haveli001);
                break;
            }else if(i==2){
                userImageViewReviewId.setImageResource(R.drawable.haveli002);
                break;
            }else if(i==3){
                userImageViewReviewId.setImageResource(R.drawable.haveli003);
                break;
            }else if(i==4){
                userImageViewReviewId.setImageResource(R.drawable.haveli004);
                break;
            }else{
                userImageViewReviewId.setImageResource(R.drawable.haveli001);
                break;
            }
        }

        return convertView;
    }
}
