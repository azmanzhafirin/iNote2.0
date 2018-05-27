package demo.inote;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class NoteList extends ArrayAdapter<Notes> {
    private Activity context;
    private List<Notes> noteList;

    public NoteList(Activity context, List<Notes> noteList){
        super(context, R.layout.list_layout, noteList);
        this.context = context;
        this.noteList = noteList;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.list_layout, null, true);

        TextView textViewCategory = (TextView) listViewItem.findViewById(R.id.textViewCategory);
        TextView textViewNote = (TextView) listViewItem.findViewById(R.id.textViewNote);
        TextView textViewRating = (TextView) listViewItem.findViewById(R.id.textViewRating);

        Notes note = noteList.get(position);

        textViewCategory.setText(note.getNoteCategory());
        textViewNote.setText(note.getNote());
        textViewRating.setText(note.getRating());

        return listViewItem;
    }
}
