package com.oatrice.ShareLocationRealtime.activity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.oatrice.ShareLocationRealtime.R;

import timber.log.Timber;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    private GoogleMap.InfoWindowAdapter infoWindowAdapter = new GoogleMap.InfoWindowAdapter() {
        @Override
        public View getInfoWindow(Marker marker) {
            Timber.i("getInfoWindow");
            return null;
        }

        @Override
        public View getInfoContents(final Marker marker) {
            Timber.i("getInfoContents");
            View myContentsView = getLayoutInflater().inflate(R.layout.view_marker_info, null);
            final ImageView ivAvatar = (ImageView) myContentsView.findViewById(R.id.avatar);
            TextView tvTitle = (TextView) myContentsView.findViewById(R.id.title);
            TextView tvSnippet = (TextView) myContentsView.findViewById(R.id.snippet);
            tvTitle.setText(marker.getTitle());
            tvSnippet.setText(marker.getSnippet());

            String avatarUrl = "https://static.comicvine.com/uploads/original/11/110482/3191339-aang+1.jpg";
            Glide.with(MapsActivity.this)
                        .load(avatarUrl)
                    .asBitmap()
                    .placeholder(R.drawable.ic_user)
                    .override(200, 200)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(new SimpleTarget<Bitmap>() {
                        @Override
                        public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                            refreshInfoWindow(marker);
                            ivAvatar.setImageBitmap(resource);
                        }
                    });

            return myContentsView;
        }

        private void refreshInfoWindow(Marker marker) {
            //refresh marker เพื่อให้รูปขึ้น
            if (marker != null && marker.isInfoWindowShown()) {
                marker.hideInfoWindow();
                marker.showInfoWindow();
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-34, 151);
        MarkerOptions markerOption = new MarkerOptions().position(sydney).title("Marker in Sydney");
        Marker marker = mMap.addMarker(markerOption);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        mMap.setInfoWindowAdapter(infoWindowAdapter);

        //force show marker
        marker.showInfoWindow();
//        infoWindowAdapter.getInfoContents(marker.get);
    }
}
