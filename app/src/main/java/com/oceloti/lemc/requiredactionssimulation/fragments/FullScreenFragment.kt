package com.oceloti.lemc.requiredactionssimulation.fragments

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebChromeClient
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import com.oceloti.lemc.requiredactionssimulation.R

class FullScreenFragment: Fragment() {

  interface OnFragmentInteractionListener {
    fun onFragmentClosedAndUrlDetected(url: String)
  }

   lateinit var webView: WebView
   private var listener: OnFragmentInteractionListener? = null
  private var isBeingDetached: Boolean = false
  private var isUrlSearchedFound: Boolean = false

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    Log.d(TAG, "onCreateView()")
    val view = inflater.inflate(R.layout.fragment_full_screen, container, false)
    webView = view.findViewById(R.id.webView)

    // Enable JavaScript (if needed)
    webView.settings.javaScriptEnabled = true

    // Set WebView clients
    webView.webViewClient = object : WebViewClient() {
      override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {
        val url = request?.url.toString()
        Log.d(TAG, "Loading URL: $url")
        if(url.equals("https://es.m.wikipedia.org/wiki/Idioma_italiano")){
          isUrlSearchedFound = true
          listener?.onFragmentClosedAndUrlDetected(url)
          activity?.supportFragmentManager?.beginTransaction()?.remove(this@FullScreenFragment)?.commit()
          activity?.supportFragmentManager?.popBackStack()
          return true
        }
         // Logic
        return false
        //return super.shouldOverrideUrlLoading(view, request)
      }
    }
    webView.webChromeClient = WebChromeClient()

    // Load the URL
    val url = arguments?.getString("url")
    url?.let {
      webView.loadUrl(it)
    }

    return view
  }

  // Call onFragmentClosed when the fragment is destroyed
  override fun onDestroy() {
    super.onDestroy()
    Log.d(TAG, "onDestroy()")
    if(!isUrlSearchedFound)
    if (isRemoving) {
      Log.d(TAG, "onDestroy() - onFragmentClosedAndUrlDetected(empty)")
      listener?.onFragmentClosedAndUrlDetected("empty")
    }
  }

  override fun onAttach(context: Context) {
    super.onAttach(context)
    if (context is OnFragmentInteractionListener) {
      listener = context
    } else {
      throw RuntimeException("$context must implement OnFragmentInteractionListener")
    }
  }

  override fun onDetach() {
    super.onDetach()
    listener = null
    isBeingDetached = true
  }


  companion object {
    const val TAG = "FullScreenFragment"

    fun newInstance(url:String): FullScreenFragment {
      val fragment = FullScreenFragment()
      val args = Bundle()
      args.putString("url", url)
      fragment.arguments = args
      return fragment
    }
  }
}