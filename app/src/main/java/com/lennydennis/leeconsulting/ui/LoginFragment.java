package com.lennydennis.leeconsulting.ui;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Toast;

import com.lennydennis.leeconsulting.R;
import com.lennydennis.leeconsulting.databinding.FragmentLoginBinding;


public class LoginFragment extends Fragment {

    private static final String TAG = "LoginFragment";

    private FragmentLoginBinding mFragmentLoginBinding;
    private String loginEmail;
    private String loginPassword;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mFragmentLoginBinding = FragmentLoginBinding.inflate(inflater);
        loginEmail = mFragmentLoginBinding.etLoginEmail.getText().toString();
        loginPassword = mFragmentLoginBinding.etLoginPassword.getText().toString();

        mFragmentLoginBinding.loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(!loginEmail.isEmpty() && loginPassword.isEmpty()){
                    Log.d(TAG, "onClick: Authenitication");
                }else{
                    Toast.makeText(getContext(), "You didn't fill in all the fields.", Toast.LENGTH_SHORT).show();
                }

            }
        });

        mFragmentLoginBinding.tvRegisterLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        mFragmentLoginBinding.tvForgotPasswordLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        mFragmentLoginBinding.tvEmailVerfication.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        return mFragmentLoginBinding.getRoot();
    }

    private boolean isEmpty(String string){
        return string.equals("");
    }


    private void showDialog(){
        mFragmentLoginBinding.loginProgressBar.setVisibility(View.VISIBLE);

    }

    private void hideDialog(){
        if(mFragmentLoginBinding.loginProgressBar.getVisibility() == View.VISIBLE){
            mFragmentLoginBinding.loginProgressBar.setVisibility(View.INVISIBLE);
        }
    }

    private void hideSoftKeyboard(){
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }
}