package br.insper.pi.evento.service;

import br.insper.pi.evento.Evento;
import br.insper.pi.evento.EventoRepository;
import br.insper.pi.evento.EventoService;
import br.insper.pi.usuario.RetornarUsuarioDTO;
import br.insper.pi.usuario.UsuarioService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class EventoServiceTests {

    @InjectMocks
    private EventoService eventoService;

    @Mock
    private EventoRepository eventoRepository;

    @Mock
    private UsuarioService usuarioService;


    @Test
    public void TestCadastrarEvento() {
        Evento evento = new Evento();
        evento.setNome("Evento 1");
        evento.setDescricao("Evento 1");
        evento.setMaxConvidados(10);
        evento.setCpfCriador("90210");

        ArrayList<String> lista = new ArrayList<>();
        lista.add("123");
        evento.setUsuarios(lista);

        // Mocking the usuarioService to return a successful response
        RetornarUsuarioDTO usuarioMock = new RetornarUsuarioDTO();
        Mockito.when(usuarioService.getUsuario("90210"))
                .thenReturn(ResponseEntity.ok(usuarioMock));

        Mockito.when(eventoRepository.save(Mockito.any(Evento.class))).thenReturn(evento);

        // Chamada do método 'salvar' com o CPF do criador
        Evento retorno = eventoService.salvar(evento, evento.getCpfCriador());

        // Verifica os resultados
        Assertions.assertNotNull(retorno);
        Assertions.assertEquals("Evento 1", retorno.getNome());
        Assertions.assertEquals("Evento 1", retorno.getDescricao());
        Assertions.assertEquals(10, retorno.getMaxConvidados());
        Assertions.assertEquals("90210", retorno.getCpfCriador());
        Assertions.assertEquals(lista, retorno.getUsuarios());
    }


    @Test
    public void testListarEventosNome(){

        Evento evento = new Evento();
        evento.setNome("Evento 1");
        evento.setDescricao("Evento 1");
        evento.setMaxConvidados(10);
        evento.setCpfCriador("90210");

        ArrayList<String> lista = new ArrayList<>();
        lista.add("123");
        evento.setUsuarios(lista);

        Mockito.when(eventoRepository.findByNome(Mockito.anyString())).thenReturn(new ArrayList<Evento>(){{add(evento);}});

        List<Evento> retorno = eventoService.listar("Evento 1");

        // Verify the results
        Assertions.assertNotNull(retorno);
        Assertions.assertEquals(1, retorno.size());
        Assertions.assertEquals("Evento 1", retorno.get(0).getNome());
        Assertions.assertEquals("Evento 1", retorno.get(0).getDescricao());
        Assertions.assertEquals(10, retorno.get(0).getMaxConvidados());
        Assertions.assertEquals("90210", retorno.get(0).getCpfCriador());
        Assertions.assertEquals(lista, retorno.get(0).getUsuarios());

    }

    @Test
    public void testListarTodosEventos(){

        Evento evento = new Evento();
        evento.setNome("Evento 1");
        evento.setDescricao("Evento 1");
        evento.setMaxConvidados(10);
        evento.setCpfCriador("90210");

        ArrayList<String> lista = new ArrayList<>();
        lista.add("123");
        evento.setUsuarios(lista);

        Mockito.when(eventoRepository.findAll()).thenReturn(new ArrayList<Evento>(){{add(evento);}});

        List<Evento> retorno = eventoService.listar(null);

        // Verify the results
        Assertions.assertNotNull(retorno);
        Assertions.assertEquals(1, retorno.size());
        Assertions.assertEquals("Evento 1", retorno.get(0).getNome());
        Assertions.assertEquals("Evento 1", retorno.get(0).getDescricao());
        Assertions.assertEquals(10, retorno.get(0).getMaxConvidados());
        Assertions.assertEquals("90210", retorno.get(0).getCpfCriador());
        Assertions.assertEquals(lista, retorno.get(0).getUsuarios());

    }

    @Test
    public void testSalvarEventoNotSuccessful() {
        Evento evento = new Evento();
        evento.setId("1");
        evento.setCpfCriador("90210"); // Adicione o CPF do criador do evento

        // Mockando a resposta do usuarioService para retornar NOT_FOUND
        ResponseEntity<RetornarUsuarioDTO> responseEntity = new ResponseEntity<>(HttpStatus.NOT_FOUND);
        Mockito.when(usuarioService.getUsuario(evento.getCpfCriador())).thenReturn(responseEntity);

        // Verifica se uma RuntimeException é lançada ao tentar salvar
        Assertions.assertThrows(RuntimeException.class, () -> {
            eventoService.salvar(evento, evento.getCpfCriador());
        });
    }


    @Test
    public void testAddAlunoEventoNotSuccessful() {

        Evento evento = new Evento();
        evento.setId("1");

        RetornarUsuarioDTO usuarioDTO = new RetornarUsuarioDTO();
        ResponseEntity<RetornarUsuarioDTO> responseEntity = new ResponseEntity<>(usuarioDTO, HttpStatus.NOT_FOUND);

        Assertions.assertThrows(RuntimeException.class, () -> {
            eventoService.addAluno(evento, "123");
        });

    }

    @Test
    public void testSalvarEventoWhenStatusCodeIsNotSuccessful() {
        Evento evento = new Evento();
        evento.setNome("Matheus Pereira");
        evento.setMaxConvidados(26);
        evento.setCpfCriador("123"); // Adicionando o CPF do criador
        evento.setId("1");

        ArrayList<String> lista = new ArrayList<>();
        lista.add("123");
        evento.setUsuarios(lista);

        // Simulando o retorno do usuário não encontrado (status NOT_FOUND)
        ResponseEntity<RetornarUsuarioDTO> responseEntity = new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        Mockito.when(usuarioService.getUsuario(evento.getCpfCriador())).thenReturn(responseEntity);

        // Verificando se a exceção é lançada
        Assertions.assertThrows(RuntimeException.class, () -> {
            eventoService.salvar(evento, evento.getCpfCriador()); // Passando o CPF
        });
    }

    // teste adicionar aluno
    @Test
    public void testAddAlunoEvento() {
        Evento evento = new Evento();
        evento.setId("1");
        evento.setNome("Evento 1");
        evento.setDescricao("Evento 1");
        evento.setMaxConvidados(10);
        evento.setCpfCriador("90210");

        ArrayList<String> lista = new ArrayList<>();
        lista.add("123");
        evento.setUsuarios(lista);

        // Mocking the usuarioService to return a successful response
        RetornarUsuarioDTO usuarioMock = new RetornarUsuarioDTO();
        Mockito.when(usuarioService.getUsuario("123"))
                .thenReturn(ResponseEntity.ok(usuarioMock));

        Mockito.when(eventoRepository.findById("1")).thenReturn(java.util.Optional.of(evento));

        Mockito.when(eventoRepository.save(Mockito.any(Evento.class))).thenReturn(evento);

        // Chamada do método 'salvar' com o CPF do criador
        Evento retorno = eventoService.addAluno(evento, "123");

        // Verifica os resultados
        Assertions.assertNotNull(retorno);
        Assertions.assertEquals("Evento 1", retorno.getNome());
        Assertions.assertEquals("Evento 1", retorno.getDescricao());
        Assertions.assertEquals(10, retorno.getMaxConvidados());
        Assertions.assertEquals("90210", retorno.getCpfCriador());
        Assertions.assertEquals(lista, retorno.getUsuarios());
    }


}

