import kotlin.properties.Delegates
import kotlin.reflect.KProperty

class Console {
    var type: String by Delegates.observable("", this::onType)
    private fun onType(property: KProperty<*>, oldType: String, newType: String) {
            print(newType)
    }
}