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

public class PnrFragment extends Fragment {
    EditText meditText;
    Button mButton;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_pnr,null);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        meditText=(EditText) view.findViewById(R.id.editText);
        mButton=(Button) view.findViewById(R.id.button);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(meditText.length()<10){
                    Toast.makeText(getActivity(), " Train No. Incorrect. ", Toast.LENGTH_SHORT).show();
                }
                else{
                    String pnr=String.valueOf(meditText.getText().toString());
                    String url = "https://api.railwayapi.com/v2/pnr-status/"+pnr+"/apikey/kgn08nprha/";
                    Intent intent=new Intent(getActivity(),Display2.class);
                    intent.putExtra("url",url);
                    startActivity(intent);
                }
            }
        });

    }
}
