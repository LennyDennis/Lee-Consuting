package com.lennydennis.leeconsulting.ui;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.lennydennis.leeconsulting.R;
import com.lennydennis.leeconsulting.databinding.FragmentLoginBinding;
import com.lennydennis.leeconsulting.util.FragmentUtility;


public class LoginFragment extends Fragment {

    private static final String TAG = "LoginFragment";

    private FragmentLoginBinding mFragmentLoginBinding;
    private String loginEmail;
    private String loginPassword;
    private FirebaseAuth mFirebaseAuth;
    private FirebaseAuth.AuthStateListener firebaseAuthStateListener;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mFragmentLoginBinding = FragmentLoginBinding.inflate(inflater);
        mFirebaseAuth = FirebaseAuth.getInstance();

        setupFirebaseAuth();

        
        mFragmentLoginBinding.loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginEmail = mFragmentLoginBinding.etLoginEmail.getText().toString();
                loginPassword = mFragmentLoginBinding.etLoginPassword.getText().toString();
                if(!(loginEmail.isEmpty() && loginPassword.isEmpty())){
                    Log.d(TAG, "onClick: Authenitication");
                    showDialog();
                    mFirebaseAuth.signInWithEmailAndPassword(loginEmail,loginPassword)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    hideDialog();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getActivity(), "Authentication Failed", Toast.LENGTH_SHORT).show();
                        }
                    });
                }else{
                    Toast.makeText(getContext(), "You didn't fill in all the fields.", Toast.LENGTH_SHORT).show();
                }

            }
        });

        mFragmentLoginBinding.tvRegisterLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RegistrationFragment registrationFragment = new RegistrationFragment();
                FragmentUtility.replaceFragment(getActivity(),registrationFragment, R.id.fragment_host,true);

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
    
    private void setupFirebaseAuth(){
        firebaseAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if(user != null){
                    if(user.isEmailVerified()){
                        Log.d(TAG, "onAuthStateChanged: User is logged in "+ user.getUid());
                    }else{
                        Toast.makeText(getActivity(), "Check your verification link", Toast.LENGTH_SHORT).show();
                        mFirebaseAuth.signOut();
                    }
                }else{
                    Log.d(TAG, "onAuthStateChanged: User not logged in");
                }
            }
        };
    }

    @Override
    public void onStart() {
        super.onStart();
        mFirebaseAuth.addAuthStateListener(firebaseAuthStateListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        mFirebaseAuth.removeAuthStateListener(firebaseAuthStateListener);
    }
}