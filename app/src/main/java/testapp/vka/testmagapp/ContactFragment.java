package testapp.vka.testmagapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

/**
 * Created by kirill on 18.10.2016.
 */

public class ContactFragment extends SupportMapFragment implements OnMapReadyCallback {

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getMapAsync(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        drawTitleBar();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        LatLng fakeUserPosition = new LatLng(53.907442, 27.5539103);
        final CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(fakeUserPosition)
                .zoom(10)
                .build();
        googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

        MarkerOptions userOptions = new MarkerOptions();
        userOptions.position(fakeUserPosition);
        userOptions.title("Это вы");
        userOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));


        MarkerOptions restaurant1 = new MarkerOptions();
        restaurant1.position(new LatLng(53.9057129, 27.5456715));
        restaurant1.title("Кафе № 1");
        restaurant1.snippet("Фарфор № 1 тел. 232 21 12");

        MarkerOptions restaurant2 = new MarkerOptions();
        restaurant2.position(new LatLng(53.9083678, 27.5938871));
        restaurant2.title("Кафе № 2");
        restaurant2.snippet("Фарфор № 2 тел. 232 33 33");

        googleMap.addMarker(userOptions).showInfoWindow();

        googleMap.addMarker(restaurant1);
        googleMap.addMarker(restaurant2);
    }


    private void drawTitleBar() {
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(R.string.contacts);

    }
}
