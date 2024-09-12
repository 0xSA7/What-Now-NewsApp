# What Now! - News App

> A comprehensive mobile news application that provides the latest updates on news from various domains, using a clean and modern UI.

---

## Table of Contents
- [About](#about)
- [Features](#features)
- [Technologies](#technologies)
- [Architecture](#architecture)
- [Installation](#installation)
- [Usage](#usage)
- [Contributing](#contributing)
- [License](#license)
- [Contact](#contact)

---

## About

**What Now** is a mobile news application built with Kotlin, utilizing **XML** for the UI. This app provides real-time updates from various news domains, including categories like politics, sports, technology, and more. It is designed with a focus on modular architecture, reusability, and scalability.

---

## Features
- **Latest News Updates**: Browse the latest news based on categories like sports, politics, technology, and more.
- **Advanced Search**: Search news by domain, language, date, and other filters.
- **Clean UI**: Built with a user-friendly interface using modern Android UI principles.
- **Multi-Language Support**: The app supports multiple languages based on the user's preference.

---

## Technologies

- **Programming Language**: Kotlin
- **Platform:** Android
- **UI**: XML, View Binding, Lottie Animations, Material Design, Fragments
- **Networking**: Retrofit
- **Firebase**: Authentication, Realtime Database, Push Notifications
- **Gradle Custom Plugins**: For build configuration and optimization
- **Architecture:** MVVM
- **Database:** Firestore
- **State Management:** LiveData, ViewModel
- **Local Storage:** SharedPreferences

---

## Architecture

This project follows the **Multi-Modular Architecture** design with separation of concerns, leveraging best practices like:

- **MVVM Pattern**: Model-View-ViewModel for managing UI-related data.
- **Repository Pattern**: For abstracting the data layer and handling API calls.
- **Builder Pattern**: For Building API calls.
- **Factory Pattern**:  Ritrofit

---

## Installation

1. **Clone the repository:**
   ```bash
   git clone https://github.com/0xSA7/What-Now-NewsApp.git
    ```
2.   **Open the project in Android Studio:**  
     Open Android Studio.
     Select "Open an existing project".
     Navigate to the cloned repository and select it.
3.   **Create a Firebase project and add the google-services.json file to the app directory.**
4.   **This App uses API from https://newsapi.org/**
5.  **Build the project:**  
    Let Android Studio download and configure all dependencies.
    Once the build is complete, you can run the app on an emulator or a physical device.

---

## Contributing

Contributions are welcome! Please follow these steps:

1. **Fork the Repository**
2. **Create a New Branch**
    ```bash
    git checkout -b feature/your-feature-name
    ```
3. **Commit Your Changes**
    ```bash
    git commit -m "Add some feature"
    ```
4. **Push to the Branch**
    ```bash
    git push origin feature/your-feature-name
    ```
5. **Create a Pull Request** explaining your changes.
---

## License
This project is licensed under the MIT License - see the LICENSE file for details.
---

## Meet our team:
- [Eyad Amro](https://github.com/eyadamr905)
- [Khaled Mahmoud](https://github.com/KhaledMa7mouad)
- [SA7](https://github.com/0xSA7)
- [Polla joseph](https://github.com/PollaJoseph)