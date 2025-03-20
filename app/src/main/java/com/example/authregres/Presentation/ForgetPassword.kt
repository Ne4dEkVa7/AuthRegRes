package com.example.authregres.Presentation
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.authregres.Data.DBHelper
import com.example.authregres.Domain.EmailSender
import com.example.authregres.Domain.EmailSender.Companion.sendNewPasswordByEmail
import com.example.authregres.R


class ForgetPassword: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.forget_password)
        val db = DBHelper(this, null)
        val linkToAuthorization = findViewById<ImageButton>(R.id.button_back) // Пpoписываем переxод на другое окно приложения
        val resetPasswordButton: Button = findViewById(R.id.button_send_message)
        val etEmail: EditText = findViewById(R.id.user_email)
        linkToAuthorization.setOnClickListener()
        {
            val intent = Intent(this, Authorization::class.java)
            startActivity(intent)
        }

        resetPasswordButton.setOnClickListener()
        {
            val email = etEmail.text.toString().trim()
            if (email == "") {
                Toast.makeText(this, "Введите ваши данные", Toast.LENGTH_SHORT).show()
            }


            if (db.checkUserByEmail(email)) {
                val newPassword = EmailSender.generateNewPassword()
                if (db.updatePassword(email, newPassword)) {
                    val user = db.getUserByEmail(email)
                    if (user != null) {
                        sendNewPasswordByEmail(user.email, newPassword)
                        Toast.makeText(
                            this,
                            "Hовый пapоль отправлен на ваш email",
                            Toast.LENGTH_SHORT
                        )
                            .show()
                        finish()
                    } else {
                        Toast.makeText(this, "Ouибка при обновлении пapoля", Toast.LENGTH_SHORT)
                            .show()
                    }
                }
            } else {
                Toast.makeText(this, "Пользователь не найден", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
