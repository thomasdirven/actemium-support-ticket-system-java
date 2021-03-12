package gui.viewModels;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

import domain.ActemiumContract;
import domain.Contract;
import domain.enums.ContractStatus;
import domain.facades.ContractFacade;
import gui.GUIEnum;
import javafx.collections.ObservableList;

public class ContractViewModel extends ViewModel {

    private GUIEnum currentState;
    private Contract selectedContract;
    private final ContractFacade contractFacade;

    public ContractViewModel(ContractFacade contractFacade) {
        super();
        this.contractFacade = contractFacade;
        setCurrentState(GUIEnum.CONTRACT);
    }

    public ObservableList<Contract> giveContracts() {
        return contractFacade.giveActemiumContracts();
    }

    public Contract getSelectedContracts() {
        return selectedContract;
    }

    public void setSelectedContract(Contract contract) {
        this.selectedContract = contract;
        if (contract != null){
            // substring(8) to remove ACTEMIUM
            setCurrentState(GUIEnum.valueOf(contract.getClass().getSimpleName().substring(8).toUpperCase()));
        }
        fireInvalidationEvent();
    }

	public ArrayList<String> getDetailsNewContract() {
		return new ArrayList<String>(Arrays.asList("Customer ID", "Contract Type Name", "Start date", "Duration (in years)"));
	}

    public Map<String, Map<Boolean, Object>> getDetails() {
        Contract contract = selectedContract;
        Map<String,Map<Boolean, Object>> details = new LinkedHashMap<>();
        details.put("Contract Nr", Collections.singletonMap(false, contract.getContractNrString()));
        details.put("Company", Collections.singletonMap(false, contract.giveCustomer().giveCompany().getName()));
        details.put("Type", Collections.singletonMap(false, contract.giveContractType().getName()));
        details.put("Status", Collections.singletonMap(true, contract.getStatusAsEnum()));
        details.put("Start date", Collections.singletonMap(false, contract.getStartDate().toString()));
        details.put("End date", Collections.singletonMap(false, contract.getEndDate().toString()));

        return details;
    }

    public void modifyContract(ContractStatus status) {
        contractFacade.modifyContract((ActemiumContract) selectedContract, status);
    }
    
    public void registerContract(Long customer, String contractTypeName, LocalDate startDate, int duration) {    	
    	contractFacade.registerContract(customer, contractTypeName, startDate, duration);
    	setSelectedContract(contractFacade.getLastAddedContract());
    }

    public GUIEnum getCurrentState() {
        return currentState;
    }

    public void setCurrentState(GUIEnum currentState) {
        this.currentState = currentState;
    }
    
    public String getNrSelectedContract() {
        return selectedContract.getContractNrString();
    }

	@Override
	public void delete() {
		// Unnecessary for this class but has to be implemented
	}

}
