package com.list.montyhallsim


import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import kotlin.math.roundToInt
import kotlin.random.Random.Default.nextInt

private var wins = 0
private var losses = 0
private var percentage = 0.00

private var gameStage: Int = 0
private val TAG = MainActivity::class.java.simpleName

private var doorPrize: Int = 0 // 0 null, 1 2 or 3 is door
private var doorPicked: Int = 0 // 0 null, 1 2 or 3 is door
private var doorOpened: Int = 0 // 0 null, 1 2 or 3 is door

private var stay: Int = 2 // 0 false 1 true 2 null
private var switch: Int = 2 // 0 false 1 true 2 null


class MainActivity : AppCompatActivity() {

    private lateinit var gameScoreTextView: TextView
    private lateinit var lossScoreTextView: TextView
    private lateinit var winPercentTextView: TextView
    private lateinit var messageTextView: TextView
    private lateinit var switchStayMessage: TextView
    private lateinit var door1arrow: ImageView
    private lateinit var door2arrow: ImageView
    private lateinit var door3arrow: ImageView
    private lateinit var switchButton : Button
    private lateinit var stayButton: Button
    private lateinit var door1button: Button
    private lateinit var door2button: Button
    private lateinit var door3button: Button
    private lateinit var newgamebutton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Log.d(TAG, "\nonCreate called. \ngameStage is $gameStage. \ndoorPrize is $doorPrize. \ndoorPicked is $doorPicked. \ndoorOpened is $doorOpened. \nwins is $wins. \nlosses is $losses")

        gameScoreTextView = findViewById(R.id.game_score_text_view)
        lossScoreTextView = findViewById(R.id.loss_score_text_view)
        winPercentTextView = findViewById(R.id.win_percent_text_view)
        messageTextView = findViewById(R.id.message)
        newgamebutton = findViewById(R.id.new_game)
        switchStayMessage=findViewById(R.id.switchstaymessage)
        switchButton = findViewById(R.id.switch_button)
        stayButton = findViewById(R.id.stay_button)

        door1arrow = findViewById(R.id.door1_arrow)
        door2arrow = findViewById(R.id.door2_arrow)
        door3arrow = findViewById(R.id.door3_arrow)

        door1button = findViewById(R.id.door_1_button)
        door2button = findViewById(R.id.door_2_button)
        door3button = findViewById(R.id.door_3_button)

        door1arrow.isVisible = false
        door2arrow.isVisible = false
        door3arrow.isVisible = false
        switchStayMessage.isVisible=false

        door1arrow.setColorFilter(Color.argb(255,255,255,255))
        door2arrow.setColorFilter(Color.argb(255,255,255,255))
        door3arrow.setColorFilter(Color.argb(255,255,255,255))

        messageTextView.text = "Pick a Door"
        newgamebutton.text = "Continue"
        door1button.text = "Door 1"
        door2button.text = "Door 2"
        door3button.text = "Door 3"

        newgamebutton.isVisible = false
        stayButton.isVisible = false
        switchButton.isVisible = false

        //button listener setup
        newgamebutton.setOnClickListener { view ->
            resetGame()
        }

        switchButton.setOnClickListener { view ->
            if (stay == 2) {
                switch = 1
            }
            switchDoors()
        }

        stayButton.setOnClickListener { view ->
            if (switch == 2) {
                stay = 1
            }
            switchDoors()
        }

        door1button.setOnClickListener { view ->
            door1arrow.isVisible=true
            gameLogic(1)
        }

        door2button.setOnClickListener{ view ->
            door2arrow.isVisible=true
            gameLogic(2)
        }

        door3button.setOnClickListener{ view ->
            door3arrow.isVisible=true
            gameLogic(3)
        }

        val newWins = "Wins:     $wins"
        gameScoreTextView.text=newWins

        val newLosses = "Losses: $losses"
        lossScoreTextView.text=newLosses

