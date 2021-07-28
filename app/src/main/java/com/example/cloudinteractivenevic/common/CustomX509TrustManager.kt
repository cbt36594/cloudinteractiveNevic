package com.example.cloudinteractivenevic.common

import android.util.Log
import com.example.cloudinteractivenevic.MyApplication
import okio.Buffer
import java.io.*
import java.security.GeneralSecurityException
import java.security.KeyStore
import java.security.KeyStoreException
import java.security.NoSuchAlgorithmException
import java.security.cert.CertificateException
import java.security.cert.CertificateFactory
import java.security.cert.X509Certificate
import java.util.*
import javax.net.ssl.KeyManagerFactory
import javax.net.ssl.TrustManager
import javax.net.ssl.TrustManagerFactory
import javax.net.ssl.X509TrustManager

class CustomX509TrustManager(keystore: KeyStore?) : X509TrustManager {

    // 建立憑證鏈，會依序檢查是否符合憑證
    private val trustManagers: MutableList<X509TrustManager?>

    init {
        trustManagers = ArrayList()
        // 加入原本的 TrustManager
        trustManagers.add(getDefaultTrustManager())
        // 加入指定 keystore TrustManager
        trustManagers.add(getTrustManager(keystore))
        try {
            trustManagers.add(trustManagerForCertificates(trustedCertificatesInputStream()))
        } catch (e: Exception) {
            Log.d("QR X509 ", e.message.toString())
            // 無法添加我們自己的 cert，使用系統預設的
        }
    }
    @Throws(CertificateException::class)
    override fun checkClientTrusted(chain: Array<X509Certificate>, authType: String) {
        for (trustManager in trustManagers) {
            try {
                trustManager?.checkClientTrusted(chain, authType)
                return  // 有其中一個 cert 通過，就當作通過
            } catch (e: CertificateException) {
                // 這個不通過，換下一個
            }
        }
        throw CertificateException("None of the TrustManagers trust this certificate chain")
    }

    @Throws(CertificateException::class)
    override fun checkServerTrusted(chain: Array<X509Certificate>, authType: String) {
        for (trustManager in trustManagers) {
            try {
                trustManager?.checkServerTrusted(chain, authType)
                return  // 有其中一個 cert 通過，就當作通過
            } catch (e: CertificateException) {
                // 這個不通過，換下一個
            }
        }
        throw CertificateException("None of the TrustManagers trust this certificate chain")
    }

    override fun getAcceptedIssuers(): Array<X509Certificate> {
        val certificates: MutableList<X509Certificate> = ArrayList()
        trustManagers.forEach {
            certificates.addAll(listOf(*it!!.acceptedIssuers))
        }

        return certificates.toTypedArray()
    }

    private fun getDefaultTrustManager() : X509TrustManager? {
        return getTrustManager(null)
    }

    private fun getTrustManager(keystore: KeyStore?): X509TrustManager? {
        return getTrustManager(TrustManagerFactory.getDefaultAlgorithm(), keystore)
    }

    /**
     * 只取出 keystore 中 X509 TrustManager
     */
    private fun getTrustManager(algorithm: String, keystore: KeyStore?): X509TrustManager? {
        val factory: TrustManagerFactory
        try {
            factory = TrustManagerFactory.getInstance(algorithm)
            factory.init(keystore)
            val trustManagerList = listOf(*factory.trustManagers)
            val x509TrustManagerList: MutableList<X509TrustManager> = ArrayList()

            trustManagerList.forEach {
                if (it is X509TrustManager) {
                    x509TrustManagerList.add(it)
                }
            }

            if (x509TrustManagerList.size > 0) {
                return x509TrustManagerList[0]
            }
        } catch (e: NoSuchAlgorithmException) {
            e.printStackTrace()
        } catch (e: KeyStoreException) {
            e.printStackTrace()
        }
        return null
    }

    /**
     * 將指定 InputStream 的的憑證建成 TrustManager
     */
    @Throws(GeneralSecurityException::class)
    private fun trustManagerForCertificates(input: InputStream): X509TrustManager? {
        val certificateFactory = CertificateFactory.getInstance("X.509")
        val certificates = certificateFactory.generateCertificates(input)
        require(!certificates.isEmpty()) {
            "Unexpected empty InputStream"
        }

        // 建立 keystore
        val password = "password".toCharArray() // Any password will work.
        val keyStore = newEmptyKeyStore(password)

        certificates.forEachIndexed { index, certificate ->
            val certificateAlias = index.toString()
            keyStore.setCertificateEntry(certificateAlias, certificate)
        }

        // 使用 keystore 建立 TrustManager
        val keyManagerFactory =
            KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm())
        keyManagerFactory.init(keyStore, password)
        val trustManagerFactory =
            TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm())
        trustManagerFactory.init(keyStore)

        val trustManagerList = listOf(trustManagerFactory.trustManagers)
        val x509TrustManagerList: MutableList<X509TrustManager> = ArrayList()

        trustManagerList.forEach {
            if (it is X509TrustManager) {
                x509TrustManagerList.add(it)
            }
        }

        return if (x509TrustManagerList.size > 0) {
            x509TrustManagerList[0]
        } else x509TrustManagerList[0]
    }

    @Throws(GeneralSecurityException::class)
    private fun newEmptyKeyStore(password: CharArray): KeyStore {
        return try {
            val keyStore = KeyStore.getInstance(KeyStore.getDefaultType())
            val input: InputStream? = null // By convention, 'null' creates an empty key store.
            keyStore.load(input, password)
            keyStore
        } catch (e: IOException) {
            throw AssertionError(e)
        }
    }

    companion object {
        fun getTrustManagers(keyStore: KeyStore?): Array<TrustManager> {
            return arrayOf(CustomX509TrustManager(keyStore))
        }

        private fun trustedCertificatesInputStream(): InputStream {

            return Buffer().writeUtf8(decodeCertificatePem("server.cer")).inputStream()
        }

        private fun decodeCertificatePem(str: String):String {
            try {
                val input: InputStream= BufferedInputStream(MyApplication.appContext.assets.open(str))
                val br = BufferedReader(
                    InputStreamReader(input, charset("UTF-8"))
                )
                input.use { input ->
                    val sb = StringBuilder()
                    var line = br.readLine()
                    while (line != null) {
                        sb.append(line).append("\n")
                        line = br.readLine()
                    }
                    return sb.toString()
                }

            } catch (e: NoSuchElementException) {
                throw IllegalArgumentException("failed to decode certificate", e)
            } catch (e: IllegalArgumentException) {
                throw IllegalArgumentException("failed to decode certificate", e)
            } catch (e: GeneralSecurityException) {
                throw IllegalArgumentException("failed to decode certificate", e)
            } catch (e: FileNotFoundException) {
                throw IllegalArgumentException("failed to decode certificate", e)
            } catch (e: IOException) {
                e.printStackTrace()
            }

            return ""
        }
    }


}