package com.company.androidapp.main;

import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AlertDialog.Builder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.company.androidapp.R;

import java.util.ArrayList;

public class MenuAdapter extends BaseAdapter {

    Context context;
    ArrayList<MenuItem> menuItems;

    public MenuAdapter(Context context, ArrayList<MenuItem> navItems) {
        this.context = context;
        this.menuItems = navItems;
    }

    @Override
    public int getCount() {
        return menuItems.size();
    }

    @Override
    public Object getItem(int position) {
        return menuItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view;
        boolean isButtonsMenuItem = ("buttons".equals(menuItems.get(position).getName()));

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate((isButtonsMenuItem) ? R.layout.buttons_menu_item : R.layout.menu_item, null);
        }
        else {
            view = convertView;
        }

        TextView titleView = (TextView) view.findViewById(R.id.title);
        titleView.setText(menuItems.get(position).getTitle());
        if (!isButtonsMenuItem) {
            ImageView iconView = (ImageView) view.findViewById(R.id.icon);
            iconView.setImageResource(menuItems.get(position).getIconId());
        }else {
            Button toastButton = (Button) view.findViewById(R.id.btnToast);
            toastButton.setOnClickListener( new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(context,"A toast." , Toast.LENGTH_SHORT).show();
                    ((MainActivity) context).selectItemFromDrawer(position, true);
                }
            });
            Button alertButton = (Button) view.findViewById(R.id.btnAlert);
            alertButton.setOnClickListener( new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Builder alertDialogBuilder = new Builder(context);
                    alertDialogBuilder.setMessage("An alert msg.");
                    alertDialogBuilder.setPositiveButton("Ok",null);
                    AlertDialog alertDialog = alertDialogBuilder.create();
                    alertDialog.show();
                    ((MainActivity) context).selectItemFromDrawer(position, true);
                }
            });
        }

        return view;
    }
}