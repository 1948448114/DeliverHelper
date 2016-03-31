package main.deliverhelp;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import model.DbModel;


public class MainActivity extends FragmentActivity implements View.OnClickListener {
    private ViewPager mPaper;
    private RadioGroup mGroup;
    private RadioButton home,setting;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        String number="18795880629";
        String content="get";
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();

//        sendMessage send=new sendMessage(this,number,content);

    }

public void init(){
    mPaper = (ViewPager)findViewById(R.id.content);
    mGroup = (RadioGroup)findViewById(R.id.group);
    home = (RadioButton)findViewById(R.id.home);
    setting = (RadioButton)findViewById(R.id.setting);

    home.setOnClickListener(this);
    setting.setOnClickListener(this);

//        mGroup.check(R.id.home);
//        mGroup.setOnCheckedChangeListener(new CheckedChangeListener());

    mPaper.setAdapter(new MyPagerAdapter(getSupportFragmentManager()));
    mPaper.setOnPageChangeListener(new PageChangeListener());
    mPaper.setOffscreenPageLimit(2);
}
    @Override
    public void onDestroy(){
        super.onDestroy();
        DbModel model=new DbModel(this);
        model.onfinish();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }



        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.home:
                    mPaper.setCurrentItem(0);
                    break;
                case R.id.setting:
                    mPaper.setCurrentItem(1);
                    break;
                default:
                    break;
            }
        }



//    private class CheckedChangeListener implements RadioGroup.OnCheckedChangeListener {
//
//        @Override
//        public void onCheckedChanged(RadioGroup buttonView, int isChecked) {
//            switch (isChecked){
//                case R.id.home:
//                    mPaper.setCurrentItem(0);
//                    System.out.print("home");
//                    break;
//                case R.id.setting:
//                    mPaper.setCurrentItem(1);
//                    System.out.print("setting");
//                    break;
//            }
//        }
//    }
//

    private class PageChangeListener implements ViewPager.OnPageChangeListener{

        @Override
        public void onPageScrolled(int i, float v, int i2) {

        }

        @Override
        public void onPageSelected(int i) {
            switch (i){
                case 0:
//                    mGroup.check(R.id.home);
                    home.setChecked(true);
                    break;

                case 1:
//                    mGroup.check(R.id.setting);
                    setting.setChecked(true);
                    break;
                default:
                    break;
            }
        }

        @Override
        public void onPageScrollStateChanged(int i) {

        }
    }

    private class MyPagerAdapter extends FragmentPagerAdapter{
        public MyPagerAdapter(FragmentManager fma){
            super(fma);
        }
        @Override
        public Fragment getItem(int i) {

            switch (i){
                case 0:
                    Frag frag = new Frag();
                    return  frag;
                case 1:
                    Frag_settings frags = new Frag_settings();
                    return  frags;
            }
//            Frag frag = new Frag();
//            Bundle bundle = new Bundle();
//            bundle.putString("key","hello word"+i);
//            frag.setArguments(bundle);
            return null;
        }
        @Override
        public int getCount() {
            return 2;
        }
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
