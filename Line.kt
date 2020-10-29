import java.util.*
import kotlin.properties.Delegates

class Line (val console: Console) {
    companion object{
        var buffer = StringBuilder()
        var pos = 0
        var ins = false
    }
    fun write (c : Char): Boolean{
        if (ins){
            buffer.insert(pos-1, c)
            pos++
            console.type = CLEAR + buffer.toString() + "\u001b[${pos-1}G"
        }
        else if (c != '~') {
            buffer.insert(pos, c)
            pos++
            console.type = CLEAR + buffer.toString() + "\u001b[${pos+1}G"
        }

        return ins
    }
    fun right():Boolean{
        return if(pos != buffer.length){
            pos++
            console.type = RIGHT_KEY
            true
        }
        else false
    }
    fun left():Boolean{
        return if(pos != 0){
            pos--
            console.type = LEFT_KEY
            true
        }
        else false
    }
    fun home():Int{
        console.type = HOME_KEY
        pos = 0
        return pos
    }
    fun end():Int{
        pos = buffer.length
        console.type = END_KEY + pos + "C"
        return pos
    }
    fun bksp(): Boolean{
        return if (buffer.isNotEmpty()){
            buffer.deleteAt(pos-1)
            pos--
            console.type = BKSP_KEY
            true
        }
        else  false
    }
    fun delete(): Boolean{
        return if (buffer.isNotEmpty() && pos!=buffer.length){
            buffer.deleteAt(pos)
            pos--
            console.type = DEL_KEY
            true
        }
        else  false
    }
    fun toggle() : Boolean{
        if (!ins) console.type = LEFT_KEY
        else if (ins) console.type = RIGHT_KEY
        return !ins
    }

    override fun toString(): String {
        return buffer.toString()
    }
}


