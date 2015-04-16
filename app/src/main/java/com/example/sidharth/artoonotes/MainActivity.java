package com.example.sidharth.artoonotes;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
public class MainActivity extends ActionBarActivity {
    LinearLayout leftPane, rightPane, mainHolder;
    MyDBHandler dbHandler;
    EditText noteInput;
    List<Product> notesList;
    Product pr;
    static final int REQUEST_CAPTURE = 1;
    static final int REQUEST_IMAGE_CAPTURE = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        allNoteAdder();
        handler();
    }
    public void handler(){

        List<View> left = new ArrayList<View>();
        List<View> right = new ArrayList<View>();
        int leftCount = leftPane.getChildCount();
        int rightCount = rightPane.getChildCount();
        for(int i=0;i<leftCount;i++)
            left.add(leftPane.getChildAt(i));
        for(int i=0;i<rightCount;i++)
            right.add(rightPane.getChildAt(i));
        for(View v1 : left) {
             final int id = v1.getId();

            v1.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                Product pm = dbHandler.returnProduct(id);
                byte outImage[] = pm.getImage();
                if(outImage==null) {
                    Intent i = new Intent(MainActivity.this, NoteExpander.class);
                    i.putExtra("id", id);
                    startActivity(i);
                    overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
                    // dbHandler.deleteProduct(id);
                }
                else
                {
                    Intent i = new Intent(MainActivity.this, ImageExpander.class);
                    i.putExtra("id", id);
                    startActivity(i);
                    overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
                }

                }


            });
        }

        for(View v1 : right) {
           final int id = v1.getId();
            v1.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    Product pm = dbHandler.returnProduct(id);
                    byte outImage[] = pm.getImage();
                    if(outImage==null) {
                        Intent i = new Intent(MainActivity.this, NoteExpander.class);
                        i.putExtra("id", id);
                        startActivity(i);
                        overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
                        // dbHandler.deleteProduct(id);
                    }
                    else
                    {
                        Intent i = new Intent(MainActivity.this, ImageExpander.class);
                        i.putExtra("id", id);
                        startActivity(i);
                        overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
                    }
                }

            });

        }
    }
    protected void onResume(){
        super.onResume();

        allNoteAdder();
    }


    //Sets The Initial view of the Layout
    public void allNoteAdder() {
        leftPane = (LinearLayout) findViewById(R.id.leftPane);
        rightPane = (LinearLayout) findViewById(R.id.rightPane);
        mainHolder = (LinearLayout) findViewById(R.id.mainHolder);
        noteInput = (EditText) findViewById(R.id.txtNoteInput);
        noteInput.setTextColor(Color.WHITE);
        dbHandler = new MyDBHandler(this, null, null, 1);
        leftPane.removeAllViews();
        rightPane.removeAllViews();
        //dbHandler.deleteTable();
        SetLayout viewSet = new SetLayout(getBaseContext());
        Animation translate,right_in;
        int i=0;
        translate= AnimationUtils.loadAnimation(getBaseContext(), R.anim.translate);
        right_in= AnimationUtils.loadAnimation(getBaseContext(), R.anim.right_in);
        notesList = dbHandler.getAllNotes();
        for (Iterator iterator = notesList.iterator(); iterator.hasNext(); i++) {
            Product noteItem = (Product) iterator.next();
            View v = viewSet.setView(noteItem);
           // v.setId(noteItem.get_id());
            if (i % 2 == 0) {
                leftPane.addView(v);
                v.startAnimation(translate);
            } else {
                rightPane.addView(v);
                v.startAnimation(right_in);
            }
        }
        handler();
    }



    //The AddNote(tick mark) Button to add new Note
    public void addButtonClicked(View view){
        Product product = new Product(noteInput.getText().toString());
        noteInput.setText("");
        dbHandler.addProduct(product);
        printDatabase();
        handler();
    }
    //Camera Logic
    public void addClick(View view) {
        Button btnImage = (Button) findViewById(R.id.btnImage);
        if (!hasCamera()) {
            btnImage.setEnabled(false);
        }
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent,REQUEST_IMAGE_CAPTURE);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap image = (Bitmap) extras.get("data");
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            image.compress(Bitmap.CompressFormat.JPEG, 100, stream);
            byte imageInByte[] = stream.toByteArray();
            dbHandler.addProduct(new Product(noteInput.getText().toString(), imageInByte));
            printDatabase();
            handler();
        }
    }
    //Checks if the User has a Camera
    private boolean hasCamera()
    {
        return getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_ANY);
    }
    //Logic to add a view in the Panes i.e left and right
//The approach used is Bottom-up approach
    public void printDatabase() {
        SetLayout viewSet = new SetLayout(getBaseContext());
        int ct,i;
        Animation translate, diagonal;
        translate = AnimationUtils.loadAnimation(getBaseContext(), R.anim.translate);
        diagonal= AnimationUtils.loadAnimation(getBaseContext(), R.anim.diagonal);
        pr = dbHandler.databaseToString();
        View view3 = viewSet.setView(pr);
        ct = dbHandler.getCount();
        ct--;// Reduce the Count relevant to the current views
        if (ct % 2 == 1)
            i = (ct / 2);
        else
            i = (ct / 2) - 1;
        for (; i >= 0; i--) {
            View v1 = leftPane.getChildAt(i);
            View v2 = rightPane.getChildAt(i);
            if (v1 == null && v2 == null) { //Check if both the Left and right panel are null
                break;
            }
            if (v2 == null) { //Check only if the right panel is null
                leftPane.removeViewAt(i);
                rightPane.addView(v1, i);
                v1.startAnimation(translate);
            }
            if (v1 != null && v2 != null) { //Check if both the left and right panel are not null
                rightPane.removeViewAt(i);
                leftPane.addView(v2, i + 1);
                leftPane.removeViewAt(i);
                rightPane.addView(v1, i);
                v2.startAnimation(diagonal);
            }
        }
        leftPane.addView(view3, 0); // Adds the recent added view to the top
        view3.startAnimation(translate);
    }

}
