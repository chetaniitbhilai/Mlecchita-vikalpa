# PocketMoney App

## Overview
PocketMoney is a daily allowance-based payment application that utilizes **Trust Tokens** for transactions. The app deducts payments from a predefined daily allowance, logs transactions, and prevents overspending beyond the allocated limit.

## Features
- **Trust Token-based Payments**: Transactions are processed using Trust Tokens.
- **Daily Allowance Management**: Users can spend within a set allowance.
- **JSON-based Transactions**: Payments are recorded in JSON format with item details and price.
- **Transaction History**: Every transaction is logged for user reference.
- **Spending Limit Enforcement**: Payments exceeding the daily allowance are rejected.
- **QR Code Scanner**: Allows payments via QR scanning, extracting item and price details from JSON.

## File Structure
```
PocketMoneyApp/
│── androidTest/               # Android test files
│── main/
│   ├── cpp/                   # Native C++ Code (if any)
│   ├── java/
│   │   ├── com.example.trusttoken_starter/
│   │   │   ├── ui.theme/       # Theme-related Kotlin files
│   │   │   │   ├── Color.kt
│   │   │   │   ├── Theme.kt
│   │   │   │   ├── Type.kt
│   │   │   ├── MainActivity.kt # Main Screen Logic
│   │   │   ├── QRScannerActivity.kt # Handles QR Payments
│   │   │   ├── TransactionHistoryActivity.kt # Transaction Logs
│   │   │   ├── TrusToken.kt    # Trust Token Handling
│   ├── res/
│   │   ├── drawable/           # App Icons & Images
│   │   ├── layout/             # UI Layout XMLs
│   │   │   ├── activity_main.xml
│   │   │   ├── activity_qr.xml
│   │   │   ├── activity_transaction_history.xml
│   │   │   ├── activity_trus_token.xml
│   │   ├── values/             # Resource Values
│   │   │   ├── colors.xml
│   │   │   ├── strings.xml
│   │   │   ├── themes.xml
│   │   ├── xml/                # Backup & Data Rules
│   │   │   ├── backup_rules.xml
│   │   │   ├── data_extraction_rules.xml
│   ├── AndroidManifest.xml     # Manifest File
│── test/                       # Unit Tests
```

## Functionality Breakdown
### 1. MainActivity.java
- Initializes the app
- Displays user balance
- Handles navigation to transaction history and QR scanner


### 2. TransactionHistoryActivity.java
- Fetches transaction records
- Displays a list of transactions

### 3. QRScannerActivity.java
- Scans QR codes for payment processing
- Extracts item and price details from JSON
- Example JSON scanned:
```json
{
    "thing": "Chocolate",
    "price": 20.0
}
```

### 5. activity_main.xml
- UI layout for the main dashboard
- Shows daily allowance and balance

### 6. activity_transaction_history.xml
- Displays transaction history

## JSON Transaction Format
```json
{
    "thing": "Coffee",
    "price": 50.0,
    "timestamp": "2025-03-25T10:30:00Z"
}
```

## Implementation Steps
1. Clone the repository:
   ```sh
   git clone https://github.com/your-repo/PocketMoneyApp.git
   ```
2. Open the project in **Android Studio**.
3. Build and run the application on an emulator or a physical device.
4. Use the app to:
   - Set a daily allowance.
   - Make payments using Trust Tokens.
   - View transaction history.
5. Ensure that transactions exceeding the allowance are blocked.

## Future Enhancements
- Implement Firebase for cloud-based transactions.
- Add user authentication and profile management.
- Enable spending reports and analytics.

## Contribution
Feel free to submit pull requests to improve the application.

---

### Contact
For queries or contributions, reach out at **your-email@example.com**.

