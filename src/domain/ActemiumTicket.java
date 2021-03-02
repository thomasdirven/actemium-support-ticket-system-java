package domain;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

@Entity
@Access(AccessType.FIELD)
public class ActemiumTicket implements Serializable {
	@Serial
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int ticketId;

	@Enumerated(EnumType.STRING)
	private TicketStatus ticketStatus;
	@Enumerated(EnumType.STRING)
	private TicketPriority ticketPriority;
	private LocalDate dateOfCreation;
	private String title;
	private String description;
	@ManyToOne
	private Customer customer;
	private String remarks;
	private String attachments;	
	// List of technicians contain all the technicians assigned to the ticket
	@ManyToMany
	private List<Employee> technicians = new ArrayList<>();
	
	// Following list of additional data would prove usefull 
	// for further follow up on tickets and reporting:
	// - How was the solution acquired: through the KB or not. 
	//   This allows us to check if the KB must be 
	//   modified and expanded or not
	private String solution;
	// - Quality: the information was sufficient or not. 
	//   This would allow us to decided wether or not additional
	//   (mandatorry) fields are required when creating a ticket
	private String quality;
	// - Support needed: was the ticket created correctly? 
	//   E.g. a ticket was created but the wrong  
	//   department was assigned
	private String supportNeeded;
	
	public ActemiumTicket() {
		super();
	}

	public ActemiumTicket(TicketPriority ticketPriority, String title, String description, Customer customer,
			String remarks, String attachments) {
		super();
		// new ticket always gets TicketStatus CREATED
		setTicketStatus(TicketStatus.CREATED);
		setTicketPriority(ticketPriority);
		setDateOfCreation(LocalDate.now());
		setTitle(title);
		setDescription(description);
		setCustomer(customer);
		setRemarks(remarks);
		setAttachments(attachments);
	}

	// remarks and attachments are optional
	public ActemiumTicket(TicketPriority ticketPriority, String title, String description, Customer customer) {
		this(ticketPriority, title, description, customer, null, null);
	}

	public TicketStatus getTicketStatus() {
		return ticketStatus;
	}

	public void setTicketStatus(TicketStatus ticketStatus) {
		this.ticketStatus = ticketStatus;
	}

	public TicketPriority getTicketPriority() {
		return ticketPriority;
	}

	public void setTicketPriority(TicketPriority ticketPriority) {
		this.ticketPriority = ticketPriority;
	}

	public LocalDate getDateOfCreation() {
		return dateOfCreation;
	}

	public void setDateOfCreation(LocalDate dateOfCreation) {
		this.dateOfCreation = dateOfCreation;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		if (title == null || title.isBlank()) {
			throw new IllegalArgumentException("You must provide a title for the ticket.");
		}
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		if (description == null || description.isBlank()) {
			throw new IllegalArgumentException("You must provide a description for the ticket.");
		}
		this.description = description;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		if (customer == null) {
			throw new IllegalArgumentException("Ticket must belong to a customer.");
		}
		this.customer = customer;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		// Optional, can be null or blank
		this.remarks = remarks;
	}

	public String getAttachments() {
		return attachments;
	}

	public void setAttachments(String attachments) {
		// Optional, can be null or blank
		this.attachments = attachments;
	}
	
	public List<Employee> getTechnicians() {
		return technicians;
	}

	public void setTechnicians(List<Employee> technicians) {
		this.technicians = technicians;
	}
	
	public void addTechnician(Employee technician) {
		technicians.add(technician);
	}

	public String getSolution() {
		return solution;
	}

	public void setSolution(String solution) {
		this.solution = solution;
	}

	public String getQuality() {
		return quality;
	}

	public void setQuality(String quality) {
		this.quality = quality;
	}

	public String getSupportNeeded() {
		return supportNeeded;
	}

	public void setSupportNeeded(String supportNeeded) {
		this.supportNeeded = supportNeeded;
	}
	
}
