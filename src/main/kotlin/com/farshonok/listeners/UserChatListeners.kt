package com.farshonok.listeners

import com.farshonok.entities.UserChat
import jakarta.persistence.PostPersist
import jakarta.persistence.PreRemove

class UserChatListeners {

    @PostPersist
    fun postPersist(entity: UserChat) {
        entity.chat.usersInChat += 1
    }

    @PreRemove
    fun preRemove(entity: UserChat) {
        entity.chat.usersInChat -= 1
    }
}