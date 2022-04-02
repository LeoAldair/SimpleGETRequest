package com.example.methodget

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.Response
import com.android.volley.toolbox.Volley
import com.example.methodget.databinding.ActivityMainBinding
import org.json.JSONObject

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var queue: RequestQueue
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        queue = Volley.newRequestQueue(this)

        binding.btnUpdatePokemon.setOnClickListener {
            val cantidad = Integer.parseInt(binding.etPokemonAmount.text.toString())
            getPokemonList(cantidad)
        }


    }

    fun getPokemonList(ListAmount: Int){
        val url = "https://pokeapi.co/api/v2/pokemon/?limit=${ListAmount}"

        val jsonRequest = JsonObjectRequest(url, Response.Listener<JSONObject>{response ->
            Log.i("JSONRESPONSE", response.toString())
            val pokemonData = response.getJSONArray("results")
            binding.rvPokemonEntries.adapter = MainAdapter(pokemonData)
        },
        Response.ErrorListener { error ->
            Log.w("JSONRESPONSE", error.message as String)
        })

        queue.add(jsonRequest)
    }

    override fun onStop() {
        super.onStop()
        queue.cancelAll("Stopped")
    }
}