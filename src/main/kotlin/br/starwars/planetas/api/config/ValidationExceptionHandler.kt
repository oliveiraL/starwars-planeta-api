package br.starwars.planetas.api.config

import br.starwars.planetas.api.exception.PlanetaException
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler
import java.io.IOException
import javax.servlet.http.HttpServletResponse
import javax.validation.ConstraintViolationException
import org.springframework.http.ResponseEntity
import org.springframework.web.context.request.WebRequest
import org.springframework.web.bind.MethodArgumentNotValidException
import java.util.*
import kotlin.streams.toList


@ControllerAdvice
class CustomGlobalExceptionHandler : ResponseEntityExceptionHandler() {

    @ExceptionHandler(ConstraintViolationException::class)
    @Throws(IOException::class)
    fun constraintViolationException(response: HttpServletResponse) {
        response.sendError(HttpStatus.BAD_REQUEST.value())
    }

    @ExceptionHandler(PlanetaException::class)
    fun handlePlanetaErroGeralException(exception: PlanetaException, request: WebRequest): ResponseEntity<Any> {
        val body = LinkedHashMap<String, Any>()
        body["timestamp"] = Date()
        body["error"] = exception.mensagem
        return ResponseEntity(body, HttpStatus.BAD_REQUEST)
    }

    override fun handleMethodArgumentNotValid(ex: MethodArgumentNotValidException, headers: HttpHeaders, status: HttpStatus, request: WebRequest): ResponseEntity<Any> {
        val body = LinkedHashMap<String, Any>()
        body["timestamp"] = Date()
        body["status"] = status.value()

        val errors = ex.bindingResult
                .fieldErrors
                .stream()
                .map { x -> "${x.field}: ${x.defaultMessage}" }.toList()

        body["errors"] = errors
        return ResponseEntity(body, headers, status)
    }

}

