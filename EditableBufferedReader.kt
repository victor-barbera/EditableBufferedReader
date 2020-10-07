import java.io.*

class EditableBufferedReader(val input: Reader) : BufferedReader(input){
    companion object {
        const val RIGHT_ARROW = 300
        const val LEFT_ARROW = 301
        const val HOME = 302 // mac: ^+a
        const val END = 303
        const val INSERT = 304
        const val DELETE = 305 // mac: fn+backspace
        const val BACKSPACE = 306
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
        }else {  // Caracter asci
            if(firstChar == 127) return BACKSPACE
            return firstChar
        }

    }
    override fun readLine() : String {
        var buffer = ""
        do{
            var value = read()
            buffer = buffer.plus(value.toChar())
            print(value.toChar())
        }while (value != 13)
        return buffer
    }
}
