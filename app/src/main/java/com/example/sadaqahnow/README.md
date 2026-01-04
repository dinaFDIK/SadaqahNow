# 📱 SadaqahNow – Aplikasi Donasi Mikro Harian

## 🕌 Donasi Mikro Harian dengan Gamifikasi

---

## 📌 Deskripsi Aplikasi

**SadaqahNow** adalah aplikasi mobile Android yang bertujuan memfasilitasi **donasi mikro harian** dengan nominal kecil secara mudah, cepat, dan konsisten. Aplikasi ini dirancang untuk membantu pengguna membangun kebiasaan bersedekah melalui alur donasi yang sederhana serta pengalaman pengguna yang nyaman.

Aplikasi dikembangkan menggunakan **Kotlin dan Jetpack Compose** dengan pendekatan navigasi modern berbasis Bottom Navigation.

---

## 🎯 Tujuan Pengembangan

* Mempermudah pengguna dalam melakukan donasi harian
* Menyediakan alur donasi yang cepat dan intuitif
* Menerapkan struktur aplikasi Android modern
* Sebagai implementasi praktikum Pemrograman Mobile

---

## ✨ Fitur Utama

* Onboarding pengguna
* Login & Register
* Kategori donasi
* Detail donasi
* Pembayaran donasi
* Halaman sukses donasi
* Profil pengguna
* Pengaturan aplikasi
* Bottom Navigation

---

## 🛠️ Teknologi yang Digunakan

* Android Studio
* Kotlin
* Jetpack Compose
* Material Design 3
* Compose Navigation

---

## 📂 Struktur Folder Project

Struktur folder aplikasi disesuaikan dengan implementasi fitur sebagai berikut:

```
app/
├── manifests/
├── kotlin+java/
│   └── com.example.sadaqahnow/
│       ├── features/
│       │   ├── auth/
│       │   │   ├── LoginScreen.kt
│       │   │   ├── OnBoardingScreen.kt
│       │   │   └── RegisterScreen.kt
│       │   ├── home/
│       │   │   ├── HomeScreen.kt
│       │   │   ├── DonationCategoryScreen.kt
│       │   │   ├── DonationDetailScreen.kt
│       │   │   ├── DonationPaymentScreen.kt
│       │   │   └── DonationSuccessScreen.kt
│       │   ├── profile/
│       │   │   └── ProfileScreen.kt
│       │   └── settings/
│       │       ├── SettingsScreen.kt
│       │       └── PaymentSourceScreen.kt
│       ├── navigation/
│       │   ├── SadaqahNowNavigation.kt
│       │   └── SadaqahNowBottomBar.kt
│       ├── ui/
│       │   ├── Color.kt
│       │   └── Theme.kt
│       └── MainActivity.kt
└── screenshots/
```

---

## ▶️ Cara Menjalankan Aplikasi

1. Buka project di Android Studio
2. Pastikan Gradle Sync berhasil
3. Jalankan aplikasi menggunakan Emulator atau perangkat Android
4. Aplikasi akan menampilkan halaman onboarding SadaqahNow

---

## 🧪 Hasil Praktikum (Screenshot Aplikasi)

### 1️⃣ Onboarding Screen


![Onboarding](./ss/onboard.jpg)

---

### 2️⃣ Login Screen

```
./screenshots/02-login.png
```

![Login](./ss/login.jpg)

---

### 3️⃣ Register Screen

```
./screenshots/03-register.png
```

![Register](./ss/register.jpg)

---

### 4️⃣ Home Screen

```
./screenshots/04-home.png
```

![Home](./ss/home.jpg)

---

### 5️⃣ Donation Category Screen


![Category](./ss/donation_category.jpg)

---

![Category](./ss/category.jpg)

---

![Category](./ss/categorie.jpg)

---

![Category](./ss/categori.jpg)

---

![Category](./ss/categoryy.jpg)


### 6️⃣ Donation Detail Screen

```
./screenshots/06-donation-detail.png
```

![Detail](./ss/detail.jpg)

---

### 7️⃣ Donation Payment Screen

```
./screenshots/07-donation-payment.png
```

![Payment](./ss/payment.jpg)

---

### 8️⃣ Donation Success Screen

```
./screenshots/08-donation-success.png
```

![Success](./ss/donation_success.jpg)

---

### 9️⃣ Profile Screen

```
./screenshots/09-profile.png
```

![Profile](./ss/profil.jpg)

---

### 🔟 Settings Screen

```
./screenshots/10-settings.png
```

![Settings](./ss/settings.jpg)

---

### 1️⃣1️⃣ Payment Source Screen


![Onboarding](./ss/payment_source.jpg)

---

## 👩‍🎓 Identitas Praktikum

* **Nama** : Dina Muzaina Aqillah
* **NIM** : 230104040221
* **Kelas** : TI 23A
* **Mata Kuliah** : Pemrograman Mobile
* **Aplikasi** : SadaqahNow

---

✍️ *README.md ini dibuat sebagai dokumentasi UAS aplikasi mobile Android menggunakan Jetpack Compose.*
