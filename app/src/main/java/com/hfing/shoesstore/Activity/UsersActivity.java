package com.hfing.shoesstore.Activity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.hfing.shoesstore.Adapter.UsersAdapter;
import com.hfing.shoesstore.DAO.RoleDAO;
import com.hfing.shoesstore.DAO.UsersDAO;
import com.hfing.shoesstore.Model.Role;
import com.hfing.shoesstore.Model.User;
import com.hfing.shoesstore.R;

import java.util.ArrayList;

public class UsersActivity extends AppCompatActivity {
    Integer id = -1;
     Spinner spnRoles;
     RoleDAO roleDAO= new RoleDAO(this);
     UsersDAO usersDAO= new UsersDAO(this);
     Button btnAddUser, btnUpdateUser, btnDeleteUser;
     EditText edtNameUser, edtEmailUser, edtPasswordUser, edtAddress, edtPhone, edtUserName;
     RadioButton rdbMale, rdbFemale;
     ListView lvUser;
     UsersAdapter usersAdapter;
     ArrayList<User> users;
     Context context= this;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users);

        lvUser= findViewById(R.id.lvUsers);
        btnAddUser= findViewById(R.id.bntAddUser);
        btnUpdateUser= findViewById(R.id.bntUpdateUser);
        btnDeleteUser= findViewById(R.id.bntDeleteUser);
        edtNameUser= findViewById(R.id.edtNameUser);
        edtEmailUser= findViewById(R.id.edtEmailUser);
        edtPasswordUser= findViewById(R.id.edtPassword);
        edtAddress= findViewById(R.id.edtAdressUser);
        edtPhone= findViewById(R.id.edtPhoneUser);
        edtUserName= findViewById(R.id.edtUserName);
        rdbMale= findViewById(R.id.rbMale);
        rdbFemale= findViewById(R.id.rbFemale);
        spnRoles= findViewById(R.id.spinnerUserRole);

        users= usersDAO.getAllUsers();
        usersAdapter= new UsersAdapter(users, context);
        lvUser.setAdapter(usersAdapter);

        ArrayList<Role> roles= new ArrayList<>();
        roles.addAll(roleDAO.getAllRoles());
        ArrayAdapter<Role> roleArrayAdapter= new ArrayAdapter<>(context, android.R.layout.simple_spinner_item, roles);
        roleArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnRoles.setAdapter(roleArrayAdapter);
        reloadListView();

       btnAddUser.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
                User user= new User();
                if (edtNameUser.getText().toString().isEmpty() || edtEmailUser.getText().toString().isEmpty() || edtPasswordUser.getText().toString().isEmpty()) {
                    Toast.makeText(context, "Please enter all fields", Toast.LENGTH_SHORT).show();
                        return;
                }
                user.setName(edtNameUser.getText().toString());
                user.setEmail(edtEmailUser.getText().toString());
                user.setPassword(edtPasswordUser.getText().toString());
                user.setUsername(edtUserName.getText().toString());
                user.setRole_id(spnRoles.getSelectedItemPosition());
                user.setAddress(edtAddress.getText().toString());
                user.setPhone(edtPhone.getText().toString());
                if (rdbMale.isChecked()) {
                    user.setGender(1);
                } else {
                    user.setGender(0);
                }
               if(usersDAO.addUser(user) > 0){
                   Toast.makeText(context, "Add category successfully", Toast.LENGTH_SHORT).show();
//                categoryDAO.addCategory(category);
                   users.clear();
                   users.addAll(usersDAO.getAllUsers());
                   usersAdapter.notifyDataSetChanged();}
               else {
                   Toast.makeText(context, "Add category failed", Toast.LENGTH_SHORT).show();
               }
               reloadListView();
               edtAddress.setText("");
                edtEmailUser.setText("");
                edtNameUser.setText("");
                edtPasswordUser.setText("");
                edtPhone.setText("");
                edtUserName.setText("");
           }

       });

       btnDeleteUser.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               reloadListView();
               if (id == -1) {
                   Toast.makeText(context, "Please select user", Toast.LENGTH_SHORT).show();
                   return;
               }
               if (usersDAO.deleteUser(id) > 0 ) {
                   Toast.makeText(context, "Delete user successfully", Toast.LENGTH_SHORT).show();
                   usersAdapter.notifyDataSetChanged();
               }
               edtAddress.setText("");
               edtEmailUser.setText("");
               edtNameUser.setText("");
               edtPasswordUser.setText("");
               edtPhone.setText("");
               edtUserName.setText("");
               id = -1;
               reloadListView();
           }
       });

       btnUpdateUser.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               reloadListView();
               User user= new User();
               if (edtNameUser.getText().toString().isEmpty() || id == -1) {
                   Toast.makeText(context, "Please select user", Toast.LENGTH_SHORT).show();
                   return;
               }
               user.setId(id);
               user.setName(edtNameUser.getText().toString());
               user.setEmail(edtEmailUser.getText().toString());
               user.setPassword(edtPasswordUser.getText().toString());
               user.setUsername(edtUserName.getText().toString());
               user.setRole_id(spnRoles.getSelectedItemPosition());
               user.setAddress(edtAddress.getText().toString());
               user.setPhone(edtPhone.getText().toString());
               if (rdbMale.isChecked()) {
                   user.setGender(1);
               } else {
                   user.setGender(0);
               }
               if (usersDAO.updateUser(user) > 0 ) {
                   Toast.makeText(context, "Update user successfully", Toast.LENGTH_SHORT).show();
                   usersAdapter.notifyDataSetChanged();
               }
               edtAddress.setText("");
               edtEmailUser.setText("");
               edtNameUser.setText("");
               edtPasswordUser.setText("");
               edtPhone.setText("");
               edtUserName.setText("");
               id = -1;
               reloadListView();
           }
       });

       lvUser.setOnItemClickListener(new AdapterView.OnItemClickListener() {
           @Override
           public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
               edtNameUser.setText(users.get(i).getName());
               edtEmailUser.setText(users.get(i).getEmail());
               edtPasswordUser.setText(users.get(i).getPassword());
               edtAddress.setText(users.get(i).getAddress());
               edtPhone.setText(users.get(i).getPhone());
               edtUserName.setText(users.get(i).getUsername());
               id= users.get(i).getId();
               if(users.get(i).getGender() == 1){
                   rdbMale.setChecked(true);
               } else {
                   rdbFemale.setChecked(true);
               }
           }
       });

    }
    public void reloadListView(){
        users.clear();
        users.addAll(usersDAO.getAllUsers());
        usersAdapter.notifyDataSetChanged();
    }
}
