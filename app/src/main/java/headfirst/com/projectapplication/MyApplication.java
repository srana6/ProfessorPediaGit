package headfirst.com.projectapplication;

import android.app.Application;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.util.Base64;
import android.util.Log;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by sumit rana on 29-03-2016.
 */
public class MyApplication extends Application {

    private int Comment;
    @Override
    public void onCreate(){
        super.onCreate();
        setComment(1);
        printHashKey();

    }

    public void setComment(int comment) {
        Comment = comment;
    }

    public int getComment() {
        return Comment;
    }

    public void printHashKey(){
        try {
            PackageInfo info = getPackageManager().getPackageInfo(
                    "headfirst.com.projectapplication",
                    PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("KeyHashSumit:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {

        } catch (NoSuchAlgorithmException e) {

        }
    }
}
