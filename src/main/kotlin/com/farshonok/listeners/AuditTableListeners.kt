package com.farshonok.listeners

import com.farshonok.entities.Audit
import com.farshonok.entities.Operation
import org.hibernate.event.spi.*
import java.io.Serializable

class AuditTableListeners : PostInsertEventListener, PostUpdateEventListener, PostDeleteEventListener {
    override fun onPostInsert(event: PostInsertEvent?) {
        event?.let { persistOperation(it, Operation.Insert) }
    }

    override fun onPostUpdate(event: PostUpdateEvent?) {
        event?.let { persistOperation(it, Operation.Update) }
    }

    override fun onPostDelete(event: PostDeleteEvent?) {
        event?.let { persistOperation(it, Operation.Delete) }
    }


    fun persistOperation(event: AbstractPostDatabaseOperationEvent, operation: Operation) {
        if (event.entity is Audit) return
        val audit = Audit(
            entityId = event.id.toString(),
            entityContent = event.entity.toString(),
            operation = operation,
        )
        event.session.persist(audit)
    }
}