package com.example.sidharth.artoonotes;

import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;


public class NoteExpander extends ActionBarActivity {

    MyDBHandler dbHandler=new MyDBHandler(this, null, null, 1);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_expander);
        EditText ab = (EditText) findViewById(R.id.txtNoteModifier);
        TextView tempText = (TextView) findViewById(R.id.tempText);
        Bundle note = getIntent().getExtras();
        int id = note.getInt("id");
        Product nm = dbHandler.returnProduct(id);
        String op = nm.get_productname().toString();
        ab.setText(op);
        tempText.setTextSize(40);
        tempText.setTypeface(null, Typeface.BOLD_ITALIC);
        tempText.setGravity(Gravity.CENTER);
        tempText.setTextColor(Color.BLACK);
        tempText.setBackgroundColor(Color.argb(100, (int) (Math.random() * 1000) % 255, (int) (Math.random() * 1000) % 255, (int) (Math.random() * 1000) % 255));
        tempText.setText(op);
    }

   public void exitActivity(View v){
       EditText ab = (EditText) findViewById(R.id.txtNoteModifier);
       Bundle note = getIntent().getExtras();
       int id = note.getInt("id");
       String noteup = ab.getText().toString();
       dbHandler.updateNote(id,noteup);
       finish();
   }
    public void deleteNoteActivity(View v ){
        Bundle note = getIntent().getExtras();
        int id = note.getInt("id");
        Log.i("MainActivity"," "+id,null);
        dbHandler.deleteProduct(id);
        finish();
    }

}
