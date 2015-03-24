package sendMessage;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.telephony.SmsManager;
import android.widget.Toast;

import java.util.List;

import main.deliverhelp.MainActivity;

/**
 * Created by LJ on 2015/3/24.
 */
public class sendMessage extends Thread{
    String url = "";
    boolean flag=true;
    String phoneNumber="";
    String content="";
    Context ac;
    public sendMessage(Context ac,String phoneNumber,String content){
        this.ac=ac;
        this.phoneNumber=phoneNumber;
        this.content=content;
        start();
    }

   @Override
   public void run(){
       SmsManager smsManager = SmsManager.getDefault();
       PendingIntent sentIntent = PendingIntent.getBroadcast(ac, 0, new Intent(), 0);

       if(content.length() >= 70)
       {
           //短信字数大于70，自动分条
           List<String> ms = smsManager.divideMessage(content);

           for(String str : ms )
           {
               //短信发送
               smsManager.sendTextMessage(phoneNumber, null, str, sentIntent, null);
           }
       }
       else
       {
           smsManager.sendTextMessage(phoneNumber, null, content, sentIntent, null);
       }
       onSuccess();
   }



    private void onSuccess(){
        flag=true;
    }

    private  void onFailed(){
        flag=false;
    }
}
