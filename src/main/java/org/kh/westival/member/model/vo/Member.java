package org.kh.westival.member.model.vo;

import java.sql.Date;

public class Member implements java.io.Serializable {
   private static final long serialVersionUID = 9999L;
   private String user_id;
   private String user_pwd;
   private String user_name;
   private Date user_birth;
   private String user_address;
   private String user_phone;
   private String user_email;
   private String user_gender;
   
   
   public Member() { }

   public Member(String user_id, String user_pwd, String user_name, Date user_birth, String user_address,
         String user_phone, String user_email, String user_gender) {
      super();
      this.user_id = user_id;
      this.user_pwd = user_pwd;
      this.user_name = user_name;
      this.user_birth = user_birth;
      this.user_address = user_address;
      this.user_phone = user_phone;
      this.user_email = user_email;
      this.user_gender = user_gender;
   
   }

   public String getUser_id() {
      return user_id;
   }

   public void setUser_id(String user_id) {
      this.user_id = user_id;
   }

   public String getUser_pwd() {
      return user_pwd;
   }

   public void setUser_pwd(String user_pwd) {
      this.user_pwd = user_pwd;
   }

   public String getUser_name() {
      return user_name;
   }

   public void setUser_name(String user_name) {
      this.user_name = user_name;
   }

   public Date getUser_birth() {
      return user_birth;
   }

   public void setUser_birth(Date user_birth) {
      this.user_birth = user_birth;
   }

   public String getUser_address() {
      return user_address;
   }

   public void setUser_address(String user_address) {
      this.user_address = user_address;
   }

   public String getUser_phone() {
      return user_phone;
   }

   public void setUser_phone(String user_phone) {
      this.user_phone = user_phone;
   }

   public String getUser_email() {
      return user_email;
   }

   public void setUser_email(String user_email) {
      this.user_email = user_email;
   }

   public String getUser_gender() {
      return user_gender;
   }

   public void setUser_gender(String user_gender) {
      this.user_gender = user_gender;
   }

@Override
public String toString() {
	return "Member [user_id=" + user_id + ", user_pwd=" + user_pwd + ", user_name=" + user_name + ", user_birth="
			+ user_birth + ", user_address=" + user_address + ", user_phone=" + user_phone + ", user_email="
			+ user_email + ", user_gender=" + user_gender + "]";
}

   

  }