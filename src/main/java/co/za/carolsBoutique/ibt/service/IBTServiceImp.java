package co.za.carolsBoutique.ibt.service;

import co.za.carolsBoutique.codeGenerator.CodeGenerator;
import co.za.carolsBoutique.ibt.model.IBT;
import co.za.carolsBoutique.ibt.repository.IIBTRepository;
import java.util.List;
import java.util.Map;

public class IBTServiceImp implements IServiceIBT{
     private IIBTRepository dao;
    private CodeGenerator gen;
    public IBTServiceImp(IIBTRepository dao,CodeGenerator gen){
        this.dao = dao;
        this.gen = gen;
    }

    @Override
    public String requestIBT(IBT ibt) {
        return dao.addIBT(ibt)?"IBT has been requested":"IBT request failed";
    }

    @Override
    public IBT getIBT(String ibtId) {
        return dao.findIBT(ibtId);    
    }

    @Override
    public List<IBT> findStoreIBTS(String storeId) {
        return dao.findStoreIBTS(storeId);
    }

    @Override
    public String approveIBT(Map<String, Boolean> details) {
        String id = details.keySet().iterator().next();
        boolean value = details.get(id);
            if(value){
                return dao.updateIBT(id, value)?"IBT was approved":"IBT approval failed";
            }else{
                return dao.updateIBT(id, value)?"IBT was rejected":"IBT rejection failed";
            }
    }

  

   
    
}
