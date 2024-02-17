package com.g985892345.androidwheel

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.SpannableStringBuilder
import android.widget.ImageView
import android.widget.TextView
import com.g985892345.android.utils.view.text.span.TextTagSpan

class MainActivity : AppCompatActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

    val tv: TextView = findViewById(R.id.tv)
    tv.text = SpannableStringBuilder("123")
      .append(
        TextTagSpan.getTagBuilder(
        ImageView(this).apply {
          setImageResource(R.drawable.ic_android_black_24dp)
        }
      ))
  }
}