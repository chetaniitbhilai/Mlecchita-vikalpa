package com.example.trustoken_starter;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbManager;
import android.os.Build;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private Button btnSetAllowance, btnScanQR, btnViewHistory, btnDetectToken;
    private TextView tvRemainingAllowance, tvTokenName;
    private double dailyAllowance = 1000;
    private double remainingAllowance = 1000;
    private boolean isTokenConnected = false;
    private static final String ACTION_USB_PERMISSION = "com.example.USB_PERMISSION";
    private List<String> transactionHistory = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvTokenName = findViewById(R.id.token_name);
        btnSetAllowance = findViewById(R.id.set_allowance);
        btnScanQR = findViewById(R.id.scan_qr);
        btnViewHistory = findViewById(R.id.view_history);
        btnDetectToken = findViewById(R.id.detect_token);
        tvRemainingAllowance = findViewById(R.id.remaining_allowance);

        updateRemainingAllowanceText();

        btnDetectToken.setOnClickListener(v -> detectSmartCard());

        btnSetAllowance.setOnClickListener(v -> {
            if (isTokenConnected) {
                dailyAllowance = 1000.0;
                remainingAllowance = dailyAllowance;
                updateRemainingAllowanceText();
                Toast.makeText(this, "Allowance Set", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Trustoken Required", Toast.LENGTH_SHORT).show();
            }
        });

        btnScanQR.setOnClickListener(v -> {
            Intent intent = new Intent(this, QRScannerActivity.class);
            startActivity(intent);
        });

        btnViewHistory.setOnClickListener(v -> {
            Intent intent = new Intent(this, TransactionHistoryActivity.class);
            intent.putStringArrayListExtra("transactions", new ArrayList<>(transactionHistory));
            startActivity(intent);
        });
    }

    private void detectSmartCard() {
        UsbManager usbManager = (UsbManager) getSystemService(Context.USB_SERVICE);
        if (usbManager == null) return;

        HashMap<String, UsbDevice> deviceList = usbManager.getDeviceList();
        for (UsbDevice device : deviceList.values()) {
            if (isSmartCardReader(device)) {
                int flag = (Build.VERSION.SDK_INT >= 33) ? PendingIntent.FLAG_IMMUTABLE : 0;
                PendingIntent permissionIntent = PendingIntent.getBroadcast(this, 0, new Intent(ACTION_USB_PERMISSION), flag);
                usbManager.requestPermission(device, permissionIntent);

                if (usbManager.hasPermission(device)) {
                    isTokenConnected = true;
                    tvTokenName.setText("Trustoken");
                    Toast.makeText(this, "Trustoken Connected", Toast.LENGTH_SHORT).show();
                    return;
                }
            }
        }

        isTokenConnected = false;
        Toast.makeText(this, "Trustoken Not Found", Toast.LENGTH_SHORT).show();
    }

    private boolean isSmartCardReader(UsbDevice device) {
        return device.getVendorId() == 10381 && device.getProductId() == 64;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK && data != null) {
            String qrData = data.getStringExtra("qrData");
            processTransaction(qrData);
        }
    }

    private void processTransaction(String qrData) {
        try {
            JSONObject jsonObject = new JSONObject(qrData);
            String item = jsonObject.getString("thing");
            double price = jsonObject.getDouble("price");

            if (remainingAllowance - price < 0) {
                Toast.makeText(this, "Can't proceed, insufficient allowance!", Toast.LENGTH_SHORT).show();
            } else {
                remainingAllowance -= price;
                transactionHistory.add(item + " - ₹" + price);
                updateRemainingAllowanceText();
                Toast.makeText(this, "Transaction Successful!", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            Toast.makeText(this, "Invalid QR Data", Toast.LENGTH_SHORT).show();
        }
    }

    private void updateRemainingAllowanceText() {
        tvRemainingAllowance.setText("Remaining: ₹" + remainingAllowance);
    }
}
