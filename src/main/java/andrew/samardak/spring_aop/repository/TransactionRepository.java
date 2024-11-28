package andrew.samardak.spring_aop.repository;

import andrew.samardak.spring_aop.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    BigDecimal sumAmountByAccountId(Long accountId);

    List<Transaction> findByAccountId(Long accountId);
}
