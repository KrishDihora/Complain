package com.complain.Views.Activitys;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.complain.R;
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

public class SignInActivity extends AppCompatActivity {

    TextInputEditText monumber,enteredOTP;
    CardView btnsignin;
    TextView txtsignin,gotosignup;
    private FirebaseAuth mauth;
    int count=1;
    String verificationCode;
    ProgressBar progressBar;
    TextInputLayout l1;
    ConstraintLayout clmain;
    SharedPreferences userPreference;
    SharedPreferences.Editor userEditor;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);

        monumber=findViewById(R.id.tiet_number);
        enteredOTP=findViewById(R.id.tiet_vcode);
        btnsignin=findViewById(R.id.cv_login);
        txtsignin= (TextView) findViewById(R.id.login);
        progressBar=findViewById(R.id.progress);
        gotosignup=findViewById(R.id.signup1);
        l1=findViewById(R.id.til_vcode);
        clmain=findViewById(R.id.clmain);



        FirebaseApp.initializeApp(this);
        mauth= FirebaseAuth.getInstance();

        // Force reCAPTCHA for testing
        FirebaseAuth.getInstance().getFirebaseAuthSettings()
                .forceRecaptchaFlowForTesting(true);
        FirebaseAuth.getInstance().getFirebaseAuthSettings()
                .setAppVerificationDisabledForTesting(true);

        //Local Database
        userPreference = getSharedPreferences("user",MODE_PRIVATE);
        userEditor = userPreference.edit();

        btnsignin.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                clmain.setAlpha(0.2f);
                progressBar.setVisibility(View.VISIBLE);
                if (count==1)
                {

                    if (monumber.getText().toString().isEmpty()) {
                        Toast.makeText(SignInActivity.this, "Please Enter Mo. Number", Toast.LENGTH_SHORT).show();
                        clmain.setAlpha(1f);
                        progressBar.setVisibility(View.GONE);

                    } /*else if (enteredOTP.getText().toString().isEmpty()) {
                        Toast.makeText(SignInActivity.this, "Please Enter Verification Code", Toast.LENGTH_SHORT).show();
                        clmain.setAlpha(1f);
                        progressBar.setVisibility(View.GONE);

                    }*/ else {
                        clmain.setAlpha(0.2f);
                        progressBar.setVisibility(View.VISIBLE);

                        String phoneNumber=monumber.getText().toString();
                        //String verificationCode =vcode.getText().toString();

                        l1.setVisibility(View.VISIBLE);
                        txtsignin.setText("Submit OTP");
                        count = 0;

                        sendveryficationcode("+91 " + phoneNumber);

                        /*userEditor.putString("phoneNumber",phoneNumber);
                        userEditor.
                        putString("password",verificationCode);
                        userEditor.commit();

                        startActivity(new Intent(SignInActivity.this, MainActivity.class));
                        finish();*/

                    }

                }
                else
                {
                    if (enteredOTP.getText().toString().isEmpty())
                    {
                        Toast.makeText(SignInActivity.this, "Please Enter OTP", Toast.LENGTH_SHORT).show();
                    }else {
                        verifycode(enteredOTP.getText().toString());
                    }


                }

            }
        });

        gotosignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SignInActivity.this, signupActivity.class));
                finish();
            }
        });
    }


    private void sendveryficationcode(String number)
    {
        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(mauth)
                        .setPhoneNumber(number)
                        .setTimeout(60L, TimeUnit.SECONDS)
                        .setActivity(this)
                        .setCallbacks(testCallbacks)
                        .build();
        PhoneAuthProvider.verifyPhoneNumber(options);


    }

    private final PhoneAuthProvider.OnVerificationStateChangedCallbacks testCallbacks =
            new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

                @Override
                public void onVerificationCompleted(@NonNull PhoneAuthCredential credential) {
                    signinbycredential(credential);
                }

                @Override
                public void onVerificationFailed(@NonNull FirebaseException e) {
                    Toast.makeText(SignInActivity.this, "Verification failed: " + e.getMessage(), Toast.LENGTH_LONG).show();

                    clmain.setAlpha(1f);
                    progressBar.setVisibility(View.GONE);

                    l1.setVisibility(View.GONE);
                    txtsignin.setText("Sign In");

                    count = 1;

                }

                @Override
                public void onCodeSent(@NonNull String id, @NonNull PhoneAuthProvider.ForceResendingToken token) {
                    super.onCodeSent(id, token);
                    verificationCode = id;

                    Toast.makeText(SignInActivity.this, "OTP has been sent", Toast.LENGTH_LONG).show();

                    clmain.setAlpha(1f);
                    progressBar.setVisibility(View.GONE);
                }
            };

    private void verifycode(String enteredOTP)
    {
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationCode,enteredOTP);
        signinbycredential(credential);

        clmain.setAlpha(0.2f);
        progressBar.setVisibility(View.VISIBLE);

    }

    private void signinbycredential(PhoneAuthCredential credential)
    {
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        firebaseAuth.signInWithCredential(credential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task)
            {
                if (task.isSuccessful())
                {
                    Toast.makeText(SignInActivity.this, "Sign-in Success", Toast.LENGTH_SHORT).show();

                    startActivity(new Intent(SignInActivity.this, MainActivity.class));
                    finish();

                    clmain.setAlpha(1f);
                    progressBar.setVisibility(View.GONE);
                } else {
                    Toast.makeText(SignInActivity.this, "Incorrect OTP", Toast.LENGTH_SHORT).show();

                    clmain.setAlpha(1f);
                    progressBar.setVisibility(View.GONE);
                }
            }
        });
    }

   @Override
    protected void onStart() {
        super.onStart();
        /*FirebaseUser firebaseUser=FirebaseAuth.getInstance().getCurrentUser();

        if (firebaseUser!=null)
        {
            startActivity(new Intent(SignInActivity.this,MainActivity.class));
        }*/
    }
}