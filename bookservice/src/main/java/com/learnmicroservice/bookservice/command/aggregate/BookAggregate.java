package com.learnmicroservice.bookservice.command.aggregate;

import com.learnmicroservice.bookservice.command.command.CreateBookCommand;
import com.learnmicroservice.bookservice.command.command.DeleteBookCommand;
import com.learnmicroservice.bookservice.command.command.UpdateBookCommand;
import com.learnmicroservice.bookservice.command.event.BookCreateEvent;
import com.learnmicroservice.bookservice.command.event.BookDeleteEvent;
import com.learnmicroservice.bookservice.command.event.BookUpdateEvent;
import com.ltfullstack.commonservice.command.UpdateStatusBookCommand;
import com.ltfullstack.commonservice.event.BookUpdateStatusEvent;
import lombok.NoArgsConstructor;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;
import org.springframework.beans.BeanUtils;

@Aggregate
@NoArgsConstructor
public class BookAggregate {

    @AggregateIdentifier
    private String id;

    private String name;

    private String author;

    private Boolean isRealy;

    @CommandHandler
    public BookAggregate(CreateBookCommand command){
        BookCreateEvent bookCreateEvent = new BookCreateEvent(command.getId(), command.getName(), command.getAuthor(),true);
        AggregateLifecycle.apply(bookCreateEvent);
    }

    @CommandHandler
    public void handleUpdate(UpdateBookCommand command){
        BookUpdateEvent bookUpdateEvent = new BookUpdateEvent(command.getId(), command.getName(), command.getAuthor(), command.getIsRealy());
        AggregateLifecycle.apply(bookUpdateEvent);
    }

    @CommandHandler
    public void hanleDelete(DeleteBookCommand command){
        BookDeleteEvent bookDeleteEvent  =  new BookDeleteEvent(command.getId());
        AggregateLifecycle.apply(bookDeleteEvent);
    }

    @CommandHandler
    public void handler(UpdateStatusBookCommand command){
        BookUpdateStatusEvent event = new BookUpdateStatusEvent();
        BeanUtils.copyProperties(command,event);
        AggregateLifecycle.apply(event);
    }

    @EventSourcingHandler
    public void on(BookUpdateStatusEvent event){
        this.id = event.getBookId();
        this.isRealy = event.getIsReady();
    }


    @EventSourcingHandler
    public void on(BookCreateEvent event){
        this.id = event.getId();
        this.name = event.getName();
        this.author = event.getAuthor();
        this.isRealy = event.getIsRealy();
    }

    @EventSourcingHandler
    public void on(BookUpdateEvent event){
        this.id = event.getId();
        this.name = event.getName();
        this.author = event.getAuthor();
        this.isRealy = event.getIsRealy();
    }
    @EventSourcingHandler
    public void on(BookDeleteEvent event){
        this.id = event.getId();
    }
}
