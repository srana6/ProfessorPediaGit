package headfirst.com.projectapplication;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ExpandableListView.OnGroupClickListener;
import android.widget.ExpandableListView.OnGroupCollapseListener;
import android.widget.ExpandableListView.OnGroupExpandListener;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 */
public class FaqFragment extends Fragment {

    ExpandableListAdapter listAdapter;
    ExpandableListView expListView;
    List<String> listDataHeader;
    HashMap<String, List<String>> listDataChild;

    public Context context;

    public FaqFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        context=getActivity().getApplicationContext();

        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_faq, container, false);

        // get the listview
        expListView = (ExpandableListView) v.findViewById(R.id.lvExp);

        // preparing list data
        prepareListData();

        listAdapter = new ExpandableListAdapter(context, listDataHeader, listDataChild);

        // setting list adapter
        expListView.setAdapter(listAdapter);

        return v;
    }
    private void prepareListData() {
        listDataHeader = new ArrayList<String>();
        listDataChild = new HashMap<String, List<String>>();

        // Adding child data
        listDataHeader.add(getResources().getString(R.string.faq1));
        listDataHeader.add(getResources().getString(R.string.faq2));
        listDataHeader.add(getResources().getString(R.string.faq3));
        listDataHeader.add(getResources().getString(R.string.faq4));
        listDataHeader.add(getResources().getString(R.string.faq5));

        // Adding child data
        List<String> question1 = new ArrayList<String>();
        question1.add(getResources().getString(R.string.faqAns1));

        List<String> question2 = new ArrayList<String>();
        question2.add(getResources().getString(R.string.faq2Ans2));

        List<String> question3 = new ArrayList<String>();
        question3.add(getResources().getString(R.string.faqAns3));

        List<String> question4 = new ArrayList<String>();
        question4.add(getResources().getString(R.string.faqAns4));

        List<String> question5 = new ArrayList<String>();
        question5.add(getResources().getString(R.string.faqAns5));


        listDataChild.put(listDataHeader.get(0), question1); // Header, Child data
        listDataChild.put(listDataHeader.get(1), question2);
        listDataChild.put(listDataHeader.get(2), question3);
        listDataChild.put(listDataHeader.get(3), question4);
        listDataChild.put(listDataHeader.get(4), question5);
    }


}

