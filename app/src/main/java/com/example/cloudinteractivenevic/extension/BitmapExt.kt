package com.example.cloudinteractivenevic.extension

import android.content.Context
import android.content.ContextWrapper
import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.net.Uri
import android.util.Log
import timber.log.Timber
import java.io.*
import java.net.URL
import java.net.URLConnection
import java.util.*

fun Bitmap.saveToFile(filePath: String, fileName: String, quality: Int = 100): File? {
    return try {
        val file = File(filePath, fileName)
        val outputStream: OutputStream = FileOutputStream(file)
        compress(Bitmap.CompressFormat.JPEG, quality, outputStream)
        outputStream.flush()
        outputStream.close()
        file
    } catch (e: Exception) {
        null
    }
}

fun Bitmap.toByteArray(quality: Int = 100): ByteArray {
    val stream = ByteArrayOutputStream()
    compress(Bitmap.CompressFormat.JPEG, quality, stream)
    return stream.toByteArray()
}

fun vectorDrawableToBitmap(drawable: Drawable): Bitmap{

        val bitmap: Bitmap = Bitmap.createBitmap(
            drawable.intrinsicWidth,
            drawable.intrinsicHeight,
            Bitmap.Config.ARGB_8888
        )
        val canvas = Canvas(bitmap)
        drawable.setBounds(0, 0, canvas.width, canvas.height)
        drawable.draw(canvas)
    return bitmap

}

fun Bitmap2Bytes(bm: Bitmap): ByteArray {
    val stream = ByteArrayOutputStream()
    bm.compress(Bitmap.CompressFormat.PNG, 90, stream)
    return stream.toByteArray()
}

fun decodeByteArray(content: ByteArray?, reqWidth: Int, reqHeight: Int): Bitmap? {
    var stream = ByteArrayInputStream(content)
    return try {
        // First decode with inJustDecodeBounds=true to check dimensions
        val options = BitmapFactory.Options()
        options.inJustDecodeBounds = true //獲得圖片的原始寬高信息，不返回整張圖片
        BitmapFactory.decodeStream(stream, null, options)
        stream.close()

        // Calculate inSampleSize
        options.inSampleSize = calculateWidthInSampleSize(
            options,
            reqWidth,
            reqHeight
        )
        stream = ByteArrayInputStream(content)
        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false
        BitmapFactory.decodeStream(stream, null, options)
    } catch (e: java.lang.Exception) {
        throw RuntimeException(e)
    } finally {
        try {
            stream.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }
}

fun calculateWidthInSampleSize(
    options: BitmapFactory.Options, reqWidth: Int, reqHeight: Int
): Int {
    // Raw width of image
    val width = options.outWidth
    val height = options.outHeight
    var inSampleSize = 1

    // Calculate the largest inSampleSize value that is a power of 2 and keeps both
    // height and width larger than the requested height and width.
    while (width / inSampleSize > reqWidth || height / inSampleSize > reqHeight) {
        inSampleSize *= 2
    }
    return inSampleSize
}

fun decodeSampledBitmapFromResource(
    res: Resources?, resId: Int,
    reqWidth: Int, reqHeight: Int
): Bitmap? {

    // First decode with inJustDecodeBounds=true to check dimensions
    val options = BitmapFactory.Options()
    options.inJustDecodeBounds = true
    BitmapFactory.decodeResource(res, resId, options)

    // Calculate inSampleSize
    options.inSampleSize = calculateWidthInSampleSize(options, reqWidth, reqHeight)

    // Decode bitmap with inSampleSize set
    options.inJustDecodeBounds = false
    return BitmapFactory.decodeResource(res, resId, options)
}

fun urlToBitmap(url: String) : Bitmap? {

        var bitmap: Bitmap? = null
        try {
            val inputStream: InputStream = URL(url).openStream()
            bitmap = BitmapFactory.decodeStream(inputStream)
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return bitmap
}
fun URLConnection.toBitmap(): Bitmap? {
    return try {
        BitmapFactory.decodeStream(getInputStream())
    } catch (e: IOException){
        Timber.d("TestBit: inputStream $e")
        null
    }
}

fun Bitmap.saveToInternalStorage(context: Context): Uri?{
    // get the context wrapper instance
    val wrapper = ContextWrapper(context)

    // initializing a new file
    // bellow line return a directory in internal storage
    var file = wrapper.getDir("images", Context.MODE_PRIVATE)

    // create a file to save the image
    file = File(file, "${UUID.randomUUID()}.jpg")

    return try {
        // get the file output stream
        val stream: OutputStream = FileOutputStream(file)

        // compress bitmap
        compress(Bitmap.CompressFormat.JPEG, 100, stream)

        // flush the stream
        stream.flush()

        // close stream
        stream.close()

        // return the saved image uri
        Uri.parse(file.absolutePath)
    } catch (e: IOException){ // catch the exception
        e.printStackTrace()
        null
    }
}

fun reads (ins: InputStream): ByteArray? {
    val outStream = ByteArrayOutputStream()
    val b = ByteArray(1024)
    var len = 0
    while (ins.read(b).also { len = it } != -1) {
        outStream.write(b, 0, len)
    }
    outStream.close()
    ins.close()
    return outStream.toByteArray()
}