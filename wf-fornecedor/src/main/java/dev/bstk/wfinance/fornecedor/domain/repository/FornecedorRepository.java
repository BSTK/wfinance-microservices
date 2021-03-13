package dev.bstk.wfinance.fornecedor.domain.repository;

import dev.bstk.wfinance.fornecedor.domain.entidade.Fornecedor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface FornecedorRepository extends JpaRepository<Fornecedor, Long>, FornecedorRepositoryQuery {

    @Query("SELECT CASE WHEN COUNT(p) > 0 THEN true ELSE false END FROM Fornecedor p "
        + "WHERE UPPER(p.endereco.cep)         = UPPER(:cep) "
        + "AND   UPPER(p.endereco.logradouro)  = UPPER(:logradouro) "
        + "AND   UPPER(p.endereco.numero)      = UPPER(:numero) ")
    boolean existeEnderecoCadastrado(@Param("cep") final String cep,
                                     @Param("logradouro") final String logradouro,
                                     @Param("numero") final String numero);

    @Query("SELECT CASE WHEN COUNT(p) > 0 THEN true ELSE false END FROM Fornecedor p "
        + "WHERE TRIM(UPPER(p.nome)) = TRIM(UPPER(:nome)) ")
    boolean existeFornecedorCadastradaComNome(@Param("nome") final String nome);

}
