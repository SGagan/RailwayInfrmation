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

public class TrainRouteFragment extends Fragment {
    EditText mEditText;
    Button mButton;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_trainroute,null);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mEditText=(EditText) view.findViewById(R.id.editText);
        mButton=(Button) view.findViewById(R.id.button);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mEditText.length()<5){
                    Toast.makeText(getActivity(), " Train No. Incorrect. ", Toast.LENGTH_SHORT).show();
                }
                else{
                    String train=String.valueOf(mEditText.getText().toString());
                    String url = "https://api.railwayapi.com/v2/route/train/"+train+"/apikey/kgn08nprha/";
                    Intent intent=new Intent(getActivity(),TrainRouteActivity.class);
                    intent.putExtra("url",url);
                    startActivity(intent);
                }
            }
        });

    }
}