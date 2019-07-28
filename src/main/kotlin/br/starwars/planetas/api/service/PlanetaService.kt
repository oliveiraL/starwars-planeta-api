package br.starwars.planetas.api.service

import br.starwars.planetas.api.exception.PlanetaException
import br.starwars.planetas.api.models.Planeta
import br.starwars.planetas.api.repository.PlanetaRepository
import org.springframework.stereotype.Service
import java.util.*


@Service
class PlanetaService(val planetaRepository: PlanetaRepository) {


    fun criarPlaneta(planeta: Planeta): Planeta {
        planeta.filmes = recuperarQuantidadeDeFilmesQueApareceu(planeta.nome)
        validarPlaneta(planeta)
        return planetaRepository.save(planeta)
    }

    fun buscarPorNome(nome: String): Optional<Planeta> =
            planetaRepository.findPlanetaByNomeIgnoreCase(nome)

    fun buscarPorId(id: Long): Optional<Planeta> =
            planetaRepository.findById(id)

    fun buscarTodos(): List<Planeta> =
            planetaRepository.findAll()

    fun deletarPlaneta(id: Long): Unit =
            planetaRepository.deleteById(id)

    private fun recuperarQuantidadeDeFilmesQueApareceu(nome: String): Int {
        val resposta = RequestHTTP.get("http://swapi.co/api/planets?search=$nome")
        return when {
            resposta.get("count").asInt == 1 -> {
                val planeta = resposta.get("results").asJsonArray.get(0).asJsonObject
                if (planeta.get("name").asString == nome) planeta.get("films").asJsonArray.size()
                else 0
            }
            else -> 0
        }
    }

    private fun validarPlaneta(planeta: Planeta): Unit {
        val planetaNoBanco = planetaRepository.findPlanetaByNomeIgnoreCase(planeta.nome)
        if (planetaNoBanco.isPresent && planetaNoBanco.get().id != planeta.id)
            throw PlanetaException("planeta já cadastrado")
    }

}
