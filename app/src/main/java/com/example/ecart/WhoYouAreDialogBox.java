package com.example.ecart;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.fragment.app.DialogFragment;

public class WhoYouAreDialogBox extends DialogFragment {

    static WhoYouAreDialogBox newInstace(String q){
        WhoYouAreDialogBox fragment=new WhoYouAreDialogBox();
        Bundle args=new Bundle();
        args.putString("title", q);
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        String title = getArguments().getString("title");
        return new AlertDialog.Builder(getActivity())
                .setIcon(R.drawable.ic_baseline_account_circle_24)
                .setTitle(title)
                .setPositiveButton("Customer",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                ((Login) getActivity()).clickedOnCustomer();
                            }
                        })
                .setNegativeButton("Businessman",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                ((Login) getActivity()).clickedOnBusinessman();
                            }
                        }).create();
    }
}