package com.example.myappktgk.data;
import com.example.myappktgk.model.Document;
import java.util.*;

public class DocumentManager {

    private static DocumentManager instance;
    private List<Document> list = new ArrayList<>();

    private DocumentManager() {}

    public static DocumentManager getInstance() {
        if (instance == null) {
            instance = new DocumentManager();
        }
        return instance;
    }

    public List<Document> getAll() {
        return list;
    }

    public void add(Document doc) {
        list.add(doc);
    }

    public void update(Document doc) {
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getId().equals(doc.getId())) {
                list.set(i, doc);
                return;
            }
        }
    }

    public void delete(String id) {
        list.removeIf(d -> d.getId().equals(id));
    }

    public Document findById(String id) {
        for (Document d : list) {
            if (d.getId().equals(id)) return d;
        }
        return null;
    }
}
