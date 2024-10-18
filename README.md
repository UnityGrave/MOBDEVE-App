# Senate Election Info Application

**Course**: MOBDEVE Major Course Output  
**Program**: BSCS-ST1, BSIT2  
**Team Members**:  
- Akia, Dylan Lee (S14)  
- Genota, Kean (S12)  
- Refuerzo, Lloyd Dominic (S12)  

---

## Description

The **Senate Election Info Application** provides users with a user-friendly method of accessing election information, learning more about candidates, and simulating voting through a mock ballot. To enhance security, the application includes a sample ballot creator, QR code scanning for quick candidate profile access, and Two-Factor Authentication (2FA). Voters can use this app to become more informed and familiar with the voting process before election day.

---

## Services / APIs

- **Camera Usage (QR Scanner)**:  
  Allows users to easily access candidate information by scanning QR codes displayed during campaigns or on campaign materials.

- **MySQL Database**:  
  Stores essential data such as user profiles, candidate information, and mock ballots.

- **Facial Recognition (2FA)**:  
  Provides additional security through biometric verification during user log-in, ensuring authorized access and data protection.

- **Firebase Cloud Messaging (FCM)**:  
  A Google service used to send push notifications to iOS, Android, and web applications, keeping users informed about important election updates.

---

## Functions

| **Function**             | **Description**                                                                                                                                                             |
|--------------------------|-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| **Register**             | Users must register an account by providing their full name, birthday, email, and password before accessing other features of the app.                                         |
| **Log-in**               | Users log in by entering their registered email and password to access the app's features.                                                                                     |
| **QR Scanner Tab**       | Enables users to scan QR codes for quick redirection to candidate profile pages.                                                                                               |
| **View Candidate Profile** | Users can view detailed profiles of each candidate, including their platform, background, and campaign promises.                                                            |
| **Sample Ballot Maker**  | Users can create sample ballots by selecting candidates, simulating the election process. They can also print or save the mock ballots for reference on election day.          |
| **Two-Factor Authenticator** | Adds an extra layer of security by requiring facial recognition, in addition to a password, to ensure authorized access and protect user information.                      |
| **Election Reminders**   | Sends push notifications to remind users of important election dates such as registration deadlines, debates, and election day.                                                |

---

## Installation Instructions

1. Clone the repository using the following command:
    ```bash
    git clone https://github.com/UnityGrave/MOBDEVE-App
    ```

2. Open the project in **Android Studio**.

3. Sync the project with Gradle files to ensure all dependencies are installed.

4. Configure your MySQL database and ensure Firebase services (such as FCM and Authentication) are set up.

5. Run the application on your Android device or emulator.

---

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.
