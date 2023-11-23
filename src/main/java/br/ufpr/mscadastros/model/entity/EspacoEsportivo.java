package br.ufpr.mscadastros.model.entity;

import br.ufpr.mscadastros.exceptions.BussinessException;
import br.ufpr.mscadastros.model.dto.espaco_esportivo.EspEsportivoAlteracaoRequest;
import br.ufpr.mscadastros.model.dto.espaco_esportivo.EspEsportivoCriacaoRequest;
import br.ufpr.mscadastros.utils.StrUtils;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;

import java.time.Duration;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;

@Entity(name = "tb_espaco_esportivo")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EspacoEsportivo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String nome;

    private String descricao;

    @Column(length = 100)
    private String localidade;

    @Column(length = 30)
    private String piso;

    @Column(length = 10)
    private String dimensoes;

    private Short capacidadeMin;

    private Short capacidadeMax;

    @Column(nullable = false)
    private Boolean disponivel;

    @Column(nullable = false)
    private LocalTime horaAbertura;

    @Column(nullable = false)
    private LocalTime horaFechamento;

    //De quanto em quanto tempo poderá ser feita a locação (ex: de meia em meia hora, de uma em uma hora...)
    @Column(nullable = false)
    private LocalTime periodoLocacao;

    @Column(nullable = false)
    private Integer maxLocacaoDia;

    @Column(nullable = false)
    private String diasFuncionamento;

    @Lob
    @Column(columnDefinition = "MEDIUMBLOB")
    private byte[] imagemBase64;

    private Double mediaAvaliacao;
    private Integer contagemAvaliacoes;

    private Boolean excluido;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "tb_espaco_esportivo_esporte",
            joinColumns = @JoinColumn(name = "id_espaco_esportivo"),
            inverseJoinColumns = @JoinColumn(name = "id_esporte"))
    private List<Esporte> listaEsportes = new ArrayList<>();

    public EspacoEsportivo(EspEsportivoCriacaoRequest request) {
        this.nome = request.getNome();
        this.descricao = request.getDescricao();
        this.localidade = request.getLocalidade();
        this.piso = request.getPiso();
        this.dimensoes = request.getDimensoes();
        this.capacidadeMin = request.getCapacidadeMin();
        this.capacidadeMax = request.getCapacidadeMax();
        this.disponivel = request.getDisponivel();
        this.horaAbertura = request.getHoraAbertura();
        this.horaFechamento = request.getHoraFechamento();
        this.periodoLocacao = request.getPeriodoLocacao();
        this.maxLocacaoDia = request.getMaxLocacaoDia();
        this.contagemAvaliacoes = 0;
        this.excluido = false;

        String strImgFormatada = StrUtils.removerPrefixoBase64(request.getImagemBase64());
        this.imagemBase64 = Base64.getDecoder().decode(strImgFormatada);

        this.diasFuncionamento = request.getDiasFuncionamento().stream()
                .map(String::valueOf)
                .collect(Collectors.joining(","));
    }

    public void editarDados(EspEsportivoAlteracaoRequest request) {
        if(StringUtils.isNotBlank(request.getNome())) {
            this.nome = request.getNome();
        }
        if(StringUtils.isNotBlank(request.getDescricao())) {
            this.descricao = request.getDescricao();
        }
        if(StringUtils.isNotBlank(request.getLocalidade())) {
            this.localidade = request.getLocalidade();
        }
        if(StringUtils.isNotBlank(request.getPiso())) {
            this.piso = request.getPiso();
        }
        if(StringUtils.isNotBlank(request.getDescricao())) {
            this.descricao = request.getDescricao();
        }
        if(request.getCapacidadeMin() != null) {
            this.capacidadeMin = request.getCapacidadeMin();
        }
        if(request.getCapacidadeMax() != null) {
            this.capacidadeMax = request.getCapacidadeMax();
        }
        if(request.getHoraAbertura() != null) {
            this.horaAbertura = request.getHoraAbertura();
        }
        if(request.getHoraFechamento() != null) {
            this.horaFechamento = request.getHoraFechamento();
        }
        if(request.getPeriodoLocacao() != null) {
            this.periodoLocacao = request.getPeriodoLocacao();
        }
        if(request.getMaxLocacaoDia() != null) {
            this.maxLocacaoDia = request.getMaxLocacaoDia();
        }

        if(request.getImagemBase64() != null) {
            String strImgFormatada = StrUtils.removerPrefixoBase64(request.getImagemBase64());
            this.imagemBase64 = Base64.getDecoder().decode(strImgFormatada);
        }
        if(request.getDisponivel() != null) {
            this.disponivel = request.getDisponivel();
        }

        if(request.getDiasFuncionamento() != null && !request.getDiasFuncionamento().isEmpty()) {
            this.diasFuncionamento = request.getDiasFuncionamento().stream()
                    .map(String::valueOf)
                    .collect(Collectors.joining(","));
        }

        this.listaEsportes = new ArrayList<>();
    }

    public void validarEspacoEsportivo() {
        Duration duracaoCiclo = Duration.between(LocalTime.MIN, periodoLocacao);
        Duration duracaoAberturaEFechamento = Duration.between(horaAbertura, horaFechamento);

        if(horaAbertura.isAfter(horaFechamento)) {
            throw new BussinessException("A hora de abertura deve ser após a hora de fechamento");
        }
        if(maxLocacaoDia * duracaoCiclo.toMillis() > duracaoAberturaEFechamento.toMillis()) {
            throw new BussinessException("maxLocacaoDia excede o período de funcionamento do espaço esportivo");
        }

        if(Duration.between(LocalTime.MIN, periodoLocacao).compareTo(Duration.between(horaAbertura, horaFechamento)) > 0) {
            throw new BussinessException("periodoLocacao não pode ser superior ao período de funcionamento do espaço esportivo");
        }

        var diasDisponveisLista = Arrays.stream(diasFuncionamento.split(","))
                .map(Integer::parseInt)
                .toList();

        diasDisponveisLista.forEach(dia -> {
            if(dia < 0 || dia > 6) {
                throw new BussinessException("Os dias disponíveis deve estar entre 0 e 6");
            }
        });

        if(capacidadeMin != null && capacidadeMin > capacidadeMax) {
            throw new BussinessException("A capacidade mínima deve ser menor que a capacidade máxima");
        }
    }
}
