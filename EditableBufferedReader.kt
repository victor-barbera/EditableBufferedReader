import java.io.*

class EditableBufferedReader(val input: Reader) : BufferedReader(input){
    companion object {
        const val RIGHT_ARROW = 300
        const val LEFT_ARROW = 301
        const val HOME = 302 // mac: fn+esquerra
        const val END = 303 // mac: fn+dreta
        const val INSERT = 304
        const val DELETE = 305 // mac: fn+backspace
        const val BACKSPACE = 127
        const val NOT_VALID_COMMAND = -1
    }
    fun setRaw(){
        val cmd = arrayOf("/bin/sh", "-c", "stty raw </dev/tty")
        Runtime.getRuntime().exec(cmd).waitFor()
    }
    fun unsetRaw(){
        val cmd = arrayOf("/bin/sh", "-c", "stty -raw echo </dev/tty")
        Runtime.getRuntime().exec(cmd).waitFor()
    }
    override fun read() : Int {
        // Al final definim les constats.
        val firstChar = input.read()
        if(firstChar == 27) { // Sequencia d'escape
            return if(input.read() == 91) {
                when(input.read().toChar()) {
                    'C'-> RIGHT_ARROW
                    'D'-> LEFT_ARROW
                    'H'-> HOME
                    'F'-> END
                    '2'-> INSERT
                    '3'-> DELETE
                    else -> NOT_VALID_COMMAND
                }
            } else NOT_VALID_COMMAND
        }else return firstChar // Caracter asci
    }

    override fun readLine() : String {
        /* 
        * En aquest apartat aquesta funció l'utilitzem per testejar read().
        * Imprimim per pantalla tots els valors rebuts.
        */
        var buffer = ""
        do{
            var value = read()
            if(value!= NOT_VALID_COMMAND){
                buffer = buffer.plus(value.toChar())
                print(value.toChar())
            }
        }while (value != 13)
        return buffer
    }
}
