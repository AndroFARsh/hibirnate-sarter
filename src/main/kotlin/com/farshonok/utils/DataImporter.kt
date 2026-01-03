package com.farshonok.utils

import com.farshonok.entities.Birthday
import com.farshonok.entities.Chat
import com.farshonok.entities.Company
import com.farshonok.entities.Payment
import com.farshonok.entities.User
import com.farshonok.entities.UserChat
import com.farshonok.entities.User_
import org.hibernate.Session
import org.hibernate.SessionFactory
import java.time.LocalDate
import java.time.Month


fun SessionFactory.fillDatabase() = openSession().use { session ->
    val transaction = session.beginTransaction()

    val microsoft = session.createCompany( "Microsoft")
    val apple = session.createCompany("Apple")
    val google = session.createCompany("Google")

    val billGates = session.createUser(
        "bill.gates@microsoft.com", "Bill", "Gates",
        LocalDate.of(1955, Month.OCTOBER, 28), microsoft
    )
    val steveJobs = session.createUser(
        "steve.jobs@apple.com", "Steve", "Jobs",
        LocalDate.of(1955, Month.FEBRUARY, 24), apple
    )
    val sergeyBrin = session.createUser(
        "sergey.brin@gmail.com", "Sergey", "Brin",
        LocalDate.of(1973, Month.AUGUST, 21), google
    )
    val timCook = session.createUser(
        "tim.cook@apple.com", "Tim", "Cook",
        LocalDate.of(1960, Month.NOVEMBER, 1), apple
    )
    val dianeGreene = session.createUser(
        "diane.greene@gmail.com", "Diane", "Greene",
        LocalDate.of(1955, Month.JANUARY, 1), google
    )

    session.createPayment( billGates, 100)
    session.createPayment( billGates, 300)
    session.createPayment( billGates, 500)

    session.createPayment( steveJobs, 250)
    session.createPayment( steveJobs, 600)
    session.createPayment( steveJobs, 500)

    session.createPayment( timCook, 400)
    session.createPayment( timCook, 300)

    session.createPayment( sergeyBrin, 500)
    session.createPayment( sergeyBrin, 500)
    session.createPayment( sergeyBrin, 500)

    session.createPayment( dianeGreene, 300)
    session.createPayment( dianeGreene, 300)
    session.createPayment( dianeGreene, 300)

    val googleCoChat = session.createChat( "Google CO")
    val appleCoChat = session.createChat( "Apple CO")
    val coChat = session.createChat( "CO")

    session.linkChatAndUser(googleCoChat, sergeyBrin)
    session.linkChatAndUser(googleCoChat, dianeGreene)

    session.linkChatAndUser(appleCoChat,steveJobs)
    session.linkChatAndUser(appleCoChat, timCook)

    session.linkChatAndUser(coChat, sergeyBrin)
    session.linkChatAndUser(coChat, dianeGreene)
    session.linkChatAndUser(coChat,steveJobs)
    session.linkChatAndUser(coChat, timCook)
    session.linkChatAndUser(coChat, billGates)

    transaction.commit()
}

private fun Session.createCompany(name: String) = Company(name = name).apply {
    persist(this)
}

private fun Session.createUser(
    email: String,
    firstName: String,
    lastName: String,
    birthday: LocalDate,
    company: Company
) = User(
    email = email,
    fullName = "$firstName $lastName",
    firstName = firstName,
    lastName = lastName,
    birthDate = Birthday(birthday),
    company = company
).apply {
    company.users.add(this)
    persist(this)
}

private fun Session.createPayment(user: User, amount: Int) = Payment(receiver = user, amount = amount).apply {
    user.payments.add(this)
    persist(this)
}

private fun Session.createChat(name: String) = Chat(name = name).apply {
    persist(this)
}

private fun Session.linkChatAndUser(chat: Chat, user: User) = UserChat(user = user, chat = chat).apply {
    user.userChats.add(this)
    chat.userChats.add(this)

    persist(this)
}