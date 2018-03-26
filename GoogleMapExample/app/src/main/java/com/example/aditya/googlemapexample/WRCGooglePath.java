package com.example.aditya.googlemapexample;

import android.app.Activity;
import android.graphics.Color;
import android.location.Location;
import android.util.Log;

import com.akexorcist.googledirection.DirectionCallback;
import com.akexorcist.googledirection.GoogleDirection;
import com.akexorcist.googledirection.constant.TransportMode;
import com.akexorcist.googledirection.model.Direction;
import com.akexorcist.googledirection.model.Leg;
import com.akexorcist.googledirection.model.Route;
import com.akexorcist.googledirection.util.DirectionConverter;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;



import java.util.ArrayList;

/**
 * Created by abhishekagarwal on 2/23/18.
 */

public class WRCGooglePath {

    private static WRCGooglePath wrcGooglePath = null;
    private PolylineOptions polylineOptions;
    private Marker markerDriverLoc;
    private MarkerOptions markerChangeBarBar;

    public static WRCGooglePath getInstance() {

        if (wrcGooglePath != null)
            return wrcGooglePath;
        else
            return new WRCGooglePath();
    }

    public void register(final Activity context, final GoogleMap g_map, final LatLng origin, final LatLng destination, final String status) {
        GoogleDirection.withServerKey("AIzaSyA80xUhg6I4roIFfc0Vh9qYll3kN18PDkU")
                .from(origin)
                .to(destination)
                .transportMode(TransportMode.DRIVING)
                .execute(new DirectionCallback() {
                    @Override
                    public void onDirectionSuccess(Direction direction, String rawBody) {
                        Log.e("sizewa", String.valueOf(direction.getRouteList().size()));
                        g_map.animateCamera(CameraUpdateFactory.newLatLngZoom(origin, 17.0f));
                        if (!(direction.getRouteList().size() == 0)) {

                            Route route = direction.getRouteList().get(0);
                            Leg leg = route.getLegList().get(0);
                            ArrayList<LatLng> directionPositionList = leg.getDirectionPoint();


                            if (markerDriverLoc != null) {
                                markerDriverLoc.remove();
                            }
                            markerDriverLoc = g_map.addMarker(new MarkerOptions()
                                    .position(origin)
                                );

                            if (polylineOptions != null)
                                polylineOptions = null;

                            polylineOptions = DirectionConverter.createPolyline(context, directionPositionList, 4, Color.BLACK);
                            g_map.addPolyline(polylineOptions);

                        }

                    }

                    @Override
                    public void onDirectionFailure(Throwable t) {
                Log.e("aditya","TESTINGGGGGGGGGGG"+t);
                    }


                });
    }

    public interface PathUpdateListener {

        void onLocationUpdate(Location location, Boolean isLastKnownLocation);

        void onLazyLocationUpdate(Location location, Boolean isLastKnownLocation);

    }


}
