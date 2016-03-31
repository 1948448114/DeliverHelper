package excelDeal;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import Message.SuccessHandler;
import adapter.itemAdapter;
import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import model.DbModel;
import model.deliver;


/**
 * Created by LJ on 2015/9/29.
 */
public class DealExcelTask extends AsyncTask<Void,Void,List<HashMap<String,String >>>{
    public Context context;
    public ListView item;
    itemAdapter itemadapter;
    List<HashMap<String, String>> data ;
    List<HashMap<String, String>> record;
    SuccessHandler successHandler;
    public DealExcelTask(Context context,ListView item,List<HashMap<String, String>> record, SuccessHandler successHandler){
        super();
//        this.itemadapter = itemadapter;
        this.context=context;
        this.item=item;
        this.record = record;
        this.data = record;
        this.successHandler = successHandler;
    }


    public void save_to_db(List<HashMap<String, String>> data){
        DbModel model = new DbModel(context);
        for(int i=0;i<data.size();i++){
            HashMap<String, String> map = data.get(i);
            deliver item = new deliver(map.get("id"),map.get("name"),map.get("content"),map.get("phone"),0);
            model.save(item);
        }
    }

    @Override
    protected List<HashMap<String, String>> doInBackground(Void... params) {
        try {
            Workbook workbook = null;

            try {
                File file = new File(Environment.getExternalStorageDirectory() + File.separator + "test.xls");
                System.out.println(Environment.getExternalStorageDirectory() + File.separator);


//                File[] subFile = file.listFiles();
//
//                for (int iFileLength = 0; iFileLength < subFile.length; iFileLength++) {
//                    // 判断是否为文件夹
//                    if (!subFile[iFileLength].isDirectory()) {
//                        String filename = subFile[iFileLength].getName();
//                        // 判断是否为MP4结尾
//                        System.out.println(filename);
//                    }
//                }
                workbook = Workbook.getWorkbook(file);
            } catch (Exception e) {
                Toast toast = Toast.makeText(context, "文件不存在", Toast.LENGTH_LONG);
                toast.show();
                System.out.print(e.toString());
                System.out.println("File not found");
            }
            //得到第一张表
            Sheet sheet = workbook.getSheet(0);
            //列数
            int columnCount = sheet.getColumns();
            //行数
            int rowCount = sheet.getRows();
            //单元格
            Cell cell = null;
            for (int everyRow = 1; everyRow < rowCount; everyRow++) {
                HashMap<String, String> map = new HashMap<String, String>();
                map.put("id",sheet.getCell(0, everyRow).getContents());
                map.put("name", sheet.getCell(1, everyRow).getContents());
                map.put("phone", sheet.getCell(2, everyRow).getContents());
                map.put("content", sheet.getCell(3, everyRow).getContents());
                data.add(map);
            }
            //关闭workbook,防止内存泄露
            workbook.close();
            save_to_db(data);//将数据存入数据库
            Log.w("background","ok");
        } catch (Exception e) {
            Log.w("error",e.toString());
        }

        return data;
    }


    @Override
    protected void onPostExecute(List<HashMap<String,String >> result){
        super.onPostExecute(result);
        Log.w("taskPost","get");
//        itemAdapter adapter = new itemAdapter(context,result);
//        record = result;
        Log.w("deal ok data",record.toString());
        successHandler.OnSuccess();
//        item.setAdapter(itemadapter);
//        itemadapter.notifyDataSetChanged();
//        item.setAdapter(itemadapter);
    }
}
