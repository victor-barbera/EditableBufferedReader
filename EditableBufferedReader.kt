import java.io.BufferedReader
import java.io.Reader

class EditableBufferedReader(val input: Reader) : BufferedReader(input) {
    var line = Line(buffer = StringBuffer(), pos = 0)
    fun setRaw() {
        val cmd = arrayOf("/bin/sh", "-c", "stty raw </dev/tty")
        Runtime.getRuntime().exec(cmd).waitFor()
    }

    fun unsetRaw() {
        val cmd = arrayOf("/bin/sh", "-c", "stty -raw echo </dev/tty")
        Runtime.getRuntime().exec(cmd).waitFor()
    }

    override fun read(): Int {
        // Al final definim les constats.
        val firstChar = input.read()
        if (firstChar == 27) { // Sequencia d'escape
            return if (input.read() == 91) {
                when (input.read().toChar()) {
                    'C' -> Constants.RIGHT_ARROW
                    'D' -> Constants.LEFT_ARROW
                    'H' -> Constants.HOME
                    'F' -> Constants.END
                    '2' -> Constants.INSERT
                    '3' -> Constants.DELETE
                    else -> Constants.NOT_VALID_COMMAND
                }
            } else Constants.NOT_VALID_COMMAND
        } else {  // Caracter asci
            if (firstChar == 127) return Constants.BACKSPACE
            else {
                return firstChar
            }

        }

    }

    override fun readLine(): String {
        /*do{
            var value = read()
            if(value != 91 && value != 127)line.buffer = line.buffer.insert(line.pos,value.toChar())
            //print("${EditableBufferedReader.Constants.ESC}[2J") //netejar pantalla
            Runtime.getRuntime().exec("clear")
            print(line.buffer)
            line.write()

        }while (value != 13) //13 is ASCII for return
        return line.buffer.toString()
*/      do{
            val firstChar = input.read()
            if (firstChar == 27) { // Sequencia d'escape
                if (input.read() == 91) {
                    when (input.read().toChar()) {
                        'C' -> line.move(true)
                        'D' -> line.move(false)
                        'H' -> line.home()
                        'F' -> line.end()
                        '2' -> line.delete(true)
                        '3' -> line.delete(false)
                    }
                }
            } else {  // Caracter asci
                if (firstChar == 127) line.delete(true)
                else {
                    line.buffer = line.buffer.insert(line.pos, firstChar.toChar())
                    line.pos++
                    //print("${EditableBufferedReader.Constants.ESC}[2J")
                    print(line.buffer)
                    Runtime.getRuntime().exec("clear")
                }
            }
        }while (firstChar != 13) //13 is ASCII for return
        return line.buffer.toString()
    }
}
