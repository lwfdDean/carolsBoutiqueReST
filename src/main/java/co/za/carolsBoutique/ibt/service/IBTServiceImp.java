package co.za.carolsBoutique.ibt.service;

import co.za.carolsBoutique.codeGenerator.CodeGenerator;
import co.za.carolsBoutique.ibt.model.IBT;
import co.za.carolsBoutique.ibt.repository.IIBTRepository;
import co.za.carolsBoutique.mailService.MailService;
import jakarta.mail.MessagingException;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class IBTServiceImp implements IServiceIBT{
    private IIBTRepository dao;
    private CodeGenerator gen;
    public IBTServiceImp(IIBTRepository dao,CodeGenerator gen){
        this.dao = dao;
        this.gen = gen;
    }

    @Override
    public String requestIBT(IBT ibt) {
        ibt.setId(gen.generateId(ibt.getCustomerEmail(), ibt.getRequestingBoutique(), true));
        prepareMail(dao.getManagerEmail(ibt.getApprovingBoutique()), "New IBT Request Received", "");
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
        String requestingBoutique = getIBT(id).getRequestingBoutique();
        String managerEmail = dao.getManagerEmail(requestingBoutique);
            if(value){
                prepareMail(managerEmail, "IBT Approved", "");
            }else{
                prepareMail(managerEmail, "IBT Rejected", "");
            }
        return dao.updateIBT(id, value)?"Successfully Updated":"Error has Occured";
    }

   private void prepareMail(String emailAddress, String subject, String body) {
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    new MailService(emailAddress, subject, body).sendMail();
                } catch (MessagingException ex) {
                    Logger.getLogger(IBTServiceImp.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        t1.start();
    }
}