class Line (var buffer : StringBuilder, var pos : Int, var ins : Boolean) {
    fun write (c : Char): Boolean{
        if (ins){
            buffer.append(c, pos-1, 1)
        }
        else buffer.append(c)
        pos++
        return true
    }
    fun right():Boolean{
        if(pos != buffer.length){
            pos++
            return true
        }
        else return false
    }
    fun left():Boolean{
        if(pos != 0){
            pos--
            return true
        }
        else return false
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
        if (buffer.isNotEmpty()){
            buffer.deleteAt(pos)
            pos--
            return true
        }
        else return false
    }
    fun toggle() = !ins

    override fun toString(): String {
        return buffer.toString()
    }
}