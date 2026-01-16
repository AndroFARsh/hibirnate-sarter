package com.farshonok.utils

import org.hibernate.Session
import org.hibernate.SessionFactory
import java.lang.reflect.Proxy

fun SessionFactory.proxyCurrentSession(): Session = Proxy.newProxyInstance(
    javaClass.classLoader,
    arrayOf<Class<*>>(Session::class.java)
) { _, method, args ->
    if (args != null) {
        method?.invoke(currentSession, *args)
    } else {
        method?.invoke(currentSession)
    }
} as Session