package com.farshonok.interceptor

import org.hibernate.Interceptor
import org.hibernate.type.Type

class GlobalInterceptor : Interceptor {
    override fun onFlushDirty(
        entity: Any?,
        id: Any?,
        currentState: Array<out Any?>?,
        previousState: Array<out Any?>?,
        propertyNames: Array<out String?>?,
        types: Array<out Type?>?
    ): Boolean {
        println("GlobalInterceptor::onFlushDirty")
        return super.onFlushDirty(entity, id, currentState, previousState, propertyNames, types)
    }
}

