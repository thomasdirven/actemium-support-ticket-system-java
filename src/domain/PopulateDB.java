package domain;

import java.security.SecureRandom;
import java.time.LocalDate;
import java.time.LocalDateTime;

import domain.enums.ContractTypeStatus;
import domain.enums.EmployeeRole;
import domain.enums.KbItemType;
import domain.enums.TicketPriority;
import domain.enums.TicketStatus;
import domain.enums.TicketType;
import domain.enums.Timestamp;
import domain.enums.UserStatus;
import exceptions.InformationRequiredException;
import languages.LanguageResource;
import repository.GenericDao;
import repository.UserDaoJpa;

public class PopulateDB {
    public void run(UserDaoJpa userDao, GenericDao<ActemiumContractType> contractTypeDao,
    		GenericDao<ActemiumKbItem> kbItemDao) throws InformationRequiredException {
    	
    	System.out.println("DBTester - creating and persisting multiple user objects");
    	
        userDao.startTransaction();

        ActemiumCompany theWhiteHouse = new ActemiumCompany.CompanyBuilder()
                .name("The White House")
                .country("United States")
                .city("Washington, D.C. 20500")
                .address("1600 Pennsylvania Avenue NW")
                .phoneNumber("+1-202-456-1111")
                .build();

        ActemiumCompany naessensNV = new ActemiumCompany.CompanyBuilder()
                .name("Construct Willy Naessens")
                .country("Belgium")
                .city("9700 Oudenaarde")
                .address("Bedrijvenpark Coupure 15")
                .phoneNumber("055 61 98 19")
                .build();

        ActemiumCompany google = new ActemiumCompany.CompanyBuilder()
                .name("Google")
                .country("United States")
                .city("Mountain View, CA 94043")
                .address("1600 Amphitheatre Parkway")
                .phoneNumber("+1-650-253-0000")
                .build();

        ActemiumCompany microsoft = new ActemiumCompany.CompanyBuilder()
                .name("Microsoft")
                .country("United States")
                .city("Redmond, WA 98052")
                .address("1 Microsoft Way")
                .phoneNumber("+1-425-882-8080")
                .build();

        ActemiumCompany facebook = new ActemiumCompany.CompanyBuilder()
                .name("Facebook")
                .country("United States")
                .city("Menlo Park, CA 94025")
                .address("1 Hacker Way")
                .phoneNumber("+1-650-308-7300")
                .build();
        
        ActemiumCompany amazon = new ActemiumCompany.CompanyBuilder()
                .name("Amazon")
                .country("United States")
                .city("Seattle, WA 98109-5210")
                .address("410 Terry Avenue North")
                .phoneNumber("+1-206-266-1000")
                .build();
        
        ActemiumCompany tesla = new ActemiumCompany.CompanyBuilder()
                .name("Tesla")
                .country("United States")
                .city("Palo Alto, CA 94304")
                .address("3500 Deer Creek Road")
                .phoneNumber("+31 20 365 0008")
                .build();

        ActemiumContractType bCtype = new ActemiumContractType.ContractTypeBuilder()
                .name("BasicSupport")
                .status(ContractTypeStatus.ACTIVE)
                .hasEmail(true)
                .hasPhone(false)
                .hasApplication(false)
                .timestamp(Timestamp.WORKINGHOURS)
                .maxHandlingTime(5)
                .minThroughputTime(2)
                .price(999.99)
                .build();

        ActemiumContractType bCtype02 = new ActemiumContractType.ContractTypeBuilder()
                .name("BasicEmailSupport")
                .status(ContractTypeStatus.ACTIVE)
                .hasEmail(true)
                .hasPhone(false)
                .hasApplication(false)
                .timestamp(Timestamp.WORKINGHOURS)
                .maxHandlingTime(5)
                .minThroughputTime(2)
                .price(999.99)
                .build();
        
        ActemiumContractType cCtype = new ActemiumContractType.ContractTypeBuilder()
                .name("BasicAppSupport")
                .status(ContractTypeStatus.ACTIVE)
                .hasEmail(false)
                .hasPhone(false)
                .hasApplication(true)
                .timestamp(Timestamp.WORKINGHOURS)
                .maxHandlingTime(5)
                .minThroughputTime(2)
                .price(1299.99)
                .build();

        ActemiumContractType dCtype = new ActemiumContractType.ContractTypeBuilder()
                .name("FullEmailSupport")
                .status(ContractTypeStatus.ACTIVE)
                .hasEmail(true)
                .hasPhone(false)
                .hasApplication(false)
                .timestamp(Timestamp.ALWAYS)
                .maxHandlingTime(4)
                .minThroughputTime(1)
                .price(1999.99)
                .build();
        
        ActemiumContractType eCtype = new ActemiumContractType.ContractTypeBuilder()
                .name("FullPhoneSupport")
                .status(ContractTypeStatus.ACTIVE)
                .hasEmail(false)
                .hasPhone(true)
                .hasApplication(false)
                .timestamp(Timestamp.ALWAYS)
                .maxHandlingTime(4)
                .minThroughputTime(1)
                .price(2999.99)
                .build();

        ActemiumContractType fCtype = new ActemiumContractType.ContractTypeBuilder()
                .name("FullAppSupport")
                .status(ContractTypeStatus.ACTIVE)
                .hasEmail(false)
                .hasPhone(false)
                .hasApplication(true)
                .timestamp(Timestamp.ALWAYS)
                .maxHandlingTime(4)
                .minThroughputTime(1)
                .price(2299.99)
                .build();

        ActemiumContractType gCtype = new ActemiumContractType.ContractTypeBuilder()
                .name("BasicAllSupport")
                .status(ContractTypeStatus.ACTIVE)
                .hasEmail(true)
                .hasPhone(true)
                .hasApplication(true)
                .timestamp(Timestamp.WORKINGHOURS)
                .maxHandlingTime(4)
                .minThroughputTime(2)
                .price(2999.99)
                .build();

        ActemiumContractType hCtype = new ActemiumContractType.ContractTypeBuilder()
                .name("FullAllSupport")
                .status(ContractTypeStatus.ACTIVE)
                .hasEmail(true)
                .hasPhone(true)
                .hasApplication(true)
                .timestamp(Timestamp.ALWAYS)
                .maxHandlingTime(4)
                .minThroughputTime(2)
                .price(3999.99)
                .build();

        ActemiumContractType jCtype = new ActemiumContractType.ContractTypeBuilder()
                .name("AmazonSupport")
                .status(ContractTypeStatus.ACTIVE)
                .hasEmail(true)
                .hasPhone(false)
                .hasApplication(false)
                .timestamp(Timestamp.WORKINGHOURS)
                .maxHandlingTime(5)
                .minThroughputTime(2)
                .price(999.99)
                .build();

        ActemiumContractType jCtype02 = new ActemiumContractType.ContractTypeBuilder()
                .name("MonopolySupport")
                .status(ContractTypeStatus.ACTIVE)
                .hasEmail(true)
                .hasPhone(true)
                .hasApplication(true)
                .timestamp(Timestamp.ALWAYS)
                .maxHandlingTime(3)
                .minThroughputTime(1)
                .price(6969.69)
                .build();

        ActemiumContractType pCtype = new ActemiumContractType.ContractTypeBuilder()
                .name("BasicPhoneSupport")
                .status(ContractTypeStatus.ACTIVE)
                .hasEmail(false)
                .hasPhone(true)
                .hasApplication(false)
                .timestamp(Timestamp.WORKINGHOURS)
                .maxHandlingTime(5)
                .minThroughputTime(2)
                .price(1999.99)
                .build();



        ActemiumCustomer florian = new ActemiumCustomer.CustomerBuilder()
                .username("cust01Florian")
                .password("Passwd123&")
                .firstName("Florian")
                .lastName("Goossens")
                .company(theWhiteHouse)
                .build();
        ActemiumCustomer jeff = new ActemiumCustomer.CustomerBuilder()
                .username("cust02Jeff")
                .password("Passwd123&")
                .firstName("Jeff")
                .lastName("Bezos")
                .company(amazon)
                .build();
        ActemiumCustomer mark = new ActemiumCustomer.CustomerBuilder()
                .username("cust03Mark")
                .password("Passwd123&")
                .firstName("Mark")
                .lastName("Zuckerberg")
                .company(facebook)
                .build();
        ActemiumCustomer bill = new ActemiumCustomer.CustomerBuilder()
                .username("cust04Bill")
                .password("Passwd123&")
                .firstName("Bill")
                .lastName("Gates")
                .company(microsoft)
                .build();
        ActemiumCustomer larry = new ActemiumCustomer.CustomerBuilder()
                .username("cust05Larry")
                .password("Passwd123&")
                .firstName("Larry")
                .lastName("Page")
                .company(google)
                .build();
        ActemiumCustomer elon = new ActemiumCustomer.CustomerBuilder()
                .username("cust06Elon")
                .password("Passwd123&")
                .firstName("Elon")
                .lastName("Musk")
                .company(tesla)
                .build();

        ActemiumContract bContract = new ActemiumContract.ContractBuilder()
                                                            .contractType(bCtype)
                                                            .company(florian.getCompany())
                                                            .endDate(LocalDate.now().plusYears(1))
                                                            .build();
        ActemiumContract bContract02 = new ActemiumContract.ContractBuilder()
                .contractType(bCtype02)
                .company(florian.getCompany())
                .endDate(LocalDate.now().plusYears(1))
                .build();
        ActemiumContract jContract = new ActemiumContract.ContractBuilder()
                .contractType(jCtype)
                .company(jeff.getCompany())
                .endDate(LocalDate.now().plusYears(3))
                .build();
        ActemiumContract jContract02 = new ActemiumContract.ContractBuilder()
                .contractType(jCtype02)
                .company(jeff.getCompany())
                .endDate(LocalDate.now().plusYears(3))
                .build();

        ActemiumContract mContract = new ActemiumContract.ContractBuilder()
                .contractType(cCtype)
                .company(mark.getCompany())
                .endDate(LocalDate.now().plusYears(1))
                .build();
        ActemiumContract mContract02 = new ActemiumContract.ContractBuilder()
                .contractType(gCtype)
                .company(bill.getCompany())
                .endDate(LocalDate.now().plusYears(1))
                .build();
        ActemiumContract gContract = new ActemiumContract.ContractBuilder()
                .contractType(dCtype)
                .company(bill.getCompany())
                .endDate(LocalDate.now().plusYears(1))
                .build();

        ActemiumContract gContract02 = new ActemiumContract.ContractBuilder()
                .contractType(dCtype)
                .company(bill.getCompany())
                .endDate(LocalDate.now().plusYears(1))
                .build();
        ActemiumContract lContract = new ActemiumContract.ContractBuilder()
                .contractType(eCtype)
                .company(larry.getCompany())
                .endDate(LocalDate.now().plusYears(3))
                .build();
        ActemiumContract lContract02 = new ActemiumContract.ContractBuilder()
                .contractType(eCtype)
                .company(larry.getCompany())
                .endDate(LocalDate.now().plusYears(3))
                .build();

        ActemiumContract eContract = new ActemiumContract.ContractBuilder()
                .contractType(fCtype)
                .company(elon.getCompany())
                .endDate(LocalDate.now().plusYears(2))
                .build();
        ActemiumContract eContract02 = new ActemiumContract.ContractBuilder()
                .contractType(pCtype)
                .company(elon.getCompany())
                .endDate(LocalDate.now().plusYears(2))
                .build();

        florian.addContract(bContract);
        florian.addContract(bContract02);

        jeff.addContract(jContract);
        jeff.addContract(jContract02);

        mark.addContract(mContract);
        mark.addContract(mContract02);

        bill.addContract(gContract);
        bill.addContract(gContract02);

        larry.addContract(lContract);
        larry.addContract(lContract02);

        elon.addContract(eContract);
        elon.addContract(eContract02);

        mark.setStatus(UserStatus.BLOCKED);
        larry.setStatus(UserStatus.INACTIVE);
        ActemiumEmployee tech = new ActemiumEmployee.EmployeeBuilder()
                .username("bartvdb123")
                .password("Passwd123&")
                .firstName("Bart (HARDWARE)")
                .lastName("Vandenbosche")
                .address("Overwale 42")
                .phoneNumber("091354864")
                .emailAddress("bartvdb@hogent.be")
                .role(EmployeeRole.TECHNICIAN)
                .build();
        tech.addSpecialty(TicketType.HARDWARE);
        tech.addSpecialty(TicketType.OTHER);

        ActemiumEmployee tech2 = new ActemiumEmployee.EmployeeBuilder()
                .username("don123")
                .password("Passwd123&")
                .firstName("Don (Soft)")
                .lastName("Verstekker")
                .address("Overwale 42")
                .phoneNumber("091354864")
                .emailAddress("don.verstekker@hogent.be")
                .role(EmployeeRole.TECHNICIAN)
                .build();
        tech2.addSpecialty(TicketType.SOFTWARE);
        tech2.addSpecialty(TicketType.OTHER);


        userDao.insert(tech);
        userDao.insert(tech2);
        ActemiumTicket ticket01 = new ActemiumTicket.TicketBuilder()
                                                    .ticketPriority(TicketPriority.P1)
                                                    .ticketType(TicketType.DATABASE)
                                                    .title("Database issues")
                                                    .description("I have had some issues with my database. I'll need some help!")
                                                    .company(florian.getCompany())
                                                    .build();
        ticket01.addTicketComment(createTicketComment(ticket01, tech, String.format("(%s)", LanguageResource.getString("none"))));
        ActemiumTicket ticket02 = new ActemiumTicket.TicketBuilder()
                .ticketPriority(TicketPriority.P1)
                .ticketType(TicketType.HARDWARE)
                .title("My mouse doesn't move")
                .description("Help my mouse won't move on my screen! I don't know how to fix it.")
                .company(jeff.getCompany())
                .build();
        ticket02.addTicketComment(createTicketComment(ticket02, tech, String.format("(%s)", LanguageResource.getString("none"))));
        ActemiumTicket ticket03 = new ActemiumTicket.TicketBuilder()
                .ticketPriority(TicketPriority.P1)
                .ticketType(TicketType.INFRASTRUCTURE)
                .title("Printer stopped working")
                .description("I was printing a document when all of a sudden the printer stopped working.")
                .company(mark.getCompany())
                .build();
        ticket03.addTicketComment(createTicketComment(ticket03, tech, String.format("(%s)", LanguageResource.getString("none"))));
        ActemiumTicket ticket04 = new ActemiumTicket.TicketBuilder()
                .ticketPriority(TicketPriority.P1)
                .ticketType(TicketType.NETWORK)
                .title("Wifi issues")
                .description("I have some issues with connecting my laptop to the wifi.")
                .company(bill.getCompany())
                .build();
        ticket04.addTicketComment(createTicketComment(ticket04, tech, String.format("(%s)", LanguageResource.getString("none"))));
        ActemiumTicket ticket05 = new ActemiumTicket.TicketBuilder()
                .ticketPriority(TicketPriority.P1)
                .ticketType(TicketType.SOFTWARE)
                .title("Problems with the installation of word")
                .description("I don't know how to install word. I would like to have some help!")
                .company(larry.getCompany())
                .build();
        ticket05.addTicketComment(createTicketComment(ticket05, tech, String.format("(%s)", LanguageResource.getString("none"))));
        ActemiumTicket ticket06 = new ActemiumTicket.TicketBuilder()
                .ticketPriority(TicketPriority.P1)
                .ticketType(TicketType.SOFTWARE)
                .title("Problems with the installation of word powerpoint")
                .description("I don't know how to install powerpoint. I would like to have some help!")
                .company(elon.getCompany())
                .build();        
        ticket06.addTicketComment(createTicketComment(ticket06, tech, String.format("(%s)", LanguageResource.getString("none"))));       
        
        ticket05.setStatus(TicketStatus.COMPLETED);

        florian.addTicket(ticket01);
        jeff.addTicket(ticket02);
        mark.addTicket(ticket03);
        bill.addTicket(ticket04);
        larry.addTicket(ticket05);
        elon.addTicket(ticket06);
        
        ticket01.addTechnician(tech);
        ticket02.addTechnician(tech);
        ticket03.addTechnician(tech);
        ticket04.addTechnician(tech2);
        ticket05.addTechnician(tech2);
        ticket06.addTechnician(tech);
        ticket06.addTechnician(tech2);
        
        userDao.insert( new ActemiumEmployee.EmployeeBuilder()
                .username("Admin123")
                .password("Passwd123&")
                .firstName("Ward")
                .lastName("De Bever")
                .address("Langemunt 12")
                .phoneNumber("091354864")
                .emailAddress("ward.db@hogent.be")
                .role(EmployeeRole.ADMINISTRATOR)
                .build());
        userDao.insert( new ActemiumEmployee.EmployeeBuilder()
                .username("Sup123")
                .password("Passwd123&")
                .firstName("Thomas")
                .lastName("Dekker")
                .address("Hoekstraat 4")
                .phoneNumber("091354864")
                .emailAddress("thomas.dekker@hogent.be")
                .role(EmployeeRole.SUPPORT_MANAGER)
                .build());
        ActemiumEmployee tech3 = new ActemiumEmployee.EmployeeBuilder()
                .username("Tech123")
                .password("Passwd123&")
                .firstName("Jonas(DB)")
                .lastName("Nician")
                .address("Poortstraat 2")
                .phoneNumber("091354864")
                .emailAddress("jonas.nician@hogent.be")
                .role(EmployeeRole.TECHNICIAN)
                .build();
        tech3.addSpecialty(TicketType.DATABASE);
        tech3.addSpecialty(TicketType.OTHER);

        userDao.insert(tech3);
        userDao.insert( new ActemiumEmployee.EmployeeBuilder()
                .username("thomas123")
                .password("Passwd123&")
                .firstName("Thomas")
                .lastName("Dirven")
                .address("Overwale 42")
                .phoneNumber("091354864")
                .emailAddress("thomas.dirven@hogent.be")
                .role(EmployeeRole.ADMINISTRATOR)
                .build());
        userDao.insert( new ActemiumEmployee.EmployeeBuilder()
                .username("isaac123")
                .password("Passwd123&")
                .firstName("Isaac")
                .lastName("Bauters")
                .address("Kerstraat 71")
                .phoneNumber("091354864")
                .emailAddress("isaac.bauters@hogent.be")
                .role(EmployeeRole.ADMINISTRATOR)
                .build());
        userDao.insert( new ActemiumEmployee.EmployeeBuilder()
                .username("florian123")
                .password("Passwd123&")
                .firstName("Florian")
                .lastName("Goossens")
                .address("Groensstraat 103")
                .phoneNumber("091354864")
                .emailAddress("florian.goossens@hogent.be")
                .role(EmployeeRole.ADMINISTRATOR)
                .build());
        userDao.insert( new ActemiumEmployee.EmployeeBuilder()
                .username("SibianDG")
                .password("Passwd123&")
                .firstName("Sibian")
                .lastName("De Gussem")
                .address("Hoogstraat 89")
                .phoneNumber("091354864")
                .emailAddress("sibian.degussem@hogent.be")
                .role(EmployeeRole.ADMINISTRATOR)
                .build());

        ActemiumEmployee badguy = new ActemiumEmployee.EmployeeBuilder()
                .username("bartje123")
                .password("Passwd123&")
                .firstName("Bart")
                .lastName("Bommel")
                .address("Hoogstraat 89")
                .phoneNumber("091354864")
                .emailAddress("bart.bommel@hogent.be")
                .role(EmployeeRole.SUPPORT_MANAGER)
                .build();
        ActemiumEmployee inactive = new ActemiumEmployee.EmployeeBuilder()
                .username("tommie123")
                .password("Passwd123&")
                .firstName("Tom")
                .lastName("Timmerman")
                .address("Hoogstraat 89")
                .phoneNumber("091354864")
                .emailAddress("tom.timmerman@hogent.be")
                .role(EmployeeRole.SUPPORT_MANAGER)
                .build();
        badguy.setStatus(UserStatus.BLOCKED);
        inactive.setStatus(UserStatus.INACTIVE);
        userDao.insert(badguy);
        userDao.insert(inactive);
        userDao.insert(florian);
        userDao.insert(jeff);
        userDao.insert(mark);
        userDao.insert(bill);
        userDao.insert(larry);
        userDao.insert(elon);

        userDao.insert(new ActemiumCustomer.CustomerBuilder()
                .username("cust02Willy")
                .password("Passwd123&")
                .firstName("Willy")
                .lastName("Naessens")
                .company(naessensNV)
                .build());
        ActemiumEmployee tech4 = new ActemiumEmployee.EmployeeBuilder()
                .username("BObieTHeBuilder")
                .password("Passwd123&")
                .firstName("Bob(infra)")
                .lastName("Berkmans")
                .address("Stationstraat 56")
                .phoneNumber("092548736")
                .emailAddress("bob.berkmans@hogent.be")
                .role(EmployeeRole.TECHNICIAN)
                .build();
        tech4.addSpecialty(TicketType.INFRASTRUCTURE);
        tech4.addSpecialty(TicketType.OTHER);
        ActemiumEmployee tech5 = new ActemiumEmployee.EmployeeBuilder()
                .username("jhonnyboy")
                .password("Passwd123&")
                .firstName("Jhon (network)")
                .lastName("Opmeer")
                .address("Stationstraat 56")
                .phoneNumber("092548736")
                .emailAddress("jhon.opmeer@hogent.be")
                .role(EmployeeRole.TECHNICIAN)
                .build();
        tech5.addSpecialty(TicketType.NETWORK);
        tech5.addSpecialty(TicketType.OTHER);
        userDao.insert(tech4);
        userDao.insert(tech5);
        userDao.insert(
                new ActemiumEmployee.EmployeeBuilder()
                        .username("supman01John")
                        .password("Passwd123&")
                        .firstName("John")
                        .lastName("Smiths")
                        .address("Stationstraat 34")
                        .phoneNumber("09254875536")
                        .emailAddress("john.smiths@hogent.be")
                        .role(EmployeeRole.SUPPORT_MANAGER)
                        .build());
        ActemiumCompany vatican = new ActemiumCompany.CompanyBuilder()
                .name("Vatican")
                .country("Vatican")
                .city("Vatican City")
                .address("00120 Vatican City")
                .phoneNumber("666")
                .build();


        ActemiumCustomer pope = new ActemiumCustomer.CustomerBuilder()
                .username("PopeFrancis")
                .password("Passwd123&")
                .firstName("Jorge")
                .lastName("Bergoglio")
                .company(vatican)
                .build();
        userDao.insert(pope);

        SecureRandom randomGen = new SecureRandom();
        TicketPriority[] prios = TicketPriority.values();
        TicketType[] types = TicketType.values() ;

        TicketStatus[] status = TicketStatus.values();
        
        String[] ticketNames = new String[] {"Internet problems", "Wifi problems", "Hardware problems", "Mouse problems", "Screen problems", "Database not available", "Error occured while installing excel", "Not enough disk space", "I have a virus on my laptop", "Printing problems"};
        
        for (int i = 0; i < 10; i++) {
            ActemiumTicket t = new ActemiumTicket.TicketBuilder()
                                                    .ticketPriority(prios[randomGen.nextInt(3)])
                                                    .ticketType(types[randomGen.nextInt(types.length)])
                                                    .title(ticketNames[i])
                                                    .description(String.format("%s %d", LanguageResource.getString("description"), i))
                                                    .company(bill.getCompany())
                                                    .comments(null)
                                                    .attachments(String.format("%s%d.png", LanguageResource.getString("screenshot"), i))
                                                    .build();
            t.setStatus(status[randomGen.nextInt(status.length)]);
            t.addTicketComment(createTicketComment(t, tech, String.format("%s %d", LanguageResource.getString("remark"), i)));
            mark.addTicket(t);
        }
                
        userDao.insert(bill);

        userDao.commitTransaction();

        contractTypeDao.startTransaction();

        contractTypeDao.insert(new ActemiumContractType.ContractTypeBuilder()
                .name("ExperimentalSupport")
                .status(ContractTypeStatus.INACTIVE)
                .hasEmail(true)
                .hasPhone(true)
                .hasApplication(true)
                .timestamp(Timestamp.ALWAYS)
                .maxHandlingTime(4)
                .minThroughputTime(1)
                .price(2999.99)
                .build());        
        
		contractTypeDao.commitTransaction();
		
		kbItemDao.startTransaction();
		kbItemDao.insert(new ActemiumKbItem.KbItemBuilder()
				.title("Word installation")
				.keywords("word")
				.type(KbItemType.DATABASE)
				.text("In this article you will find out how to install microsoft word...")
				.build());
        KbItemType[] kbItemTypes = KbItemType.values();
        
        String[] articleNames = new String[] {"powerpoint", "excel", "connecting to the internet", "printing files", "installing powerpoint", "installing anti virus software", "connecting to a database", "deleting files", "saving word documents", "databases"};
        for (int i = 0; i < 10; i++) {
        	KbItemType randomKbItem = kbItemTypes[randomGen.nextInt(kbItemTypes.length)];
        	kbItemDao.insert(new ActemiumKbItem.KbItemBuilder()
                                    .title("Article about " + articleNames[i])
                                    .keywords(randomKbItem.toString())
                                    .type(randomKbItem)
                                    .text("This article is about tells you everything about " + articleNames[i] + "...")
                                    .build());
        }
		kbItemDao.commitTransaction();
		
    }

	private ActemiumTicketComment createTicketComment(ActemiumTicket ticket, ActemiumEmployee tech, String commentText) {
		try {
			return new ActemiumTicketComment.TicketCommentBuilder()
					.ticket(ticket)
					.user(tech)
					.userRole(tech.getRole().toString())
					.dateTimeOfComment(LocalDateTime.now())
					.commentText(commentText)
					.build();
		} catch (InformationRequiredException e) {
			e.printStackTrace();
		}
		return null;
	}    

}
