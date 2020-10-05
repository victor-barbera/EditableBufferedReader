import java.io.*
fun main() {
    val input = EditableBufferedReader(InputStreamReader(System.`in`))
    var str:String?=null
    var int=0
    try{
        //str=input.readLine()
        int=input.read()
        input.unsetRaw()
    }catch (e: IOException){e.printStackTrace()}
    println("\nline is: $int")
}
