package com.example.recyclerviewselectionadddeletemodify

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

// Our RV adapter
//
// The first line needs a bit of explanation from both Android and Kotlin perspectives... -fgreco
//

class FrankRVAdapter(private val frankData: ArrayList<String>, private val tv : TextView) :    // Pass your data (and datatype) to your Adapter's constructor
    RecyclerView.Adapter<FrankRVAdapter.FrankHolder>() {            // Your adapter subclasses RecyclerView.Adapter
    // You have to tell this superclass what view holder datatype you are using (ie, FrankRVAdapter.FrankHolder).
    // And you have to specify the primary constructor used to initialize the superclass (note the '()')

    // Btw, frankData is the variable in this class that holds my data.
    // I decided to use an ArrayList (but I could have used any other data structure, eg, HashMap...
    //      but if I did, I would have to change all the ArrayList methods to HashMap methods in this class)
    //      Since the RecyclerView uses onCreateViewHolder(), onBindViewHolder(), getItemCount(), etc, it doesn't care
    //      what data structure we use internally.  This is the benefit of the adapter technique.

    private var numVis: Int = 0                         // shows how many times onCreateViewHolder() is called
    private lateinit var mycontext: Context
    private var pos: Int = -1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FrankHolder {
        this.mycontext = parent.context
        val view = LayoutInflater.from(this.mycontext).inflate(R.layout.recyclerview_listitem, parent, false)
        Log.i("Frank", "onCreateViewHolder() called... " + numVis++)
        return FrankHolder(view)
    }

    override fun onViewRecycled(holder: FrankHolder) {          // just for fun to see when a holder gets recycled
        super.onViewRecycled(holder)
        Log.i("Frank", "onViewRecycled() called... ")
    }

    /*
     onBindViewHolder() - what to do when any offscreen information needs to put into the visible Views
     */
    override fun onBindViewHolder(holder: FrankHolder, @SuppressLint("RecyclerView") position: Int) {
        val myitem = frankData.get(position)
        Log.i("Frank", "onBindViewHolder() called ------ myitem: [$myitem] : $position : $numVis" )

        holder.textView.setText(myitem)                 // Replace the info in the holder

        holder.textView.setOnClickListener {            // Replace the OnClickListener() for that row item
            tv.setText(myitem)                          // Set the edit text to whatever the user clicked on
            pos = position                              // Save the position for later... we want to replace the item at 'pos' with text the user enters
            Log.i("Frank", "position: $position  [$myitem]  [$pos]")

            holder.textView.setText("* " + myitem)      // simple-minded way to show my last selection (highlighting row is tricky in RV... so I'm avoiding for now)
        }
    }

    override fun getItemCount(): Int {
        return frankData.size
    }

    fun getPositionSelected(): Int {                    // Our own method to retrieve the 'pos' in the MainActivity
        return pos
    }

    /*
     FrankHolder() - my view holder.  It subclasses RecyclerView.ViewHolder and indicates the primary constructor to initialize that superclass
     *
     * Since this is a simple hello world, I'm just using a String in each list item of the RV
     */
    class FrankHolder(myView: View) : RecyclerView.ViewHolder(myView) {
        val textView : TextView = myView.findViewById(R.id.textView)
    }
}