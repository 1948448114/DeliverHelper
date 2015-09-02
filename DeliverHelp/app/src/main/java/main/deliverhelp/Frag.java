package main.deliverhelp;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by LJ on 2015/8/23.
 */
public class Frag extends Fragment{
    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container,Bundle savedInstanceState)
    {
        return inflater.inflate(R.layout.frag_home,container,false);
    }

//    @Override
//    public void onActivityCreated(Bundle savedInstanceState){
//        super.onActivityCreated(savedInstanceState);
//        TextView text = (TextView)getActivity().findViewById(R.id.text);
//        text.setText("hello");
//    }
}
