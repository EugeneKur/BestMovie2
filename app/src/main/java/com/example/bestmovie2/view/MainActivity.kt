package com.example.bestmovie2.view

import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.example.bestmovie2.R
import com.example.bestmovie2.databinding.MainActivityBinding
import com.example.bestmovie2.model.ConnectBroadcastReceiver
import android.content.SharedPreferences




class MainActivity : AppCompatActivity() {

    private val binding: MainActivityBinding by lazy {
        MainActivityBinding.inflate(layoutInflater)
    }

    private val receiver = ConnectBroadcastReceiver()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)


        registerReceiver(receiver,
            IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION)
        )

        supportFragmentManager.beginTransaction()
            .add(R.id.main_container, MainFragment.newInstance())
            .commit()

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        val sharedPreferences = getPreferences(Context.MODE_PRIVATE)
        IS_AGE = sharedPreferences.getBoolean("key1", false)
        if (menu != null) {
            menu.getItem(0).setChecked(IS_AGE)
        }
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val sharedPreferences = getPreferences(Context.MODE_PRIVATE)

        when (item.itemId) {
            R.id.action_age -> {
                item.setChecked(!item.isChecked)
                if (item.isChecked) {
                    IS_AGE = true
                } else IS_AGE = false
                val editor = sharedPreferences.edit()
                editor.putBoolean("key1", IS_AGE)
                editor.apply()
                return true
            }
            R.id.action_history -> {
                startActivity(Intent(applicationContext, HistoryActivity::class.java))
                return true
            }
            R.id.action_contacts -> {
                startActivity(Intent(this, ContactsActivity::class.java))
                return true
            }
            R.id.action_exit -> {
                finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onDestroy() {
        unregisterReceiver(receiver)
        super.onDestroy()
    }

    companion object {
        var IS_AGE = true
    }

}