package br.com.fiap.abctechapi.security.jwt

import br.com.fiap.abctechapi.model.UserDetailsImpl
import io.jsonwebtoken.JwtException
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.ResponseCookie
import org.springframework.stereotype.Component
import org.springframework.web.util.WebUtils
import java.util.Date
import javax.servlet.http.HttpServletRequest

@Component
class JwtUtils {
    @Value("\${abctech.app.jwtSecret}")
    private lateinit var jwtSecret: String

    @Value("\${abctech.app.jwtExpirationMs}")
    private var jwtExpirationMs: Int = 0

    @Value("\${abctech.app.jwtCookieName}")
    private lateinit var jwtCookie: String

    fun getJwtFromCookies(request: HttpServletRequest): String? {
        val cookie = WebUtils.getCookie(request, jwtCookie)
        return cookie?.value
    }

    fun generateJwtCookie(userDetails: UserDetailsImpl): ResponseCookie {
        val jwt: String = generateTokenFromUsername(userDetails.username)
        return ResponseCookie.from(jwtCookie, jwt).path("/api").maxAge((24 * 60 * 60).toLong()).httpOnly(true).build()
    }

    fun getCleanJwtCookie(): ResponseCookie? =
        ResponseCookie.from(jwtCookie, "").path("/api").build()

    fun getUserNameFromJwtToken(token: String): String =
        Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).body.subject

    fun validateJwtToken(authToken: String?): Boolean {
        return try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken)
            true
        } catch (_: JwtException) {
            false
        }
    }

    private fun generateTokenFromUsername(username: String): String =
        Jwts.builder()
            .setSubject(username)
            .setIssuedAt(Date())
            .setExpiration(Date(Date().time + jwtExpirationMs))
            .signWith(SignatureAlgorithm.HS512, jwtSecret)
            .compact()
}
