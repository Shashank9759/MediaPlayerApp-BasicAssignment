package com.studies.basicassigment.Activities

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.studies.basicassigment.Adapters.videoAdapter
import com.studies.basicassigment.Models.videomodel
import com.studies.basicassigment.R
import com.studies.basicassigment.databinding.ActivityMainBinding
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.postgrest.Postgrest
import io.github.jan.supabase.postgrest.postgrest
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {
    val binding by lazy{
        ActivityMainBinding.inflate(layoutInflater)
    }
    lateinit var adapter:videoAdapter
    lateinit var users2:List<videomodel>
    lateinit var users:List<videomodel>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
         users= listOf<videomodel>()
        adapter=videoAdapter(this@MainActivity,users)
        binding.mainRV.adapter=adapter


        val supabase = createSupabaseClient(
            supabaseUrl = "https://munyrniumvvumxrokszg.supabase.co",
            supabaseKey = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6Im11bnlybml1bXZ2dW14cm9rc3pnIiwicm9sZSI6ImFub24iLCJpYXQiOjE3MTUyNTU5NjAsImV4cCI6MjAzMDgzMTk2MH0.NFG-_ikStDncluegPlHi9bcZu7MDphwh7jnAwxCgxBQ"
        ) {
            this.install(Postgrest)
        }
        CoroutineScope(Dispatchers.IO).launch {
             users2=supabase.postgrest["user"].select().decodeList<videomodel>()
            withContext(Dispatchers.Main) {
               adapter.ondatachange(users2)


            }
        }

        binding.search.setOnClickListener {

            searchQuery(binding.searchedittext.text.toString())

        }
    }

    private fun searchQuery(query: String) {
        val capsQuery=query.toLowerCase()
        val templist= mutableListOf<videomodel>()
        if(users2!=null){
            for (i in users2){
                val capstitle= i.title.toLowerCase()
                if(capstitle.contains(capsQuery)){
                    templist.add(i)
                }
            }
            adapter.ondatachange(templist)
        }


    }
}