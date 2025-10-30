package com.org.trademe

import android.graphics.Bitmap
import android.os.Build
import android.webkit.*
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InternalBrowserScreen(
    initialUrl: String = "https://www.google.com",
    onClose: () -> Unit = {}
) {
    var currentUrl by remember { mutableStateOf(initialUrl) }
    var urlInput by remember { mutableStateOf(initialUrl) }
    var canGoBack by remember { mutableStateOf(false) }
    var canGoForward by remember { mutableStateOf(false) }
    var isLoading by remember { mutableStateOf(false) }
    var loadingProgress by remember { mutableStateOf(0) }
    var webView by remember { mutableStateOf<WebView?>(null) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Browser") },
                navigationIcon = {
                    IconButton(onClick = onClose) {
                        Icon(Icons.Default.Close, "Close")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            // Loading indicator
            if (isLoading) {
                LinearProgressIndicator(
                    progress = loadingProgress / 100f,
                    modifier = Modifier.fillMaxWidth()
                )
            }

            // WebView with loading overlay
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .weight(1f)
            ) {
                AndroidView(
                    factory = { context ->
                        WebView(context).apply {
                            // Set background to transparent initially to avoid white flash
                            setBackgroundColor(android.graphics.Color.TRANSPARENT)

                            // Enable hardware acceleration for better performance
                            setLayerType(android.view.View.LAYER_TYPE_HARDWARE, null)

                            // Enable JavaScript
                            settings.javaScriptEnabled = true

                            // Enable cookies
                            val cookieManager = CookieManager.getInstance()
                            cookieManager.setAcceptCookie(true)
                            cookieManager.setAcceptThirdPartyCookies(this, true)

                            // Enable DOM storage
                            settings.domStorageEnabled = true

                            // Enable database
                            settings.databaseEnabled = true

                            // Enable multiple windows (popups)
                            settings.javaScriptCanOpenWindowsAutomatically = true
                            settings.setSupportMultipleWindows(true)

                            // Enable zoom controls
                            settings.setSupportZoom(true)
                            settings.builtInZoomControls = true
                            settings.displayZoomControls = false

                            // Other settings
                            settings.loadWithOverviewMode = true
                            settings.useWideViewPort = true
                            settings.mixedContentMode = WebSettings.MIXED_CONTENT_ALWAYS_ALLOW

                            // Enable file access
                            settings.allowFileAccess = true
                            settings.allowContentAccess = true

                            // Enable safe browsing
                           // settings.safeBrowsingEnabled = true

                            // Enable proper rendering
                            settings.cacheMode = WebSettings.LOAD_DEFAULT
                            settings.mediaPlaybackRequiresUserGesture = false

                            // Fix touch/click issues
                            isFocusable = true
                            isFocusableInTouchMode = true
                            isClickable = true

                            // WebViewClient for handling page navigation
                            webViewClient = object : WebViewClient() {
                                override fun onPageFinished(view: WebView?, url: String?) {
                                    super.onPageFinished(view, url)
                                    isLoading = false
                                    loadingProgress = 100
                                    url?.let {
                                        currentUrl = it
                                        urlInput = it
                                    }
                                    canGoBack = view?.canGoBack() ?: false
                                    canGoForward = view?.canGoForward() ?: false

                                    // Force focus and enable interactions after page load
                                    view?.requestFocus()
                                    view?.requestFocusFromTouch()
                                }

                                override fun onPageStarted(
                                    view: WebView?,
                                    url: String?,
                                    favicon: Bitmap?
                                ) {
                                    super.onPageStarted(view, url, favicon)
                                    isLoading = true
                                    loadingProgress = 0
                                }

                                override fun shouldOverrideUrlLoading(
                                    view: WebView?,
                                    request: WebResourceRequest?
                                ): Boolean {
                                    // Let WebView handle all URLs
                                    return false
                                }

                                override fun onReceivedError(
                                    view: WebView?,
                                    request: WebResourceRequest?,
                                    error: WebResourceError?
                                ) {
                                    super.onReceivedError(view, request, error)
                                    isLoading = false
                                }

                                override fun onReceivedHttpError(
                                    view: WebView?,
                                    request: WebResourceRequest?,
                                    errorResponse: WebResourceResponse?
                                ) {
                                    super.onReceivedHttpError(view, request, errorResponse)
                                    // Don't stop loading for HTTP errors on sub-resources
                                }
                            }

                            // WebChromeClient for handling popups, progress, and other features
                            webChromeClient = object : WebChromeClient() {
                                override fun onProgressChanged(view: WebView?, newProgress: Int) {
                                    super.onProgressChanged(view, newProgress)
                                    loadingProgress = newProgress
                                    isLoading = newProgress < 100
                                }

                                // Handle popup windows
                                override fun onCreateWindow(
                                    view: WebView?,
                                    isDialog: Boolean,
                                    isUserGesture: Boolean,
                                    resultMsg: android.os.Message?
                                ): Boolean {
                                    val newWebView = WebView(context)
                                    newWebView.settings.javaScriptEnabled = true
                                    newWebView.settings.javaScriptCanOpenWindowsAutomatically = true
                                    newWebView.settings.setSupportMultipleWindows(true)

                                    newWebView.webViewClient = object : WebViewClient() {
                                        override fun shouldOverrideUrlLoading(
                                            view: WebView?,
                                            request: WebResourceRequest?
                                        ): Boolean {
                                            // Load popup URL in main WebView
                                            request?.url?.toString()?.let { url ->
                                                this@apply.loadUrl(url)
                                            }
                                            return true
                                        }
                                    }

                                    val transport = resultMsg?.obj as? WebView.WebViewTransport
                                    transport?.webView = newWebView
                                    resultMsg?.sendToTarget()

                                    return true
                                }

                                // Handle JavaScript alerts
                                override fun onJsAlert(
                                    view: WebView?,
                                    url: String?,
                                    message: String?,
                                    result: JsResult?
                                ): Boolean {
                                    result?.confirm()
                                    return true
                                }

                                // Handle JavaScript confirmations
                                override fun onJsConfirm(
                                    view: WebView?,
                                    url: String?,
                                    message: String?,
                                    result: JsResult?
                                ): Boolean {
                                    result?.confirm()
                                    return true
                                }

                                // Handle JavaScript prompts
                                override fun onJsPrompt(
                                    view: WebView?,
                                    url: String?,
                                    message: String?,
                                    defaultValue: String?,
                                    result: JsPromptResult?
                                ): Boolean {
                                    result?.confirm(defaultValue ?: "")
                                    return true
                                }
                            }

                            // Load initial URL
                            loadUrl(initialUrl)

                            webView = this
                        }
                    },
                    modifier = Modifier.fillMaxSize(),
                    update = { view ->
                        // Update webView reference if needed
                        webView = view
                    }
                )

                // Full screen loading overlay before page loads
                if (isLoading && loadingProgress < 100) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }
            }
        }
    }
}

// Usage in your activity or another composable
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun BrowserExample() {
    var showBrowser by remember { mutableStateOf(false) }

    if (showBrowser) {
        InternalBrowserScreen(
            initialUrl = "https://www.xero.com/nz/guides/starting-a-business/create-website/",
            onClose = { showBrowser = false }
        )
    } else {
        // Your main content
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Button(onClick = { showBrowser = true }) {
                Text("Open Browser")
            }
        }
    }
}