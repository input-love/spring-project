package andrew.samardak.spring_aop.repository;

import andrew.samardak.spring_aop.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    List<Transaction> findByAccountId(Long accountId);

    @Query("SELECT SUM(t.amount) FROM Transaction t WHERE t.account.id = :accountId")
    BigDecimal sumAmountByAccountId(Long accountId);
}
