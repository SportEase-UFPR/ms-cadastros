package br.ufpr.mscadastros.service;

import br.ufpr.mscadastros.exceptions.EntityNotFoundException;
import br.ufpr.mscadastros.model.dto.espaco_esportivo.*;
import br.ufpr.mscadastros.model.dto.locacao.InformacoesComplementaresLocacaoRequest;
import br.ufpr.mscadastros.model.dto.locacao.InformacoesComplementaresLocacaoResponse;
import br.ufpr.mscadastros.model.entity.EspacoEsportivo;
import br.ufpr.mscadastros.model.entity.Esporte;
import br.ufpr.mscadastros.repository.ClienteRepository;
import br.ufpr.mscadastros.repository.EspacoEsportivoRepository;
import br.ufpr.mscadastros.repository.EsporteRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class EspacoEsportivoService {

    public static final String ESPACO_ESPORTIVO_NAO_CADASTRADO = "Espaço esportivo não cadastrado";
    private final EspacoEsportivoRepository repository;
    private final ClienteRepository clienteRepository;

    private final EsporteRepository esporteRepository;

    public EspacoEsportivoService(EspacoEsportivoRepository repository, ClienteRepository clienteRepository, EsporteRepository esporteRepository) {
        this.repository = repository;
        this.clienteRepository = clienteRepository;
        this.esporteRepository = esporteRepository;
    }

    public EspEsportivoCriacaoResponse criarEspacoEsportivo(EspEsportivoCriacaoRequest request) {
         //Criação de um novo espaço esportivo
        EspacoEsportivo novoEE = new EspacoEsportivo(request);

        //Buscar esportes, validar se eles existem e vinculá-los ao novo espaço esportivo
        request.getListaEsportes().forEach(esporte -> {
            Esporte esporteRecuperado = esporteRepository.findByNome(esporte.getNome())
                    .orElseThrow(() -> new EntityNotFoundException("O esporte " + esporte.getNome() + " não existe"));
            novoEE.getListaEsportes().add(esporteRecuperado);
        });

        novoEE.validarEspacoEsportivo();

        repository.save(novoEE);
        return new EspEsportivoCriacaoResponse(novoEE);
    }

    public List<EspEsportivoBuscaResponse> listarEspacosEsportivos() {
        List<EspacoEsportivo> listaEspacosEsportivos = repository.findAll();
        if (listaEspacosEsportivos.isEmpty()) {
            throw new EntityNotFoundException("Não há espaços esportivos cadastrados");
        }

        List<EspEsportivoBuscaResponse> response = new ArrayList<>();
        listaEspacosEsportivos.forEach(ee -> response.add(new EspEsportivoBuscaResponse(ee)));
        return response;
    }

    public EspEsportivoBuscaResponse buscarEspacoEsportivoPorId(Long espEsportivoId) {
        EspacoEsportivo ee = repository.findById(espEsportivoId)
                .orElseThrow(() -> new EntityNotFoundException(ESPACO_ESPORTIVO_NAO_CADASTRADO));

        return new EspEsportivoBuscaResponse(ee);
    }

    public EspEsportivoExclusaoResponse excluirEspacoEsportivoPorId(Long espEsportivoId) {
        EspacoEsportivo ee = repository.findById(espEsportivoId)
                .orElseThrow(() -> new EntityNotFoundException(ESPACO_ESPORTIVO_NAO_CADASTRADO));

        repository.excluirEspacoEsportivoEsporte(ee.getId());
        repository.delete(ee);

        return EspEsportivoExclusaoResponse.builder().msg("Espaço esportivo excluído com sucesso").build();
    }


    public EspEsportivoAlteracaoResponse editarEspacoEsportivo(Long espEsportivoId, EspEsportivoAlteracaoRequest request) {
        //Recuperar Espaço esportivo
        EspacoEsportivo ee = repository.findById(espEsportivoId)
                .orElseThrow(() -> new EntityNotFoundException(ESPACO_ESPORTIVO_NAO_CADASTRADO));

        ee.editarDados(request);

        //Buscar esportes, validar se eles existem e vinculá-los ao espaço esportivo a ser editado
        if(!request.getListaEsportes().isEmpty()) {
            ee.setListaEsportes(new ArrayList<>());

            request.getListaEsportes().forEach(esporte -> {
                Esporte esporteRecuperado = esporteRepository.findByNome(esporte.getNome())
                        .orElseThrow(() -> new EntityNotFoundException("O esporte " + esporte.getNome() + " não existe"));
                ee.getListaEsportes().add(esporteRecuperado);
            });
        }

        ee.validarEspacoEsportivo();

        repository.save(ee);

        return new EspEsportivoAlteracaoResponse(ee);
    }

    public List<EspEsportivoReservaResponse> listarEspacosEsportivosDisponiveis() {
        List<EspacoEsportivo> listaEspacosEsportivos = repository.findAll();
        if (listaEspacosEsportivos.isEmpty()) {
            throw new EntityNotFoundException("Não há espaços esportivos cadastrados");
        }

        //remover espaços esportivos indisponíveis
        listaEspacosEsportivos.removeIf(espacoEsportivo -> !espacoEsportivo.getDisponivel());

        List<EspEsportivoReservaResponse> response = new ArrayList<>();
        listaEspacosEsportivos.forEach(ee -> response.add(new EspEsportivoReservaResponse(ee)));
        return response;
    }

    public List<InformacoesComplementaresLocacaoResponse> buscarInformacoesComplementaresLocacao(List<InformacoesComplementaresLocacaoRequest> request) {
        var response = new ArrayList<InformacoesComplementaresLocacaoResponse>();

        request.forEach(infCReq -> {
            var espEsport = repository.findById(infCReq.getIdEspacoEsportivo()).orElse(null);
            var cliente = clienteRepository.findById(infCReq.getIdCliente()).orElse(null);

            if(espEsport != null && cliente != null) {
                response.add(new InformacoesComplementaresLocacaoResponse(espEsport, cliente, infCReq.getIdLocacao()));
            }
        });

        return response;
    }

    public Void atualizarMediaAvaliacaoEspacoEsportivo(Long idEspacoEsportivo, AtualizarMediaAvaliacaoEERequest request) {
        var espacoEsportivo = repository.findById(idEspacoEsportivo)
                .orElseThrow(() -> new EntityNotFoundException("Espaço esportivo não encontrado"));

        if(espacoEsportivo.getContagemAvaliacoes() == null || espacoEsportivo.getContagemAvaliacoes() == 0) {
            espacoEsportivo.setMediaAvaliacao(Double.valueOf(request.getAvaliacao()));
            espacoEsportivo.setContagemAvaliacoes(1);
        } else {
            var mediaAtual = espacoEsportivo.getMediaAvaliacao();
            var contagemAvaliacoes = espacoEsportivo.getContagemAvaliacoes();
            var novaAvaliacao = request.getAvaliacao();

            var novaMedia = (mediaAtual * contagemAvaliacoes + novaAvaliacao) / (contagemAvaliacoes + 1);

            espacoEsportivo.setMediaAvaliacao(novaMedia);
            espacoEsportivo.setContagemAvaliacoes(contagemAvaliacoes + 1);
        }
        repository.save(espacoEsportivo);
        return null;
    }
}
