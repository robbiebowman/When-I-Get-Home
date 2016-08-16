package com.example.robbiebowman.whenigethome;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import org.joda.time.DateTime;

import java.util.List;

public class ReminderListAdapter extends ArrayAdapter<Reminder> {

    private List<Reminder> reminders;
    private Context context;

    public ReminderListAdapter(Context context, List<Reminder> objects) {
        super(context, -1, objects);
        reminders = objects;
        this.context = context;
    }


    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        final LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.reminder, viewGroup, false);
        TextView text = (TextView) rowView.findViewById(R.id.text);
        text.setText(reminders.get(i).getText());
        final int noteIndex = i;
        Button deleteButton = (Button) rowView.findViewById(R.id.deleteButton);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseHelper dbHelper = new DatabaseHelper(getContext());
                dbHelper.removeReminder(reminders.get(noteIndex));
                reminders.remove(noteIndex);
                notifyDataSetChanged();
            }
        });
        return rowView;
    }

    public void addNote(String note) {
        DatabaseHelper dbHelper = new DatabaseHelper(getContext());
        Reminder reminder = new Reminder();
        reminder.setText(note);
        reminder.setDateTime(DateTime.now());
        dbHelper.saveReminder(reminder);
        reminders.add(reminder);
        notifyDataSetChanged();
    }
}
