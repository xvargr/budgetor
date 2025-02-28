package xvargr.budgetor.mp

class Greeting {
    private val platform = getPlatform()

    fun greet(): String {
        return "Muah, ${platform.name}!"
    }
}