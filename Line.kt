import java.util.*
import kotlin.properties.Delegates

class Line (val console: Console) {
    companion object{
        var buffer = StringBuilder()
        var pos = 0
        var ins = false
    }
    fun write (c : Char){
        if (c != '~'){
            if (ins){
                buffer.setCharAt(pos-1, c)
                console.type = CLEAR + buffer.toString() + "\u001b[${pos}G"
            }
            else{
                buffer.insert(pos, c)
                console.type = CLEAR + buffer.toString() + "\u001b[${pos+2}G"
            }
            pos++
        }
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
    fun toggle() {
        ins = !ins
        if (ins == true) console.type = LEFT_KEY
        else console.type = RIGHT_KEY

    }

    override fun toString(): String {
        return buffer.toString()
    }
}


