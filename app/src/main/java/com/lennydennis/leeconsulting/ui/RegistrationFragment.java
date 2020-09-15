package com.lennydennis.leeconsulting.ui;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.lennydennis.leeconsulting.R;
import com.lennydennis.leeconsulting.databinding.FragmentRegistrationBinding;

public class RegistrationFragment extends Fragment {

    private static final String TAG = "RegistrationFragment";
    private static final String DOMAIN_NAME = "gmail.com";

    private FragmentRegistrationBinding mFragmentRegistrationBinding;
    private String registrationEmail, registrationPassword, confirmRegistrationPassword;
    private ProgressBar registrationProgressBar;
    private Button registerButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mFragmentRegistrationBinding = FragmentRegistrationBinding.inflate(inflater);
        registrationEmail = mFragmentRegistrationBinding.etRegistrationEmail.getText().toString();
        registrationPassword = mFragmentRegistrationBinding.etRegistrationPassword.getText().toString();
        confirmRegistrationPassword = mFragmentRegistrationBinding.etConfirmPassword.getText().toString();
        registrationProgressBar = mFragmentRegistrationBinding.registrationProgressBar;
        registerButton = mFragmentRegistrationBinding.registerButton;

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!registrationEmail.isEmpty() && registrationPassword.isEmpty() && confirmRegistrationPassword.isEmpty()){
                    if(isValidDomain(registrationEmail)){
                        if(doStringsMatch(registrationPassword,confirmRegistrationPassword)){

                        }else{
                            Toast.makeText(getActivity(), "Passwords do not Match", Toast.LENGTH_SHORT).show();
                        }
                    }else{
                        Toast.makeText(getActivity(), "Please Register with Company Email", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(getActivity(), "You must fill out all the fields", Toast.LENGTH_SHORT).show();
                }
            }
        });

        hideSoftKeyboard();

        return mFragmentRegistrationBinding.getRoot();
    }

    private boolean isValidDomain(String email){
        Log.d(TAG, "isValidDomain: verifying email has correct domain: " + email);
        String domain = email.substring(email.indexOf("@") + 1).toLowerCase();
        Log.d(TAG, "isValidDomain: users domain: " + domain);
        return domain.equals(DOMAIN_NAME);
    }

    private void redirectLoginScreen(){

    }

    private boolean doStringsMatch(String s1, String s2){
        return s1.equals(s2);
    }

    private void showDialog(){
        registrationProgressBar.setVisibility(View.VISIBLE);

    }

    private void hideDialog(){
        if(registrationProgressBar.getVisibility() == View.VISIBLE){
            registrationProgressBar.setVisibility(View.INVISIBLE);
        }
    }

    private void hideSoftKeyboard(){
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }
}