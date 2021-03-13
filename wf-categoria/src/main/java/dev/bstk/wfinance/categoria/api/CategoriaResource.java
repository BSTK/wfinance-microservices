package dev.bstk.wfinance.categoria.api;

import dev.bstk.wfinance.categoria.api.request.NovaCategoriaRequest;
import dev.bstk.wfinance.categoria.api.response.CategoriaResponse;
import dev.bstk.wfinance.categoria.api.response.CategoriaResumoResponse;
import dev.bstk.wfinance.categoria.domain.entidade.Categoria;
import dev.bstk.wfinance.categoria.domain.helper.Mapper;
import dev.bstk.wfinance.categoria.domain.helper.NovoRecursoCriadoEvento;
import dev.bstk.wfinance.categoria.domain.helper.PageHelper;
import dev.bstk.wfinance.categoria.domain.repository.CategoriaRepository;
import dev.bstk.wfinance.categoria.domain.service.CategoriaService;
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
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/categorias")
public class CategoriaResource {

    private final CategoriaService categoriaService;
    private final CategoriaRepository categoriaRepository;
    private final ApplicationEventPublisher applicationEventPublisher;

    @Autowired
    public CategoriaResource(final CategoriaService categoriaService,
                             final CategoriaRepository categoriaRepository,
                             final ApplicationEventPublisher applicationEventPublisher) {
        this.categoriaService = categoriaService;
        this.categoriaRepository = categoriaRepository;
        this.applicationEventPublisher = applicationEventPublisher;
    }

    @GetMapping
    public ResponseEntity<Page<CategoriaResponse>> categorias(
        @RequestParam(value = "nome", defaultValue = "", required = false) final String nome, final Pageable pageable) {

        final var pageCategorias = categoriaService.categorias(nome, pageable);
        final var categoriasResponse = Mapper.list(pageCategorias.getContent(), CategoriaResponse.class);
        final var pageCategoriasResponse = PageHelper.page(categoriasResponse, pageCategorias);

        return ResponseEntity.ok(pageCategoriasResponse);
    }

    @GetMapping("/resumo")
    public ResponseEntity<List<CategoriaResumoResponse>> categorias() {
        final var categorias = categoriaRepository.findAll();
        final var categoriasResponse = Mapper.list(categorias, CategoriaResumoResponse.class);
        return ResponseEntity.ok(categoriasResponse);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoriaResponse> categoriaPorId(@PathVariable("id") final Long id) {
        final Optional<Categoria> categoriaPorId = categoriaRepository.findById(id);

        if (categoriaPorId.isPresent()) {
            final var categoria = categoriaPorId.get();
            final var categoriaResponse = Mapper.map(categoria, CategoriaResponse.class);
            return ResponseEntity.ok(categoriaResponse);
        }

        return ResponseEntity.notFound().build();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<CategoriaResponse> novaCategoria(@RequestBody @Valid final NovaCategoriaRequest request,
                                                           final HttpServletResponse httpServletResponse) {
        final var categoriaSalva = categoriaService.novaCategoria(request);
        final var categoriaResponse = Mapper.map(categoriaSalva, CategoriaResponse.class);

        applicationEventPublisher.publishEvent(new NovoRecursoCriadoEvento(
            this, httpServletResponse, categoriaSalva.getId()
        ));

        return ResponseEntity.ok(categoriaResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable("id") final Long categoriaId) {
        categoriaService.excluir(categoriaId);
        return ResponseEntity.noContent().build();
    }

}
