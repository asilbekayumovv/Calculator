package uz.itschool.myapplication

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.View.OnClickListener
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.AppCompatButton
import androidx.core.text.isDigitsOnly

class MainActivity : AppCompatActivity(), OnClickListener {
    lateinit var btnC: AppCompatButton
    lateinit var btnPercent: AppCompatButton

    lateinit var btnDiv: AppCompatButton
    lateinit var btnMul: AppCompatButton
    lateinit var btnMin: AppCompatButton
    lateinit var btnPlus: AppCompatButton
    lateinit var btnEql: AppCompatButton

    lateinit var btn1: AppCompatButton
    lateinit var btn2: AppCompatButton
    lateinit var btn3: AppCompatButton
    lateinit var btn4: AppCompatButton
    lateinit var btn5: AppCompatButton
    lateinit var btn6: AppCompatButton
    lateinit var btn7: AppCompatButton
    lateinit var btn8: AppCompatButton
    lateinit var btn9: AppCompatButton
    lateinit var btn0: AppCompatButton

    lateinit var btnDot: AppCompatButton
    lateinit var btnBack: ImageButton

    lateinit var btn00: AppCompatButton

    lateinit var tv1: TextView
    lateinit var tv2: TextView

    private var isDigit: Boolean = false
    private var isPoint: Boolean = true
    private var isPercent: Boolean = false

    private var result: String = ""

    private lateinit var list: MutableList<Any>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        loadElements()

        loadNumbers()

        loadOthers()

