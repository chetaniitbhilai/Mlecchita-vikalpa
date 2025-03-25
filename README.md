# PocketMoney App

## Overview
PocketMoney is a daily allowance-based payment application that utilizes **Trust Tokens** for allowance setting. The app deducts payments from a predefined daily allowance, logs transactions, and prevents overspending beyond the allocated limit.

## Features
- **Daily Allowance Management**: Users can spend within a set allowance.
- **Parent Allowance Control**: Parents can set the allowance limit as it only sets if the trusttoken is connected.
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
}
```

## Implementation Steps
1. Clone the repository:
   ```sh
   git clone https://github.com/your-repo/PocketMoneyApp.git
   ```
2. Open the project in **Android Studio**.
3. Use Cmake 3.30.5
4. Build and run the application on an emulator or a physical device.
5. Use the app to:
   - Set a daily allowance.
   - Make payments using Trust Tokens.
   - View transaction history.
6. Ensure that transactions exceeding the allowance are blocked.

## Future Enhancements
- Implement Firebase for cloud-based transactions.
- If the limit exceeds we can use trusttoken to make the payment.
- If a certain threshold is reached for one payment trustoken will send a cipher text, if the reciving person is able to decrypt only then the payment succeeds.
- Add user authentication and profile management.
- Enable spending reports and analytics.

## Contribution
Feel free to submit pull requests to improve the application.

---

### Contact
For queries or contributions, reach out at **chetan@iitbhilai.ac.in**, **kirtiraj@iitbhilai.ac.in**, **bhavyaj@iitbhilai.ac.in**, **kritia@iitbhilai.ac.in**.

