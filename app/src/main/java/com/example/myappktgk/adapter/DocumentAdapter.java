package com.example.myappktgk.adapter;

import android.app.AlertDialog;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myappktgk.R;
import com.example.myappktgk.data.DocumentManager;
import com.example.myappktgk.model.Document;
import com.example.myappktgk.ui.AddEditActivity;
import com.example.myappktgk.ui.DetailActivity;

import java.util.List;

public class DocumentAdapter extends RecyclerView.Adapter<DocumentAdapter.ViewHolder> {

    private List<Document> list;

    public DocumentAdapter(List<Document> list) {
        this.list = list;
    }

    public void setList(List<Document> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_document, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Document doc = list.get(position);

        holder.txtId.setText("ID: " + doc.getId());
        holder.txtName.setText(doc.getName());
        holder.txtType.setText("Loại: " + doc.getClass().getSimpleName());
        holder.txtFee.setText("Phí: " + doc.tinhPhiMuon());

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(v.getContext(), DetailActivity.class);
            intent.putExtra("id", doc.getId());
            v.getContext().startActivity(intent);
        });

        holder.btnEdit.setOnClickListener(v -> {
            Intent intent = new Intent(v.getContext(), AddEditActivity.class);
            intent.putExtra("id", doc.getId());
            v.getContext().startActivity(intent);
        });

        holder.btnDelete.setOnClickListener(v -> {

            new AlertDialog.Builder(v.getContext())
                    .setTitle("Xác nhận")
                    .setMessage("Bạn có chắc muốn xóa tài liệu này?")
                    .setPositiveButton("Xóa", (dialog, which) -> {

                        int currentPosition = holder.getAdapterPosition();
                        if (currentPosition != RecyclerView.NO_POSITION) {

                            DocumentManager.getInstance().delete(doc.getId());
                            list.remove(currentPosition);

                            notifyItemRemoved(currentPosition);
                            notifyItemRangeChanged(currentPosition, list.size());
                        }
                    })
                    .setNegativeButton("Hủy", null)
                    .show();
        });
    }

    @Override
    public int getItemCount() {
        return list != null ? list.size() : 0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView txtId, txtName, txtType, txtFee;
        Button btnEdit, btnDelete;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            txtId = itemView.findViewById(R.id.txtId);
            txtName = itemView.findViewById(R.id.txtName);
            txtType = itemView.findViewById(R.id.txtType);
            txtFee = itemView.findViewById(R.id.txtFee);

            btnEdit = itemView.findViewById(R.id.btnEdit);
            btnDelete = itemView.findViewById(R.id.btnDelete);

            btnEdit.setFocusable(false);
            btnDelete.setFocusable(false);
        }
    }
}