package com.example.android.lifequotes;


import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.constraintlayout.widget.ConstraintLayout
import com.google.firebase.database.FirebaseDatabase


class NewQuoteAdapter(private val ctx: Context, private val layoutResId:Int, private val newQuoteList:List<WriteQuoteModel>)
    : ArrayAdapter<WriteQuoteModel>(ctx,layoutResId,newQuoteList){

    @SuppressLint("ViewHolder")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val layoutInflater:LayoutInflater= LayoutInflater.from(ctx)
        val view:View = layoutInflater.inflate(layoutResId,null)
        val quoteView: TextView =view.findViewById(R.id.textQuote)
        val share:ImageView=view.findViewById(R.id.share)
        val delete: ImageView =view.findViewById(R.id.delete)
        val layout:ConstraintLayout=view.findViewById(R.id.updateLayout)
        val quote = newQuoteList[position]
        quoteView.text=quote.myQuote

       delete.setOnClickListener {
            remove(quote)
        }
        layout.setOnClickListener {
            updateInfo(quote)
        }

        share.setOnClickListener {
            val intent= Intent()                                                                       //implicit intent
            intent.action= Intent.ACTION_SEND
            intent.putExtra(Intent.EXTRA_TEXT,quote.myQuote)
            intent.type="text/plain"
            ctx.startActivity(Intent.createChooser(intent,"Share to : "))
        }

        return view
    }
    @SuppressLint("InflateParams")
    private fun updateInfo(quote:WriteQuoteModel) {
        val builder= AlertDialog.Builder(ctx)
        val layoutInflater:LayoutInflater= LayoutInflater.from(ctx)
        builder.setTitle("Edit Your Quote")
        val view:View = layoutInflater.inflate(R.layout.edit_quote,null)
        val quoteUpdate:TextView=view.findViewById(R.id.edit_quote)
        builder.setView(view)

        builder.setPositiveButton("Update",object : DialogInterface.OnClickListener{
            override fun onClick(dialog: DialogInterface?, which: Int) {
                val upQuote= FirebaseDatabase.getInstance().getReference("quotes")
                val myQuote = quoteUpdate.text.toString().trim()
                if (myQuote.isEmpty()) {
                    quoteUpdate.error="This field can't be empty!"
                    quoteUpdate.requestFocus()
                    return
                }
                val mQuote=WriteQuoteModel(quote.id,myQuote)

                upQuote.child(mQuote.id).setValue(mQuote)
                Toast.makeText(ctx,"Updated successfully", Toast.LENGTH_SHORT).show()
            }

        })
        builder.setNegativeButton("No",object : DialogInterface.OnClickListener{
            override fun onClick(dialog: DialogInterface?, which: Int) {
                    Toast.makeText(ctx, "Quote remains as it is", Toast.LENGTH_SHORT).show()
            }
        })
        val alert=builder.create()
        alert.show()
    }

    private fun remove(quote:WriteQuoteModel) {

        val addedQuote = FirebaseDatabase.getInstance().getReference("quotes").child(quote.id)
        addedQuote.removeValue()
        Toast.makeText(ctx, "Quote is Removed", Toast.LENGTH_LONG).show()
    }
}