package com.example.android.lifequotes

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

/* interface CallbackInterface {
    fun passDataCallback(name:String,message: String)
}*/
class CategoryAdapter(var context: Context, var arrayList: ArrayList<Category>,val onItemClick:(Int)->Unit) :
    RecyclerView.Adapter<CategoryAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val viewHolder = LayoutInflater.from(parent.context).inflate(R.layout.category_list_item, parent, false)
        val myViewHolder=MyViewHolder(viewHolder,onItemClick)
        return myViewHolder
    }

    override fun getItemCount(): Int {
        return arrayList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        holder!!.bindData(arrayList[position],context)

    }

    inner class MyViewHolder(itemView: View,val onItemClick: (Int) -> Unit) : RecyclerView.ViewHolder(itemView) {

        var categoryImage = itemView.findViewById<ImageView>(R.id.categoryImage)!!
        var categoryName = itemView.findViewById<TextView>(R.id.categoryName)!!

        fun bindData(category: Category,context: Context){
            categoryImage.setImageResource(category.image)
            categoryName.text= category.name
            //   itemView.setOnClickListener {
            //  Toast.makeText(context,"You Clicked on ${category.name}",Toast.LENGTH_LONG).show()

            //     val intent=Intent(context,QuoteDetailsActivity::class.java)
            //      context.startActivity(intent)
            // }
            itemView.setOnClickListener {
                onItemClick(category.id)
            }

        }




    }

}