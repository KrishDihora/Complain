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

public class signupActivity extends AppCompatActivity {

    TextInputEditText name,number;
    TextInputEditText verificationcode;
    TextInputEditText otp;
    TextInputLayout l1;
    TextView tvsignup,gotosign;
    CardView btnsignup;
    FirebaseAuth mauth;
    private int count=1;
    String verificationId;
    ProgressBar progressBar;
    ConstraintLayout  cl_login,cl_logout,clmain;
    String testPhoneNumber = "9999999999";
    String testVerificationCode = "999999";
    SharedPreferences userPreference;
    SharedPreferences.Editor userEditor;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        name=findViewById(R.id.tiet_name);
        number=findViewById(R.id.tiet_mobileno);
        verificationcode =findViewById(R.id.tiet_otp);
        otp=findViewById(R.id.tiet_otp);
        btnsignup= findViewById(R.id.cv_signup);
        l1=findViewById(R.id.til_otp);
        tvsignup=findViewById(R.id.signup);
        progressBar=findViewById(R.id.progress);
        gotosign= (TextView) findViewById(R.id.signin);
        clmain=findViewById(R.id.clmain);

        //initializing firebase
        FirebaseApp.initializeApp(this);
        mauth=FirebaseAuth.getInstance();

        // Force reCAPTCHA for testing
        /*FirebaseAuth.getInstance().getFirebaseAuthSettings()
                .forceRecaptchaFlowForTesting(true);*/
        FirebaseAuth.getInstance().getFirebaseAuthSettings()
                .setAppVerificationDisabledForTesting(true);

        //Local Database
        userPreference = getSharedPreferences("user",MODE_PRIVATE);
        userEditor = userPreference.edit();


        btnsignup.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                clmain.setAlpha(0.2f);
                progressBar.setVisibility(View.VISIBLE);
                if (count==1)
                {
                    if (name.getText().toString().isEmpty()) {
                        Toast.makeText(signupActivity.this, "Please Enter Name", Toast.LENGTH_SHORT).show();
                        clmain.setAlpha(1f);
                        progressBar.setVisibility(View.GONE);

                    } else if (number.getText().toString().isEmpty()) {
                        Toast.makeText(signupActivity.this, "Please Enter Mo. Number", Toast.LENGTH_SHORT).show();
                        clmain.setAlpha(1f);
                        progressBar.setVisibility(View.GONE);

                    }else if (otp.getText().toString().isEmpty()) {
                        Toast.makeText(signupActivity.this, "Please Enter OTP", Toast.LENGTH_SHORT).show();
                        clmain.setAlpha(1f);
                        progressBar.setVisibility(View.GONE);

                    } else {
                        clmain.setAlpha(0.2f);
                        progressBar.setVisibility(View.VISIBLE);
                        String phoneNumber=number.getText().toString();
                        String verificationCode=otp.getText().toString();
                        //l1.setVisibility(View.VISIBLE);
                        //tvsignup.setText("Submit OTP");
                        //count = 0;
                        //sendveryficationcode(monumber,code);

                        userEditor.putString("phoneNumber",phoneNumber);
                        userEditor.putString("password",verificationCode);
                        userEditor.commit();

                        startActivity(new Intent(signupActivity.this, MainActivity.class));
                        finish();

                    }

                }
                else
                {
                    /*if (otp.getText().toString().isEmpty())
                    {
                        Toast.makeText(signupActivity.this, "Please Enter OTP", Toast.LENGTH_SHORT).show();
                    }else {
                        verifycode(otp.getText().toString());
                    }*/


                }

            }
        });


        gotosign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(signupActivity.this, SignInActivity.class));
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
                        otp.setText(code);
                        verifycode(code);
                    }
                }

                @Override
                public void onVerificationFailed(@NonNull FirebaseException e) {
                    Toast.makeText(signupActivity.this, "Verification failed: " + e.getMessage(), Toast.LENGTH_LONG).show();
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

            final String code=otp.getText().toString();
            if (code!=null)
            {
                verifycode(code);
            }

        }

        @Override
        public void onVerificationFailed(@NonNull FirebaseException e)
        {

            Log.w("TAG", "onVerificationFailed", e);
            Log.d("Failed", e.toString());

            Toast.makeText(signupActivity.this, "Verification failed. Please Try Again.", Toast.LENGTH_SHORT).show();
            clmain.setAlpha(1f);
            progressBar.setVisibility(View.GONE);
            l1.setVisibility(View.GONE);
            tvsignup.setText("Sign Up");
            count = 1;

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
        public void onCodeSent(@NonNull String verifId, @NonNull PhoneAuthProvider.ForceResendingToken token)
        {
            super.onCodeSent(verifId,token);
            verificationId=verifId;
            clmain.setAlpha(1f);
            progressBar.setVisibility(View.GONE);
            Toast.makeText(signupActivity.this, "SMS has been sent ", Toast.LENGTH_SHORT).show();
        }
    };

    private void verifycode(String vcode)
    {

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
                    Toast.makeText(signupActivity.this, "Sign-in Success", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(signupActivity.this, MainActivity.class));
                    finish();
                    clmain.setAlpha(1f);
                    progressBar.setVisibility(View.GONE);
                } else {
                    Toast.makeText(signupActivity.this, "Sign-in Failed", Toast.LENGTH_SHORT).show();
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