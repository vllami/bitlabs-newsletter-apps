package com.bitlabs.newsletter.helper

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.bitlabs.newsletter.entity.News

/**
 * Dalam membuat database dalam aplikasi Android, butuh yang namanya Helper Class
 * Helper Class akan melakukan semua operasi query yang ada dalam aplikasi
 * Setelah itu, buat class NewsDBHelper dan lakukan inheritance terhadap class SQLiteOpenHelper..
 * Lalu tambahkan satu constructor dalam class NewsDBHelper, yaitu context
 */
class NewsletterDBHelper(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    /**
     * Membuat variable/properti yang disimpan dalam companion object { }
     * Companion Object -> Membuat variable menjadi static
     */
    companion object {
        private const val DATABASE_VERSION = 1
        private const val DATABASE_NAME = "newsletter.db"
        const val TABLE_NAME = "news"
        const val COLUMN_ID = "_id"
        const val COLUMN_TITLE = "title"
        const val COLUMN_BODY = "body"
        const val COLUMN_DATE = "date"
    }

    /**
     * Implementasi method yang dimiliki oleh class class SQLiteOpenHelper, sebelum kedua method dibawah ini (onCreate
     * dan onUpgrade) ada.. akan terdapat error pada class NewsDBHelper, arahkan kursor ke error tersebut dan klik
     * implement method
     */

    // Method onCreate, akan mengeksekusi sebuah DDL untuk membuat tabel.
    override fun onCreate(db: SQLiteDatabase) {
        // Membuat table dengan nama news (sesuai deklarasi pada companion object)
        val createNewsTable = ("CREATE TABLE $TABLE_NAME("
                + "$COLUMN_ID INTEGER PRIMARY KEY,"
                + "$COLUMN_TITLE TEXT NOT NULL,"
                + "$COLUMN_BODY TEXT NOT NULL,"
                + "$COLUMN_DATE TEXT NOT NULL)")

        // Setelah membuat variable yang menampung DDL, selanjutnya DDL tersebut eksekusi dengan fungsi execSQL()
        db.execSQL(createNewsTable)
    }

    /**
     * Method onUpgrade, hanya akan dipanggil ketika versi database yang ada pada penyimpanan lebih rendah
     * dibanding yang diinisialisasikan pada companion object
     */
    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }

    // Membuat function insert
    fun insertNews(news: News) {
        /**
         * Membuat variable values dengan value ContentValues dimana merupakan class yang akan menyimpan data-data
         * yang akan dimasukkan dalam database
         */
        val values = ContentValues()

        // values.put -> Berperan memasukkan datanya tersebut kedalam class ContentValues
        values.put(COLUMN_TITLE, news.title)
        values.put(COLUMN_BODY, news.body)
        values.put(COLUMN_DATE, news.date)

        // Membuat variable db dengan value this.writableDatabase yang bisa memanipulasi data yang ada pada database
        val db = this.writableDatabase

        // db.insert -> Berperan memasukkan data yang disimpan values kedalam database
        db.insert(TABLE_NAME, null, values)
        db.close()
    }

    // Membuat function updateNews, syntax-nya hampir mirip function insert.. hanya menambahkan parameter id
    fun updateNews(news: News, id: Int) {
        val values = ContentValues()
        values.put(COLUMN_TITLE, news.title)
        values.put(COLUMN_BODY, news.body)
        values.put(COLUMN_DATE, news.date)

        // Lalu buat variable db dengan value this.writableDatabase yang berarti database bisa di manipulasi
        val db = this.writableDatabase

        /**
         * Memanggil function db.update()
         * Argumen ketiga adalah where clause-nya dimana akan update data berdasarkan id dari News
         */
        db.update(TABLE_NAME, values, "_id = $id", null)
    }

    // Membuat function deleteNews
    fun deleteNews(id: Int) {

        // Lalu buat variable db dengan value this.writableDatabase yang berarti database bisa di manipulasi
        val db = this.writableDatabase

        /**
         * Memanggil method delete()
         * Argumen kedua adalah where clause-nya dimana akan delete data berdasarkan id dari News
         */
        db.delete(TABLE_NAME, "_Id = $id", null)
    }

    /**
     * Membuat function get untuk menampilkan data yang di-insert di HomeActivity..
     * Buat function-nya dengan return type ArrayList agar dapat dimasukkan dalam RecyclerView
     */
    fun getAllNews(): ArrayList<News> {
        // Lalu buat variable listNews untuk menyimpan ArrayList agar data-datanya bisa ditampung
        val listNews = ArrayList<News>()

        /**
         * Lalu buat query SQL..
         * SELECT untuk Mendapatkan data
         * Wildcard (*) untuk Mengambil semua atribut (kolom) dari table
         */
        val query = "SELECT * FROM $TABLE_NAME"

        /**
         * Lalu buat variable db dengan value this.readableDatabase yang berarti database hanya bisa dibaca,
         * tidak bisa dimasukkan/diubah datanya
         */
        val db = this.readableDatabase

        /**
         * Lalu buat variable cursor dengan value db.rawQuery(), argumen pertama dari function rawQuery adalah
         * query yang ditampung di variable.. dan argumen keduanya adalah default value apabila query tidak jalan
         * Cursor dapat diibaratkan sebuah collection dalam database, yakni dapat menampung banyak data didalamnya
         */
        val cursor = db.rawQuery(query, null)

        if (cursor.moveToFirst()) {
            // Setelah membuat cursor, sekarang looping cursor tersebut dengan do-while
            do {
                // Didalam do-while buat objek news untuk ditampung didalam ArrayList yang sudah dibuat
                val news = News(
                    // cursor.getString -> Mengambil data dari kolom table berdasarkan index
                    cursor.getString(1), // Index ke-1 -> title
                    cursor.getString(2), // Index ke-2 -> body
                    cursor.getString(3), // Index ke-3 -> date
                )
                news.id = cursor.getInt(0)
                listNews.add(news)
            } while (cursor.moveToNext())
        }
        db.close()
        cursor.close()

        // Terakhir, return variable listNews dengan ArrayList yang sudah menampung data yang ada di database
        return listNews
    }
}