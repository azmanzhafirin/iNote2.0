package demo.inote;

import android.content.DialogInterface;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MyNotes extends AppCompatActivity {

    ListView listViewNotes;
    List<Notes> notesList;
    DatabaseReference databaseNotes;
    TextView category;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_notes);

        databaseNotes = FirebaseDatabase.getInstance().getReference("notes");

        listViewNotes = (ListView) findViewById(R.id.listViewNotes);
        category = (TextView) findViewById(R.id.textViewCategory);
        notesList = new ArrayList<>();


       listViewNotes.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
           @Override
           public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
               Notes notes = notesList.get(i);
               showUpdateDialog(notes.getNoteId(), notes.getNote(), notes.getRating());
               return false;
           }
       });

    }


    private void showUpdateDialog(final String noteId, String noteName, String noteRating){

        final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);

        LayoutInflater inflater = getLayoutInflater();

        final View dialogView = inflater.inflate(R.layout.update_note, null);

        dialogBuilder.setView(dialogView);

        final EditText editTextCategory = (EditText) dialogView.findViewById(R.id.editTextCategory);
        final Button buttonDelete = (Button) dialogView.findViewById(R.id.buttonDelete);
        final Button buttonCancel = (Button) dialogView.findViewById(R.id.buttonCancel);

        dialogBuilder.setTitle("Delete Confirmation");
        dialogBuilder.setMessage("Are you sure you want to delete this note?");
        dialogBuilder.setIcon(R.drawable.icon_delete2);
        //dialogBuilder.setCancelable(true);

        final AlertDialog alertDialog = dialogBuilder.create();
        alertDialog.show();


        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteNote(noteId);

            }
        });

        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
            }
        });

    }

    private void deleteNote(String noteId) {
        DatabaseReference drNote = FirebaseDatabase.getInstance().getReference("notes").child(noteId);
        drNote.removeValue();
        Toast.makeText(this, "Note is deleted", Toast.LENGTH_LONG).show();
    }


    @Override
    protected void onStart() {
        super.onStart();
        databaseNotes.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                notesList.clear();
                for(DataSnapshot noteSnapshot : dataSnapshot.getChildren()){
                //    Map<String, String> map = (Map<String, String>) dataSnapshot.getValue();
                  //  Log.d("test", "map" + map);

                 //  String category2 = map.get(map);
                //   Log.d("LOOK HERE", "hola" +category2);
                   //category.setText(category2);
                   Notes notes = noteSnapshot.getValue(Notes.class);
                    notesList.add(notes);

                }

                NoteList adapter = new NoteList(MyNotes.this, notesList);
                listViewNotes.setAdapter(adapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }


}
