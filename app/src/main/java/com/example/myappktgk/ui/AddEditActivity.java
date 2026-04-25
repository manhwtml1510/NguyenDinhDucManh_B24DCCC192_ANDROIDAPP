package com.example.myappktgk.ui;

import android.os.Bundle;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myappktgk.R;
import com.example.myappktgk.data.DocumentManager;
import com.example.myappktgk.model.*;

public class AddEditActivity extends AppCompatActivity {

    Spinner spinnerType;
    EditText edtId, edtName, edtPrice, edtExtra;
    Button btnSave, btnBack;

    String editId = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit);

        spinnerType = findViewById(R.id.spinnerType);
        edtId = findViewById(R.id.edtId);
        edtName = findViewById(R.id.edtName);
        edtPrice = findViewById(R.id.edtPrice);
        edtExtra = findViewById(R.id.edtExtra);
        btnSave = findViewById(R.id.btnSave);
        btnBack = findViewById(R.id.btnBack);

        ArrayAdapter<String> typeAdapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_dropdown_item,
                new String[]{"Book", "Magazine", "Ebook"}
        );
        spinnerType.setAdapter(typeAdapter);

        editId = getIntent().getStringExtra("id");

        if (editId != null) {
            loadData(editId);
        }

        btnSave.setOnClickListener(v -> save());

        btnBack.setOnClickListener(v -> finish());
    }

    private void loadData(String id) {
        Document doc = DocumentManager.getInstance().findById(id);
        if (doc == null) return;

        edtId.setText(doc.getId());
        edtName.setText(doc.getName());
        edtPrice.setText(String.valueOf(doc.getPrice()));
        edtId.setEnabled(false); // không cho sửa ID

        if (doc instanceof Book) {
            spinnerType.setSelection(0);
            edtExtra.setText(String.valueOf(((Book) doc).getPages()));
        } else if (doc instanceof Magazine) {
            spinnerType.setSelection(1);
            edtExtra.setText(String.valueOf(((Magazine) doc).getIssueNumber()));
        } else if (doc instanceof Ebook) {
            spinnerType.setSelection(2);
            edtExtra.setText(String.valueOf(((Ebook) doc).getFileSize()));
        }
    }

    private void save() {
        try {
            String id = edtId.getText().toString().trim();
            String name = edtName.getText().toString().trim();
            String extra = edtExtra.getText().toString().trim();

            if (id.isEmpty() || name.isEmpty() || edtPrice.getText().toString().trim().isEmpty() || extra.isEmpty()) {
                Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                return;
            }

            double price = Double.parseDouble(edtPrice.getText().toString());
            String type = spinnerType.getSelectedItem().toString();

            Document doc;

            if (type.equals("Book")) {
                int pages = Integer.parseInt(extra);
                if (pages <= 0) throw new Exception("Số trang phải > 0");
                doc = new Book(id, name, price, pages);

            } else if (type.equals("Magazine")) {
                int issue = Integer.parseInt(extra);
                if (issue <= 0) throw new Exception("Số kỳ phải > 0");
                doc = new Magazine(id, name, price, issue);

            } else {
                double size = Double.parseDouble(extra);
                if (size <= 0) throw new Exception("Dung lượng phải > 0");
                doc = new Ebook(id, name, price, size);
            }

            if (price <= 0) {
                Toast.makeText(this, "Giá phải > 0", Toast.LENGTH_SHORT).show();
                return;
            }

            if (editId == null) {
                DocumentManager.getInstance().add(doc);
            } else {
                DocumentManager.getInstance().update(doc);
            }

            Toast.makeText(this, "Lưu thành công", Toast.LENGTH_SHORT).show();
            finish();

        } catch (Exception e) {
            Toast.makeText(this, "Lỗi: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
}