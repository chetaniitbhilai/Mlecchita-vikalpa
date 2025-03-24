package com.example.trustoken_starter;

import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.List;

public class TransactionHistoryActivity extends AppCompatActivity {
    private TextView tvHistory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction_history);

        tvHistory = findViewById(R.id.history_text);

        List<String> transactions = getIntent().getStringArrayListExtra("transactions");

        StringBuilder historyText = new StringBuilder("Transaction History:\n");
        if (transactions != null && !transactions.isEmpty()) {
            for (String transaction : transactions) {
                historyText.append(transaction).append("\n");
            }
        } else {
            historyText.append("No transactions yet.");
        }

        tvHistory.setText(historyText.toString());
    }
}
