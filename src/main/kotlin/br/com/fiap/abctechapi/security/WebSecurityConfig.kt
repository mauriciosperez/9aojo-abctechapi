package br.com.fiap.abctechapi.security

import br.com.fiap.abctechapi.security.jwt.AuthEntryPointJwt
import br.com.fiap.abctechapi.security.jwt.AuthTokenFilter
import br.com.fiap.abctechapi.service.UserDetailsServiceImpl
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.dao.DaoAuthenticationProvider
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
open class WebSecurityConfig @Autowired constructor(
    private val userDetailsService: UserDetailsServiceImpl,
    private val unauthorizedHandler: AuthEntryPointJwt
) {
    @Bean
    open fun authenticationJwtTokenFilter(): AuthTokenFilter = AuthTokenFilter()

    @Bean
    open fun authenticationProvider(): DaoAuthenticationProvider? {
        val authProvider = DaoAuthenticationProvider()
        authProvider.setUserDetailsService(userDetailsService)
        authProvider.setPasswordEncoder(passwordEncoder())
        return authProvider
    }

    @Bean
    open fun authenticationManager(authConfig: AuthenticationConfiguration): AuthenticationManager =
        authConfig.authenticationManager

    @Bean
    open fun passwordEncoder(): PasswordEncoder = BCryptPasswordEncoder()

    @Bean
    open fun filterChain(http: HttpSecurity): SecurityFilterChain {
        with(http) {
            cors()
            csrf().disable()
            exceptionHandling().authenticationEntryPoint(unauthorizedHandler)
            sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            authorizeRequests().antMatchers("/auth/**").permitAll()
            authorizeRequests().antMatchers("/version").permitAll()
                .anyRequest().authenticated()
            authenticationProvider(authenticationProvider())
            addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter::class.java)
        }
        return http.build()
    }
}
