import java.io.*
fun main() {
    val input = EditableBufferedReader(InputStreamReader(System.`in`))
    var str:String?=null
    var caracter:Int = 0
    try{
        input.setRaw()
        //str=input.readLine()
        caracter = input.read()
        input.unsetRaw()
    }catch (e: IOException){e.printStackTrace()}
    println("\nline is: $caracter")
}
