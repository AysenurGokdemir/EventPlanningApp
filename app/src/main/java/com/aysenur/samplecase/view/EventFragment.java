package com.aysenur.samplecase.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.aysenur.samplecase.R;
import com.aysenur.samplecase.db.model.Event;
import com.aysenur.samplecase.viewmodel.EventViewModel;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

public class EventFragment extends Fragment implements View.OnClickListener {

    public static final String EXTRA_TITLE ="EXTRA_TITLE";
    public static final String EXTRA_DESC ="EXTRA_DESC";
    private EventViewModel eViewModel;
    private EditText jobTitle;
    private EditText jobDesc;
    private Button save;
    private Button cancel;

    public static EventFragment newInstance(){return new EventFragment();}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_add_event,container, false);
        jobTitle=view.findViewById(R.id.edt_title);
        jobDesc=view.findViewById(R.id.edt_desc);
        save=view.findViewById(R.id.add);
        cancel=view.findViewById(R.id.cancel);
        save.setOnClickListener(this);
        cancel.setOnClickListener(this);


        return view;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        eViewModel= ViewModelProviders.of(this).get(EventViewModel.class);
        ViewModelProviders.of(getActivity()).get(EventViewModel.class).getMessage().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                if (s!=null) {
                    jobTitle.setText(s);
                    Toast.makeText(getContext(),s, Toast.LENGTH_SHORT).show();
                }
                else{
                    startActivity(new Intent(getActivity(),ExpTestMain.class));
                }
            }
        });

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.add:
                saveEvent();
                break;
            case R.id.cancel:
                openDialog();

                break;

            default:
        }

    }

    private void saveEvent(){
        String title=jobTitle.getText().toString();
        String desc=jobDesc.getText().toString();
        eViewModel = ViewModelProviders.of(this).get(EventViewModel.class);
        if (title.trim().isEmpty() || desc.trim().isEmpty()) {
            Toast.makeText(getActivity(), "please insert a title and description", Toast.LENGTH_SHORT).show();
            return;
        }else{
            Event event = new Event(title, desc);
            eViewModel.insert(event);
            Toast.makeText(getActivity(), "Form Saved", Toast.LENGTH_SHORT).show();
            Intent i=new Intent(getActivity(),ExpTestMain.class);
            startActivity(i);
        }
    }


        public void openDialog(){
        Dialog dialog= new Dialog();
        dialog.show(getFragmentManager(),"Dialog");
        }
}
