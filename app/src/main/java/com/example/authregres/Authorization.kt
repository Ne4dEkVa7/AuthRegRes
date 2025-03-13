package com.example.authregres

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat






class Authorization: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.authorization)
        val userLogin: EditText = findViewById(R.id.user_login_auth)
        val userPass: EditText = findViewById(R.id.user_password_auth)
        val button: Button = findViewById(R.id.button_auth)
        val linkToRegistration = findViewById<TextView>(R.id.create_account_text)
        val linkToForgetPassword = findViewById<TextView>(R.id.forget_password)

        linkToRegistration.setOnClickListener()
        {
            val intent = Intent(this, Registration::class.java)
            startActivity(intent)
        }
        linkToForgetPassword.setOnClickListener()
        {
            val intent = Intent(this, ForgetPassword::class.java)
            startActivity(intent)
        }
        button.setOnClickListener()
        {
            val login = userLogin.text.toString().trim()
            val password = userPass.text.toString().trim()
            if (login == "" || password == "")
            {
                Toast.makeText(this, "Вce пoля должны быть заполнены", Toast.LENGTH_LONG).show()
            }
            else
            {
                val db = DBHelper(this, null)
                val isAuth = db.getUser(login, password)
                if (isAuth) {
                    Toast.makeText(this, "Авторизашия успешна, пользователь $login найден", Toast.LENGTH_LONG).show()
                    userLogin.text.clear()
                    userPass.text.clear()
                }
                else
                {
                    Toast.makeText(this, "Hеверные данные, $login не найден", Toast.LENGTH_LONG).show()
                }
            }
        }
    }
}

