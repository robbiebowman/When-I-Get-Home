package com.example.robbiebowman.whenigethome;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.List;

public class NoteListAdapter extends ArrayAdapter<String> {

    private List<String> notes;
    private Context context;

    public NoteListAdapter(Context context, List<String> objects) {
        super(context, -1, objects);
        notes = objects;
        this.context = context;
    }


    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        final LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.reminder, viewGroup, false);
        TextView text = (TextView) rowView.findViewById(R.id.text);
        text.setText(notes.get(i));
        final int noteIndex = i;
        Button deleteButton = (Button) rowView.findViewById(R.id.deleteButton);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                notes.remove(noteIndex);
                notifyDataSetChanged();
            }
        });
        return rowView;
    }

    public void addNote(String note) {
        notes.add(note);
        notifyDataSetChanged();
    }
}
