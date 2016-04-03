package programs.taskoid;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Date;

public class TaskListActivity extends AppCompatActivity {

    private static final String TAG = "TaskListActivity";
    private static final int EDIT_TASK_REQUEST = 10;

    private ArrayList<Task>mTasks;
    private int mLastPositionClicked;
    private TaskAdapter mAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_list);

        mTasks = new ArrayList<Task>();
        mTasks.add(new Task());
        mTasks.get(0).setName("Task 1");
        mTasks.get(0).setDueDate(new Date());
        mTasks.add(new Task());
        mTasks.get(1).setName("Task 2");
        mTasks.get(1).setDueDate(new Date());
        mTasks.add(new Task());
        mTasks.get(2).setName("Task 3");
        mTasks.get(2).setDueDate(new Date());

        ListView listView = (ListView)findViewById(R.id.task_list);
        mAdapter = new TaskAdapter(mTasks);
        listView.setAdapter(mAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mLastPositionClicked = position;
                Intent i = new Intent(TaskListActivity.this, TaskActivity.class);
                Task task = (Task)parent.getAdapter().getItem(position);
                i.putExtra(TaskActivity.EXTRA,task);
                startActivityForResult(i,EDIT_TASK_REQUEST);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == EDIT_TASK_REQUEST){
            if (resultCode == RESULT_OK){
                Task task = (Task)data.getSerializableExtra(TaskActivity.EXTRA);
                mTasks.set(mLastPositionClicked,task);
                mAdapter.notifyDataSetChanged();
                Log.d(TAG,task.getName());
            }
        }
    }

    private class TaskAdapter extends ArrayAdapter<Task>{
        TaskAdapter(ArrayList<Task> tasks){
            super(TaskListActivity.this, R.layout.task_list_row,R.id.task_item_name, tasks);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            convertView =  super.getView(position, convertView, parent);
            Task task = getItem(position);
            TextView taskName = (TextView)convertView.findViewById(R.id.task_item_name);
            taskName.setText(task.getName());

            CheckBox doneBox = (CheckBox)convertView.findViewById(R.id.task_item_done);
            doneBox.setChecked(task.isDone());

            return convertView;
        }
    }
}
