package com.tlpcraft.kmp.demo.shared

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform
