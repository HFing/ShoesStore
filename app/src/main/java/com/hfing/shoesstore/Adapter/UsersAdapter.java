package com.hfing.shoesstore.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.hfing.shoesstore.DAO.RoleDAO;
import com.hfing.shoesstore.Model.Role;
import com.hfing.shoesstore.Model.User;
import com.hfing.shoesstore.R;

import java.util.List;

public class UsersAdapter extends BaseAdapter {

    private final List<User> users;
    private final Context context;

    public UsersAdapter(List<User> users, Context context) {
        this.users = users;
        this.context = context;
    }

    @Override
    public int getCount() {
        return users.size();
    }

    @Override
    public Object getItem(int position) {
        return users.get(position);
    }

    @Override
    public long getItemId(int position) {
        return users.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        RoleDAO roleDAO = new RoleDAO(context);

        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            convertView = inflater.inflate(R.layout.user_list_item, parent, false);
        }

        User user = users.get(position);
        TextView tvUserName = convertView.findViewById(R.id.tvUserName);
        TextView tvEmail = convertView.findViewById(R.id.tvUserEmail);
        TextView tvRoleName = convertView.findViewById(R.id.tvUserRole);

        tvUserName.setText(user.getName());
        tvEmail.setText(user.getEmail());
        Role role = roleDAO.getRoleNameById(user.getRole_id());
        if (role != null) {
            tvRoleName.setText(role.getName());
        } else {
            tvRoleName.setText("Role not found");
        }

        return convertView;
    }
}