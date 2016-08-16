package com.example.robbiebowman.whenigethome;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ListView reminderList;
    private EditText noteInput;
    private Button addButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        reminderList = (ListView) findViewById(R.id.listView);
        List<String> notes = new ArrayList<>();
        notes.add("Hello, i need to remember stuff");
        notes.add("Hello - i need to do stuff");
        notes.add("Hello - i need to get stuff");
        final NoteListAdapter noteListAdapter = new NoteListAdapter(this, notes);
        reminderList.setAdapter(noteListAdapter);
        noteInput = (EditText) findViewById(R.id.noteInput);
        noteInput.setImeActionLabel("Add", KeyEvent.KEYCODE_ENTER);
        noteInput.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                boolean handled = false;
                if (i == KeyEvent.KEYCODE_ENTER)
                {
                    handled = true;
                    noteListAdapter.addNote(noteInput.getText().toString());
                    noteInput.setText("");
                }
                return handled;
            }
        });
        addButton = (Button) findViewById(R.id.addButton);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                noteListAdapter.addNote(noteInput.getText().toString());
                noteInput.setText("");
            }
        });
    }
}
