package com.learnmicroservice.bookservice.command.event;

import com.learnmicroservice.bookservice.command.data.Book;
import com.learnmicroservice.bookservice.command.data.BookRepository;
import com.ltfullstack.commonservice.event.BookUpdateStatusEvent;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class BookEventHandler {

    private final BookRepository bookRepository;

    public BookEventHandler(BookRepository bookRepository){
        this.bookRepository = bookRepository;
    }

    @EventHandler
    public void on(BookCreateEvent event){
        Book book= new Book();
        BeanUtils.copyProperties(event, book);
        this.bookRepository.save(book);
    }

    @EventHandler
    public void on(BookUpdateEvent event){
        Optional<Book> optionalBook = bookRepository.findById(event.getId());
        if(optionalBook.isPresent()){
            Book book = optionalBook.get();
            book.setName(event.getName());
            book.setAuthor(event.getAuthor());
            book.setIsRealy(event.getIsRealy());

            bookRepository.save(book);
        }
    }
    @EventHandler
    public void on(BookDeleteEvent event){
     Optional<Book> optionalBook = bookRepository.findById(event.getId());
     if(optionalBook.isPresent()){
         Book book = optionalBook.get();
         bookRepository.delete(book);
     }
    }

    @EventHandler
    public void on(BookUpdateStatusEvent event){
        Optional<Book> oldBook = bookRepository.findById(event.getBookId());
        oldBook.ifPresent(book -> {
                    book.setIsRealy(event.getIsReady());
                    bookRepository.save(book);
                }
        );

    }
}
