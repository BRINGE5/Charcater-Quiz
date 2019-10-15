package com.example.vocabquiz

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import kotlinx.android.synthetic.main.activity_study.*
import java.util.*
import kotlin.collections.ArrayList

class StudyActivity : AppCompatActivity() {

    private val words = ArrayList<String>()
    private val defns = ArrayList<String>()
    private var wordToDefn = HashMap<String, String>()
    private var index = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_study)

        readDictionaryFile()

        val myAdapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, words)
        words_list.setAdapter(myAdapter)

        //This changes the definition_view to display the definition of the clicked word.
        words_list.setOnItemClickListener { _, _, index, _ ->
            val studyDefinition = wordToDefn.get(words[index])
            val definition = studyDefinition.toString()
            definition_view.text = "$definition"

            if(words[index].toString() == "Kratos"){
                characterView.setImageResource(R.drawable.kratos)
            }
            else if(words[index].toString() == "Mario"){
                characterView.setImageResource(R.drawable.mario)
            }
            else if(words[index].toString() == "Thermite"){
                characterView.setImageResource(R.drawable.thermite)
            }
            else if(words[index].toString() == "Tidehunter"){
                characterView.setImageResource(R.drawable.tidehunter)
            }
            else if(words[index].toString() == "Banshee-44"){
                characterView.setImageResource(R.drawable.banshee44)
            }

        }

    }

    private fun readDictionaryFile() {
        val reader = Scanner(resources.openRawResource(R.raw.grewords))
        while (reader.hasNextLine()){
            // "alpha \t first letter of the Greek alphabet
            val line = reader.nextLine()
            Log.d("mgk","the line is: $line")
            val pieces = line.split("\t")
            if (pieces.size >= 2) {
                words.add(pieces[0])
                wordToDefn.put(pieces[0], pieces[1])
            }
        }
    }


    fun goBackButtonClick(view: View){
        onBackPressed()
    }
}
