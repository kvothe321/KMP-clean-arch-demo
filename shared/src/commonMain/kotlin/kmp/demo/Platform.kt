package kmp.demo

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform