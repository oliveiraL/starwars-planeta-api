package br.starwars.planetas.api.rest

import br.starwars.planetas.api.models.Planeta
import br.starwars.planetas.api.service.PlanetaService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("planetas")
class PlanetaRest(val planetaService: PlanetaService) {

    @GetMapping()
    fun buscarTodos(): ResponseEntity<List<Planeta>> =
            ResponseEntity.ok(planetaService.buscarTodos())

    @GetMapping("{id}")
    fun buscarPorId(@PathVariable id: Long): ResponseEntity<Planeta> {
        val planeta = planetaService.buscarPorId(id)
        return when (planeta.isPresent) {
            true -> ResponseEntity.ok(planeta.get())
            false -> ResponseEntity.noContent().build()
        }
    }

    @GetMapping("nome/{nome}")
    fun buscarPorNome(@PathVariable nome: String): ResponseEntity<Planeta> {
        val planeta = planetaService.buscarPorNome(nome)
        return when (planeta.isPresent) {
            true -> ResponseEntity.ok(planeta.get())
            false -> ResponseEntity.noContent().build()
        }
    }

    @PostMapping()
    fun cadastrarPlaneta(@Valid @RequestBody planeta: Planeta): ResponseEntity<Planeta> {
        val planetaCadastrado = planetaService.criarPlaneta(planeta)
        return ResponseEntity.ok(planetaCadastrado)
    }

    @DeleteMapping("{id}")
    fun deletarPlaneta(@PathVariable id: Long): ResponseEntity<Planeta> {
        planetaService.deletarPlaneta(id)
        return ResponseEntity.ok().build()
    }

}
