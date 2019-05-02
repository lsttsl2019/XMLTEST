package com.isttis2019.ex_xml_test_2018_12_20;


import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;


import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;

import android.widget.Toast;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;

    AlertDialog.Builder builder;
    AlertDialog dialog;

    EditText se_et;

    LayoutInflater inflater;

    String showRang;
    String yearWeekTime;
    String name;
    String rank;
    String movieCd;
    String rankOldAndNew;
    String openDt;
    String audiCnt;
    String audiAcc;
    Menber menber1;

    MyAdatper adatper;
    ArrayList<Menber> menbers = new ArrayList<>();

    Menber menber;

    String string;
    //키값
    final String apikey = "533d4527daa8d52d47aea6a3f8b6d71e";

    SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView= findViewById(R.id.recycler);


        inflater = getLayoutInflater();

        adatper = new MyAdatper(menbers, inflater);
        recyclerView.setAdapter(adatper);

        getSupportActionBar().setTitle("주간박스오피스");



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.actionbor, menu);

        MenuItem item=menu.findItem(R.id.action_bar_search);

        searchView=(SearchView) item.getActionView();

        searchView.setQueryHint("순위검색");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {


                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        final int id = item.getItemId();
        switch (id) {
            case R.id.action_bar_search:
                break;

            case R.id.action_bar_add:
                builder = new AlertDialog.Builder(MainActivity.this);
                LayoutInflater inflater = this.getLayoutInflater();
                View layout = inflater.inflate(R.layout.dialog, null);
                se_et = layout.findViewById(R.id.dialog_targetDt);


                builder.setView(layout);
                dialog = builder.create();
                dialog.setCanceledOnTouchOutside(false);
                dialog.show();

                break;
        }


        return super.onOptionsItemSelected(item);
    }

    public void clickBtn(View view) {

        if (menber1 != null) menbers.clear();
        if (se_et.length()!=8){
            dialog.cancel();
            Toast.makeText(this, "검색년도를 올바르게 입력 해주세요", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(this, "검색시작", Toast.LENGTH_SHORT).show();


            new Thread() {
                @Override
                public void run() {

                    final String targetDt = se_et.getText().toString();
                    //서버 주소 작성
                    String address = "http://www.kobis.or.kr/kobisopenapi/webservice/rest/boxoffice/searchWeeklyBoxOfficeList.xml"
                            + "?key=" + apikey  //키값
                            + "&targetDt=" + targetDt//조회하는 날짜
                            + "&weekGb=0"    //주중으로 받아온다
                            + "&itemPerPage=10"  //총10개의 데이터를 받아온다.
                            + "&showRange"     //상영 기간을출력
                            + "&yearWeekTime"  //조회하는 년도 주차를
                            + "&rank"           //순위를 츨력을
                            + "&movieCd"        //영화 대표코드
                            + "&movieNm"        //영화 이름/
                            + "&rankOldAndNew"  //영화가 새로들어왔는 기존의 있는지 확인
                            + "&openDt"         //영화 개봉일 출력
                            + "&audiCnt"        //해당 일 관객수
                            + "&audiAcc";         //해당 일 누적관객수 출력


                    try {
                        URL url = new URL(address);

                        InputStream is = url.openStream();
                        InputStreamReader isr = new InputStreamReader(is);


                        //xml분석
                        XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
                        XmlPullParser xpp = factory.newPullParser();
                        xpp.setInput(isr);


                        int eventType = xpp.getEventType();

                        String TagName = null;


                        while (eventType != XmlPullParser.END_DOCUMENT) {

                            switch (eventType) {
                                case XmlPullParser.START_DOCUMENT:

                                    break;

                                case XmlPullParser.START_TAG:
                                    TagName = xpp.getName();

                                    if (TagName.equals("boxOfficeResult")) {  //상영기간과 ,조회하는해당 연도와 주차를 알수있다.

                                    } else if (TagName.equals("showRange")) {
                                        xpp.next();
                                        showRang = xpp.getText();
                                    } else if (TagName.equals("yearWeekTime")) {
                                        xpp.next();
                                        yearWeekTime = xpp.getText();
                                    }


                                    if (TagName.equals("weeklyBoxOffice")) {

                                        menber1 = new Menber();
                                        menber1.setShowRang(showRang);
                                        menber1.setYearWeekTime(yearWeekTime);

                                    } else if (TagName.equals("movieNm")) {
                                        xpp.next();
                                        name = xpp.getText();
                                        menber1.setName(name);
                                    } else if (TagName.equals("rank")) {
                                        xpp.next();
                                        rank = xpp.getText();
                                        menber1.setRank(rank);
                                    } else if (TagName.equals("movieCd")) {
                                        xpp.next();
                                        movieCd = xpp.getText();
                                        menber1.setMovieCd(movieCd);
                                    } else if (TagName.equals("rankOldAndNew")) {
                                        xpp.next();
                                        rankOldAndNew = xpp.getText();
                                        menber1.setRankOldAndNew(rankOldAndNew);
                                    } else if (TagName.equals("openDt")) {
                                        xpp.next();
                                        openDt = xpp.getText();
                                        menber1.setOpenDt(openDt);
                                    } else if (TagName.equals("audiCnt")) {
                                        xpp.next();
                                        audiCnt = xpp.getText();
                                        menber1.setAudiCnt(audiCnt);
                                    } else if (TagName.equals("audiAcc")) {
                                        xpp.next();
                                        audiAcc = xpp.getText();
                                        menber1.setAudiAcc(audiAcc);
                                    }


                                    break;

                                case XmlPullParser.TEXT:
                                    break;

                                case XmlPullParser.END_TAG:
                                    TagName = xpp.getName();

                                    if (TagName.equals("weeklyBoxOffice")) {

                                        menbers.add(menber1);


                                    }


                                    break;


                            }


                            xpp.next();
                            eventType = xpp.getEventType();

                        }//while문 종료


                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(MainActivity.this, "검색 완료", Toast.LENGTH_SHORT).show();

                                adatper.notifyDataSetChanged();

                            }
                        });


                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (XmlPullParserException e) {
                        e.printStackTrace();
                    }


                }
            }.start();


            dialog.cancel();

        }

    }


}

























