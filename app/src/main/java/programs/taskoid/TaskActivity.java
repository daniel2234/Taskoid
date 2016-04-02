package programs.taskoid;

import android.app.DatePickerDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;

import java.util.Calendar;

public class TaskActivity extends AppCompatActivity {

    public static final String EXTRA = "TaskExtra";

    private Calendar mCal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task);

        Task task = (Task)getIntent().getSerializableExtra(EXTRA);

        mCal = Calendar.getInstance();
        mCal.setTime(task.getDueDate());


        EditText taskNameInput = (EditText)findViewById(R.id.task_name);
        Button dateButton = (Button)findViewById(R.id.task_date);
        CheckBox doneBox = (CheckBox)findViewById(R.id.task_done);
        Button saveButton = (Button)findViewById(R.id.save_button);

        taskNameInput.setText(task.getName());
        if (task.getDueDate() == null){
            dateButton.setText(getResources().getString(R.string.no_date));
        } else {
            java.text.DateFormat df = java.text.DateFormat.getDateInstance();

            dateButton.setText(df.format(task.getDueDate()));
        }
        doneBox.setChecked(task.isDone());

        dateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog dpd = new DatePickerDialog(TaskActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                    }
                }, mCal.get(Calendar.YEAR), mCal.get(Calendar.MONTH), mCal.get(Calendar.DAY_OF_MONTH));

                dpd.show();
            }
        });

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}