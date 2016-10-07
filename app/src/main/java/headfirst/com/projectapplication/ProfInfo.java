package headfirst.com.projectapplication;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.ogaclejapan.smarttablayout.SmartTabLayout;

import java.util.ArrayList;

public class ProfInfo extends AppCompatActivity {

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
        setContentView(R.layout.activity_prof_info);

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
        getMenuInflater().inflate(R.menu.menu_prof_info, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
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
        private Context mcontext;

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
            mcontext = getActivity().getApplicationContext();
            String dept = String.valueOf(getArguments().getInt(ARG_SECTION_NUMBER));
            DatabaseHelper dbHelper = new DatabaseHelper(mcontext);
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            final ArrayList<String> prof_list = new ArrayList<>();
            final ArrayList<String> prof_image = new ArrayList<>();
            final ArrayList<String> prof_info = new ArrayList<>();
            final ArrayList<Integer> prof_course1 = new ArrayList<>();
            final ArrayList<Integer> prof_course2 = new ArrayList<>();
            final ArrayList<Integer> prof_ID = new ArrayList<>();
            String sql = "Select * from "+ DatabaseHelper.TABLE_Prof + " where " + DatabaseHelper.Dept_id+ " = "+ dept + " ;" ;
            Cursor cursor = db.rawQuery(sql, null);
            while(cursor.moveToNext())
            {

                prof_list.add(cursor.getString(cursor.getColumnIndex(DatabaseHelper.Prof_name)));
                prof_image.add(cursor.getString(cursor.getColumnIndex(DatabaseHelper.Prof_image)));
                prof_info.add(cursor.getString(cursor.getColumnIndex(DatabaseHelper.Prof_info)));
                prof_course1.add(cursor.getInt(cursor.getColumnIndex(DatabaseHelper.Course1_id)));
                prof_course2.add(cursor.getInt(cursor.getColumnIndex(DatabaseHelper.Course2_id)));
                prof_ID.add(cursor.getInt(cursor.getColumnIndex(DatabaseHelper.Prof_id)));
            }
            cursor.close();
            View rootView = inflater.inflate(R.layout.fragment_prof_info, container, false);
            ListView listView = (ListView) rootView.findViewById(R.id.prof_list);

            ArrayAdapter<String> adapter = new ArrayAdapter<>(mcontext, R.layout.simple_1, prof_list);
            listView.setAdapter(adapter);

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent intent = new Intent(mcontext, prof_detail.class);
                    intent.putExtra("NAME", prof_list.get(position));
                    intent.putExtra("IMAGE",prof_image.get(position));
                    intent.putExtra("INFO",prof_info.get(position));
                    intent.putExtra("COURSE1", prof_course1.get(position));
                    intent.putExtra("COURSE2", prof_course2.get(position));
                    intent.putExtra("ID", prof_ID.get(position));
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
            return 8;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "CS";
                case 1:
                    return "ECE";
                case 2:
                    return "ME";
                case 3:
                    return "ITM";
                case 4:
                    return "ARCH";
            }
            return null;
        }
    }
}
