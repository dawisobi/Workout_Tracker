package com.example.workouttracker.data.repository

import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import java.net.HttpURLConnection
import java.net.MalformedURLException
import java.net.URL

class FileDownloadRepository {
    suspend fun downloadFile(url: String, destination: File): Boolean {
        try {

            Log.d("FileDownload", "Downloading file from URL: $url")
            Log.d("FileDownload", "Destination file path: ${destination.absolutePath}")

            // Check if URL is valid
            val parsedUrl = try {
                URL(url)
            } catch (e: MalformedURLException) {
                Log.e("FileDownload", "Invalid URL: $url", e)
                return false
            }

            // Open connection
            val connection = withContext(Dispatchers.IO) {
                Log.d("FileDownload", "Opening connection...")
                val conn = (parsedUrl.openConnection() as HttpURLConnection).apply {
                    requestMethod = "GET"
                    connectTimeout = 5000 // 5 seconds timeout
                    readTimeout = 5000 // 5 seconds timeout
                }


            // Ensure the connection is successful
                val responseCode = conn.responseCode
                Log.d("FileDownload", "Response code: $responseCode")
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    conn
                } else {
                    Log.d("FileDownload", "Failed to connect, Response code: $responseCode")
                    return@withContext null
                }
            }

            connection?.inputStream?.use { input ->
                withContext(Dispatchers.IO) {
                    destination.outputStream().use { output ->
                        Log.d("FileDownload", "Starting file copy")
                        input.copyTo(output)

                        // Check if the file is downloaded and has content
                        if (destination.exists() && destination.length() > 0) {
                            Log.d("FileDownload", "File downloaded successfully: ${destination.absolutePath}, size: ${destination.length()} bytes = ${destination.length() / 1024} KB")
                        } else {
                            Log.d("FileDownload", "File not downloaded or empty: ${destination.absolutePath}, size: ${destination.length()} bytes")
                        }
                    }
                }
            }

            return true
        } catch (e: Exception) {
            e.printStackTrace()
            Log.e("FileDownload", "Download failed", e)
            return false
        }
    }
}