import java.lang.System.`in`
import java.awt.event.KeyEvent
import java.io.*
import javax.swing.plaf.basic.BasicSplitPaneUI

class EditableBufferedReader(val input: InputStreamReader) : BufferedReader(input){
    fun setRaw(){
        val cmd = arrayOf("/bin/sh", "-c", "stty raw </dev/tty")
        val p=Runtime.getRuntime().exec(cmd).waitFor()
    }
    fun unsetRaw(){
        val cmd = arrayOf("/bin/sh", "-c", "stty -raw echo </dev/tty")
        Runtime.getRuntime().exec(cmd).waitFor()
    }
    override fun read() : Int {
        this.setRaw()
        val value = input.read()
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
        }

    }
    override fun readLine() : String {
        /* S'haurà de posar el setRaw i unsetRaw al principi
         * i final d'aquesta funció.
         */
        return ""
    }
}
