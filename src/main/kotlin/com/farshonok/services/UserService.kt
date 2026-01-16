package com.farshonok.services

import com.farshonok.dto.UserCreateDto
import com.farshonok.dto.UserReadDto
import com.farshonok.dto.mappers.Mapper
import com.farshonok.dto.mappers.UserCreateMapper
import com.farshonok.dto.mappers.UserReanMapper
import com.farshonok.entities.User
import com.farshonok.repositories.UserRepository
import jakarta.transaction.Transactional
import java.util.*

open class UserService(
    val userRepository: UserRepository,
    val userReadMapper: UserReanMapper,//Mapper<User, UserReadDto>,
    val userCreateMapper: UserCreateMapper,//Mapper<UserCreateDto, User>
) {
    @Transactional
    open fun findById(userId: Long): Optional<UserReadDto> =
        userRepository
            .findById(userId, mapOf())
            .map(userReadMapper::map)

    @Transactional
    open fun delete(userId: Long): Boolean =
        userRepository.findById(userId).apply {
            ifPresent { userRepository.delete(userId) }
        }.isPresent

    @Transactional
    open fun create(user: UserCreateDto): Long {
        // validate
        // map
        val entity = userCreateMapper.map(user)
        return userRepository.save(entity).id
    }
}