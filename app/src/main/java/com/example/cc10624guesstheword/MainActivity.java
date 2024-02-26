package com.example.cc10624guesstheword;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.util.Random;


public class MainActivity extends AppCompatActivity {

    private String[] words;
    private String[] hints;
    private String selectedWord;
    private TextView hintTextView;
    private TextView blanksTextView;
    private EditText letterInputEditText;
    private EditText finalAnswerEditText;
    private Button submitLetterButton;
    private Button submitFinalButton;
    private TextView attemptsTextView;
    private String hint;

    private Button resetbutton;

    private int attemptsLeft = 5;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        words = getResources().getStringArray(R.array.words_array);
        hints = getResources().getStringArray(R.array.hint_array);

        hintTextView = findViewById(R.id.textViewHint);
        blanksTextView = findViewById(R.id.textViewBlanks);
        letterInputEditText = findViewById(R.id.editTextLetterInput);
        finalAnswerEditText = findViewById(R.id.editTextFinalAnswer);
        submitLetterButton = findViewById(R.id.buttonSubmitLetter);
        submitFinalButton = findViewById(R.id.buttonSubmitFinal);
        attemptsTextView = findViewById(R.id.textViewAttempts);
        resetbutton = findViewById(R.id.btnreset);


        submitLetterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkLetterGuess();
            }
        });


        resetbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetGame();
            }
        });

        submitFinalButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkFinalGuess();
            }
        });

        selectNewWord();
        updateUI();
    }


    private void selectNewWord() {
        Random random = new Random();
        int index = random.nextInt(words.length);
        selectedWord = words[index];
        hint = hints[index];

        hintTextView.setText("Hint: " + hint);

        // Set up blanks for the word
        String blanks = "";
        for (int i = 0; i < selectedWord.length(); i++) {
            blanks += "_ ";
        }
        blanksTextView.setText(blanks);
    }


    private void checkLetterGuess() {
        String guess = letterInputEditText.getText().toString().toUpperCase();

        // Check if guess is blank
        if (guess.trim().isEmpty()) {
            // Show an error message, toast is one way to do it
            Toast.makeText(this, "Please enter a letter", Toast.LENGTH_SHORT).show();
            return;
        }

        // Clear the input field after getting the guess
        letterInputEditText.setText("");

        // Check if the guessed letter is in the selected word
        boolean letterFound = false;
        StringBuilder updatedBlanks = new StringBuilder(blanksTextView.getText().toString());

        for (int i = 0; i < selectedWord.length(); i++) {
            char letterInWord = selectedWord.charAt(i);
            if (guess.charAt(0) == letterInWord) {
                updatedBlanks.setCharAt(i * 2, letterInWord); // Update the blanksTextView
                letterFound = true;
            }
        }

        if (!letterFound) {
            // Decrement attemptsLeft if the guessed letter is incorrect
            attemptsLeft--;
        }

        blanksTextView.setText(updatedBlanks);

        updateUI();
    }



    private void checkFinalGuess() {
        String guess = finalAnswerEditText.getText().toString().toUpperCase();
        if (guess.equals(selectedWord)) {
            Toast.makeText(this, "You guessed correctly!", Toast.LENGTH_SHORT).show();
            resetGame();
            // Handle correct guess
        } else {
            Toast.makeText(this, "Sorry! Your answer is not correct.", Toast.LENGTH_SHORT).show();
            // Handle incorrect guess
        }
    }

    private void updateUI() {
        // Update remaining attempts
        attemptsTextView.setText("Attempts Left: " + attemptsLeft);

        // Check if attempts are exhausted
        if (attemptsLeft <= 0) {
            submitLetterButton.setEnabled(false);
            return;
        }

        // Check if word is guessed
        if (blanksTextView.getText().toString().replace(" ", "").equals(selectedWord)) {
            // Handle word guessed
            return;
        }

        // Enable or disable buttons based on attempts left
        if (attemptsLeft <= 1) {
            return;
        }

        // Update other UI elements as needed
    }

    private void resetGame() {
        attemptsLeft = 5;
        letterInputEditText.setText("");
        finalAnswerEditText.setText("");
        selectNewWord();
        updateUI();
        submitLetterButton.setEnabled(true);
    }
}

