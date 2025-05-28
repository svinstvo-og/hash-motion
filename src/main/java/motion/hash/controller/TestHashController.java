package motion.hash.controller;

import motion.hash.service.HashingSerivce;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestHashController {

    final HashingSerivce hashingSerivce;

    public TestHashController(HashingSerivce hashingSerivce) {
        this.hashingSerivce = hashingSerivce;
    }

    @GetMapping("/hash")
    public String generateHash() {
        return hashingSerivce.generateUniqueHash();
    }

}
