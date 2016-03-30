package programs.taskoid;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class TaskListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_list);

        String[] items = {"1","2","3"};

        ListView list = (ListView)findViewById(R.id.task_list);
        list.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, items));



    }
}
