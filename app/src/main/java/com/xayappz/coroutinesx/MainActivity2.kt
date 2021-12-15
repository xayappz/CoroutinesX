package com.xayappz.coroutinesx

import android.app.ProgressDialog
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.awaitResponse
import retrofit2.converter.gson.GsonConverterFactory

const val BASEURL = "https://jsonplaceholder.typicode.scom/"

class MainActivity2 : AppCompatActivity() {
    lateinit var textView: TextView;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)
        textView = findViewById(R.id.Tv2)
        val progressDialog = ProgressDialog(this@MainActivity2)
        progressDialog.setTitle("Loading")
        progressDialog.setMessage("loading data, please wait")
        progressDialog.show()
        val api =
            Retrofit.Builder().baseUrl(BASEURL).addConverterFactory(GsonConverterFactory.create())
                .build().create(MyApi::class.java)



        lifecycleScope.launch(Dispatchers.IO) {

            lifecycleScope.launch (mCoroutineExceptionHandler){


// awaitResponse gives the list of response.
//           val data= api.getComments().awaitResponse()

                val data = api.getComments().awaitResponse()
                if (data.isSuccessful) {
                    val sb = StringBuilder()
                    withContext(Dispatchers.Main) {
                        for (dataFetch in data.body()!!) {
                            sb.append(dataFetch.name).append("\n")
                            textView.text = sb
                            Log.d("Response", dataFetch.name)
                            progressDialog.dismiss()
                        }


                    }


                } else {
                    Toast.makeText(this@MainActivity2, "Some Error!", Toast.LENGTH_SHORT).show()
                    progressDialog.dismiss()

                }

            }



        }


    }

    private val mCoroutineExceptionHandler =
        CoroutineExceptionHandler { coroutineContext, throwable ->

            Toast.makeText(this@MainActivity2, "Some Error!", Toast.LENGTH_SHORT).show()

        }
}