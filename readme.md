# 💖 DonShare

DonShare adalah platform berbasis **Android** yang dirancang untuk **mempermudah proses donasi** serta **menyalurkan bantuan kepada yang membutuhkan** secara cepat, aman, dan transparan.

---

## 🚀 Tujuan Proyek

- 🤝 Membantu menyalurkan bantuan kepada pihak yang membutuhkan  
- 💸 Mempermudah masyarakat untuk berdonasi kapan saja dan di mana saja  
- 🔍 Menyediakan riwayat donasi yang transparan  

---

## 🛠️ Tools yang Digunakan

### 📱 Frontend (Mobile)
- Android Studio  
- Kotlin  
- Material Design  

### 🌐 Backend
- Node.js  
- Express.js  

### 🗄️ Database
- PostgreSQL  

---

## ✨ Fitur Utama

- 🔐 **Login** – Autentikasi pengguna  
- 📝 **Register** – Pendaftaran akun pengguna baru  
- 💰 **Donasi** – Melakukan donasi dengan mudah  
- 📜 **Riwayat Donasi** – Melihat histori donasi  
- 💳 **Isi Saldo** – Top up saldo untuk donasi  
- 👤 **Profil Pengguna** – Melihat dan mengelola data akun  

---

## 📱 Alur Singkat Aplikasi

1. Pengguna melakukan **registrasi / login**
2. Pengguna melakukan **isi saldo**
3. Pengguna memilih menu **donasi**
4. Donasi berhasil diproses
5. Riwayat donasi tersimpan dan dapat dilihat

---

## 📂 Struktur Umum Proyek

```bash
📦 project-root
├── 📱 android-app
│   ├── activities
│   ├── adapters
│   ├── models
│   └── layouts
│   
│
├── 🌐 backend
│   ├── routes
│   ├── controllers
│   ├── middlewares
│   └── models
│  
│   
│
└── 🗄️ database
    ├── migrations
    └── schema.sql

```

## ⚙️ Instalasi & Menjalankan Backend

# Masuk ke dalam folder backend
```bash
cd backend

```

# Install Package
```bash
npm install

```

# Configuration
```bash
# Masuk Folder database dan konfigurasi db_create dan db 
```
# Membuat database
```bash
node database/dbCreate.js
node database/tableCreate.js
node migrate.js
```

# Menjalankan server
```bash
node server.js
```