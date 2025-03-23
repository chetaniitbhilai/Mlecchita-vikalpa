package com.example.trustoken_starter;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import org.json.JSONObject;
import com.journeyapps.barcodescanner.CaptureActivity;

public class MainActivity extends AppCompatActivity {
    private Button btnSetAllowance, btnScanQR, btnViewHistory;
    private TextView tvRemainingAllowance;
    private double dailyAllowance = 0;
    private double remainingAllowance = 0;
    private TrustokenManager trustokenManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        trustokenManager = new TrustokenManager(this);
        btnSetAllowance = findViewById(R.id.set_allowance);
        btnScanQR = findViewById(R.id.scan_qr);
        btnViewHistory = findViewById(R.id.view_history);
        tvRemainingAllowance = findViewById(R.id.remaining_allowance);

        btnSetAllowance.setOnClickListener(v -> {
            if (trustokenManager.isTokenConnected()) {
                dailyAllowance = 100.0; // Example
                remainingAllowance = dailyAllowance;
                tvRemainingAllowance.setText("Remaining: " + remainingAllowance);
                Toast.makeText(this, "Allowance Set", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Trustoken Required", Toast.LENGTH_SHORT).show();
            }
        });

        btnScanQR.setOnClickListener(v -> {
            Intent intent = new Intent(this, QRScannerActivity.class);
            startActivityForResult(intent, 1);
        });

        btnViewHistory.setOnClickListener(v -> {
            Intent intent = new Intent(this, TransactionHistoryActivity.class);
            startActivity(intent);
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK) {
            String qrData = data.getStringExtra("QR_RESULT");
            try {
                JSONObject json = new JSONObject(qrData);
                String item = json.getString("item");
                double price = json.getDouble("price");

                if (price <= remainingAllowance) {
                    remainingAllowance -= price;
                    tvRemainingAllowance.setText("Remaining: " + remainingAllowance);
                    Toast.makeText(this, "Purchased: " + item, Toast.LENGTH_SHORT).show();
                } else if (trustokenManager.isTokenConnected()) {
                    remainingAllowance = 0;
                    tvRemainingAllowance.setText("Remaining: " + remainingAllowance);
                    Toast.makeText(this, "Purchased with Trustoken: " + item, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "Exceeds Limit! Trustoken Required", Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
