package com.tech4gen.eLearning.util

fun dataLogger(
    message: String
) {
    println("${ConsoleColors.GREEN}Data: $message${ConsoleColors.RESET}")
}

fun errLogger(
    message: String
) {
    println("${ConsoleColors.RED}Error: $message${ConsoleColors.RESET}")
}

fun reqLogger(
    message: String
) {
    println("${ConsoleColors.BLUE}Request: $message${ConsoleColors.RESET}")
}

fun resLogger(
    message: String
) {
    println("${ConsoleColors.YELLOW}Response: $message${ConsoleColors.RESET}")
}