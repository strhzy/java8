package com.example.test14062024;

import android.os.Bundle;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private Button[] numberButtons = new Button[10];
    private Button Minus, Plus, Division, Multiply, Result, Sqrt, Sqr, Percent, Clear;
    private TextView Formula, EndResult;
    private char Act;
    private double Solution = Double.NaN;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        setupView();

        setNumberButtonsClickListener();
        setActionButtonsClickListener();
        setResultButtonClickListener();
        setSpecialButtonsClickListener();
        setClearButtonClickListener();
    }

    private void setupView() {
        numberButtons[0] = findViewById(R.id.Zero);
        numberButtons[1] = findViewById(R.id.One);
        numberButtons[2] = findViewById(R.id.Two);
        numberButtons[3] = findViewById(R.id.Three);
        numberButtons[4] = findViewById(R.id.Four);
        numberButtons[5] = findViewById(R.id.Five);
        numberButtons[6] = findViewById(R.id.Six);
        numberButtons[7] = findViewById(R.id.Seven);
        numberButtons[8] = findViewById(R.id.Eight);
        numberButtons[9] = findViewById(R.id.Nine);

        Minus = findViewById(R.id.Minus);
        Plus = findViewById(R.id.Plus);
        Result = findViewById(R.id.Result);
        Division = findViewById(R.id.Division);
        Multiply = findViewById(R.id.Multiply);
        Sqrt = findViewById(R.id.Sqrt);
        Sqr = findViewById(R.id.Sqr);
        Percent = findViewById(R.id.Percent);
        Clear = findViewById(R.id.Clear);

        EndResult = findViewById(R.id.EndResult);
        Formula = findViewById(R.id.Formula);
    }

    private void setNumberButtonsClickListener() {
        View.OnClickListener numberClickListener = view -> {
            Button button = (Button) view;
            Formula.append(button.getText().toString());
        };

        for (Button button : numberButtons) {
            button.setOnClickListener(numberClickListener);
        }
    }

    private void setActionButtonsClickListener() {
        View.OnClickListener actionClickListener = view -> {
            Button button = (Button) view;
            if (isValidFormula()) {
                calculate();
                Act = button.getText().charAt(0);
                Formula.setText(String.valueOf(Solution) + Act);
                EndResult.setText(null);
            }
        };

        Plus.setOnClickListener(actionClickListener);
        Minus.setOnClickListener(actionClickListener);
        Division.setOnClickListener(actionClickListener);
        Multiply.setOnClickListener(actionClickListener);
        Percent.setOnClickListener(actionClickListener);
    }

    private void setResultButtonClickListener() {
        Result.setOnClickListener(v -> {
            if (isValidFormula()) {
                calculate();
                Act = '=';
                EndResult.setText(String.valueOf(Solution));
                Formula.setText(null);
            }
        });
    }

    private void setSpecialButtonsClickListener() {
        View.OnClickListener specialClickListener = view -> {
            Button button = (Button) view;
            if (isValidFormula()) {
                calculate();
                Act = button.getText().charAt(0);
                Formula.setText(String.valueOf(Solution) + Act);
                specialCalculate();
                EndResult.setText(String.valueOf(Solution));
                Formula.setText(null);
            }
        };

        Sqrt.setOnClickListener(specialClickListener);
        Sqr.setOnClickListener(specialClickListener);
    }

    private void setClearButtonClickListener() {
        Clear.setOnClickListener(v -> {
            Formula.setText("");
            Solution = Double.NaN;
            EndResult.setText("0");
        });
    }

    private boolean isValidFormula() {
        String formulaText = Formula.getText().toString();
        return !formulaText.isEmpty() && Character.isDigit(formulaText.charAt(formulaText.length() - 1));
    }

    private void calculate() {
        if (!Double.isNaN(Solution)) {
            String textFormula = Formula.getText().toString();
            int index = textFormula.indexOf(Act);
            if (index != -1) {
                String numberValue = textFormula.substring(index + 1);
                double value = Double.parseDouble(numberValue);
                switch (Act) {
                    case '/':
                        Solution = (value == 0) ? 0.0 : Solution / value;
                        break;
                    case '*':
                        Solution *= value;
                        break;
                    case '+':
                        Solution += value;
                        break;
                    case '-':
                        Solution -= value;
                        break;
                    case '%':
                        Solution = Solution / 100 * value;
                        break;
                    case '=':
                        Solution = value;
                        break;
                }
            }
        } else {
            try {
                Solution = Double.parseDouble(Formula.getText().toString());
            } catch (NumberFormatException e) {
                Solution = 0.0;
            }
        }
        EndResult.setText(String.valueOf(Solution));
    }

    private void specialCalculate() {
        if (!Double.isNaN(Solution) && Solution != 0.0) {
            switch (Act) {
                case '√':
                    Solution = Math.sqrt(Solution);
                    break;
                case '²':
                    Solution = Math.pow(Solution, 2);
                    break;
            }
        } else {
            try {
                Solution = Double.parseDouble(Formula.getText().toString());
            } catch (NumberFormatException e) {
                Solution = 0.0;
            }
        }
    }
}
