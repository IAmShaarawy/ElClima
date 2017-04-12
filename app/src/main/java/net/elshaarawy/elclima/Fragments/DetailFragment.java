package net.elshaarawy.elclima.Fragments;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import net.elshaarawy.elclima.R;

/**
 * Created by elshaarawy on 26-Mar-17.
 */

public class DetailFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detail,container,false);
        Intent intent= this.getActivity().getIntent();
        String data=null;
        if (intent != null && intent.hasExtra(Intent.EXTRA_TEXT) ){
            data =intent.getStringExtra(Intent.EXTRA_TEXT);
        }
        TextView textView  =(TextView) view.findViewById(R.id.detail_text);
        textView.setText(data);
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
}
