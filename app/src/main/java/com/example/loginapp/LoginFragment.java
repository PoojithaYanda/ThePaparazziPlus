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
import android.widget.TextView;

import com.example.loginapp.model.User;

public class LoginFragment extends Fragment {

    private Button login_button , register_button;

    public LoginFragment() {
        // Required empty public constructor
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        final EditText emailView = view.findViewById(R.id.et_email);
        final EditText passwordView = view.findViewById(R.id.et_password);
        login_button = (Button) view.findViewById(R.id.btn_login);
        login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (emailView == null || passwordView == null) {
                    Log.e("LOGIN VIEW", "Unable to find views.");
                    return;
                }
                User newUser = new User();
                newUser.validateUser(emailView.getText().toString(), passwordView.getText().toString());
                if ("".equals(newUser.getErrorMessage())) {
                    AppState.setUserInstance(newUser);
                    Log.d("LOGIN VIEW", "Login successful.");
                    openhomepage(view);
                } else {
                    Log.e("LOGIN VIEW", newUser.getErrorMessage());
                }
            }
        });

        register_button = (Button) view.findViewById(R.id.button2);
        register_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    getFragmentManager()
                            .beginTransaction()
                            .replace(R.id.main_activity, new RegisterFragment())
                            .commit();
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login,container,false);
    }

    public void openhomepage(View view)
    {
        Intent intent = new Intent(view.getContext(), Homepage.class);
        startActivity(intent);
    }

    public void openregister(View view)
    {
        Intent intent = new Intent(view.getContext(), RegisterFragment.class);
        startActivity(intent);
    }
}