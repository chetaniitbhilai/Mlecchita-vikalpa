package com.example.trustoken_starter;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;

public class TransactionHistoryActivity extends AppCompatActivity {
    private ListView listView;
    private ArrayList<String> transactions;
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction_history);

        listView = findViewById(R.id.transaction_list);
        transactions = new ArrayList<>();
        transactions.add("Bought Item A - ₹50");
        transactions.add("Bought Item B - ₹30");

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, transactions);
        listView.setAdapter(adapter);
    }
}
