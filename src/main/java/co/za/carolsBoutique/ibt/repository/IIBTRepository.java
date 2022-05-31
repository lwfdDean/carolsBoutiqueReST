package co.za.carolsBoutique.ibt.repository;

import co.za.carolsBoutique.ibt.model.IBT;
import java.util.List;

public interface IIBTRepository {
    boolean addIBT(IBT ibt);
    IBT findIBT(String ibtId);
    List<IBT> findStoreIBTS(String storeId);
    List<IBT> findStoreIBTRequests(String storeId);
    boolean updateIBT(String ibtId, boolean approved);
    boolean deleteIBT(String ibtId);
}
