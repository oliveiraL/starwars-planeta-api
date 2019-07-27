package br.starwars.planetas.api.models

import javax.persistence.*
import javax.validation.constraints.NotBlank


@Entity
@Table
@SequenceGenerator(name = "default_generator", sequenceName = "planeta_id_seq", allocationSize = 1)
data class Planeta(

        @Id
        @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "default_generator")
        val id: Long,

        @field:NotBlank
        val nome: String,

        @field:NotBlank
        val clima: String,

        @field:NotBlank
        val terreno: String
)
