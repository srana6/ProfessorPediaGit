package headfirst.com.projectapplication;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.ImageView;

import com.facebook.login.LoginManager;

import java.io.IOException;

import info.hoang8f.widget.FButton;


public class HomeFragment extends Fragment implements View.OnClickListener {

    private Button btnFAQ;
    Fragment fragment=null;

    private FButton prof;
    private FButton course;
    private FButton Trendingcourse;


    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);

        RotateAnimation rotate = new RotateAnimation(0, 180, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        rotate.setDuration(5000);
        rotate.setRepeatCount(Animation.INFINITE);
        rotate.setInterpolator(new LinearInterpolator());

        ImageView image= (ImageView) rootView.findViewById(R.id.iitseal);
        image.startAnimation(rotate);

        btnFAQ=(Button) rootView.findViewById(R.id.buttonFAQID);
        btnFAQ.setOnClickListener(this);

        prof = (FButton) rootView.findViewById(R.id.buttonProfessorID);
        prof.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity().getBaseContext(),ProfInfo.class);
                startActivity(intent);
            }
        });

        course = (FButton) rootView.findViewById(R.id.buttonCoursesID);
        course.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity().getBaseContext(),Course_info.class);
                startActivity(intent);
            }
        });

        Trendingcourse = (FButton) rootView.findViewById(R.id.buttonTrendingCoursesID);
        Trendingcourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity().getBaseContext(),Trending_courses.class);
                startActivity(intent);
            }
        });

        // Inflate the layout for this fragment
        return rootView;
    }

    @Override
    public void onClick(View v) {

        FragmentManager fragmentManager=getFragmentManager();
        FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
       // FaqFragment faqFragment=new FaqFragment();
        fragment=new FaqFragment();
        //fragmentTransaction.replace(R.id.homeFragment,faqFragment);
        fragmentTransaction.replace(R.id.container_body,fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();


    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }


}
