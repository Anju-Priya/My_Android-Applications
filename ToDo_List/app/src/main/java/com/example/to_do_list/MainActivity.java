package com.example.to_do_list;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;

import com.example.todolist.R;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private EditText taskNameEditText;
    private EditText taskDescriptionEditText;
    private Button addTaskButton;
    private ListView taskListView;

    private TaskRepository taskRepo;
    private ArrayAdapter<Task> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        taskNameEditText = findViewById(R.id.taskName);
        taskDescriptionEditText = findViewById(R.id.taskDescription);
        addTaskButton = findViewById(R.id.addTaskButton);
        taskListView = findViewById(R.id.taskListView);

        taskRepo = new TaskRepository(this);
        taskRepo.open();

        List<Task> tasks = taskRepo.getAllTasks();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, tasks);
        taskListView.setAdapter(adapter);

        addTaskButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = taskNameEditText.getText().toString();
                String description = taskDescriptionEditText.getText().toString();

                if (!name.isEmpty()) {
                    Task newTask = taskRepo.createTask(name, description);
                    adapter.add(newTask);
                    taskNameEditText.setText("");
                    taskDescriptionEditText.setText("");
                }
            }
        });

        taskListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Task selectedTask = (Task) parent.getItemAtPosition(position);
                showEditDeleteDialog(selectedTask);
            }
        });
    }

    private void showEditDeleteDialog(final Task task) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Edit or Delete Task");

        builder.setItems(new CharSequence[]{"Edit", "Delete"}, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (which == 0) { // Edit
                    showEditTaskDialog(task);
                } else if (which == 1) { // Delete
                    taskRepo.deleteTask(task.getId());
                    adapter.remove(task);
                }
            }
        });

        builder.show();
    }

    private void showEditTaskDialog(final Task task) {
        View view = getLayoutInflater().inflate(R.layout.dialog_edit_task, null);
        final EditText editName = view.findViewById(R.id.editTaskName);
        final EditText editDescription = view.findViewById(R.id.editTaskDescription);

        editName.setText(task.getName());
        editDescription.setText(task.getDescription());

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Edit Task");
        builder.setView(view);
        builder.setPositiveButton("Save", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String name = editName.getText().toString();
                String description = editDescription.getText().toString();

                Task updatedTask = taskRepo.updateTask(task.getId(), name, description);
                adapter.remove(task);
                adapter.add(updatedTask);
            }
        });
        builder.setNegativeButton("Cancel", null);
        builder.show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        taskRepo.close();
    }
}
