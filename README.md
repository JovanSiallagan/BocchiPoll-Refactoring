# 🎸 Bocchi the Rock! - Live Waifu Polling System

Aplikasi desktop interaktif untuk melakukan polling karakter favorit dari anime *Bocchi the Rock!* menggunakan Java Swing dan SQLite.

## ✨ Fitur Utama

- Voting karakter favorit secara interaktif
- Hasil voting diperbarui secara langsung (real-time)
- Tampilan modern dengan dark mode
- Gambar karakter menyesuaikan ukuran panel secara otomatis
- Klik gambar atau panel untuk memilih karakter
- Data voting tersimpan permanen menggunakan SQLite
- Export hasil voting ke file TXT
- Fitur reset data dengan proteksi password

---

## 🛠️ Teknologi yang Digunakan

- Java (JDK 8+)
- Java Swing & AWT
- SQLite
- SQLite JDBC Driver

---

## 📂 Struktur Proyek

```text
src/
├── BocchiPollMainFrame.java
├── VoteDAO.java
├── DatabaseConfig.java
├── KarakterVote.java
├── KandidatGridPanel.java
├── HasilChartPanel.java
├── ControlPanel.java
├── ResponsiveImagePanel.java
└── ReportExporter.java

images/
└── Gambar karakter

bocchi_poll.db
```

---

## 🚀 Cara Menjalankan

### Menjalankan dari Source Code

1. Clone repository:

```bash
git clone https://github.com/username/repository.git
```

2. Buka project menggunakan Eclipse, IntelliJ IDEA, atau NetBeans.
3. Pastikan library SQLite JDBC sudah ditambahkan ke project.
4. Jalankan file:

```java
BocchiPollMainFrame.java
```

---

## 🗄️ Database

Aplikasi menggunakan SQLite sebagai database lokal.

File database:

```text
bocchi_poll.db
```

Seluruh hasil voting akan tersimpan secara otomatis dan tetap tersedia meskipun aplikasi ditutup.

---

## 🔧 Smell Code yang Ditemukan dan Refactoring yang Dilakukan

Proyek ini telah melalui proses refactoring untuk meningkatkan kualitas kode, keterbacaan, dan kemudahan pengembangan.

### 1. Large Class

Sebelumnya hampir seluruh logika aplikasi berada dalam satu file besar. Setelah refactoring, tanggung jawab setiap bagian dipisahkan ke dalam beberapa kelas khusus:

| Kelas | Tanggung Jawab |
|---------|---------------|
| DatabaseConfig | Mengelola koneksi database |
| VoteDAO | Operasi CRUD dan akses data |
| KarakterVote | Model data karakter |
| ResponsiveImagePanel | Menampilkan gambar karakter |
| KandidatGridPanel | Menampilkan pilihan karakter |
| HasilChartPanel | Menampilkan hasil voting |
| ControlPanel | Tombol aksi aplikasi |
| ReportExporter | Export laporan voting |
| BocchiPollMainFrame | Pengendali utama aplikasi |

### 2. Long Method

Beberapa method yang terlalu panjang dipecah menjadi method yang lebih kecil dan fokus pada satu tugas tertentu.

Manfaat:
- Kode lebih mudah dibaca
- Lebih mudah diperbaiki dan dikembangkan
- Mempermudah proses debugging

### 3. Shotgun Surgery

Seluruh akses database dipusatkan pada kelas `VoteDAO`, sehingga logika database tidak bercampur dengan logika tampilan (UI) dan tidak terpisah-pisah sendiri.

### 4. Duplicated Code

Koneksi database yang sebelumnya ditulis berulang kali kini dipusatkan dalam kelas `DatabaseConfig`, sehingga lebih mudah dikelola dan dipelihara.

### 5. Message Chains

Pemanggilan method berantai yang terlalu panjang pada proses pengolahan gambar diperbaiki dengan menambahkan method pembantu.

---
