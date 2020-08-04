package com.example.loginapp;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.loginapp.model.User;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RegisterFragment#} factory method to
 * create an instance of this fragment.
 */
public class RegisterFragment extends Fragment {

    Button btn_register;

    public RegisterFragment() {
        // Required empty public constructor
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        final EditText nameView = view.findViewById(R.id.et_name);
        final EditText emailView = view.findViewById(R.id.et_email);
        final EditText passwordView = view.findViewById(R.id.et_password);
        final EditText reEnterpasswordView = view.findViewById(R.id.et_repassword);
        btn_register = (Button) view.findViewById(R.id.btn_register);
        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                User newUser = new User();
                newUser.createUser(nameView.getText().toString(), emailView.getText().toString(), passwordView.getText().toString());
                if ("".equals(newUser.getErrorMessage())) {
                    Log.d("REGISTRATION VIEW", "Registration successful.");
                    openhomepage(view);
                } else {
                    Log.e("LOGIN VIEW", newUser.getErrorMessage());
                }
            }
        });


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_register, container, false);
    }

    public void openhomepage(View view) {
        Intent intent = new Intent(view.getContext(), Homepage.class);
        startActivity(intent);
    }
}