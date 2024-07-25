package com.example.vamosrachar

import java.util.Locale
import java.text.DecimalFormat

import android.speech.tts.TextToSpeech
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.TextView
import android.util.Log

import android.widget.ImageButton
import android.content.Intent

class MainActivity : AppCompatActivity(), TextToSpeech.OnInitListener {
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

        tts = TextToSpeech(this, this)

        resultado()
        btnCompartilhamento()
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
                }
            }
        })
    }

    // CALCULAR E FORMATAR RESULTADO
    fun calculoFormatado(conta : Double, grupo : Double) : String{
        val perPessoa = conta/grupo
        val dFormat = DecimalFormat("##.##")
        return "R$${dFormat.format(perPessoa)}"
    }

    // CRIAR MENSAGEM
    fun mensagemResultado() : String{
        val valorConta:TextView = findViewById(R.id.valorConta)
        val qtdPessoa:TextView = findViewById(R.id.qtdPessoa)

        if (valorConta.text.isNotEmpty() && qtdPessoa.text.isNotEmpty()) {
            val valorTexto = valorConta.text.toString()
            val qtdTexto = qtdPessoa.text.toString()
            val perPessoa = calculoFormatado(valorTexto.toDouble(), qtdTexto.toDouble())

            return "Opa, caloteiros! A conta deu ${valorTexto} reais, viiiu? Dividindo para nós (${qtdTexto} queridos), fica ${perPessoa} para cada, tá bem? Sem estresse, agilizem!"
        } else {
            return "Faltam campos serem preenchidos"
        }
    }

    // BOTAO TextToSpeech
    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            tts.language = Locale.getDefault()
        } else {
            Log.e("My Log", "Failed to initialize TTS engine.")
        }
    }

    fun clickFale(v: View){
        Log.v("My Log", "$v pressed")
        tts.speak(mensagemResultado(), TextToSpeech.QUEUE_FLUSH, null, null)
    }

    // BOTAO COMPARTILHAMENTO
    fun btnCompartilhamento(){
        val btnShare : ImageButton=findViewById(R.id.btnCompart)

        btnShare.setOnClickListener{
            val perPessoa:TextView =findViewById(R.id.perPessoa)

            if (perPessoa.text.toString().isNotEmpty()){
                val sendIntent : Intent = Intent().apply {
                    action = Intent.ACTION_SEND
                    putExtra(Intent.EXTRA_TEXT, mensagemResultado())
                    type = "text/plain"
                }

                val shareIntent = Intent.createChooser(sendIntent, null)
                startActivity(shareIntent)
            }
        }
    }

    override fun onDestroy() {
        tts.stop()
        tts.shutdown()
        super.onDestroy()
    }
}