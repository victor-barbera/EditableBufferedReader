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
            setChanged()
            notifyObservers(INSERT)
        }
        else if (c != '~') buffer.insert(pos, c)
        setChanged()
        notifyObservers(c)
        pos++
        return ins
    }
    fun right():Boolean{
        return if(pos != buffer.length){
            pos++
            setChanged()
            notifyObservers(RIGHT)
            true
        }
        else false
    }
    fun left():Boolean{
        return if(pos != 0){
            pos--
            setChanged()
            notifyObservers(LEFT)
            true
        }
        else false
    }
    fun home():Int{
        pos = 0
        setChanged()
        notifyObservers(HOME)
        return pos
    }
    fun end():Int{
        pos = buffer.length
        setChanged()
        notifyObservers(END)
        return pos
    }
    fun bksp(): Boolean{
        return if (buffer.isNotEmpty()){
            buffer.deleteAt(pos-1)
            pos--
            setChanged()
            notifyObservers(BKSP)
            true
        }
        else  false
    }
    fun delete(): Boolean{
        return if (buffer.isNotEmpty() && pos!=buffer.length){
            buffer.deleteAt(pos)
            pos--
            setChanged()
            notifyObservers(DELETE)
            true
        }
        else  false
    }
    fun toggle() = !ins

    override fun toString(): String {
        return buffer.toString()
    }
}