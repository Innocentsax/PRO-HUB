package dev.Innocent.Service;

import dev.Innocent.enums.PlanType;
import dev.Innocent.model.Subscription;
import dev.Innocent.model.User;

public interface SubscriptionService {
    Subscription createSubscription(User user);
    Subscription getUsersSubscription(Long userId) throws Exception;
    Subscription upgradeSubscription(Long userId, PlanType planType);
    boolean isValid(Subscription subscription);
}
