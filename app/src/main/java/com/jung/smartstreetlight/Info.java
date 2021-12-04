package com.jung.smartstreetlight;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;

public class Info extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);

        ItemAdapter adapter;
        RecyclerView recyclerView;

        ArrayList<ItemData> Itemdata = new ArrayList();

        Itemdata.add(new ItemData("김준", R.drawable.menu4,"팀장"));
        Itemdata.add(new ItemData("김동훈", R.drawable.menu4, "팀원"));
        Itemdata.add(new ItemData("김승일", R.drawable.menu4,"팀원"));
        Itemdata.add(new ItemData("정광근", R.drawable.menu4,"팀원"));
        Itemdata.add(new ItemData("박지원", R.drawable.menu4,"팀원"));

        recyclerView = findViewById(R.id.recyclerView);
        adapter = new ItemAdapter(this, Itemdata);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this,RecyclerView.VERTICAL,false));
    }
}