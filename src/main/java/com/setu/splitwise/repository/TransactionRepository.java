package com.setu.splitwise.repository;

import com.setu.splitwise.dto.dbResponse.UserExpenseResponse;
import com.setu.splitwise.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

        @Query("SELECT " +
                "new com.setu.splitwise.dto.dbResponse.UserExpenseResponse(t.paidBy.id as userId, SUM(t.amount) as amount) " +
                "FROM " +
                "Transaction t "+
                " where t.paidInGroup.id = :groupId " +
                "GROUP BY " +
                "    t.paidBy.id ")
        UserExpenseResponse getTotalExpenseAmountForUser(@Param("groupId") Long groupId);
}
