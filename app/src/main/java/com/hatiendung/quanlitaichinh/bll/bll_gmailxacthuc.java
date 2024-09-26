package com.hatiendung.quanlitaichinh.bll;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import javax.mail.MessagingException;
import com.hatiendung.quanlitaichinh.dal.dal_gmailxacthuc;

public class bll_gmailxacthuc {
    private ExecutorService executorService;
    private String maxacminh = "", ma;
    private boolean kt;
    dal_gmailxacthuc mail = new dal_gmailxacthuc("aesusu11@gmail.com", "spfh rdyc uazm enhc");

    public boolean sendMail(String gmail){
        executorService = Executors.newSingleThreadExecutor();
        executorService.execute(() -> {
            try
            {
                maxacminh = mail.taoMa();
                setMa(maxacminh);
                mail.guiMail(gmail, "Mã xác thực", "Mã xác thực của bạn là: " + maxacminh);
                kt = true;
            }catch (MessagingException e)
            {
                e.printStackTrace();
                kt = false;
            }
        });

        executorService.shutdown();
        try {
            if (!executorService.awaitTermination(60, TimeUnit.SECONDS)) {
                executorService.shutdownNow();
            }
        } catch (InterruptedException e) {
            executorService.shutdownNow();
        }
        return kt;
    }

    public Boolean isGmail(String gmail){
        return mail.isGmail(gmail);
    }

    public void shutdownExecu(){
        if(!executorService.isShutdown()){
            executorService.shutdown();
        }
    }
    public void setMa(String maxacminh){
        this.ma = maxacminh;
    }
    public String getMa(){
        return ma;
    }
}
