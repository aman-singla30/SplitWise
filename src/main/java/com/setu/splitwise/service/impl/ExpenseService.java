package com.setu.splitwise.service.impl;

import com.setu.splitwise.dto.*;
import com.setu.splitwise.dto.dbResponse.SettlementResponse;
import com.setu.splitwise.dto.dbResponse.UserExpenseResponse;
import com.setu.splitwise.enums.ExpenseType;
import com.setu.splitwise.helper.IExpenseServiceHelper;
import com.setu.splitwise.model.Group;
import com.setu.splitwise.model.Settlement;
import com.setu.splitwise.model.Transaction;
import com.setu.splitwise.model.User;
import com.setu.splitwise.repository.SettlementRepository;
import com.setu.splitwise.repository.TransactionRepository;
import com.setu.splitwise.service.IExpenseService;
import com.setu.splitwise.service.IGroupService;
import com.setu.splitwise.service.IUserService;
import com.setu.splitwise.strategy.ExpenseStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;


/**
 * @author amankumar
 * To serve Expense related operations like addExpense, Show Owe money
 */
@Service
public class ExpenseService implements IExpenseService {

    @Autowired
    private IExpenseServiceHelper expenseServiceHelper;

    @Autowired
    private IUserService userService;

    @Autowired
    private IGroupService groupService;

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private SettlementRepository settlementRepository;

    @Override
    @Transactional
    public ExpenseResponseDto addExpense(ExpenseRequestDto expenseRequestDto) {

        User paidByUser = userService.getUser(expenseRequestDto.getPaidByUserId());
        Group paidInGroup = groupService.getGroup(expenseRequestDto.getPaidInGroupId());
        List<Split> splits;
        if(expenseRequestDto.getExpenseType() == ExpenseType.EQUAL) {
            Map<Long, Integer> splitsMap = new HashMap<>();
            Set<User> allUsers = paidInGroup.getUsers();
            for (User user : allUsers) {
                splitsMap.put(user.getId(), null);
            }
            splits = expenseServiceHelper.getAllSplits(splitsMap, expenseRequestDto.getExpenseType());
        }
        else {
            splits = expenseServiceHelper.getAllSplits(expenseRequestDto.getSplits(), expenseRequestDto.getExpenseType());
        }
        ExpenseStrategy expenseStrategy = expenseServiceHelper
                .calculateExpense(expenseRequestDto.getAmount()
                        ,paidByUser,expenseRequestDto.getExpenseType(),splits, expenseRequestDto.getDescription());
        //make entry in transaction table to maintain all the Expenses information
        Transaction transaction = Transaction.builder()
                .transactionDate(LocalDate.now())
                .description(expenseRequestDto.getDescription())
                .amount(expenseRequestDto.getAmount())
                .paidBy(paidByUser)
                .paidInGroup(paidInGroup)
                .splits(expenseRequestDto.getSplits())
                .transactionType(expenseRequestDto.getExpenseType())
                .build();
        //Get Existing expenses from user
        List<SettlementResponse> expenseResponses = settlementRepository
                .getTotalExpenseAmountForUser(paidInGroup.getId(),paidByUser.getId());
        Map<Long, Double> paidToHistory = new HashMap<>();
        if(!CollectionUtils.isEmpty(expenseResponses)) {
            paidToHistory = expenseResponses.stream().collect(Collectors.
                    toMap(SettlementResponse::getPaidTo, SettlementResponse::getAmount));
        }

        for (Split split : expenseStrategy.getSplits()) {
            if(paidToHistory.containsKey(split.getUser().getId())) {
                split.setAmount(split.getAmount() + paidToHistory.get(split.getUser().getId()));
            }
        }
        //
        expenseStrategy.getSplits().forEach(split -> {
            Settlement settlement = Settlement.builder()
                    .group(paidInGroup).paidBy(paidByUser).paidTo(split.getUser()).amount(split.getAmount()).build();
            settlementRepository.save(settlement);
        });

        Transaction successTransaction = transactionRepository.save(transaction);
        ExpenseResponseDto expenseResponseDto = ExpenseResponseDto.builder().expenseId(successTransaction.getId())
                .amount(successTransaction.getAmount()).description(successTransaction.getDescription())
                .paidBy(successTransaction.getPaidBy().getId()).build();

        return expenseResponseDto;
    }

    @Override
    public TotalExpenseResponseDto calculateTotalExpense(TotalExpenseRequestDto totalExpenseRequestDto) {
        //check group and userId exists
        User user = userService.getUser(totalExpenseRequestDto.getUserId());
        Group group = groupService.getGroup(totalExpenseRequestDto.getGroupId());
        //Get Total expense amount by user in required group
        UserExpenseResponse userExpenseDBResponses = transactionRepository
                .getTotalExpenseAmountForUser(group.getId(), user.getId());
        List<SettlementResponse> expenseResponses = settlementRepository
                .getTotalExpenseAmountForUser(group.getId(),user.getId());

        Map<Long, Double> splitMap = new HashMap<>();
        expenseResponses.forEach(split ->splitMap.put(split.getPaidTo(), split.getAmount()));
        TotalExpenseResponseDto totalExpenseResponseDto = TotalExpenseResponseDto.builder()
                .totalPaidAmount(userExpenseDBResponses.getAmount()).userId(user.getId())
                .groupId(group.getId())
                .totalExpense(splitMap).build();
        return totalExpenseResponseDto;
    }
}
