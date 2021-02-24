package main;

import domain.Customer;
import domain.PopulateDB;
import domain.UserModel;
import domain.controllers.DomainController;
import repository.UserDaoJpa;

public class MainTester {
    public static void main(String[] args) {
        System.out.println("This class tests the same as the follow test in DomainTest:");
        System.out.println("loginAttempt_4InValidAdmin_3InValidTech_1InValidAdmin_AdminUserBlocked_1ValidTech_TechUserLoginSuccess_1ValidAdmin_AdminUserStillBlocked()\n");
        UserDaoJpa userDaoJpa = new UserDaoJpa();
        PopulateDB populateDB = new PopulateDB();
        populateDB.run(userDaoJpa);
        System.out.println("populateDB successful");
        
//        UserModel a = new Administrator("Admin123", "PassWd123&","Jan", "A");
//        UserModel t = new Technician("Tech123", "PassWd123&","Pol", "T");
        
        //TODO or do we have to do something like this?
        //GenericDao genericDao = new GenericDaoJpa(UserModel.class);

        DomainController dc = new DomainController(userDaoJpa);

        // 4 failed login attempts for Admin123
        for (int i = 0; i < 4; i++) {
            try {
            	System.out.println("\ndc.signIn(\"Admin123\", \"Passwd123\")");
                dc.signIn("Admin123", "Passwd123");
            } catch (Exception e) {
//              e.printStackTrace();
            	System.out.println(e.getMessage());
            }
        }
        // 3 failed login attempts for Tech123
        for (int i = 0; i < 3; i++) {
            try {
            	System.out.println("\ndc.signIn(\"Tech123\", \"Passwd123\")");
                dc.signIn("Tech123", "Passwd123");
            } catch (Exception e) {
//              e.printStackTrace();
            	System.out.println(e.getMessage());
            }
        }
        // 1 failed login attempts for Admin123
        // the 4 previous failed login attempts have been saved in the DB
        // the system knows this will be the 5th failed login attempt
        // the system blocks the user
        try {
        	System.out.println("\ndc.signIn(\"Admin123\", \"Passwd123\")");
            dc.signIn("Admin123", "Passwd123");	
		} catch (Exception e) {
//          e.printStackTrace();
			System.out.println(e.getMessage());
		}
        
        // 1 successful login attempt for Tech123
    	System.out.println("\ndc.signIn(\"Tech123\", \"Passwd123&\")");
        dc.signIn("Tech123", "Passwd123&");
        
        // Admin123 is already blocked but still tries to sign in
        // this time with the correct password but since the accout 
        // is blocked this will result in a FAILED login attempt
        try {
        	System.out.println("\ndc.signIn(\"Admin123\", \"Passwd123&\")");
            dc.signIn("Admin123", "Passwd123&");	
		} catch (Exception e) {
//          e.printStackTrace();
			System.out.println(e.getMessage());
		}
        
        // Can't register if username is already taken
        try {
        	System.out.println("\ndc.registerCustomer(\"Admin123\", \"Passwd123&\", \"Thierry\", \"Kempens\")");
        	dc.registerCustomer("Admin123", "Passwd123&", "Thierry", "Kempens");
		} catch (Exception e) {
//          e.printStackTrace();
			System.out.println(e.getMessage());
		}
        
        Customer customer = (Customer) userDaoJpa.findByUsername("Cust123");
        
        System.out.printf("%nCustomer before modifyCustomer:%nFirst name: %s%nLast name: %s%n", customer.getFirstName(), customer.getLastName());
        
        dc.modifyCustomer(customer, "Cust123", "Passwd123&", "Thierry", "Kempens");  
        
        System.out.printf("%nCustomer after modifyCustomer:%nFirst name: %s%nLast name: %s%n%n", customer.getFirstName(), customer.getLastName());
        
        System.out.println(dc.giveCustomerList());
        dc.giveCustomerList().stream().map(UserModel::getUsername).forEach(System.out::println);
        System.out.println();
        System.out.println(dc.giveEmployeeList());
        dc.giveEmployeeList().stream().map(UserModel::getUsername).forEach(System.out::println);

        userDaoJpa.closePersistency();
    }

}
