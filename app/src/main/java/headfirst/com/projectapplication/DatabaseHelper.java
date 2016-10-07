package headfirst.com.projectapplication;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by Dell on 3/26/2016.
 */


//DatabaseHelper db = DatabaseHelper.getInstance(getApplicationContext());
public class DatabaseHelper extends SQLiteOpenHelper {

    //Database name
    public static final String DATABASE_NAME = "ProfPedia.db";
    //database path
    private static String DB_PATH = "/data/data/headfirst.com.projectapplication/databases/";
    public static DatabaseHelper instance = null;
    private SQLiteDatabase myDataBase;
    public final Context myContext;
    public static final int DATABASE_VERSION = 3;

    // table names
    public static final String TABLE_Prof = "Professors";
    public static final String TABLE_Course = "Courses";
    public static final String TABLE_Department = "Departments";
    public static final String TABLE_Users = "Users";
    public static final String TABLE_Reviews = "Reviews";
    public static final String TABLE_Semester="Semesters";
 

    //column names of Professors table
    public static final String Prof_id ="Prof_id";
    public static final String Prof_info ="Prof_info";
    public static final String Prof_name ="Prof_name";
    public static final String Prof_image ="Prof_image";
    public static final String Course1_id="Course1_id";
    public static final String Course2_id="Course2_id";
    public static final String Course3_id="Course3_id";

    //column names of Courses table
    public static final String Course_name="Course_name";
    public static final String Course_crn="Course_crn";
    public static final String Course_info="Course_info";
    public static final String Course_id="Course_id";
    public static final String Semester="Semester";

    //column names of Dept table
    public static final String Dept_id="Dept_id"   ;
    public static final String Dept_name="Dept_name" ;
    public static final String Dept_info="Dept_info";

    //cloumn names of Semester table
    public static final String Semester_id="Semester_id"   ;
    public static final String Semester_name="Semester_name" ;

    //column names for review table
    public static final String Review_id ="Review_id";
    public static final String Rating ="Rating";
    public static final String Review ="Review";
    public static final String Helpfulness ="Helpfulness";
    public static final String Clarity ="Clarity";
    public static final String Easiness ="Easiness";

    //column names for User Registration
    public static final String User_id ="User_id";
    public static final String User_name="User_name";
    public static final String Password ="Password";
    public static final String email_id = "email_id";
    public static final String phone_number ="Phone_number";
    public static final String A_number = "A_number";

 

    //prof_id PK,course_id FK,dept_id FK,Prof_name,Prof_info,Prof_image          //PK-primary key FK-foreign key
    private static final String Prof_TABLE_create = "CREATE TABLE "
            + TABLE_Prof + "(" + Prof_id +" INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, " + Course1_id+" INTEGER, " + Course2_id+" INTEGER, " + Course3_id+" INTEGER, " + Dept_name + " TEXT," +
            Dept_id+ " INTEGER, " + Prof_name+" TEXT NOT NULL, " + Prof_info+ " TEXT, " + Prof_image+ " TEXT, "
            +" FOREIGN KEY(Dept_name) REFERENCES Departments(Dept_name), " + " FOREIGN KEY(Course1_id) REFERENCES Courses(Course_id), " +
            " FOREIGN KEY(Course2_id) REFERENCES Courses(Course_id), " + " FOREIGN KEY(Course3_id) REFERENCES Courses(Course_id), "+
            " FOREIGN KEY(Dept_id) REFERENCES Departments(Dept_id) " + ")";


    //c_id PK, dept_id FK, Course_name, Course_info
    private static final String Course_TABLE_create = "CREATE TABLE " + TABLE_Course + "(" + Course_id+" INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, "
            +  Course_name + " TEXT, " + Dept_id+" INTEGER, " + Course_info+" TEXT, " + Course_crn+" TEXT, "+ Semester_id+ " INT, " +
            " FOREIGN KEY(Dept_id) REFERENCES Departments(Dept_id), " + "FOREIGN KEY(Semester_id) REFERENCES Semester(Semester_id) " + ")";

    //D_id PK, Dept_name, Dept_info
    private static final String Dept_Table_create = "CREATE TABLE " + TABLE_Department + "(" + Dept_id+ " INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, "
            +  Dept_name+" TEXT, " + Dept_info+" TEXT " + ")";

    private static final String Semester_Table_create = "CREATE TABLE " + TABLE_Semester + "(" + Semester_id+ " INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, "
            +  Semester_name+" TEXT " + ")";

    private static final String User_Table_create = "CREATE TABLE " + TABLE_Users + "(" + User_id + " INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, "+User_name+
            " TEXT NOT NULL, " + Password + " TEXT NOT NULL, " + email_id + " TEXT NOT NULL, " + phone_number+ " INTEGER NOT NULL, " + A_number+ " INTEGER NOT NULL " + ")";

    private static final String Review_Table_create = "CREATE TABLE " + TABLE_Reviews +"(" + Review_id + " INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, " + Rating + " INTEGER, "
            + Review + " TEXT, " + Course_id+" INTEGER, "+Prof_id+" INTEGER,"+ Helpfulness + " TEXT, "+ Clarity + " TEXT, "+ Easiness + " TEXT, "+ " FOREIGN KEY(Course_id) REFERENCES Courses(Course_id), " + " FOREIGN KEY(Prof_id) REFERENCES Professors(Prof_id) " + ")" ;

    

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.myContext = context;
        //Log.d("info", "Success");
    }

    public void createDataBase() throws IOException {

        boolean dbExist = checkDataBase();

        if (dbExist) {
            // do nothing
        } else {

            this.getReadableDatabase();
            try {
                copyDataBase();
            } catch (IOException e) {
                throw new Error("Error copying database");
            }
        }

    }

    private boolean checkDataBase() {
        SQLiteDatabase checkDB = null;
        try {
            String myPath = DB_PATH + DATABASE_NAME;
            checkDB = SQLiteDatabase.openDatabase(myPath, null,
                    SQLiteDatabase.OPEN_READONLY);
        } catch (SQLiteException e) {		}

        if (checkDB != null) {
            checkDB.close();
        }
        return checkDB != null ? true : false;
    }

    private void copyDataBase() throws IOException {

        InputStream myInput = myContext.getAssets().open(DATABASE_NAME);

        String outFileName = DB_PATH + DATABASE_NAME;

        OutputStream myOutput = new FileOutputStream(outFileName);

        byte[] buffer = new byte[1024];
        int length;
        while ((length = myInput.read(buffer)) > 0) {
            myOutput.write(buffer, 0, length);
        }

        myOutput.flush();
        myOutput.close();
        myInput.close();

    }

    public void openDataBase() throws SQLException {
        String myPath = DB_PATH + DATABASE_NAME;
        myDataBase = SQLiteDatabase.openDatabase(myPath, null,
                SQLiteDatabase.OPEN_READONLY);
    }

    @Override
    public synchronized void close() {
        if (myDataBase != null)
            myDataBase.close();
        super.close();
    }


    public void onCreate(SQLiteDatabase database) {
        database.execSQL(Prof_TABLE_create);
        database.execSQL(Course_TABLE_create);
        database.execSQL(Dept_Table_create);
        database.execSQL(User_Table_create);
        database.execSQL(Review_Table_create);
        database.execSQL(Semester_Table_create);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public static DatabaseHelper getInstance(Context context) {
        if (instance == null) {
            instance = new DatabaseHelper(context.getApplicationContext());
        }
        return instance;
    }
}