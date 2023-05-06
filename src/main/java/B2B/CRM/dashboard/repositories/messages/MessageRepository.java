package B2B.CRM.dashboard.repositories.messages;


import B2B.CRM.dashboard.entities.accounts.User;
import B2B.CRM.dashboard.entities.messages.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {
    List<Message> findByReceiverOrderByCreatedAtDesc(User receiver);
}
