package com.example.quiz

import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.activity_quiz_question.*
import org.json.JSONArray
import kotlin.random.Random

class QuizQuestionActivity : AppCompatActivity()
{

    var qNo = 0
    var selectedOption = 0
    var selectedTV : TextView? = null
    var correctTV : TextView? = null
    var correctPos : Int = 0
    var noOfCorrectAns = 0
    var name : String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz_question)


        val intent = intent
       name = intent.getStringExtra("name")
        tv_question.text = name

            loadQuestion(btn_submit)

    }


    fun loadQuestion(v : View)
    {
            qNo++ ;
        progress_bar.progress = qNo
        tv_qNo.text = "$qNo/10"
        defaultOptionViews()
        val requestQueue = Volley.newRequestQueue(this)
        val url = "https://opentdb.com/api.php?amount=1&type=multiple"
        val jsonObjReq = JsonObjectRequest(Request.Method.GET , url ,null ,
            { response ->


                val jsonArr = response.getJSONArray("results")
                val jsonQ = jsonArr.getJSONObject(0)
                val que = jsonQ.getString("question").replace("&quot;" , "\"")
                    .replace("&#039;" , "'").replace("&shy;" , "-")

                val correctAns = jsonQ.getString("correct_answer").replace("&quot;" , "\"")
                    .replace("&#039;" , "'").replace("&shy;" , "-").replace("&amp" , "&")

                val incorrectAr = jsonQ.getJSONArray("incorrect_answers")

                tv_question.text = "Q.  $que"

                correctPos = Random.nextInt(4)
                var i = 0

                if (correctPos == 0) {
                    tv_option1.text = correctAns
                    correctTV  = tv_option1
                }
                else tv_option1.text = incorrectAr.getString(i++).replace("&quot;" , "\"")
                    .replace("&#039;" , "'").replace("&shy;" , "-")
                    .replace("&amp" , "&")

                if (correctPos == 1) {
                    tv_option2.text = correctAns
                    correctTV = tv_option2
                }
                else tv_option2.text = incorrectAr.getString(i++).replace("&quot;" , "\"")
                    .replace("&#039;" , "'").replace("&shy;" , "-")
                    .replace("&amp" , "&")

                if (correctPos == 2){
                    tv_option3.text = correctAns
                    correctTV = tv_option3
                }
                else tv_option3.text = incorrectAr.getString(i++).replace("&quot;" , "\"")
                    .replace("&#039;" , "'").replace("&shy;" , "-")
                    .replace("&amp" , "&")

                if (correctPos == 3){
                    tv_option4.text = correctAns
                    correctTV = tv_option4
                }
                else tv_option4.text = incorrectAr.getString(i++).replace("&quot;" , "\"")
                    .replace("&#039;" , "'").replace("&shy;" , "-")
                    .replace("&amp" , "&")

            } ,
            {
                    error ->
                Toast.makeText(this , error.message , Toast.LENGTH_SHORT).show()
            }
        )

        requestQueue.add(jsonObjReq)
    }


    fun defaultOptionViews()
    {
        val options = ArrayList<TextView>()
        options.add(tv_option1)
        options.add(tv_option2)
        options.add(tv_option3)
        options.add(tv_option4)


        for (option in options) {
            option.setTextColor(Color.parseColor("#7A8089"))
            option.typeface = Typeface.DEFAULT
            option.background = ContextCompat.getDrawable(this , R.drawable.default_option_border_bg)
        }

    }


    fun selectedOptionView(tv : TextView)
    {
        defaultOptionViews()

        tv.setTextColor(Color.parseColor("#363A43"))
        tv.setTypeface(tv.typeface , Typeface.BOLD)
        tv.background = ContextCompat.getDrawable(this ,R.drawable.selected_option_border_bg)
    }

    fun correctOptionView(tv : TextView)
    {
        defaultOptionViews()
        tv.background = ContextCompat.getDrawable(this ,R.drawable.correct_option_border_bg)
    }

    fun incorrectOptionView(tv : TextView)
    {
        tv.background = ContextCompat.getDrawable(this , R.drawable.incorrect_option_border_bg)
    }


    fun onOptionClick(v : View)
    {
        if (btn_submit.text.toString() == "SUBMIT")
        {

            when (v) {
                tv_option1 -> selectedOption = 1
                tv_option2 -> selectedOption = 2
                tv_option3 -> selectedOption = 3
                tv_option4 -> selectedOption = 4
            }
            selectedTV = v as TextView
            selectedOptionView(v)
       }
    }


    fun onSubmitClick(v : View)
    {
        if ((v as Button).text.toString() == "SUBMIT")
        {
            if (selectedOption == 0) {
                Toast.makeText(this , "Please select an answer" , Toast.LENGTH_SHORT).show()
                return
            }

            if (selectedTV != correctTV) {
                correctOptionView(correctTV as TextView)
                incorrectOptionView(selectedTV as TextView)
            } else {
                correctOptionView(correctTV as TextView)
                noOfCorrectAns++
            }
            selectedOption = 0
                if (qNo == 10) btn_submit.text = "FINISH"
            else
            btn_submit.text = getString(R.string._go_to_next_question)
        }
          else if (v.text.toString() == getString(R.string._go_to_next_question))
          {
            loadQuestion(v)
            btn_submit.text = "SUBMIT"
          }

        else {
            qNo = 0
            val intent = Intent(this , ResultActivity::class.java)
            intent.putExtra("name" , name)
            intent.putExtra("score" , noOfCorrectAns)
            startActivity(intent)
            finish()
        }

    }




}









