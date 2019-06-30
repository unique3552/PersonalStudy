package com.example.mywebbrowser

import android.content.Intent
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.ContextMenu
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.inputmethod.EditorInfo
import android.webkit.WebViewClient
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.browse
import org.jetbrains.anko.email
import org.jetbrains.anko.sendSMS
import org.jetbrains.anko.share

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // 웹뷰 기본 설정
        webView.apply{
            settings.javaScriptEnabled = true
            webViewClient = WebViewClient()
        }

        webView.loadUrl("http://www.google.com")

        urlEditText.setOnEditorActionListener{_, actionId, _ ->
            if(actionId == EditorInfo.IME_ACTION_SEARCH) {
                webView.loadUrl(urlEditText.text.toString())
                true
            }
            else {
                false
            }
        }

        // 컨텍스트 메뉴 등록
        registerForContextMenu(webView)
    }

    override fun onBackPressed() {
        if(webView.canGoBack()){
            webView.goBack()
        }
        else {
            super.onBackPressed()
        }
    }

    // 옵션 버튼
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.action_google, R.id.action_home -> {
                webView.loadUrl("http://www.google.com")
                return true
            }
            R.id.action_naver -> {
                webView.loadUrl("http://www.naver.com")
                return true
            }
            R.id.action_daum -> {
                webView.loadUrl("http://www.daum.com")
                return true
            }
            R.id.action_call -> {
                val intent = Intent(Intent.ACTION_DIAL)
                intent.data = Uri.parse("tel:010-9263-3552")
                if (intent.resolveActivity(packageManager) != null){
                    startActivity(intent)
                }

                return true
            }
            R.id.action_send_text -> {
                // 문자보내기
                sendSMS("010-9263-3552", webView.url)
                return true
            }
            R.id.action_email -> {
                email("wor0@naver.com", "좋은 사이트", webView.url)
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    // 컨텍스트 메뉴
    override fun onCreateContextMenu(menu: ContextMenu?, v: View?, menuInfo: ContextMenu.ContextMenuInfo?) {
        super.onCreateContextMenu(menu, v, menuInfo)
        menuInflater.inflate(R.menu.context, menu)
    }

    override fun onContextItemSelected(item: MenuItem?): Boolean {
        when(item?.itemId){
            R.id.action_share -> {
                // 페이지 공유
                share(webView.url)
                return true
            }
            R.id.action_browser -> {
                // 기본 웹 브라우저에서 열기
                browse(webView.url)
                return true
            }
        }
        return super.onContextItemSelected(item)
    }
}
