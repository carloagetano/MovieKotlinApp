package com.example.moviekotlinapp.data.utils

import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import android.util.Base64
import com.example.moviekotlinapp.data.model.Movie
import com.google.gson.Gson
import java.security.KeyStore
import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey
import javax.crypto.spec.IvParameterSpec

class CryptoManager {

    companion object {
        private const val ALGORITHM = KeyProperties.KEY_ALGORITHM_AES
        private const val BLOCK_MODE = KeyProperties.BLOCK_MODE_CBC
        private const val PADDING = KeyProperties.ENCRYPTION_PADDING_PKCS7
        private const val TRANSFORMATION = "$ALGORITHM/$BLOCK_MODE/$PADDING"
    }

    private val gson = Gson()

    private val keyStore = KeyStore.getInstance("AndroidKeyStore").apply {
        load(null)
    }

    private fun createKey(): SecretKey {
        return KeyGenerator.getInstance(ALGORITHM).apply {
            init(
                KeyGenParameterSpec.Builder(
                    "secret",
                    KeyProperties.PURPOSE_ENCRYPT or KeyProperties.PURPOSE_DECRYPT
                )
                    .setBlockModes(BLOCK_MODE)
                    .setEncryptionPaddings(PADDING)
                    .setUserAuthenticationRequired(false)
                    .setRandomizedEncryptionRequired(true)
                    .build()
            )
        }.generateKey()
    }

    private fun getKey(): SecretKey {
        val existingKey = keyStore.getEntry("secret", null) as? KeyStore.SecretKeyEntry
        return existingKey?.secretKey ?: createKey()
    }

    fun encryptMovie(movie: Movie): String {
        val movieString = gson.toJson(movie)
        val movieBytes = movieString.encodeToByteArray()

        val cipher = Cipher.getInstance(TRANSFORMATION)
        cipher.init(Cipher.ENCRYPT_MODE, getKey())
        val encryptedBytes = cipher.doFinal(movieBytes)
        val iv = cipher.iv

        val encryptedString = encryptedBytes.encodeToBase64()
        val ivString = iv.encodeToBase64()

        return "$ivString:$encryptedString"
    }

    fun decryptMovie(encryptedMovieString: String): Movie {
        val (ivString, encryptedString) = encryptedMovieString.split(":")
        val iv = ivString.decodeBase64()
        val encryptedBytes = encryptedString.decodeBase64()

        val cipher = Cipher.getInstance(TRANSFORMATION)
        cipher.init(Cipher.DECRYPT_MODE, getKey(), IvParameterSpec(iv))
        val decryptedBytes = cipher.doFinal(encryptedBytes)

        val decryptedMovieString = decryptedBytes.decodeToString()
        return gson.fromJson(decryptedMovieString, Movie::class.java)
    }

    // Kotlin extensions for Base64 encoding/decoding
    private fun ByteArray.encodeToBase64(): String = Base64.encodeToString(this, Base64.DEFAULT)
    private fun String.decodeBase64(): ByteArray = Base64.decode(this, Base64.DEFAULT)
}