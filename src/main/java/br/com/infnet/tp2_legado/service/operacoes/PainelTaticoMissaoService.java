package br.com.infnet.tp2_legado.service.operacoes;

import br.com.infnet.tp2_legado.model.operacoes.PainelTaticoMissao;
import br.com.infnet.tp2_legado.repository.operacoes.PainelTaticoMissaoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PainelTaticoMissaoService {

    private final PainelTaticoMissaoRepository repository;

    @Cacheable("todasMissoes")
    public List<PainelTaticoMissao> listarTodos() {
        return repository.findAll();
    }

    @Cacheable("top10Missoes")
    public List<PainelTaticoMissao> listarTop10Ultimos15Dias() {
        LocalDateTime quinzeDiasAtras = LocalDateTime.now().minusDays(15);
        return repository.findTop10ByUltimaAtualizacaoAfterOrderByIndiceProntidaoDesc(quinzeDiasAtras);
    }
}