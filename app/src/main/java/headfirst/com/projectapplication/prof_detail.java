package headfirst.com.projectapplication;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import info.hoang8f.widget.FButton;
public class prof_detail extends AppCompatActivity {

    private TextView textView;
    private ImageView imageView;
    private TextView INFO;
    private FButton Hide;
    private int ID;
    private TextView helptv;
    private TextView claritytv;
    private SQLiteDatabase db;
    private TextView easytv;
    private ListView Review_List;
    private RelativeLayout rlay;
    private RelativeLayout upperlay;
    private RelativeLayout lowerlay;
    private LinearLayout relay;
    private RelativeLayout slay;
    private RelativeLayout buttonlay;
    private Context mContext;
    private int pos;
    private RatingBar hratingBar;
    private RatingBar cratingBar;
    private  RatingBar eratingBar;
    private RatingBar hratingBar2;
    private RatingBar cratingBar2;
    private  RatingBar eratingBar2;
    private FButton Submit;
    private FButton showinf;
    private FButton showrev;
    private FButton addrev;
    private EditText Comment;
    private MyApplication app;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prof_detail);
        Intent intent = getIntent();
        final String Name = intent.getStringExtra("NAME");
        String Image = intent.getStringExtra("IMAGE");
        String Info = intent.getStringExtra("INFO");
        rlay = (RelativeLayout)findViewById(R.id.listlayx);
        upperlay = (RelativeLayout)findViewById(R.id.upper_lay);
        upperlay.setVisibility(View.VISIBLE);
        lowerlay = (RelativeLayout)findViewById(R.id.lower_lay);
        lowerlay.setVisibility(View.GONE);
        rlay.setVisibility(View.GONE);
        relay = (LinearLayout)findViewById(R.id.listlay0);
        relay.setVisibility(View.GONE);
        slay = (RelativeLayout)findViewById(R.id.listlayy);
        slay.setVisibility(View.GONE);
        buttonlay = (RelativeLayout)findViewById(R.id.buttonlay);
        buttonlay.setVisibility(View.GONE);
        mContext = getApplicationContext();
        final ArrayList<String> course_list = new ArrayList<>();
        final int course1 = intent.getIntExtra("COURSE1", 0);
        final int course2 = intent.getIntExtra("COURSE2", 0);
        ID = intent.getIntExtra("ID",0);
        DatabaseHelper dbHelper = new DatabaseHelper(this);
        db = dbHelper.getWritableDatabase();
        String sql = "Select * from "+ DatabaseHelper.TABLE_Course + " where " + DatabaseHelper.Course_id+ " = "+ course1 + " or "+ DatabaseHelper.Course_id+"="+course2 +";" ;
        Cursor cursor = db.rawQuery(sql, null);
        hratingBar = new RatingBar(mContext, null, android.R.attr.ratingBarStyleSmall);
        hratingBar = (RatingBar)findViewById(R.id.helpratingBar);
        cratingBar = new RatingBar(mContext, null, android.R.attr.ratingBarStyleSmall);
        cratingBar = (RatingBar)findViewById(R.id.clarityratingBar);
        eratingBar = new RatingBar(mContext, null, android.R.attr.ratingBarStyleSmall);
        eratingBar = (RatingBar)findViewById(R.id.easyratingBar);
        hratingBar2 = new RatingBar(mContext, null, android.R.attr.ratingBarStyleSmall);
        hratingBar2 = (RatingBar)findViewById(R.id.helpratingBar2);
        cratingBar2 = new RatingBar(mContext, null, android.R.attr.ratingBarStyleSmall);
        cratingBar2 = (RatingBar)findViewById(R.id.clarityratingBar2);
        eratingBar2 = new RatingBar(mContext, null, android.R.attr.ratingBarStyleSmall);
        eratingBar2 = (RatingBar)findViewById(R.id.easyratingBar2);
        helptv = (TextView)findViewById(R.id.helptv);
        claritytv = (TextView)findViewById(R.id.claritytv);
        easytv = (TextView)findViewById(R.id.easytv);
        final ArrayList<Integer> courseid = new ArrayList<>();
        while(cursor.moveToNext())
        {
            course_list.add(cursor.getString(cursor.getColumnIndex(DatabaseHelper.Course_name)));
            courseid.add(cursor.getInt(cursor.getColumnIndex(DatabaseHelper.Course_id)));
        }
        cursor.close();
        final ListView courselist =  (ListView) findViewById(R.id.excourselist);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.simple_1, course_list);
        courselist.setAdapter(adapter);
        showrev = (FButton) findViewById(R.id.submit1);
        Submit=(FButton) findViewById(R.id.submit);
        addrev = (FButton) findViewById(R.id.hide1);
        app=(MyApplication)getApplication();
        if(app.getComment()==0)
            addrev.setVisibility(View.GONE);//if comment is 0, remove the add review button
        else
            addrev.setVisibility(View.VISIBLE);//if comment is 1, resume the add review button
        showinf = (FButton) findViewById(R.id.binfo);
        courselist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView textView = (TextView) view.findViewById(R.id.text1);
                textView.setTextColor(Color.RED);
                buttonlay.setVisibility(View.VISIBLE);
                pos = position;
                showrev.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        upperlay.setVisibility(View.GONE);
                        lowerlay.setVisibility(View.VISIBLE);
                        int pid = courseid.get(pos);
                        String sql2 = "Select * from " + DatabaseHelper.TABLE_Reviews + " where " + DatabaseHelper.Course_id + " = " + pid + " and " + DatabaseHelper.Prof_id + " = " + ID + ";";
                        final ArrayList<String> Rev_list = new ArrayList<>();
                        final ArrayList<Integer> help_list = new ArrayList<>();
                        final ArrayList<Integer> clarity_list = new ArrayList<>();
                        final ArrayList<Integer> easy_list = new ArrayList<>();
                        Cursor cursor = db.rawQuery(sql2, null);
                        while (cursor.moveToNext()) {
                            Rev_list.add(cursor.getString(cursor.getColumnIndex(DatabaseHelper.Review)));
                            help_list.add(cursor.getInt(cursor.getColumnIndex(DatabaseHelper.Helpfulness)));
                            clarity_list.add(cursor.getInt(cursor.getColumnIndex(DatabaseHelper.Clarity)));
                            easy_list.add(cursor.getInt(cursor.getColumnIndex(DatabaseHelper.Easiness)));
                        }
                        cursor.close();
                        float helpavg = 0;
                        float cavg = 0;
                        float eavg = 0;
                        for (int i = 0; i < help_list.size(); i++) {
                            helpavg += help_list.get(i);
                            cavg += clarity_list.get(i);
                            eavg += clarity_list.get(i);
                        }
                        helpavg = helpavg / help_list.size();
                        cavg = cavg / help_list.size();
                        eavg = eavg / help_list.size();
                        hratingBar2.setRating(helpavg);
                        cratingBar2.setRating(cavg);
                        eratingBar2.setRating(eavg);
                        Review_List = (ListView) findViewById(R.id.review_list);
                        ArrayAdapter<String> adapter = new ArrayAdapter<>(mContext, R.layout.simple_1, Rev_list);
                        Review_List.setAdapter(adapter);
                        rlay.setVisibility(View.VISIBLE);
                        relay.setVisibility(View.VISIBLE);
                        slay.setVisibility(View.GONE);
                    }
                });
                addrev.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        upperlay.setVisibility(View.GONE);
                        lowerlay.setVisibility(View.VISIBLE);
                        slay.setVisibility(View.VISIBLE);
                        rlay.setVisibility(View.GONE);
                        relay.setVisibility(View.GONE);
                        Comment = (EditText) findViewById(R.id.comment1);
                        Submit.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                int happy;
                                int clarity;
                                int easy;
                                int prof_id;
                                happy = (int) hratingBar.getRating();
                                clarity = (int) cratingBar.getRating();
                                easy = (int) eratingBar.getRating();
                                String comment = Comment.getText().toString();
                                prof_id = courseid.get(0);
                                String email="sum.rana05@gmail.com";
                                String courseName=course_list.get(pos);
                                String name =Name;

                                SendComments comments=new SendComments(prof_detail.this,email,comment,name,courseName);
                                comments.execute();

                                ContentValues values = new ContentValues();
                                values.put("Rating", happy);
                                values.put("Review", comment);
                                values.put("Course_id", prof_id);
                                values.put("Prof_id", ID);
                                values.put("Helpfulness", happy);
                                values.put("Clarity", clarity);
                                values.put("Easiness", easy);
                                db.insert("Reviews", null, values);
                                Comment.setText("");
                                hratingBar.setRating(0);
                                cratingBar.setRating(0);
                                eratingBar.setRating(0);
                            }
                        });
                    }
                });
                showinf.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        upperlay.setVisibility(View.VISIBLE);
                        lowerlay.setVisibility(View.GONE);
                        buttonlay.setVisibility(View.GONE);
                    }
                });
            }
        });
        textView = (TextView)findViewById(R.id.prof_name);
        textView.setText(Name);
        INFO = (TextView) findViewById(R.id.prof_info);
        INFO.setText(Info);
        imageView = (ImageView) findViewById(R.id.prof_image);
        int id = getResources().getIdentifier("headfirst.com.projectapplication:drawable/"+Image,null,null);
        imageView.setImageResource(id);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
