package com.example.authregres
import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context

import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
class DBHelper (private val context: Context, private val factory: SQLiteDatabase.CursorFactory?) :
    SQLiteOpenHelper(context, "database", factory, 1)
{
    override fun onCreate(db: SQLiteDatabase?)
    {
        val query = "CREATE TABLE users (id integer primary key autoincrement default null, login text, email text, password text)"
        db!!.execSQL(query)
    }

    override fun onUpgrade(db: SQLiteDatabase?, p1: Int, p2: Int)
    {
        db!!.execSQL("Drop table if exists users")
        onCreate(db)
    }
    fun addUser(user: User)
    {
        val values = ContentValues()
        values.put("login", user.login)
        values.put("email", user.email)
        values.put("password", user.password)
        val db = this.writableDatabase
        db.insert("users", null, values)
        db.close()
    }
    @SuppressLint("Recycle")
    fun getUser (login: String, password: String): Boolean {
        val db = this.readableDatabase
        val result = db.rawQuery( "SELECT * FROM users WHERE login = '$login' AND password = '$password'", null)
        return result.moveToFirst()
    }
    @SuppressLint("Recycle")
    fun checkUserByEmail(email: String): Boolean
    {
        val db = this.readableDatabase
        val result = db.rawQuery("SELECT * FROM users WHERE email = '$email'", null)
        return result.moveToFirst()
    }
    fun updatePassword(email:String, newPassword:String): Boolean{
        val db = this.writableDatabase
        val values = ContentValues()
        values.put("password", newPassword)
        val rowsAffected = db.update("users", values, "email = ?", arrayOf(email))
        db.close()
        return rowsAffected > 0
    }
    @SuppressLint("Recycle")
    fun getUserByEmail(email:String): User?{
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM users WHERE email = '$email'", null)
        return if (cursor.moveToFirst())
        {
            val login = cursor.getString(cursor.getColumnIndexOrThrow("login"))
            val password = cursor.getString(cursor.getColumnIndexOrThrow("password"))
            User(login, email, password)
        }
        else
        {
            null
        }
    }
}