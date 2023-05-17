package model.services;

import java.time.LocalDate;

import model.entities.Contract;
import model.entities.Installment;

public class ContractService {

	private OnlinePaymentService onlinePaymentService;

	public ContractService(OnlinePaymentService onlinePaymentService) {
		this.onlinePaymentService = onlinePaymentService;
	}
	
	public void processContract(Contract contract, int months) {
		double basicQuote = contract.getTotalValue() / months;
		
		for (int i = 1; i <= months; i++) {
			LocalDate duoDate = contract.getDate().plusMonths(i);
			double interest = onlinePaymentService.interest(basicQuote, i);
			double fee = onlinePaymentService.paymentFee(basicQuote + interest);
			double quote = basicQuote + interest + fee;
			contract.getInstallments().add(new Installment(duoDate, quote));													
		}
	}

}
