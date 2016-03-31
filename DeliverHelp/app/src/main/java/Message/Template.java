package Message;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by LJ on 2015/10/16.
 */
public class Template {
    SharedPreferences messageData;
    SharedPreferences.Editor editor;
    Context context;
    public Template(Context context){
        this.context = context;
        init();
    }

    public void init(){
        messageData = context.getSharedPreferences("message_setting",Context.MODE_PRIVATE);
        editor = messageData.edit();
    }
    public String getMessage(String deliverid){
        try {
            String messageContent = getContent();
            String[] temp = messageContent.split("\\{");
            if(temp.length>2){
                return null;
            }
            String before = temp[0];
            String after = temp[1].split("\\}")[1];
            return before + deliverid + after;
        }
        catch (Exception e){
            return null;
        }
    }
    public boolean saveData(String data){
        try {
            String[] temp = data.split("\\{");
            int length = temp.length;
            Set<String> replace_array =new HashSet<String>();
            for (int item=1;item<length;item++) {
                replace_array.add(temp[item].split("\\}")[0]);
            }
            editor.putString("content", data);
            editor.putStringSet("replace",replace_array);
            editor.commit();
           return true;
        }
        catch (Exception e){
            Log.w("error", e.toString());
            return false;
        }
    }

    public String getContent(){
        return messageData.getString("content","");
    }

    public Set<String> getReplace(){
        return messageData.getStringSet("replace",new HashSet<String>());
    }


}
