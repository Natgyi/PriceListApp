package com.example.pricelistapp;

import android.content.Context;
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class BackupManager {
    private static final String BACKUP_FILE_NAME = "PriceList_Backup.json";

    public static boolean exportBackup(Context context, List<PriceItem> items) {
        try {
            JSONArray jsonArray = new JSONArray();
            for (PriceItem item : items) {
                JSONObject obj = new JSONObject();
                obj.put("name", item.getItemName());
                obj.put("price", item.getPrice());
                obj.put("category", item.getCategory());
                jsonArray.put(obj);
            }
            File backupFile = new File(context.getExternalFilesDir(null), BACKUP_FILE_NAME);
            FileOutputStream fos = new FileOutputStream(backupFile);
            fos.write(jsonArray.toString(4).getBytes(StandardCharsets.UTF_8));
            fos.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean importRestore(Context context, DatabaseHelper dbHelper) {
        try {
            File backupFile = new File(context.getExternalFilesDir(null), BACKUP_FILE_NAME);
            if (!backupFile.exists()) return false;

            FileInputStream fis = new FileInputStream(backupFile);
            byte[] size = new byte[fis.available()];
            fis.read(size);
            fis.close();

            String jsonStr = new String(size, StandardCharsets.UTF_8);
            JSONArray jsonArray = new JSONArray(jsonStr);
            
            dbHelper.clearTable();
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject obj = jsonArray.getJSONObject(i);
                dbHelper.insertItem(obj.getString("name"), obj.getDouble("price"), obj.getString("category"));
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
