package business.service;

import data.interfaces.ISecurityRepository;
import data.model.User;
import data.repository.SecurityRepository;

public class SecurityService implements ISecurityRepository<User> {

    private SecurityRepository repo;

    public SecurityService() {
        this.repo = new SecurityRepository();
    }

    @Override
    public boolean authenticate(User user) {
        return repo.authenticate(user);
    }

    @Override
    public boolean register(User user) {
        return repo.register(user);
    }

    @Override
    public boolean isAuthorized(User user) {
        return repo.isAuthorized(user);
    }

}
