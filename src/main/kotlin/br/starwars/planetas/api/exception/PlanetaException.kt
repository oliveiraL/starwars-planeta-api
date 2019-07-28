package br.starwars.planetas.api.exception

import java.lang.Exception

class PlanetaException(
        val mensagem: String
) : Exception(mensagem)
