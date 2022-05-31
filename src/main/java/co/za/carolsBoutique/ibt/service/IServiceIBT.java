package co.za.carolsBoutique.ibt.service;

import co.za.carolsBoutique.ibt.model.IBT;
import java.util.List;
import java.util.Map;

public interface IServiceIBT {
    String requestIBT(IBT ibt);
    IBT getIBT(String ibtId);
    List<IBT> findStoreIBTS(String storeId);
    String approveIBT(Map<String,Boolean> approvedIBT);
    String rejectIBT(String ibtId);
}
