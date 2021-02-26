package br.com.zup.transacoes.consumer;

public class CartaoMessage {
    private String id;
    private String email;

    @Deprecated
    public CartaoMessage(){}

    public CartaoMessage(String id, String email) {
        this.id = id;
        this.email = email;
    }

    public String getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public String toString() {
        return "CartaoMessage{" +
                "id=" + id +
                ", email='" + email + '\'' +
                '}';
    }
}
