package com.farshonok.utils

import com.farshonok.convertes.BirthdayConverter
import com.farshonok.entities.Chat
import com.farshonok.entities.Company
import com.farshonok.entities.Payment
import com.farshonok.entities.User
import com.farshonok.entities.UserChat
import org.hibernate.SessionFactory
import org.hibernate.boot.model.naming.PhysicalNamingStrategySnakeCaseImpl
import org.hibernate.cfg.Configuration

fun createSessionFactory(builder: Configuration.()-> Unit = {}): SessionFactory {
    val config = Configuration()
    config.addAnnotatedClass(UserChat::class.java)
    config.addAnnotatedClass(Chat::class.java)
    config.addAnnotatedClass(User::class.java)
    config.addAnnotatedClass(Company::class.java)
    config.addAnnotatedClass(Payment::class.java)

    config.physicalNamingStrategy = PhysicalNamingStrategySnakeCaseImpl()

    config.addAttributeConverter(BirthdayConverter(), true)

    builder.invoke(config)

    config.configure()

    return config.buildSessionFactory()
}