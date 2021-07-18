package fr.shining_cat.blocketgithubapp

import java.io.BufferedReader
import java.io.InputStreamReader

abstract class AbstractTest {

    protected fun readFile(filePath: String): String {
        val bufferedReader =
            BufferedReader(InputStreamReader(javaClass.classLoader?.getResourceAsStream(filePath)))
        val stringBuilder = StringBuilder()
        var line = bufferedReader.readLine()
        while (line != null) {
            stringBuilder.append(line)
            line = bufferedReader.readLine()
        }
        return stringBuilder.toString()
    }
}