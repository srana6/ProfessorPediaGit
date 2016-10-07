package headfirst.com.projectapplication;

import android.app.AlertDialog;
import android.app.SearchManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.login.LoginManager;
import com.facebook.login.widget.ProfilePictureView;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    //Defining Variables
    private Toolbar toolbar;
    private NavigationView navigationView;
    private DrawerLayout drawerLayout;
    public JSONObject response, profile_pic_data, profile_pic_url;
    public TextView user_name, user_email;
    public ImageView user_picture;
    public NavigationView navigation_view;
    private LoginManager mLoginManager;
    public ProfilePictureView profilePictureView;
    Fragment fragment = null;
    public String jsondata;
    public String username;
    public SharedPreferences sharedPreferences;
    public static final int pref=0;
    public SharedPreferences.Editor editor;
    private Cursor cursor;
    private Cursor cursor2;
    private SearchMethods searchMethods;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main_screen);

        Toolbar toolbar = (Toolbar) findViewById(R.id.tool_bar);
        toolbar.setTitle(" " + "ProfessorPedia");
        searchMethods = new SearchMethods(this);
        setSupportActionBar(toolbar);

        getSupportActionBar().setLogo(R.drawable.toolbarlogo);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayUseLogoEnabled(true);

        Intent intent = getIntent();

        String strAr[]=intent.getStringArrayExtra("strArray");
        jsondata=strAr[0];
        username=strAr[1];

        setNavigationHeader(username);    // call setNavigationHeader Method.
        setUserProfile(jsondata);  // ca


        // Initializing Drawer Layout and ActionBarToggle
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.openDrawer, R.string.closeDrawer){



            @Override
            public void onDrawerClosed(View drawerView) {
                // Code here will be triggered once the drawer closes as we dont want anything to happen so we leave this blank
                super.onDrawerClosed(drawerView);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                // Code here will be triggered once the drawer open as we dont want anything to happen so we leave this blank

                super.onDrawerOpened(drawerView);
            }
        };

        //Setting the actionbarToggle to drawer layout
        drawerLayout.setDrawerListener(actionBarDrawerToggle);

        //calling sync state is necessay or else your hamburger icon wont show up
        actionBarDrawerToggle.syncState();

        navigation_view.setNavigationItemSelectedListener(this);
        DatabaseHelper dbHelper = new DatabaseHelper(this);
        try {
            dbHelper.createDataBase();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            Log.d("info", "Failed");
        }
        displayView(0);

    }
    private void displayView(int position) {
        fragment = null;
        // String title = getString(R.string.app_name);
        switch (position) {
            case 0:
                fragment = new HomeFragment();
                //title = getString(R.string.title_home);
                break;
            default:
                break;
        }

        if (fragment != null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.container_body, fragment);
            fragmentTransaction.commit();


        }
    }

    public void setNavigationHeader(String logdinUser) {

        navigation_view = (NavigationView) findViewById(R.id.navigation_view);

        user_name = (TextView) findViewById(R.id.username);
        profilePictureView = (ProfilePictureView) findViewById(R.id.profile_image);
        user_email = (TextView) findViewById(R.id.email);

        user_name.setText(logdinUser);
    }

     public void setUserProfile(String jsondata1) {

         try {
             profilePictureView.setPresetSize(ProfilePictureView.NORMAL);
             profilePictureView.setProfileId(jsondata1);
         } catch (Exception e) {
             e.printStackTrace();
         }
     }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        // Retrieve the SearchView and plug it into SearchManager
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(menu.findItem(R.id.action_search));
        SearchManager searchManager = (SearchManager) getSystemService(SEARCH_SERVICE);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                cursor = searchMethods.getCourseListByKeyword(query);
                cursor2 = searchMethods.getProfListByKeyword(query);
                Intent in = new Intent(MainActivity.this, prof_detail.class);
                Intent in2 = new Intent(MainActivity.this, Course_Detail.class);
                if (cursor == null) {


                    if (cursor2 == null) {
                        final AlertDialog.Builder alertDialog=new AlertDialog.Builder(MainActivity.this);
                        alertDialog.setTitle("Alert!!!");
                        alertDialog.setMessage("No Records Found");
                        alertDialog.setCancelable(false);
                        alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });
                        AlertDialog dialog=alertDialog.create();
                        dialog.show();

                    } else {
                        in.putExtra("NAME", cursor2.getString(cursor2.getColumnIndex(DatabaseHelper.Prof_name)));
                        in.putExtra("IMAGE", cursor2.getString(cursor2.getColumnIndex(DatabaseHelper.Prof_image)));
                        in.putExtra("INFO", cursor2.getString(cursor2.getColumnIndex(DatabaseHelper.Prof_info)));
                        in.putExtra("COURSE1", cursor2.getInt(cursor2.getColumnIndex(DatabaseHelper.Course1_id)));
                        in.putExtra("COURSE2", cursor2.getInt(cursor2.getColumnIndex(DatabaseHelper.Course2_id)));
                        in.putExtra("ID", cursor2.getInt(cursor2.getColumnIndex(DatabaseHelper.Prof_id)));

                        cursor2.close();
                        startActivity(in);

                    }
                } else {

                    in2.putExtra("NAME", cursor.getString(cursor.getColumnIndex(DatabaseHelper.Course_name)));

                    in2.putExtra("ID", cursor.getInt(cursor.getColumnIndex(DatabaseHelper.Course_id)));

                    in2.putExtra("INFO", cursor.getString(cursor.getColumnIndex(DatabaseHelper.Course_info)));

                    in2.putExtra("CRN", cursor.getString(cursor.getColumnIndex(DatabaseHelper.Course_crn)));



                    cursor.close();
                    startActivity(in2);

                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_search) {


            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public boolean onNavigationItemSelected(MenuItem menuItem) {


        //Checking if the item is in checked state or not, if not make it in checked state
        if(menuItem.isChecked()) menuItem.setChecked(false);
        else menuItem.setChecked(true);

        //Closing drawer on item click
        drawerLayout.closeDrawers();
        
        String title = getString(R.string.app_name);
        //Check to see which item was being clicked and perform appropriate action
        switch (menuItem.getItemId()) {


            //Replacing the main content with ContentFragment Which is our Inbox View;
            case R.id.home:
                fragment = new HomeFragment();
                //title = getString(R.string.title_home);
                android.support.v4.app.FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.container_body, fragment);
                fragmentTransaction.commit();
                return true;


            // For rest of the options we just show a toast on click

            case R.id.AboutUs:
                fragment = new AboutUsFragment();
                android.support.v4.app.FragmentTransaction fragmentTransaction1 = getSupportFragmentManager().beginTransaction();
                fragmentTransaction1.replace(R.id.container_body, fragment);
                fragmentTransaction1.addToBackStack(null);
                fragmentTransaction1.commit();
                return true;
            case R.id.ContactUs:
                fragment = new ContactUsFragment();
                android.support.v4.app.FragmentTransaction fragmentTransaction2 = getSupportFragmentManager().beginTransaction();
                fragmentTransaction2.replace(R.id.container_body, fragment);
                fragmentTransaction2.addToBackStack(null);
                fragmentTransaction2.commit();
                return true;
            case R.id.maps:
                fragment = new MapFragment();
                android.support.v4.app.FragmentTransaction fragmentTransaction3 = getSupportFragmentManager().beginTransaction();
                fragmentTransaction3.replace(R.id.container_body, fragment);
                fragmentTransaction3.addToBackStack(null);
                fragmentTransaction3.commit();
                return true;
            case R.id.reminder:
                fragment = new AlarmFragment();
                android.support.v4.app.FragmentTransaction fragmentTransaction4 = getSupportFragmentManager().beginTransaction();
                fragmentTransaction4.replace(R.id.container_body, fragment);
                fragmentTransaction4.addToBackStack(null);
                fragmentTransaction4.commit();
                return true;
            case R.id.logoutandexit:
                jsondata=null;
                mLoginManager = LoginManager.getInstance();
                mLoginManager.logOut();
                android.os.Process.killProcess(android.os.Process.myPid());
                System.exit(1);
                return true;
            default:
                return true;

        }

    }
}



