package com.example.authregres.Presentation

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.example.authregres.Data.DBHelper
import com.example.authregres.R
import com.example.authregres.Presentation.Home.HomeNavigation


class Authorization: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        enableEdgeToEdge()
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
            if (login.isEmpty() || password.isEmpty())
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

                    val user = db.getUserByLogin(login)
                    if (user != null) {
                        val sharedPreferences = getSharedPreferences("user_prefs", MODE_PRIVATE)
                        with(sharedPreferences.edit()) {
                            putString("login", login)
                            putString("email", user.email)
                            apply()
                        }


                        val intent = Intent(this, HomeNavigation::class.java)
                        startActivity(intent)
                        finish()
                    } else {
                        Toast.makeText(this, "Ошибка: email пользователя не найден", Toast.LENGTH_LONG).show()
                    }
                }
                else
                {
                    Toast.makeText(this, "Hеверные данные, $login не найден", Toast.LENGTH_LONG).show()
                }
            }
        }
    }
}

