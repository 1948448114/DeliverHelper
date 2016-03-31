package adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;

import main.deliverhelp.R;
import model.DbModel;

/**
 * Created by LJ on 2015/9/22.
 */
public class itemAdapter extends BaseAdapter {
    List<HashMap<String, String>> data;
    Context context;
    private LayoutInflater inflater;
    public itemAdapter(Context context,List<HashMap<String,String>> data){
        this.inflater = LayoutInflater.from(context);
        this.context=context;
        this.data=data;
//        Log.w("adapter data",data.toString());
//        DbModel model=new DbModel(context);
//        this.data = model.getAll();
    }
    @Override
    public int getCount() {
//        Log.w("datasize",Integer.toString(data.size()));
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Log.w("id",Integer.toString(position));
        ViewHolder holder = null;
        if(convertView==null){
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.item,null);
            holder.itemId = (TextView)convertView.findViewById(R.id.itemId);
            holder.name = (TextView)convertView.findViewById(R.id.name);
            holder.phone = (TextView)convertView.findViewById(R.id.phone);
            holder.item_content = (TextView)convertView.findViewById(R.id.item_content);

            convertView.setTag(holder);
        }
        else{
            holder = (ViewHolder)convertView.getTag();
        }
        holder.itemId.setText(data.get(position).get("id").toString());
        holder.name.setText(data.get(position).get("name").toString());
        holder.phone.setText(data.get(position).get("phone").toString());
        holder.item_content.setText(data.get(position).get("content").toString());
        return convertView;
    }

    public final class ViewHolder{
        public TextView itemId;
        public TextView name;
        public TextView phone;
        public TextView item_content;
    }
}
