package br.insper.pi.evento;

import br.insper.pi.usuario.RetornarUsuarioDTO;
import br.insper.pi.usuario.UsuarioNaoEncontradoException;
import br.insper.pi.usuario.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class EventoService {

    @Autowired
    private EventoRepository eventoRepository;

    @Autowired
    private UsuarioService usuarioService;

    public Evento salvar(Evento evento, String cpf){
        ResponseEntity<RetornarUsuarioDTO> usuario = usuarioService.getUsuario(cpf);
        if(usuario.getStatusCode().is2xxSuccessful()){
            return eventoRepository.save(evento);
        }
        else {
            throw new RuntimeException("Usuário não encontrado");
        }
    }

//    public Evento salvar(Evento evento){
//        //TODO: Checagens
//        String cpf = evento.getCpfCriador(); // cpf do criador do evento
//        ResponseEntity<RetornarUsuarioDTO> usuario = usuarioService.getUsuario(cpf);
//        if(usuario.getStatusCode().is2xxSuccessful()){
//            return eventoRepository.save(evento);
//        }
//        else {
//            throw new RuntimeException("Usuário não encontrado");
//        }
//    }

    // listar todos com os eventos com opção de filtro por nome
    public List<Evento> listar(String nome){
        if(nome == null){
            return eventoRepository.findAll();
        }
        return eventoRepository.findByNome(nome);
    }


    public Evento addAluno(Evento evento, String cpf){
        //TODO: Checagens2
        Optional<Evento> op = eventoRepository.findById(evento.getId());
        if (op.isEmpty()) {
            throw new RuntimeException("Evento não encontrado");
        }
        Evento evento1 = op.get();
        ResponseEntity<RetornarUsuarioDTO> usuario = usuarioService.getUsuario(cpf);
        if(usuario.getStatusCode().is2xxSuccessful()){
            // verifica se o evento tem vagas
            if (evento1.getMaxConvidados() > evento1.getUsuarios().size()) {
                RetornarUsuarioDTO usuarioDTO = usuario.getBody();
                ArrayList<String> lista = evento1.getUsuarios();
                lista.add(usuarioDTO.getCpf());
                evento1.setUsuarios(lista);
                return eventoRepository.save(evento1);
            } else {
                throw new RuntimeException("Evento lotado");
            }

        }
        else {
            throw new UsuarioNaoEncontradoException("Usuário não encontrado");
        }


    }



}
