package gui.viewModels;

import java.time.format.DateTimeFormatter;
import java.util.*;

import domain.ActemiumEmployee;
import domain.ActemiumTicket;
import domain.Employee;
import domain.Ticket;
import domain.enums.TicketPriority;
import domain.enums.TicketStatus;
import domain.enums.TicketType;
import domain.facades.TicketFacade;
import exceptions.InformationRequiredException;
import gui.GUIEnum;
import javafx.collections.ObservableList;

public class TicketViewModel extends ViewModel {

    private GUIEnum currentState;
    private Ticket selectedTicket;
    //private ObservableList<Employee> techniciansForTicket;
    private final TicketFacade ticketFacade;
    //private final UserFacade userFacade;

    private List<ActemiumEmployee> techniciansAsignedToTicket = new ArrayList<>();

    public TicketViewModel(TicketFacade ticketFacade) {
        super();
        this.ticketFacade = ticketFacade;
        //his.userFacade = userFacade;
        setCurrentState(GUIEnum.TICKET);
    }

    public ObservableList<Ticket> giveTickets() {
        return ticketFacade.giveActemiumTickets();
    }

    public ObservableList<Ticket> giveTicketsOutstanding() {
        return ticketFacade.giveActemiumTicketsOutstanding();
    }

    public ObservableList<Ticket> giveTicketsResolved() {
        return ticketFacade.giveActemiumTicketsResolved();
    }

    public Ticket getSelectedTicket() {
        return selectedTicket;
    }

    public void setSelectedTicket(Ticket ticket) {
        this.selectedTicket = ticket;
        if (ticket != null){
            // substring(8) to remove ACTEMIUM
            setCurrentState(GUIEnum.valueOf(ticket.getClass().getSimpleName().substring(8).toUpperCase()));
            setTechniciansAsignedToTicketEmpty();
            //techniciansForTicket = ticket.giveTechnicians();
        }
        fireInvalidationEvent();
    }

    public ArrayList<String> getDetailsNewTicket(){
        return new ArrayList<String>(Arrays.asList("Title", "Creation date", "Priority", "Type", "Customer ID", "Description", "Remarks", "Attachments"));
    }

    public Map<String, Map<Boolean, Object>> getDetailsOutstanding() {
        Ticket ticket = selectedTicket;
        Map<String, Map<Boolean, Object>> details = new LinkedHashMap<>();
        details.put("Title", Collections.singletonMap(true, ticket.getTitle()));
        details.put("Creation date", Collections.singletonMap(false, ticket.getDateOfCreation().format(DateTimeFormatter.ISO_DATE)));
        details.put("Creation time", Collections.singletonMap(false, ticket.getDateAndTimeOfCreation().format(DateTimeFormatter.ISO_TIME)));
        if (!TicketStatus.isOutstanding()) {
            details.put("Completion date", Collections.singletonMap(false, ticket.getDateAndTimeOfCreation().format(DateTimeFormatter.ISO_DATE)));
            details.put("Completion time", Collections.singletonMap(false, ticket.getDateAndTimeOfCreation().format(DateTimeFormatter.ISO_TIME)));
        }
        details.put("Priority", Collections.singletonMap(true, ticket.getPriorityAsEnum()));
        details.put("Type", Collections.singletonMap(true, ticket.getTicketTypeAsEnum()));
        details.put("Status", Collections.singletonMap(true, ticket.getStatusAsEnum()));
        details.put("Description", Collections.singletonMap(true, ticket.getDescription()));
        details.put("Customer/Company", Collections.singletonMap(false, ticket.giveCustomer().giveCompany().getName()));
        details.put("Technicians", Collections.singletonMap(true, ticket.giveTechnicians()));
        details.put("Remarks", Collections.singletonMap(true, ticket.getRemarks()));
        details.put("Attachments", Collections.singletonMap(true, ticket.getAttachments()));
        return details;
    }

