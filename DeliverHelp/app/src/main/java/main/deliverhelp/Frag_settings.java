package main.deliverhelp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableRow;
import android.widget.Toast;

/**
 * Created by LJ on 2015/8/23.
 */
public class Frag_settings extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.frag_settings, container, false);

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.w("settings frag","get");
        init();
//
    }

    public void init() {
        TableRow aboutus = (TableRow) getActivity().findViewById(R.id.aboutus);
        aboutus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(),aboutus.class));
            }
        });
        TableRow refresh = (TableRow) getActivity().findViewById((R.id.newfresh));
        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast toast = Toast.makeText(getActivity(), "已是最新版本", Toast.LENGTH_LONG);
                toast.show();
            }
        });
        TableRow messge_setting = (TableRow) getActivity().findViewById(R.id.setting_message);
        messge_setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
        startActivity(new Intent(getActivity(),message_setting.class));
            }
        });
    }

}