package com.nds.baking.king.views.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.nds.baking.king.R;

/**
 * Created by Namrata Shah on 5/8/2017.
 */

public class ErrorMessageFragment extends Fragment {
    private String errorMsg;
    private static final String ERROR_MSG_KEY="error";

    public static ErrorMessageFragment newInstance(String msg){
        ErrorMessageFragment fragment = new ErrorMessageFragment();
        Bundle bundle = new Bundle();
        bundle.putString(ERROR_MSG_KEY, msg);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(savedInstanceState != null && savedInstanceState.containsKey(ERROR_MSG_KEY))
            errorMsg = savedInstanceState.getString(ERROR_MSG_KEY);
        else if(getArguments()!=null && getArguments().getString(ERROR_MSG_KEY)!=null)
            errorMsg = getArguments().getString(ERROR_MSG_KEY);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View errorView = inflater.inflate(R.layout.error_message_fragment, container, false);
        TextView errorMsgTV = (TextView)errorView.findViewById(R.id.errorMessageTV);
        errorMsgTV.setText(errorMsg);
        Button okButton = (Button)errorView.findViewById(R.id.okButton);
        okButton.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });
        return errorView;
    }
}
