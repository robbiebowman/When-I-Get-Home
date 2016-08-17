package com.example.robbiebowman.whenigethome;

import android.content.Context;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import org.joda.time.DateTime;
import org.joda.time.Period;
import org.joda.time.format.PeriodFormatter;
import org.joda.time.format.PeriodFormatterBuilder;
import org.ocpsoft.prettytime.PrettyTime;

import java.util.LinkedList;
import java.util.List;

public class ReminderListAdapter extends ArrayAdapter<Reminder> {

    private LinkedList<Reminder> reminders;
    private Context context;

    public ReminderListAdapter(Context context, List<Reminder> objects) {
        super(context, -1, objects);
        reminders = new LinkedList<>(objects);
        this.context = context;
    }


    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        final LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.reminder, viewGroup, false);

        TextView text = (TextView) rowView.findViewById(R.id.text);
        text.setText(reminders.get(i).getText());

        String relative = new PrettyTime().format(reminders.get(i).getDateTime().toDate());;
        TextView date = (TextView) rowView.findViewById(R.id.date);
        date.setText(relative);

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
        reminders.addFirst(reminder);
        notifyDataSetChanged();
    }

    private String getRelativeDateString(DateTime date) {
        Period period = new Period(date, DateTime.now());
        PeriodFormatter formatter = new PeriodFormatterBuilder()
                .appendSeconds().appendSuffix(" seconds ago\n")
                .appendMinutes().appendSuffix(" minutes ago\n")
                .appendHours().appendSuffix(" hours ago\n")
                .appendDays().appendSuffix(" days ago\n")
                .appendWeeks().appendSuffix(" weeks ago\n")
                .appendMonths().appendSuffix(" months ago\n")
                .appendYears().appendSuffix(" years ago\n")
                .printZeroNever()
                .toFormatter();

        return formatter.print(period);
    }
}
