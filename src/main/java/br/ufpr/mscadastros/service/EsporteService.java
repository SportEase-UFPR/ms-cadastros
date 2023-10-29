package br.ufpr.mscadastros.service;

import br.ufpr.mscadastros.exceptions.ConflictException;
import br.ufpr.mscadastros.exceptions.EntityNotFoundException;
import br.ufpr.mscadastros.model.dto.esporte.EsporteExclusaoResponse;
import br.ufpr.mscadastros.model.dto.esporte.EsporteRequest;
import br.ufpr.mscadastros.model.dto.esporte.EsporteResponse;
import br.ufpr.mscadastros.model.entity.Esporte;
import br.ufpr.mscadastros.repository.EsporteRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class EsporteService {

    private final EsporteRepository esporteRepository;

    public EsporteService(EsporteRepository esporteRepository) {
        this.esporteRepository = esporteRepository;
    }

    public EsporteResponse criarEsporte(EsporteRequest esporteRequest) {
        //Verificando se o esporte já existe
        Optional<Esporte> esporte = esporteRepository.findByNome(esporteRequest.getNome());

        if(esporte.isPresent()) {
            throw new ConflictException("Esporte já cadastrado");
        }

        Esporte novoEsporte = new Esporte(new EsporteResponse(Long.parseLong("0"), esporteRequest.getNome()));
        esporteRepository.save(novoEsporte);

        return new EsporteResponse(novoEsporte);
    }

    public List<EsporteResponse> listarEsportes() {
        List<Esporte> listaEsportes = esporteRepository.findAll();

        if(listaEsportes.isEmpty()){
            throw new EntityNotFoundException("Não há esportes cadastrados");
        }

        List<EsporteResponse> response = new ArrayList<>();
        listaEsportes.forEach(e -> response.add(new EsporteResponse(e)));
        return response;
    }

    public EsporteExclusaoResponse excluirEsporte(Long idEsporte) {
        Esporte esporte = esporteRepository.findById(idEsporte)
                .orElseThrow(() -> new EntityNotFoundException("Esporte não cadastrado"));

        //Excluir esportes vinculados aos espaços esportivos
        esporteRepository.excluirEspacoEsportivoEsporte(idEsporte);

        //Excluir esporte
        esporteRepository.delete(esporte);

        return EsporteExclusaoResponse.builder().mensagem("Esporte excluído com sucesso").build();

    }
}
