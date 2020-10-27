import java.util.*

class Line () : Observable() {
    companion object{
        var buffer = StringBuilder()
        var pos = 0
        var ins = false
    }
    fun write (c : Char): Boolean{
        if (ins){
            buffer.insert(pos-1, c)
        }
        else if (c != '~')buffer.insert(pos, c)
        pos++
        return ins
    }
    fun right():Boolean{
        return if(pos != buffer.length){
            pos++
            true
        }
        else false
    }
    fun left():Boolean{
        return if(pos != 0){
            pos--
            true
        }
        else false
    }
    fun home():Int{
        pos = 0
        return pos
    }
    fun end():Int{
        pos = buffer.length
        return pos
    }
    fun bksp(): Boolean{
        return if (buffer.isNotEmpty()){
            buffer.deleteAt(pos-1)
            pos--
            true
        }
        else  false
    }
    fun delete(): Boolean{
        return if (buffer.isNotEmpty() && pos!=buffer.length){
            buffer.deleteAt(pos)
            pos--
            true
        }
        else  false
    }
    fun toggle() = !ins

    override fun toString(): String {
        return buffer.toString()
    }
}