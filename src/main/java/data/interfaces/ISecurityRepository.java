package data.interfaces;

public interface ISecurityRepository<E> {

    boolean authenticate(E user);

    boolean register(E user);

    boolean isAuthorized(E user);

}
