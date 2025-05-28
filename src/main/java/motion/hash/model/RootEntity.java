package motion.hash.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;


// This dummy entity allows us to bypass JpaRepository require to have an entity, without this dummy I couldn't initiate the repository
@Entity
public class RootEntity {
    @Id
    private Integer id;
}
