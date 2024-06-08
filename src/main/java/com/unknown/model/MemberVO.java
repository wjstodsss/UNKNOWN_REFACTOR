package com.unknown.model;



import java.util.Date;

import lombok.Data;

@Data
public class MemberVO {

    private String memberId;
    private String memberPw;
    private String memberName;
    private String memberPhone;
    private String memberMail;
    private String memberAddr1;
    private String memberAddr2;
    private String memberAddr3;
    private int adminCk;
    private Date regDate;
    private int point;
    private String suspended;
    private String withdrawal; // ȸ�� Ż�� ���θ� �����ϴ� �ʵ�
    private String withdrawalMessage; // Ż�� ���� �޽����� �����ϱ� ���� �ʵ�
    private int result;
    
    public String getWithdrawal() {
        return withdrawal;
    }

    public String getWithdrawalMessage() {
        return withdrawalMessage;
    }
    
    public void setWithdrawal(String withdrawal) {
        this.withdrawal = withdrawal;
    }

    
    public void setWithdrawalMessage(String withdrawalMessage) {
        this.withdrawalMessage = withdrawalMessage;
    }
    
    public void setResult(int result) {
        this.result = result;
    }
}
