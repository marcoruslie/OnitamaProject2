package com.example.onitama

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.random.Random

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
        randomPlayerCard()
        initPanelCard()
        cardEnemy1.setOnClickListener {

        }
        cardEnemy2.setOnClickListener {

        }
        cardPlayer1.setOnClickListener {

        }
        cardPlayer2.setOnClickListener {

        }
//        Toast.makeText(this, cards[0].cardMovement.toString(), Toast.LENGTH_SHORT).show()
    }
    // inisiasi card onitama
    fun initCard(){
        cards = ArrayList()
        var movement = arrayListOf(2,17)
        var card = CardClass("Tiger",movement)
        cards.add(card)
        movement = arrayListOf(7,10,14)
        card = CardClass("Crab",movement)
        cards.add(card)
        movement = arrayListOf(6,8,16,18)
        card = CardClass("Monkey",movement)
        cards.add(card)
        movement = arrayListOf(7,16,18)
        card = CardClass("Crane",movement)
        cards.add(card)

        movement = arrayListOf(5,9,16,18)
        card = CardClass("Dragon",movement)
        cards.add(card)
        movement = arrayListOf(6,8,11,13)
        card = CardClass("Elephant",movement)
        cards.add(card)
        movement = arrayListOf(6,8,17)
        card = CardClass("Mantis",movement)
        cards.add(card)
        movement = arrayListOf(7,11,13)
        card = CardClass("Boar",movement)
        cards.add(card)

        movement = arrayListOf(6,10,18)
        card = CardClass("Frog",movement)
        cards.add(card)
        movement = arrayListOf(6,11,13,18)
        card = CardClass("Goose",movement)
        cards.add(card)
        movement = arrayListOf(7,11,17)
        card = CardClass("Horse",movement)
        cards.add(card)
        movement = arrayListOf(6,13,16)
        card = CardClass("Eel",movement)
        cards.add(card)

        movement = arrayListOf(8,14,16)
        card = CardClass("Rabbit",movement)
        cards.add(card)
        movement = arrayListOf(8,11,13,16)
        card = CardClass("Rooster",movement)
        cards.add(card)
        movement = arrayListOf(7,13,17)
        card = CardClass("Ox",movement)
        cards.add(card)
        movement = arrayListOf(8,11,18)
        card = CardClass("Cobra",movement)
        cards.add(card)
    }
    // Random 5 Card For Player
    fun randomPlayerCard(){
        cardPlayer = ArrayList()
        for(i in 0..4){
            var cek = true

            var idx : Int
            do {
                idx = (0..15).shuffled().last()
                for (card in cardPlayer){
                    if(card.cardName.equals(cards[idx].cardName)){
                        cek = false
                    }
                }
                if(cek){
                    cardPlayer.add(cards[idx])
                }
            }while(!cek)
        }
    }
    // Inisiasi card panel
    fun initPanelCard(){
        for (i in 0..cardPlayer.size-1){
            tvSelect(i).text = cardPlayer[i].cardName
            cardSelect(i).setImageDrawable(imageSelect(cardPlayer[i].cardName))
        }
    }
    fun cardSelect(i:Int):ImageView{
        val tileID = "imageView$i"
        val resourceID = this.resources.getIdentifier(tileID, "id", packageName)
        val btn: ImageView = findViewById(resourceID)
        return btn
    }

    fun tvSelect(i:Int):TextView{
        val tileID = "tvCard$i"
        val resourceID = this.resources.getIdentifier(tileID, "id", packageName)
        val tv: TextView = findViewById(resourceID)
        return tv
    }

    fun imageSelect(name:String): Drawable {
        val tileID = name
        val resourceID = this.resources.getIdentifier(tileID.lowercase(), "drawable", packageName)
        val btn: Drawable = resources.getDrawable(resourceID)
        return btn
    }
}
