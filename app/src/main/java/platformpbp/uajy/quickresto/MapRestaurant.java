package platformpbp.uajy.quickresto;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputLayout;
import com.mapbox.android.core.permissions.PermissionsListener;
import com.mapbox.android.core.permissions.PermissionsManager;
import com.mapbox.api.directions.v5.models.DirectionsResponse;
import com.mapbox.api.directions.v5.models.DirectionsRoute;
import com.mapbox.api.geocoding.v5.GeocodingCriteria;
import com.mapbox.api.geocoding.v5.MapboxGeocoding;
import com.mapbox.api.geocoding.v5.models.CarmenFeature;
import com.mapbox.geojson.Feature;
import com.mapbox.geojson.FeatureCollection;
import com.mapbox.geojson.Point;
import com.mapbox.mapboxsdk.Mapbox;
import com.mapbox.mapboxsdk.annotations.Marker;
import com.mapbox.mapboxsdk.annotations.MarkerOptions;
import com.mapbox.mapboxsdk.camera.CameraPosition;
import com.mapbox.mapboxsdk.camera.CameraUpdateFactory;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.location.LocationComponent;
import com.mapbox.mapboxsdk.location.LocationComponentActivationOptions;
import com.mapbox.mapboxsdk.location.modes.CameraMode;
import com.mapbox.mapboxsdk.location.modes.RenderMode;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;
import com.mapbox.mapboxsdk.maps.Style;
import com.mapbox.mapboxsdk.plugins.places.autocomplete.PlaceAutocomplete;
import com.mapbox.mapboxsdk.plugins.places.autocomplete.model.PlaceOptions;
import com.mapbox.mapboxsdk.style.layers.SymbolLayer;
import com.mapbox.mapboxsdk.style.sources.GeoJsonSource;
import com.mapbox.services.android.navigation.ui.v5.NavigationLauncher;
import com.mapbox.services.android.navigation.ui.v5.NavigationLauncherOptions;
import com.mapbox.services.android.navigation.ui.v5.route.NavigationMapRoute;
import com.mapbox.services.android.navigation.v5.navigation.NavigationRoute;

import java.util.List;

import platformpbp.uajy.quickresto.model.Restorant;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.iconAllowOverlap;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.iconIgnorePlacement;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.iconImage;

public class MapRestaurant extends AppCompatActivity implements OnMapReadyCallback, PermissionsListener {

    private static final String DESTINATION_SYMBOL_LAYER_ID = "destination-symbol-layer-id";
    private static final String DESTINATION_ICON_ID = "destination-icon-id";
    private static final String DESTINATION_SOURCE_ID = "destination-source-id";
    private static final int REQUEST_CODE_AUTOCOMPLETE = 1;
    private FloatingActionButton searchfab;
    private PermissionsManager permissionsManager;
    private MapboxMap mapboxMap;
    private MapView mapView;
    private Point origin;
    private TextView txtFullName;
    private FloatingActionButton backmr;
    private Double lon,la;
    private String namaresto,alamatResto,url;
    private ImageView gambar;

    private Button nextPage;

    private Point destinationPointer;
    private Marker destinationMarker;
    private NavigationMapRoute navigationMapRoute;
    private DirectionsRoute currentRoute;
    private FloatingActionButton navifab;

    @Override
    protected void onStart()
    {
        super.onStart();
        mapView.onStart();
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        mapView.onResume();
    }

    @Override
    protected void onPause()
    {
        super.onPause();
        mapView.onPause();
    }

