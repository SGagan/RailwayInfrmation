package com.example.gagan.railway;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LiveTrainStatusFragment extends Fragment {
    EditText mEditText,mEditText2;
    Button mButton;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_livestatus,null);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mEditText=(EditText) view.findViewById(R.id.editText);
        mEditText2=(EditText)view.findViewById(R.id.date);
        mButton=(Button) view.findViewById(R.id.button);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                    String train=String.valueOf(mEditText.getText().toString());
                    String date=String.valueOf(mEditText2.getText().toString());
                    String url = "https://api.railwayapi.com/v2/live/train/"+train+"/date/13-07-2018/apikey/kgn08nprha/";
                    Intent intent=new Intent(getActivity(),LiveTrainStatusActivity.class);
                    intent.putExtra("url",url);
                    startActivity(intent);

            }
        });

    }
}