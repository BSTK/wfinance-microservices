package dev.bstk.wfinance.fornecedor.api;

import dev.bstk.wfinance.fornecedor.api.request.EnderecoRequest;
import dev.bstk.wfinance.fornecedor.api.request.NovoFornecedorRequest;
import dev.bstk.wfinance.fornecedor.api.response.FornecedorResponse;
import dev.bstk.wfinance.fornecedor.api.response.FornecedorResumoResponse;
import dev.bstk.wfinance.fornecedor.domain.FornecedorService;
import dev.bstk.wfinance.fornecedor.domain.helper.Mapper;
import dev.bstk.wfinance.fornecedor.domain.helper.NovoRecursoCriadoEvento;
import dev.bstk.wfinance.fornecedor.domain.helper.PageHelper;
import dev.bstk.wfinance.fornecedor.domain.repository.FornecedorRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v1/fornecedores")
public class FornecedorResource {

    private final FornecedorService fornecedorService;
    private final FornecedorRepository fornecedorRepository;
    private final ApplicationEventPublisher applicationEventPublisher;

    @Autowired
    public FornecedorResource(final FornecedorService fornecedorService,
                              final FornecedorRepository fornecedorRepository,
                              final ApplicationEventPublisher applicationEventPublisher) {
        this.fornecedorService = fornecedorService;
        this.fornecedorRepository = fornecedorRepository;
        this.applicationEventPublisher = applicationEventPublisher;
    }

    @GetMapping
    public ResponseEntity<Page<FornecedorResponse>> pessoas(
        @RequestParam(value = "nome", defaultValue = "", required = false) final String nome,
        final Pageable pageable) {

        final var pageFornecedores = fornecedorService.pessoas(nome, pageable);
        final var fornecedoresResponse = Mapper.list(pageFornecedores.getContent(), FornecedorResponse.class);
        final var pageFornecedoresResponse = PageHelper.page(fornecedoresResponse, pageFornecedores);

        return ResponseEntity.ok(pageFornecedoresResponse);
    }

    @GetMapping("/resumo")
    public ResponseEntity<List<FornecedorResumoResponse>> pessoas() {
        final var fornecedores = fornecedorRepository.findAll();
        final var fornecedoresResponse = Mapper.list(fornecedores, FornecedorResumoResponse.class);
        return ResponseEntity.ok(fornecedoresResponse);
    }

    @GetMapping("/{id}")
    public ResponseEntity<FornecedorResponse> pessoaPorId(@PathVariable("id") final Long id) {
        final var fornecedorOptional = fornecedorRepository.findById(id);

        if (fornecedorOptional.isPresent()) {
            final var fornecedor = fornecedorOptional.get();
            final var fornecedorResponse = Mapper.map(fornecedor, FornecedorResponse.class);
            return ResponseEntity.ok(fornecedorResponse);
        }

        return ResponseEntity.notFound().build();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<FornecedorResponse> novaPessoa(@RequestBody @Valid final NovoFornecedorRequest request,
                                                         final HttpServletResponse httpServletResponse) {
        final var fornecedorSalvo = fornecedorService.novaPessoa(request);
        final var fornecedorSalvoResponse = Mapper.map(fornecedorSalvo, FornecedorResponse.class);

        applicationEventPublisher.publishEvent(new NovoRecursoCriadoEvento(
            this, httpServletResponse, fornecedorSalvo.getId()));

        return ResponseEntity.ok(fornecedorSalvoResponse);
    }

    @PutMapping("/{id}")
    public ResponseEntity<FornecedorResponse> atualizar(@PathVariable("id") final Long id,
                                                        @RequestBody @Valid final NovoFornecedorRequest request) {
        final var fornecedorOptional = fornecedorService.atualizar(id, request);

        if (fornecedorOptional.isPresent()) {
            final var fornecedor = fornecedorOptional.get();
            final var fornecedorResponse = Mapper.map(fornecedor, FornecedorResponse.class);
            return ResponseEntity.ok(fornecedorResponse);
        }

        return ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}/endereco")
    public ResponseEntity<FornecedorResponse> atualizarEndereco(@PathVariable("id") final Long fornecedorId,
                                                                @RequestBody @Valid final EnderecoRequest request) {
        final var fornecedorOptional = fornecedorService.atualizarEndereco(fornecedorId, request);

        if (fornecedorOptional.isPresent()) {
            final var fornecedor = fornecedorOptional.get();
            final var fornecedorSalvoResponse = Mapper.map(fornecedor, FornecedorResponse.class);
            return ResponseEntity.ok(fornecedorSalvoResponse);
        }

        return ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}/ativo")
    public ResponseEntity<FornecedorResponse> atualizarAtivo(@PathVariable("id") final Long fornecedorId,
                                                             @RequestBody final Boolean ativo) {
        final var fornecedorOptional = fornecedorService.atualizarAtivo(fornecedorId, ativo);

        if (fornecedorOptional.isPresent()) {
            final var fornecedor = fornecedorOptional.get();
            final var fornecedorSalvoResponse = Mapper.map(fornecedor, FornecedorResponse.class);
            return ResponseEntity.ok(fornecedorSalvoResponse);
        }

        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable("id") final Long fornecedorId) {
        fornecedorService.excluir(fornecedorId);
        return ResponseEntity.noContent().build();
    }
}
