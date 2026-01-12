package com.farshonok.listeners

import com.farshonok.entities.Revision
import org.hibernate.envers.RevisionListener

class RevisionListenerImpl : RevisionListener {
    override fun newRevision(revisionEntity: Any?) {
        val revision = revisionEntity as? Revision ?: return
        revision.userName = "demo"
    }
}