package com.example.android.lifequotes;

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import com.example.android.lifequotes.WriteQuoteModel
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.my_quote_list_item.*
import kotlinx.android.synthetic.main.quote_detail_item_list.*
import kotlinx.android.synthetic.main.quote_detail_item_list.share
import java.util.Collections.list

class ShowMyQuote : AppCompatActivity() {
    private lateinit var quoteList:MutableList<WriteQuoteModel>
    private lateinit var back: Button
    private lateinit var myRef:DatabaseReference
    private lateinit var listView: ListView
    @SuppressLint("WrongConstant")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.show_my_quotes)

        listView=findViewById(R.id.list)

        quoteList= mutableListOf()

        back = findViewById(R.id.back)
        back.setOnClickListener {
            val intent = Intent(this, CategoryActivity::class.java)
            startActivity(intent)
            finish()

        }


        myRef = FirebaseDatabase.getInstance().getReference("quotes") //at time to read value from database


        myRef.addValueEventListener(object:ValueEventListener{
            override fun onCancelled(p0: DatabaseError) {
                TODO("Not yet implemented")
            }

            override fun onDataChange(p0: DataSnapshot) {
                if(p0.exists()){
                    quoteList.clear()
                    for(i in p0.children){
                        val plan=i.getValue(WriteQuoteModel::class.java)
                        quoteList.add(plan!!)


                    }
                    val adapter=NewQuoteAdapter(this@ShowMyQuote, R.layout.my_quote_list_item,quoteList)
                    listView.adapter=adapter
                }
            }

        })

    }


}