package com.example.myappktgk.ui;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.*;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.*;

import com.example.myappktgk.R;
import com.example.myappktgk.adapter.DocumentAdapter;
import com.example.myappktgk.data.DocumentManager;
import com.example.myappktgk.model.Document;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    DocumentAdapter adapter;
    Button btnAdd;

    EditText edtSearch;
    Spinner spinnerFilter;

    List<Document> fullList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);
        btnAdd = findViewById(R.id.btnAdd);
        edtSearch = findViewById(R.id.edtSearch);
        spinnerFilter = findViewById(R.id.spinnerFilter);

        fullList = DocumentManager.getInstance().getAll();

        adapter = new DocumentAdapter(fullList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        ArrayAdapter<String> filterAdapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_dropdown_item,
                new String[]{"All", "Book", "Magazine", "Ebook"}
        );
        spinnerFilter.setAdapter(filterAdapter);

        edtSearch.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void afterTextChanged(Editable s) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                filterData();
            }
        });

        spinnerFilter.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, android.view.View view, int position, long id) {
                filterData();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        btnAdd.setOnClickListener(v -> {
            startActivity(new Intent(this, AddEditActivity.class));
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        fullList = DocumentManager.getInstance().getAll();
        filterData();
    }

    private void filterData() {
        String keyword = edtSearch.getText().toString().toLowerCase();
        String type = spinnerFilter.getSelectedItem().toString();

        List<Document> filtered = new ArrayList<>();

        for (Document doc : fullList) {

            boolean matchName = doc.getName().toLowerCase().contains(keyword);

            boolean matchType = type.equals("All") ||
                    doc.getClass().getSimpleName().equals(type);

            if (matchName && matchType) {
                filtered.add(doc);
            }
        }

        adapter.setList(filtered);
    }
}