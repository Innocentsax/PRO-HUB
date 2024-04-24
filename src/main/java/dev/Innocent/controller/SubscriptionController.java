package dev.Innocent.controller;

import dev.Innocent.Service.SubscriptionService;
import dev.Innocent.Service.UserService;
import dev.Innocent.enums.PlanType;
import dev.Innocent.model.Subscription;
import dev.Innocent.model.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequestMapping("/api/subscription")
public class SubscriptionController {
    private SubscriptionService subscriptionService;
    private UserService userService;
    @Autowired
    public SubscriptionController(SubscriptionService subscriptionService,
                                  UserService userService) {
        this.subscriptionService = subscriptionService;
        this.userService = userService;
    }
    @GetMapping("/user")
    public ResponseEntity<Subscription> getUserSubscription(
            @RequestHeader("Authorization") String jwt) throws Exception {
        User user = userService.findUserProfileByJwt(jwt);
        Subscription subscription = subscriptionService.getUsersSubscription(user.getId());
        return new ResponseEntity<>(subscription, HttpStatus.OK);
    }

    @PatchMapping("/upgrade")
    public ResponseEntity<Subscription> upgradeSubscription(
            @RequestHeader("Authorization") String jwt,
            @RequestParam PlanType planType) throws Exception {
        User user = userService.findUserProfileByJwt(jwt);
        Subscription subscription = subscriptionService.upgradeSubscription(user.getId(), planType);
        return new ResponseEntity<>(subscription, HttpStatus.OK);
    }
}
