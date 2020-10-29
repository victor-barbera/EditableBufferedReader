
import java.lang.StringBuilder
import kotlin.properties.Delegates
import kotlin.reflect.KProperty

class Console {
    companion object{
        //Up: \u001b[{n}A  <- n es numero d moviments
        //Down: \u001b[{n}B
        //Right: \u001b[{n}C
        //Left: \u001b[{n}D
        /*
        //Clear Screen: \u001b[{n}J clears the screen  .flush()??
        n=0 clears from cursor until end of screen,
        n=1 clears from cursor to beginning of screen
        n=2 clears entire screen
        //Clear Line: \u001b[{n}K clears the current line
        n=0 clears from cursor to end of line
        n=1 clears from cursor to start of line
        n=2 clears entire line*/
        //Set Column: \u001b[{n}G moves cursor to column n
        //Set Position: \u001b[{n};{m}H moves cursor to row n column m

    }
    var type: String by Delegates.observable("", this::onType)
    private fun onType(property: KProperty<*>, oldType: String, newType: String) {
            print(newType)
            /*when (newType) {
                RIGHT -> print(RIGHT_KEY)
                LEFT -> print(LEFT_KEY)
                INSERT -> print(INSERT_KEY)
                BKSP -> print(BKSP_KEY)
                DELETE -> print(DEL_KEY)
                HOME -> print(HOME_KEY)
                END -> print(END_KEY)
                else -> print(newType.toChar())
            }
        }*/
    }
}