package com.example.smartfinance.ml

import android.content.Context
import android.util.Log
import com.example.smartfinance.data.model.Transaction
import com.example.smartfinance.data.model.TransactionType
import org.tensorflow.lite.Interpreter
import java.io.FileInputStream
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.nio.channels.FileChannel
import java.util.*

/**
 * This class handles the classification of transactions using a TensorFlow Lite model.
 * In a real app, you would train a model on transaction data to categorize expenses.
 */
class TransactionClassifier(private val context: Context) {
    private var interpreter: Interpreter? = null
    private val categories = listOf(
        "Groceries", "Dining", "Transportation", "Entertainment", "Utilities",
        "Shopping", "Health", "Travel", "Education", "Other"
    )

    init {
        try {
            loadModel()
        } catch (e: Exception) {
            Log.e("TransactionClassifier", "Error loading model", e)
        }
    }

    private fun loadModel() {
        // In a real app, you would load a trained TensorFlow Lite model
        // This is a placeholder for demonstration purposes
        // interpreter = Interpreter(loadModelFile())
    }

    private fun loadModelFile(): ByteBuffer {
        // Load model from assets folder
        val assetFileDescriptor = context.assets.openFd("transaction_classifier.tflite")
        val fileInputStream = FileInputStream(assetFileDescriptor.fileDescriptor)
        val fileChannel = fileInputStream.channel
        val startOffset = assetFileDescriptor.startOffset
        val declaredLength = assetFileDescriptor.declaredLength
        return fileChannel.map(FileChannel.MapMode.READ_ONLY, startOffset, declaredLength)
    }

    fun classifyTransaction(transaction: Transaction): String {
        // In a real app, this would use the TensorFlow model to classify the transaction
        // For demonstration, we'll use a simple rule-based approach

        val title = transaction.title.lowercase(Locale.getDefault())

        return when {
            title.contains("grocery") || title.contains("market") || title.contains("food") -> "Groceries"
            title.contains("restaurant") || title.contains("cafe") || title.contains("coffee") -> "Dining"
            title.contains("gas") || title.contains("uber") || title.contains("lyft") || title.contains("taxi") -> "Transportation"
            title.contains("movie") || title.contains("cinema") || title.contains("theater") -> "Entertainment"
            title.contains("electric") || title.contains("water") || title.contains("internet") || title.contains("bill") -> "Utilities"
            title.contains("clothing") || title.contains("mall") || title.contains("store") -> "Shopping"
            title.contains("doctor") || title.contains("pharmacy") || title.contains("medical") -> "Health"
            title.contains("hotel") || title.contains("flight") || title.contains("airbnb") -> "Travel"
            title.contains("school") || title.contains("course") || title.contains("book") -> "Education"
            else -> "Other"
        }
    }

    fun close() {
        interpreter?.close()
    }
}