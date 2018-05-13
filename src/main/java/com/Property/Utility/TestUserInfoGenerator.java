package com.Property.Utility;

public class TestUserInfoGenerator
{
	public void printInfo()
	{
		CryptoUtil cryptoUtil=new CryptoUtil();
		int saltLength=128;

		String ManagerPassword="mana";
		String ManagerSalt=cryptoUtil.getRandomString(saltLength);
		ManagerPassword=cryptoUtil.getHashedPassword(ManagerPassword,"Manager",ManagerSalt);

		String SupervisorPassword="supe";
		String SupervisorSalt=cryptoUtil.getRandomString(saltLength);
		SupervisorPassword=cryptoUtil.getHashedPassword(SupervisorPassword,"Supervisor",SupervisorSalt);

		String GuardPassword="guar";
		String GuardSalt=cryptoUtil.getRandomString(saltLength);
		GuardPassword=cryptoUtil.getHashedPassword(GuardPassword,"Guard",GuardSalt);

		String CleanerPassword="clea";
		String CleanerSalt=cryptoUtil.getRandomString(saltLength);
		CleanerPassword=cryptoUtil.getHashedPassword(CleanerPassword,"Cleaner",CleanerSalt);

		String RepairmanPassword="repa";
		String RepairmanSalt=cryptoUtil.getRandomString(saltLength);
		RepairmanPassword=cryptoUtil.getHashedPassword(RepairmanPassword,"Repairman",RepairmanSalt);

		String AccountantPassword="acco";
		String AccountantSalt=cryptoUtil.getRandomString(saltLength);
		AccountantPassword=cryptoUtil.getHashedPassword(AccountantPassword,"Accountant",AccountantSalt);

		String ReceptionistPassword="rece";
		String ReceptionistSalt=cryptoUtil.getRandomString(saltLength);
		ReceptionistPassword=cryptoUtil.getHashedPassword(ReceptionistPassword,"Receptionist",ReceptionistSalt);

		String ProprietorPassword="prop";
		String ProprietorSalt=cryptoUtil.getRandomString(saltLength);
		ProprietorPassword=cryptoUtil.getHashedPassword(ProprietorPassword,"Proprietor",ProprietorSalt);



		System.out
				.println("INSERT INTO UserInfo\n VALUES(0,'Manager','Manager','"+ManagerPassword+"','"+ManagerSalt+"',TRUE);\nINSERT INTO UserRole\n VALUES(0,0);");
		System.out
				.println("INSERT INTO UserInfo\n VALUES(1,'Supervisor','Supervisor','"+SupervisorPassword+"','"+SupervisorSalt+"',TRUE);\nINSERT INTO UserRole\n VALUES(1,1);");
		System.out
				.println("INSERT INTO UserInfo\n VALUES(2,'Guard','Guard','"+GuardPassword+"','"+GuardSalt+"',TRUE);\nINSERT INTO UserRole\n VALUES(2,2);");
		System.out
				.println("INSERT INTO UserInfo\n VALUES(3,'Cleaner','Cleaner','"+CleanerPassword+"','"+CleanerSalt+"',TRUE);\nINSERT INTO UserRole\n VALUES(3,3);");
		System.out
				.println("INSERT INTO UserInfo\n VALUES(4,'Repairman','Repairman','"+RepairmanPassword+"','"+RepairmanSalt+"',TRUE);\nINSERT INTO UserRole\n VALUES(4,4);");
		System.out
				.println("INSERT INTO UserInfo\n VALUES(5,'Accountant','Accountant','"+AccountantPassword+"','"+AccountantSalt+"',TRUE);\nINSERT INTO UserRole\n VALUES(5,5);");
		System.out
				.println("INSERT INTO UserInfo\n VALUES(6,'Receptionist','Receptionist','"+ReceptionistPassword+"','"+ReceptionistSalt+"',TRUE);\nINSERT INTO UserRole\n VALUES(6,6);");
		System.out
				.println("INSERT INTO UserInfo\n VALUES(7,'Proprietor','Proprietor','"+ProprietorPassword+"','"+ProprietorSalt+"',TRUE);\nINSERT INTO UserRole\n VALUES(7,7);");

	}
}
