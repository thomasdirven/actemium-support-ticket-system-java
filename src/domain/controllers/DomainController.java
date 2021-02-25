package domain.controllers;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;

import domain.Customer;
import domain.Employee;
import domain.EmployeeRole;
import domain.LoginAttempt;
import domain.LoginStatus;
import domain.UserModel;
import domain.UserStatus;
import exceptions.BlockedUserException;
import exceptions.PasswordException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import languages.LanguageResource;
import repository.UserDao;
import repository.UserDaoJpa;

public class DomainController {

	// For now we are still working with a single DomainController
	// In the next sprint this will be replaced by:
	// - Abstract GuiController with e.g. LoginController inheriting from it
	// - Facade Interface in pacakge domain.facades
	// - UserModelFacade implements Facade
	// - SignedInUserManagaer in domain.facades
	// - ...	
	
	private UserModel signedInUser;
	private UserModel selectedUser;
	private UserDao userRepo;
		
	private static final int USER_LOGIN_MAX_ATTEMPTS = 5;

	private ObservableList<UserModel> customerList;
	private ObservableList<UserModel> employeeList;
	
	public DomainController(UserDao userRepo) {
		this.userRepo = userRepo;
		fillInUserLists();

	}

	private void fillInUserLists(){
		List<UserModel> userList = userRepo.findAll();
		List<UserModel> customerList = userList.stream()
				.filter(c -> c instanceof Customer)
				.map(c -> (Customer) c)
				.collect(Collectors.toList());
		List<UserModel> employeeList = userList.stream()
				.filter(e -> e instanceof Employee)
				.map(e -> (Employee) e)
				.collect(Collectors.toList());

		this.customerList = FXCollections.observableArrayList(customerList);
		this.employeeList = FXCollections.observableArrayList(employeeList);
	}
	
	//TODO constructor vs setter injection?
	public DomainController() {
		this(new UserDaoJpa());
//		setUserRepo(new UserDaoJpa());
	}

	public void setUserRepo(UserDao mock) {
		userRepo = mock;
	}
		
	private void setSignedInUser(UserModel signedInEmployee) {
		this.signedInUser = signedInEmployee;
	}

	public UserModel getSelectedUser() {
		return selectedUser;
	}

	public void setSelectedUser(UserModel selectedUser) {
		this.selectedUser = selectedUser;
	}

	public void signIn(String username, String password) {
		UserModel userModel = userRepo.findByUsername(username);

		if (password.isBlank()) {
			throw new PasswordException(LanguageResource.getString("password_blank"));
		}

		userRepo.startTransaction();
		
		// user account already blocked, only the failed login attempt needs to registered
		if (userModel.getStatus().equals(UserStatus.BLOCKED)) {
			userModel.increaseFailedLoginAttempts();
			LoginAttempt loginAttempt = new LoginAttempt(LocalDateTime.now(), userModel, LoginStatus.FAILED);	
			userModel.addLoginAttempt(loginAttempt);
//			userRepo.registerLoginAttempt(userModel, LoginStatus.FAILED);
//			userRepo.registerLoginAttempt(loginAttempt);
			userRepo.commitTransaction();
			throw new BlockedUserException(String.format(
					"User account has been blocked because more than%n %d failed login attempts have been registered."
					+ "%nPlease contact your system administrator.",
					USER_LOGIN_MAX_ATTEMPTS));
		}
				
		// check password
		if (!userModel.getPassword().equals(password)) {
			userModel.increaseFailedLoginAttempts();
			LoginAttempt loginAttempt = new LoginAttempt(LocalDateTime.now(), userModel, LoginStatus.FAILED);	
			userModel.addLoginAttempt(loginAttempt);
//			userRepo.registerLoginAttempt(userModel, LoginStatus.FAILED);
//			userRepo.registerLoginAttempt(loginAttempt);
			// block user after 5 failed login attempts
			if (userModel.getFailedLoginAttempts() >= USER_LOGIN_MAX_ATTEMPTS) {
				userModel.blockUser();
				userRepo.commitTransaction();
				throw new BlockedUserException(
						String.format("Wrong password%nUser has reached more than %d failed login attempts,%n account has been blocked.",
								USER_LOGIN_MAX_ATTEMPTS));
			}
			userRepo.commitTransaction();
			throw new PasswordException(String.format("Wrong password%nOnly %d attempts remaining", 5 - userModel.getFailedLoginAttempts()));
		}

		// correct password
		userModel.resetLoginAttempts();

		LoginAttempt loginAttempt = new LoginAttempt(LocalDateTime.now(), userModel, LoginStatus.SUCCESS);	
		userModel.addLoginAttempt(loginAttempt);
		
//		userRepo.registerLoginAttempt(userModel, LoginStatus.SUCCESS);
//		userRepo.registerLoginAttempt(loginAttempt);

		userRepo.commitTransaction();

		setSignedInUser(userModel);
		
		System.out.println("Just signed in: " + signedInUser.getUsername());
	}

	public String giveUserType() {
		if(signedInUser instanceof Employee) {
			return ((Employee) signedInUser).getRole().toString();
		}
		return "Customer";
	}

	public String giveUsername() {
		return signedInUser.getUsername();
	}

	public String giveUserFirstName() {
		return signedInUser.getFirstName();
	}

	public String giveUserLastName() {
		return signedInUser.getLastName();
	}

	public void existingUsername(String username) {
		try {
			if(userRepo.findByUsername(username) != null) {
				throw new IllegalArgumentException("Username is already taken.");
			}
		} catch (EntityNotFoundException e){
			//ignore
		}

	}

	public void registerCustomer(String username, String password, String firstName, String lastName) {
		existingUsername(username);		
		Customer customer = new Customer(username, password, firstName, lastName);
		userRepo.startTransaction();
		userRepo.insert(customer);
		userRepo.commitTransaction();
		fillInUserLists();

	}

	public void registerEmployee(String username, String password, String firstName, String lastName, String address,
			String phoneNumber, String emailAddress, EmployeeRole role) {
		existingUsername(username);
		Employee employee = new Employee(username, password, firstName, lastName, address, phoneNumber, emailAddress, role);
		userRepo.startTransaction();
		userRepo.insert(employee);
		userRepo.commitTransaction();
		fillInUserLists();

	}

	public void modifyCustomer(Customer customer, String username, String password, String firstName, String lastName) {
		// only needs to be checked if you changed the username 
		if (!customer.getUsername().equals(username)) {
			existingUsername(username);
		}
		customer.setUsername(username);
		customer.setPassword(password);
		customer.setFirstName(firstName);
		customer.setLastName(lastName);
		userRepo.startTransaction();
		userRepo.update(customer);
		userRepo.commitTransaction();
	}

	public void modifyEmployee(Employee employee, String username, String password, String firstName, String lastName, String address,
			String phoneNumber, String emailAddress, EmployeeRole role) {
		// only needs to be checked if you changed the username 
		if (!employee.getUsername().equals(username)) {
			existingUsername(username);
		}
		employee.setUsername(username);
		employee.setPassword(password);
		employee.setFirstName(firstName);
		employee.setLastName(lastName);
		employee.setAddress(address);
		employee.setPhoneNumber(phoneNumber);
		employee.setEmailAddress(emailAddress);
		employee.setRole(role);
		userRepo.startTransaction();
		userRepo.update(employee);
		userRepo.commitTransaction();
	}

	public ObservableList<UserModel> giveCustomerList() {
		return FXCollections.unmodifiableObservableList(customerList);
	}

	public ObservableList<UserModel> giveEmployeeList() {
		return FXCollections.unmodifiableObservableList(employeeList);
	}
	 
	
}
