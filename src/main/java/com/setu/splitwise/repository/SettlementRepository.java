package com.setu.splitwise.repository;

import com.setu.splitwise.dto.dbResponse.SettlementResponse;
import com.setu.splitwise.model.Settlement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SettlementRepository extends JpaRepository<Settlement, Long> {

    @Query("SELECT " +
            "new com.setu.splitwise.dto.dbResponse.SettlementResponse(t.paidTo.id as paidTo, t.amount as amount) " +
            "FROM " +
            "Settlement t "+
            "where t.group.id = :groupId and " +
            "t.paidBy.id = :userId and t.paidTo.id <> :userId")
    List<SettlementResponse> getTotalExpenseAmountForUser(@Param("groupId") Long groupId,@Param("userId") Long userId);
}
