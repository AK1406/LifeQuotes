package com.example.android.lifequotes

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.widget.ImageView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_quotes_detail.*
import kotlinx.android.synthetic.main.quote_detail_item_list.*

class QuoteDetailActivity : AppCompatActivity(){
    var categoryId=0
   private var adapter:QuoteAdapter?=null
    private var recyclerView: RecyclerView? = null
    private var quotesAdapter:QuoteAdapter?=null
    private var share:ImageView?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quotes_detail)

         share=findViewById(R.id.share)

        categoryId=intent.extras!!.get("QUOTE_CATEGORY_ID").toString().toInt()
        Toast.makeText(this,categoryId.toString(),Toast.LENGTH_LONG).show()
        recyclerView = findViewById(R.id.recyclerViewQuotes)
        setupRecyclerview()

     /*   share?.setOnClickListener {
            val msg: String = textViewQuote.text.toString()
            val intent=Intent()                                                                       //implicit intent
            intent.action=Intent.ACTION_SEND
            intent.putExtra(Intent.EXTRA_TEXT,msg)
            intent.type="text/plain"
            startActivity(Intent.createChooser(intent,"Share to : "))

        }*/

    }

    private fun setupRecyclerview() {
        val layoutManager = LinearLayoutManager(this)
        layoutManager.orientation= LinearLayoutManager.VERTICAL
        recyclerView?.layoutManager=layoutManager
        recyclerView?.setHasFixedSize(true)
        when (categoryId) {
            1 -> {
                adapter= QuoteAdapter(this, Supplier.life)
            }
            2 -> {
                adapter= QuoteAdapter(this, Supplier.childhood)
            }
            3 -> {
                adapter= QuoteAdapter(this, Supplier.friendship)
            }
            4 -> {
                adapter= QuoteAdapter(this, Supplier.love)
            }
            5 -> {
                adapter= QuoteAdapter(this, Supplier.motivation)
            }
            6 -> {
                adapter= QuoteAdapter(this, Supplier.parents)
            }
            7 -> {
                adapter= QuoteAdapter(this, Supplier.passion)
            }
            8 -> {
                adapter= QuoteAdapter(this, Supplier.romance)
            }
            9 -> {
                adapter= QuoteAdapter(this, Supplier.sad)
            }
            10 -> {
                adapter= QuoteAdapter(this, Supplier.women)
            }
        }
        recyclerViewQuotes.adapter=adapter
    }
}
