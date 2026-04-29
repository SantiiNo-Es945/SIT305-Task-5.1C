package com.example.a51c;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginFragment extends Fragment {

    public LoginFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_login, container, false);

        EditText usernameEditText = view.findViewById(R.id.loginUsernameEditText);
        EditText passwordEditText = view.findViewById(R.id.loginPasswordEditText);
        Button loginButton = view.findViewById(R.id.loginButton);
        Button goToSignupButton = view.findViewById(R.id.goToSignupButton);

        AppDatabase db = AppDatabase.getInstance(requireContext());

        loginButton.setOnClickListener(v -> {
            String username = usernameEditText.getText().toString();
            String password = passwordEditText.getText().toString();

            User user = db.userDao().login(username, password);

            if (user != null) {
                SessionManager.currentUserId = user.id;

                Toast.makeText(requireContext(), "Login successful", Toast.LENGTH_SHORT).show();

                requireActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(android.R.id.content, new HomeFragment())
                        .commit();
            } else {
                Toast.makeText(requireContext(), "Invalid credentials", Toast.LENGTH_SHORT).show();
            }
        });

        goToSignupButton.setOnClickListener(v -> {
            requireActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .replace(android.R.id.content, new SignupFragment())
                    .addToBackStack(null)
                    .commit();
        });

        return view;
    }
}