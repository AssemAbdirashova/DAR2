package com.example.recyclerview.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.recyclerview.Database
import com.example.recyclerview.R
import com.example.recyclerview.models.Student

class MainActivity : AppCompatActivity() {
    private val db = Database.instance
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setAmountOfStudent()

        findViewById<Button>(R.id.add_student).setOnClickListener {
            val name = findViewById<EditText>(R.id.name_of_student).text.toString()
            val surname = findViewById<EditText>(R.id.surname_of_student).text.toString()
            val student = Student(name, surname)
            if (name == "" || surname == "") {
                showResponse("Нету данных")
            }else if(db.checkExisting(student)) {
               showResponse("Существует")
            }else {
                db.addStudent(student)
                setAmountOfStudent()
                showResponse("Добавлен")
            }
            hideKeyboard()
        }

        findViewById<Button>(R.id.next_page).setOnClickListener {
            val intent = Intent(this, SecondActivity::class.java)
            startActivity(intent)
        }
    }

    private fun setAmountOfStudent(){
        findViewById<TextView>(R.id.amount_of_students).text = db.size().toString()
    }

    private fun showResponse(s: String?){
        when(s){
            "Нету данных" -> Toast.makeText(this, "Введите необходимые данные!", Toast.LENGTH_SHORT).show()
            "Существует" ->  Toast.makeText(this, "Студент уже существует!", Toast.LENGTH_SHORT).show()
            "Добавлен" -> Toast.makeText(this, "Студент успешно добавлен!", Toast.LENGTH_SHORT).show()
        }
    }

    private fun hideKeyboard(){
        try {
            val imm: InputMethodManager =
                getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(currentFocus!!.windowToken, 0)
        } catch (e: Exception) {
        }
    }


}