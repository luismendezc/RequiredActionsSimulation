package com.oceloti.lemc.requiredactionssimulation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.oceloti.lemc.requiredactionssimulation.databinding.ActivityDigIdentAuthorizationBinding
import com.oceloti.lemc.requiredactionssimulation.databinding.ActivityMainBinding
import com.oceloti.lemc.requiredactionssimulation.fragments.FullScreenFragment

class DigIdentAuthorizationActivity : AppCompatActivity(), FullScreenFragment.OnFragmentInteractionListener {
  private lateinit var binding: ActivityDigIdentAuthorizationBinding

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = ActivityDigIdentAuthorizationBinding.inflate(layoutInflater)
    setContentView(binding.root)

    Log.d(TAG, "onCreate()")

  }

  fun showFullScreenWebFragment(view: View) {
    Log.d(TAG, "showFragmentButton onClick")
    val fullScreenFragment = FullScreenFragment.newInstance(getRandomString())
    val fragmentManager: FragmentManager = supportFragmentManager
    val fragmentTransaction: FragmentTransaction = fragmentManager.beginTransaction()

    // Replace R.id.fragment_container with your actual container view ID
    fragmentTransaction.replace(R.id.fragment_container, fullScreenFragment)
    fragmentTransaction.addToBackStack(null)
    fragmentTransaction.commit()
  }

  fun getRandomString(): String {
    val randomNumber = (1..3).random()

    return when (randomNumber) {
      1 -> "https://www.spore.com/"
      2 -> "https://mock-service.galactus.verimi.cloud/"
      3 -> "https://es.wikipedia.org/wiki/Coliseo"
      else -> "https://www.google.com/"
    }
  }

  override fun onBackPressed() {
    Log.d(TAG, "onBackPressed()")
    val fragment = supportFragmentManager.findFragmentById(R.id.fragment_container)
    if (fragment is FullScreenFragment && fragment.webView.canGoBack()) {
      // If the WebView can go back, navigate back in the WebView
      fragment.webView.goBack()
    } else {
      // If the WebView cannot go back or there's no fragment, proceed with default back behavior
      super.onBackPressed()
    }
  }

  companion object {
    const val TAG = "DigIdentAuthorizationActivity"
  }

  override fun onFragmentClosedAndUrlDetected(url:String) {
    Log.d(TAG, "FullScreenFragment closed: $url")
    when (url){
      "empty" -> {
        binding.showFragmentButton.text = "empty"
      }
        else -> {
          binding.showFragmentButton.text = url
        }
    }
  }

}