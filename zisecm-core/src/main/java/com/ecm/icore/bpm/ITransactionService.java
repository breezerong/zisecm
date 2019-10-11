package com.ecm.icore.bpm;

import java.util.List;

import com.ecm.core.entity.EcmTransaction;

public interface ITransactionService {

	List<EcmTransaction> getTransactions(String token, String acitivtyId);

}
