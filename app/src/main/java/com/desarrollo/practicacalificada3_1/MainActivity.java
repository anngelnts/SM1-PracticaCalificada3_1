package com.desarrollo.practicacalificada3_1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

public class MainActivity extends AppCompatActivity {

    private String StringLocation = "";
    TextView text_name;
    TextView text_address;
    TextView text_city;
    TextView text_state;
    TextView text_zip;
    ImageView image_camera;
    Button view_map;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        view_map = findViewById(R.id.view_map);

        Intent receptorIntent = getIntent();
        String action = receptorIntent.getAction();
        String type = receptorIntent.getType();
        if(Intent.ACTION_SEND.equals(action) && type != null){
            if("text/plain".equals(type)){
                getData(receptorIntent);
            }
        }

        view_map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri location = Uri.parse("geo:0.0?q="+StringLocation);
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, location);
                startActivity(mapIntent);
            }
        });
    }

    private void getData(Intent intent){
        String name = intent.getStringExtra("name");
        String address = intent.getStringExtra("address");
        String city = intent.getStringExtra("city");
        String state = intent.getStringExtra("state");
        String zip = intent.getStringExtra("zip");
        String image = intent.getStringExtra("image");

        StringLocation = address;

        text_name = findViewById(R.id.text_name);
        text_address = findViewById(R.id.text_address);
        text_city = findViewById(R.id.text_city);
        text_state = findViewById(R.id.text_state);
        text_zip = findViewById(R.id.text_zip);
        image_camera = findViewById(R.id.image_camera);

        text_name.setText(name);
        text_address.setText(address);
        text_city.setText(city);
        text_state.setText(state);
        text_zip.setText(zip);
        if(!image.isEmpty()){
            byte[] decodedString = Base64.decode(image, Base64.NO_WRAP);
            InputStream input=new ByteArrayInputStream(decodedString);
            Bitmap ext_pic = BitmapFactory.decodeStream(input);
            image_camera.setImageBitmap(ext_pic);
        }
    }
}
