package main.deliverhelp;

import android.app.Activity;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import Message.Template;


public class message_setting extends Activity {
    SharedPreferences messageData;
    Editor editor;
    EditText settings;
    Template template;
    public final String DEFAULT_STRING = "没有模板，快来添加吧";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.message_setting);
        init();
    }

    public void init(){
        template = new Template(this);

        TextView back = (TextView)findViewById(R.id.message_setting_return);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        settings = (EditText)findViewById(R.id.editText);
        getData();
        Button change_message_button = (Button)findViewById(R.id.change_message);
        change_message_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveData();
                getData();
            }
        });

    }

    public void saveData(){

        String ChangeData = settings.getText().toString();
        if(template.saveData(ChangeData)){
            Toast toast = Toast.makeText(this, "保存成功", Toast.LENGTH_LONG);
            toast.show();
        }
        else{
            Toast toast = Toast.makeText(this, "保存失败", Toast.LENGTH_LONG);
            toast.show();
        }

    }

    public String getData(){
        String origin_data = template.getContent();
        origin_data = origin_data.length()>0?origin_data:DEFAULT_STRING;
        settings.setText(origin_data);
        return origin_data;
    }


}
