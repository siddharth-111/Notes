package com.example.sidharth.artoonotes;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import java.io.ByteArrayInputStream;


public class ImageExpander extends ActionBarActivity {

    MyDBHandler dbHandler=new MyDBHandler(this, null, null, 1);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_expander);
        EditText ab = (EditText) findViewById(R.id.imgNoteModifier);
        ab.setEnabled(false);
        ImageView img = (ImageView) findViewById(R.id.imageView2);
        img.setBackgroundColor(Color.argb(100, (int) (Math.random() * 1000) % 255, (int) (Math.random() * 1000) % 255, (int) (Math.random() * 1000) % 255));
        Bundle note = getIntent().getExtras();
        int id = note.getInt("id");
        Product nm = dbHandler.returnProduct(id);
        byte [] outImage = nm.getImage();
        ByteArrayInputStream imageStream = new ByteArrayInputStream(outImage);
        Bitmap theImage = BitmapFactory.decodeStream(imageStream);
        img.setImageBitmap(theImage);
    }

   public void EditImageActivity(View v){
       finish();
   }
    public void deleteImageActivity(View v){
        Bundle note = getIntent().getExtras();
        int id = note.getInt("id");
        Log.i("MainActivity", " " + id, null);
        dbHandler.deleteProduct(id);
        finish();
    }


}
