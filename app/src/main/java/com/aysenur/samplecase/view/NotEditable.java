package com.aysenur.samplecase.view;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.widget.EditText;

import com.aysenur.samplecase.R;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class NotEditable extends AppCompatActivity {

    EditText edt_title;
    EditText edt_desc;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_not_editable_form);
        edt_title=findViewById(R.id.edt_title);
        edt_desc=findViewById(R.id.edt_desc);
        Intent data = getIntent();
        String title = data.getStringExtra(EventFragment.EXTRA_TITLE);
        String desc = data.getStringExtra(EventFragment.EXTRA_DESC);

        edt_title.setText(title);
        edt_desc.setText(desc);

        edt_title.setInputType(InputType.TYPE_NULL);
        edt_desc.setInputType(InputType.TYPE_NULL);
    }
}
