package com.lanyou.lib_base.utils

import com.lanyou.lib_base.utils.Base64Utils.encode
import com.lanyou.lib_base.utils.Base64Utils.decode
import com.lanyou.lib_base.utils.AESUtil
import com.lanyou.lib_base.utils.Base64Utils
import kotlin.Throws
import androidx.annotation.RequiresApi
import android.os.Build
import java.lang.Exception
import java.nio.charset.StandardCharsets
import java.security.NoSuchAlgorithmException
import java.security.SecureRandom
import java.util.*
import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.SecretKeySpec

class AESUtil {
    /**
     * @Title:AESUtil
     * @Description:自定义偏移量构造AES加密工具，使用默认的KEY
     * @param ivParameter 原始偏移量字符串
     */
    constructor(ivParameter: String) {
        iv = IvParameterSpec(ivParameter.toByteArray())
        var keyGenerator: KeyGenerator? = null
        try {
            keyGenerator = KeyGenerator.getInstance("AES")
        } catch (e: NoSuchAlgorithmException) {
            e.printStackTrace()
        }
        keyGenerator!!.init(128, SecureRandom(KEY.toByteArray()))
        val generatedSecretKey = keyGenerator.generateKey()
        val encodedBytes = generatedSecretKey.encoded
        key = SecretKeySpec(KEY.toByteArray(), ENCRYPTION_ALGORITHM)
    }

    /**
     * @Title:AESUtil
     * @Description:自定义偏移量和密钥构造AES加密工具
     * @param secretKey 原始密钥字符串
     * @param ivParameter 原始偏移量字符串
     */
    constructor(secretKey: String, ivParameter: String) {
        iv = IvParameterSpec(ivParameter.toByteArray())
        key = SecretKeySpec(secretKey.toByteArray(), ENCRYPTION_ALGORITHM)
    }

    /**
     * @Title: encrypt
     * @Description: AES加密
     * @param enString 用来加密的明文
     * @throws Exception
     * @return String 加密后密文
     */
    fun encrypt(enString: String): String {
        // Base64.Encoder base64 = Base64.getEncoder();
        var cipher: Cipher? = null
        var encrypted: ByteArray? = null
        try {
            cipher = Cipher.getInstance(CIPHER_PARAM)
            cipher.init(Cipher.ENCRYPT_MODE, key, iv)
            encrypted = cipher.doFinal(enString.toByteArray(charset("utf-8")))
        } catch (e: NoSuchAlgorithmException) {
            e.printStackTrace()
        } catch (e: Exception) {
            e.printStackTrace()
        }

        //return base64.encodeToString(encrypted);
        return encode(encrypted!!)
    }

    /**
     * @Title: decrypt
     * @Description: AES解密
     * @param deString 用来解密的密文
     * @throws Exception
     * @return String 解密后的明文
     */
    @Throws(Exception::class)
    fun decrypt(deString: String?): String? {
        return try {
            // Base64.Decoder base64 = Base64.getDecoder();
            val cipher = Cipher.getInstance(CIPHER_PARAM)
            cipher.init(Cipher.DECRYPT_MODE, key, iv)
            // byte[] decrypted = base64.decode(deString);
            val decrypted = decode(deString!!)
            val original = cipher.doFinal(decrypted)
            String(original, StandardCharsets.UTF_8)
        } catch (ex: Exception) {
            null
        }
    }

    companion object {
        const val ENCRYPTION_ALGORITHM = "AES"
        const val CIPHER_PARAM = "AES/CBC/PKCS5Padding"
        private const val DEFAULT_KEY_AND_IV = "abcdef0123456789" //带偏移量16位长度
        private const val KEY = "ihlih*0039JOHT$)(PIJY*(()JI^)IO%" //key

        /**
         * @Title: getInstance
         * @Description: 单例AES加密工具，使用默认的密钥以及偏移量
         * @return AESUtil
         * @author hanzhiyong
         */
        var instance: AESUtil? = null
            get() {
                if (field == null) {
                    field = AESUtil(DEFAULT_KEY_AND_IV)
                }
                return field
            }
            private set

        /** 偏移量  */
        private lateinit var iv: IvParameterSpec

        /** 密钥  */
        private lateinit var key: SecretKeySpec

        /**
         * 取得密钥
         *
         * @throws Exception
         */
        @RequiresApi(api = Build.VERSION_CODES.O)
        fun getKey(): String {
            val base64 = Base64.getEncoder()
            println("密钥String:" + String(key.encoded))
            return base64.encodeToString(key.encoded)
        }

        @RequiresApi(api = Build.VERSION_CODES.O)
        fun getKey(str: String): String {
            val base64 = Base64.getEncoder()
            return base64.encodeToString(str.toByteArray())
        }

        /**
         * 取得偏移量
         */
        @RequiresApi(api = Build.VERSION_CODES.O)
        @Throws(Exception::class)
        fun getIv(): String {
            val base64 = Base64.getEncoder()
            println("偏移量String:" + String(iv.iv))
            return base64.encodeToString(iv.iv)
        }
    }
}