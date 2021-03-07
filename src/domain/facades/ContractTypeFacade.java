package domain.facades;

import domain.*;
import domain.enums.*;
import domain.manager.Actemium;
import javafx.collections.ObservableList;

import java.util.List;

public class ContractTypeFacade implements Facade{

    private Actemium actemium;

    public ContractTypeFacade(Actemium actemium) {
        this.actemium = actemium;
    }

    public void registerContractType(String name, ContractTypeStatus contractTypeStatus, boolean hasEmail, boolean hasPhone,
                                     boolean hasApplication, Timestamp timestamp, int maxHandlingTime, int minThroughputTime, double price) {
        ActemiumContractType contractType = new ActemiumContractType(name, contractTypeStatus, hasEmail, hasPhone, hasApplication, timestamp, maxHandlingTime, minThroughputTime, price);
        actemium.registerContractType(contractType);
    }

    public void modifyContractType(ActemiumContractType contractType, String name, ContractTypeStatus contractTypeStatus, boolean hasEmail, boolean hasPhone,
                                   boolean hasApplication, Timestamp timestamp, int maxHandlingTime, int minThroughputTime, double price) {

        contractType.setName(name);
        contractType.setContractTypeStatus(contractTypeStatus);
        contractType.setHasEmail(hasEmail);
        contractType.setHasPhone(hasPhone);
        contractType.setHasApplication(hasApplication);
        contractType.setTimestamp(timestamp);
        contractType.setMaxHandlingTime(maxHandlingTime);
        contractType.setMinThroughputTime(minThroughputTime);
        contractType.setPrice(price);

        System.out.println("All setters are ok.");
        actemium.modifyContractType(contractType);
    }

    public ObservableList<ContractType> giveActemiumContractTypes() {
        return actemium.giveActemiumContractTypes();
    }

    public ContractType getLastAddedContractType() {
        return actemium.getLastAddedContractType();
    }


}