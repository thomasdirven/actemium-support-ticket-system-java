package gui.viewModels;

import domain.Employee;
import domain.enums.TicketStatus;
import domain.enums.TicketType;
import domain.facades.ContractFacade;
import domain.facades.ContractTypeFacade;
import domain.facades.TicketFacade;
import domain.facades.UserFacade;

import java.time.LocalDate;
import java.util.*;

public class ChartViewModel{

    private final UserFacade userFacade;
    private final TicketFacade ticketFacade;
    private final ContractTypeFacade contractTypeFacade;
    private final ContractFacade contractFacade;

    public ChartViewModel(TicketFacade facade, UserFacade userFacade, ContractTypeFacade contractTypeFacade, ContractFacade contractFacade) {
        this.userFacade = userFacade;
        this.ticketFacade = facade;
        this.contractTypeFacade = contractTypeFacade;
        this.contractFacade = contractFacade;
    }

    public Map<String, Integer> barChartMonthlyTickets() {
        Map<String, Integer> mapMonthly = new HashMap<>();
        List<String> months = Arrays.asList("January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December");

        months.forEach(m -> mapMonthly.put(m, 0));

        ticketFacade.giveActemiumTickets()
                .stream()
                .filter(t -> t.getDateAndTimeOfCreation().getYear() == LocalDate.now().getYear())
                .forEach(t -> {
                    String month = t.getDateAndTimeOfCreation().getMonth().toString().charAt(0) + t.getDateAndTimeOfCreation().getMonth().toString().substring(1).toLowerCase();
                    mapMonthly.put(month, mapMonthly.get(month) + 1);
                });

        return mapMonthly;
    }

    public Map<String, Integer> barChartTicketType() {
        Map<String, Integer> mapTicketType = new HashMap<>();

        for (TicketType type : TicketType.values()) {
            mapTicketType.put(type.toString(), 0);
        }

        ticketFacade.giveActemiumTickets()
                .forEach(t -> mapTicketType.put(t.getTicketTypeAsString(), mapTicketType.get(t.getTicketTypeAsString()) + 1));

        return mapTicketType;
    }

    public Map<String, Integer> barChartDataAmountOfTicketsPerStatus() {
        Map<String, Integer> mapStatus = new HashMap<>();

        for (TicketStatus status : TicketStatus.values()) {
            mapStatus.put(status.toString(), 0);
        }

        ticketFacade.giveActemiumTickets()
                .forEach(t -> mapStatus.put(t.getStatusAsString(), mapStatus.get(t.getStatusAsString()) + 1));

        return mapStatus;
    }

    public Map<Long, Integer> barChartDataAmountOfResolvedTicketsAsignedToTechnician() {
        Map<Long, Integer> mapTechniciansResolved = new HashMap<>();

        //Technician is unique by its UserID
        ticketFacade.getAllTechnicians()
                .forEach(tech -> mapTechniciansResolved.put(tech.getUserId(), 0));

        ticketFacade.giveActemiumTicketsResolved()
                .forEach(ticket -> ticket.giveTechnicians()
                        .forEach(tech -> mapTechniciansResolved.put(tech.getUserId(), mapTechniciansResolved.get(tech.getUserId()) + 1)));

        return mapTechniciansResolved;
    }

    public Map<Long, Integer> barChartDataAmountOfOutstandingTicketsAsignedToTechnician() {
        Map<Long, Integer> mapTechniciansOutstanding = new HashMap<>();

        //Technician is unique by its UserID
        ticketFacade.getAllTechnicians()
                .forEach(tech -> mapTechniciansOutstanding.put(tech.getUserId(), 0));

        ticketFacade.giveActemiumTicketsOutstanding()
                .forEach(ticket -> ticket.giveTechnicians()
                        .forEach(tech -> mapTechniciansOutstanding.put(tech.getUserId(), mapTechniciansOutstanding.get(tech.getUserId()) + 1)));

        return mapTechniciansOutstanding;
    }

    public String getnameOftechnicianByID(long id) {
        return userFacade.getNameByID(id);
    }

    public Map<String, Integer> barChartAmountOfContractsPerType() {
        Map<String, Integer> mapContractTypes = new HashMap<>();

        contractTypeFacade.giveActemiumContractTypes()
                .forEach(type -> mapContractTypes.put(type.getName(), 0));

        contractFacade.giveActemiumContracts()
                .forEach(c -> mapContractTypes.put(c.giveContractType().getName(), mapContractTypes.get(c.giveContractType().getName()) + 1));

        return mapContractTypes;
    }

    public Map<String, Integer> barChartMonthlyContracts() {
        Map<String, Integer> mapMonthlyContracts = new HashMap<>();
        List<String> months = Arrays.asList("January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December");

        months.forEach(m -> mapMonthlyContracts.put(m, 0));

        contractFacade.giveActemiumContracts()
                .stream()
                .filter(t -> t.getStartDate().getYear() == LocalDate.now().getYear())
                .forEach(t -> {
                    String month = t.getStartDate().getMonth().toString().charAt(0) + t.getStartDate().getMonth().toString().substring(1).toLowerCase();
                    mapMonthlyContracts.put(month, mapMonthlyContracts.get(month) + 1);
                });

        return mapMonthlyContracts;
    }

}