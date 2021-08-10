package com.shockwave.mind_lottery

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.widget.Toast
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.tongs.*

class Tongs : AppCompatActivity() {

    private var link: Link? = null

    private val BUNDLE_KEY = "BUNDLE_KEY"
    private val URL = "URL"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.tongs)

        val data_s_base = FirebaseDatabase.getInstance().reference
        if (mang_ket_noi(this)) {
            data_s_base.child("link").addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    link = snapshot.getValue(Link::class.java)
                }

                override fun onCancelled(error: DatabaseError) {
                    Toast.makeText(this@Tongs, error.message, Toast.LENGTH_SHORT).show()
                }
            })
        } else {
            Toast.makeText(this, "Mất kết nối, làm ơn hãy thử lại sau", Toast.LENGTH_SHORT).show()
        }

        kis.setOnClickListener {
            val bundleKis = Bundle()
            val intentKis = Intent(this, Luois::class.java)
            bundleKis.putString(URL, link?.register)
            intentKis.putExtra(BUNDLE_KEY, bundleKis)
            startActivity(intentKis)
        }

        nhaps.setOnClickListener {
            val bundleNhaps = Bundle()
            val intentNhaps = Intent(this, Luois::class.java)
            bundleNhaps.putString(URL, link?.login)
            intentNhaps.putExtra(BUNDLE_KEY, bundleNhaps)
            startActivity(intentNhaps)
        }
    }


    fun mang_ket_noi(con_s_text: Context?): Boolean {
        if (con_s_text != null) {
            val mCon_s_nectivity_s_manager = con_s_text
                .getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val mNetworkInfo = mCon_s_nectivity_s_manager.activeNetworkInfo
            if (mNetworkInfo != null) {
                return mNetworkInfo.isAvailable
            }
        }
        return false
    }
}