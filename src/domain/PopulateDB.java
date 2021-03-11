package domain;

import domain.enums.*;
import repository.GenericDao;
import repository.UserDaoJpa;

import java.security.SecureRandom;
import java.time.LocalDate;
import java.util.ArrayList;

public class PopulateDB {
    public void run(UserDaoJpa userDao, GenericDao<ActemiumContractType> contractTypeDao) {
    	System.out.println("DBTester - creating and persisting multiple user objects");        
        userDao.startTransaction();
        
        ActemiumCompany theWhiteHouse = new ActemiumCompany("The White House", "America 420", "911");
        ActemiumCustomer barak = new ActemiumCustomer("cust01Barak", "Passwd123&", "Barak", "Obama", theWhiteHouse);
        ActemiumContractType bCtype = new ActemiumContractType("BasicEmailSupport", ContractTypeStatus.ACTIVE, true, false, false, Timestamp.WORKINGHOURS, 5, 2, 999.99);
        ActemiumContract bContract = new ActemiumContract(bCtype, barak, LocalDate.now().plusYears(1));
        barak.addContract(bContract);
        ActemiumCustomer barak2 = new ActemiumCustomer("cust02Barak", "Passwd123&", "Barak", "Obama", theWhiteHouse);
        ActemiumCustomer barak3 = new ActemiumCustomer("cust03Barak", "Passwd123&", "Barak", "Obama", theWhiteHouse);
        ActemiumCustomer barak4 = new ActemiumCustomer("cust04Barak", "Passwd123&", "Barak", "Obama", theWhiteHouse);
        ActemiumCustomer barak5 = new ActemiumCustomer("cust05Barak", "Passwd123&", "Barak", "Obama", theWhiteHouse);
        barak4.setStatus(UserStatus.BLOCKED);
        barak5.setStatus(UserStatus.INACTIVE);
        ActemiumEmployee tech = new ActemiumEmployee("Technician", "Passwd123&", "Johan", "Van Schoor","Overwale 42","091354864","thomas.dirven@hogent.be", EmployeeRole.TECHNICIAN);
        ActemiumEmployee tech2 = new ActemiumEmployee("Technician", "Passwd123&", "Donald", "Trump","Overwale 42","091354864","thomas.dirven@hogent.be", EmployeeRole.TECHNICIAN);

        userDao.insert(tech);
        userDao.insert(tech2);
        ActemiumTicket ticket01 = new ActemiumTicket(TicketPriority.P1,TicketType.DATABASE, "Ticket1", "Lorem ipsum dolor sit amet, consectetur adipiscing elit.", barak);
        ActemiumTicket ticket02 = new ActemiumTicket(TicketPriority.P1, TicketType.HARDWARE,"Ticket2", "Lorem ipsum dolor sit amet, consectetur adipiscing elit.", barak);
        ActemiumTicket ticket03 = new ActemiumTicket(TicketPriority.P1, TicketType.INFRASTRUCTURE, "Ticket3", "Lorem ipsum dolor sit amet, consectetur adipiscing elit.", barak);
        ActemiumTicket ticket04 = new ActemiumTicket(TicketPriority.P1, TicketType.NETWORK, "Ticket4", "Lorem ipsum dolor sit amet, consectetur adipiscing elit.", barak);
        ActemiumTicket ticket05 = new ActemiumTicket(TicketPriority.P1, TicketType.SOFTWARE, "Ticket5", "Lorem ipsum dolor sit amet, consectetur adipiscing elit.", barak);
        ticket05.setStatus(TicketStatus.COMPLETED);
        barak.addTicket(ticket01);
        barak2.addTicket(ticket02);
        barak3.addTicket(ticket03);
        barak4.addTicket(ticket04);
        barak5.addTicket(ticket05);
        ticket01.addTechnician(tech);
        ticket02.addTechnician(tech);
        ticket03.addTechnician(tech);
        ticket04.addTechnician(tech2);
        ticket05.addTechnician(tech2);
        userDao.insert(new ActemiumEmployee("Admin123", "Passwd123&", "Johan", "Van Schoor","Overwale 42","091354864","thomas.dirven@hogent.be", EmployeeRole.ADMINISTRATOR));
        userDao.insert(new ActemiumEmployee("Sup123", "Passwd123&", "Johan", "Van Schoor","Overwale 42","091354864","thomas.dirven@hogent.be", EmployeeRole.SUPPORT_MANAGER));
        userDao.insert(new ActemiumEmployee("Tech123", "Passwd123&", "Johan", "Van Schoor","Overwale 42","091354864","thomas.dirven@hogent.be", EmployeeRole.TECHNICIAN));
        userDao.insert(new ActemiumEmployee("thomas123", "Passwd123&", "Thomas", "Dirven","Overwale 42","091354864","thomas.dirven@hogent.be", EmployeeRole.ADMINISTRATOR));
        userDao.insert(new ActemiumEmployee("isaac123", "Passwd123&", "Isaac", "Bauters","Kerstraat 71","094812384","isaac.bauters@hogent.be", EmployeeRole.ADMINISTRATOR));
        userDao.insert(new ActemiumEmployee("florian123", "Passwd123&", "Florian", "Goossens","Groensstraat 103","096248753","florian.goossens@hogent.be", EmployeeRole.ADMINISTRATOR));
        userDao.insert(new ActemiumEmployee("sibianDG", "Passwd123&", "Sibian", "De Gussem","Hoogstraat 89","092564812","sibian.degussem@hogent.be", EmployeeRole.ADMINISTRATOR));
        ActemiumEmployee badguy = new ActemiumEmployee("badGuy", "Passwd123&", "bad", "guy","Hoogstraat 89","092564812","bad.guy@hogent.be", EmployeeRole.SUPPORT_MANAGER);
        ActemiumEmployee inactive = new ActemiumEmployee("fired", "Passwd123&", "bad", "guy","Hoogstraat 89","092564812","bad.guy@hogent.be", EmployeeRole.SUPPORT_MANAGER);
        badguy.setStatus(UserStatus.BLOCKED);
        inactive.setStatus(UserStatus.INACTIVE);
        userDao.insert(badguy);
        userDao.insert(inactive);
        userDao.insert(barak);
        userDao.insert(barak2);
        userDao.insert(barak3);
        userDao.insert(barak4);
        userDao.insert(barak5);
        userDao.insert(new ActemiumCustomer("cust02Johan", "Passwd123&", "Johan", "Vercauteren", theWhiteHouse));
        userDao.insert(new ActemiumCustomer("cust03Emma", "Passwd123&", "Emma", "Dupont", theWhiteHouse));
        userDao.insert(new ActemiumEmployee("tech01Donald", "Passwd123&", "Donald", "Trump", "Stationstraat 56", "092548736", "donald.trump@hogent.be", EmployeeRole.TECHNICIAN));
        userDao.insert(new ActemiumEmployee("supman01John", "Passwd123&", "John", "Smiths", "Stationstraat 34", "093504816", "john.smiths@hogent.be", EmployeeRole.SUPPORT_MANAGER));

        ActemiumCompany vatican = new ActemiumCompany("Vatican", "00120 Vatican City", "666");
        ActemiumCustomer pope = new ActemiumCustomer("PopeFrancis", "Passwd123&", "Jorge Mario", "Bergoglio", vatican);
        SecureRandom randomGen = new SecureRandom();
        TicketPriority[] prios = TicketPriority.values();
        TicketType[] types = TicketType.values() ;
        
        TicketStatus[] status = TicketStatus.values();
        
        for (int i = 0; i < 20; i++) {
            ActemiumTicket t = new ActemiumTicket(prios[randomGen.nextInt(3)], types[randomGen.nextInt(types.length)], "Title"+i, "Description"+i, pope, "Remark"+i, "screenshot"+i+".png");
            t.setStatus(status[randomGen.nextInt(status.length)]);
            pope.addTicket(t);
        }
        userDao.insert(pope);
        userDao.commitTransaction();

        contractTypeDao.startTransaction();
        contractTypeDao.insert(new ActemiumContractType("BasisEmailSupport", ContractTypeStatus.ACTIVE, true, false, false, Timestamp.WORKINGHOURS, 5, 2, 999.99));
        contractTypeDao.insert(new ActemiumContractType("BasisPhoneSupport", ContractTypeStatus.ACTIVE, false, true, false, Timestamp.WORKINGHOURS, 5, 2, 1999.99));
        contractTypeDao.insert(new ActemiumContractType("BasisAppSupport", ContractTypeStatus.ACTIVE, false, false, true, Timestamp.WORKINGHOURS, 5, 2, 1299.99));
        contractTypeDao.insert(new ActemiumContractType("FullEmailSupport", ContractTypeStatus.ACTIVE, true, false, false, Timestamp.ALWAYS, 4, 1, 1999.99));
        contractTypeDao.insert(new ActemiumContractType("FullPhoneSupport", ContractTypeStatus.ACTIVE, false, true, false, Timestamp.ALWAYS, 4, 1, 2999.99));
        contractTypeDao.insert(new ActemiumContractType("FullAppSupport", ContractTypeStatus.ACTIVE, false, false, true, Timestamp.ALWAYS, 4, 1, 2299.99));
        contractTypeDao.insert(new ActemiumContractType("BasisAllSupport", ContractTypeStatus.ACTIVE, true, true, true, Timestamp.WORKINGHOURS, 4, 2, 2999.99));
        contractTypeDao.insert(new ActemiumContractType("FullAllSupport", ContractTypeStatus.ACTIVE, true, true, true, Timestamp.ALWAYS, 3, 1, 3999.99));
        contractTypeDao.insert(new ActemiumContractType("ExperimentalSupport", ContractTypeStatus.INACTIVE, true, false, true, Timestamp.ALWAYS, 4, 1, 2999.99));
		contractTypeDao.commitTransaction();

    }

}
