package motion.hash.service;

import lombok.extern.slf4j.Slf4j;
import motion.hash.model.HashStorageObject;
import motion.hash.repository.HashRepository;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.*;

@Service
@Slf4j
public class HashingSerivce {
    private final HashRepository hashRepository;
    private final RedisTemplate<String, String> redisTemplate;

    public HashingSerivce(HashRepository hashRepository, RedisTemplate<String, String> redisTemplate) {
        this.hashRepository = hashRepository;
        this.redisTemplate = redisTemplate;
        
        checkAvailability();
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

    public String generateHash() {
        long seqNum = hashRepository.getNextSequenceNumber();
        //log.info("Retrieved sequence number: {}", seqNum);
        return encode(seqNum);
    }

    private Set<String> generateHashes(Integer numberOfHashes) {
        Set<String> hashes = new HashSet<>();

        log.info("Generating {} hashes...", numberOfHashes);
        for (int i = 0; i < numberOfHashes; i++) {
            hashes.add(generateHash());
        }
        log.info("Completed generating {} hashes.", numberOfHashes);
        return hashes;
    }

    public void putHashes(Integer numberOfHashes) {
        Set<String> hashes = generateHashes(numberOfHashes);
        log.info("Putting hashes into redis");
        redisTemplate.opsForSet().add("hashes:available", hashes.toArray(new String[0]));
    }

    public void checkAvailability() {
        Long numberOfHashes = redisTemplate.opsForSet().size("hashes:available");
        if (numberOfHashes == null && numberOfHashes == 0) {
            log.info("No hashes available");
            putHashes(300);
        }
    }
}
