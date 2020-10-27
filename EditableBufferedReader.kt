import java.io.BufferedReader
import java.io.IOException
import java.io.Reader

const val RIGHT = Integer.MIN_VALUE
const val LEFT = Integer.MIN_VALUE + 1
const val HOME = Integer.MIN_VALUE + 2 // mac: ^+a
const val END = Integer.MIN_VALUE + 3
const val INSERT = Integer.MIN_VALUE + 4 // ^[[2~
const val DELETE = Integer.MIN_VALUE + 5 // mac: fn+backspace  ^[[3~
const val BKSP = Integer.MIN_VALUE + 6
const val NOT_VALID_COMMAND = -1
const val ENTER = 13

class EditableBufferedReader(val input: Reader) : BufferedReader(input) {
    companion object{
        fun setRaw() {
            val cmd = arrayOf("/bin/bash", "-c", "stty -echo raw </dev/tty")
            Runtime.getRuntime().exec(cmd).waitFor()
        }

        fun unsetRaw() {
            val cmd = arrayOf("/bin/bash", "-c", "stty -raw echo </dev/tty")
            Runtime.getRuntime().exec(cmd).waitFor()
        }
        var line = Line()
        var console = Console()
    }
    override fun read(): Int {
        val firstChar = input.read()
        if (firstChar == 27) { // Sequencia d'escape
            return if (input.read() == 91) {
                when (input.read().toChar()) {
                    'C' -> RIGHT
                    'D' -> LEFT
                    'H' -> HOME
                    'F' -> END
                    '2' -> INSERT
                    '3' -> DELETE
                    else -> NOT_VALID_COMMAND
                }
            } else NOT_VALID_COMMAND
        }
        else {  // Caracter asci
            return if (firstChar == 127) BKSP
            else firstChar
        }
    }
    @Throws(IOException::class)
    override fun readLine(): String {
        try{
            setRaw()
            var ch = this.read()
            while(ch != ENTER){
                when(ch){
                    RIGHT -> line.right()
                    LEFT -> line.left()
                    HOME -> line.home()
                    END -> line.end()
                    DELETE -> line.delete()
                    BKSP -> line.bksp()
                    INSERT -> line.toggle()
                    else -> line.write(ch.toChar())
                }
                ch = this.read()
            }
        }catch (e: IOException){
            throw e
        }finally { unsetRaw() }
        return line.toString()
    }
}
