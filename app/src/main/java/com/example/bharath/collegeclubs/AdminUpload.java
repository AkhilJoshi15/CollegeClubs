package com.example.bharath.collegeclubs;

import android.Manifest;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.NotificationCompat;
import android.test.mock.MockPackageManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.content.Intent;
import android.content.Context;
import android.widget.Toast;

import java.io.File;
import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AdminUpload extends AppCompatActivity {

    EditText ename, edes, edate;
    ImageView eimg;
    Button choose, upload;
    Uri imageUri;
    File file;
    String real_path, arole;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_upload);

        arole = getIntent().getStringExtra("arole");
        ename = (EditText) findViewById(R.id.ename);
        edes = (EditText) findViewById(R.id.edes);
        edate = (EditText) findViewById(R.id.edate);
        eimg = (ImageView) findViewById(R.id.eimg);
        choose = (Button) findViewById(R.id.choose);
        upload = (Button) findViewById(R.id.upload);

        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ename.getText().toString().equals("") || edes.getText().toString().equals("") || edate.getText().toString().equals("")) {
                    Toast.makeText(getApplicationContext(), "Fill all fields", Toast.LENGTH_LONG).show();
                } else {
                    DBconnector db = new DBconnector(getApplicationContext());
                    HashMap<String, String> map = new HashMap<String, String>();
                    map.put("ename", ename.getText().toString());
                    map.put("edes", edes.getText().toString());
                    map.put("edate", edate.getText().toString());
                    map.put("eimg", real_path);
                    map.put("club", arole);
                    db.insert_event(map);
                    Intent intent = new Intent(getApplicationContext(), AdminView.class);
                    intent.putExtra("arole", arole);
                    Toast.makeText(getApplicationContext(), "Event Details Upload Success", Toast.LENGTH_LONG).show();
                    addNotification();
                    startActivity(intent);
                }
            }
        });

        choose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onStart();
                openGallery();
            }
        });

    }

    private void addNotification() {
        NotificationCompat.Builder builder = (NotificationCompat.Builder) new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.ccn)
                .setContentTitle("College Clubs")
                .setContentText("Just now event uploaded")
                .setAutoCancel(true)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);
        Intent intent = new Intent(this, Login.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pi = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(pi);
        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(0, builder.build());
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            int hasWritePermission = checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE);
            int hasReadPermission = checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE);
            int hasCameraPermission = checkSelfPermission(Manifest.permission.CAMERA);


            List<String> permissions = new ArrayList<String>();
            if (hasWritePermission != MockPackageManager.PERMISSION_GRANTED) {
                permissions.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);

            }

            if (hasReadPermission != MockPackageManager.PERMISSION_GRANTED) {
                permissions.add(Manifest.permission.READ_EXTERNAL_STORAGE);

            }

            if (hasCameraPermission != MockPackageManager.PERMISSION_GRANTED) {
                permissions.add(Manifest.permission.CAMERA);

            }
            if (!permissions.isEmpty()) {
                requestPermissions(permissions.toArray(new String[permissions.size()]), 111);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case 111: {
                for (int i = 0; i < permissions.length; i++) {
                    if (grantResults[i] == MockPackageManager.PERMISSION_GRANTED) {
                        System.out.println("Permissions --> " + "Permission Granted: " + permissions[i]);


                    } else if (grantResults[i] == MockPackageManager.PERMISSION_DENIED) {
                        System.out.println("Permissions --> " + "Permission Denied: " + permissions[i]);

                    }
                }
            }
            break;
            default: {
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
            }
        }
    }

    private void openGallery() {
        Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(gallery, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == 1) {
            imageUri = data.getData();
            eimg.setImageURI(imageUri);
            file = new File(getRealPathFromUri(imageUri));
        }
    }

    private String getRealPathFromUri(Uri imageUri) {
        Cursor cursor = getContentResolver().query(imageUri, null, null, null, null);
        cursor.moveToFirst();
        int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
        real_path = cursor.getString(idx);
        return cursor.getString(idx);
    }
}
