package com.rku.tutorial08;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

public class RegistrationActivity extends AppCompatActivity {

    DatabaseHelper databaseHelper;
    EditText fName,lName,email,password;
    Switch switchbranch;
    RadioGroup radioGroup;
    RadioButton radioSex;
    Spinner spinnerCity;
    CheckBox checkBox;
    Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        databaseHelper=new DatabaseHelper(this);
        button = (Button) findViewById(R.id.btnRegister);

        fName=(EditText) findViewById(R.id.editFname);
        lName=(EditText)findViewById(R.id.editLastname);
        email =(EditText)findViewById(R.id.editEmail);
        password=(EditText)findViewById(R.id.editPassword);
        switchbranch=(Switch) findViewById(R.id.branchSwitch);
        radioGroup=(RadioGroup) findViewById(R.id.radioGroup);
        spinnerCity=(Spinner) findViewById(R.id.spinnerCity);
        checkBox=(CheckBox) findViewById(R.id.checkBox);

    }

    public void registerUser(View view) {

        String first_name=fName.getText().toString();
        String last_name=lName.getText().toString();
        String email_id=email.getText().toString();
        String Password=password.getText().toString();

        String branchStatus;
        String radio;
        int selectedId;
        String City;
        String check;

        if(first_name.isEmpty()){
            fName.setError("Enter First Name");
            fName.requestFocus();
            return;
        }

        if(last_name.isEmpty()){
            lName.setError("Enter Last Name");
            lName.requestFocus();
            return;
        }

        if(email_id.isEmpty()){
            email.setError("Enter Email Id");
            email.requestFocus();
            return;
        }
        else if(!Patterns.EMAIL_ADDRESS.matcher(email_id).matches()){
            email.setError("Enter valid email id");
            email.requestFocus();
            return;
        }

        if(Password.isEmpty()){
            password.setError("Enter Password");
            password.requestFocus();
            return;
        }

        if(switchbranch.isChecked()){
            branchStatus="CE";
        }
        else{
            branchStatus="IT";
        }

        City=spinnerCity.getSelectedItem().toString();
        if(City.equals("Select City")){
            Toast.makeText(RegistrationActivity.this,"Please Select City",Toast.LENGTH_LONG).show();
            return;
        }

        if(checkBox.isChecked()){
            check="Active";
        }
        else{
            check="Inactive";
        }

        selectedId = radioGroup.getCheckedRadioButtonId();
        radioSex=findViewById(selectedId);
        radio=radioSex.getText().toString();

        if (!databaseHelper.isUsernameTaken(email_id)) {
            if (databaseHelper.insertData(first_name, last_name, email_id, Password, branchStatus, radio, City, check)) {
                Toast.makeText(this, "Account Created Successfully", Toast.LENGTH_SHORT).show();
                //Intent intent = new Intent(this, LoginActivity.class); // redirecting to LoginActivity.
                startActivity(new Intent(this, LoginActivity.class));
                finish();
            } else {
                Toast.makeText(this, "Unable To Create Account", Toast.LENGTH_SHORT).show();
                email.requestFocus();
            }
        }else {
            Toast.makeText(this, "Username is Already Taken", Toast.LENGTH_SHORT).show();
        }
    }
}