package main.deliverhelp;

import android.os.Bundle;
import android.os.Looper;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import Message.Failhandler;
import Message.SuccessHandler;
import Message.sendMessage;
import Message.senfFailHandler;
import adapter.itemAdapter;
import excelDeal.DealExcelTask;

/**
 * Created by LJ on 2015/8/23.
 */
public class Frag extends Fragment{
    sendMessage message;
    ListView itemView;
    itemAdapter itemAdapter;
    List<HashMap<String,String >> record;
    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container,Bundle savedInstanceState)
    {
        return inflater.inflate(R.layout.frag_home,container,false);

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);
        itemView = (ListView)getActivity().findViewById(R.id.home_view);
        record = new ArrayList<HashMap<String, String>>();

//        itemView.setAdapter((itemAdapter));
        DealExcelTask task=new DealExcelTask(getActivity(),itemView,record,new SuccessHandler() {
            @Override
            public void OnSuccess() {
                Log.w("fragSuccess","get");
//                Log.w("fragSuccess ok",record.toString());
                if(record.size()==0){
                    Toast toast = Toast.makeText(getActivity(), "没有可发送的信息", Toast.LENGTH_LONG);
                    toast.show();
                }
                else {
                    itemAdapter = new itemAdapter(getActivity(), record);
                    itemView.setAdapter(itemAdapter);
                }
            }
        });
        Log.w("fragment task", "get");
        task.execute();
//        DealExcel dealExcel;
//        dealExcel = new DealExcel(getActivity());
//        List<HashMap<String, String>> content = dealExcel.parseExcel();
//        Log.w("content",content.toString());
//        ListView itemView = (ListView)getActivity().findViewById(R.id.home_view);
//        itemAdapter adapter = new itemAdapter(getActivity(),content);
//        itemView.setAdapter(adapter);
        Button send_btn = (Button)getActivity().findViewById(R.id.send_btn);
        send_btn.setOnClickListener(new send_btn_click());



    }

    public void send(HashMap<Integer,String> record1,HashMap<String,String> phonerecord1){
        message = new sendMessage(getActivity(),itemView,record,itemAdapter,phonerecord1,record1,new SuccessHandler() {
            @Override
            public void OnSuccess() {
                Toast toast = Toast.makeText(getActivity(), "发送成功", Toast.LENGTH_LONG);
                toast.show();
            }
        },
                new senfFailHandler() {
                    @Override
                    public void OnFail(int type) {
                        if(type==1) {
                            Toast toast = Toast.makeText(getActivity(), "发送失败", Toast.LENGTH_LONG);
                            toast.show();
                        }
                        else if(type==2){
                            Toast toast = Toast.makeText(getActivity(), "请先初始化模板", Toast.LENGTH_LONG);
                            toast.show();
                        }
                        else{
                            Toast toast = Toast.makeText(getActivity(), "未知错误。T_T", Toast.LENGTH_LONG);
                            toast.show();
                        }
                    }
                });
        message.execute();
    }
    class send_btn_click implements View.OnClickListener{

        @Override
        public void onClick(View v) {
//            Looper.prepare();
            ListView itemList = (ListView)getActivity().findViewById(R.id.home_view);
            HashMap<Integer,String> record = new HashMap<>();
            HashMap<String,String> phonerecord = new HashMap<>();
            int j=0;
            for(int i=0;i<itemList.getChildCount();i++){

                View childview = itemList.getChildAt(i);
                CheckBox cb = (CheckBox)childview.findViewById(R.id.checkBoxId);
                if(cb.isChecked()){
                    LinearLayout itemlayout = (LinearLayout)childview.findViewById(R.id.itemlayout);
                    TextView IdView =  (TextView)itemlayout.getChildAt(0);
                    TextView phoneView = (TextView)itemlayout.getChildAt(2);
                    String Id = IdView.getText().toString();
                    record.put(j,Id);
                    phonerecord.put(Id,phoneView.getText().toString());
                    j++;
                }
            }
            Log.w("checkbox test",record.toString());
            send(record,phonerecord);

//            Looper.loop();

//            itemList.
        }
    }
}