        loadSigns()
    }

    @SuppressLint("SetTextI18n")
    override fun onClick(v: View?) {
        val btn = findViewById<AppCompatButton>(v!!.id)
        if (tv1.text != "0") {
            tv1.text = tv1.text.toString() + btn.text
        } else {
            if(btn.text == "00"){
                tv1.text = "0"
            }
            else tv1.text = btn.text
        }

        tv2.text = calculate()
        isDigit = true
        isPercent = true

    }

    private fun loadElements() {
        btn0 = findViewById(R.id.btn0)
        btn1 = findViewById(R.id.btn1)
        btn2 = findViewById(R.id.btn2)
        btn3 = findViewById(R.id.btn3)
        btn4 = findViewById(R.id.btn4)
        btn5 = findViewById(R.id.btn5)
        btn6 = findViewById(R.id.btn6)
        btn7 = findViewById(R.id.btn7)
        btn8 = findViewById(R.id.btn8)
        btn9 = findViewById(R.id.btn9)

        btnC = findViewById(R.id.btnC)
        btnBack = findViewById(R.id.btnBack)
        btnEql = findViewById(R.id.btnEql)
        btnDiv = findViewById(R.id.btnDiv)
        btnMin = findViewById(R.id.btnMin)
        btnMul = findViewById(R.id.btnMul)
        btnPlus = findViewById(R.id.btnPlus)
        btnDot = findViewById(R.id.btnDot)
        btnPercent = findViewById(R.id.btnPercent)

        tv1 = findViewById(R.id.tv1)
        tv2 = findViewById(R.id.tv2)

        btn00 = findViewById(R.id.btn00)
    }

    private fun loadNumbers() {
        btn0.setOnClickListener(this)
        btn1.setOnClickListener(this)
        btn2.setOnClickListener(this)
        btn3.setOnClickListener(this)
        btn4.setOnClickListener(this)
        btn5.setOnClickListener(this)
        btn6.setOnClickListener(this)
        btn7.setOnClickListener(this)
        btn8.setOnClickListener(this)
        btn9.setOnClickListener(this)
        btn0.setOnClickListener(this)
        btn00.setOnClickListener(this)
    }

    private fun loadSigns() {
        addSign(btnDiv)
        addSign(btnMin)
        addSign(btnMul)
        addSign(btnPlus)
        addSign(btnPercent)

        addDot()
        addPercent()


    }

    @SuppressLint("SetTextI18n")
    private fun addDot() {
        btnDot.setOnClickListener {
            if (isPoint && tv1.text[tv1.text.length - 1].isDigit()) {
                tv1.text = tv1.text.toString() + "."
                isPoint = false
                isDigit = false
                isPercent = false
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun addPercent() {
        btnPercent.setOnClickListener {
            if (isPercent) {
                tv1.text = tv1.text.toString() + "%"
                isPercent = false
                isDigit = false
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun addSign(btn: AppCompatButton) {
        btn.setOnClickListener {
            if (isDigit && tv1.text[tv1.text.length - 1] != '.') {
                tv1.text = tv1.text.toString() + btn.text
                isDigit = false
                isPercent = false
            } else {
                if (tv1.text != "0" && tv1.text[tv1.text.length - 1] != '.') {
                    tv1.text = tv1.text.substring(0, tv1.text.length - 1) + btn.text
                }
            }
            isPoint = true
        }

    }

    private fun loadOthers() {
        btnC.setOnClickListener { clear() }

        btnBack.setOnClickListener {
            if (tv1.text.length == 1) {
                clear()
            } else {
                if (tv1.text[tv1.text.length - 1] == '.') {
                    isPoint = true
                }
                if (!tv1.text[tv1.text.length - 1].isDigit()) {
                    isDigit = true
                }
                tv1.text = tv1.text.substring(0, tv1.text.length - 1)
            }

            var str: String = tv1.text.toString()

            if (!tv1.text[tv1.text.length - 1].isDigit()) {
                tv1.text = tv1.text.substring(0, tv1.text.length - 1)
                tv2.text = calculate()
                tv1.text = str
            } else {
                tv2.text = calculate()
            }
        }

        btnEql.setOnClickListener {
            clear()
            tv1.text = result
            isDigit = true
        }


    }

    private fun clear() {
        tv1.text = "0"
        tv2.text = "0"
        isPoint = true
        isDigit = false
        isPercent = false
    }

    fun collectToList(s: String): MutableList<Any> {
        var list = mutableListOf<Any>()

        var temp = ""
        for (i in s) {
            if (i.isDigit() || i == '.') {
                temp += i
            } else {
                list.add(temp)
                list.add(i)
                temp = ""
            }
        }
        if (temp.isNotEmpty()) {
            list.add(temp)
        }
        return list
    }

    private fun replace(i: Int, myList: MutableList<Any>) {
        myList.removeAt(i)
        myList.removeAt(i)
        myList.removeAt(i - 1)
    }


    private fun calculate(): String {
        var res: Any = "0"
        var myList: MutableList<Any> = collectToList(tv1.text.toString())
        var i = 0
        var j = 0
        var foiz = 0

        while (foiz < myList.size) {
            if (myList[foiz].toString() == "%") {
                res = ((myList[foiz - 1].toString().toDouble()) / 100 * (myList[foiz + 1].toString()
                    .toDouble()))
                replace(foiz, myList)
                myList.add(foiz - 1, res)
                foiz -= 2
            }
            foiz++
        }


        if (myList.size >= 3 && myList.size % 2 != 0) {
            while (i < myList.size) {
                if ((myList[i].toString() == "×") || (myList[i].toString() == "÷")) {
                    if (myList[i].toString() == "×") {
                        res = (myList[i - 1].toString().toDouble()) * (myList[i + 1].toString()
                            .toDouble())
                        replace(i, myList)
                        myList.add(i - 1, res)
                        i -= 2
                    } else {
                        res = (myList[i - 1].toString().toDouble()) / (myList[i + 1].toString()
                            .toDouble())
                        replace(i, myList)
                        myList.add(i - 1, res)
                        i -= 2
                    }
                }
                i++
            }
            while (j < myList.size) {
                if ((myList[j].toString() == "+") || (myList[j].toString() == "-")) {
                    if (myList[j].toString() == "+") {
                        res = (myList[j - 1].toString().toDouble()) + (myList[j + 1].toString()
                            .toDouble())
                        replace(j, myList)
                        myList.add(j - 1, res)
                        j -= 2
                    } else {
                        res = (myList[j - 1].toString().toDouble()) - (myList[j + 1].toString()
                            .toDouble())
                        replace(j, myList)
                        myList.add(j - 1, res)
                        j -= 2
                    }
                }
                j++
            }
        }
        if (myList.size == 1) {
            res = myList[0].toString()
        }

        if (res.toString().takeLast(2) == ".0") {
            res = res.toString().dropLast(2)
        }

        result = res.toString()

        return res.toString()
    }

}