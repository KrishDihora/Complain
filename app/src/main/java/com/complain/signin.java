package com.complain;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

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

public class signin extends AppCompatActivity {

    TextInputEditText monumber,vcode;
    CardView btnsignin;
    TextView txtsignin,gotosignup;
    private FirebaseAuth mauth;
    int count=1;
    String verificationId1;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);

        monumber=findViewById(R.id.tiet_mobileno);
        vcode=findViewById(R.id.tiet_vcode);
        btnsignin=findViewById(R.id.cv_login);
        txtsignin= (TextView) findViewById(R.id.login);
        progressBar=findViewById(R.id.progress);


        FirebaseApp.initializeApp(this);
        mauth= FirebaseAuth.getInstance();

        btnsignin.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                progressBar.setVisibility(View.VISIBLE);
                if (count==1)
                {

                    if (monumber.getText().toString().isEmpty())
                    {
                        Toast.makeText(signin.this, "Please Enter Mo. Number", Toast.LENGTH_SHORT).show();
                        progressBar.setVisibility(View.GONE);
                    } else
                    {
                        progressBar.setVisibility(View.VISIBLE);
                        String number=monumber.getText().toString();
                        txtsignin.setText("Submit OTP");
                        count++;
                        sendveryficationcode(number);

                    }

                }
                else
                {
                    if (vcode.getText().toString().isEmpty())
                    {
                        Toast.makeText(signin.this, "Please Enter OTP", Toast.LENGTH_SHORT).show();
                    }
                    verifycode(vcode.getText().toString());

                }

            }
        });

        gotosignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(signin.this,signupActivity.class));
                finish();
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

            Toast.makeText(signin.this, "Verification failed. Please Try Again.", Toast.LENGTH_SHORT).show();
            progressBar.setVisibility(View.GONE);
            count++;

            if (e instanceof FirebaseAuthInvalidCredentialsException)
            {
                Toast.makeText(signin.this, "Invalid Detail Try Again After few Minutes.", Toast.LENGTH_LONG).show();
            }
            else if (e instanceof FirebaseTooManyRequestsException)
            {
                Toast.makeText(signin.this, "To many Try. Try Again After few Minutes.", Toast.LENGTH_LONG).show();            }
            else if (e instanceof FirebaseAuthMissingActivityForRecaptchaException)
            {
                Toast.makeText(signin.this, "Please Try Again After few Minutes.", Toast.LENGTH_LONG).show();
            }



        }

        @Override
        public void onCodeSent(@NonNull String verificationId, @NonNull PhoneAuthProvider.ForceResendingToken token)
        {
            super.onCodeSent(verificationId,token);
            verificationId1=verificationId;
            progressBar.setVisibility(View.GONE);
            Toast.makeText(signin.this, "SMS Has bin Sent ", Toast.LENGTH_SHORT).show();
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
                    Toast.makeText(signin.this, "SignIn Complate", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(signin.this,MainActivity.class));
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
            startActivity(new Intent(signin.this,MainActivity.class));
        }
    }
}