    @Override
    protected void onStop()
    {
        super.onStop();
        mapView.onStop();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState)
    {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    public void onLowMemory()
    {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        Mapbox.getInstance(this, getString(R.string.mapbox_access_token));
        setContentView(R.layout.activity_map_restaurant);
        String username;
        SharePreferenceClass sp=new SharePreferenceClass(this);
        nextPage=findViewById(R.id.next_page);
        lon = getIntent().getDoubleExtra("longitude",0);
        la = getIntent().getDoubleExtra("latitude",0);
        namaresto=getIntent().getStringExtra("resto");
        alamatResto=getIntent().getStringExtra("alamat");
        url=getIntent().getStringExtra("gambar");
        mapView = findViewById(R.id.mapView);

//        username = sp.getUsernameS();
//        txtFullName=findViewById(R.id.UserName);
//        txtFullName.setText(username);
        backmr = (FloatingActionButton) findViewById(R.id.floating_backMap);
//        searchfab = findViewById(R.id.fab_location_Search);
//        navifab = findViewById(R.id.fab_location_navigation);
//        navifab.setEnabled(false);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);
        backmr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MapRestaurant.this,ReservationMenu.class);
                startActivity(intent);
            }
        });

        nextPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MapRestaurant.this,regisReservation.class);
                intent.putExtra("longitutePassing",lon);
                intent.putExtra("latitudePassing",la);
                intent.putExtra("gambar2",url);
                intent.putExtra("alamat2",String.valueOf(alamatResto));
                intent.putExtra("resto2",String.valueOf(namaresto));
                startActivity(intent);
            }
        });
