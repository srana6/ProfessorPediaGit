package headfirst.com.projectapplication;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

/**
 * A simple {@link Fragment} subclass.
 */
public class ContactUsFragment extends Fragment implements View.OnClickListener {

    private EditText editTextName;
    private EditText editTextEmail;
    private EditText editTextMessage;

    //Send button
    private Button buttonSend;

    public ContactUsFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_contact_us, container, false);
        editTextName = (EditText) rootView.findViewById(R.id.EditTextName);
        editTextEmail = (EditText) rootView.findViewById(R.id.EditTextEmail);
        editTextMessage = (EditText) rootView.findViewById(R.id.editTextMessage);

        buttonSend = (Button) rootView.findViewById(R.id.buttonSend);
        buttonSend.setOnClickListener(this);
        return rootView;
    }
    @Override
    public void onClick(View v) {
        sendEmail();
    }
    private void sendEmail() {
        //Getting content for email
        String name = editTextName.getText().toString().trim();
        String email = "sum.rana05@gmail.com";
        String message = editTextMessage.getText().toString().trim();

        //Creating SendMail object
        SendMail sm = new SendMail(getContext(), email, name, message);

        //Executing sendmail to send email
        sm.execute();
        editTextEmail.setText(" ");
        editTextMessage.setText(" ");
        editTextName.setText(" ");
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


