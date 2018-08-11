package com.example.nikhil.mynotes

import android.app.SearchManager
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.*
import android.widget.ArrayAdapter
import android.widget.BaseAdapter
import android.widget.SearchView
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.ticket.*
import kotlinx.android.synthetic.main.ticket.view.*

class MainActivity : AppCompatActivity() {
    var lisofnotes=ArrayList<Note>()
    var ankush=1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //lisofnotes.add(Note(1,"meet ankush","meet ankushmeet ankushmeet ankushmeet ankushmeet ankushmeet ankush"))
        //lisofnotes.add(Note(2,"meet ankush","meet ankushmeet ankushmeet ankushmeet ankushmeet ankushmeet ankush"))
        //lisofnotes.add(Note(3,"meet ankush","meet ankushmeet ankushmeet ankushmeet ankushmeet ankushmeet ankush"))
       if(ankush==1){
           Toast.makeText(this@MainActivity,"#@cker's Studio App By Ankush Shrivastava",Toast.LENGTH_SHORT).show()
           ankush=0
       }
        LoadQuery("%")
    }

    override fun onResume() {
        lisofnotes.clear()
        LoadQuery("%")
        super.onResume()
    }

    fun LoadQuery(title:String){
        lisofnotes.clear()

        var dbManager=dbManager(this@MainActivity)
        val projection= arrayOf("ID","Title","Description")
        val selectionArgs= arrayOf(title)
        val cursor=dbManager.Query(projection,"Title like ?",selectionArgs,"Title")

        if(cursor.moveToFirst()){

            do{

                val ID=cursor.getInt(cursor.getColumnIndex("ID"))
                val title=cursor.getString(cursor.getColumnIndex("Title"))
                val description=cursor.getString(cursor.getColumnIndex("Description"))
                lisofnotes.add(Note(ID,title,description))
            }while(cursor.moveToNext())
        }
        var listofnotes_adapter=MyNotesAdapter(lisofnotes)
        list_View.adapter=listofnotes_adapter
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {


        menuInflater.inflate(R.menu.main_menu,menu)
        var search_View= menu!!.findItem(R.id.menu_bar_search).actionView as SearchView
        var search_manager=getSystemService(Context.SEARCH_SERVICE) as SearchManager
        search_View.setSearchableInfo(search_manager.getSearchableInfo(componentName))
        search_View.setOnQueryTextListener(object: SearchView.OnQueryTextListener {

            override fun onQueryTextSubmit(p0: String?): Boolean {
            Toast.makeText(this@MainActivity,p0,Toast.LENGTH_SHORT).show()
                LoadQuery("%"+ p0 +"%")
            return false
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                LoadQuery("%"+ p0 +"%")
            return false
            }

        })

        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item != null) {
            when(item.itemId){

                R.id.add_note_menu->{
                    var intent=Intent(this@MainActivity,Add_notes::class.java)
                    startActivity(intent)

                }
                R.id.menu_bar_search->{

                }
            }
        }
        return super.onOptionsItemSelected(item)
    }

    inner class MyNotesAdapter:BaseAdapter{
       var listNotesAdapter=ArrayList<Note>()
       constructor(listNotesAdapter: ArrayList<Note>):super(){
           this.listNotesAdapter=listNotesAdapter
       }


       override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {
           var myView=layoutInflater.inflate(R.layout.ticket,null)
           var myNote=listNotesAdapter[p0]
           myView.tv_title.text=myNote.noteName
           myView.tv_content.text=myNote.noteDes
           myView.btn_delete.setOnClickListener(View.OnClickListener {

               var dbManager=dbManager(this@MainActivity)
               val selectionArgs= arrayOf(myNote.noteId.toString())
               dbManager.Delete("ID=?",selectionArgs)
               lisofnotes.clear()
               LoadQuery("%")

           })

           myView.btn_edit.setOnClickListener(View.OnClickListener {


               GoToUpdate(myNote)

           })

       return myView
       }

       override fun getItem(p0: Int): Any {
              return listNotesAdapter[p0]
        }

       override fun getItemId(p0: Int): Long {
         return p0.toLong()
       }

       override fun getCount(): Int {
          return listNotesAdapter.size
       }


   }
    fun GoToUpdate(note:Note){

        var intent=Intent(this@MainActivity,Add_notes::class.java)
           intent.putExtra("ID",note.noteId)
           intent.putExtra("name",note.noteName)
           intent.putExtra("des",note.noteDes)

        startActivity(intent)



    }
}

//#@cker's Studio'
//By Ankush Shrivastava
