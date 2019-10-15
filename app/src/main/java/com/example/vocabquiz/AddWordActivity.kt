package com.example.vocabquiz

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import kotlinx.android.synthetic.main.activity_add_word.*
import java.io.PrintStream

class AddWordActivity : AppCompatActivity() {
    private val WORDS_FILE_NAME = "extrawords.txt"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_word)
    }

    fun letsAddTheWord(view: View) {
        val word = word_to_add.text.toString()
        val defn = word_definition.text.toString()

        val line = "$word\t$defn"

        val outStream = PrintStream(openFileOutput(WORDS_FILE_NAME, MODE_PRIVATE))
        outStream.println(line)
        outStream.close()

// go back to the main activity and return word+defn
        val myIntent = Intent()
        myIntent.putExtra("word", word)
        myIntent.putExtra("defn", defn)
        setResult(RESULT_OK, myIntent)
        finish()

    }

    fun goBackButtonClick(view: View){
        /*
        val myIntent = Intent(this, MainActivity::class.java)
        startActivity(myIntent)
         */
        onBackPressed()
    }

}
