package domain;

import java.io.Serial;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

import domain.enums.RequiredElement;
import exceptions.InformationRequiredException;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

// Customer is the contactPerson of a company
// Company is the "real" Customer, but for ease of naming
// we will use the name Customer for contactPerson
// Could be changed if we realy have to

/**
 * The type Actemium customer.
 */
@Entity
@Access(AccessType.FIELD)
public class ActemiumCustomer extends UserModel implements Customer, Seniority {
	@Serial
	private static final long serialVersionUID = 1L;
	
	@ManyToOne(cascade = CascadeType.PERSIST)
	private ActemiumCompany company;
	
	@Column(unique = true)
	private String emailAddress;	

	@Transient
	private StringProperty customerIdProperty = new SimpleStringProperty();
	
	/**
	 * Instantiates a new Actemium customer.
	 */
	public ActemiumCustomer() {
		super();
	}
	

	/**
	 * Instantiates a new Actemium customer.
	 *
	 * @param builder the builder
	 */
	public ActemiumCustomer(CustomerBuilder builder){
		super(builder.username, builder.password, builder.firstName, builder.lastName);
		this.company = builder.company;
		this.emailAddress = builder.emailAddress;
	}

	/**
	 * Gets the email address.
	 *
	 * @return email address
	 */
	public String getEmailAddress() {
		return emailAddress;
	}

	/**
	 * Sets email address.
	 *
	 * @param emailAddress the email address
	 */
	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

	/**
	 *	Gets the customer nr. In fact the userid.
	 *
	 * @return customer nr
	 */
	public int getCustomerNr() {
		return (int) super.getUserId();
	}
	
	/**
	 *	Gets the property customer nr. In fact the userid.
	 *
	 * @return customer nr
	 */
	public StringProperty customerIdProperty() {
		customerIdProperty.set(Integer.toString(super.getUserId()));
		return customerIdProperty;
	}

	/**
	 * Gets contracts.
	 *
	 * @return the contracts
	 */
	public List<ActemiumContract> getContracts() {
		return company.getActemiumContracts();
	}

	/**
	 * Gives the contracts in a observable list.
	 *
	 * @return ObservableList of contracts
	 */
	public ObservableList<Contract> giveContracts() {
		return (ObservableList<Contract>) (Object) FXCollections.observableList(company.giveContracts());
	}

	/**
	 * Gets company.
	 *
	 * @return the company
	 */
	public ActemiumCompany getCompany() {
		return company;
	}

	/**
	 * Give the company of a customer.
	 *
	 * @return company
	 */
	public Company giveCompany() {
		return (Company) company;
	}

	/**
	 * Sets company.
	 *
	 * @param company the company
	 */
	public void setCompany(ActemiumCompany company) {
		this.company = company;
	}

	/**
	 * Add ticket.
	 *
	 * @param ticket the ticket
	 */
	public void addTicket(ActemiumTicket ticket) {
//		tickets.add(ticket);
		company.addActemiumTicket(ticket);
	}

	/**
	 * Add contract.
	 *
	 * @param contract the contract
	 */
	public void addContract(ActemiumContract contract) {
//		contracts.add(contract);
		company.addActemiumContract(contract);
	}

	/**
	 * Give seniority int for the customer.
	 *
	 * @return the int
	 */
	@Override
	public int giveSeniority() {
		return LocalDate.now().getYear() - super.getRegistrationDate().getYear();
	}

	/**
	 * Check attributes.
	 *
	 * @throws InformationRequiredException the information required exception
	 */
	public void checkAttributes() throws InformationRequiredException {
		new CustomerBuilder()
				.username(super.usernameProperty().get())
				.password(super.getPassword())
				.firstName(super.firstNameProperty().get())
				.lastName(super.lastNameProperty().get())
				.emailAddress(this.getEmailAddress())
				.company(this.company)
				.registrationDate(super.getRegistrationDate())
				.build();
	}

	/**
	 * The type Customer builder.
	 */
	public static class CustomerBuilder {
		private String username;
		private String password;
		private String firstName;
		private String lastName;
		private String emailAddress;

