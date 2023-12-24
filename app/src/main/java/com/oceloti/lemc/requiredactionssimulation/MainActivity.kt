package com.oceloti.lemc.requiredactionssimulation

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.oceloti.lemc.requiredactionssimulation.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
  private lateinit var binding: ActivityMainBinding
  private var callsOnCreate = 0
  private var callsOnResume = 0

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = ActivityMainBinding.inflate(layoutInflater)
    setContentView(binding.root)
    callsOnCreate++
    Log.d(TAG, "onCreate(): $callsOnCreate")
  }

  fun startDigIdentActivity(view: View){
    Log.d(TAG, "startDigIdentActivity onClick()")
    val intent = Intent(this, DigIdentAuthorizationActivity::class.java)
    startActivity(intent)
  }

  fun startDigIdentActivity2(view: View){
    Log.d(TAG, "startDigIdentActivity2 onClick()")
    val intent = Intent(this, DigIdentAuthorizationActivity2::class.java)
    startActivity(intent)
  }

  override fun onResume() {
    super.onResume()
    callsOnResume++
    Log.d(TAG, "onResume(): $callsOnResume")
  }


  companion object {
    const val TAG = "MainActivity"
  }
}