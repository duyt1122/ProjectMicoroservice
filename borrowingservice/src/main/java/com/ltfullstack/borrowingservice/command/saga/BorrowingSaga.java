package com.ltfullstack.borrowingservice.command.saga;

import com.ltfullstack.borrowingservice.command.command.DeleteBorrowingCommand;
import com.ltfullstack.borrowingservice.command.event.BorrowingCreatedEvent;
import com.ltfullstack.borrowingservice.command.event.BorrwingDeleteEvent;
import com.ltfullstack.commonservice.command.RollBackStatusBookCommand;
import com.ltfullstack.commonservice.command.UpdateStatusBookCommand;
import com.ltfullstack.commonservice.event.BookRollBackStatusEvent;
import com.ltfullstack.commonservice.event.BookUpdateStatusEvent;
import com.ltfullstack.commonservice.model.BookResponseCommandModel;
import com.ltfullstack.commonservice.model.EmployeeResponseCommandModel;
import com.ltfullstack.commonservice.queries.GetBookDetailQuery;
import com.ltfullstack.commonservice.queries.GetDetailEmployeeModel;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.modelling.saga.SagaEventHandler;
import org.axonframework.modelling.saga.SagaLifecycle;
import org.axonframework.modelling.saga.StartSaga;
import org.axonframework.queryhandling.QueryGateway;
import org.axonframework.spring.stereotype.Saga;
import org.springframework.beans.factory.annotation.Autowired;

@Slf4j
@Saga
public class BorrowingSaga {

    @Autowired
    private transient CommandGateway commandGateway;

    @Autowired
    private transient QueryGateway queryGateway;

    @StartSaga
    @SagaEventHandler(associationProperty = "id")
    private void handle(BorrowingCreatedEvent event){
      log.info("BorrowingCreatedEvent in saga for BookId: " + event.getBookId() + " : EmployeeId: " + event.getEmployeeId());

      try {
          GetBookDetailQuery getBookDetailQuery = new GetBookDetailQuery(event.getBookId());
          BookResponseCommandModel bookResponseCommandModel = queryGateway.query(getBookDetailQuery, ResponseTypes.instanceOf(BookResponseCommandModel.class)).join();

          if(!bookResponseCommandModel.getIsRealy()){
              throw new Exception("Sach Da co nguoi muon");
          }else{
              SagaLifecycle.associateWith("bookId", event.getBookId());
              UpdateStatusBookCommand command = new UpdateStatusBookCommand(event.getBookId(), false , event.getEmployeeId(), event.getId());
              commandGateway.sendAndWait(command);
          }
      }catch (Exception ex){
          rollbackBorrowingRecord(event.getId());
          log.error(ex.getMessage());
      }
    }

    @SagaEventHandler(associationProperty = "bookId")
    public void handler(BookUpdateStatusEvent event){
        log.info("BookUpdateStatusEvent in Saga for bookId:  " + event.getBookId());
        try{
            GetDetailEmployeeModel getDetailEmployeeModel = new GetDetailEmployeeModel(event.getEmployeeId());
            EmployeeResponseCommandModel employeeResponseCommandModel = queryGateway.query(getDetailEmployeeModel,ResponseTypes.instanceOf(EmployeeResponseCommandModel.class)).join();
            if(employeeResponseCommandModel.getIsDisciplined()){
                throw new Exception("Nhân viên đã bị kỉ luật");
            }else{
                log.info("Đã mượn sách thành công");
                SagaLifecycle.end();
            }
        } catch (Exception e) {
            rollBackBookStatus(
                    event.getBookId(), event.getEmployeeId(), event.getBorrowingId()
            );
            log.info(e.getMessage());
        }

    }

    private void rollbackBorrowingRecord(String id){
        DeleteBorrowingCommand command = new DeleteBorrowingCommand(id);
        commandGateway.sendAndWait(command);
    }

    private void rollBackBookStatus(String bookId, String employeeId, String borrowingId){
        SagaLifecycle.associateWith("bookId",bookId);
        RollBackStatusBookCommand command = new RollBackStatusBookCommand(bookId,true,employeeId,borrowingId);
        commandGateway.sendAndWait(command);
    }

    @SagaEventHandler(associationProperty = "bookId")
    public void hanle(BookRollBackStatusEvent event){
        log.info("BookRollbackStatusEvent in Saga with book id : {}",event.getBookId());
        rollbackBorrowingRecord(event.getBorrowingId());
    }
    @SagaEventHandler(associationProperty = "id")
    public void handlle(BorrwingDeleteEvent event){
    log.info("BorrowingDeleteEvent in Saga with Borrowing id : {}",event.getId());
    SagaLifecycle.end();
    }

}
