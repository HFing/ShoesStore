package com.hfing.shoesstore.Activity;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import androidx.appcompat.app.AppCompatActivity;
import com.hfing.shoesstore.Activity.domain.DatabaseHelper;
import com.hfing.shoesstore.Activity.domain.User;
import com.hfing.shoesstore.R;

import java.util.ArrayList;
import java.util.List;

public class UserManagementActivity extends AppCompatActivity {

    private EditText etUsername, etEmail, etPassword;
    private Spinner spinnerRole;
    private Button btnAddUser, btnUpdateUser, btnDeleteUser;
    private ListView lvUsers;
    private DatabaseHelper dbHelper;
    private ArrayAdapter<String> userAdapter;
    private List<String> userList;
    private int selectedUserId = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_management);

        // Initialize views
        etUsername = findViewById(R.id.getUsername);
        etEmail = findViewById(R.id.getEmail);
        etPassword = findViewById(R.id.getPassword);
        spinnerRole = findViewById(R.id.spinnerRole);
        btnAddUser = findViewById(R.id.btnAddUser);
        btnUpdateUser = findViewById(R.id.btnUpdateUser);
        btnDeleteUser = findViewById(R.id.btnDeleteUser);
        lvUsers = findViewById(R.id.lvUsers);

        dbHelper = new DatabaseHelper(this);
        userList = new ArrayList<>();
        userAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, userList);
        lvUsers.setAdapter(userAdapter);

        // Load users
        loadUsers();

        // Set listeners
        btnAddUser.setOnClickListener(v -> addUser());
        btnUpdateUser.setOnClickListener(v -> updateUser());
        btnDeleteUser.setOnClickListener(v -> deleteUser());

        lvUsers.setOnItemClickListener((parent, view, position, id) -> {
            String selectedUser = userList.get(position);
            selectedUserId = Integer.parseInt(selectedUser.split(":")[0]);
            etUsername.setText(selectedUser.split(":")[1]);
            etEmail.setText(selectedUser.split(":")[2]);
            etPassword.setText(""); // Clear password field for security
            spinnerRole.setSelection(selectedUser.split(":")[3].equals("Admin") ? 0 : 1);
        });
    }

    private void loadUsers() {
        userList.clear();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT u.id, u.username, u.email, r.role_name FROM user u JOIN user_role ur ON u.id = ur.user_id JOIN role r ON ur.role_id = r.id", null);
        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0);
                String username = cursor.getString(1);
                String email = cursor.getString(2);
                String role = cursor.getString(3);
                userList.add(id + ": " + username + ": " + email + ": " + role);
            } while (cursor.moveToNext());
        }
        cursor.close();
        userAdapter.notifyDataSetChanged();
    }

    private void addUser() {
        String username = etUsername.getText().toString();
        String email = etEmail.getText().toString();
        String password = etPassword.getText().toString();
        String role = spinnerRole.getSelectedItem().toString();

        User user = new User();
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(password);
        dbHelper.addUser(user);

        int userId = getUserIdByUsername(username);
        int roleId = role.equals("Admin") ? 1 : 2;
        dbHelper.addUserRole(userId, roleId);

        loadUsers(); // Ensure this is called to refresh the list
        clearFields();
    }

    private void updateUser() {
        if (selectedUserId == -1) return;

        String username = etUsername.getText().toString();
        String email = etEmail.getText().toString();
        String password = etPassword.getText().toString();
        String role = spinnerRole.getSelectedItem().toString();

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("username", username);
        values.put("email", email);
        if (!password.isEmpty()) {
            values.put("password", password);
        }
        db.update("user", values, "id = ?", new String[]{String.valueOf(selectedUserId)});

        int roleId = role.equals("Admin") ? 1 : 2;
        ContentValues roleValues = new ContentValues();
        roleValues.put("role_id", roleId);
        db.update("user_role", roleValues, "user_id = ?", new String[]{String.valueOf(selectedUserId)});

        loadUsers(); // Ensure this is called to refresh the list
        clearFields();
    }

    private void deleteUser() {
        if (selectedUserId == -1) return;

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete("user_role", "user_id = ?", new String[]{String.valueOf(selectedUserId)});
        db.delete("user", "id = ?", new String[]{String.valueOf(selectedUserId)});

        loadUsers(); // Ensure this is called to refresh the list
        clearFields();
    }

    private int getUserIdByUsername(String username) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT id FROM user WHERE username = ?", new String[]{username});
        if (cursor.moveToFirst()) {
            int userId = cursor.getInt(0);
            cursor.close();
            return userId;
        }
        cursor.close();
        return -1;
    }

    private void clearFields() {
        etUsername.setText("");
        etEmail.setText("");
        etPassword.setText("");
        spinnerRole.setSelection(0);
        selectedUserId = -1;
    }
}