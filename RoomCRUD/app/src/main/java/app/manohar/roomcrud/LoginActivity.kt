package app.manohar.roomcrud

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_login)

        val button = findViewById<Button>(R.id.login_btn)
        val studentNumber = findViewById<TextView>(R.id.studentNumber_login)
        val userPassword = findViewById<TextView>(R.id.password_login)

        button.setOnClickListener{
            val defaultPassword = "COMP304Lab4"
            val defaultStudentNumber = "301236235"

            if(studentNumber.text.toString() == defaultStudentNumber
                && userPassword.text.toString() == defaultPassword)
            {
                val intent = Intent(this, DataEntryActivity::class.java)
                startActivity(intent)
            }
            else
                Toast.makeText(this, "Incorrect credentials!", Toast.LENGTH_SHORT).show()

        }
    }
}