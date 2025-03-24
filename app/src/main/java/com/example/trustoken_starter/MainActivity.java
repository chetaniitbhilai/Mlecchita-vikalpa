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
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {
    private Button btnSetAllowance, btnScanQR, btnViewHistory, btnDetectToken;
    private TextView tvRemainingAllowance, tvTokenName;
    private double dailyAllowance = 0;
    private double remainingAllowance = 0;
    private boolean isTokenConnected = false;
    private static final String ACTION_USB_PERMISSION = "com.example.USB_PERMISSION";

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

        btnDetectToken.setOnClickListener(v -> {
            int fd = detectSmartCard();
            if (fd != -1) {
                isTokenConnected = true;
                Toast.makeText(this, "Trustoken Connected", Toast.LENGTH_SHORT).show();
            } else {
                isTokenConnected = false;
                Toast.makeText(this, "Trustoken Not Found", Toast.LENGTH_SHORT).show();
            }
        });

        btnSetAllowance.setOnClickListener(v -> {
            if (isTokenConnected) {
                dailyAllowance = 100.0;
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

    private int detectSmartCard() {
        UsbManager usbManager = (UsbManager) getSystemService(Context.USB_SERVICE);
        if (usbManager == null) return -1;

        HashMap<String, UsbDevice> deviceList = usbManager.getDeviceList();
        for (UsbDevice device : deviceList.values()) {
            if (isSmartCardReader(device)) {
                int flag = (Build.VERSION.SDK_INT >= 33) ? PendingIntent.FLAG_IMMUTABLE : 0;
                PendingIntent permissionIntent = PendingIntent.getBroadcast(this, 0, new Intent(ACTION_USB_PERMISSION), flag);
                usbManager.requestPermission(device, permissionIntent);

                if (usbManager.hasPermission(device)) {
                    return getFileDescriptor(usbManager, device);
                }
            }
        }
        return -1;
    }

    private boolean isSmartCardReader(UsbDevice device) {
        if (device.getVendorId() == 10381 && device.getProductId() == 64) {
            tvTokenName.setText("Trustoken");
            return true;
        }
        return false;
    }

    private int getFileDescriptor(UsbManager manager, UsbDevice device) {
        return manager.openDevice(device) != null ? manager.openDevice(device).getFileDescriptor() : -1;
    }
}
