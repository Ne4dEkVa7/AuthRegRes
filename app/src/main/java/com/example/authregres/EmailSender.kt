package com.example.authregres

import android.util.Log
import java.util.Properties
import javax.mail.Authenticator
import javax.mail.Message
import javax.mail.MessagingException
import javax.mail.PasswordAuthentication
import javax.mail.Session
import javax.mail.Transport
import javax.mail.internet.InternetAddress
import javax.mail.internet.MimeMessage

class EmailSender {
    companion object {
        private const val TAG = "EmailSender"
        fun generateNewPassword(): String {

            val allowedChars = ('A'..'Z') + ('a'..'z') + ('0'..'9')
            return (1..8)
                .map { allowedChars.random() }
                .joinToString("")

        }

        fun sendNewPasswordByEmail(email: String, newPassword: String) {
            val subject = "Сброс пароля"
            val body = "Bаш новый пароль: $newPassword"

            Thread {
                EmailSender().sendEmail(email, subject, body)
            }.start()
        }
    }
            fun sendEmail(to: String, subject: String, body: String) {
                val username = "zinovarsen723@gmail.com"
                val password = "nrgf ikyb kyum omiy"
                val props = Properties().apply {
                    put("mail.smtp.auth", "true")
                    put("mail.smtp.starttls.enable", "true")
                    put("mail.smtp.host", "smtp.gmail.com")
                    put("mail.smtp.port", "587")
                }
                val session = Session.getInstance(props, object : Authenticator() {
                    override fun getPasswordAuthentication(): PasswordAuthentication {
                        return PasswordAuthentication(username, password)
                    }
                })
                try {
                    val message = MimeMessage(session).apply {
                        setFrom(InternetAddress(username))
                        setRecipients(Message.RecipientType.TO, InternetAddress.parse(to))
                        setSubject(subject)
                        setText(body)
                    }
                    Transport.send(message)
                    Log.d(TAG, "Email sent successfully to $to")
                } catch (e: MessagingException) {
                    Log.e(TAG, "Failed to send email", e)
                }
            }
        }