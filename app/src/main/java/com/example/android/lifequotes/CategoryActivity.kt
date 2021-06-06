package com.example.android.lifequotes

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.main_page.*
import kotlinx.android.synthetic.main.nav_header.*
import kotlinx.android.synthetic.main.nav_header.view.*

class CategoryActivity : AppCompatActivity(),NavigationView.OnNavigationItemSelectedListener{

    private var recyclerView: RecyclerView? = null
    private var categoryItem: ArrayList<Category>? = null
    private var gridLayoutManager: GridLayoutManager? = null
    private var categoryAdapter: CategoryAdapter? = null
    private lateinit var toolbar: Toolbar
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navView: NavigationView
    private lateinit var myRef: DatabaseReference
    private var userId: String? = null
    private val user = FirebaseAuth.getInstance().currentUser
    var categoryList: ArrayList<Category> = ArrayList()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_page)

        recyclerView = findViewById(R.id.category_recyclerView)


        categoryItem = ArrayList()
        categoryItem = setCategory()
        categoryAdapter= CategoryAdapter(this,categoryList){categoryId->
            val intent= Intent(this,QuoteDetailActivity::class.java)
            intent.putExtra("QUOTE_CATEGORY_ID",categoryId)
            startActivity(intent)
        }

        val column =calNoOfColumns(applicationContext)
        gridLayoutManager = GridLayoutManager(applicationContext, column, LinearLayoutManager.VERTICAL, false)
        recyclerView?.layoutManager = gridLayoutManager
        recyclerView?.setHasFixedSize(true)
       // categoryAdapter = CategoryAdapter(applicationContext, categoryItem!!)
        recyclerView?.adapter = categoryAdapter


        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        drawerLayout = findViewById(R.id.student_drawer_layout)
        navView = findViewById(R.id.nav_view)

        val toggle = ActionBarDrawerToggle(
            this, drawerLayout, toolbar, 0, 0
        )
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()
        navView.setNavigationItemSelectedListener(this)

        changeNavigationHeaderInfo()

    }

    private fun setCategory(): ArrayList<Category> {



        categoryList.add(Category(1,"Life",R.drawable.life))
        categoryList.add(Category(2,"Childhood",R.drawable.childhood))
        categoryList.add(Category(3,"Friendship",R.drawable.friendship))
        categoryList.add(Category(4,"Love",R.drawable.love))
        categoryList.add(Category(5,"Motivation",R.drawable.motivation))
        categoryList.add(Category(6,"Parents",R.drawable.parents))
        categoryList.add(Category(7,"Passion",R.drawable.passion))
        categoryList.add(Category(8,"Romance",R.drawable.romance))
        categoryList.add(Category(9,"Hard Times",R.drawable.sad))
        categoryList.add(Category(10,"Women Power",R.drawable.women))

        return categoryList

    }


    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.see_your_quotes -> {
                Toast.makeText(this, "my quotes", Toast.LENGTH_SHORT).show()
                 val intent = Intent(this,ShowMyQuote::class.java)
                startActivity(intent)
                 finish()
            }
            R.id.write_quote -> {
                Toast.makeText(this, "write your quote", Toast.LENGTH_SHORT).show()
                      val intent = Intent(this, OwnQuoteWriter::class.java)
                    startActivity(intent)
                  finish()
            }

            R.id.update_pass -> {
                Toast.makeText(this, "Update clicked", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, UpdatePasswordActivity::class.java)
                startActivity(intent)
                finish()
            }

            R.id.logout -> {
                Toast.makeText(this, "Sign out clicked", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            }
        }
        drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }

    //auto calculate no. of columns
    private fun calNoOfColumns(context: Context): Int {
        val displayMetrics = context.resources.displayMetrics
        val dpWidth = displayMetrics.widthPixels / displayMetrics.density
        return (dpWidth / 180).toInt()
    }
    private fun changeNavigationHeaderInfo() {
        val headerView = nav_view.getHeaderView(0)
        headerView.UserEmail.text = user?.email.toString() //retrieve email of user
        //retrieve name
        myRef = FirebaseDatabase.getInstance().getReference("profile")
        userId = user?.uid
        // User data change listener
        myRef.child(userId!!).addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val userInfo = dataSnapshot.getValue(ProfileModel::class.java)
                headerView.UsersName.text = userInfo?.name.toString()
                if(userInfo?.name.toString()== "null"){
                    headerView.UsersName.text=user?.displayName.toString()
                }
            }

            override fun onCancelled(error: DatabaseError) {
                // Failed to read value
                //  Log.e(ProfileFragment.TAG, "Failed to read user", error.toException())
            }
        })


    }
    private fun emailVerification(){
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Email Verification !")
        builder.setMessage("Verify Your Email to get update with us.")
        builder.setPositiveButton("YES"){ _, _ ->
            user?.sendEmailVerification()
                ?.addOnCompleteListener {
                    if (it.isSuccessful) {
                        Toast.makeText(this, "Verification email sent ", Toast.LENGTH_LONG).show()
                    } else {
                        Toast.makeText(this, "Failed to send verification email ", Toast.LENGTH_LONG).show()
                    }
                }
        }
        builder.setNegativeButton("CANCEL"){ _, _ ->
            Toast.makeText(this,"Email not Verified !",Toast.LENGTH_LONG).show()
        }
        builder.create()
        builder.show()
    }
}