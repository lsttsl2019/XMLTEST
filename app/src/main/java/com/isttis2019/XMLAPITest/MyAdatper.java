package com.isttis2019.XMLAPITest;

import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

public class MyAdatper extends RecyclerView.Adapter {


ArrayList<Menber> menbers;
LayoutInflater inflater;
    public MyAdatper(ArrayList<Menber> menbers, LayoutInflater inflater) {
    this.menbers=menbers;
    this.inflater=inflater;

    }



    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.addlist,parent,false);
        VH holder =new VH(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
        Menber item =menbers.get(position);
        VH holder= (VH) viewHolder;
        holder.showRang.setText(item.showRang);
        holder.yearWeekTime.setText(item.yearWeekTime);
        holder.name.setText(item.name);
        holder.rank.setText(item.rank);
        holder.ranoldANdNew.setText(item.rankOldAndNew);
        holder.openDt.setText(item.openDt);
        holder.audiAcc.setText(item.audiAcc);
        holder.audiCcnt.setText(item.audiCnt);

    }

    @Override
    public int getItemCount() {
        return menbers.size();
    }

    class  VH extends RecyclerView.ViewHolder {

        TextView showRang;
        TextView yearWeekTime;
        TextView name;
        TextView rank;
        TextView ranoldANdNew;
        TextView openDt;
        TextView audiCcnt;
        TextView audiAcc;


        public VH(@NonNull final View itemView) {
            super(itemView);

            showRang=itemView.findViewById(R.id.add_showRang);
            yearWeekTime=itemView.findViewById(R.id.add_yearWeekTime);
            name=itemView.findViewById(R.id.add_name);
            rank=itemView.findViewById(R.id.add_rank);
            ranoldANdNew=itemView.findViewById(R.id.add_rankOldAndNew);
            openDt=itemView.findViewById(R.id.add_openDt);
            audiCcnt=itemView.findViewById(R.id.add_audiCnt);
            audiAcc=itemView.findViewById(R.id.add_audiAcc);



            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {

                    String name=menbers.get(getLayoutPosition()).name;


                    String uris = "";

                    try {
                        uris = URLEncoder.encode(name, "utf-8");
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }


                    Intent browserIntent = new Intent(Intent.ACTION_VIEW);

                    PackageManager packageManager = inflater.getContext().getPackageManager();

                    Uri uri = Uri.parse("https://search.naver.com/search.naver?sm=top_hty&fbm=1&ie=utf8&query=" + uris);

                    browserIntent.setDataAndType(uri, "text/html");

                    List<ResolveInfo> list = packageManager.queryIntentActivities(browserIntent, 0);

                    for (ResolveInfo resolveInfo : list) {

                        String activityName = resolveInfo.activityInfo.name;


                        Log.e("activityName", activityName);


                        if (activityName.contains("Browser")) {

                            browserIntent =

                                    packageManager.getLaunchIntentForPackage(resolveInfo.activityInfo.packageName);

                            ComponentName comp =

                                    new ComponentName(resolveInfo.activityInfo.packageName, resolveInfo.activityInfo.name);

                            browserIntent.setAction(Intent.ACTION_VIEW);

                            browserIntent.addCategory(Intent.CATEGORY_BROWSABLE);

                            browserIntent.setComponent(comp);

                            browserIntent.setData(uri);
                            inflater.getContext().startActivity(browserIntent);

                            break;

                        }

                    }


                    //  Toast.makeText(MainActivity.this, nam, Toast.LENGTH_SHORT).show();
                    return false;
                }
            });
        }
    }




}




//
//    TextView showRang=convertView.findViewById(R.id.add_showRang);
//    TextView  yearWeekTime=convertView.findViewById(R.id.add_yearWeekTime);
//    TextView name=convertView.findViewById(R.id.add_name);
//    TextView rank=convertView.findViewById(R.id.add_rank);
//
//    TextView rankOldAndNew=convertView.findViewById(R.id.add_rankOldAndNew);
//    TextView openDt=convertView.findViewById(R.id.add_openDt);
//    TextView audiCnt=convertView.findViewById(R.id.add_audiCnt);
//    TextView audiAcc=convertView.findViewById(R.id.add_audiAcc);
//
//
//    Menber menber=menbers.get(position);
//
//        showRang.setText(menber.showRang);
//                yearWeekTime.setText(menber.yearWeekTime);
//                name.setText(menber.name);
//                rank.setText(menber.rank);
//
//                rankOldAndNew.setText(menber.rankOldAndNew);
//                openDt.setText(menber.openDt);
//                audiAcc.setText(menber.audiAcc);
//                audiCnt.setText(menber.audiCnt);