    public Map<String, Map<Boolean, Object>> getDetailsResolved() {
        Ticket ticket = selectedTicket;
        Map<String, Map<Boolean, Object>> details = new LinkedHashMap<>();
        details.put("Title", Collections.singletonMap(false, ticket.getTitle()));
        details.put("Creation date", Collections.singletonMap(false, ticket.getDateOfCreation().format(DateTimeFormatter.ISO_DATE)));
        details.put("Creation time", Collections.singletonMap(false, ticket.getDateAndTimeOfCreation().format(DateTimeFormatter.ISO_TIME)));
        if (!TicketStatus.isOutstanding()) {
            details.put("Completion date", Collections.singletonMap(false, ticket.getDateAndTimeOfCreation().format(DateTimeFormatter.ISO_DATE)));
            details.put("Completion time", Collections.singletonMap(false, ticket.getDateAndTimeOfCreation().format(DateTimeFormatter.ISO_TIME)));
        }
        details.put("Priority", Collections.singletonMap(false, ticket.getPriorityAsEnum()));
        details.put("Type", Collections.singletonMap(false, ticket.getTicketTypeAsEnum()));
        details.put("Status", Collections.singletonMap(false, ticket.getStatusAsEnum()));
        details.put("Description", Collections.singletonMap(false, ticket.getDescription()));
        details.put("Customer/Company", Collections.singletonMap(false, ticket.giveCustomer().giveCompany().getName()));
        details.put("Technicians", Collections.singletonMap(false, ticket.giveTechnicians()));
        details.put("Remarks", Collections.singletonMap(false, ticket.getRemarks()));
        details.put("Attachments", Collections.singletonMap(false, ticket.getAttachments()));
        //TODO only the fields below should be editable in resloved tickets
        // this means all the above fields should be set to false, ...
        // How will we do this without using one big if/else block?
        details.put("Solution", Collections.singletonMap(true, ticket.getSolution()));
        details.put("Quality", Collections.singletonMap(true, ticket.getQuality()));
        details.put("Support Needed", Collections.singletonMap(true, ticket.getSupportNeeded()));
        return details;
    }

    public String getIdSelectedTicket() {
        return selectedTicket.getTicketIdString();
    }

    public void registerTicket(TicketPriority priority, TicketType ticketType, String title, String description,
                               String remarks, String attachments, long customerId) throws InformationRequiredException {
        ticketFacade.registerTicket(priority, ticketType, title, description, remarks, attachments, customerId);
        setSelectedTicket(ticketFacade.getLastAddedTicket());
    }

    // TODO
    // Cannot modify customer of the ticket, needs to be unmodifiable field
    public void modifyTicketOutstanding(TicketPriority priority, TicketType ticketType, TicketStatus status, String title, String description,
                                     String remarks, String attachments, List<ActemiumEmployee> technicians) throws InformationRequiredException {
        ticketFacade.modifyTicket((ActemiumTicket) selectedTicket, priority, ticketType, status, title, description, remarks, attachments, technicians);
    }

    public void modifyTicketResolved(String solution, String quality, String supportNeeded) throws InformationRequiredException {
        ticketFacade.modifyTicketOutstanding((ActemiumTicket) selectedTicket, solution, quality, supportNeeded);
    }

    public GUIEnum getCurrentState() {
        return currentState;
    }

    public void setCurrentState(GUIEnum currentState) {
        this.currentState = currentState;
    }

    @Override
    public void delete() throws InformationRequiredException {
        ticketFacade.delete((ActemiumTicket) selectedTicket);
    }

    //TODO shouldn't be in here but in userViewModel
    public ObservableList<Employee> getAllTechnicians() {
        return ticketFacade.getAllTechnicians();
    }

    public void addTechnicianToTicket(Employee tech){
        if(!techniciansAsignedToTicket.contains(tech))
            techniciansAsignedToTicket.add((ActemiumEmployee) tech);
    }

    public void removeTechnician(Employee tech) {
        techniciansAsignedToTicket.remove(tech);
    }

    public List<ActemiumEmployee> getTechniciansAsignedToTicket() {
        return techniciansAsignedToTicket;
    }

    public void setTechniciansAsignedToTicketEmpty() {
        this.techniciansAsignedToTicket = new ArrayList<>();
    }
}