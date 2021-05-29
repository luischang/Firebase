package dev.luischang.firebase

import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.annotation.Nullable
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.*


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val db = FirebaseFirestore.getInstance()
        val tvCurso: TextView = findViewById(R.id.tvCurso)
        val tvNota: TextView = findViewById(R.id.tvNota)
        db.collection("courses")
//            .whereEqualTo("state", "CA")
            .addSnapshotListener { snapshots, e ->
                    if (e != null) {
                        Log.w("Firebase", "listen:error", e)
                        return@addSnapshotListener
                    }
                    for (dc in snapshots!!.documentChanges) {
                        when (dc.type) {
                            DocumentChange.Type.ADDED ->{
                                Log.d("Firebase", "Data: " + dc.document.data)
                                tvCurso.text=dc.document.data["description"].toString()
                                tvNota.text=dc.document.data["score"].toString()
                            }
                            DocumentChange.Type.MODIFIED -> {
                                tvCurso.text=dc.document.data["description"].toString()
                                tvNota.text=dc.document.data["score"].toString()


                            }
                            DocumentChange.Type.REMOVED -> Log.d("Firebase",
                                "Removed Data: " + dc.document.data
                            )
                        }
                    }
            }

    }
}