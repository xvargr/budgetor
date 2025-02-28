package xvargr.budgetor.mp

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform