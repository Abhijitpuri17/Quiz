    package com.example.quiz

    import android.content.Intent
    import android.os.Build
    import androidx.appcompat.app.AppCompatActivity
    import android.os.Bundle
    import android.view.View
    import android.view.WindowInsets
    import android.widget.Toast
    import com.android.volley.Request
    import com.android.volley.Response
    import com.android.volley.toolbox.JsonObjectRequest
    import com.android.volley.toolbox.Volley
    import kotlinx.android.synthetic.main.activity_main.*
    import org.json.JSONObject

    class MainActivity : AppCompatActivity()
    {


        override fun onCreate(savedInstanceState: Bundle?)
        {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_main)

            // HIDE STATUS BAR IN LAYOUT
                window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN



            btnStart.setOnClickListener {


                if (ETname.text.toString() == "") Toast.makeText(this , "Please Enter your name" , Toast.LENGTH_SHORT).show()
                else
                {
                    val intent = Intent(this, QuizQuestionActivity::class.java)
                    intent.putExtra("name" , ETname.text.toString())
                    startActivity(intent)
                    finish()


                }
            }


        }









    }