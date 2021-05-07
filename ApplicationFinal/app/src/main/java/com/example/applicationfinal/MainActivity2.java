package com.example.applicationfinal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MainActivity2 extends AppCompatActivity {
    //initialiser les variables
    private Spinner typePlat;
    private Button btLocation;
    private TextView textGps1, textGps2, textGps3, textGps4, textGps5;
    private FusedLocationProviderClient fusedLocationProviderClient;
    public static ArrayList<String> notes = new ArrayList<>();
    public static ArrayAdapter arrayAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        this.typePlat = (Spinner) findViewById(R.id.type_Plat);


        btLocation = findViewById(R.id.bt_location);
        textGps1 = findViewById(R.id.text_gps1);
        textGps2 = findViewById(R.id.text_gps2);
        textGps3 = findViewById(R.id.text_gps3);
        textGps4 = findViewById(R.id.text_gps4);
        textGps5 = findViewById(R.id.text_gps5);
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        ListView listView = (ListView) findViewById(R.id.listView);
        notes.add("Example note");
        arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, notes);
        listView.setAdapter(arrayAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {


            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Intent intent = new Intent(getApplicationContext(), Note.class);
                intent.putExtra("noteId", i);
                startActivity(intent);

            }
        });

        btLocation.setOnClickListener(new View.OnClickListener() {

            /**
             * Verifie les permission, quand elle est accordées on accede a la geolocalisation sinon non quand elle est refusée
             * @param v
             */
            @Override
            public void onClick(View v) {
                //verifier les permissions
                if (ActivityCompat.checkSelfPermission(MainActivity2.this,
                        Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                    //quand la permission est accordée
                    getLocation();
                } else {
                    //quand la permission est refusée
                    ActivityCompat.requestPermissions(MainActivity2.this
                            , new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 44);
                }
            }
        });


        Plat[] plats = PlatsUtils.getPlats();

        // (@resource) android.R.layout.simple_spinner_item:
        //   The resource ID for a layout file containing a TextView to use when instantiating views.
        //    (Layout for one ROW of Spinner)
        ArrayAdapter<Plat> adapter = new ArrayAdapter<Plat>(this,
                android.R.layout.simple_spinner_item,
                plats);

        // Layout for All ROWs of Spinner.  (Optional for ArrayAdapter).
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        this.typePlat.setAdapter(adapter);

        // When user select a List-Item.
        this.typePlat.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            /**
             *
             * @param parent
             * @param view
             * @param position
             * @param id
             */
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                onItemSelectedHandler(parent, view, position, id);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void getLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        fusedLocationProviderClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
            @Override
            public void onComplete(@NonNull Task<Location> task) {
                //initialiser la localisation
                Location location = task.getResult();
                if (location != null) {
                    try {
                        //initialiser geocoder
                        Geocoder geocoder = new Geocoder(MainActivity2.this, Locale.getDefault());
                        //Initialiser les adresses
                        List<Address> addresses = geocoder.getFromLocation(
                                location.getLatitude(), location.getLongitude(), 1
                        );
                        //Set latitude dans le textview
                        textGps1.setText(Html.fromHtml(
                                "<front color='#6200EE'><b>Latitude : </b<br></font>"
                                        + addresses.get(0).getLatitude()
                        ));
                        //Set longitude dans le textview
                        textGps2.setText(Html.fromHtml(
                                "<front color='#6200EE'><b>Longitude : </b<br></font>"
                                        + addresses.get(0).getLongitude()
                        ));
                        //Set pays dans le textview
                        textGps3.setText(Html.fromHtml(
                                "<front color='#6200EE'><b>Pays : </b<br></font>"
                                        + addresses.get(0).getCountryName()
                        ));
                        //Set location dans le textview
                        textGps4.setText(Html.fromHtml(
                                "<front color='#6200EE'><b>Location : </b<br></font>"
                                        + addresses.get(0).getLocality()
                        ));
                        //Set Adresse dans le textview
                        textGps5.setText(Html.fromHtml(
                                "<front color='#6200EE'><b>Adresse : </b<br></font>"
                                        + addresses.get(0).getAddressLine(0)
                        ));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }



    private void onItemSelectedHandler(AdapterView<?> adapterView, View view, int position, long id) {
        Adapter adapter = adapterView.getAdapter();
        Plat plat = (Plat) adapter.getItem(position);

        Toast.makeText(getApplicationContext(), "vous avez sélectionné: " + plat.getFullName() ,Toast.LENGTH_SHORT).show();
    }

}