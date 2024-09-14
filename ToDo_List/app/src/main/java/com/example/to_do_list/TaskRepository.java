package com.example.to_do_list;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class TaskRepository {

    private SQLiteDatabase database;
    private DatabaseHelper dbHelper;

    public TaskRepository(Context context) {
        dbHelper = new DatabaseHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public Task createTask(String name, String description) {
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.COLUMN_NAME, name);
        values.put(DatabaseHelper.COLUMN_DESCRIPTION, description);
        long insertId = database.insert(DatabaseHelper.TABLE_TASKS, null, values);
        Cursor cursor = database.query(DatabaseHelper.TABLE_TASKS,
                new String[]{DatabaseHelper.COLUMN_ID, DatabaseHelper.COLUMN_NAME, DatabaseHelper.COLUMN_DESCRIPTION},
                DatabaseHelper.COLUMN_ID + " = " + insertId, null, null, null, null);
        cursor.moveToFirst();
        Task newTask = cursorToTask(cursor);
        cursor.close();
        return newTask;
    }

    public void deleteTask(long taskId) {
        database.delete(DatabaseHelper.TABLE_TASKS, DatabaseHelper.COLUMN_ID + " = " + taskId, null);
    }

    public List<Task> getAllTasks() {
        List<Task> tasks = new ArrayList<>();
        Cursor cursor = database.query(DatabaseHelper.TABLE_TASKS,
                new String[]{DatabaseHelper.COLUMN_ID, DatabaseHelper.COLUMN_NAME, DatabaseHelper.COLUMN_DESCRIPTION},
                null, null, null, null, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Task task = cursorToTask(cursor);
            tasks.add(task);
            cursor.moveToNext();
        }
        cursor.close();
        return tasks;
    }

    public Task updateTask(long taskId, String name, String description) {
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.COLUMN_NAME, name);
        values.put(DatabaseHelper.COLUMN_DESCRIPTION, description);
        database.update(DatabaseHelper.TABLE_TASKS, values, DatabaseHelper.COLUMN_ID + " = " + taskId, null);
        Cursor cursor = database.query(DatabaseHelper.TABLE_TASKS,
                new String[]{DatabaseHelper.COLUMN_ID, DatabaseHelper.COLUMN_NAME, DatabaseHelper.COLUMN_DESCRIPTION},
                DatabaseHelper.COLUMN_ID + " = " + taskId, null, null, null, null);
        cursor.moveToFirst();
        Task updatedTask = cursorToTask(cursor);
        cursor.close();
        return updatedTask;
    }

    private Task cursorToTask(Cursor cursor) {
        long id = cursor.getLong(0);
        String name = cursor.getString(1);
        String description = cursor.getString(2);
        return new Task(id, name, description);
    }
}
