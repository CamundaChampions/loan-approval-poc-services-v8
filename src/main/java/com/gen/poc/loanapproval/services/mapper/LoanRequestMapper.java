package com.gen.poc.loanapproval.services.mapper;

import com.gen.poc.loanapproval.repository.entity.*;
import com.gen.poc.loanapproval.web.dto.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface LoanRequestMapper {
	@Mapping(target = "loanApplicationId", ignore = true)
	@Mapping(target = "comments", ignore = true)
	@Mapping(target = "processInstanceId", ignore = true)
	@Mapping(target = "status", ignore = true)
	@Mapping(target = "customerId", ignore = true)
	LoanApplication toLoanRequestEntityOnCreate(LoanRequestDTO dto);

	List<LoanSummaryDto> mapTo(List<LoanSummary> source);
//
//	@Mapping(target = "id", ignore = true)
//	@Mapping(target = "loanRequest", ignore = true)
//    Customer toCustomerEntity(CustomerDTO dto);
//
//	@Mapping(target = "id", ignore = true)
//	@Mapping(target = "loanRequest", ignore = true)
//	LoanDocuments toLoanDocumentEntity(LoanDocumentDTO loanDocumentDTO);
//
//	@Mapping(target = "id", ignore = true)
//	@Mapping(target = "loanRequest", ignore = true)
//    CashDepositCollateral toCashDepositCollateralEntity(CashDepositCollateralDTO cashDepositCollateralDTO);
//
//	LoanRequestDTO toDto(LoanApplication model);
//
//	@Mapping(target = "id", ignore = true)
//	@Mapping(target = "customer", ignore = true)
//    Address toAddressEntity(AddressDTO addressDTO);
//
//	@Mapping(target = "id", ignore = true)
//	@Mapping(target = "customer", ignore = true)
//    Employment toEmploymentEntity(EmploymentDTO employmentDTO);
//
//	@Mapping(target = "id", ignore = true)
//	@Mapping(target = "customer", ignore = true)
//    DepositAccount toDepositAccountEntity(DepositAccountDTO depositAccountDTO);
//
//	DepositAccountDTO toDepositAccountDto(DepositAccount depositAccount);
//
//	LoanDocumentDTO toLoanDocumentDto(LoanDocuments loanDocuments);
//
//	LoanDetailsDTO toLoanDetailsDto(LoanApplication loanApplication);
}