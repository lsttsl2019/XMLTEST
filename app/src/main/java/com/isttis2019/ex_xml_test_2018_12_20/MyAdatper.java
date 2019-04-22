package com.isttis2019.ex_xml_test_2018_12_20;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class MyAdatper extends BaseAdapter {


ArrayList<Menber> menbers;
LayoutInflater inflater;
    public MyAdatper(ArrayList<Menber> menbers, LayoutInflater inflater) {
    this.menbers=menbers;
    this.inflater=inflater;

    }


    @Override
    public int getCount() {
       return menbers.size();
    }

    @Override
    public Object getItem(int position) {
        return menbers.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView==null){
            convertView=inflater.inflate(R.layout.addlist, parent,false);
        }

        TextView showRang=convertView.findViewById(R.id.add_showRang);
        TextView  yearWeekTime=convertView.findViewById(R.id.add_yearWeekTime);
        TextView name=convertView.findViewById(R.id.add_name);
        TextView rank=convertView.findViewById(R.id.add_rank);

        TextView rankOldAndNew=convertView.findViewById(R.id.add_rankOldAndNew);
        TextView openDt=convertView.findViewById(R.id.add_openDt);
        TextView audiCnt=convertView.findViewById(R.id.add_audiCnt);
        TextView audiAcc=convertView.findViewById(R.id.add_audiAcc);


        Menber menber=menbers.get(position);

        showRang.setText(menber.showRang);
        yearWeekTime.setText(menber.yearWeekTime);
        name.setText(menber.name);
        rank.setText(menber.rank);

        rankOldAndNew.setText(menber.rankOldAndNew);
        openDt.setText(menber.openDt);
        audiAcc.setText(menber.audiAcc);
        audiCnt.setText(menber.audiCnt);


        return convertView;
    }
}













