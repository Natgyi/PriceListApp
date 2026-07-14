package com.example.pricelistapp;

import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbHelper = new DatabaseHelper(this);

        Button btnAdd = findViewById(R.id.btnAddItem);
        Button btnBackup = findViewById(R.id.btnBackup);
        Button btnRestore = findViewById(R.id.btnRestore);

        btnAdd.setOnClickListener(v -> {
            dbHelper.insertItem("Sample Item", 4500.00, "Electronics");
            Toast.makeText(this, "Data Inserted Successfully!", Toast.LENGTH_SHORT).show();
        });

        btnBackup.setOnClickListener(v -> {
            List<PriceItem> items = dbHelper.getAllItems();
            if (BackupManager.exportBackup(this, items)) {
                Toast.makeText(this, "Backup Process Succeeded!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Backup Failed Process Error.", Toast.LENGTH_SHORT).show();
            }
        });

        btnRestore.setOnClickListener(v -> {
            if (BackupManager.importRestore(this, dbHelper)) {
                Toast.makeText(this, "Database Restored Successfully!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Restore Execution Error.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
