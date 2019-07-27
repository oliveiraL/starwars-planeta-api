package br.starwars.planetas.api.repository

import br.starwars.planetas.api.models.Planeta
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface PlanetaRepository : JpaRepository<Planeta, Long>
