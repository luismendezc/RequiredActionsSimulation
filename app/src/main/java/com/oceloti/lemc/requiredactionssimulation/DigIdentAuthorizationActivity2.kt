package com.oceloti.lemc.requiredactionssimulation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Button
import android.widget.LinearLayout
import android.widget.RelativeLayout
import androidx.constraintlayout.widget.ConstraintLayout
import com.oceloti.lemc.requiredactionssimulation.databinding.ActivityDigIdentAuthorization2Binding
import com.oceloti.lemc.requiredactionssimulation.fragments.FullScreenFragment

class DigIdentAuthorizationActivity2 : AppCompatActivity() {
  private lateinit var binding: ActivityDigIdentAuthorization2Binding
  private var webView: WebView? = null

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = ActivityDigIdentAuthorization2Binding.inflate(layoutInflater)
    setContentView(binding.root)

    binding.openWebViewButton.setOnClickListener {
      showOrAddWebView()
    }

    // Setup WebView if you want it to be added initially
    // setupWebView()
  }

  private fun showOrAddWebView() {
    if (webView == null) {
      createAndAddWebView()
    } else {
      showWebView()
    }
  }

  private fun createAndAddWebView() {
    webView = WebView(this)
    webView?.id = View.generateViewId()

    val params = ConstraintLayout.LayoutParams(
      ConstraintLayout.LayoutParams.MATCH_PARENT,
      ConstraintLayout.LayoutParams.MATCH_PARENT
    )

    webView?.layoutParams = params

    setupWebView()

    // Add WebView to the root view
    binding.root.addView(webView)

    // Show the WebView
    showWebView()
  }

  private fun setupWebView() {
    webView?.settings?.javaScriptEnabled = true
    webView?.webViewClient = object : WebViewClient() {
      override fun shouldOverrideUrlLoading(
        view: WebView?,
        request: WebResourceRequest?
      ): Boolean {
        val url = request?.url.toString()
        if (url == "https://twitter.com/chesscom") {
          // Finish the WebView
          destroyWebView()
          // Update the text of the activity and button
          updateText()
          return true
        }
        return false
      }
    }
    webView?.loadUrl("https://www.chess.com/")
  }

  private fun showWebView() {
    webView?.visibility = View.VISIBLE
    binding.openWebViewButton.visibility = View.GONE
  }

  private fun hideWebView() {
    webView?.visibility = View.GONE
    binding.openWebViewButton.visibility = View.VISIBLE
  }

  private fun updateText() {
    // Update the text of the activity and button when the target URL is reached
    // For example:
    // titleTextView.text = "New Title"
    binding.openWebViewButton.text = "I found the URL"
  }

  override fun onBackPressed() {
    if (webView?.visibility == View.VISIBLE && webView?.canGoBack() == true) {
      // If WebView is visible and can go back, go back in WebView history
      Log.d(TAG, "can go back")
      webView?.goBack()
    } else {
      // If WebView is at the initial state or not visible, handle back press as usual
      Log.d(TAG, "closing")
      if (webView != null) {
        // Destroy the WebView and show the original Image and Button
        destroyWebView()
      } else {
        // If there's no WebView, handle back press as usual
        super.onBackPressed()
      }
    }
  }

  private fun destroyWebView() {
    // Remove WebView from the root view
    binding.root.removeView(webView)
    // Set webView to null
    webView = null
    // Show the original Image and Button
    binding.imageView.visibility = View.VISIBLE
    binding.openWebViewButton.visibility = View.VISIBLE
  }

  companion object{
    const val TAG = "DigIdentAuthorizationActivity2"
  }
}