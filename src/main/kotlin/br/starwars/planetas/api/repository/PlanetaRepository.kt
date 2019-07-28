package br.starwars.planetas.api.repository

import br.starwars.planetas.api.models.Planeta
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface PlanetaRepository : JpaRepository<Planeta, Long> {
    fun findPlanetaByNomeIgnoreCase(nome: String): Optional<Planeta>
}
