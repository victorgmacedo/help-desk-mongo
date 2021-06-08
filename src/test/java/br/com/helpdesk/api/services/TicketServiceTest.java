package br.com.helpdesk.api.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import br.com.helpdesk.api.entity.Ticket;

@ExtendWith(SpringExtension.class)
public class TicketServiceTest {

	@MockBean
	private TicketService ticketService;

	@BeforeEach
	public void before() {
		Ticket ticket = new Ticket();
		ticket.setId("123");
		Mockito.when(ticketService.findById(Mockito.anyString())).thenReturn(ticket);
	}
	
	@Test
	public void simpleTest() {
		System.out.println("Test");
		Ticket ticket = ticketService.findById("123");
		assertNotNull(ticket);
		assertEquals("123", ticket.getId());
	}
}
