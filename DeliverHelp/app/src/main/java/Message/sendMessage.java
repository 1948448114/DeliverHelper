package Message;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.telephony.SmsManager;
import android.util.Log;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import adapter.itemAdapter;
import model.DbModel;

/**
 * Created by LJ on 2015/3/24.
 */
public class sendMessage extends AsyncTask<Void,Void,List<HashMap<String,String >>> {
    String url = "";
    boolean flag=true;
    SuccessHandler successHandler;
    senfFailHandler failhandler;
    HashMap<Integer,String > record;
    HashMap<String,String> phonerecord;
    List<HashMap<String,String>> originRecord;
    itemAdapter itemadapter;
    Context ac;
    Template template;
    int state=0;
    public ListView item;
    public sendMessage(Context ac,ListView item,List<HashMap<String,String>> originRecord,itemAdapter itemadapter,HashMap<String,String> phonerecord,HashMap<Integer,String> record,SuccessHandler successHandler,senfFailHandler failhandler){
        this.ac=ac;
        this.record = record;
        this.successHandler = successHandler;
        this.failhandler = failhandler;
        this.item = item;
        this.originRecord = originRecord;
        this.phonerecord = phonerecord;
        this.itemadapter = itemadapter;
    }


    @Override
    protected List<HashMap<String, String>> doInBackground(Void... params) {
        List<HashMap<String,String>> temp = new ArrayList<>();
        template = new Template(ac);
        Set<String> replace = template.getReplace();
        String content = template.getContent();
        SmsManager smsManager = SmsManager.getDefault();
        PendingIntent sentIntent = PendingIntent.getBroadcast(ac, 0, new Intent(), 0);
        try {
            for (int i = 0; i < record.size(); i++) {
               String MessageContent =template.getMessage(record.get(i));
//                if(MessageContent!=null){
//                    Log.w("send_message_content",MessageContent);
//                }
//                else{
//                    state=2;
//                }
               if(MessageContent!=null) {
                   String phoneNumber = phonerecord.get(record.get(i));
//               String phoneNumber = record.get(i);
                   if (MessageContent.length() >= 70) {
                       //短信字数大于70，自动分条
                       List<String> ms = smsManager.divideMessage(MessageContent);

                       for (String str : ms) {
                           //短信发送
                           smsManager.sendTextMessage(phoneNumber, null, str, sentIntent, null);
                       }
                   } else {
                       smsManager.sendTextMessage(phoneNumber, null, MessageContent, sentIntent, null);
                   }
               }
                else{
                    state = 2;
               }
            }
            if(state==0) {
                DbModel model = new DbModel(ac);
                model.changeState(record);
                temp = model.getAll();
//            if(temp.size()==0){
//                HashMap<String,String > st = new HashMap<>();
//                st.put("id","-1");
//                st.put("name","");
//                st.put("phone","");
//                st.put("content","暂时没有信息哦~");
//                temp.add(st);
//            }
                Log.w("init result", temp.toString());
            }


//            successHandler.OnSuccess();
        }
        catch (Exception e){
            Log.w("error",e.toString());
            state=2;
//            failhandler.OnFail();
        }
        return temp;
    }


    @Override
    protected void onPostExecute(List<HashMap<String,String >> result){
        super.onPostExecute(result);
        Log.w("updatePost", "get");

        if(state==0){
            originRecord.clear();
            originRecord.addAll(result);
            itemadapter.notifyDataSetChanged();
            successHandler.OnSuccess();
        }
        else{
            failhandler.OnFail(state);
        }

//        itemAdapter adapter = new itemAdapter(ac,result);
//        item.setAdapter(adapter);
    }
}
