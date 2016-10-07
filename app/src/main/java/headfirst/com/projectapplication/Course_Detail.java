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
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

import info.hoang8f.widget.FButton;
public class Course_Detail extends AppCompatActivity {
    private TextView crname;
    private TextView crinfo;
    private TextView crcrn;
    private int Id;
    private TextView helptv;
    private TextView claritytv;
    private SQLiteDatabase db;
    private TextView easytv;
    private ListView Review_List;
    private RelativeLayout rlay;
    private LinearLayout relay;
    private RelativeLayout lowerlay;
    private RelativeLayout slay;
    private RelativeLayout upperlay;
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
        setContentView(R.layout.activity_course__detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        Intent intent = getIntent();
        Id = intent.getIntExtra("ID", 0);
        final String Name = intent.getStringExtra("NAME");
        String Info = intent.getStringExtra("INFO");
        String CRN = intent.getStringExtra("CRN");
        rlay = (RelativeLayout)findViewById(R.id.listlayx);
        rlay.setVisibility(View.GONE);
        relay = (LinearLayout)findViewById(R.id.listlay0);
        upperlay = (RelativeLayout)findViewById(R.id.upper_lay);
        lowerlay = (RelativeLayout)findViewById(R.id.lower_lay);
        lowerlay.setVisibility(View.GONE);
        relay.setVisibility(View.GONE);
        slay = (RelativeLayout)findViewById(R.id.listlayy);
        slay.setVisibility(View.GONE);
        buttonlay = (RelativeLayout)findViewById(R.id.buttonlay);
        buttonlay.setVisibility(View.GONE);
        mContext = getApplicationContext();
        crname = (TextView)findViewById(R.id.crs_name);
        crname.setText(Name);
        crinfo = (TextView)findViewById(R.id.crs_info);
        crinfo.setText(Info);
        crcrn = (TextView)findViewById(R.id.crs_crn);
        crcrn.setText(CRN);
        DatabaseHelper dbHelper = new DatabaseHelper(mContext);
        db = dbHelper.getWritableDatabase();
        final ArrayList<String> prof_list = new ArrayList<>();
        final ArrayList<Integer> prof_Id = new ArrayList<>();
        String sql = "Select * from "+ DatabaseHelper.TABLE_Prof + " where " + DatabaseHelper.Course1_id+ " = "+ Id +" or "+ DatabaseHelper.Course2_id+ " = "+ Id+";" ;
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
        while(cursor.moveToNext())
        {

            prof_list.add(cursor.getString(cursor.getColumnIndex(DatabaseHelper.Prof_name)));
            prof_Id.add(cursor.getInt(cursor.getColumnIndex(DatabaseHelper.Prof_id)));
        }
        cursor.close();
        ListView exlv =  (ListView) findViewById(R.id.excourselist);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(mContext, R.layout.simple_1, prof_list);
        exlv.setAdapter(adapter);
        showrev = (FButton) findViewById(R.id.submit1);
        showinf = (FButton) findViewById(R.id.binfo);
        Submit=(FButton) findViewById(R.id.submit);
        addrev = (FButton) findViewById(R.id.hide1);
        app=(MyApplication)getApplication();
        if(app.getComment()==0)
            addrev.setVisibility(View.GONE); //if comment is 0, remove the add review button
        else
            addrev.setVisibility(View.VISIBLE); //if comment is 1, resume the add review button
        exlv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                buttonlay.setVisibility(View.VISIBLE);
                TextView textView = (TextView) view.findViewById(R.id.text1);
                textView.setTextColor(Color.RED);
                pos = position;
                showrev.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        lowerlay.setVisibility(View.VISIBLE);
                        upperlay.setVisibility(View.GONE);
                        int pid = prof_Id.get(pos);
                        String sql2 = "Select * from " + DatabaseHelper.TABLE_Reviews + " where " + DatabaseHelper.Course_id + " = " + Id + " and " + DatabaseHelper.Prof_id + " = " + pid + ";";
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

                                prof_id = prof_Id.get(0);
                                ContentValues values = new ContentValues();

                                String comment = Comment.getText().toString();
                                String email="sum.rana05@gmail.com";
                                String courseName=Name;
                                String name =prof_list.get(pos);

                                SendComments comments=new SendComments(Course_Detail.this,email,comment,name,courseName);
                                comments.execute();
                                values.put("Rating", happy);
                                values.put("Review", comment);
                                values.put("Course_id", Id);
                                values.put("Prof_id", prof_id);
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
                        lowerlay.setVisibility(View.GONE);
                        upperlay.setVisibility(View.VISIBLE);
                        buttonlay.setVisibility(View.GONE);
                    }
                });
            }
        });
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
