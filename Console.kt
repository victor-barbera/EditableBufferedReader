import java.util.*

class Console : Observer {
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
        const val RIGHT = "\u001b[1C"
        const val CLEAR = "\u001b[2J"
        const val LEFT = "\u001b[1D"
        const val INSERT = "\u001b[1@"
        const val BKSP = "\u001b[1D" + "\u001b[1P" //left + Delete
        const val DEL = "\u001b[1P" //Delete
        const val HOME = "\u001b[1G" // Move cursor to col 1 in current row.
        const val END = "\u001b["
    }
    override fun update(o: Observable?, arg: Any?) {
        TODO("Not yet implemented")
    }
}