//        searchfab.setOnClickListener(new View.OnClickListener()
//        {
//            @Override
//            public void onClick(View view)
//            {
//                Intent intent = new PlaceAutocomplete.IntentBuilder().accessToken(Mapbox.getAccessToken()!=null ? Mapbox.getAccessToken():getString(R.string.mapbox_access_token))
//                        .placeOptions(PlaceOptions.builder().backgroundColor(Color.parseColor("#EEEEEE")).limit(10).build(PlaceOptions.MODE_CARDS))
//                        .build(MainActivity.this);
//                startActivityForResult(intent, REQUEST_CODE_AUTOCOMPLETE);
//            }
//        });
//        navifab.setOnClickListener(new View.OnClickListener()
//        {
//            @Override
//            public void onClick(View view)
//            {
//                NavigationLauncherOptions options = NavigationLauncherOptions.builder()
//                        .directionsRoute(currentRoute).shouldSimulateRoute(true).build();
//                NavigationLauncher.startNavigation(MainActivity.this, options);
//                navifab.setEnabled(true);
//            }
//        });
    }

    private void initLayers(@NonNull Style loadedMapStyle)
    {
        loadedMapStyle.addImage(DESTINATION_ICON_ID, BitmapFactory.decodeResource(this.getResources(), R.drawable.mapbox_marker_icon_default));
        GeoJsonSource geoJsonSource = new GeoJsonSource(DESTINATION_SOURCE_ID);
        loadedMapStyle.addSource(geoJsonSource);
        SymbolLayer destinationSymbolLayer = new SymbolLayer(DESTINATION_SYMBOL_LAYER_ID, DESTINATION_SOURCE_ID);
        destinationSymbolLayer.withProperties(iconImage(DESTINATION_ICON_ID),iconAllowOverlap(true),iconIgnorePlacement(true));
        loadedMapStyle.addLayer(destinationSymbolLayer);
    }

    @SuppressLint("MissingPermission")
    private void enableLocationComponent(@NonNull Style loadedMapStyle)
    {
        if (PermissionsManager.areLocationPermissionsGranted(this))
        {
            LocationComponent locationComponent = mapboxMap.getLocationComponent();
            locationComponent.activateLocationComponent(LocationComponentActivationOptions.builder(this, loadedMapStyle).build());
            locationComponent.setLocationComponentEnabled(true);
            locationComponent.setCameraMode(CameraMode.TRACKING);
            locationComponent.setRenderMode(RenderMode.COMPASS);
            this.origin = Point.fromLngLat(locationComponent.getLastKnownLocation().getLongitude(), locationComponent.getLastKnownLocation().getLatitude());
        }
        else
        {
            permissionsManager = new PermissionsManager(this);
            permissionsManager.requestLocationPermissions(this);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode== Activity.RESULT_OK && requestCode==REQUEST_CODE_AUTOCOMPLETE)
        {
            CarmenFeature selectedCarmenFeature = PlaceAutocomplete.getPlace(data);
            if (mapboxMap!=null)
            {
                Style style = mapboxMap.getStyle();
                if (style!=null)
                {
                    GeoJsonSource source = style.getSourceAs("geojsonSourceLayerId");
                    if (source!=null)
                    {
                        source.setGeoJson(FeatureCollection.fromFeatures(new Feature[] {Feature.fromJson(selectedCarmenFeature.toJson())}));
                    }
                    if(destinationMarker != null)
                    {
                        mapboxMap.removeMarker(destinationMarker);
                    }
                    mapboxMap.animateCamera(CameraUpdateFactory.newCameraPosition(new CameraPosition.Builder()
                            .target(new LatLng(((Point) selectedCarmenFeature.geometry()).latitude(),((Point)selectedCarmenFeature.geometry())
                                    .longitude())).zoom(14).build()), 4000);
                    destinationMarker = mapboxMap.addMarker(new MarkerOptions().position(new LatLng(((Point) selectedCarmenFeature.geometry()).latitude(),
                            ((Point) selectedCarmenFeature.geometry()).longitude())));
                    destinationPointer = Point.fromLngLat(((Point) selectedCarmenFeature.geometry()).longitude(), ((Point) selectedCarmenFeature.geometry()).latitude());
//                    navifab.setEnabled(true);
                    getRoute(origin, destinationPointer);
                }
            }
        }
    }

    @Override
    public void onPermissionResult(boolean granted)
    {
        if (granted)
        {
            mapboxMap.getStyle(new Style.OnStyleLoaded()
            {
                @Override
                public void onStyleLoaded(@NonNull Style style)
                {
                    enableLocationComponent(style);
                }
            });
        }
        else
        {
            Toast.makeText(this, "Permission Not Granted", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onExplanationNeeded(List<String> permissionsToExplain)
    {
        Toast.makeText(this, "Grant Location Permission", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults)
    {
        permissionsManager.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public void onMapReady(@NonNull MapboxMap mapboxMap)
    {
        this.mapboxMap = mapboxMap;
        mapboxMap.addMarker(new MarkerOptions().position(new LatLng(la, lon)).title(namaresto));
        CameraPosition position = new CameraPosition.Builder()
                .target(new LatLng(la, lon)) // Sets the new camera position
                .zoom(17) // Sets the zoom
                .bearing(180) // Rotate the camera
                .tilt(30) // Set the camera tilt
                .build(); // Creates a CameraPosition from the builder

        mapboxMap.setStyle(new Style.Builder().fromUri(Style.MAPBOX_STREETS), new Style.OnStyleLoaded()
        {
            @Override
            public void onStyleLoaded(@NonNull Style style)
            {
                enableLocationComponent(style);
                initLayers(style);
                mapboxMap.animateCamera(CameraUpdateFactory
                        .newCameraPosition(position), 500);




// The result of this reverse geocode will give you "Pennsylvania Ave NW"

//                mapboxMap.addOnMapClickListener(new MapboxMap.OnMapClickListener()
//                {
//                    @Override
//                    public boolean onMapClick(@NonNull LatLng point)
//                    {
//                        if(destinationMarker != null)
//                        {
//                            mapboxMap.removeMarker(destinationMarker);
//                        }
//                        destinationMarker = mapboxMap.addMarker(new MarkerOptions().position(point));
//                        destinationPointer = Point.fromLngLat(point.getLongitude(), point.getLatitude());
//                        getRoute(origin, destinationPointer);
////                        navifab.setEnabled(true);
//                        return true;
//                    }
//                });
            }
        });
    }

    private void getRoute(Point originPointer, Point destination)
    {
        NavigationRoute.builder(getApplicationContext())
                .accessToken(Mapbox.getAccessToken()).origin(originPointer).destination(destination).build().getRoute(new Callback<DirectionsResponse>()
        {
            @Override
            public void onResponse(Call<DirectionsResponse> call, Response<DirectionsResponse> response)
            {
                if(response.body() == null)
                {
                    Toast.makeText(getApplicationContext(), "No Route Found, Check Right User And Access Token", Toast.LENGTH_SHORT).show();
                    return;
                }
                else if(response.body().routes().size()==0)
                {
                    Toast.makeText(getApplicationContext(), "No Route Found", Toast.LENGTH_SHORT).show();
                    return;
                }
                currentRoute = response.body().routes().get(0);
                if(navigationMapRoute!=null)
                {
                    navigationMapRoute.removeRoute();
                }
                else
                {
                    navigationMapRoute = new NavigationMapRoute(null, mapView, mapboxMap);
                }
                navigationMapRoute.addRoute(currentRoute);
            }

            @Override
            public void onFailure(Call<DirectionsResponse> call, Throwable t)
            {
                Toast.makeText(getApplicationContext(), "Error:" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

}