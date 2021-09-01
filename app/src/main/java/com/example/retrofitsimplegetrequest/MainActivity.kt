package com.example.retrofitsimplegetrequest

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.retrofitsimplegetrequest.adapter.MyAdapter
import com.example.retrofitsimplegetrequest.repository.Repository

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel
    private lateinit var textView: TextView
    private lateinit var button: Button
    private lateinit var button2: Button
    private lateinit var editText: EditText
    private lateinit var recyclerView: RecyclerView
    private val myAdapter by lazy { MyAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        textView = findViewById<TextView>(R.id.textView)
        button = findViewById<Button>(R.id.button)
        button2 = findViewById<Button>(R.id.button2)
        editText = findViewById<EditText>(R.id.editText)

        val repository = Repository()
        val viewModelFactory = MainViewModelFactory(repository)

        setupRecyclerview()

        viewModel = ViewModelProvider(this, viewModelFactory).get(MainViewModel::class.java)
        viewModel.getCustomPosts(1, "id", "asc")
        viewModel.myCustomPosts.observe(this, Observer { response ->
            if (response.isSuccessful) {
                response.body()?.let { myAdapter.setData(it) }
            } else {
                Toast.makeText(this, response.code(), Toast.LENGTH_SHORT).show()
            }
        })


        button.setOnClickListener {
            val options: HashMap<String, String> = HashMap()
            options["_sort"] = "id"
            options["_order"] = "desc"

            val myNumber = editText.text.toString()
            viewModel.getCustomPosts2(Integer.parseInt(myNumber), options)

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

        button2.setOnClickListener {
            //val myPost = Post(1, 1, "layon.f", "Android Developer")
            //viewModel.pushPost(myPost)
            viewModel.pushPost2(1, 1, "layon.f", "Android Developer")

            viewModel.myResponse.observe(this, Observer { response ->
                if (response.isSuccessful) {
                    textView.text = response.body().toString()
                    Log.d("Response body", response.body().toString())
                    Log.d("Response code", response.code().toString())
                    Log.d("Response message", response.message())
                } else {
                    textView.text = response.code().toString()
                }
            })
        }
    }

    private fun setupRecyclerview() {
        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.adapter = myAdapter
        recyclerView.layoutManager = LinearLayoutManager(this)
    }
}