package com.example.recyclerviewselectionadddeletemodify

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlin.random.Random

class MainActivity : AppCompatActivity() {
    private lateinit var adapter: FrankRVAdapter
    private lateinit var tv: EditText
    val frankData = ArrayList<String>()      // create empty ArrayList

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val rv = findViewById<RecyclerView>(R.id.names_recyclerView)
        rv.layoutManager = LinearLayoutManager(this)

        // Create data source -------------------------------
        Log.i("Frank", "Creating ArrayList...")
        createData(frankData, 50)           // populate it

        tv = this.findViewById<EditText>(R.id.input_edittext)
        Log.i("Frank", "******* tv: [$tv]")

        // Attach your RecyclerView Adapter to the RecyclerView instance
        adapter = FrankRVAdapter(frankData, tv)
        rv.adapter = adapter

        createCallbacks()           // Set up callback for editText
    }

    /*
    createCallbacks() - Keep callback init in separate function
    */
    private fun createCallbacks() {

        // Set the KeyListener on the Edittext and look for <CR> =======================
        tv.setOnKeyListener(object : View.OnKeyListener {
            override fun onKey(myview: View?, keyCode: Int, event: KeyEvent): Boolean {
                if (keyCode == KeyEvent.KEYCODE_ENTER && event.action == KeyEvent.ACTION_DOWN) {

                    val currentPos = adapter.getPositionSelected()      // Call our method that saved the position of the selected item
                    val userInput = tv.getText().toString()             // just a convenience variable...
                    Log.i("Frank", "ENTER key pressed. pos: $currentPos   selection: [$userInput]")

                    // change the actual data and then tell the adapter things were changed...
                    frankData[currentPos] = userInput
                    adapter.notifyDataSetChanged()          // should really use notifyItemChanged(pos)

                    return true

                } else
                    return false
            }
        })

        findViewById<Button>(R.id.delete_button).setOnClickListener {
            Log.i("Frank", "------Clicked on DELETE Button")

            val currentPos = adapter.getPositionSelected()
            frankData.removeAt(currentPos)          // remove item at current position
            adapter.notifyDataSetChanged()
        }

        findViewById<Button>(R.id.add_button).setOnClickListener {
            Log.i("Frank", "+++++++Clicked on ADD Button")

            val currentPos = adapter.getPositionSelected()
            frankData.add(currentPos, tv.getText().toString())  // insert input at current position
            adapter.notifyDataSetChanged()
        }

    }

    /*
     createData() - create a data source (of size num) with random names taken from a static list
     */
    private fun createData(mydata: ArrayList<String>, num: Int) {
        var s: String
        val names = arrayListOf<String>()
        names.addAll(
            listOf(
                "Frank Greco",
                "Miles Davis",
                "Louie Armstrong",
                "Mick Jagger",
                "BB King",
                "John Mayer",
                "Bernard Purdie",
                "Angela Bassett",
                "Sister Rosetta Tharpe"
            )
        )

        for (i in 0..num) {
            s = names.get(Random.nextInt(0, names.size)) + " - $i"
            mydata.add(s)
            Log.i("Frank", "name [" + s + "]")
        }
    }
}
