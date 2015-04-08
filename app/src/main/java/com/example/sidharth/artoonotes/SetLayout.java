package com.example.sidharth.artoonotes;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import java.io.ByteArrayInputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
public class SetLayout {
    Context baseContext;
    public SetLayout(Context context) {
        baseContext = context;
    }
    public View setView(Product pr) {
        byte [] outImage = pr.getImage();
// Checks if the image field of database is null,and if it is then make a call to my_note_layout.xml
        if(outImage==null)
        {
            LayoutInflater inflater = (LayoutInflater) baseContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view1 = inflater.inflate(R.layout.my_note_layout, null);
            TextView Tv = (TextView) view1.findViewById(R.id.noteTitle);
            TextView Tv2 = (TextView) view1.findViewById(R.id.noteDate);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.MATCH_PARENT
            );
            params.setMargins(10, 10, 10, 10);
            view1.setLayoutParams(params);
            Tv.setGravity(Gravity.CENTER);
            Tv.setTypeface(null, Typeface.BOLD_ITALIC);
            String wtf = pr.get_productname().toString();
            String date = pr.getCreationTime().toString();
            String[] dateTime = date.split(" ");
            String finaldate = dateTime[0];
            String day = convert(finaldate);
            Tv2.setText("Added:" + day);
            int len = wtf.length();
            if (len < 5) Tv.setTextSize(30);
            else if (len < 10) Tv.setTextSize(20);
            else
                Tv.setTextSize(15);
            Tv.setText(wtf);
            view1.setBackgroundColor(Color.argb(100, (int) (Math.random() * 1000) % 255, (int) (Math.random() * 1000) % 255, (int) (Math.random() * 1000) % 255));
            return view1;
        }
// In case the image field is not null , then make a call to the my_image_layout.xml
        else{
            LayoutInflater inflater = (LayoutInflater) baseContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view1 = inflater.inflate(R.layout.my_image_layout, null);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.MATCH_PARENT
            );
            params.setMargins(10, 10, 10, 10);
            view1.setLayoutParams(params);
            TextView Tv2 = (TextView) view1.findViewById(R.id.noteimgTitle);
            ImageView Iv = (ImageView) view1.findViewById(R.id.imageView);
            String date = pr.getCreationTime().toString();
            String[] dateTime = date.split(" ");
            String finaldate = dateTime[0];
            String day = convert(finaldate);
            Tv2.setText("Added:" + day);
            ByteArrayInputStream imageStream = new ByteArrayInputStream(outImage);
            Bitmap theImage = BitmapFactory.decodeStream(imageStream);
            Iv.setImageBitmap(theImage);
            view1.setBackgroundColor(Color.argb(100, (int) (Math.random() * 1000) % 255, (int) (Math.random() * 1000) % 255, (int) (Math.random() * 1000) % 255));
            return view1;
        }
    }
    //Converts the date from sqlDate to JavaDate,in further iterations will work with epoch time for increased accuracy
    public String convert(String sqlDate){
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal = Calendar.getInstance();
        String ab = dateFormat.format(cal.getTime()).toString();
        String [] jav = ab.split("-");
        int javaDay = Integer.parseInt(jav[2]);
        int javaMonth = Integer.parseInt(jav[1]);
        String [] sql = sqlDate.split("-");
        int sqlDay = Integer.parseInt(sql[2]);
        int sqlMonth = Integer.parseInt(sql[1]);
        if(sqlMonth==javaMonth) {
            if ((javaDay-sqlDay)==1) //Check if the current date and added date differ by one
                return "yesterday";
            if(javaDay==sqlDay)
                return "today";
        }
        return sqlDate; //Returns the date in dd-mm-yyyy in case the days don't fit in,like Google keep
    }
}