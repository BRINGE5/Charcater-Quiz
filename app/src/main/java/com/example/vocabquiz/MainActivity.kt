package com.example.vocabquiz

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {
    private val ADD_WORD_STUPID_CODE = 1234
    private var wordToDefn = HashMap<String, String>()
    private val words = ArrayList<String>()
    private val defns = ArrayList<String>()
    private lateinit var myAdapter : ArrayAdapter<String>

    private var index = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        readDictionaryFile()
        setupList()

        trivia_list.setOnItemClickListener { _, _, index, _ ->
            // todo
            Log.d ("mgk", "index is $index")
            val word = the_Name.text.toString()
            val defnCorrect = wordToDefn[word]
            val defnChosen = defns[index]
            if (defnChosen == defnCorrect) {
                results.text = "Correct!"
            }
                else {
                results.text = "Wrong!"
            }
            setupList()
            //  defns.removeAt(index)
            // myAdapter.notifyDataSetChanged()
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
    private fun setupList() {
        // pick a random word

        val rand = Random()
        index = rand.nextInt(words.size)
        val word = words[index]
        the_Name.text = word

        if(words[index].toString() == "Kratos"){
            characterQuizView.setImageResource(R.drawable.kratos)
        }
        else if(words[index].toString() == "Mario"){
            characterQuizView.setImageResource(R.drawable.mario)
        }
        else if(words[index].toString() == "Thermite"){
            characterQuizView.setImageResource(R.drawable.thermite)
        }
        else if(words[index].toString() == "Tidehunter"){
            characterQuizView.setImageResource(R.drawable.tidehunter)
        }
        else if(words[index].toString() == "Banshee-44"){
            characterQuizView.setImageResource(R.drawable.banshee44)
        }
        else{
            characterQuizView.setImageResource(R.drawable.controller)
        }

        // pick random definitions for the word

        defns.clear()
        defns.add(wordToDefn[word]!!)
       // answer = wordToDefn[word]!!
        words.shuffle()
        for (otherWord in words.subList(0,3)) {
            if (otherWord == word || defns.size == 3) {
                continue
            }
            defns.add(wordToDefn[otherWord]!!)
        }

        defns.shuffle()

        myAdapter = ArrayAdapter<String>(this,
            android.R.layout.simple_list_item_1, defns)

        trivia_list.adapter = myAdapter
    }

    fun studyButtonClick(view: View){
        //Launch StudyActivity
        val myIntent = Intent(this, StudyActivity::class.java)
        startActivity(myIntent)
    }

    fun addWordButtonClick(view: View) {
        // launch the AddWordActivity
        val myIntent = Intent(this, AddWordActivity::class.java)
        startActivityForResult(myIntent, ADD_WORD_STUPID_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, myIntent: Intent?) {
        super.onActivityResult(requestCode, resultCode, myIntent)
        if (requestCode == ADD_WORD_STUPID_CODE) {
            //unpack the word and definition sent back
            if (myIntent != null) {

                val word = myIntent.getStringExtra("word")
                val defn = myIntent.getStringExtra("defn")
                wordToDefn.put(word, defn)
                words.add(word)
            }
        }
    }


}

