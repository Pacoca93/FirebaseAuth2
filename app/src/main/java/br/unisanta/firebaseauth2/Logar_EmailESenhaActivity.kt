package br.unisanta.firebaseauth2

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import br.unisanta.firebaseauth2.databinding.ActivityLogarEmailEsenhaBinding
import br.unisanta.firebaseauth2.databinding.ActivitySignInEmailEsenhaBinding
import com.firebase.ui.auth.FirebaseAuthUIActivityResultContract
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class Logar_EmailESenhaActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLogarEmailEsenhaBinding
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLogarEmailEsenhaBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        auth = Firebase.auth

        binding.btnLogar.setOnClickListener {
            val email = binding.edtEmail.text.toString()
            val senha = binding.edtSenha.text.toString()

            if(senha.length < 6) {
                Toast.makeText(
                    this,
                    "Senha muito curta! A senha deve conter pelo menos 6 caracteres!",
                    Toast.LENGTH_LONG
                ).show()
            } else {
                auth.signInWithEmailAndPassword(email, senha)
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            Toast.makeText(
                                baseContext,
                                "Autenticado com sucesso!",
                                Toast.LENGTH_SHORT,
                            ).show()
                        } else {
                            Toast.makeText(
                                baseContext,
                                "Falha na autenticação.",
                                Toast.LENGTH_SHORT,
                            ).show()
                        }
                    }
            }
        }

        binding.imgbtnVoltar.setOnClickListener {
            finish()
        }

        binding.btnEmailMudarSenha.setOnClickListener {
            val email = binding.edtEmail.text.toString()

            Firebase.auth.sendPasswordResetEmail(email)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(
                            baseContext,
                            "Email enviado.",
                            Toast.LENGTH_SHORT,
                        ).show()
                    } else {
                        Toast.makeText(
                            baseContext,
                            "Falha em enviar o email.",
                            Toast.LENGTH_SHORT,
                        ).show()
                    }
                }
        }
    }
}