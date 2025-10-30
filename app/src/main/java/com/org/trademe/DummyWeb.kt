package com.org.trademe

import android.webkit.WebChromeClient
import android.webkit.WebView
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView

@Composable
fun RichContentWebView() {
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
                    
                    /* Ensuring all media elements are fluid and won't cause horizontal scrolling */
                    img, video, iframe {
                        max-width: 100%;
                    }

                    body {
                        /* Updated Font Family and Size as requested by the user */
                        font-family: Roboto, "Proxima Nova", "Noto Sans", Helvetica, Arial, sans-serif;
                        font-size: 18px; /* Set to 18px as requested */
                        
                        padding: 20px;
                        background-color: #ffffff; /* Light Mode Background */
                        color: #333; /* Light Mode Text */
                        /* Using a max-width on the body for better readability on very large screens */
                        max-width: 900px;
                        margin: 0 auto;
                        transition: background-color 0.3s, color 0.3s; /* Smooth transition */
                    }
                    
                    h1 {
                        color: #2c3e50; /* Light Mode Heading Color */
                    }
                    
                    p {
                        font-size: 18px; 
                        line-height: 1.6;
                    }
                    
                    ul {
                        margin-top: 10px;
                        padding-left: 20px;
                    }
                    
                    li {
                        margin-bottom: 8px;
                    }
                    
                    a {
                        color: #2980b9; /* Light Mode Link Color */
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

                    /* --- Dark Mode Styles --- */
                    .dark-mode {
                        background-color: #1e1e2d; /* Darker background */
                        color: #e0e0e0; /* Lighter text */
                    }

                    .dark-mode h1 {
                        color: #ff6e8c; /* Accent color for dark mode headings */
                    }
                    
                    .dark-mode a {
                        color: #79d7d3; /* Dark Mode Link Color */
                    }

                    /* --- Toggle Button Styles --- */
                    #theme-toggle {
                        background-color: #3498db;
                        color: white;
                        padding: 8px 15px;
                        border: none;
                        border-radius: 4px;
                        cursor: pointer;
                        transition: background-color 0.3s;
                        float: right;
                        font-size: 14px;
                        margin-bottom: 20px;
                    }
                    
                    #theme-toggle:hover {
                        background-color: #2980b9;
                    }

                    .dark-mode #theme-toggle {
                        background-color: #ff6e8c;
                    }
                    .dark-mode #theme-toggle:hover {
                        background-color: #e94560;
                    }
                </style>
            </head>
            <body>

                <!-- Theme Toggle Button -->
                <button id="theme-toggle">Toggle Dark Mode</button>
                
                <h1>Welcome to the Multimedia Showcase</h1>

                <p>
                    This page demonstrates how to combine various types of content using HTML. You can include text, images, videos, and links to create engaging and informative web experiences.
                    
                    For more tutorials, visit <a href="https://developer.mozilla.org/en-US/">MDN Web Docs</a> or explore <a href="https://www.w3schools.com/">W3Schools</a>.
                </p>

                <p>
                    Multimedia content is essential for modern websites. It helps convey information more effectively and keeps users engaged. Whether you're building a blog, a portfolio, or an educational platform, integrating rich media is a great way to enhance your content.
                </p>

                <ul>
                    <li>**Responsive design** for all devices is now enabled with the viewport tag.</li>
                    <li><a href="https://www.youtube.com/">Find tutorials on YouTube</a></li>
                    <li>Use high-quality images to support your message</li>
                    <li><a href="https://unsplash.com/">Find free images on Unsplash</a></li>
                    <li>Keep your content accessible and readable with **18px** font size.</li>
                </ul>

                <!-- Image is now fully responsive, constrained by max-width: 100% -->
                <img src="https://media.istockphoto.com/id/1266230179/photo/human-heart-with-blood-vessels.jpg?s=612x612&w=0&k=20&c=9H1v3t5nkmwmw_IyRsqR6vqqSj_OiZgHGWxFvJKN764=" alt="Image of a human heart with blood vessels showing blood flow and vessels." />
                
                <!-- Iframe is fully responsive (width: 100%) and uses clean attributes -->
                <iframe 
                    src="https://www.youtube.com/embed/zUyuUoU7lAQ?si=WbgjhgZCjFBq_9Zy" 
                    title="YouTube video player explaining human anatomy" 
                    frameborder="0" 
                    allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share" 
                    referrerpolicy="strict-origin-when-cross-origin" 
                    allowfullscreen>
                </iframe>

                <script>
                    document.addEventListener('DOMContentLoaded', () => {
                        const toggleButton = document.getElementById('theme-toggle');
                        const body = document.body;
                        const darkThemeKey = 'dark-mode';

                        // 1. Load saved theme preference
                        const savedTheme = localStorage.getItem(darkThemeKey);
                        if (savedTheme === 'true') {
                            body.classList.add(darkThemeKey);
                            toggleButton.textContent = 'Toggle Light Mode';
                        }

                        // 2. Handle theme toggling
                        toggleButton.addEventListener('click', () => {
                            const isDarkMode = body.classList.toggle(darkThemeKey);
                            
                            // Update button text
                            toggleButton.textContent = isDarkMode ? 'Toggle Light Mode' : 'Toggle Dark Mode';

                            // Save preference to localStorage
                            localStorage.setItem(darkThemeKey, isDarkMode);
                        });
                    });
                </script>
            </body>
            </html>
    """.trimIndent()

    AndroidView(
        modifier = Modifier.fillMaxSize(), // Fill the whole area defined by the Composable
        factory = { context ->
            // Creates the WebView instance
            WebView(context).apply {
                // Configure settings
                settings.javaScriptEnabled = true
                settings.domStorageEnabled = true

                // Load HTML content with base URL to ensure localStorage and video/image loading work
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
