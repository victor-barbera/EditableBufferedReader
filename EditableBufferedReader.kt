import java.lang.System.`in`
import java.awt.event.KeyEvent
import java.io.*
import javax.swing.plaf.basic.BasicSplitPaneUI

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
        val p=Runtime.getRuntime().exec(cmd).waitFor()
    }
    fun unsetRaw(){
        val cmd = arrayOf("/bin/sh", "-c", "stty -raw echo </dev/tty")
        Runtime.getRuntime().exec(cmd).waitFor()
    }
    override fun read() : Int {
        // Al final definim les constats.
        val firstChar = input.read()
        if(firstChar == 27) { // Sequencia d'escape
            if(input.read() == 91) {
                when(input.read().toChar()) {
                    'C'-> return RIGHT_ARROW
                    'D'-> return LEFT_ARROW
                    'H'-> return HOME
                    'F'-> return END
                    '2'-> return INSERT
                    '3'-> return DELETE
                    else -> return NOT_VALID_COMMAND
                }
            } else return NOT_VALID_COMMAND
        }else {  // Caracter asci
            if(firstChar == 127) return BACKSPACE
            return firstChar
        }
        /*val value = input.read()
        when(value) {
            KeyEvent.VK_RIGHT-> {
                return 0
            }
            KeyEvent.VK_LEFT-> {
                return 1
            }
            KeyEvent.VK_HOME-> {
                return 2
            }
            KeyEvent.VK_END-> {
                return 4
            }
            KeyEvent.VK_DELETE-> {
                return 5
            }
            KeyEvent.VK_BACK_SPACE -> {
                return 6
            }
            else -> {
                return value
            }
        }*/
    }
    override fun readLine() : String {
        /* S'haurà de posar el setRaw i unsetRaw al principi
         * i final d'aquesta funció.
         */
        return ""
    }
}
