package com.example.headsupgame

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isInvisible
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.random.Random
import android.content.res.Configuration
import android.os.PersistableBundle
import android.view.View
import androidx.core.view.isVisible

class MainActivity : AppCompatActivity() {
    private lateinit var sharedPreferences: SharedPreferences
    var  celebrityData = ArrayList<Celebrity>()
    lateinit var titleTv : TextView
    lateinit var timerTV : TextView
    lateinit var rotateTV : TextView
    lateinit var startBu : Button
    lateinit var layout: ConstraintLayout
    lateinit var nameTV : TextView
    lateinit var taboo1Tv : TextView
    lateinit var taboo2Tv : TextView
    lateinit var taboo3Tv : TextView
    lateinit var  timer :CountDownTimer
    var clicked = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        titleTv = findViewById(R.id.titleTV)
        timerTV =findViewById(R.id.timerTV)
        rotateTV = findViewById(R.id.RotateTv)
        startBu =  findViewById(R.id.StartBu)
        layout =  findViewById(R.id.layout)
        nameTV = findViewById(R.id.nameTV)
        taboo1Tv = findViewById(R.id.taboo1Tv)
        taboo2Tv = findViewById(R.id.taboo2Tv)
        taboo3Tv = findViewById(R.id.taboo3Tv)

        startBu.setOnClickListener{
            rotateTV.visibility = View.VISIBLE
            clicked = true
            startTimer ()


        }



    }

    fun startTimer (){



            timer = object: CountDownTimer(60000, 1000) {
                override fun onTick(millisUntilFinished: Long) {

                    timerTV.setText("seconds remaining: " + millisUntilFinished / 1000);
                    startBu.isEnabled = false
                }

                override fun onFinish() {
                    timerTV.setText("Time is over  ")
                    startBu.isEnabled = true

                }

            }



        timer.start()
    }

    fun getData(){
        val apiInterface = APIClient().getClient()?.create(APIInterface::class.java)
        apiInterface?.getCelebrityData()?.enqueue(object : Callback<List<Celebrity>> {

            override fun onResponse(call: Call<List<Celebrity>>, response: Response<List<Celebrity>>) {



                for(data in response.body()!!){
                    celebrityData.add(data)



                    val randomNumber = Random.nextInt(0, celebrityData.size)

                    nameTV.text = celebrityData[randomNumber].name
                    taboo1Tv.text = celebrityData[randomNumber].taboo1
                    taboo2Tv.text = celebrityData[randomNumber].taboo2
                    taboo3Tv.text =  celebrityData[randomNumber].taboo2


                }



            }

            override fun onFailure(call: Call<List<Celebrity>>, t: Throwable) {
                Toast.makeText(this@MainActivity, "ERROR", Toast.LENGTH_LONG).show()
            }
        })
    }
    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            rotateTV.visibility = View.INVISIBLE

            nameTV.visibility =  View.VISIBLE
            taboo1Tv.visibility = View.VISIBLE
            taboo2Tv.visibility = View.VISIBLE
            taboo3Tv.visibility = View.VISIBLE


                 getData()





        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            if (clicked==true){
                rotateTV.visibility = View.VISIBLE

            nameTV.visibility =  View.INVISIBLE
            taboo1Tv.visibility = View.INVISIBLE
            taboo2Tv.visibility = View.INVISIBLE
            taboo3Tv.visibility = View.INVISIBLE
            }
            else {
                rotateTV.visibility = View.INVISIBLE
            }




        }
    }


}