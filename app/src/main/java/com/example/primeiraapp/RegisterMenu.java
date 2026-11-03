package com.example.primeiraapp;

import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.primeiraapp.data.FirebaseAuthRepository;
import com.example.primeiraapp.data.FirebaseUserRepository;
import com.example.primeiraapp.domain.RegisterUserUseCase;

public class RegisterMenu extends AppCompatActivity {

    private static final String TAG = "RegisterMenu";
    private EditText nameInput;
    private Spinner spinner;
    private Button saveButton;
    private final RegisterUserUseCase registerUserUseCase = new RegisterUserUseCase(
            new FirebaseAuthRepository(),
            new FirebaseUserRepository()
    );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_menu);

        nameInput = findViewById(R.id.name);
        spinner = findViewById(R.id.spinner);
        saveButton = findViewById(R.id.btnSaveRegistration);
        String[] opcoes = getResources().getStringArray(R.array.opcoes_spinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, opcoes);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        saveButton.setOnClickListener(view -> saveProfile());
    }

    private void saveProfile() {
        saveButton.setEnabled(false);
        saveButton.setText(R.string.register_saving);

        String name = nameInput.getText().toString();
        String availability = spinner.getSelectedItem() == null
                ? ""
                : spinner.getSelectedItem().toString();

        registerUserUseCase.saveProfile(name, availability)
                .addOnSuccessListener(unused -> {
                    Toast.makeText(this, R.string.register_success, Toast.LENGTH_SHORT).show();
                    openHomePage();
                })
                .addOnFailureListener(error -> {
                    saveButton.setEnabled(true);
                    saveButton.setText(R.string.register_continue);
                    Log.e(TAG, "Erro ao guardar perfil", error);
                    Toast.makeText(this, R.string.register_error, Toast.LENGTH_LONG).show();
                });
    }

    private void openHomePage() {
        android.content.Intent intent = new android.content.Intent(this, HomePage.class);
        intent.addFlags(android.content.Intent.FLAG_ACTIVITY_NEW_TASK
                | android.content.Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }
}
