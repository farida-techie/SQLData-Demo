package com.malkinfo.addinformationsqldatabes

import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.malkinfo.addinformationsqldatabes.databes.Contacts
import com.malkinfo.addinformationsqldatabes.databes.SqliteDatabase
import com.malkinfo.addinformationsqldatabes.recyclerviews.ContactAdapter
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private lateinit var dataBase: SqliteDatabase
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        title = "KotlinApp"
        val contactView: RecyclerView = findViewById(R.id.myContactList)
        val linearLayoutManager = LinearLayoutManager(this)
        contactView.layoutManager = linearLayoutManager
        contactView.setHasFixedSize(true)
        dataBase = SqliteDatabase(this)
        val allContacts= dataBase.listContacts()
        if (allContacts.size > 0) {
            contactView.visibility = View.VISIBLE
            textV.visibility = View.GONE
            val mAdapter = ContactAdapter(this, allContacts)
            contactView.adapter = mAdapter
        }
        else {
            contactView.visibility = View.GONE
            textV.visibility = View.VISIBLE
            Toast.makeText(
                    this,
                    "There is no contact in the database. Start adding now",
                    Toast.LENGTH_LONG
            ).show()
        }
        val btnAdd: Button = findViewById(R.id.btnAdd)
        btnAdd.setOnClickListener { addTaskDialog() }
    }
    private fun addTaskDialog() {
        val inflater = LayoutInflater.from(this)
        val subView = inflater.inflate(R.layout.add_contacts, null)
        val nameField: EditText = subView.findViewById(R.id.enterName)
        val noField: EditText = subView.findViewById(R.id.enterPhoneNum)
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Add new CONTACT")
        builder.setView(subView)
        builder.create()
        builder.setPositiveButton("ADD CONTACT") { _, _ ->
            val name = nameField.text.toString()
            val phoneNum = noField.text.toString()
            if (TextUtils.isEmpty(name)) {
                Toast.makeText(
                        this@MainActivity,
                        "Something went wrong. Check your input values",
                        Toast.LENGTH_LONG
                ).show()
            }
            else {
                val newContact = Contacts(name, phoneNum)
                dataBase.addContacts(newContact)
                finish()
                startActivity(intent)
            }
        }
        builder.setNegativeButton("CANCEL") { _, _ -> Toast.makeText(this@MainActivity, "Task cancelled",
                Toast.LENGTH_LONG).show()}
        builder.show()
    }
    override fun onDestroy() {
        super.onDestroy()
        dataBase.close()
    }
}