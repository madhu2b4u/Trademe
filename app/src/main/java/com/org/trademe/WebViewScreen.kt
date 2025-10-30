package com.org.trademe

import android.webkit.WebChromeClient
import android.webkit.WebView
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView

@Composable
fun WebViewScreen() {
    // 1. Determine system theme preference using Compose hook
    val isSystemInDark = isSystemInDarkTheme()

    // 2. HTML content with Kotlin string interpolation for system theme injection
    val htmlContent = """
            <!DOCTYPE html>
            <html lang="en">
            <head>
                <meta charset="UTF-8">
                <!-- CRITICAL for mobile responsiveness -->
                <meta name="viewport" content="width=device-width, initial-scale=1.0">
                
                <title>Multimedia Content Page</title>
                
              <style>
                  img, video, iframe {
                      max-width: 100%;
                  }

                  body {
                      font-family: Roboto, "Proxima Nova", "Noto Sans", Helvetica, Arial, sans-serif;
                      font-size: 16px;
                      line-height: 24px;
                      padding: 10px;
                      background-color: #ffffff; 
                      color: #1D1B20; 
                      max-width: 900px;
                      margin: 0 auto;
                      transition: background-color 0.3s, color 0.3s;
                  }
                  
                  h1 {
                      font-size: 24px;
                      font-weight: 500;
                      line-height: 32px;
                      color: #1D1B20;
                      margin-bottom: 16px;
                  }
                  
                  h2 {
                      font-size: 22px;
                      font-weight: 500;
                      line-height: 28px;
                      color: #1D1B20;
                      margin-bottom: 14px;
                  }
                  
                  h3 {
                      font-size: 16px;
                      font-weight: 600;
                      line-height: 24px;
                      color: #1D1B20;
                      margin-bottom: 12px;
                  }
                  
                  p {
                      font-size: 16px;
                      font-weight: 400;
                      line-height: 24px;
                  }
                  
                  .subhead {
                      font-size: 14px;
                      font-weight: 600;
                      line-height: 20px;
                      color: #49454F;
                  }
                  
                  .footnote {
                      font-size: 14px;
                      font-weight: 400;
                      line-height: 20px;
                      color: #49454F;
                  }
                  
                  ul {
                      margin-top: 10px;
                      padding-left: 0;
                      list-style: none;
                  }
                  
                  li {
                      margin-bottom: 8px;
                      font-size: 16px;
                      line-height: 24px;
                  }
                  
                  /* Style for list items that contain only a link (References section) */
                  h3 + ul li a,
                  li > a:only-child {
                      display: flex;
                      justify-content: space-between;
                      align-items: center;
                      background-color: #F7F2FA;
                      padding: 12px 16px;
                      border-radius: 12px;
                      text-decoration: none;
                      color: #1D1B20;
                      font-size: 14px;
                      font-weight: 600;
                      transition: background-color 0.3s, transform 0.2s;
                  }
                  
                  h3 + ul li a:hover,
                  li > a:only-child:hover {
                      transform: translateY(-2px);
                      text-decoration: none;
                  }
                  
                  h3 + ul li a::after,
                  li > a:only-child::after {
                      content: "›";
                      font-size: 24px;
                      font-weight: 300;
                      color: #49454F;
                      margin-left: 12px;
                  }
                  
                  /* Regular links (not in card format) */
                  a {
                      color: #6750A4; 
                      text-decoration: none;
                      transition: color 0.3s;
                  }
                  
                  a:hover {
                      text-decoration: underline;
                  }
                  
                  img {
                      max-width: 100%;
                      height: auto;
                      margin-top: 20px;
                      border-radius: 6px;
                  }
                  
                  iframe {
                      width: 100%;
                      height: 315px;
                      margin-top: 20px;
                      border: none;
                  }
                  
                  /* Manual content card class (if needed) */
                  .content-card {
                      display: flex;
                      justify-content: space-between;
                      align-items: center;
                      background-color: #F7F2FA;
                      padding: 12px 16px;
                      border-radius: 12px;
                      margin: 16px 0;
                      text-decoration: none;
                      color: #1D1B20;
                      transition: background-color 0.3s, transform 0.2s;
                  }
                  
                  .content-card:hover {
                      transform: translateY(-2px);
                      text-decoration: none;
                  }
                  
                  .content-card .card-title {
                      flex: 1;
                      font-size: 14px;
                      font-weight: 600;
                      line-height: 24px;
                      color: #1D1B20;
                      margin: 0;
                  }
                  
                  .content-card::after {
                      content: "›";
                      font-size: 24px;
                      font-weight: 300;
                      color: #49454F;
                      margin-left: 12px;
                  }

                  /* --- Dark Mode Styles --- */
                  .dark-mode {
                      background-color: #0F0D13;
                      color: #E6E0E9;
                  }

                  .dark-mode h1,
                  .dark-mode h2,
                  .dark-mode h3 {
                      color: #E6E0E9;
                  }
                  
                  .dark-mode a {
                      color: #6750A4;
                  }
                  
                  .dark-mode p,
                  .dark-mode li {
                      color: #E6E0E9;
                  }
                  
                  .dark-mode .subhead,
                  .dark-mode .footnote {
                      color: #CAC4D0;
                  }
                  
                  /* Dark mode for automatic card-styled links */
                  .dark-mode h3 + ul li a,
                  .dark-mode li > a:only-child {
                      background-color: #141218;
                      color: #E6E0E9;
                      border: 1px solid #49454F;
                  }
                  
                  .dark-mode h3 + ul li a::after,
                  .dark-mode li > a:only-child::after {
                      color: #938F99;
                  }
                  
                  /* Dark mode for manual content card */
                  .dark-mode .content-card {
                      background-color: #141218;
                      color: #E6E0E9;
                      border: 1px solid #49454F;
                  }
                  
                  .dark-mode .content-card .card-title {
                      color: #E6E0E9;
                  }

                  .dark-mode .content-card::after {
                      color: #938F99;
                  }
              </style>
            </head>
            <body>

    <!-- Inline script to apply dark mode class based on system setting immediately -->
    <script>
        const darkThemeKey = 'dark-mode';
        // System preference injected from Kotlin/Compose: true or false
        const systemPrefersDark = ${isSystemInDark};

        if (systemPrefersDark) {
            // Apply the dark-mode class if the system is dark
            document.body.classList.add(darkThemeKey);
        }
    </script>
  <h2>Topic 1: Benefits of Daily Walking</h2>

<img src="https://images.unsplash.com/photo-1511367461989-f85a21fda167?w=800" alt="Person walking on a scenic path outdoors" />

<h3>Key Takeaways:</h3>
<ul>
    <li>30 minutes a day can boost heart health and mood.</li>
    <li>Regulates blood sugar, blood pressure, and supports weight.</li>
    <li>Improves sleep and reduces stress.</li>
</ul>

<h3>Details:</h3>
<p>
    Walking is a low-impact exercise accessible to most. Regular walking lowers cardiovascular risk, improves mood via endorphin release, enhances sleep quality, and supports weight control.
</p>

<h3>Try This Today:</h3>
<p>
    Set a 30-minute daily walk goal or break into 10-minute segments.
</p>

<h3>References:</h3>
<ul>
    <li><a href="https://www.health.harvard.edu/topics/exercise-and-fitness">Harvard Health: Walking for Health</a></li>
    <li><a href="https://www.cdc.gov/physical-activity-basics/benefits/index.html">CDC: Benefits of Physical Activity</a></li>
</ul>
</body>
            </html>
        """.trimIndent()

    // Use AndroidView to embed the WebView into the Compose UI
    AndroidView(
        modifier = Modifier.fillMaxSize(), // Fill the whole area defined by the Composable
        factory = { context ->
            // Creates the WebView instance
            WebView(context).apply {
                // Configure settings
                settings.javaScriptEnabled = true
                settings.domStorageEnabled = true

                // IMPORTANT: Set WebChromeClient for fullscreen video support
                webChromeClient = WebChromeClient()

                // Load HTML content with base URL to ensure proper resource and script execution
                loadDataWithBaseURL(
                    "file:///android_asset/",
                    htmlContent,
                    "text/html",
                    "UTF-8",
                    null // History URL
                )
            }
        },
        // The update block can be left empty since the HTML content is static
        update = {}
    )
}
