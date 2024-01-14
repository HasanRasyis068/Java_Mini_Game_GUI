# Java_Mini_Game_GUI
# Game Mini Sudoku

Ini adalah enhance program TictacToe dengan implementasi sederhana game Sudoku 3x3 dalam Java menggunakan Swing untuk antarmukanya. 

## Fitur

- Membangkitkan papan Sudoku 3x3 acak baru dengan 2 angka yang sudah diisi sebagai permulaan
- Timer yang berjalan saat kamu bermain
- Memeriksa apakah ada angka yang sama pada baris dan kolom
- Mencegah input angka yang tidak valid
- Memberi tahu saat teka-teki selesai diselesaikan
- Tombol New Game, Jeda, dan Periksa

## Aturan

- Isi sel kosong dengan angka 1 sampai 3
- Setiap baris harus memiliki angka unik
- Setiap kolom harus memiliki angka unik  
- Keseluruhan kotak harus memiliki angka unik

## Implementasi

Papan permainan direpresentasikan sebagai larik 2D `board[][]` yang menampung angka Sudoku sebenarnya. 

Larik 2D lain `originalNumbers[][]` menyimpan angka yang dibangkitkan secara acak diawal agar bisa diganti saat memulai game baru.

Kotak ditampilkan menggunakan komponen Swing `JTextField[][]`. 

Sebuah `Timer` digunakan untuk memperbarui tampilan waktu saat bermain. Dapat dijeda dan direset.

Validasi input mencegah memasukkan angka yang tidak valid atau duplikat.

Metode `isSudokuComplete()` memeriksa apakah semua aturan sudah diikuti untuk menentukan apakah teka-teki sudah terpecahkan.


## Cara Menjalankan

Game ini memerlukan Java dan Swing. Untuk menjalankan:

```
javac MiniSudoku.java
java MiniSudoku
```

Antarmuka game grafis akan terbuka dan teka-teki baru akan dibangkitkan.

## Langkah Selanjutnya

Beberapa ide untuk mengembangkan implementasi sederhana ini:

- Menambahkan tingkat kesulitan seperti kotak 4x4 atau 6x6
- Mengizinkan jumlah sel awal yang bisa dikustomisasi
- Mengimplementasikan petunjuk atau menyorot angka duplikat
- Menambah kemampuan memasukkan teka-teki Sudoku selain acak
- Menyimpan waktu penyelesaian tercepat
- Dukungan jaringan multiplayer
