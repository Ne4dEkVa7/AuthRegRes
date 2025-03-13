package com.example.authregres

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class Registration : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.registration)

        val userLogin: EditText = findViewById(R.id.user_login)
        val userEmail: EditText = findViewById(R.id.user_email)
        val userPass: EditText = findViewById(R.id.user_password)
        val button = findViewById<Button>(R.id.button_reg)

        val linkToAuthorization = findViewById<ImageButton>(R.id.button_back)

        linkToAuthorization.setOnClickListener()
        {
            val intent = Intent(this, Authorization::class.java)
            startActivity(intent)
        }
        button.setOnClickListener()
        {
            val login = userLogin.text.toString()
            val email = userEmail.text.toString()
            val password = userPass.text.toString()

            val listOfEdits = listOf(login, email, password)

            if (listOfEdits.any{it.isEmpty()})
            {
                Toast.makeText(this, "Все поля должны быть заполнены!", Toast.LENGTH_LONG).show()
            }
            else
            {
                val user = User(login, email, password)
                val db = DBHelper(this, null)
                db.addUser(user)
                Toast.makeText(this, "Регистрация прошла успешно!", Toast.LENGTH_LONG).show()
                userLogin.text.clear()
                userEmail.text.clear()
                userPass.text.clear()
            }
        }


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.LinearLayout)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}