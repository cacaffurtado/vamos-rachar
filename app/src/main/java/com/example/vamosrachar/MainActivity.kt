package com.example.vamosrachar

import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.ImageButton
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import java.text.DecimalFormat
import android.content.Intent

class MainActivity : AppCompatActivity() {
    private lateinit var tts: TextToSpeech

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        resultado()
        //shareButton()
        //tts = TextToSpeech(this, this)
    }

    // RESULTADO
    fun resultado(){
        val valorConta:TextView=findViewById(R.id.valorConta)
        val qtdPessoa :TextView=findViewById(R.id.qtdPessoa)
        val perPessoa:TextView=findViewById(R.id.perPessoa)

        // TEXTWATCHER : valorConta
        valorConta.addTextChangedListener(object:TextWatcher {
            override fun afterTextChanged(s: Editable) {}
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                if (s.isNotEmpty() && qtdPessoa.text.isNotEmpty() && !qtdPessoa.text.equals("0")){
                    val valorDouble = valorConta.text.toString().toDouble()
                    val qtdDouble = qtdPessoa.text.toString().toDouble()
                    perPessoa.text = calculoFormatado(valorDouble, qtdDouble)
                } else {
                    perPessoa.text = " "
                }
            }
        })
        // TEXTWATCHER : qtdPessoa
        qtdPessoa.addTextChangedListener(object:TextWatcher {
            override fun afterTextChanged(s: Editable) {}
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                if (s.isNotEmpty() && valorConta.text.isNotEmpty() && !qtdPessoa.text.equals("0")){
                    val valorDouble = valorConta.text.toString().toDouble()
                    val qtdDouble = qtdPessoa.text.toString().toDouble()
                    perPessoa.text = calculoFormatado(valorDouble, qtdDouble)
                } else {
                    perPessoa.text = " "
                }
            }
        })
    }

    // CALCULAR E FORMATAR RESULTADO
    fun calculoFormatado(conta : Double, grupo : Double) : String{
        val perPessoa = conta/grupo
        val dFormat = DecimalFormat("##.##")
        return "R$${dFormat.format(perPessoa)} PARA CADA PESSOA!"
    }

    // BOTAO COMPARTILHAMENTO

    // CRIAR MENSAGEM

    // BOTAO TextToSpeech


}