package com.example.onitama

import android.graphics.Color
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    lateinit var cards:ArrayList<Card> // List of all card (16 Type of Card)
    lateinit var playing_card: ArrayList<Card> // 5 Card that played in a game

    // GAME BOARD
    lateinit var board:ArrayList<ArrayList<Button>>

    // BOARD UNIT
    lateinit var Player_Pieces:ArrayList<Piece> // Player's 5 pieces (4 pawn, 1 King)
    lateinit var AI_Pieces:ArrayList<Piece> // AI's 5 pieces (4 pawn, 1 King)

    // GAMEPLAY RELATED
    var isUnitSelected = false
    var selectedPiece = Piece(-1,-1,false)

    var isCardSelected = false
    var selectedCard = Card("", intArrayOf(), intArrayOf())
    var selectedCardIdx = -1

    var playerTurn = true;

    var xMovePossible = ArrayList<Int>()
    var yMovePossible = ArrayList<Int>()

    var idx = mutableListOf<Int>(0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // HIDE DEFAULT ACTION BAR (Application Header)
        try {
            this.supportActionBar!!.hide()
        } catch (e: NullPointerException) {
            Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show()
        }
        setContentView(R.layout.activity_main)

        // CARD RELATED INITIALIZATION FUNCTION
        initCard()
        randomPlayerCard()
        initPanelCard()

        // BOARD AND PIECES INITIALIZATION FUNCTION
        initBoard()
        initPiece()

        // CARD ON CLICK LISTENER
        cardEnemy1.setOnClickListener {
            if(isUnitSelected) {
                if (isCardSelected) {
                    clearPossibleMove()
                }
                selectedCard = playing_card[0]
                selectedCardIdx = 0
                cardEnemy1.setBackgroundColor(Color.GRAY)
                cardEnemy2.setBackgroundColor(Color.TRANSPARENT)
                isCardSelected = true

                generatePossibleMove()
            }
        }
        cardEnemy2.setOnClickListener {
            if(isUnitSelected){
                if(isCardSelected){ clearPossibleMove() }

                selectedCard = playing_card[1]
                selectedCardIdx = 1

                cardEnemy2.setBackgroundColor(Color.GRAY)
                cardEnemy1.setBackgroundColor(Color.TRANSPARENT)
                isCardSelected = true

                generatePossibleMove()
            }
        }

        cardPlayer1.setOnClickListener {
            if(isUnitSelected){
                if(isCardSelected){ clearPossibleMove() }
                selectedCard = playing_card[3]
                selectedCardIdx = 3
                cardPlayer1.setBackgroundColor(Color.GRAY)
                cardPlayer2.setBackgroundColor(Color.TRANSPARENT)
                isCardSelected = true

                generatePossibleMove()
            }else{
                Toast.makeText(this, "Please select your unit first!", Toast.LENGTH_SHORT).show()
            }
        }
        cardPlayer2.setOnClickListener {
            if(isUnitSelected){
                if(isCardSelected){ clearPossibleMove() }
                selectedCard = playing_card[4]
                selectedCardIdx = 4
                cardPlayer2.setBackgroundColor(Color.GRAY)
                cardPlayer1.setBackgroundColor(Color.TRANSPARENT)
                isCardSelected = true

                generatePossibleMove()
            }else{
                Toast.makeText(this, "Please select your unit first!", Toast.LENGTH_SHORT).show()
            }
        }

    }

    // CARD RELATED FUNCTION
    fun initCard(){
        /*
            This method Initiate 16 Onitama's Card
            Card Data will be saved into a CardClass with constructor
            consist of Card Name, Card Movement and Card Color
        */

        cards = ArrayList()

        var xMove = intArrayOf(0,0)
        var yMove = intArrayOf(1,-2)

        // 1. Tiger : [0,1],[0,-2]
        createCard("Tiger",xMove,yMove)

        // 2. Crab : [-2,0],[2,0],[0,-1]
        xMove = intArrayOf(-2,2,0)
        yMove = intArrayOf(0,0,-1)
        createCard("Crab",xMove,yMove)

        // 3. Monkey : [-1,-1],[-1,1],[1,-1],[1,1]
        xMove = intArrayOf(-1,-1,1,1)
        yMove = intArrayOf(-1,1,-1,1)
        createCard("Monkey",xMove,yMove)

        // 4. Crane : [0,-1],[-1,1],[1,1]
        xMove = intArrayOf(0,-1,1)
        yMove = intArrayOf(-1,1,1)
        createCard("Crane",xMove,yMove)

        // 5. Dragon : [-2,-1],[-1,1],[1,1],[2,-1]
        xMove = intArrayOf(-2,-1,1,2)
        yMove = intArrayOf(-1,1,1,-1)
        createCard("Dragon",xMove,yMove)

        // 6. Elephant : [-1,-1],[-1,0],[1,-1],[1,0]
        xMove = intArrayOf(-1,-1,1,1)
        yMove = intArrayOf(-1,0,-1,0)
        createCard("Crab",xMove,yMove)

        // 7. Mantis : [-1,-1],[0,1],[1,-1]
        xMove = intArrayOf(-1,0,1)
        yMove = intArrayOf(-1,1,-1)
        createCard("Mantis",xMove,yMove)

        // 8. Boar : [-1,0],[0,-1],[1,0]
        xMove = intArrayOf(-1,0,1)
        yMove = intArrayOf(0,-1,0)
        createCard("Boar",xMove,yMove)

        // 9. Frog : [-2,0],[-1,-1],[1,1]
        xMove = intArrayOf(-2,-1,1)
        yMove = intArrayOf(0,-1,1)
        createCard("Frog",xMove,yMove)

        // 10. Goose : [-1,-1],[-1,0],[1,0],[1,1]
        xMove = intArrayOf(-1,-1,1,1)
        yMove = intArrayOf(-1,-1,0,1)
        createCard("Goose",xMove,yMove)

        // 11. Horse : [0,-1],[-1,0],[0,1]
        xMove = intArrayOf(0,-1,0)
        yMove = intArrayOf(-1,0,1)
        createCard("Horse",xMove,yMove)

        // 12. Eel : [-1,-1],[1,0],[-1,1]
        xMove = intArrayOf(-1,1,-1)
        yMove = intArrayOf(-1,0,1)
        createCard("Eel",xMove,yMove)

        // 13. Rabbit : [-1,1],[1,-1],[2,0]
        xMove = intArrayOf(-1,1,2)
        yMove = intArrayOf(1,-1,0)
        createCard("Rabbit",xMove,yMove)

        // 14. Rooster : [-1,0],[-1,-1],[1,0],[1,-1]
        xMove = intArrayOf(-1,-1,1,1)
        yMove = intArrayOf(0,-1,0,-1)
        createCard("Rooster",xMove,yMove)

        // 15. Ox : [0,-1],[1,0],[0,1]
        xMove = intArrayOf(0,1,0)
        yMove = intArrayOf(-1,0,1)
        createCard("Ox",xMove,yMove)

        // 16. Cobra : [-1,0],[1,-1],[1,1]
        xMove = intArrayOf(-1,1,1)
        yMove = intArrayOf(0,-1,1)
        createCard("Cobra",xMove,yMove)
    }
    fun createCard(cardName:String,xMove:IntArray,yMove:IntArray){
        var card = Card(cardName,xMove,yMove)
        cards.add(card)
    }
    fun randomPlayerCard(){
        // RANDOM 5 CARD TO PLAY WITH

        playing_card = ArrayList()

        idx.shuffle()

        for(i in 0 until 5){
            playing_card.add(cards[idx[i]])
        }
    }
    fun initPanelCard(){
        // Initiate Card Panel
        for (i in 0..playing_card.size-1){
            tvSelect(i).text = playing_card[i].cardName
            cardSelect(i).setImageDrawable(imageSelect(playing_card[i].cardName))
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

    // BOARD & PIECE RELATED FUNCTION
    fun initBoard(){
        board = ArrayList()
        var startIdx = 0
        for(i in 0..4){
            var newRow = ArrayList<Button>()
            for (j in 1..5){
                var id = startIdx+j
                var btnID = "btn$id"
                val resourcesID = this.resources.getIdentifier(btnID, "id", packageName)
                val newButton = findViewById<Button>(resourcesID)
                newButton.setOnClickListener(){
                    var x = j-1
                    var y = i
                    boardClicked(x,y)
                }
                newRow.add(findViewById(resourcesID))
            }
            board.add(newRow)
            startIdx+=5
        }
    }
    fun initPiece(){
        Player_Pieces = ArrayList()
        AI_Pieces = ArrayList()

        var yPiece = 0
        for (i in 0..1){
            for (j in 0..4){
                var isKing = false
                var role = "P"
                if(j == 2){
                    isKing = true
                    role = "K"
                }

                var new_piece = Piece(j,yPiece,isKing)
                if(yPiece == 0){
                    AI_Pieces.add(new_piece)
                    board[new_piece.y][new_piece.x].setBackgroundColor(Color.RED)
                }
                else{
                    Player_Pieces.add(new_piece)
                    board[new_piece.y][new_piece.x].setBackgroundColor(Color.GREEN)
                }
                board[new_piece.y][new_piece.x].setText(role)
            }
            yPiece = 4
        }
    }
    fun boardClicked(x:Int,y:Int){
        if(!isUnitSelected){
            // IF PLAYER'S UNIT HASN'T BEEN SELECTED YET
            for (i in 0..Player_Pieces.size-1){
                if(Player_Pieces[i].x == x && Player_Pieces[i].y == y){
                    // SELECTED PIECE IS PLAYER'S PIECE
                    board[y][x].setBackgroundColor(Color.YELLOW)
                    selectedPiece = Player_Pieces[i]
                    Toast.makeText(this, "${selectedPiece.getPieceInfo()}", Toast.LENGTH_SHORT).show()
                    isUnitSelected = true
                    break
                }
            }

        }
        else{
            // IF PLAYER'S UNIT ALREADY SELECTED
            if(x!=selectedPiece.x || y!=selectedPiece.y){
                // IF SELECTED TILES ISN'T CURRENT POSITION
                if(isCardSelected){
                    /*
                        IF THE CARD ALREADY SELECTED
                        CHECK IS THE MOVE LEGAL, LEGAL MOVE MUST :
                            -> not attacking teamates
                            -> match moveset of the card selected

                        IF MOVE IS LEGAL THEN MOVE SELECTED PIECE TO DESTINATION
                    */
                    var isMoveLegal = false
                    for(i in 0..xMovePossible.size-1){
                        if(x == xMovePossible[i] && y == yMovePossible[i]){
                            isMoveLegal = true
                        }
                    }

                    if(isMoveLegal){
                        movePiece(x,y)
                        rotateCard()
                        clearSelectedPiece()
                        playerTurn = !playerTurn
                        Toast.makeText(this, "${playerTurn.toString()}", Toast.LENGTH_SHORT).show()
                        if(!playerTurn){
                            Toast.makeText(this, "AI has moved it's piece! Move your piece now!", Toast.LENGTH_SHORT).show()
                            AI_Move()
                        }

                    }else{ Toast.makeText(this, "This move is Illegal!", Toast.LENGTH_SHORT).show() }
                }else{ Toast.makeText(this, "Please select card first!", Toast.LENGTH_SHORT).show() }
            }
            else{
                // CANCEL SELECTED PAWN
                board[y][x].setBackgroundColor(Color.GREEN)
//                rotateCard()
                clearPossibleMove()
                clearSelectedPiece()
            }
        }
    }

    fun clearSelectedPiece(){
        isUnitSelected = false
        selectedPiece = Piece(-1,-1,false)

        isCardSelected = false
        selectedCard = Card("", intArrayOf(), intArrayOf())
        cardPlayer1.setBackgroundColor(Color.TRANSPARENT)
        cardPlayer2.setBackgroundColor(Color.TRANSPARENT)

        cardEnemy1.setBackgroundColor(Color.TRANSPARENT)
        cardEnemy2.setBackgroundColor(Color.TRANSPARENT)
    }
    fun clearPossibleMove(){
        for(i in 0..xMovePossible.size-1){
            board[yMovePossible[i]][xMovePossible[i]].setBackgroundColor(Color.parseColor("#AA9772"))
            board[yMovePossible[i]][xMovePossible[i]].setText("")
        }
        xMovePossible.clear()
        yMovePossible.clear()
    }

    fun generatePossibleMove(){
        for(i in 0..selectedCard.xMove.size-1){
            try {
                var possible_y = selectedPiece.y+selectedCard.yMove[i]
                var possible_x = selectedPiece.x+selectedCard.xMove[i]
                var isMovePossible = true

                // LOOP TO CHECK IF MOVEMENT OF THE CARD IS ATTACKING TEAMATES POSITION
                if(playerTurn){
                    for(j in 0..Player_Pieces.size-1){
                        if(possible_x == Player_Pieces[j].x && possible_y == Player_Pieces[j].y){
                            isMovePossible = false
                        }
                    }
                }
                else{
                    possible_y = selectedPiece.y+(selectedCard.yMove[i]*-1)
                    possible_x = selectedPiece.x+(selectedCard.xMove[i]*-1)
                    for(j in 0..AI_Pieces.size-1){
                        if(possible_x == AI_Pieces[j].x && possible_y == AI_Pieces[j].y){
                            isMovePossible = false
                        }
                    }
                }

                // IF MOVE IS POSSIBLE GENERATE MOVEMENT HELPER/HINT ON BOARD
                if(isMovePossible){
                    board[possible_y][possible_x].setBackgroundColor(Color.BLUE)
                    xMovePossible.add(possible_x)
                    yMovePossible.add(possible_y)
                }
            } catch (e: IndexOutOfBoundsException) { }
        }
    }
    fun movePiece(x:Int,y:Int){
        clearPossibleMove()

        if(playerTurn){
            // PLAYER MOVES
            board[y][x].setBackgroundColor(Color.GREEN)
        }else{
            // AI MOVES
            board[y][x].setBackgroundColor(Color.RED)
        }
        if(!selectedPiece.isKing){ board[y][x].setText("P") }
        else{ board[y][x].setText("K") }

        // CLEAR OLD POSITION
        board[selectedPiece.y][selectedPiece.x].setBackgroundColor(Color.parseColor("#AA9772"))
        board[selectedPiece.y][selectedPiece.x].setText("")

        selectedPiece.x = x
        selectedPiece.y = y
    }
    fun rotateCard(){
        val tempCard = playing_card[2]
        playing_card[2] = playing_card[selectedCardIdx]
        playing_card[selectedCardIdx] = tempCard
        initPanelCard()
    }

    fun AI_Move(){
        //PLY 1
        if(!playerTurn){
            for (k in 0 until AI_Pieces.size - 1){
                if(k == 0) {
                    selectedPiece = AI_Pieces[k]
                    isUnitSelected = true
                    // TRACE EVERY AI CARD

                    // SECOND AI CARD
                    cardEnemy2.performClick()
                    if(xMovePossible.size > 0){
                        boardClicked(
                            (xMovePossible[0]),
                            (yMovePossible[0])
                        )
//                        for (j in 0 until playing_card[1].xMove.size - 1) {
//                            // MOVE BASED ON GENERATED MOVE
//
//                        }
                    }
                    // FIRST AI CARD
                }
            }
        }

    }
}
