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

    TextInputEditText monumber,vcode;
    CardView btnsignin;
    TextView txtsignin,gotosignup;
    private FirebaseAuth mauth;
    int count=1;
    String verificationId;
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
        vcode=findViewById(R.id.tiet_vcode);
        btnsignin=findViewById(R.id.cv_login);
        txtsignin= (TextView) findViewById(R.id.login);
        progressBar=findViewById(R.id.progress);
        gotosignup=findViewById(R.id.signup1);
        l1=findViewById(R.id.til_vcode);
        clmain=findViewById(R.id.clmain);



        FirebaseApp.initializeApp(this);
        mauth= FirebaseAuth.getInstance();

        // Force reCAPTCHA for testing
        /*FirebaseAuth.getInstance().getFirebaseAuthSettings()
                .forceRecaptchaFlowForTesting(true);*/
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

                    } else if (vcode.getText().toString().isEmpty()) {
                        Toast.makeText(SignInActivity.this, "Please Enter Verification Code", Toast.LENGTH_SHORT).show();
                        clmain.setAlpha(1f);
                        progressBar.setVisibility(View.GONE);

                    } else {
                        clmain.setAlpha(0.2f);
                        progressBar.setVisibility(View.VISIBLE);
                        String phoneNumber=monumber.getText().toString();
                        String verificationCode =vcode.getText().toString();
                        //l1.setVisibility(View.VISIBLE);
                        //txtsignin.setText("Submit OTP");
                        //count = 0;
                        //sendveryficationcode(number,code);

                        userEditor.putString("phoneNumber",phoneNumber);
                        userEditor.putString("password",verificationCode);
                        userEditor.commit();

                        startActivity(new Intent(SignInActivity.this, MainActivity.class));
                        finish();

                    }

                }
                /*else
                {
                    if (vcode.getText().toString().isEmpty())
                    {
                        Toast.makeText(SignInActivity.this, "Please Enter OTP", Toast.LENGTH_SHORT).show();
                    }else {
                        verifycode(vcode.getText().toString());
                    }


                }*/

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


    private void sendveryficationcode(String number,String code)
    {
        mauth.getFirebaseAuthSettings().setAutoRetrievedSmsCodeForPhoneNumber("+91"+number,code);
        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(mauth)
                        .setPhoneNumber("+91"+number)
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
                    String code = credential.getSmsCode();
                    if (code != null) {
                        Log.d("verificationId",code);
                        //vcode.setText(code);
                        verifycode(code);
                    }
                }

                @Override
                public void onVerificationFailed(@NonNull FirebaseException e) {
                    Toast.makeText(SignInActivity.this, "Verification failed: " + e.getMessage(), Toast.LENGTH_LONG).show();
                    Log.d("onVerificationFailed", "Verification failed: " + e.getMessage());
                    clmain.setAlpha(1f);
                    progressBar.setVisibility(View.GONE);
                    //l1.setVisibility(View.GONE);
                    count = 1;

                }

                @Override
                public void onCodeSent(@NonNull String id, @NonNull PhoneAuthProvider.ForceResendingToken token) {
                    super.onCodeSent(id, token);
                    verificationId = id;
                    Log.d("verificationId",id);
                    clmain.setAlpha(1f);
                    progressBar.setVisibility(View.GONE);
                }
            };

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

            Toast.makeText(SignInActivity.this, "Verification failed. Please Try Again.", Toast.LENGTH_SHORT).show();
            clmain.setAlpha(1f);
            progressBar.setVisibility(View.GONE);
            l1.setVisibility(View.GONE);
            txtsignin.setText("Get Verification Code");
            count = 1;

            if (e instanceof FirebaseAuthInvalidCredentialsException)
            {
                Toast.makeText(SignInActivity.this, "Invalid Detail Try Again After few Minutes.", Toast.LENGTH_LONG).show();
            }
            else if (e instanceof FirebaseTooManyRequestsException)
            {
                Toast.makeText(SignInActivity.this, "To many Try. Try Again After few Minutes.", Toast.LENGTH_LONG).show();            }
            else if (e instanceof FirebaseAuthMissingActivityForRecaptchaException)
            {
                Toast.makeText(SignInActivity.this, "Please Try Again After few Minutes.", Toast.LENGTH_LONG).show();
            }



        }

        @Override
        public void onCodeSent(@NonNull String verifId, @NonNull PhoneAuthProvider.ForceResendingToken token)
        {
            super.onCodeSent(verifId,token);
            verificationId=verifId;
            clmain.setAlpha(1f);
            progressBar.setVisibility(View.GONE);
            Toast.makeText(SignInActivity.this, "SMS has been sent ", Toast.LENGTH_SHORT).show();
        }
    };

    private void verifycode(String vcode)
    {
        Log.d("verificationId",vcode);
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId,vcode);
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
                if (task.isComplete())
                {
                    Toast.makeText(SignInActivity.this, "Sign-in Success", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(SignInActivity.this, MainActivity.class));
                    finish();
                    clmain.setAlpha(1f);
                    progressBar.setVisibility(View.GONE);
                } else {
                    Toast.makeText(SignInActivity.this, "Sign-in Failed", Toast.LENGTH_SHORT).show();
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
            startActivity(new Intent(SignInActivity.this,MainActivity.class));
        }
    }
}