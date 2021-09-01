package com.example.retrofitsimplegetrequest

import android.os.Bundle
import android.util.Log
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
            viewModel.getCustomPosts(Integer.parseInt(myNumber), "id", "desc")

            viewModel.myCustomPosts.observe(this, Observer { response ->
                if (response.isSuccessful) {
                    textView.text = response.body().toString()
                    response.body()?.forEach {
                        Log.d("Response", it.userId.toString())
                        Log.d("Response", it.id.toString())
                        Log.d("Response", it.title.toString())
                        Log.d("Response", it.body.toString())
                        Log.d("Response", "------------")
                    }
                } else {
                    textView.text = response.code().toString()
                }
            })
        }

    }
}