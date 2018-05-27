package demo.inote;


import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;
import com.r0adkll.slidr.Slidr;

import java.text.DateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static java.text.DateFormat.getDateTimeInstance;



public class NewNoteActivity extends AppCompatActivity {

//        private GestureDetectorCompat gestureObject;

        EditText editText, editText2;
        Button buttonAdd;
        RadioButton radioButton, radioButton2, rb;
        RadioGroup rg;

        DatabaseReference databaseNotes;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_newnote);

            Slidr.attach(this);


            databaseNotes = FirebaseDatabase.getInstance().getReference("notes");


            editText = (EditText) findViewById(R.id.editTextCategory);
            editText2 = (EditText) findViewById(R.id.editTextNotes);
            buttonAdd = (Button) findViewById(R.id.button_Save);
            rg = (RadioGroup) findViewById(R.id.rgroup);

            buttonAdd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    addNote();
                }
            });
        }

        public void onRadioButtonClicked(View view) {
            int radiobuttonid = rg.getCheckedRadioButtonId();
            rb = (RadioButton) findViewById(radiobuttonid);
            Toast.makeText(getBaseContext(), rb.getText(), Toast.LENGTH_SHORT).show();
        }




        private void addNote() {
            String category = editText.getText().toString().trim();
            String note = editText2.getText().toString().trim();
            String rating = rb.getText().toString().trim();

            if (!TextUtils.isEmpty(note)) {
                String id = databaseNotes.push().getKey();

                Notes notes = new Notes(id, category, note, rating);
                databaseNotes.child(id).setValue(notes);


                //   Map map = new HashMap();
                //  map.put("time", ServerValue.TIMESTAMP);
                //   databaseNotes.child(id).updateChildren(map);


                Toast.makeText(this, "Notes added", Toast.LENGTH_SHORT).show();
                editText.getText().clear();
                editText2.getText().clear();
                rb.setChecked(false);
            } else {
                Toast.makeText(this, "Please enter note", Toast.LENGTH_SHORT).show();
            }
        }

        public static String getTimeDate(long timeStamp) {
            try {
                DateFormat dateFormat = getDateTimeInstance();
                Date netDate = (new Date(timeStamp));
                return dateFormat.format(netDate);
            } catch (Exception e) {
                return "date";
            }
        }



    }