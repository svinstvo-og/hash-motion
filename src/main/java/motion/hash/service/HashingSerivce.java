package motion.hash.service;

import lombok.extern.slf4j.Slf4j;
import motion.hash.repository.HashRepository;
import org.springframework.stereotype.Service;

import java.math.BigInteger;

@Service
@Slf4j
public class HashingSerivce {
    private final HashRepository hashRepository;

    public HashingSerivce(HashRepository hashRepository) {
        this.hashRepository = hashRepository;
    }

    private static final String BASE62 =
            "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";


    private static String encode(long sequenceNumber) {
        BigInteger num = BigInteger.valueOf(sequenceNumber);
        StringBuilder sb = new StringBuilder();
        do {
            sb.insert(0, BASE62.charAt(num.mod(BigInteger.valueOf(62)).intValue()));
            num = num.divide(BigInteger.valueOf(62));
        } while (num.compareTo(BigInteger.ZERO) > 0);

        while (sb.length() < 8) {
            sb.insert(0, '0');
        }
        return sb.toString();
    }

    public String generateUniqueHash() {
        long seqNum = hashRepository.getNextSequenceNumber();
        log.info("Retrieved sequence number: {}", seqNum);
        return encode(seqNum);
    }
}