		private ActemiumCompany company;
		private LocalDate registrationDate;

		private Set<RequiredElement> requiredElements;


		/**
		 * Username customer builder.
		 *
		 * @param username the username
		 * @return the customer builder
		 */
		public CustomerBuilder username(String username) {
			this.username = username;
			return this;
		}

		/**
		 * Password customer builder.
		 *
		 * @param password the password
		 * @return the customer builder
		 */
		public CustomerBuilder password(String password) {
			this.password = password;
			return this;
		}

		/**
		 * First name customer builder.
		 *
		 * @param firstName the first name
		 * @return the customer builder
		 */
		public CustomerBuilder firstName(String firstName) {
			this.firstName = firstName;
			return this;
		}

		/**
		 * Last name customer builder.
		 *
		 * @param lastName the last name
		 * @return the customer builder
		 */
		public CustomerBuilder lastName(String lastName) {
			this.lastName = lastName;
			return this;
		}

		/**
		 * Email address customer builder.
		 *
		 * @param emailAddress the email address
		 * @return the customer builder
		 */
		public CustomerBuilder emailAddress(String emailAddress) {
			this.emailAddress = emailAddress;
			return this;
		}

		/**
		 * Company customer builder.
		 *
		 * @param company the company
		 * @return the customer builder
		 */
		public CustomerBuilder company(ActemiumCompany company) {
			this.company = company;
			return this;
		}

		/**
		 * Registration date customer builder.
		 *
		 * @param registrationDate the registration date
		 * @return the customer builder
		 */
		public CustomerBuilder registrationDate(LocalDate registrationDate) {
			this.registrationDate = registrationDate;
			return this;
		}

		/**
		 * Build actemium customer.
		 *
		 * @return the actemium customer
		 * @throws InformationRequiredException the information required exception
		 */
		public ActemiumCustomer build() throws InformationRequiredException {
			requiredElements = new HashSet<>();
			checkAttributesEmployeeBuiler();
			return new ActemiumCustomer(this);
		}

		private void checkAttributesEmployeeBuiler() throws InformationRequiredException {
			if (username == null || username.isBlank() || !username.matches("[A-Za-z0-9]+"))
				requiredElements.add(RequiredElement.UsernameRequired);
			if(password == null || password.isBlank() || !password.matches("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()????????{}:;',.?/*~$^+=<>]).{8,}$"))
				requiredElements.add(RequiredElement.PasswordRequired);
			if(firstName == null || firstName.isBlank() || !firstName.matches("[^0-9]+"))
				requiredElements.add(RequiredElement.FirstnameRequired);
			if(lastName == null || lastName.isBlank() || !lastName.matches("[^0-9]+"))
				requiredElements.add(RequiredElement.LastnameRequired);
			if (emailAddress == null || emailAddress.isBlank() || !emailAddress.matches("^[a-zA-Z0-9_!#$%&???*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+[.][a-zA-Z0-9-]{2,4}$"))
				requiredElements.add(RequiredElement.EmailRequired);
			if (company == null)
				requiredElements.add(RequiredElement.CompanyNameRequired);

			if (!requiredElements.isEmpty())
				throw new InformationRequiredException(requiredElements);

		}
	}

	/**
	 * This clones an actemium customer.
	 *
	 * @return Actemium Customer
	 * @throws CloneNotSupportedException throws a clone not supported exception
	 */
	@Override
	public ActemiumCustomer clone() throws CloneNotSupportedException {

		ActemiumCustomer cloned = null;
		try {
			cloned = new ActemiumCustomer.CustomerBuilder()
					.username(this.usernameProperty().get())
					.password(this.getPassword())
					.firstName(this.firstNameProperty().get())
					.lastName(this.lastNameProperty().get())
					.emailAddress(this.getEmailAddress())
					.company(this.getCompany())
					.build();
		} catch (InformationRequiredException e) {
			e.printStackTrace();
		}
		return cloned;
	}

}
