package com.example.a51c;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SignupFragment extends Fragment {

    public SignupFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_signup, container, false);

        EditText fullNameEditText = view.findViewById(R.id.signupFullNameEditText);
        EditText usernameEditText = view.findViewById(R.id.signupUsernameEditText);
        EditText passwordEditText = view.findViewById(R.id.signupPasswordEditText);
        EditText confirmPasswordEditText = view.findViewById(R.id.signupConfirmPasswordEditText);
        Button createAccountButton = view.findViewById(R.id.createAccountButton);

        AppDatabase db = AppDatabase.getInstance(requireContext());

        createAccountButton.setOnClickListener(v -> {
            String fullName = fullNameEditText.getText().toString();
            String username = usernameEditText.getText().toString();
            String password = passwordEditText.getText().toString();
            String confirmPassword = confirmPasswordEditText.getText().toString();

            if (fullName.isEmpty() || username.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
                Toast.makeText(requireContext(), "Please fill all fields", Toast.LENGTH_SHORT).show();
                return;
            }

            if (!password.equals(confirmPassword)) {
                Toast.makeText(requireContext(), "Passwords do not match", Toast.LENGTH_SHORT).show();
                return;
            }

            User existingUser = db.userDao().findByUsername(username);

            if (existingUser != null) {
                Toast.makeText(requireContext(), "Username already exists", Toast.LENGTH_SHORT).show();
                return;
            }

            User user = new User();
            user.fullName = fullName;
            user.username = username;
            user.password = password;

            db.userDao().insertUser(user);

            Toast.makeText(requireContext(), "Account created", Toast.LENGTH_SHORT).show();

            requireActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .replace(android.R.id.content, new LoginFragment())
                    .commit();
        });

        return view;
    }
}