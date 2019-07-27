package br.starwars.planetas.api.models

import javax.persistence.Entity
import javax.persistence.SequenceGenerator


@Entity
@SequenceGenerator(name = "default_generator", sequenceName = "planeta_id_seq", allocationSize = 1)
data class Planeta(
        val id: Long,
        val nome: String,
        val clima: String,
        val terreno: String
)
