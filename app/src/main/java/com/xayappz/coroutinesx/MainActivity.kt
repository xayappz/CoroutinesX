package com.xayappz.coroutinesx

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.*
import kotlin.system.measureTimeMillis

class MainActivity : AppCompatActivity() {
    private val TAG = javaClass.simpleName
    private lateinit var textView: TextView;
    private lateinit var btn: Button;


    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        textView = findViewById(R.id.Tv)
        btn = findViewById(R.id.Bn)

        textView.text = Thread.currentThread().name

        //GlobalScope will work until app will get closed

        //lifecycleScope will work until activity destroyed

        lifecycleScope.launch(Dispatchers.IO) {
            Log.d(TAG, "running all time until App is closed")

            val time = measureTimeMillis {
                val req1 = async { doNetworkCall() }

                val req2 = async { doNetworkCall2() }

                Log.d("REQUEST1 ", "${req1.await()}")
                Log.d("REQUEST2 ", "${req2.await()}")


            }


            Log.d("TOTAL TIME TAKEN", " ${time}")

//repeating task
//            repeat(2) {
//                callNetwork0 = doNetworkCall()
//            }
//            while (true) {
//                callNetwork0 = doNetworkCall()
//            }


//            Log.d(TAG, callNetwork0)
//            Log.d(TAG, callNetwork1)
            withContext(Dispatchers.Main)
            {
                textView.text = "" + " ${Thread.currentThread().name}"
            }
        }

        btn.setOnClickListener {

            lifecycleScope.launch(Dispatchers.IO) {
                Intent(this@MainActivity, MainActivity2::class.java).also {
                    startActivity(it)
                    // finish()
                }

            }

        }

        //This will block thread
//        runBlocking {
//            launch (Dispatchers.IO){
//                delay(3000L)
//                       Log.d(TAG, "Finished IO 1")
//
//            }
//            launch (Dispatchers.IO){
//                delay(7000L)
//                Log.d(TAG, "Finished IO 2")
//
//            }
//            delay(4000L)
//        }

    }

    //suspend fn can only be called from suspend fn or coroutine
    private suspend fun doNetworkCall(): String {

        delay(2000L)
        return "This is the response"
    }

    private suspend fun doNetworkCall2(): String {

        delay(2000L)
        return "This is the response 2"
    }


}