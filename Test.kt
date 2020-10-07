import java.io.*
fun main() {
    val input = EditableBufferedReader(InputStreamReader(System.`in`))
    var line=""
    //var caracter:Int = 0
    try{
        input.setRaw()
        line=input.readLine()
        //caracter = input.read()
        input.unsetRaw()
    }catch (e: IOException){e.printStackTrace()}
    println("\nline is: $line")
}
