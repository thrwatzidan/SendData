package com.example.android.navigation;

import android.content.ClipData;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.EditText;

public class EmailListActivity extends AppCompatActivity {
private EditText etName,etEmail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email_list);
getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_check);

        Intent intent=getIntent();
        String name = intent.getStringExtra(MainActivity.NAME_KEY);
        etName = (EditText) findViewById(R.id.name_text);
        etEmail = (EditText) findViewById(R.id.email_address);

        etName.setText(name);
    }

    @Override
    public void onBackPressed() {
        String nameText =etName.getText().toString();
        String emailText =etEmail.getText().toString();
        Intent intent = new Intent();
        intent.putExtra(MainActivity.NAME_KEY, nameText);
        intent.putExtra(MainActivity.EMAIL_KEY, emailText);
        setResult(RESULT_OK,intent);
//        super.onBackPressed();
        finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
