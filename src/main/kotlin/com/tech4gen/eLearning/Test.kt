package com.tech4gen.eLearning

import org.apache.juli.logging.Log
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/test")
class Test {

    @GetMapping
    fun test() {
        print("TestPrint")
    }
}