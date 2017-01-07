package kamil.michalski.todolist;

import android.content.Context;

import com.j256.ormlite.android.AndroidConnectionSource;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.support.ConnectionSource;

import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

/**
 * Created by ppg38 on 07.01.2017.
 */

public class SqliteTaskDatabase implements ITaskDataBase {

    private Dao<TodoTask, Integer> mDao;

    public SqliteTaskDatabase(Context contex) {
        TodoDbOpenHelper dbHelper = new TodoDbOpenHelper(contex);
        ConnectionSource cs = new AndroidConnectionSource(dbHelper);
        try {
            mDao = DaoManager.createDao(cs,TodoTask.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<TodoTask> getTasks() {
        try {
            return mDao.queryBuilder()
                    .orderBy("done",true)
                    .orderBy("dateCreated",false)
                    .query();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Collections.emptyList();
    }

    @Override
    public void addTask(TodoTask task) {
        try {
            mDao.create(task);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public TodoTask getTask(int position) {
        try {
            return mDao.queryForId(position);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void updateTask(TodoTask task, int position) {
        try {
            mDao.update(task);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
