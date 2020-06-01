package com.example.android.lifequotes

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.quote_detail_item_list.view.*


class QuoteAdapter(val context: Context, val list:List<Quote>):
    RecyclerView.Adapter<QuoteAdapter.MyHolderView>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolderView {
        val view = LayoutInflater.from(context).inflate(R.layout.quote_detail_item_list, parent, false)
        return MyHolderView(view)

    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: MyHolderView, position: Int) {
        val quote=list[position]
        holder.setData(quote,position)
        AnimationHelper.animate(holder.itemView)
    }
    inner class MyHolderView(itemView: View): RecyclerView.ViewHolder(itemView){
        private var currentList: Quote?=null
        private var currentPosition :Int =0
        init{

            itemView.share.setOnClickListener {
                currentList?.let{
                    val msg: String = currentList!!.quotesLine
                    val intent= Intent()                                                                       //implicit intent
                    intent.action= Intent.ACTION_SEND
                    intent.putExtra(Intent.EXTRA_TEXT,msg)
                    intent.type="text/plain"
                    context.startActivity(Intent.createChooser(intent,"Share to : "))
                }

            }
        }

       fun setData(quote: Quote?, pos:Int){
            quote?.let {
                itemView.textViewQuote.text = quote!!.quotesLine
            }
            currentList=quote
            currentPosition=pos

        }


    }


}