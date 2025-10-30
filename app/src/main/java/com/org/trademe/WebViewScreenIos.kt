package com.org.trademe

import android.webkit.WebChromeClient
import android.webkit.WebView
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView

@Composable
fun WebViewScreenIos() {
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
                /* Base styles for Light Mode */
                img, video, iframe {
                    max-width: 100%;
                }
            
                body {
                    font-family: -apple-system, BlinkMacSystemFont, "SF Pro Display", "SF Pro Text", system-ui, sans-serif;
                    font-size: 16px;
                    line-height: 24px;
                    padding: 10px;
                    background-color: #FFFFFF; /* Light Mode Background */
                    color: #000000; /* Primary text color */
                    max-width: 900px;
                    margin: 0 auto;
                    transition: background-color 0.3s, color 0.3s;
                }
                
                h1 {
                    font-size: 24px;
                    font-weight: 500;
                    line-height: 32px;
                    color: #000000;
                    margin-bottom: 16px;
                }
                
                h2 {
                    font-size: 22px;
                    font-weight: 500;
                    line-height: 28px;
                    color: #000000;
                    margin-bottom: 14px;
                }
                
                h3 {
                    font-size: 16px;
                    font-weight: 600;
                    line-height: 24px;
                    color: #000000;
                    margin-bottom: 12px;
                }
                
                p {
                    font-size: 16px;
                    font-weight: 400;
                    line-height: 24px;
                    color: #000000;
                }
                
                /* Subhead styles */
                .subhead {
                    font-size: 14px;
                    font-weight: 600;
                    line-height: 20px;
                    color: #8A8A8E;
                }
                
                /* Footnote styles */
                .footnote {
                    font-size: 14px;
                    font-weight: 400;
                    line-height: 20px;
                    color: #8A8A8E;
                }
                
                ul {
                    margin-top: 10px;
                    padding-left: 20px;
                }
                
                li {
                    margin-bottom: 8px;
                    font-size: 16px;
                    line-height: 24px;
                    color: #000000;
                }
                
                a {
                    color: #007AFF; /* Primary link color */
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
            
                /* Content Card Styles */
                .content-card {
                    display: flex;
                    justify-content: space-between;
                    align-items: center;
                    background-color: #FFFFFF;
                    padding: 12px 16px;
                    border-radius: 12px;
                    margin: 16px 0;
                    text-decoration: none;
                    color: #000000;
                    transition: background-color 0.3s, transform 0.2s;
                    border: 1px solid #E5E5EA;
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
                    color: #000000;
                    margin: 0;
                }
                
                .content-card::after {
                    content: "›";
                    font-size: 28px;
                    font-weight: 300;
                    color: #8A8A8E;
                    flex-shrink: 0;
                }
            
                /* --- Dark Mode Styles --- */
                .dark-mode {
                    background-color: #000000; /* Main BG */
                    color: #FFFFFF;
                }
            
                .dark-mode h1,
                .dark-mode h2,
                .dark-mode h3 {
                    color: #FFFFFF;
                }
                
                .dark-mode a {
                    color: #0A84FF;
                }
                
                .dark-mode p,
                .dark-mode li {
                    color: #FFFFFF;
                }
                
                .dark-mode .subhead,
                .dark-mode .footnote {
                    color: #EBEBF5;
                }
                
                /* Dark Mode Content Card */
                .dark-mode .content-card {
                    background-color: #1C1C1E; /* Content Card background */
                    color: #FFFFFF;
                    border: 1px solid #38383A;
                }
                
                .dark-mode .content-card .card-title {
                    color: #FFFFFF;
                }
                
                .dark-mode .content-card::after {
                    color: #EBEBF5;
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

   <a href="https://www.health.harvard.edu/topics/exercise-and-fitness" class="content-card">
       <div class="card-title">Harvard Health: Walking for Health</div>
   </a>

   <a href="https://www.cdc.gov/physical-activity-basics/benefits/index.html" class="content-card">
       <div class="card-title">CDC: Benefits of Physical Activity</div>
   </a>
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
