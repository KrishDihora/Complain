package com.complain;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthMissingActivityForRecaptchaException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class signupActivity extends AppCompatActivity {

    TextInputEditText name,number;
    TextInputEditText verificationcode;
    TextInputEditText otp;
    TextInputLayout l1;
    TextView tvsignup;
    CardView btnlogin,btnsignup;
    FirebaseAuth mauth;
    private int count=1;
    String verificationId1;
    ProgressBar progressBar;
    ConstraintLayout  cl_login,cl_logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        name=findViewById(R.id.tiet_name);
        number=findViewById(R.id.tiet_mobileno);
        verificationcode =findViewById(R.id.tiet_otp);
        otp=findViewById(R.id.tiet_otp);
        btnsignup= findViewById(R.id.cv_signup);
        btnlogin =findViewById(R.id.cv_login);
        l1=findViewById(R.id.til_otp);
        tvsignup=findViewById(R.id.signup);
        progressBar=findViewById(R.id.progress);

        //initializing firebase
        FirebaseApp.initializeApp(this);
        mauth=FirebaseAuth.getInstance();


        btnsignup.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                progressBar.setVisibility(View.VISIBLE);
                if (count==1)
                {
                    if (name.getText().toString().isEmpty())
                    {
                        Toast.makeText(signupActivity.this, "Please Enter Name", Toast.LENGTH_SHORT).show();
                        progressBar.setVisibility(View.GONE);
                    }
                    else if (number.getText().toString().isEmpty())
                    {
                        Toast.makeText(signupActivity.this, "Please Enter Mo. Number", Toast.LENGTH_SHORT).show();
                        progressBar.setVisibility(View.GONE);
                    } else
                    {
                    progressBar.setVisibility(View.VISIBLE);
                    String monumber=number.getText().toString();
                    l1.setVisibility(View.VISIBLE);
                    tvsignup.setText("Submit OTP");
                    count++;
                    sendveryficationcode(monumber);

                    }

                }
                else
                {
                    if (otp.getText().toString().isEmpty())
                    {
                        Toast.makeText(signupActivity.this, "Please Enter OTP", Toast.LENGTH_SHORT).show();
                    }
                    verifycode(otp.getText().toString());

                }

            }
        });






    }

    private void sendveryficationcode(String number)
    {
        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(mauth)
                        .setPhoneNumber("+91"+number)
                        .setTimeout(60L, TimeUnit.SECONDS)
                        .setActivity(this)
                        .setCallbacks(mCallbacks)
                        .build();
        PhoneAuthProvider.verifyPhoneNumber(options);


    }

    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks()
    {

        @Override
        public void onVerificationCompleted(@NonNull PhoneAuthCredential credential)
        {

            final String code=credential.getSmsCode();
            if (code!=null)
            {
                verifycode(code);
            }

        }

        @Override
        public void onVerificationFailed(@NonNull FirebaseException e)
        {

            Log.w(TAG, "onVerificationFailed", e);

            Toast.makeText(signupActivity.this, "Verification failed. Please Try Again.", Toast.LENGTH_SHORT).show();
            progressBar.setVisibility(View.GONE);
            l1.setVisibility(View.INVISIBLE);
            count++;

            if (e instanceof FirebaseAuthInvalidCredentialsException)
            {
                Toast.makeText(signupActivity.this, "Invalid Detail Try Again After few Minutes.", Toast.LENGTH_LONG).show();
            }
            else if (e instanceof FirebaseTooManyRequestsException)
            {
                Toast.makeText(signupActivity.this, "To many Try. Try Again After few Minutes.", Toast.LENGTH_LONG).show();            }
            else if (e instanceof FirebaseAuthMissingActivityForRecaptchaException)
            {
                Toast.makeText(signupActivity.this, "Please Try Again After few Minutes.", Toast.LENGTH_LONG).show();
            }



        }

        @Override
        public void onCodeSent(@NonNull String verificationId, @NonNull PhoneAuthProvider.ForceResendingToken token)
        {
            super.onCodeSent(verificationId,token);
            verificationId1=verificationId;
            progressBar.setVisibility(View.GONE);
            Toast.makeText(signupActivity.this, "SMS Has bin Sent ", Toast.LENGTH_SHORT).show();
        }
    };

    private void verifycode(String vcode)
    {

        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId1,vcode);
        signinbycredential(credential);
        progressBar.setVisibility(View.VISIBLE);

    }

    private void signinbycredential(PhoneAuthCredential credential)
    {
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        firebaseAuth.signInWithCredential(credential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task)
            {
                if (task.isComplete())
                {
                    Toast.makeText(signupActivity.this, "Signup Complate", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(signupActivity.this,MainActivity.class));
                    finish();
                    progressBar.setVisibility(View.GONE);
                }
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser firebaseUser=FirebaseAuth.getInstance().getCurrentUser();

        if (firebaseUser!=null)
        {
            startActivity(new Intent(signupActivity.this,MainActivity.class));
        }
    }
}