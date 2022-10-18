package br.com.fiap.abctechapi.service

import br.com.fiap.abctechapi.handler.exception.UserExceptions
import br.com.fiap.abctechapi.model.UserDetailsImpl
import br.com.fiap.abctechapi.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
open class UserDetailsServiceImpl @Autowired constructor(
    private val repository: UserRepository
) : UserDetailsService {
    @Transactional
    override fun loadUserByUsername(username: String): UserDetails {
        val user = repository.findByUsername(username)
        if (user.isPresent) return UserDetailsImpl.build(user.get())
        else throw UserExceptions.UserNotFound
    }
}
