package com.example.nikhil.mynotes

import android.app.ActivityManager
import android.content.ContentValues
import android.content.Intent
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_add_notes.*
class Add_notes : AppCompatActivity() {

    var id=-1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_notes)


       try {
           var bundle=intent.extras
           id=bundle.getInt("ID",0)
           if(id!=-1) {

               et_title.setText(bundle.getString("name").toString())
               et_des.setText(bundle.getString("des").toString())
           }
       }catch (ex:Exception){}

    }

    fun btn_click(view: View){

     var dbManager=dbManager(this)
        var values= ContentValues()
        values.put("Title",et_title.text.toString())
        values.put("Description",et_des.text.toString())


        if(id==-1) {
            var ID=dbManager.Insert(values)
            if (ID > 0) {
                Toast.makeText(this@Add_notes, "Note Added", Toast.LENGTH_SHORT).show()
                finish()

            } else {

                Toast.makeText(this@Add_notes, "cannot Add Note", Toast.LENGTH_SHORT).show()
            }
        }
        else{

            var selectionArgs= arrayOf(id.toString())
            MainActivity().lisofnotes.clear()
            var ID=dbManager.Update(values,"ID=?",selectionArgs)

            if (ID > 0) {
                Toast.makeText(this@Add_notes, "Note Added", Toast.LENGTH_SHORT).show()
                              finish()
            } else {

                Toast.makeText(this@Add_notes, "cannot Add Note", Toast.LENGTH_SHORT).show()
            }

        }


    }
}
