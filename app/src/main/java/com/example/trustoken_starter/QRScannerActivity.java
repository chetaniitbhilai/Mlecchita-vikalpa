package com.example.trustoken_starter;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.journeyapps.barcodescanner.BarcodeCallback;
import com.journeyapps.barcodescanner.BarcodeResult;
import com.journeyapps.barcodescanner.BarcodeView;

public class QRScannerActivity extends AppCompatActivity {

    private BarcodeView barcodeView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qr); // Ensure the XML file name is correct!

        barcodeView = findViewById(R.id.barcode_scanner);
        barcodeView.decodeContinuous(callback); // Start scanning
    }

    private final BarcodeCallback callback = new BarcodeCallback() {
        @Override
        public void barcodeResult(BarcodeResult result) {
            if (result.getText() != null) {
                barcodeView.pause();
                Toast.makeText(QRScannerActivity.this, "Scanned: " + result.getText(), Toast.LENGTH_SHORT).show();

                // Send the scanned data back to MainActivity
                Intent resultIntent = new Intent();
                resultIntent.putExtra("qrData", result.getText());
                setResult(RESULT_OK, resultIntent);
                finish();
            }
        }
    };

    @Override
    protected void onResume() {
        super.onResume();
        if (barcodeView != null) barcodeView.resume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (barcodeView != null) barcodeView.pause();
    }
}
