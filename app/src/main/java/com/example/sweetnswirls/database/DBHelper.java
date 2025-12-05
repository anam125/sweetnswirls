package com.example.sweetnswirls.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;


public class DBHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "sweetnswirls.db";
    private static final int DB_VERSION = 1;

    public DBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Users table
        db.execSQL(
                "CREATE TABLE users(" +
                        "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        "name TEXT, " +
                        "email TEXT UNIQUE, " +
                        "password TEXT)"
        );

        // Cart Table
        db.execSQL(
                "CREATE TABLE cart(" +
                        "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        "itemName TEXT, " +
                        "itemPrice INTEGER, " +
                        "quantity INTEGER)"
        );

        // Orders Table
        db.execSQL(
                "CREATE TABLE orders(" +
                        "orderId INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        "userEmail TEXT, " +
                        "itemName TEXT, " +
                        "itemPrice INTEGER, " +
                        "quantity INTEGER, " +
                        "totalPrice INTEGER, " +
                        "address TEXT, " +
                        "phone TEXT, " +
                        "paymentMethod TEXT)"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS users");
        db.execSQL("DROP TABLE IF EXISTS cart");
        db.execSQL("DROP TABLE IF EXISTS orders");
        onCreate(db);
    }

    // 1️⃣ INSERT USER
    public boolean insertUser(String name, String email, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("name", name);
        cv.put("email", email);
        cv.put("password", password);

        long result = db.insert("users", null, cv);

        // 🔹 Log to check if insert is successful
        if (result == -1) {
            Log.d("DBHelper", "❌ Failed to insert user: " + email);
        } else {
            Log.d("DBHelper", "✅ User inserted successfully: " + email);
        }

        return result != -1;
    }

    // 2️⃣ CHECK USER LOGIN
    public boolean checkUser(String email, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(
                "SELECT * FROM users WHERE email=? AND password=?",
                new String[]{email, password}
        );

        // 🔹 Log to check login attempts
        Log.d("DBHelper", "checkUser query returned " + cursor.getCount() + " rows for email: " + email);

        boolean exists = cursor.getCount() > 0;
        cursor.close();
        return exists;
    }

    // 3️⃣ ADD TO CART
    public boolean addToCart(String name, int price, int qty) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("itemName", name);
        cv.put("itemPrice", price);
        cv.put("quantity", qty);
        long result = db.insert("cart", null, cv);

        if (result == -1) {
            Log.d("DBHelper", "❌ Failed to add to cart: " + name);
        } else {
            Log.d("DBHelper", "✅ Item added to cart: " + name);
        }

        return result != -1;
    }

    // 4️⃣ GET CART ITEMS
    public Cursor getCartItems() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM cart", null);
    }

    // 5️⃣ INSERT ORDER
    public boolean insertOrder(String userEmail, String itemName, int itemPrice, int quantity,
                               int totalPrice, String address, String phone, String paymentMethod) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("userEmail", userEmail);
        cv.put("itemName", itemName);
        cv.put("itemPrice", itemPrice);
        cv.put("quantity", quantity);
        cv.put("totalPrice", totalPrice);
        cv.put("address", address);
        cv.put("phone", phone);
        cv.put("paymentMethod", paymentMethod);

        long result = db.insert("orders", null, cv);

        if (result == -1) {
            Log.d("DBHelper", "❌ Failed to insert order for: " + userEmail);
        } else {
            Log.d("DBHelper", "✅ Order inserted successfully for: " + userEmail);
        }

        return result != -1;
    }

    // 6️⃣ CLEAR CART
    public void clearCart() {
        SQLiteDatabase db = this.getWritableDatabase();
        int deletedRows = db.delete("cart", null, null);
        Log.d("DBHelper", "🗑️ Cleared cart, deleted rows: " + deletedRows);
    }

    // 7️⃣ CHECK IF EMAIL EXISTS (for Forget Password)
    public boolean isEmailExists(String email) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(
                "SELECT * FROM users WHERE email=?",
                new String[]{email}


        );
        boolean exists = cursor.getCount() > 0;
        Log.d("DBHelper", "isEmailExists query returned " + cursor.getCount() + " rows for email: " + email);
        cursor.close();
        return exists;
    }

    // 8️⃣ UPDATE PASSWORD
    public boolean updatePassword(String email, String newPassword) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("password", newPassword);

        int rows = db.update("users", cv, "email=?", new String[]{email});

        if (rows > 0) {
            Log.d("DBHelper", "✅ Password updated for: " + email);
            return true;
        } else {
            Log.d("DBHelper", "❌ Failed to update password for: " + email);
            return false;
        }
    }
    public void exportDatabase(Context context) {

        try {
            // Get the original database file
            File dbFile = context.getDatabasePath(DB_NAME);

            // Create a directory inside: Android/data/com.example.sweetnswirls/files/exportedDB
            File exportDir = new File(context.getExternalFilesDir(null), "exportedDB");
            if (!exportDir.exists()) {
                exportDir.mkdirs();
            }

            // Output file name
            File outFile = new File(exportDir, DB_NAME);

            // Copy database
            FileInputStream fis = new FileInputStream(dbFile);
            FileOutputStream fos = new FileOutputStream(outFile);

            byte[] buffer = new byte[1024];
            int length;

            while ((length = fis.read(buffer)) > 0) {
                fos.write(buffer, 0, length);
            }

            fis.close();
            fos.close();

            Log.d("DB_EXPORT", "Database exported at: " + outFile.getAbsolutePath());

        } catch (Exception e) {
            e.printStackTrace();
            Log.d("DB_EXPORT", "❌ Failed to export database: " + e.getMessage());
        }
    }

}