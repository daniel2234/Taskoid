package programs.taskoid;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;

import java.util.Calendar;
import java.util.Date;

public class TaskActivity extends AppCompatActivity {

    public static final String EXTRA = "TaskExtra";

    private Calendar mCal;
    private Task mTask;
    private Button mDateButton;
    private EditText mTaskNameInput;
    private CheckBox mDonebox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task);

        mTask = (Task)getIntent().getSerializableExtra(EXTRA);

        mCal = Calendar.getInstance();

        mTaskNameInput = (EditText)findViewById(R.id.task_name);
        mDateButton = (Button)findViewById(R.id.task_date);
        mDonebox = (CheckBox)findViewById(R.id.task_done);
        Button saveButton = (Button)findViewById(R.id.save_button);

        mTaskNameInput.setText(mTask.getName());
        if (mTask.getDueDate() == null){
            mCal.setTime(new Date());
            mDateButton.setText(getResources().getString(R.string.no_date));
        } else {
            mCal.setTime(mTask.getDueDate());
            updateButton();
        }
        mDonebox.setChecked(mTask.isDone());

        mDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog dpd = new DatePickerDialog(TaskActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        mCal.set(Calendar.YEAR,year);
                        mCal.set(Calendar.MONTH, monthOfYear);
                        mCal.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                        updateButton();
                    }
                }, mCal.get(Calendar.YEAR), mCal.get(Calendar.MONTH), mCal.get(Calendar.DAY_OF_MONTH));

                dpd.show();
            }
        });

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mTask.setName(mTaskNameInput.getText().toString());
                mTask.setDone(mDonebox.isChecked());
                mTask.setDueDate(mCal.getTime());

                Intent i = new Intent();
                i.putExtra(EXTRA,mTask);
                setResult(RESULT_OK,i);
                finish();
            }
        });
    }

    private void updateButton(){
        java.text.DateFormat df = java.text.DateFormat.getDateInstance();
        mDateButton.setText(df.format(mCal.getTime()));
    }
}
