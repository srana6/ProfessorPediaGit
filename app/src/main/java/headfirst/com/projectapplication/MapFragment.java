package headfirst.com.projectapplication;


import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.OnMapReadyCallback;

/**
 * A simple {@link Fragment} subclass.
 */
public class MapFragment extends Fragment implements OnMapReadyCallback {


    public MapFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView=inflater.inflate(R.layout.fragment_map,container,false);
      SupportMapFragment mapFragment=(SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        return rootView;
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onMapReady(GoogleMap map) {


        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            if (!shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION))
                requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 1);

            return;
        }
        map.setMapType(GoogleMap.MAP_TYPE_NORMAL);

        LocationManager locMan = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        Location lastLoc = locMan.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);


        map.addMarker(new MarkerOptions()
                .position(new LatLng(41.8356848, -87.6283733))
                .title("Hermann Hall")
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.mapmarkericon)));

        map.addMarker(new MarkerOptions()
                .position(new LatLng(41.8386816, -87.6273531))
                .title("Stuart Building")
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.mapmarkericon)));

        map.addMarker(new MarkerOptions()
                .position(new LatLng(41.8313852, -87.6272216))
                .title("IIT Tower")
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.mapmarkericon)));

        map.addMarker(new MarkerOptions()
                .position(new LatLng(41.83903003, -87.62558788))
                .title("Keating Sports Center")
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.mapmarkericon)));
        CameraPosition googlePlex = CameraPosition.builder()
                .target(new LatLng(41.8356848, -87.6283733))
                .zoom(16)
                .bearing(0)
                .tilt(45)
                .build();

        map.animateCamera(CameraUpdateFactory.newCameraPosition(googlePlex), 10000, null);

        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        map.setMyLocationEnabled(true);
    }
}
