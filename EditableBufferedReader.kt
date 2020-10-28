import java.io.BufferedReader
import java.io.IOException
import java.io.Reader

const val RIGHT_ARROW = Integer.MIN_VALUE
const val LEFT_ARROW = Integer.MIN_VALUE + 1
const val HOME = Integer.MIN_VALUE + 2 // mac: fn + esquerra
const val END = Integer.MIN_VALUE + 3 //mac: fn + dreta
const val INSERT = Integer.MIN_VALUE + 4 //mac: opt + up
const val DELETE = Integer.MIN_VALUE + 5 // mac: fn + backspace
const val BACKSPACE = Integer.MIN_VALUE + 5
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
    }
    override fun read(): Int {
        val firstChar = input.read()
        if (firstChar == 27) { // Sequencia d'escape
            return if (input.read() == 91) {
                when (input.read().toChar()) {
                    'C' -> RIGHT_ARROW
                    'D' -> LEFT_ARROW
                    'H' -> HOME
                    'F' -> END
                    '2' -> {
                        input.read()
                        return INSERT
                    }
                    '3' -> {
                        input.read()
                        return DELETE
                    }
                    else -> NOT_VALID_COMMAND
                }
            } else NOT_VALID_COMMAND
        } else {  // Caracter asci
            return if (firstChar == 127) BACKSPACE
            else {
                firstChar
            }
        }
    }
    @Throws(IOException::class)
    override fun readLine(): String {
        var buffer = StringBuilder()
        var pos = 0
        var ins = false
        var line = Line(buffer ,pos, ins)
        var ch: Number
        try{
            setRaw()
            do {
                ch = this.read()
                when(ch){
                    RIGHT_ARROW -> line.right()
                    LEFT_ARROW -> line.left()
                    HOME -> line.home()
                    END -> line.end()
                    DELETE -> line.delete()
                    INSERT -> line.toggle()
                    else -> line.write(ch.toChar())
                }
            } while(ch != ENTER)
        }catch (e: IOException){
            throw e
        }finally { unsetRaw() }
        return line.toString()
    }
}
