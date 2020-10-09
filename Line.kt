import java.awt.Cursor

class Line (var buffer : StringBuffer, var pos : Int) : Cursor(pos) {
    /*casos:
        move dreta
        move esquerra
        eliminar
        suprimir
        home
        end
        escriptura
        return
     */
    fun move(right : Boolean ){ //0 si esquerra , 1 si dreta
        if(right) {
            if(pos != buffer.length)pos++
        }
        else {
            if(pos != 0)pos--
        }
    }
    fun delete(backspace : Boolean ){ //0 -> eliminar esquerra(del) , 1 ->eliminar dreta(bksp)
        if(backspace) {
            buffer = buffer.delete(pos, pos+1)
        }
        else buffer = buffer.delete(pos, pos-1)
        pos--
    }
    fun home() {pos = 0}

    fun end() {pos = buffer.length}
    fun write() {pos++}

}