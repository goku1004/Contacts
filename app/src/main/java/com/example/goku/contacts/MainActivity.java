package com.example.goku.contacts;

import android.Manifest;

import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;


import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private Uri mUri;
    private ArrayList<Item> mArrItem;
    public static final int REQUEST_PERMISSION_CODE=123;
    public static final int NUMBER_ONE=1;
    public static final int NUMBER_ZERO=0;
    public static final String NAME="name";
    public static final String PHONE="phone";
    private ContactApdapter mApdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        initCheck();
    }
    private void init() {
        mRecyclerView=findViewById(R.id.recyclerview);
    }
    public void adapter(){
        mApdapter=new ContactApdapter(mArrItem,getApplicationContext());

        LinearLayoutManager manager=new LinearLayoutManager(getApplicationContext());
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.setAdapter(mApdapter);
        
        itemClick();
    }
    public void itemClick(){
        mApdapter.setOnItemCLickListener1(new ContactApdapter.OnItemCLickListener() {
            @Override
            public void onItemClick(String name, String phone,int i) {
                Intent intent=new Intent(MainActivity.this,Detail.class);
                intent.putExtra(NAME,name);
                intent.putExtra(PHONE,phone);
                startActivity(intent);
            }
        });
    }


    private void fetchContacts(){
        mArrItem=new ArrayList<>();
        mUri= ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
        String [] mProjection={ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
                ContactsContract.CommonDataKinds.Phone.NUMBER};
        ContentResolver resolver=getContentResolver();
        Cursor cursor=resolver.query(mUri,mProjection,null,null,null);


        while (cursor.moveToNext()){
            String name=cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
            String phone=cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
            mArrItem.add(new Item(name,phone));
        }
        adapter();

    }
    public void initCheck(){
        String[] permissions = {Manifest.permission.READ_CONTACTS,
                               Manifest.permission.CALL_PHONE};
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            if(checkSelfPermission(Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED
                                        && checkSelfPermission(Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED){
                               requestPermissions(permissions, REQUEST_PERMISSION_CODE);
            } else {
                fetchContacts();
                }
        } else {
            fetchContacts();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        if(requestCode == REQUEST_PERMISSION_CODE){
            for(int i = permissions.length - NUMBER_ONE; i >= NUMBER_ZERO; i--){
                if(grantResults[i] != PackageManager.PERMISSION_GRANTED){
                    finish();
                    return;
                }
            }
            fetchContacts();
        }
    }

}


