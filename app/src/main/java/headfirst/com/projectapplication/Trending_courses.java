package headfirst.com.projectapplication;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.ogaclejapan.smarttablayout.SmartTabLayout;

import java.util.ArrayList;

public class Trending_courses extends AppCompatActivity {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_info);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        SmartTabLayout viewPagerTab = (SmartTabLayout) findViewById(R.id.viewpagertab);
        viewPagerTab.setViewPager(mViewPager);


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_course_info, menu);
        return true;
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

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";
        private Context mContext;
        public PlaceholderFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_course_info, container, false);
            mContext = getActivity().getApplicationContext();
            String dept = String.valueOf(getArguments().getInt(ARG_SECTION_NUMBER));
            DatabaseHelper dbHelper = new DatabaseHelper(mContext);
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            final ArrayList<String> course_list = new ArrayList<>();
            final ArrayList<Integer> course_id = new ArrayList<>();
            final ArrayList<String> course_info = new ArrayList<>();
            final ArrayList<String> course_crn = new ArrayList<>();
            final ArrayList<String> Semester_list = new ArrayList<>();

            String sqlline = "Select * from "+ DatabaseHelper.TABLE_Course + " where " + DatabaseHelper.Semester_id+ " = "+ dept + " ;" ;
            Cursor cursor = db.rawQuery(sqlline, null);
            while(cursor.moveToNext())
            {
                Semester_list.add(cursor.getString(cursor.getColumnIndex(DatabaseHelper.Course_name)));
                course_list.add(cursor.getString(cursor.getColumnIndex(DatabaseHelper.Course_name)));
                course_id.add(cursor.getInt(cursor.getColumnIndex(DatabaseHelper.Course_id)));
                course_info.add(cursor.getString(cursor.getColumnIndex(DatabaseHelper.Course_info)));
                course_crn.add(cursor.getString(cursor.getColumnIndex(DatabaseHelper.Course_crn)));

            }
            cursor.close();
            ListView listView = (ListView) rootView.findViewById(R.id.coursesList);
            ArrayAdapter<String> adapter = new ArrayAdapter<>(mContext, R.layout.simple_1, Semester_list);
            String[] list={};
            listView.setAdapter(adapter);

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent intent = new Intent(mContext, Course_Detail.class);
                    intent.putExtra("ID", course_id.get(position));
                    intent.putExtra("NAME", course_list.get(position));
                    intent.putExtra("INFO", course_info.get(position));
                    intent.putExtra("CRN", course_crn.get(position));

                    startActivity(intent);
                }
            });
            return rootView;
        }
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            return PlaceholderFragment.newInstance(position + 1);
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "Summer 2016";
                case 1:
                    return "Fall 2016";
                case 2:
                    return "Spring 2017";

            }
            return null;
        }
    }
}
