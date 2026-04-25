package com.example.myappktgk.ui;

import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myappktgk.R;
import com.example.myappktgk.data.DocumentManager;
import com.example.myappktgk.model.*;

public class DetailActivity extends AppCompatActivity {

    TextView txtId, txtName, txtType, txtPrice, txtFee, txtExtra;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        txtId = findViewById(R.id.txtId);
        txtName = findViewById(R.id.txtName);
        txtType = findViewById(R.id.txtType);
        txtPrice = findViewById(R.id.txtPrice);
        txtFee = findViewById(R.id.txtFee);
        txtExtra = findViewById(R.id.txtExtra);

        String id = getIntent().getStringExtra("id");

        if (id != null) {
            loadData(id);
        }
    }

    private void loadData(String id) {
        Document doc = DocumentManager.getInstance().findById(id);

        if (doc == null) return;

        txtId.setText("ID: " + doc.getId());
        txtName.setText("Tên: " + doc.getName());
        txtType.setText("Loại: " + doc.getClass().getSimpleName());
        txtPrice.setText("Giá: " + doc.getPrice());
        txtFee.setText("Phí mượn: " + doc.tinhPhiMuon());

        if (doc instanceof Book) {
            txtExtra.setText("Số trang: " + ((Book) doc).getPages());
        } else if (doc instanceof Magazine) {
            txtExtra.setText("Số kỳ phát hành: " + ((Magazine) doc).getIssueNumber());
        } else if (doc instanceof Ebook) {
            txtExtra.setText("Dung lượng: " + ((Ebook) doc).getFileSize() + " MB");
        }
    }
}