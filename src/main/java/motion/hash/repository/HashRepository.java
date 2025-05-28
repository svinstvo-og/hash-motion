package motion.hash.repository;

import motion.hash.model.RootEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface HashRepository extends JpaRepository<RootEntity, Integer> {
    @Query(value = "SELECT nextval('hash_id_seq')", nativeQuery = true)
    long getNextSequenceNumber();
}