        val initWinPercent = 0
        val winPercent = "Win Percentage: $initWinPercent"
        winPercentTextView.text= winPercent

    }

    override fun onDestroy(){
        super.onDestroy()
        Log.d(TAG, "onDestroy called  .")

    }
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        super.onCreateOptionsMenu(menu)
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.about_item){
            showInfo()
        }
        return true
    }

    private fun showInfo(){
        //about info
        val dialogTitle=getString(R.string.about_title,
            BuildConfig.VERSION_NAME)
        val dialogMessage = getString(R.string.about_message)

        val builder = AlertDialog.Builder(this)
        builder.setTitle(dialogTitle)
        builder.setMessage(dialogMessage)
        builder.create().show()
    }

    private fun gameLogic(clickedVal:Int) {
        Log.d(TAG, "\ngameLogic called.  \ngameStage is $gameStage. \ndoorPrize is $doorPrize. \ndoorPicked is $doorPicked. \ndoorOpened is $doorOpened. \nwins is $wins. \nlosses is $losses")

            doorPicked=clickedVal
            doorPrize = nextInt(1,4)

            openADoor()

            messageTextView.text = "The host opened door $doorOpened."
            switchStayMessage.isVisible=true

            switchStayMessage.text = "Would you like to switch doors or stay?"

            door1button.isEnabled = false
            door2button.isEnabled = false
            door3button.isEnabled = false

            stayButton.isEnabled = true
            switchButton.isEnabled = true
    }

    private fun openADoor(){
        Log.d(TAG, "\nopenADoor called.  \ngameStage is $gameStage. \ndoorPrize is $doorPrize. \ndoorPicked is $doorPicked. \ndoorOpened is $doorOpened. \nwins is $wins. \nlosses is $losses")
        var testDoor = nextInt(1,4)       //random a door to check
        Log.d(TAG, "\n before while testDoor is $testDoor")

        //if door is not door picked or the prize door, it can be opened
        while (testDoor==doorPicked || doorPrize==testDoor) {
            testDoor = nextInt(1,4)

            Log.d(TAG, "\n inWhile testDoor is $testDoor")
        }
        Log.d(TAG, "\n after while testDoor is $testDoor")
        doorOpened = testDoor

        //mark the opened door invisible
        when(doorOpened){
            1 -> {
                door1button.isVisible = false
                Log.d(TAG, "\n Door 1 marked invisible")
            }
            2 ->{
                door2button.isVisible = false
                Log.d(TAG, "\n Door 2 marked invisible")
            }
            3 ->{
                door3button.isVisible = false
                Log.d(TAG, "\n Door 3 marked invisible")
            }
        }
        stayButton.isVisible = true
        switchButton.isVisible = true
        Log.d(TAG, "\nafter doorOpened selection.\n doorOpened is $doorOpened.  \ngameStage is $gameStage. \ndoorPrize is $doorPrize. \ndoorPicked is $doorPicked. \ndoorOpened is $doorOpened. \nwins is $wins. \nlosses is $losses")

    }

    private fun switchDoors() {
        messageTextView.text = ""
        stayButton.isVisible = false
        switchButton.isVisible = false
        switchStayMessage.isVisible=false
        Log.d(TAG, "\nswitchDoors called. \ngameStage is $gameStage. \ndoorPrize is $doorPrize. \ndoorPicked is $doorPicked. \ndoorOpened is $doorOpened. \nwins is $wins. \nlosses is $losses. \nstay is $stay. \nswitch is $switch")
        switchButton.isEnabled=false
        stayButton.isEnabled=false

        //verify if switch and stay are in the right state for switch
        if(switch==1 && stay==2) {
            //determine which door to switch to based on door picked and what door was opened
            when (doorPicked) {
                1 -> {
                    if (doorOpened == 2) {
                        doorPicked = 3
                        door3arrow.isVisible=true
                        door1arrow.isVisible=false
                    } else {
                        doorPicked = 2
                        door2arrow.isVisible=true
                        door1arrow.isVisible=false
                    }
                }
                2 -> {
                    if (doorOpened == 1) {
                        doorPicked = 3
                        door3arrow.isVisible=true
                        door2arrow.isVisible=false
                    } else {
                        doorPicked = 1
                        door1arrow.isVisible=true
                        door2arrow.isVisible=false
                    }
                }
                3 -> {
                    if (doorOpened == 1) {
                        doorPicked = 2
                        door2arrow.isVisible=true
                        door3arrow.isVisible=false
                    } else {
                        doorPicked = 1
                        door1arrow.isVisible=true
                        door3arrow.isVisible=false
                    }
                }

            }
            //switching complete, score it
            scoreGame()
        }
        else if (stay==1 && switch == 2) {
            //staying so no switching, go straight to scoring
            scoreGame()
        }
        else {
            //something isn't right with switch and stay, reset game (this should never occur)
            resetGame()
        }
    }

    private fun scoreGame()
    {
        Log.d(TAG, "\nscoreGame called. \ngameStage is $gameStage. \ndoorPrize is $doorPrize. \ndoorPicked is $doorPicked. \ndoorOpened is $doorOpened. \nwins is $wins. \nlosses is $losses")
        newgamebutton.isVisible = true
        //if player picked prize door, increment wins
        if (doorPicked == doorPrize){
            wins++
            messageTextView.text = "You Won!"
        }
        //otherwise increment losses
        else {
            losses++
            messageTextView.text = "The prize was behind the other door"
        }
        //label the prize door as prize and the others empty (one of the empty is invisible)
        when (doorPrize){
            1 -> {
                door1button.text = "Prize!"
                door2button.text = "Empty"
                door3button.text = "Empty"
            }
            2 -> {
                door1button.text = "Empty"
                door2button.text = "Prize!"
                door3button.text = "Empty"
            }
            3 -> {
                door1button.text = "Empty"
                door2button.text = "Empty"
                door3button.text = "Prize!"
            }
        }
        newgamebutton.isVisible = true
        val newWins = "Wins:     $wins"
        gameScoreTextView.text=newWins
        val newLosses = "Losses: $losses"
        lossScoreTextView.text=newLosses
        percentage = (wins.toDouble()/(wins+losses)*100)
        percentage = (percentage * 10).roundToInt() / 10.0
        Log.d(TAG, "\n wins $wins, loss: $losses, Percent: $percentage")
        val winPercent = "Win Percentage: $percentage %"
        winPercentTextView.text=winPercent
    }

    private fun resetGame() {
        Log.d(TAG, "\nresetGame called. \ngameStage is $gameStage. \ndoorPrize is $doorPrize. \ndoorPicked is $doorPicked. \ndoorOpened is $doorOpened. \nwins is $wins. \nlosses is $losses")
        //variable reset
        gameStage = 0
        doorPicked = 0
        doorOpened = 0
        switch = 2
        stay = 2
        doorPrize = 0
        //button/arrow visibility reset
        door1button.isEnabled = true
        door2button.isEnabled = true
        door3button.isEnabled = true
        door1button.isVisible = true
        door2button.isVisible = true
        door3button.isVisible = true
        door1arrow.isVisible = false
        door2arrow.isVisible = false
        door3arrow.isVisible = false
        stayButton.isVisible = false
        switchButton.isVisible = false
        newgamebutton.isVisible = false

        messageTextView.text = "Pick a Door"
        door1button.text = "Door 1"
        door2button.text = "Door 2"
        door3button.text = "Door 3"
    }
}