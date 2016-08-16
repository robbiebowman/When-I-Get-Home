package com.example.robbiebowman.whenigethome;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
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
        List<Reminder> reminders = new DatabaseHelper(this).getReminders();
        final ReminderListAdapter reminderListAdapter = new ReminderListAdapter(this, reminders);
        reminderList.setAdapter(reminderListAdapter);
        noteInput = (EditText) findViewById(R.id.noteInput);
        noteInput.setImeActionLabel("Add", KeyEvent.KEYCODE_ENTER);
        noteInput.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                boolean handled = false;
                if (i == KeyEvent.KEYCODE_ENTER)
                {
                    handled = true;
                    reminderListAdapter.addNote(noteInput.getText().toString());
                    noteInput.setText("");
                }
                return handled;
            }
        });
        addButton = (Button) findViewById(R.id.addButton);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                reminderListAdapter.addNote(noteInput.getText().toString());
                noteInput.setText("");
            }
        });
    }
}
