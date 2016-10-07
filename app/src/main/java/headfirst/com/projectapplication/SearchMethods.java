package headfirst.com.projectapplication;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by fatemaalteneiji on 4/28/16.
 */
public class SearchMethods {
    private DatabaseHelper dbHelper;
    public SearchMethods(Context context) {
        dbHelper = new DatabaseHelper(context);
    }
    public Cursor getProfListByKeyword(String search) {
        //Open connection to read only
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String sql = "Select * from "+ DatabaseHelper.TABLE_Prof + " where " + DatabaseHelper.Prof_name+ " LIKE  '%"+ search + "%' ;" ;



        Cursor cursor = db.rawQuery(sql, null);
        // looping through all rows and adding to list

        if (cursor == null) {
            return null;
        } else if (!cursor.moveToFirst()) {
            cursor.close();
            return null;
        }
        return cursor;


    }
    public Cursor getCourseListByKeyword(String search) {
        //Open connection to read only
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String sql = "Select * from "+ DatabaseHelper.TABLE_Course + " where " + DatabaseHelper.Course_name+ " LIKE  '%"+ search + "%' ;" ;
        Cursor cursor = db.rawQuery(sql, null);
        // looping through all rows and adding to list

        if (cursor == null) {
            return null;
        } else if (!cursor.moveToFirst()) {
            cursor.close();
            return null;
        }
        return cursor;


    }
}