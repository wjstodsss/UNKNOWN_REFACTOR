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
    private String withdrawal; // 회원 탈퇴 여부를 저장하는 필드
    private String withdrawalMessage; // 탈퇴 여부 메시지를 저장하기 위한 필드
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
