class Line (var buffer : StringBuilder, var pos : Int, var ins : Boolean) {
    fun write (c : Char): Boolean{
        if (ins){
            buffer.insert(pos-1, c)
        }
        else buffer.insert(pos, c)
        pos++
        return true
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
        return HOME
    }
    fun end():Int{
        pos = buffer.length
        return END
    }
    fun delete(): Boolean{
        return if (buffer.isNotEmpty()){
            buffer.deleteAt(pos-1)
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