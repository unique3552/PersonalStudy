package com.example.bmicalculator

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_result.*
import org.jetbrains.anko.toast

class ResultActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)

        // 전달받은 키와 몸무게
        var height = intent.getStringExtra("height").toInt()
        var weight = intent.getStringExtra("weight").toInt()

        // BMI 계산
        val bmi = weight / Math.pow(height / 100.0, 2.0)

        // 결과 표시
        when{
            bmi >= 35 -> resultTextView.text = "고도 비만"
            bmi >= 30 -> resultTextView.text = "2딘계 비만"
            bmi >= 25 -> resultTextView.text = "1단계 비만"
            bmi >= 23 -> resultTextView.text = "과체중"
            bmi >= 18.5 -> resultTextView.text = "정상"
            else -> resultTextView.text = "저체중"
        }

        // 이미지 표시
        when{
            bmi >= 23 -> imageView.setImageResource(R.drawable.ic_sentiment_very_dissatisfied_black_24dp)
            bmi >= 18.5 -> imageView.setImageResource(R.drawable.ic_sentiment_neutral_black_24dp)
            else -> imageView.setImageResource(R.drawable.ic_sentiment_satisfied_black_24dp)
        }

        // Toast 메세지
        toast("$bmi")
    }
}
