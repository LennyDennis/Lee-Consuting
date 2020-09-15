package com.lennydennis.leeconsulting.ui;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.lennydennis.leeconsulting.R;
import com.lennydennis.leeconsulting.databinding.FragmentRegistrationBinding;
import com.lennydennis.leeconsulting.util.FragmentUtility;

import java.util.Objects;

public class RegistrationFragment extends Fragment {

    private static final String TAG = "RegistrationFragment";
    private static final String DOMAIN_NAME = "gmail.com";

    private FragmentRegistrationBinding mFragmentRegistrationBinding;
    private ProgressBar registrationProgressBar;
    private Button registerButton;
    private String mRegistrationEmail;
    private String mRegistrationPassword;
    private String mConfirmRegistrationPassword;
    private FirebaseAuth mFirebaseAuth;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mFragmentRegistrationBinding = FragmentRegistrationBinding.inflate(inflater);
        registrationProgressBar = mFragmentRegistrationBinding.registrationProgressBar;
        registerButton = mFragmentRegistrationBinding.registerButton;

        mFirebaseAuth = FirebaseAuth.getInstance();

        mFragmentRegistrationBinding.loginLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LoginFragment loginFragment = new LoginFragment();
                FragmentUtility.replaceFragment(getActivity(),loginFragment,R.id.fragment_host,true);
            }
        });

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mRegistrationEmail = mFragmentRegistrationBinding.etRegistrationEmail.getText().toString();
                mRegistrationPassword = mFragmentRegistrationBinding.etRegistrationPassword.getText().toString();
                mConfirmRegistrationPassword = mFragmentRegistrationBinding.etConfirmPassword.getText().toString();
                if(!(mRegistrationEmail.isEmpty() && mRegistrationPassword.isEmpty() && mConfirmRegistrationPassword.isEmpty())){
                    if(isValidDomain(mRegistrationEmail)){
                        if(doStringsMatch(mRegistrationPassword, mConfirmRegistrationPassword)){
                            registerNewUser(mRegistrationEmail,mRegistrationPassword);
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

    private void registerNewUser(String email, String password){
        showDialog();
        mFirebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Log.d(TAG, "createUserWithEmail:success");
                            FirebaseUser user = mFirebaseAuth.getCurrentUser();
                            Log.d(TAG, "onComplete: "+user);
                            Toast.makeText(getActivity(), "User successfully registered", Toast.LENGTH_SHORT).show();

                            mFirebaseAuth.signOut();
                            LoginFragment loginFragment = new LoginFragment();
                            FragmentUtility.replaceFragment(getActivity(),loginFragment,R.id.fragment_host,true);

                        }else {
                            Toast.makeText(getActivity(), "registration:failure", Toast.LENGTH_SHORT).show();
                        }
                        hideDialog();
                    }
                }
        );

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