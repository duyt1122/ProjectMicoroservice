package com.ltfullstack.borrowingservice.command.event;

import com.ltfullstack.borrowingservice.command.data.Borrowing;
import com.ltfullstack.borrowingservice.command.data.BorrowingRepository;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.stereotype.Component;

@Component
public class BorrowingEventHandler {

    private final BorrowingRepository borrowingRepository;

    public BorrowingEventHandler(BorrowingRepository borrowingRepository){
        this.borrowingRepository = borrowingRepository;
    }

    @EventHandler
    public void on(BorrowingCreatedEvent event){
        Borrowing borrowing = new Borrowing();
        borrowing.setId(event.getId());
        borrowing.setBorrowingDate(event.getBorrowingDate());
        borrowing.setEmployeeId(event.getEmployeeId());
        borrowing.setBookId(event.getBookId());
        borrowingRepository.save(borrowing);
    }
}
