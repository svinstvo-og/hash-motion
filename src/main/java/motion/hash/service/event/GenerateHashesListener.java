package motion.hash.service.event;

import lombok.extern.slf4j.Slf4j;
import motion.hash.service.HashingSerivce;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class GenerateHashesListener {

    private final HashingSerivce hashingSerivce;

    public GenerateHashesListener(HashingSerivce hashingSerivce) {
        this.hashingSerivce = hashingSerivce;
    }

    @KafkaListener(topics = "${kafka.topic.name:generate-hashes}")
    public void generateHashesHandler() {
        log.info("Received GenerateHashes event");
        hashingSerivce.putHashes(300);
        log.info("Putted hashes successfully");
    }
}
