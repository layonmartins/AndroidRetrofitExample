package com.example.retrofitsimplegetrequest

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.retrofitsimplegetrequest.repository.Repository

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel
    private lateinit var textView: TextView
    private lateinit var button: Button
    private lateinit var editText: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        textView = findViewById<TextView>(R.id.textView)
        button = findViewById<Button>(R.id.button)
        editText = findViewById<EditText>(R.id.editText)

        val repository = Repository()
        val viewModelFactory = MainViewModelFactory(repository)

        viewModel = ViewModelProvider(this, viewModelFactory).get(MainViewModel::class.java)
        viewModel.getPost()



        button.setOnClickListener {
            val myNumber = editText.text.toString()
            viewModel.getPost2(Integer.parseInt(myNumber))

            viewModel.myResponse2.observe(this, Observer { response ->
                if (response.isSuccessful) {
                    textView.text = response.body().toString()
                } else {
                    textView.text = response.code().toString()
                }
            })
        }

    }
}