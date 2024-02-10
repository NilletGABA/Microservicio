package com.paymentchain.transactions.repository;

import com.paymentchain.transactions.entities.Transaction;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 *
 * @author Nillet Gamboa
 */
public interface TransactionsRepository extends JpaRepository<Transaction, Long> {

    @Query("SELECT t FROM Transaction t WHERE t.ibanAccount = ?1")
    public List<Transaction> findByIbanAccount(String ibanAccount);

}
