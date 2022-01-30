package com.setu.splitwise.service;

import com.setu.splitwise.dto.ExpenseRequestDto;
import com.setu.splitwise.dto.TotalExpenseRequestDto;
import com.setu.splitwise.dto.dbResponse.SettlementResponse;
import com.setu.splitwise.dto.dbResponse.UserExpenseResponse;
import com.setu.splitwise.enums.ExpenseType;
import com.setu.splitwise.helper.IExpenseServiceHelper;
import com.setu.splitwise.helper.impl.ExpenseServiceHelper;
import com.setu.splitwise.model.Group;
import com.setu.splitwise.model.Settlement;
import com.setu.splitwise.model.Transaction;
import com.setu.splitwise.model.User;
import com.setu.splitwise.repository.SettlementRepository;
import com.setu.splitwise.repository.TransactionRepository;
import com.setu.splitwise.service.impl.ExpenseService;
import org.junit.jupiter.api.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * @author amankumar
 * Junit Test cases for User Service
 */
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ExpenseServiceTest {

    @Mock
    private TransactionRepository transactionRepository;

    @Mock
    private SettlementRepository settlementRepository;

    @InjectMocks
    private IExpenseServiceHelper expenseServiceHelper = Mockito.spy(ExpenseServiceHelper.class);

    @Mock
    private IUserService userService;

    @Mock
    private IGroupService groupService;

    @InjectMocks
    private IExpenseService expenseService = new ExpenseService();

    private static User user1;
    private static User user2;
    private static Group group;
    private static ExpenseRequestDto expenseRequestDto;
    private static Settlement settlement;
    private static Transaction transaction;
    private static UserExpenseResponse expenseResponse;
    private static SettlementResponse settlementResponse;
    private static TotalExpenseRequestDto totalExpenseRequestDto;
    @BeforeAll
    static void setUp() {
        user1= User.builder().id(1L).name("Aman")
                .phoneNumber("987676789").email("aman.singl3000@gmail.com").build();
        user2= User.builder().id(2L).name("Aman")
                .phoneNumber("987676789").email("aman.singl3000@gmail.com").build();
        group = Group.builder().id(3L).type("Milk").name("House Expenses").build();
        Map<Long, Integer> splits = new HashMap<>();
        splits.put(1L, 30);
        splits.put(2L,70);
        expenseRequestDto = ExpenseRequestDto.builder()
                .expenseType(ExpenseType.PERCENT)
                .amount(1000.0)
                .paidByUserId(1L)
                .paidInGroupId(3L)
                .description("Milk")
                .splits(splits).build();

        settlement = Settlement.builder().amount(70.0).paidTo(user2).group(group).paidBy(user1).build();
        transaction = Transaction.builder().transactionType(ExpenseType.PERCENT).paidBy(user1).paidInGroup(group).description("Milk")
                .id(7L).amount(1000.0).build();

        expenseResponse = UserExpenseResponse.builder().userId(user1.getId()).amount(1000.0).build();
        settlementResponse = SettlementResponse.builder().amount(700.0).paidTo(user2.getId()).build();
        totalExpenseRequestDto = TotalExpenseRequestDto.builder().groupId(group.getId()).userId(user1.getId()).build();
    }

    @DisplayName("Add Expense")
    @Test
    @Order(1)
    void testAddExpense() {
        Mockito.when(userService.getUser(Mockito.any())).thenReturn(user1);
        Mockito.when(groupService.getGroup(Mockito.any())).thenReturn(group);
        Mockito.when(settlementRepository.save(Mockito.any())).thenReturn(settlement);
        Mockito.when(transactionRepository.save(Mockito.any())).thenReturn(transaction);
        Assertions.assertNotNull(expenseService.addExpense(expenseRequestDto));
    }

    @DisplayName("Calculate Expense")
    @Test
    @Order(2)
    void testCalculateExpense() {
        Mockito.when(userService.getUser(Mockito.any())).thenReturn(user1);
        Mockito.when(groupService.getGroup(Mockito.any())).thenReturn(group);

        Mockito.when(settlementRepository.getTotalExpenseAmountForUser(Mockito.any(), Mockito.any())).thenReturn(Collections.singletonList(settlementResponse));
        Mockito.when(transactionRepository.getTotalExpenseAmountForUser(Mockito.any())).thenReturn(expenseResponse);
        Assertions.assertNotNull(expenseService.calculateTotalExpense(totalExpenseRequestDto));
    }

}
