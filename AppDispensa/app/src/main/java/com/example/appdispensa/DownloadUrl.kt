package com.example.appdispensa

import android.util.Log
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader
import java.lang.Exception
import java.net.HttpURLConnection
import java.net.URL

class DownloadUrl {

    fun retrieveUrl(url:String):String{

        var urlData : String = ""
        var httpUrlConnection : HttpURLConnection ?= null
        var inputStream : InputStream ?= null

        try {
            var getUrl : URL = URL(url)
            println(getUrl.toString())
            httpUrlConnection = getUrl.openConnection() as HttpURLConnection?
            httpUrlConnection!!.connect()
            inputStream = httpUrlConnection.inputStream
            var bufferedReader : BufferedReader = BufferedReader(InputStreamReader(inputStream))
            var sb : StringBuffer = StringBuffer()

            val iterator = bufferedReader.lineSequence().iterator()
            var line:String = ""
            while(iterator.hasNext()){
                line = iterator.next()
                sb.append(line)
                if(line.contains("OK")){
                    println("break")
                }
            }
            bufferedReader.close()
            println(sb.toString())
            urlData = sb.toString()

        }
        catch(e:Exception){
            println(e.toString())
            Log.d("Exception",e.toString())
        }
        finally {
            inputStream!!.close()
            httpUrlConnection!!.disconnect()

        }

        return urlData
    }
}