package com.forbitbd.financel.account_detail;

import com.forbitbd.financel.models.TransactionResponse;

import java.util.List;

public interface ParentListener {
    List<TransactionResponse> getTransaction();
}
