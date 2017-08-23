package com.example.android1.login_signup;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;
import android.widget.Toast;

import static android.widget.Toast.LENGTH_LONG;


public class Signup extends AppCompatActivity implements View.OnClickListener {

    private final AppCompatActivity activity = Signup.this;

    private NestedScrollView nestedScrollView;

    private TextInputLayout textInputLayoutName;
    private TextInputLayout textInputLayoutEmail;
    private TextInputLayout textInputLayoutPassword;
    private TextInputLayout textInputLayoutConfirmPassword;

    private TextInputEditText textInputEditTextName;
    private TextInputEditText textInputEditTextEmail;
    private TextInputEditText textInputEditTextPassword;
    private TextInputEditText textInputEditTextConfirmPassword;

    private AppCompatButton appCompatButtonRegister;
    private AppCompatTextView appCompatTextViewLoginLink;

    private InputValidation inputValidation;
    private DatabaseHelper databaseHelper;
    private User user;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);


        initViews();
        initListeners();
        initObjects();
    }

    /**
     * This method is to initialize views
     */
    private void initViews() {
        nestedScrollView = (NestedScrollView) findViewById(R.id.nestedScrollView);

        textInputLayoutName = (TextInputLayout) findViewById(R.id.namelayout);
        textInputLayoutEmail = (TextInputLayout) findViewById(R.id.emaillayout);
        textInputLayoutPassword = (TextInputLayout) findViewById(R.id.passwordlayout);
        textInputLayoutConfirmPassword = (TextInputLayout) findViewById(R.id.confirm_pass_layout);

        textInputEditTextName = (TextInputEditText) findViewById(R.id.editname);
        textInputEditTextEmail = (TextInputEditText) findViewById(R.id.editemail);
        textInputEditTextPassword = (TextInputEditText) findViewById(R.id.editpassword);
        textInputEditTextConfirmPassword = (TextInputEditText) findViewById(R.id.edit_confirm_pass);

        appCompatButtonRegister = (AppCompatButton) findViewById(R.id.register);

        appCompatTextViewLoginLink = (AppCompatTextView) findViewById(R.id.login_1);

    }

    /**
     * This method is to initialize listeners
     */
    private void initListeners() {
        appCompatButtonRegister.setOnClickListener(this);
        appCompatTextViewLoginLink.setOnClickListener(this);

    }

    /**
     * This method is to initialize objects to be used
     */
    private void initObjects() {
        inputValidation = new InputValidation(activity);
        databaseHelper = new DatabaseHelper(activity);
        user = new User();

    }


    /**
     * This implemented method is to listen the click on view
     *
     * @param v
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.register:
                if(postDataToSQLite()) {
                    Intent in = new Intent(getApplicationContext(), Welcome.class);
                    startActivity(in);
                }
                else
                {
                    finish();
                    Toast.makeText(getApplicationContext(),"Try Again",LENGTH_LONG).show();
                    Intent in = new Intent(getApplicationContext(),Signup.class);
                    startActivity(in);

                }

                break;

            case R.id.login_1:
                finish();
                break;
        }
    }

    /**
     * This method is to validate the input text fields and post data to SQLite
     */
    private boolean postDataToSQLite() {
        if (!inputValidation.isInputEditTextFilled(textInputEditTextName, textInputLayoutName, getString(R.string.error_message_name))) {
            return false;
        }
        if (!inputValidation.isInputEditTextFilled(textInputEditTextEmail, textInputLayoutEmail, getString(R.string.error_message_email))) {
            return false;
        }
        if (!inputValidation.isInputEditTextEmail(textInputEditTextEmail, textInputLayoutEmail, getString(R.string.error_message_email))) {
            return false;
        }
        if (!inputValidation.isInputEditTextFilled(textInputEditTextPassword, textInputLayoutPassword, getString(R.string.error_message_password))) {
            return false;
        }
        if (!inputValidation.isInputEditTextMatches(textInputEditTextPassword, textInputEditTextConfirmPassword,
                textInputLayoutConfirmPassword, getString(R.string.error_password_match))) {
            return false;
        }

        if (!databaseHelper.checkUser(textInputEditTextEmail.getText().toString().trim())) {

            user.setName(textInputEditTextName.getText().toString().trim());
            user.setEmail(textInputEditTextEmail.getText().toString().trim());
            user.setPassword(textInputEditTextPassword.getText().toString().trim());

            databaseHelper.addUser(user);

            // Snack Bar to show success message that record saved successfully
            Toast.makeText(getApplicationContext(), getString(R.string.success_message),LENGTH_LONG).show();
            emptyInputEditText();
            return true;


        } else {
            // Snack Bar to show error message that record already exists
            Toast.makeText(getApplicationContext(), getString(R.string.error_email_exists),LENGTH_LONG).show();
        }


        return false;
    }

    /**
     * This method is to empty all input edit text
     */
    private void emptyInputEditText() {
        textInputEditTextName.setText(null);
        textInputEditTextEmail.setText(null);
        textInputEditTextPassword.setText(null);
        textInputEditTextConfirmPassword.setText(null);
    }
}
