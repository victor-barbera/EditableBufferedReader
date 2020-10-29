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

const val RIGHT_KEY = "\u001b[1C"
const val CLEAR = "\u001b[1000D"
const val LEFT_KEY = "\u001b[1D"
const val INSERT_KEY = "\u001b[1@"
const val BKSP_KEY = "\u001b[1D" + "\u001b[1P" //left + Delete
const val DEL_KEY = "\u001b[1P" //Delete
const val HOME_KEY = "\u001b[1G" // Move cursor to col 1 in current row.
const val END_KEY = "\u001b["

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
        var console = Console()
        var line = Line(console)
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
