package com.example.onitama

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    lateinit var cards:ArrayList<CardClass>
    lateinit var cardPlayer: ArrayList<CardClass>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        try {
            this.supportActionBar!!.hide()
        } catch (e: NullPointerException) {
            Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show()
        }
        setContentView(R.layout.activity_main)
        initCard()
        cardPlayer = ArrayList()
//        Toast.makeText(this, cards[0].cardMovement.toString(), Toast.LENGTH_SHORT).show()
    }
    // inisiasi card onitama
    fun initCard(){
        cards = ArrayList()
        var movement = arrayListOf(2,17)
        var card = CardClass("TIGER",movement)
        cards.add(card)
        movement = arrayListOf(7,10,14)
        card = CardClass("CRAB",movement)
        cards.add(card)
        movement = arrayListOf(6,8,16,18)
        card = CardClass("MONKEY",movement)
        cards.add(card)
        movement = arrayListOf(7,16,18)
        card = CardClass("CRANE",movement)
        cards.add(card)

        movement = arrayListOf(5,9,16,18)
        card = CardClass("DRAGON",movement)
        cards.add(card)
        movement = arrayListOf(6,8,11,13)
        card = CardClass("ELEPHANT",movement)
        cards.add(card)
        movement = arrayListOf(6,8,17)
        card = CardClass("MANTIS",movement)
        cards.add(card)
        movement = arrayListOf(7,11,13)
        card = CardClass("BOAR",movement)
        cards.add(card)

        movement = arrayListOf(6,10,18)
        card = CardClass("FROG",movement)
        cards.add(card)
        movement = arrayListOf(6,11,13,18)
        card = CardClass("GOOSE",movement)
        cards.add(card)
        movement = arrayListOf(7,11,17)
        card = CardClass("HORSE",movement)
        cards.add(card)
        movement = arrayListOf(6,13,16)
        card = CardClass("EEL",movement)
        cards.add(card)

        movement = arrayListOf(8,14,16)
        card = CardClass("RABBIT",movement)
        cards.add(card)
        movement = arrayListOf(8,11,13,16)
        card = CardClass("ROOSTER",movement)
        cards.add(card)
        movement = arrayListOf(7,13,17)
        card = CardClass("OX",movement)
        cards.add(card)
        movement = arrayListOf(8,11,18)
        card = CardClass("COBRA",movement)
        cards.add(card)
    }
    // Random 5 Card For Player
    fun randomPlayerCard(){
        
    }
    fun playerCardCheck(){

    }
}