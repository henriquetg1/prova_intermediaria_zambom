package br.insper.pi.evento;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/evento")
public class EventoController {

    @Autowired
    private EventoService eventoService;

    @GetMapping
    public List<Evento> listar(@RequestParam(required = false) String nome){
        return eventoService.listar(nome);
    }
//
//    @PostMapping
//    public Evento salvar(@RequestBody Evento evento){
//        return eventoService.salvar(evento);
//    }

    @PostMapping
    public Evento salvar(@RequestBody Evento evento, @RequestParam String cpf){
        return eventoService.salvar(evento, cpf);
    }

    @PostMapping("/{cpf}")
    public Evento addAluno(@RequestBody Evento evento, @PathVariable String cpf){
        return eventoService.addAluno(evento, cpf);
    }

}